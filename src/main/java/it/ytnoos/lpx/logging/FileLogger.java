package it.ytnoos.lpx.logging;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileLogger {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT2;

    private final LPXBukkit lpx;

    private final Plugin plugin;

    private final ExecutorService executor;

    private LocalDate localDate;

    private File file;

    private PrintWriter writer;

    private boolean active;

    public FileLogger(LPXBukkit lPXBukkit) {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        this.executor =
                Executors.newSingleThreadExecutor(
                        threadFactoryBuilder.setNameFormat("lpx-log-writer-%d").build());
        this.localDate = LocalDate.now();
        this.active = false;
        this.lpx = lPXBukkit;
        this.plugin = lPXBukkit.getPlugin();
    }

    public void load() {
        this.update();
        this.deleteOldZips();
    }

    public void writeFlag(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfos) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled15()) {
            this.executor.execute(
                    () -> {
                        this.writeFlagThread(protocolPlayer, check, flagInfos);
                    });
        }
    }

    public void writeString(Supplier supplier) {
        this.executor.execute(
                () -> {
                    this.handleSupplier(supplier);
                });
    }

    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    public void writeException(Supplier supplier, Throwable throwable) {
        this.executor.execute(
                () -> {
                    this.handleThrowable2(supplier, throwable);
                });
    }

    public void handleThrowable(Supplier supplier, Throwable throwable) {
        this.handleThrowable2(supplier, throwable);
    }

    private void writeFlagThread(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfos) {
        Supplier supplier =
                () -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    boolean matches = true;
                    int value = flagInfos.length;
                    for (int index = 0; index < value; index++) {
                        FlagInfo flagInfo = flagInfos[index];
                        if (!matches) {
                            stringBuilder.append(", ");
                        }
                        matches = false;
                        stringBuilder
                                .append(flagInfo.getName())
                                .append("=")
                                .append(flagInfo.getValue());
                    }
                    String username = protocolPlayer.getUsername();
                    String formattedName = check.getType().getCategory().getFormattedName();
                    String text = check.getType().getId().name();
                    int vl = check.getOptions().getVl();
                    int maxVL = check.getOptions().getCheckOptions().getMaxVL();
                    String text2 = String.valueOf(stringBuilder);
                    return username
                            + " "
                            + formattedName
                            + " "
                            + text
                            + " "
                            + vl
                            + "/"
                            + maxVL
                            + " ["
                            + text2
                            + "]";
                };
        this.handleSupplier(supplier);
    }

    private void handleSupplier(Supplier supplier) {
        this.setupFile();
        if (this.active && this.file != null) {
            PrintWriter printWriter = this.writer;
            String text = SIMPLE_DATE_FORMAT2.format(System.currentTimeMillis());
            String text2 = (String) supplier.get();
            printWriter.println((String) "[" + text + (String) "] " + text2);
        }
    }

    private void handleThrowable2(Supplier supplier, Throwable throwable) {
        this.setupFile();
        if (this.active && this.file != null) {
            String text = SIMPLE_DATE_FORMAT2.format(System.currentTimeMillis());
            String text2 = (String) "[" + text + "] ";
            this.writer.println(text2 + (String) supplier.get());
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            String[] texts = stringWriter.toString().split("\\R");
            int value = texts.length;
            for (int index = 0; index < value; index++) {
                String text3 = texts[index];
                this.writer.println(text2 + text3);
            }
        }
    }

    private void setupFile() {
        boolean matches = this.localDate.getDayOfYear() == LocalDate.now().getDayOfYear();
        if (this.file == null || !matches) {
            if (!matches && this.file != null) {
                if (this.writer != null) {
                    this.writer.close();
                }
                File file2 = this.file;
                this.zipFile(file2);
            }
            this.localDate = LocalDate.now();
            File dataFolder = this.plugin.getDataFolder();
            String text = SIMPLE_DATE_FORMAT.format(System.currentTimeMillis());
            File file3 = new File(dataFolder, (String) "logs/" + text + ".log");
            this.file = file3;
            this.file.getParentFile().mkdirs();
            try {
                if (!this.file.exists()) {
                    this.file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(this.file, true);
                PrintWriter printWriter = new PrintWriter(fileWriter, true);
                this.writer = printWriter;
                this.active = true;
            } catch (Exception exception) {
                this.plugin
                        .getLogger()
                        .log(Level.SEVERE, exception, () -> "Unable to create log file");
            }
        }
    }

    private void zipFile(File file) {
        try {
            File dataFolder = this.plugin.getDataFolder();
            String text = file.getName().split("\\.")[0];
            File file2 = new File(dataFolder, (String) "logs/" + text + ".zip");
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try (FileOutputStream fileOutputStream2 = fileOutputStream) {
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream2);
                try (ZipOutputStream zipOutputStream2 = zipOutputStream) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOutputStream2.putNextEntry(zipEntry);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try (FileInputStream fileInputStream2 = fileInputStream) {
                        byte[] values = new byte[1024];
                        while (true) {
                            int value = fileInputStream2.read(values);
                            if (value <= 0) {
                                break;
                            }
                            zipOutputStream2.write(values, 0, value);
                        }
                    }
                    zipOutputStream2.closeEntry();
                }
            }
        } catch (Exception exception) {
            Logger logger = this.plugin.getLogger();
            logger.log(
                    Level.SEVERE,
                    exception,
                    () -> {
                        String name = file.getName();
                        return (String) "Unable to zip file " + name;
                    });
        }
        file.delete();
    }

    public void close() {
        this.executor.shutdownNow();
        if (this.writer != null) {
            this.writer.close();
        }
    }

    private void update() {
        File file = new File(this.plugin.getDataFolder(), "logs");
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                int value = files.length;
                for (int index = 0; index < value; index++) {
                    File file2 = files[index];
                    if (file2.getName().endsWith(".log")) {
                        try {
                            if (LocalDate.parse(
                                                    file2.getName().split("\\.")[0],
                                                    DATE_TIME_FORMATTER)
                                            .getDayOfYear()
                                    != LocalDate.now().getDayOfYear()) {
                                this.zipFile(file2);
                            }
                        } catch (Exception exception) {
                        }
                    }
                }
            }
        }
    }

    private void deleteOldZips() {
        File file = new File(this.plugin.getDataFolder(), "logs");
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                int value = files.length;
                for (int index = 0; index < value; index++) {
                    File file2 = files[index];
                    if (file2.getName().endsWith(".zip")) {
                        try {
                            LocalDate localDate =
                                    LocalDate.parse(
                                            file2.getName().split("\\.")[0], DATE_TIME_FORMATTER);
                            long value2 = ChronoUnit.DAYS.between(localDate, LocalDate.now());
                            LPXBukkit lPXBukkit = this.lpx;
                            ConfigManager configManager = lPXBukkit.getConfigManager();
                            GeneralOptions options = configManager.getOptions();
                            if (value2 > options.getLimit5()) {
                                file2.delete();
                            }
                        } catch (DateTimeParseException dateTimeParseException) {
                        } catch (SecurityException securityException) {
                            Logger logger = this.plugin.getLogger();
                            logger.log(
                                    Level.SEVERE,
                                    securityException,
                                    () -> {
                                        String name = file2.getName();
                                        return (String) "Unable to delete file " + name;
                                    });
                        }
                    }
                }
            }
        }
    }

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SIMPLE_DATE_FORMAT = simpleDateFormat;
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SIMPLE_DATE_FORMAT2 = simpleDateFormat2;
    }
}
