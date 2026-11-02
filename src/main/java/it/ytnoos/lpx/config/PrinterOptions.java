package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

public class PrinterOptions extends ConfigSection {

    private final boolean automatic;

    private final int limit;

    private final int limit2;

    private final boolean enabled;

    private final String permission;

    private final String join;

    private final String text;

    public PrinterOptions(LPXPlugin lPXPlugin, String text2, Messages messages) {
        super(lPXPlugin, text2 + ".printer", messages);
        this.automatic = this.testText((String) "automatic");
        this.limit = this.getInt((String) "place-threshold");
        this.limit2 = this.getInt((String) "disable-delay");
        this.enabled = this.testText((String) "alerts");
        this.permission = this.getString((String) "permission");
        this.join = this.getString((String) "join");
        this.text = this.getString((String) "leave");
    }

    public boolean isAutomatic() {
        return this.automatic;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getLimit2() {
        return this.limit2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getJoin() {
        return this.join;
    }

    public String getText() {
        return this.text;
    }
}
