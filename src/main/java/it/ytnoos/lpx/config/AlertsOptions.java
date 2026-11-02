package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

import java.util.List;

public class AlertsOptions extends ConfigSection {

    private final String permission;

    private final String format;

    private final List<String> hover;

    private final String enabled;

    private final String disabled;

    public AlertsOptions(LPXPlugin lPXPlugin, Messages messages, String text) {
        super(lPXPlugin, text + ".alerts", messages);
        this.permission = this.getString((String) "permission");
        this.format = this.getString((String) "format");
        this.hover = this.createValues((String) "hover");
        this.enabled = this.getString((String) "enabled");
        this.disabled = this.getString((String) "disabled");
    }

    public String getPermission() {
        return this.permission;
    }

    public String getFormat() {
        return this.format;
    }

    public List<String> getHover() {
        return this.hover;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public String getDisabled() {
        return this.disabled;
    }
}
