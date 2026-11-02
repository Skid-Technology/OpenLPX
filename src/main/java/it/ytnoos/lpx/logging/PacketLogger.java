package it.ytnoos.lpx.logging;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.PacketLogOutput;
import it.ytnoos.lpx.config.PacketLoggerOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class PacketLogger {

    private final LPXBukkit lpx;

    private final ExecutorService executorService;

    public PacketLogger(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        ThreadFactory threadFactory =
                threadFactoryBuilder.setNameFormat("lpx-packet-logger-%d").setDaemon(true).build();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1000);
        DiscardPolicy discardPolicy = new DiscardPolicy();
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        1, 1, 0L, timeUnit, linkedBlockingQueue, threadFactory, discardPolicy);
        this.executorService = threadPoolExecutor;
    }

    public void logPacket(ReceivedPacket receivedPacket) {
        Supplier supplier =
                () -> {
                    String playerName = receivedPacket.getPlayerName();
                    String clientVersionName = receivedPacket.getClientVersionName();
                    String name = receivedPacket.getPacketType().getName();
                    String formattedElapsedTime = receivedPacket.getFormattedElapsedTime();
                    String text = String.valueOf(receivedPacket);
                    long size = receivedPacket.getSize();
                    return playerName
                            + " ["
                            + clientVersionName
                            + "] sent "
                            + name
                            + " ("
                            + formattedElapsedTime
                            + "ms) ("
                            + size
                            + " bytes) "
                            + text;
                };
        this.logMessage(supplier);
    }

    public void logMessage(Supplier supplier) {
        if (!this.executorService.isShutdown()) {
            try {
                LPXBukkit lPXBukkit = this.lpx;
                ConfigManager configManager = lPXBukkit.getConfigManager();
                PacketLoggerOptions logger = configManager.getLogger();
                PacketLogOutput packetLogOutput = logger.getPacketLogOutput();
                if (packetLogOutput == PacketLogOutput.CONSOLE
                        || packetLogOutput == PacketLogOutput.BOTH) {
                    this.executorService.execute(
                            () -> this.lpx.getPlugin().getLogger().info(supplier));
                }
                if (packetLogOutput == PacketLogOutput.FILE
                        || packetLogOutput == PacketLogOutput.BOTH) {
                    lPXBukkit = this.lpx;
                    AlertManager alertManager = lPXBukkit.getAlertManager();
                    FileLogger fileLogger = alertManager.getFileLogger();
                    fileLogger.writeString(supplier);
                }
            } catch (RejectedExecutionException rejectedExecutionException) {
            }
        }
    }

    public void shutdown() {
        this.executorService.shutdown();
        try {
            if (!this.executorService.awaitTermination(5L, TimeUnit.SECONDS)) {
                this.executorService.shutdownNow();
            }
        } catch (InterruptedException interruptedException) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
