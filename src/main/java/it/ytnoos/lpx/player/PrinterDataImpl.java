package it.ytnoos.lpx.player;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.api.player.PrinterData;
import it.ytnoos.lpx.api.player.PrinterStatus;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.config.PrinterOptions;
import it.ytnoos.lpx.utils.TimeUtils;

public class PrinterDataImpl implements PrinterData {

    private final LPXBukkit lpx;

    private final ProtocolPlayer player;

    private PrinterStatus printerStatus = PrinterStatus.NONE;

    private long time;

    private long time2;

    private int limit;

    private static final String TEXT = "%mode%";

    private static final long TIME2 = 1000L;

    public PrinterDataImpl(LPXBukkit lPXBukkit, ProtocolPlayer protocolPlayer) {
        this.time = TimeUtils.current();
        this.time2 = TimeUtils.current();
        this.lpx = lPXBukkit;
        this.player = protocolPlayer;
    }

    public PrinterStatus getPrinterStatus() {
        return this.printerStatus;
    }

    public boolean isUsingPrinter() {
        return this.printerStatus != PrinterStatus.NONE;
    }

    public void setPrinterMode(boolean matches) {
        this.setPrinterMode(matches ? PrinterStatus.API : PrinterStatus.NONE);
    }

    public void setPrinterMode(PrinterStatus printerStatus2) {
        this.printerStatus = printerStatus2;
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        PrinterOptions printerOptions = options.getPrinterOptions();
        if (printerOptions.isEnabled()) {
            String permission = printerOptions.getPermission();
            lPXBukkit = this.lpx;
            AlertManager alertManager = lPXBukkit.getAlertManager();
            if (printerStatus2 != PrinterStatus.NONE) {
                String join = printerOptions.getJoin();
                ProtocolPlayer protocolPlayer = this.player;
                String text =
                        alertManager
                                .createText(join, protocolPlayer)
                                .replace(TEXT, printerStatus2.name());
                alertManager.sendPermissionAlert(text, permission);
            } else {
                String text2 = printerOptions.getText();
                ProtocolPlayer protocolPlayer2 = this.player;
                String text3 = alertManager.createText(text2, protocolPlayer2);
                alertManager.sendPermissionAlert(text3, permission);
            }
        }
    }

    public void updateLastPlace() {
        this.time = TimeUtils.current();
    }

    public long getLastPlace() {
        return this.time;
    }

    public int increaseBlocksPlaced() {
        long value = this.time2;
        Long value2 = ((Long) value);
        if (TimeUtils.calculateTime(value2) > TIME2) {
            this.time2 = TimeUtils.current();
            this.limit = 0;
        }
        PrinterDataImpl printerDataImpl = this;
        int value3 = printerDataImpl.limit + 1;
        printerDataImpl.limit = value3;
        return value3;
    }

    public int getBlocksPlaced() {
        return this.limit;
    }
}
