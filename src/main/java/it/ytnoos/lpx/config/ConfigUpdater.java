package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

public class ConfigUpdater {

    static final String TEXT = "config-version";

    private static final Map<String, String> ENTRIES =
            Map.of(
                    "options.max-item-depth",
                    "checks.netty.b.options.max-item-depth",
                    "options.max-items-per-packet",
                    "checks.netty.b.options.max-items-per-packet");

    public static void update(LPXPlugin lPXPlugin) {
        File configFile = lPXPlugin.getConfigFile();
        if (configFile != null && configFile.exists()) {
            YamlConfiguration yamlConfiguration;
            try {
                label151:
                {
                    try (InputStream resource = lPXPlugin.getResource("config.yml")) {
                        if (resource != null) {
                            InputStreamReader inputStreamReader =
                                    new InputStreamReader(resource, StandardCharsets.UTF_8);
                            yamlConfiguration =
                                    YamlConfiguration.loadConfiguration(inputStreamReader);
                            break label151;
                        }
                    }
                    return;
                }
            } catch (IOException oException) {
                lPXPlugin.getLogger().warning("Failed to read default config for auto-update");
                return;
            }
            YamlConfiguration yamlConfiguration2 = YamlConfiguration.loadConfiguration(configFile);
            LinkedHashMap entries = new LinkedHashMap();
            for (Entry<String, String> entry : ENTRIES.entrySet()) {
                if (yamlConfiguration2.isSet((String) entry.getKey())) {
                    entries.put(
                            (String) entry.getKey(),
                            yamlConfiguration2.isSet((String) entry.getValue())
                                    ? null
                                    : yamlConfiguration2.getString((String) entry.getKey()));
                }
            }
            boolean list = yamlConfiguration2.isList("checks.flood.b.options.limits");
            boolean set = yamlConfiguration2.isSet("checks.netty.b.options.resolved");
            int intValue = yamlConfiguration2.getInt("config-version", 0);
            YamlConfiguration yamlConfiguration3 = yamlConfiguration;
            ConfigurationSection section = (ConfigurationSection) yamlConfiguration3;
            ConfigurationSection section2 = (ConfigurationSection) yamlConfiguration2;
            Set<String> values = createValues(section, section2);
            if (set) {
                values.removeIf(result -> result.startsWith("checks.netty.b."));
            }
            if (!values.isEmpty() || !entries.isEmpty() || list || set || intValue < 2) {
                List<String> values2 = createValues(lPXPlugin);
                if (values2 != null) {
                    ArrayList values3;
                    try {
                        ArrayList values4 =
                                new ArrayList<>(
                                        Files.readAllLines(
                                                configFile.toPath(), StandardCharsets.UTF_8));
                        values3 = values4;
                    } catch (IOException oException2) {
                        lPXPlugin.getLogger().warning("Failed to read config file for auto-update");
                        return;
                    }
                    if (list) {
                        ArrayList values5 = values3;
                        List values6 = ((List) values5);
                        handleText(values6, values2, (String) "checks.flood.b.options.limits");
                    }
                    if (set) {
                        ArrayList values7 = values3;
                        List values8 = ((List) values7);
                        handleText(values8, values2, (String) "checks.netty.b");
                    }
                    Logger logger = lPXPlugin.getLogger();
                    ArrayList values9 = values3;
                    Map entries2 = ((Map) entries);
                    List values10 = ((List) values9);
                    handleLogger(values10, values2, values, entries2, logger);
                    Logger logger2 = lPXPlugin.getLogger();
                    Integer value = ((Integer) intValue);
                    ArrayList values11 = values3;
                    int value2 = ((int) value);
                    List values12 = ((List) values11);
                    List values13 = createValues(values12, value2, logger2);
                    try {
                        Files.write(configFile.toPath(), values3, StandardCharsets.UTF_8);
                    } catch (IOException oException3) {
                        lPXPlugin.getLogger().warning("Failed to write updated config file");
                        return;
                    }
                    lPXPlugin.reload();
                    if (list) {
                        lPXPlugin
                                .getLogger()
                                .info("Migrated FloodB config to new format with default values.");
                    }
                    if (set) {
                        lPXPlugin
                                .getLogger()
                                .info(
                                        "Replaced the removed NettyB check config with the new NettyB check.");
                    }
                    if (!values.isEmpty()) {
                        Logger logger3 = lPXPlugin.getLogger();
                        String text = String.valueOf(values);
                        logger3.info((String) "Config updated: added missing keys " + text);
                    }
                    if (!entries.isEmpty()) {
                        Logger logger4 = lPXPlugin.getLogger();
                        String text2 = String.valueOf(entries.keySet());
                        logger4.info((String) "Config updated: moved keys " + text2);
                    }
                    if (!values13.isEmpty()) {
                        Logger logger5 = lPXPlugin.getLogger();
                        String text3 = String.valueOf(values13);
                        logger5.info((String) "Config updated: new default values for " + text3);
                    }
                }
            }
        }
    }

    static List createValues(List values, int value, Logger logger) {
        ArrayList values2 = new ArrayList();
        for (ConfigMigration.Version version2 : ConfigMigration.VERSIONS) {
            if (version2.version() > value) {
                for (ConfigMigration.Change change : version2.getValues()) {
                    String text = change.path();
                    List values3 = values;
                    List values4 = values3;
                    int value2 = calculateLimit(values4, text);
                    if (value2 != -1) {
                        String text2 = (String) values.get(value2);
                        String text3 = valueOf(text2);
                        if (change.getValues().contains(text3)) {
                            String text4 = change.path();
                            String text5 = change.newValue();
                            String text6 = text4;
                            values3 = values;
                            text4 = text6;
                            String text7 = text4;
                            List values5 = values3;
                            setValue(values5, text7, text5);
                            values2.add(change.path());
                            text4 = change.path();
                            String text8 = change.newValue();
                            String text9 = text4;
                            logger.info(
                                    "Updating default value of "
                                            + text9
                                            + ": "
                                            + text3
                                            + " -> "
                                            + text8);
                        }
                    }
                }
            }
        }
        String text10 = String.valueOf(2);
        setValue(values, (String) "config-version", text10);
        return values2;
    }

    private static String valueOf(String text) {
        String text2 = text.substring(text.indexOf(58) + 1);
        String text3 = text2;
        int value = text2.indexOf(" #");
        if (value != -1) {
            text3 = text3.substring(0, value);
        }
        String text4 = text3.trim();
        text3 = text4;
        if (text4.length() >= 2
                && (text3.startsWith("\"") && text3.endsWith("\"")
                        || text3.startsWith("'") && text3.endsWith("'"))) {
            text3 = text3.substring(1, text3.length() - 1);
        }
        return text3;
    }

    static void handleLogger(List values, List values2, Set values3, Map entries, Logger logger) {
        for (String text : (Iterable<String>) ((Iterable) values3)) {
            if (testText(values, values2, text)) {
                logger.info((String) "Auto updating config: " + text);
            }
        }
        for (Entry<Object, Object> entry :
                ((Iterable<Entry<Object, Object>>) ((Iterable<?>) entries.entrySet()))) {
            if (entry.getValue() != null) {
                String text2 = ENTRIES.get(entry.getKey());
                Object value = entry.getValue();
                String text3 = (String) value;
                String text4 = text2;
                text2 = text4;
                String text5 = text2;
                setValue(values, text5, text3);
            }
            String text6 = (String) entry.getKey();
            handleText(values, text6);
        }
    }

    static void handleText(List values, List values2, String text) {
        List values3 = values;
        List values4 = values3;
        int value = calculateLimit(values4, text);
        if (value != -1) {
            values3 = values2;
            List values5 = values3;
            List<String> values6 = createValues(values5, text);
            if (!values6.isEmpty()) {
                values6.remove(0);
                int value2 =
                        ((String) values.get(value)).length()
                                - ((String) values.get(value)).stripLeading().length();
                int value3 = value;
                while (value3 > 0
                        && ((String) values.get(value3 - 1)).stripLeading().startsWith("#")
                        && ((String) values.get(value3 - 1)).length()
                                        - ((String) values.get(value3 - 1)).stripLeading().length()
                                == value2) {
                    value3--;
                }
                int value4;
                for (value4 = value + 1; value4 < values.size(); value4++) {
                    String text2 = (String) values.get(value4);
                    if (!text2.trim().isEmpty()
                            && text2.length() - text2.stripLeading().length() <= value2
                            && !text2.stripLeading().startsWith("- ")) {
                        break;
                    }
                }
                while (value4 > value + 1 && ((String) values.get(value4 - 1)).trim().isEmpty()) {
                    value4--;
                }
                values.subList(value3, value4).clear();
                values.addAll(value3, values6);
            }
        }
    }

    private static int calculateLimit(List values, String text) {
        String[] texts = text.split("\\.");
        int value = (texts.length - 1) * 2;
        String text2 = " ".repeat(value) + texts[texts.length - 1] + ":";
        for (int index = 0; index < values.size(); index++) {
            String text3 = (String) values.get(index);
            String text4 = text3;
            text3 = text4;
            String text5 = text3;
            if (testText(text5, text2)) {
                int value2 = index;
                Integer value3 = ((Integer) value2);
                int value4 = ((int) value3);
                if (testTexts(values, value4, texts)) {
                    return index;
                }
            }
        }
        return -1;
    }

    private static void setValue(List values, String text, String text2) {
        int value = calculateLimit(values, text);
        if (value != -1) {
            String text3 = (String) values.get(value);
            values.set(value, text3.substring(0, text3.indexOf(58) + 1) + " " + text2);
        }
    }

    private static void handleText(List values, String text) {
        int value = calculateLimit(values, text);
        if (value != -1) {
            int value2 = value;
            while (value2 > 0 && ((String) values.get(value2 - 1)).stripLeading().startsWith("#")) {
                value2--;
            }
            int value3 = value + 1;
            boolean matches = value2 > 0 && ((String) values.get(value2 - 1)).trim().isEmpty();
            boolean matches2 = value2 > 0 && ((String) values.get(value2 - 1)).trim().endsWith(":");
            if ((matches || matches2)
                    && value3 < values.size()
                    && ((String) values.get(value3)).trim().isEmpty()) {
                value3++;
            }
            values.subList(value2, value3).clear();
        }
    }

    private static Set createValues(ConfigurationSection section, ConfigurationSection section2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        handleValues(section, section2, ((Set) linkedHashSet));
        return linkedHashSet;
    }

    private static void handleValues(
            ConfigurationSection section, ConfigurationSection section2, Set values) {
        for (String text : section.getKeys(false)) {
            String text2 =
                    section.getCurrentPath().isEmpty()
                            ? text
                            : section.getCurrentPath() + "." + text;
            if (!section2.contains(text)) {
                values.add(text2);
            } else if (section.isConfigurationSection(text)
                    && section2.isConfigurationSection(text)) {
                ConfigurationSection configurationSection = section.getConfigurationSection(text);
                ConfigurationSection configurationSection2 = section2.getConfigurationSection(text);
                ConfigurationSection section3 = configurationSection2;
                ConfigurationSection section4 = configurationSection;
                configurationSection = section4;
                configurationSection2 = section3;
                ConfigurationSection section5 = configurationSection2;
                ConfigurationSection section6 = configurationSection;
                handleValues(section6, section5, values);
            }
        }
    }

    private static List<String> createValues(LPXPlugin lPXPlugin) {
        try (InputStream resource = lPXPlugin.getResource("config.yml")) {
            if (resource == null) {
                return null;
            }
            InputStreamReader inputStreamReader =
                    new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.lines().toList();
        } catch (IOException oException) {
            return null;
        }
    }

    private static List<String> createValues(List values, String text) {
        String[] texts = text.split("\\.");
        String text2 = texts[texts.length - 1];
        int value = (texts.length - 1) * 2;
        String text3 = " ".repeat(value) + text2 + ":";
        int value2 = -1;
        for (int index = 0; index < values.size(); index++) {
            String text4 = (String) values.get(index);
            if (testText(text4, text3)) {
                int value3 = index;
                Integer value4 = ((Integer) value3);
                int value5 = ((int) value4);
                if (testTexts(values, value5, texts)) {
                    value2 = index;
                    break;
                }
            }
        }
        if (value2 == -1) {
            return List.of();
        }
        int value6;
        for (value6 = value2; value6 > 0; value6--) {
            String text5 = (String) values.get(value6 - 1);
            String text6 = text5.stripLeading();
            if (!text6.startsWith("#") && !text6.isEmpty()) {
                break;
            }
            int value7 = text5.length() - text5.stripLeading().length();
            if (!text6.isEmpty() && value7 < value) {
                break;
            }
        }
        while (value6 < value2 && ((String) values.get(value6)).trim().isEmpty()) {
            value6++;
        }
        int value8 = value2 + 1;
        while (value8 < values.size()) {
            String text7 = (String) values.get(value8);
            if (text7.trim().isEmpty()) {
                value8++;
            } else {
                if (text7.length() - text7.stripLeading().length() <= value) {
                    break;
                }
                value8++;
            }
        }
        while (value8 > value2 + 1 && ((String) values.get(value8 - 1)).trim().isEmpty()) {
            value8--;
        }
        ArrayList<String> values2 = new ArrayList<>();
        values2.add("");
        values2.addAll(values.subList(value6, value8));
        return values2;
    }

    private static boolean testText(String text, String text2) {
        return text.equals(text2) || text.startsWith(text2 + " ") || text.startsWith(text2 + "\n");
    }

    private static boolean testTexts(List values, int value, String[] texts) {
        if (texts.length <= 1) {
            return true;
        }
        for (int value2 = texts.length - 2; value2 >= 0; value2--) {
            int value3 = value2 * 2;
            String text = " ".repeat(value3) + texts[value2] + ":";
            boolean matches = false;
            for (int value4 = value - 1; value4 >= 0; value4--) {
                String text2 = (String) values.get(value4);
                if (!text2.trim().isEmpty()
                        && !text2.stripLeading().startsWith("#")
                        && text2.length() - text2.stripLeading().length() <= value3) {
                    if (testText(text2, text)) {
                        matches = true;
                    }
                    break;
                }
            }
            if (!matches) {
                return false;
            }
        }
        return true;
    }

    private static boolean testText(List values, List values2, String text) {
        List values3 = values2;
        List values4 = values3;
        List<String> values5 = createValues(values4, text);
        if (values5.isEmpty()) {
            return false;
        }
        values5.remove(0);
        String[] texts = text.split("\\.");
        int value = (texts.length - 1) * 2;
        String text2 = texts.length == 1 ? null : text.substring(0, text.lastIndexOf(46));
        values3 = values2;
        List values6 = values3;
        int value2;
        for (value2 = calculateLimit(values6, text); value2 > 0; value2--) {
            Object result = values2.get(value2 - 1);
            String text3 = (String) result;
            Integer value3 = ((Integer) value);
            if (!testLimit(text3, ((int) value3))) {
                break;
            }
        }
        boolean matches = value2 > 0 && ((String) values2.get(value2 - 1)).trim().isEmpty();
        int value4 = calculateLimit(values, values2, text, value, text2);
        int value5 = value4;
        if (value4 != -1) {
            boolean matches2 = value5 > 0 && ((String) values.get(value5 - 1)).trim().isEmpty();
            ArrayList<String> values7 = new ArrayList<>();
            if (matches && !matches2) {
                values7.add("");
            }
            values7.addAll(values5);
            Integer value6 = ((Integer) value);
            values3 = values2;
            String text4 = text;
            String text5 = text4;
            List values8 = values3;
            if (testLimit(values8, text5, ((int) value6))) {
                values7.add("");
            }
            if (!matches && matches2) {
                values.remove(value5 - 1);
                value5--;
                Integer value7 = ((Integer) value);
                values3 = values2;
                text4 = text;
                String text6 = text4;
                List values9 = values3;
                if (!testLimit(values9, text6, ((int) value7))) {
                    values7.add("");
                }
            }
            values.addAll(value5, values7);
            return true;
        } else {
            int value8;
            if (text2 == null) {
                value8 = values.size();
            } else {
                values3 = values;
                List values10 = values3;
                value8 = calculateLimit2(values10, text2);
            }
            int value9 = value8;
            int value10 = value9;
            if (value9 == -1) {
                return false;
            }
            while (value10 > 0 && ((String) values.get(value10 - 1)).trim().isEmpty()) {
                value10--;
            }
            ArrayList<String> values11 = new ArrayList<>();
            if (matches) {
                values11.add("");
            }
            values11.addAll(values5);
            values.addAll(value10, values11);
            return true;
        }
    }

    private static boolean testLimit(String text, int value) {
        return text.stripLeading().startsWith("#")
                && text.length() - text.stripLeading().length() >= value;
    }

    private static int calculateLimit(
            List values, List values2, String text, int value, String text2) {
        List values3 = values2;
        List values4 = values3;
        for (int value2 = calculateLimit(values4, text) + 1; value2 < values2.size(); value2++) {
            String text3 = (String) values2.get(value2);
            if (!text3.trim().isEmpty() && !text3.stripLeading().startsWith("#")) {
                int value3 = text3.length() - text3.stripLeading().length();
                if (value3 < value) {
                    return -1;
                }
                if (value3 <= value && !text3.stripLeading().startsWith("-")) {
                    String text4 = text3.trim().substring(0, text3.trim().indexOf(58));
                    String text5 = text2 == null ? text4 : text2 + "." + text4;
                    values3 = values;
                    List values5 = values3;
                    int value4 = calculateLimit(values5, text5);
                    int value5 = value4;
                    if (value4 != -1) {
                        while (value5 > 0) {
                            Object result = values.get(value5 - 1);
                            String text6 = (String) result;
                            Integer value6 = ((Integer) value);
                            if (!testLimit(text6, ((int) value6))) {
                                break;
                            }
                            value5--;
                        }
                        return value5;
                    }
                }
            }
        }
        return -1;
    }

    private static boolean testLimit(List values, String text, int value) {
        int value2;
        for (value2 = calculateLimit(values, text) + 1; value2 < values.size(); value2++) {
            String text2 = (String) values.get(value2);
            if (!text2.trim().isEmpty()
                    && text2.length() - text2.stripLeading().length() <= value) {
                break;
            }
        }
        while (value2 > 0) {
            Object result = values.get(value2 - 1);
            String text3 = (String) result;
            Integer value3 = ((Integer) value);
            if (!testLimit(text3, ((int) value3))) {
                break;
            }
            value2--;
        }
        return value2 > 0 && ((String) values.get(value2 - 1)).trim().isEmpty();
    }

    private static int calculateLimit2(List values, String text) {
        int value = calculateLimit(values, text);
        if (value == -1) {
            return -1;
        }
        int value2 =
                ((String) values.get(value)).length()
                        - ((String) values.get(value)).stripLeading().length();
        int value3;
        for (value3 = value + 1; value3 < values.size(); value3++) {
            String text2 = (String) values.get(value3);
            if (!text2.trim().isEmpty()
                    && text2.length() - text2.stripLeading().length() <= value2) {
                break;
            }
        }
        return value3;
    }
}
