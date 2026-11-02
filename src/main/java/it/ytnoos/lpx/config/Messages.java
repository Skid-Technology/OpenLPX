package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

public class Messages extends ConfigSection {

    private final String mainColor;

    private final String secondColor;

    private final String prefix;

    private final String kick;

    private final String text;

    private final String text2;

    private final String text3;

    private final String text4;

    private final String text5;

    private final String text6;

    private final String text7;

    private final AlertsOptions alertsOptions;

    public Messages(LPXPlugin lPXPlugin) {
        super(lPXPlugin, "messages", null);
        this.mainColor = this.getString((String) "main-color");
        this.secondColor = this.getString((String) "second-color");
        this.setMessages(this);
        this.prefix = this.getString((String) "prefix");
        this.kick = this.getString((String) "kick");
        this.text = this.getString((String) "kick-alert");
        this.text2 = this.getString((String) "no-permission");
        this.text3 = this.getString((String) "invalid-arguments");
        this.text4 = this.getString((String) "injection-failure");
        this.text5 = this.getString((String) "update-found");
        this.text6 = this.getString((String) "null-address");
        this.text7 = this.getString((String) "unknown-command");
        Messages messages = this;
        AlertsOptions alertsOptions2 = new AlertsOptions(lPXPlugin, messages, (String) "messages");
        this.alertsOptions = alertsOptions2;
    }

    public String getMainColor() {
        return this.mainColor;
    }

    public String getSecondColor() {
        return this.secondColor;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getKick() {
        return this.kick;
    }

    public String getText() {
        return this.text;
    }

    public String getText2() {
        return this.text2;
    }

    public String getText3() {
        return this.text3;
    }

    public String getText4() {
        return this.text4;
    }

    public String getText5() {
        return this.text5;
    }

    public String getText6() {
        return this.text6;
    }

    public String getText7() {
        return this.text7;
    }

    public AlertsOptions getAlertsOptions() {
        return this.alertsOptions;
    }
}
