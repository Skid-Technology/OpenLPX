package it.ytnoos.lpx.api.player;

public interface PrinterData {

    PrinterStatus getPrinterStatus();

    boolean isUsingPrinter();

    void setPrinterMode(boolean matches);

    void setPrinterMode(PrinterStatus printerStatus);

    void updateLastPlace();

    long getLastPlace();

    int increaseBlocksPlaced();

    int getBlocksPlaced();
}
