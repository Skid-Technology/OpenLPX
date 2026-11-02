package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts.CustomChart;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;

public class MetricsBase {

    public static final String METRICS_VERSION = "3.1.0";

    private final ScheduledExecutorService scheduler;

    private final String platform;

    private final String serverUuid;

    private final int serviceId;

    private final Consumer<JsonObjectBuilder> appendPlatformDataConsumer;

    private final Consumer<JsonObjectBuilder> appendServiceDataConsumer;

    private final Consumer<Runnable> submitTaskConsumer;

    private final Supplier<Boolean> checkServiceEnabledSupplier;

    private final BiConsumer<String, Throwable> errorLogger;

    private final Consumer<String> infoLogger;

    private final boolean logErrors;

    private final boolean logSentData;

    private final boolean logResponseStatusText;

    private final Set<CustomChart> customCharts = new HashSet<>();

    private final boolean enabled;

    public MetricsBase(
            String text,
            String text2,
            int value,
            boolean matches,
            Consumer<JsonObjectBuilder> consumer,
            Consumer<JsonObjectBuilder> consumer2,
            Consumer<Runnable> consumer3,
            Supplier<Boolean> supplier,
            BiConsumer<String, Throwable> biConsumer,
            Consumer<String> consumer4,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
        (scheduledThreadPoolExecutor =
                        new ScheduledThreadPoolExecutor(
                                1,
                                result -> {
                                    Thread thread;
                                    (thread = new Thread(result, "bStats-Metrics")).setDaemon(true);
                                    return thread;
                                }))
                .setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        this.scheduler = scheduledThreadPoolExecutor;
        this.platform = text;
        this.serverUuid = text2;
        this.serviceId = value;
        this.enabled = matches;
        this.appendPlatformDataConsumer = consumer;
        this.appendServiceDataConsumer = consumer2;
        this.submitTaskConsumer = consumer3;
        this.checkServiceEnabledSupplier = supplier;
        this.errorLogger = biConsumer;
        this.infoLogger = consumer4;
        this.logErrors = matches2;
        this.logSentData = matches3;
        this.logResponseStatusText = matches4;
        if (!matches5) {
            this.checkRelocation();
        }
        if (matches) {
            this.startSubmitting();
        }
    }

    public void addCustomChart(CustomChart customChart) {
        this.customCharts.add(customChart);
    }

    public void shutdown() {
        this.scheduler.shutdown();
    }

    private void startSubmitting() {
        Runnable runnable =
                () -> {
                    if (this.enabled && this.checkServiceEnabledSupplier.get()) {
                        if (this.submitTaskConsumer != null) {
                            this.submitTaskConsumer.accept(this::submitData);
                        } else {
                            this.submitData();
                        }
                    } else {
                        this.scheduler.shutdown();
                    }
                };
        long value = (long) (60000.0 * (3.0 + Math.random() * 3.0));
        long value2 = (long) (60000.0 * (Math.random() * 30.0));
        this.scheduler.schedule(runnable, value, TimeUnit.MILLISECONDS);
        this.scheduler.scheduleAtFixedRate(
                runnable, value + value2, 1800000L, TimeUnit.MILLISECONDS);
    }

    private void submitData() {
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        this.appendPlatformDataConsumer.accept(jsonObjectBuilder);
        JsonObjectBuilder jsonObjectBuilder2 = new JsonObjectBuilder();
        this.appendServiceDataConsumer.accept(jsonObjectBuilder2);
        Stream<CustomChart> stream2 = this.customCharts.stream();
        JsonObjectBuilder.JsonObject[] jsonObjects =
                stream2.<JsonObjectBuilder.JsonObject>map(
                                result ->
                                        result.getRequestJsonObject(
                                                this.errorLogger, this.logErrors))
                        .filter(Objects::nonNull)
                        .toArray(JsonObjectBuilder.JsonObject[]::new);
        jsonObjectBuilder2.appendField("id", this.serviceId);
        jsonObjectBuilder2.appendField("customCharts", jsonObjects);
        jsonObjectBuilder.appendField("service", jsonObjectBuilder2.build());
        jsonObjectBuilder.appendField("serverUUID", this.serverUuid);
        jsonObjectBuilder.appendField("metricsVersion", "3.1.0");
        JsonObjectBuilder.JsonObject jsonObject = jsonObjectBuilder.build();
        this.scheduler.execute(
                () -> {
                    try {
                        this.sendData(jsonObject);
                    } catch (Exception exception) {
                        if (this.logErrors) {
                            this.errorLogger.accept(
                                    "Could not submit bStats metrics data", exception);
                        }
                    }
                });
    }

    private void sendData(JsonObjectBuilder.JsonObject jsonObject) throws Exception {
        if (this.logSentData) {
            this.infoLogger.accept("Sent bStats metrics data: " + jsonObject.toString());
        }
        String text = String.format("https://bStats.org/api/v2/data/%s", this.platform);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(text).openConnection();
        byte[] values = compress(jsonObject.toString());
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.addRequestProperty("Accept", "application/json");
        httpsURLConnection.addRequestProperty("Connection", "close");
        httpsURLConnection.addRequestProperty("Content-Encoding", "gzip");
        httpsURLConnection.addRequestProperty("Content-Length", String.valueOf(values.length));
        httpsURLConnection.setRequestProperty("Content-Type", "application/json");
        httpsURLConnection.setRequestProperty("User-Agent", "Metrics-Service/1");
        httpsURLConnection.setDoOutput(true);
        DataOutputStream dataOutputStream =
                new DataOutputStream(httpsURLConnection.getOutputStream());
        try {
            dataOutputStream.write(values);
        } catch (Throwable throwable) {
            try {
                dataOutputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        dataOutputStream.close();
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
        String text2;
        try {
            while ((text2 = bufferedReader.readLine()) != null) {
                stringBuilder.append(text2);
            }
        } catch (Throwable throwable3) {
            try {
                bufferedReader.close();
            } catch (Throwable throwable4) {
                throwable3.addSuppressed(throwable4);
            }
            throw throwable3;
        }
        bufferedReader.close();
        if (this.logResponseStatusText) {
            this.infoLogger.accept("Sent data to bStats and received response: " + stringBuilder);
        }
    }

    private void checkRelocation() {
        if (System.getProperty("bstats.relocatecheck") == null
                || !System.getProperty("bstats.relocatecheck").equals("false")) {
            String text = new String(new byte[] {111, 114, 103, 46, 98, 115, 116, 97, 116, 115});
            String text2 =
                    new String(new byte[] {121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101});
            if (MetricsBase.class.getPackage().getName().startsWith(text)
                    || MetricsBase.class.getPackage().getName().startsWith(text2)) {
                throw new IllegalStateException(
                        "bStats Metrics class has not been relocated correctly!");
            }
        }
    }

    private static byte[] compress(String text) throws IOException {
        if (text == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        try {
            gZIPOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (Throwable throwable) {
            try {
                gZIPOutputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        gZIPOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}
