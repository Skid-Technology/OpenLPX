package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.logging.FileLogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketExceptionHandler implements BiConsumer<String, Throwable> {

    private static final Pattern PATTERN = Pattern.compile("(?:from|to) ([^ ]+) \\(");

    private final LPXBukkit lpx;

    private final Map<String, Long> entries;

    public PacketExceptionHandler(LPXBukkit lPXBukkit) {
        ConcurrentHashMap entries2 = new ConcurrentHashMap();
        this.entries = entries2;
        this.lpx = lPXBukkit;
    }

    public void accept(String text, Throwable throwable) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        boolean enabled3 = options.isEnabled3();
        lPXBukkit = this.lpx;
        AlertManager alertManager = lPXBukkit.getAlertManager();
        FileLogger fileLogger = alertManager.getFileLogger();
        Runnable runnable =
                () -> {
                    Boolean matches = ((Boolean) enabled3);
                    this.handleAsync(text, throwable, ((boolean) matches));
                };
        fileLogger.execute(runnable);
    }

    private void handleAsync(String text, Throwable throwable, boolean matches) {
        Boolean matches2 = ((Boolean) matches);
        String text2 = createText(text, throwable, ((boolean) matches2));
        if (matches) {
            this.lpx.getLogger().warning(text2);
            LPXBukkit lPXBukkit = this.lpx;
            AlertManager alertManager = lPXBukkit.getAlertManager();
            FileLogger fileLogger = alertManager.getFileLogger();
            Supplier supplier =
                    () -> {
                        String text3 = "[PacketEvents] ";
                        String text4 = text2;
                        return text3 + text4;
                    };
            fileLogger.handleThrowable(supplier, throwable);
        } else {
            String text5 = createText(text);
            long now = System.currentTimeMillis();
            Map<String, Long> entries2 = this.entries;
            Long value = ((Long) now);
            if (testTime(entries2, text5, ((long) value))) {
                this.lpx.getLogger().warning(text2);
                LPXBukkit lPXBukkit2 = this.lpx;
                AlertManager alertManager2 = lPXBukkit2.getAlertManager();
                FileLogger fileLogger2 = alertManager2.getFileLogger();
                Supplier supplier2 =
                        () -> {
                            String text3 = "[PacketEvents] ";
                            String text4 = text2;
                            return text3 + text4;
                        };
                fileLogger2.handleThrowable(supplier2, throwable);
            }
        }
    }

    static String createText(String text, Throwable throwable, boolean matches) {
        String text2 = createText2(text);
        String text3 = createText(text, throwable);
        if (matches) {
            return (String) "LPX blocked an invalid packet from "
                    + text2
                    + (String) " and disconnected the player for safety. Reason: "
                    + text3
                    + ". Full stacktrace saved in LPX logs.";
        } else {
            return (String) "LPX blocked an invalid packet from "
                    + text2
                    + (String)
                            ". The player was not disconnected because kick-on-exception is disabled. Reason: "
                    + text3
                    + ". Full stacktrace saved in LPX logs; throttling repeated messages for this player for 10 seconds.";
        }
    }

    static String createText(String text) {
        return createText2(text);
    }

    public static boolean testTime(Map entries, String text, long value) {
        Long value2 = (Long) entries.get(text);
        if (value2 != null && value - value2 < 10000L) {
            return false;
        }
        entries.put(text, value);
        return true;
    }

    private static String createText2(String text) {
        Matcher matcher2 = PATTERN.matcher(text);
        return matcher2.find() ? matcher2.group(1) : "an unknown player";
    }

    private static String createText(String text, Throwable throwable) {
        int value = text.lastIndexOf("): ");
        if (value != -1) {
            String text2 = text.substring(value + 3).trim();
            if (!text2.isEmpty()) {
                return text2;
            }
        }
        Throwable throwable2 = throwable;
        while (throwable2.getCause() != null) {
            throwable2 = throwable2.getCause();
        }
        String message = throwable2.getMessage();
        return message != null && !message.trim().isEmpty()
                ? message
                : throwable2.getClass().getSimpleName();
    }
}
