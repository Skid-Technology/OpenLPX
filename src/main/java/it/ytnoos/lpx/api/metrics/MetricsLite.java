package it.ytnoos.lpx.api.metrics;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.GlobalRegionScheduler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;

public class MetricsLite {

    private final Plugin plugin;

    private final MetricsLite.MetricsBase metricsBase;

    public MetricsLite(Plugin plugin2) {
        this.plugin = plugin2;
        File file = new File(plugin2.getDataFolder().getParentFile(), "bStats");
        File file2;
        YamlConfiguration yamlConfiguration;
        if (!(yamlConfiguration =
                        YamlConfiguration.loadConfiguration(file2 = new File(file, "config.yml")))
                .isSet("serverUuid")) {
            yamlConfiguration.addDefault("enabled", true);
            yamlConfiguration.addDefault("serverUuid", UUID.randomUUID().toString());
            yamlConfiguration.addDefault("logFailedRequests", false);
            yamlConfiguration.addDefault("logSentData", false);
            yamlConfiguration.addDefault("logResponseStatusText", false);
            yamlConfiguration
                    .options()
                    .header(
                            "bStats (https://bStats.org) collects some basic information for plugin authors, like how\nmany people use their plugin and their total player count. It's recommended to keep bStats\nenabled, but if you're not comfortable with this, you can turn this setting off. There is no\nperformance penalty associated with having metrics enabled, and data sent to bStats is fully\nanonymous.")
                    .copyDefaults(true);
            try {
                yamlConfiguration.save(file2);
            } catch (IOException oException) {
            }
        }
        boolean booleanValue = yamlConfiguration.getBoolean("enabled", true);
        String string = yamlConfiguration.getString("serverUuid");
        boolean booleanValue2 = yamlConfiguration.getBoolean("logFailedRequests", false);
        boolean booleanValue3 = yamlConfiguration.getBoolean("logSentData", false);
        boolean booleanValue4 = yamlConfiguration.getBoolean("logResponseStatusText", false);
        this.metricsBase =
                new MetricsLite.MetricsBase(
                        "bukkit",
                        string,
                        9156,
                        booleanValue,
                        this::appendPlatformData,
                        this::appendServiceData,
                        result -> {
                            GlobalRegionScheduler globalRegionScheduler =
                                    FoliaScheduler.getGlobalRegionScheduler();
                            globalRegionScheduler.run(plugin2, result2 -> result.run());
                        },
                        plugin2::isEnabled,
                        (result, result3) ->
                                this.plugin.getLogger().log(Level.WARNING, result, result3),
                        result -> this.plugin.getLogger().log(Level.INFO, result),
                        booleanValue2,
                        booleanValue3,
                        booleanValue4);
    }

    public void shutdown() {
        this.metricsBase.shutdown();
    }

    public void addCustomChart(MetricsLite.CustomChart customChart) {
        this.metricsBase.addCustomChart(customChart);
    }

    private void appendPlatformData(MetricsLite.JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.appendField("playerAmount", this.getPlayerAmount());
        jsonObjectBuilder.appendField("onlineMode", Bukkit.getOnlineMode() ? 1 : 0);
        jsonObjectBuilder.appendField("bukkitVersion", Bukkit.getVersion());
        jsonObjectBuilder.appendField("bukkitName", Bukkit.getName());
        jsonObjectBuilder.appendField("javaVersion", System.getProperty("java.version"));
        jsonObjectBuilder.appendField("osName", System.getProperty("os.name"));
        jsonObjectBuilder.appendField("osArch", System.getProperty("os.arch"));
        jsonObjectBuilder.appendField("osVersion", System.getProperty("os.version"));
        jsonObjectBuilder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
    }

    private void appendServiceData(MetricsLite.JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.appendField("pluginVersion", this.plugin.getDescription().getVersion());
    }

    private int getPlayerAmount() {
        try {
            Method method;
            return (method = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers"))
                            .getReturnType()
                            .equals(Collection.class)
                    ? ((Collection) method.invoke(Bukkit.getServer())).size()
                    : ((Player[]) method.invoke(Bukkit.getServer())).length;
        } catch (Exception exception) {
            return Bukkit.getOnlinePlayers().size();
        }
    }

    public static class AdvancedBarChart extends MetricsLite.CustomChart {

        private final Callable<Map<String, int[]>> callable;

        public AdvancedBarChart(String text, Callable<Map<String, int[]>> callable2) {
            super(text);
            this.callable = callable2;
        }

        @Override
        protected MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception {
            MetricsLite.JsonObjectBuilder jsonObjectBuilder = new MetricsLite.JsonObjectBuilder();
            Map<String, int[]> entries;
            if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
                boolean matches = true;
                for (Entry<String, int[]> entry : entries.entrySet()) {
                    if (((int[]) entry.getValue()).length != 0) {
                        matches = false;
                        jsonObjectBuilder.appendField(
                                (String) entry.getKey(), (int[]) entry.getValue());
                    }
                }
                return matches
                        ? null
                        : new MetricsLite.JsonObjectBuilder()
                                .appendField("values", jsonObjectBuilder.build())
                                .build();
            } else {
                return null;
            }
        }
    }

    public static class AdvancedPie extends MetricsLite.CustomChart {

        private final Callable<Map<String, Integer>> callable;

        public AdvancedPie(String text, Callable<Map<String, Integer>> callable2) {
            super(text);
            this.callable = callable2;
        }

        @Override
        protected MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception {
            MetricsLite.JsonObjectBuilder jsonObjectBuilder = new MetricsLite.JsonObjectBuilder();
            Map<String, Integer> entries;
            if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
                boolean matches = true;
                for (Entry<String, Integer> entry : entries.entrySet()) {
                    if ((Integer) entry.getValue() != 0) {
                        matches = false;
                        jsonObjectBuilder.appendField(
                                (String) entry.getKey(), (Integer) entry.getValue());
                    }
                }
                return matches
                        ? null
                        : new MetricsLite.JsonObjectBuilder()
                                .appendField("values", jsonObjectBuilder.build())
                                .build();
            } else {
                return null;
            }
        }
    }

    public abstract static class CustomChart {

        private final String chartId;

        protected CustomChart(String chartId) {
            if (chartId == null) {
                throw new IllegalArgumentException("chartId must not be null");
            }
            this.chartId = chartId;
        }

        public MetricsLite.JsonObjectBuilder.JsonObject getRequestJsonObject(
                BiConsumer<String, Throwable> biConsumer, boolean matches) {
            MetricsLite.JsonObjectBuilder jsonObjectBuilder;
            (jsonObjectBuilder = new MetricsLite.JsonObjectBuilder())
                    .appendField("chartId", this.chartId);
            try {
                MetricsLite.JsonObjectBuilder.JsonObject jsonObject;
                if ((jsonObject = this.getChartData()) == null) {
                    return null;
                }
                jsonObjectBuilder.appendField("data", jsonObject);
            } catch (Throwable throwable) {
                if (matches) {
                    biConsumer.accept(
                            "Failed to get data for custom chart with id " + this.chartId,
                            throwable);
                }
                return null;
            }
            return jsonObjectBuilder.build();
        }

        protected abstract MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception;
    }

    public static class DrilldownPie extends MetricsLite.CustomChart {

        private final Callable<Map<String, Map<String, Integer>>> callable;

        public DrilldownPie(String text, Callable<Map<String, Map<String, Integer>>> callable2) {
            super(text);
            this.callable = callable2;
        }

        @Override
        public MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception {
            MetricsLite.JsonObjectBuilder jsonObjectBuilder = new MetricsLite.JsonObjectBuilder();
            Map<String, Map<String, Integer>> entries;
            if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
                boolean matches = true;
                for (Entry<String, Map<String, Integer>> entry : entries.entrySet()) {
                    MetricsLite.JsonObjectBuilder jsonObjectBuilder2 =
                            new MetricsLite.JsonObjectBuilder();
                    boolean matches2 = true;
                    for (Entry<Object, Object> entry2 :
                            ((Iterable<Entry<Object, Object>>)
                                    ((Iterable<?>)
                                            ((Map) entries.get(entry.getKey())).entrySet()))) {
                        jsonObjectBuilder2.appendField(
                                (String) entry2.getKey(), (Integer) entry2.getValue());
                        matches2 = false;
                    }
                    if (!matches2) {
                        matches = false;
                        jsonObjectBuilder.appendField(
                                (String) entry.getKey(), jsonObjectBuilder2.build());
                    }
                }
                return matches
                        ? null
                        : new MetricsLite.JsonObjectBuilder()
                                .appendField("values", jsonObjectBuilder.build())
                                .build();
            } else {
                return null;
            }
        }
    }

    public static class JsonObjectBuilder {

        private StringBuilder builder = new StringBuilder();

        private boolean hasAtLeastOneField = false;

        public JsonObjectBuilder() {
            this.builder.append("{");
        }

        private static String escape(String text) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < text.length(); index++) {
                char value;
                if ((value = text.charAt(index)) == '"') {
                    stringBuilder.append("\\\"");
                } else if (value == '\\') {
                    stringBuilder.append("\\\\");
                } else if (value <= 15) {
                    stringBuilder.append("\\u000").append(Integer.toHexString(value));
                } else if (value <= 31) {
                    stringBuilder.append("\\u00").append(Integer.toHexString(value));
                } else {
                    stringBuilder.append(value);
                }
            }
            return stringBuilder.toString();
        }

        public MetricsLite.JsonObjectBuilder appendNull(String text) {
            this.appendFieldUnescaped(text, "null");
            return this;
        }

        public MetricsLite.JsonObjectBuilder appendField(String text, String text2) {
            if (text2 == null) {
                throw new IllegalArgumentException("JSON value must not be null");
            }
            this.appendFieldUnescaped(text, "\"" + escape(text2) + "\"");
            return this;
        }

        public MetricsLite.JsonObjectBuilder appendField(String text, int value) {
            this.appendFieldUnescaped(text, String.valueOf(value));
            return this;
        }

        public MetricsLite.JsonObjectBuilder appendField(
                String text, MetricsLite.JsonObjectBuilder.JsonObject jsonObject) {
            if (jsonObject == null) {
                throw new IllegalArgumentException("JSON object must not be null");
            }
            this.appendFieldUnescaped(text, jsonObject.toString());
            return this;
        }

        public MetricsLite.JsonObjectBuilder appendField(String text, String[] texts) {
            if (texts == null) {
                throw new IllegalArgumentException("JSON values must not be null");
            }
            String text2 =
                    Arrays.stream(texts)
                            .map(result -> "\"" + escape(result) + "\"")
                            .collect(Collectors.joining(","));
            this.appendFieldUnescaped(text, "[" + text2 + "]");
            return this;
        }

        public MetricsLite.JsonObjectBuilder appendField(String text, int[] values) {
            if (values == null) {
                throw new IllegalArgumentException("JSON values must not be null");
            }
            String text2 =
                    Arrays.stream(values)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(","));
            this.appendFieldUnescaped(text, "[" + text2 + "]");
            return this;
        }

        public MetricsLite.JsonObjectBuilder appendField(
                String text, MetricsLite.JsonObjectBuilder.JsonObject[] jsonObjects) {
            if (jsonObjects == null) {
                throw new IllegalArgumentException("JSON values must not be null");
            }
            String text2 =
                    Arrays.stream(jsonObjects)
                            .map(MetricsLite.JsonObjectBuilder.JsonObject::toString)
                            .collect(Collectors.joining(","));
            this.appendFieldUnescaped(text, "[" + text2 + "]");
            return this;
        }

        private void appendFieldUnescaped(String text, String text2) {
            if (this.builder == null) {
                throw new IllegalStateException("JSON has already been built");
            }
            if (text == null) {
                throw new IllegalArgumentException("JSON key must not be null");
            }
            if (this.hasAtLeastOneField) {
                this.builder.append(",");
            }
            this.builder.append("\"").append(escape(text)).append("\":").append(text2);
            this.hasAtLeastOneField = true;
        }

        public MetricsLite.JsonObjectBuilder.JsonObject build() {
            if (this.builder == null) {
                throw new IllegalStateException("JSON has already been built");
            }
            MetricsLite.JsonObjectBuilder.JsonObject jsonObject =
                    new MetricsLite.JsonObjectBuilder.JsonObject(
                            this.builder.append("}").toString());
            this.builder = null;
            return jsonObject;
        }

        public static class JsonObject {

            private final String value;

            private JsonObject(String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return this.value;
            }
        }
    }

    public static class MetricsBase {

        public static final String METRICS_VERSION = "3.0.2";

        private final ScheduledExecutorService scheduler;

        private final String platform;

        private final String serverUuid;

        private final int serviceId;

        private final Consumer<MetricsLite.JsonObjectBuilder> appendPlatformDataConsumer;

        private final Consumer<MetricsLite.JsonObjectBuilder> appendServiceDataConsumer;

        private final Consumer<Runnable> submitTaskConsumer;

        private final Supplier<Boolean> checkServiceEnabledSupplier;

        private final BiConsumer<String, Throwable> errorLogger;

        private final Consumer<String> infoLogger;

        private final boolean logErrors;

        private final boolean logSentData;

        private final boolean logResponseStatusText;

        private final Set<MetricsLite.CustomChart> customCharts = new HashSet<>();

        private final boolean enabled;

        public MetricsBase(
                String text,
                String text2,
                int value,
                boolean matches,
                Consumer<MetricsLite.JsonObjectBuilder> consumer,
                Consumer<MetricsLite.JsonObjectBuilder> consumer2,
                Consumer<Runnable> consumer3,
                Supplier<Boolean> supplier,
                BiConsumer<String, Throwable> biConsumer,
                Consumer<String> consumer4,
                boolean matches2,
                boolean matches3,
                boolean matches4) {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
            (scheduledThreadPoolExecutor =
                            new ScheduledThreadPoolExecutor(
                                    1, result -> new Thread(result, "bStats-Metrics")))
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
            this.checkRelocation();
            if (matches) {
                this.startSubmitting();
            }
        }

        private static byte[] compress(String text) throws IOException {
            if (text == null) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                gZIPOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            }
            return byteArrayOutputStream.toByteArray();
        }

        public void addCustomChart(MetricsLite.CustomChart customChart) {
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
            MetricsLite.JsonObjectBuilder jsonObjectBuilder = new MetricsLite.JsonObjectBuilder();
            this.appendPlatformDataConsumer.accept(jsonObjectBuilder);
            MetricsLite.JsonObjectBuilder jsonObjectBuilder2 = new MetricsLite.JsonObjectBuilder();
            this.appendServiceDataConsumer.accept(jsonObjectBuilder2);
            Stream<it.ytnoos.lpx.api.metrics.MetricsLite.CustomChart> stream2 =
                    this.customCharts.stream();
            MetricsLite.JsonObjectBuilder.JsonObject[] jsonObjects =
                    stream2.<MetricsLite.JsonObjectBuilder.JsonObject>map(
                                    result ->
                                            result.getRequestJsonObject(
                                                    this.errorLogger, this.logErrors))
                            .filter(Objects::nonNull)
                            .toArray(MetricsLite.JsonObjectBuilder.JsonObject[]::new);
            jsonObjectBuilder2.appendField("id", this.serviceId);
            jsonObjectBuilder2.appendField("customCharts", jsonObjects);
            jsonObjectBuilder.appendField("service", jsonObjectBuilder2.build());
            jsonObjectBuilder.appendField("serverUUID", this.serverUuid);
            jsonObjectBuilder.appendField("metricsVersion", "3.0.2");
            MetricsLite.JsonObjectBuilder.JsonObject jsonObject = jsonObjectBuilder.build();
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

        private void sendData(MetricsLite.JsonObjectBuilder.JsonObject jsonObject)
                throws Exception {
            if (this.logSentData) {
                this.infoLogger.accept("Sent bStats metrics data: " + jsonObject.toString());
            }
            String text = String.format("https://bStats.org/api/v2/data/%s", this.platform);
            HttpsURLConnection httpsURLConnection =
                    (HttpsURLConnection) new URL(text).openConnection();
            byte[] values = compress(jsonObject.toString());
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.addRequestProperty("Accept", "application/json");
            httpsURLConnection.addRequestProperty("Connection", "close");
            httpsURLConnection.addRequestProperty("Content-Encoding", "gzip");
            httpsURLConnection.addRequestProperty("Content-Length", String.valueOf(values.length));
            httpsURLConnection.setRequestProperty("Content-Type", "application/json");
            httpsURLConnection.setRequestProperty("User-Agent", "Metrics-Service/1");
            httpsURLConnection.setDoOutput(true);
            try (DataOutputStream dataOutputStream =
                    new DataOutputStream(httpsURLConnection.getOutputStream())) {
                dataOutputStream.write(values);
            }
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            String text2;
            try {
                while ((text2 = bufferedReader.readLine()) != null) {
                    stringBuilder.append(text2);
                }
            } catch (Throwable throwable) {
                try {
                    bufferedReader.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            bufferedReader.close();
            if (this.logResponseStatusText) {
                this.infoLogger.accept(
                        "Sent data to bStats and received response: " + stringBuilder);
            }
        }

        private void checkRelocation() {
            if (System.getProperty("bstats.relocatecheck") == null
                    || !System.getProperty("bstats.relocatecheck").equals("false")) {
                String text =
                        new String(new byte[] {111, 114, 103, 46, 98, 115, 116, 97, 116, 115});
                String text2 =
                        new String(
                                new byte[] {
                                    121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101
                                });
                if (MetricsLite.MetricsBase.class.getPackage().getName().startsWith(text)
                        || MetricsLite.MetricsBase.class.getPackage().getName().startsWith(text2)) {
                    throw new IllegalStateException(
                            "bStats Metrics class has not been relocated correctly!");
                }
            }
        }
    }

    public static class MultiLineChart extends MetricsLite.CustomChart {

        private final Callable<Map<String, Integer>> callable;

        public MultiLineChart(String text, Callable<Map<String, Integer>> callable2) {
            super(text);
            this.callable = callable2;
        }

        @Override
        protected MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception {
            MetricsLite.JsonObjectBuilder jsonObjectBuilder = new MetricsLite.JsonObjectBuilder();
            Map<String, Integer> entries;
            if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
                boolean matches = true;
                for (Entry<String, Integer> entry : entries.entrySet()) {
                    if ((Integer) entry.getValue() != 0) {
                        matches = false;
                        jsonObjectBuilder.appendField(
                                (String) entry.getKey(), (Integer) entry.getValue());
                    }
                }
                return matches
                        ? null
                        : new MetricsLite.JsonObjectBuilder()
                                .appendField("values", jsonObjectBuilder.build())
                                .build();
            } else {
                return null;
            }
        }
    }

    public static class SimpleBarChart extends MetricsLite.CustomChart {

        private final Callable<Map<String, Integer>> callable;

        public SimpleBarChart(String text, Callable<Map<String, Integer>> callable2) {
            super(text);
            this.callable = callable2;
        }

        @Override
        protected MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception {
            MetricsLite.JsonObjectBuilder jsonObjectBuilder = new MetricsLite.JsonObjectBuilder();
            Map<String, Integer> entries;
            if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
                for (Entry<String, Integer> entry : entries.entrySet()) {
                    jsonObjectBuilder.appendField(
                            (String) entry.getKey(), new int[] {(Integer) entry.getValue()});
                }
                return new MetricsLite.JsonObjectBuilder()
                        .appendField("values", jsonObjectBuilder.build())
                        .build();
            } else {
                return null;
            }
        }
    }

    public static class SimplePie extends MetricsLite.CustomChart {

        private final Callable<String> callable;

        public SimplePie(String text, Callable<String> callable2) {
            super(text);
            this.callable = callable2;
        }

        @Override
        protected MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception {
            String text;
            return (text = this.callable.call()) != null && !text.isEmpty()
                    ? new MetricsLite.JsonObjectBuilder().appendField("value", text).build()
                    : null;
        }
    }

    public static class SingleLineChart extends MetricsLite.CustomChart {

        private final Callable<Integer> callable;

        public SingleLineChart(String text, Callable<Integer> callable2) {
            super(text);
            this.callable = callable2;
        }

        @Override
        protected MetricsLite.JsonObjectBuilder.JsonObject getChartData() throws Exception {
            int value;
            return (value = this.callable.call()) == 0
                    ? null
                    : new MetricsLite.JsonObjectBuilder().appendField("value", value).build();
        }
    }
}
