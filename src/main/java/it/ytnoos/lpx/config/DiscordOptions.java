package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

import java.util.List;

public class DiscordOptions extends ConfigSection {

    private final boolean enabled;

    private final String url;

    private final String color;

    private final List<String> content;

    public DiscordOptions(Messages messages, LPXPlugin lPXPlugin) {
        super(lPXPlugin, "discord", messages);
        this.enabled = this.testText((String) "enabled");
        this.url = this.getString((String) "url");
        this.color = this.getConfig().getString(this.key + ".color");
        this.content = this.createValues((String) "content");
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getUrl() {
        return this.url;
    }

    public String getColor() {
        return this.color;
    }

    public List<String> getContent() {
        return this.content;
    }
}
