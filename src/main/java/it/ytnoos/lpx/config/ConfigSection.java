package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ConfigSection {

    protected final LPXPlugin plugin;

    protected final String key;

    @Nullable private Messages messages;

    public ConfigSection(LPXPlugin lPXPlugin, String key, @Nullable Messages messages2) {
        this.plugin = lPXPlugin;
        this.key = key;
        this.messages = messages2;
    }

    public void setMessages(@Nullable Messages messages2) {
        this.messages = messages2;
    }

    public FileConfiguration getConfig() {
        return this.plugin.getConfig();
    }

    protected String getString(String text) {
        String string = this.getConfig().getString(this.key + "." + text);
        return this.replace(string);
    }

    protected boolean testText(String text) {
        return this.getConfig().getBoolean(this.key + "." + text);
    }

    protected boolean getBoolean(String text, boolean matches) {
        return this.getConfig().getBoolean(this.key + "." + text, matches);
    }

    protected long getLong(String text, long value) {
        return this.getConfig().getLong(this.key + "." + text, value);
    }

    protected int getInt(String text) {
        return this.getConfig().getInt(this.key + "." + text);
    }

    protected List<String> createValues(String text) {
        List<String> stringList = this.getConfig().getStringList(this.key + "." + text);
        for (int index = 0; index < stringList.size(); index++) {
            String text2 = stringList.get(index);
            String text3 = (String) text2;
            stringList.set(index, this.replace(text3));
        }
        return stringList;
    }

    private String replace(String text) {
        String text2 = text;
        if (text2 == null) {
            return this.key + "." + this.key;
        }
        if (this.messages != null) {
            Messages messages2 = this.messages;
            String text3 = text2.replace("%main-color%", messages2.getMainColor());
            messages2 = this.messages;
            text2 = text3.replace("%second-color%", messages2.getSecondColor());
            Messages messages3 = this.messages;
            if (messages3.getPrefix() != null) {
                messages2 = this.messages;
                text2 = text2.replace("%prefix%", messages2.getPrefix());
            }
        }
        String text4 = text2;
        return this.color(text4);
    }

    private String color(String text) {
        return ChatColor.translateAlternateColorCodes((char) (38), text);
    }
}
