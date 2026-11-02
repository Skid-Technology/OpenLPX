package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetricsConfig {

    private final File file;

    private final boolean defaultEnabled;

    private String serverUUID;

    private boolean enabled;

    private boolean logErrors;

    private boolean logSentData;

    private boolean logResponseStatusText;

    private boolean didExistBefore = true;

    public MetricsConfig(File file2, boolean defaultEnabled) throws IOException {
        this.file = file2;
        this.defaultEnabled = defaultEnabled;
        this.setupConfig();
    }

    public String getServerUUID() {
        return this.serverUUID;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isLogErrorsEnabled() {
        return this.logErrors;
    }

    public boolean isLogSentDataEnabled() {
        return this.logSentData;
    }

    public boolean isLogResponseStatusTextEnabled() {
        return this.logResponseStatusText;
    }

    public boolean didExistBefore() {
        return this.didExistBefore;
    }

    private void setupConfig() throws IOException {
        if (!this.file.exists()) {
            this.didExistBefore = false;
            this.writeConfig();
        }
        this.readConfig();
        if (this.serverUUID == null) {
            this.writeConfig();
            this.readConfig();
        }
    }

    private void writeConfig() throws IOException {
        ArrayList<String> values;
        (values = new ArrayList())
                .add(
                        "# bStats (https://bStats.org) collects some basic information for plugin authors, like");
        values.add(
                "# how many people use their plugin and their total player count. It's recommended to keep");
        values.add(
                "# bStats enabled, but if you're not comfortable with this, you can turn this setting off.");
        values.add(
                "# There is no performance penalty associated with having metrics enabled, and data sent to");
        values.add("# bStats is fully anonymous.");
        values.add("enabled=" + this.defaultEnabled);
        values.add("server-uuid=" + UUID.randomUUID().toString());
        values.add("log-errors=false");
        values.add("log-sent-data=false");
        values.add("log-response-status-text=false");
        this.writeFile(this.file, values);
    }

    private void readConfig() throws IOException {
        List<String> values;
        if ((values = this.readFile(this.file)) == null) {
            throw new AssertionError("Content of newly created file is null");
        }
        Optional<String> configValue = this.getConfigValue("enabled", values);
        this.enabled = configValue.map("true"::equals).orElse(true);
        this.serverUUID = this.getConfigValue("server-uuid", values).orElse(null);
        configValue = this.getConfigValue("log-errors", values);
        this.logErrors = configValue.map("true"::equals).orElse(false);
        configValue = this.getConfigValue("log-sent-data", values);
        this.logSentData = configValue.map("true"::equals).orElse(false);
        configValue = this.getConfigValue("log-response-status-text", values);
        this.logResponseStatusText = configValue.map("true"::equals).orElse(false);
    }

    private Optional<String> getConfigValue(String text, List<String> values) {
        Stream<String> stream2 = values.stream();
        stream2 = stream2.filter(result -> result.startsWith(text + "="));
        return stream2.map(result -> result.replaceFirst(Pattern.quote(text + "="), ""))
                .findFirst();
    }

    private List<String> readFile(File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        FileReader fileReader = new FileReader(file);
        List<String> values;
        try {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                values = bufferedReader.lines().collect(Collectors.toList());
            } catch (Throwable throwable) {
                try {
                    bufferedReader.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            bufferedReader.close();
        } catch (Throwable throwable3) {
            try {
                fileReader.close();
            } catch (Throwable throwable4) {
                throwable3.addSuppressed(throwable4);
            }
            throw throwable3;
        }
        fileReader.close();
        return values;
    }

    private void writeFile(File file, List<String> values) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            try {
                for (String text : values) {
                    bufferedWriter.write(text);
                    bufferedWriter.newLine();
                }
            } catch (Throwable throwable) {
                try {
                    bufferedWriter.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            bufferedWriter.close();
        } catch (Throwable throwable3) {
            try {
                fileWriter.close();
            } catch (Throwable throwable4) {
                throwable3.addSuppressed(throwable4);
            }
            throw throwable3;
        }
        fileWriter.close();
    }
}
