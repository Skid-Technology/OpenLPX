package it.ytnoos.lpx.netty;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.PacketLoggerOptions;
import it.ytnoos.lpx.config.PrinterPacketLoggerOptions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.logging.PacketLogger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class NettyWatchdog implements Listener {

    private final LPXBukkit lpx;

    private final ConfigManager config;

    private final Map<Long, NettyWatchdog.ChannelTiming> entries;

    private final AtomicBoolean running;

    private ScheduledExecutorService watchdog;

    public NettyWatchdog(LPXBukkit lPXBukkit) {
        HashMap entries2 = new HashMap();
        this.entries = entries2;
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.running = atomicBoolean;
        this.lpx = lPXBukkit;
        this.config = lPXBukkit.getConfigManager();
    }

    public synchronized void update() {
        if (this.running.get()) {
            this.shutdown();
        } else {
            this.enable();
        }
    }

    private void enable() {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        this.watchdog =
                Executors.newSingleThreadScheduledExecutor(
                        threadFactoryBuilder
                                .setNameFormat("lpx-netty-watchdog-%d")
                                .setDaemon(true)
                                .build());
        ScheduledExecutorService scheduledExecutorService = this.watchdog;
        Runnable runnable =
                () -> {
                    if (this.running.get()) {
                        long now = System.currentTimeMillis();
                        for (Entry<Long, it.ytnoos.lpx.netty.NettyWatchdog.ChannelTiming> entry :
                                this.entries.entrySet()) {
                            NettyWatchdog.ChannelTiming channelTiming =
                                    (NettyWatchdog.ChannelTiming) entry.getValue();
                            long value = now - channelTiming.time;
                            long value2 = now - channelTiming.time2;
                            ConfigManager configManager = this.config;
                            StackTraceElement[] stackTraceElements = new StackTraceElement[0];
                            PacketLoggerOptions logger = configManager.getLogger();
                            stackTraceElements = new StackTraceElement[0];
                            PrinterPacketLoggerOptions printerPacketLoggerOptions =
                                    logger.getPrinterPacketLoggerOptions();
                            stackTraceElements = new StackTraceElement[0];
                            if (value > printerPacketLoggerOptions.getTime()) {
                                configManager = this.config;
                                stackTraceElements = new StackTraceElement[0];
                                PacketLoggerOptions logger2 = configManager.getLogger();
                                stackTraceElements = new StackTraceElement[0];
                                PrinterPacketLoggerOptions printerPacketLoggerOptions2 =
                                        logger2.getPrinterPacketLoggerOptions();
                                stackTraceElements = new StackTraceElement[0];
                                if (value2 > printerPacketLoggerOptions2.getTime3()) {
                                    LPXBukkit lPXBukkit = this.lpx;
                                    PacketLogger packetLogger = lPXBukkit.getPacketLogger();
                                    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                                    long[] values = new long[] {(Long) entry.getKey()};
                                    ThreadInfo threadInfo =
                                            threadMXBean.getThreadInfo(values, true, true)[0];
                                    StackTraceElement[] stackTrace = threadInfo.getStackTrace();
                                    int value3 = stackTrace.length;
                                    for (int index = 0; index < value3; index++) {
                                        StackTraceElement stackTraceElement = stackTrace[index];
                                        if (stackTraceElement.toString().contains("loadClass")) {
                                            return;
                                        }
                                    }
                                    Supplier supplier =
                                            () -> {
                                                ConfigManager threadName = this.config;
                                                Object[] threadId = new Object[0];
                                                PacketLoggerOptions logger3 =
                                                        threadName.getLogger();
                                                threadId = new Object[0];
                                                PrinterPacketLoggerOptions
                                                        printerPacketLoggerOptions3 =
                                                                logger3
                                                                        .getPrinterPacketLoggerOptions();
                                                threadId = new Object[0];
                                                long time3 = printerPacketLoggerOptions3.getTime();
                                                long result = value;
                                                return "Netty thread has stopped responding for more than "
                                                        + time3
                                                        + "ms! (Delay: "
                                                        + result
                                                        + "ms)";
                                            };
                                    packetLogger.logMessage(supplier);
                                    Supplier supplier2 =
                                            () -> {
                                                String threadName = threadInfo.getThreadName();
                                                long threadId = threadInfo.getThreadId();
                                                boolean suspended = threadInfo.isSuspended();
                                                String text =
                                                        String.valueOf(threadInfo.getThreadState());
                                                boolean inNative = threadInfo.isInNative();
                                                boolean result = suspended;
                                                long value4 = threadId;
                                                String text2 = threadName;
                                                return "\tName: "
                                                        + text2
                                                        + " | PID: "
                                                        + value4
                                                        + " | Suspended: "
                                                        + result
                                                        + " | Native: "
                                                        + inNative
                                                        + " | State: "
                                                        + text;
                                            };
                                    packetLogger.logMessage(supplier2);
                                    Supplier supplier3 = () -> "------------------------------";
                                    packetLogger.logMessage(supplier3);
                                    Supplier supplier4 = () -> "Thread Information";
                                    packetLogger.logMessage(supplier4);
                                    stackTraceElements = stackTrace;
                                    StackTraceElement[] stackTraceElements2 = stackTraceElements;
                                    this.dumpThread(threadInfo, stackTraceElements2, packetLogger);
                                    Supplier supplier5 = () -> "------------------------------";
                                    packetLogger.logMessage(supplier5);
                                    channelTiming.time2 = now;
                                }
                            }
                        }
                    }
                };
        ConfigManager configManager2 = this.config;
        PacketLoggerOptions logger4 = configManager2.getLogger();
        PrinterPacketLoggerOptions printerPacketLoggerOptions4 =
                logger4.getPrinterPacketLoggerOptions();
        long time22 = printerPacketLoggerOptions4.getTime2();
        ConfigManager configManager3 = this.config;
        PacketLoggerOptions logger5 = configManager3.getLogger();
        PrinterPacketLoggerOptions printerPacketLoggerOptions5 =
                logger5.getPrinterPacketLoggerOptions();
        scheduledExecutorService.scheduleAtFixedRate(
                runnable, time22, printerPacketLoggerOptions5.getTime2(), TimeUnit.MILLISECONDS);
        this.lpx
                .getPlugin()
                .getServer()
                .getPluginManager()
                .registerEvents(this, this.lpx.getPlugin());
        this.running.set(true);
    }

    public synchronized void shutdown() {
        if (this.running.get()) {
            HandlerList.unregisterAll(this);
            this.watchdog.shutdown();
            try {
                if (!this.watchdog.awaitTermination(10L, TimeUnit.SECONDS)) {
                    this.watchdog.shutdownNow();
                }
            } catch (InterruptedException interruptedException) {
                this.watchdog.shutdownNow();
                Thread.currentThread().interrupt();
            } finally {
                this.running.set(false);
            }
        }
    }

    public boolean isRunning() {
        return this.running.get();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        this.handlePlayer(player);
    }

    private void handlePlayer(Player player) {
        User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
        if (user != null) {
            Object channel = user.getChannel();
            if (channel != null) {
                if (!player.hasMetadata("fake")) {
                    Channel channel2 = (Channel) channel;
                    this.watchChannel(channel2);
                }
            }
        }
    }

    public void watchChannel(Channel channel) {
        EventLoop eventLoop2 = channel.eventLoop();
        eventLoop2.submit(
                () -> {
                    String name = Thread.currentThread().getName();
                    long id = Thread.currentThread().getId();
                    this.watchdog.execute(
                            () -> {
                                Map<Long, it.ytnoos.lpx.netty.NettyWatchdog.ChannelTiming>
                                        entries2 = this.entries;
                                entries2.computeIfAbsent(
                                        ((Long) id),
                                        result -> {
                                            NettyWatchdog.ChannelTiming channelTiming =
                                                    new NettyWatchdog.ChannelTiming(name);
                                            channelTiming.time = System.currentTimeMillis();
                                            EventLoop eventLoop3 = channel.eventLoop();
                                            eventLoop3.scheduleAtFixedRate(
                                                    () ->
                                                            channelTiming.time =
                                                                    System.currentTimeMillis(),
                                                    1L,
                                                    1L,
                                                    TimeUnit.MILLISECONDS);
                                            return channelTiming;
                                        });
                            });
                });
    }

    private void dumpThread(
            ThreadInfo threadInfo,
            StackTraceElement[] stackTraceElements,
            PacketLogger packetLogger) {
        if (threadInfo.getLockedMonitors().length != 0) {
            Supplier supplier = () -> "\tThread is waiting on monitor(s):";
            packetLogger.logMessage(supplier);
            MonitorInfo[] lockedMonitors = threadInfo.getLockedMonitors();
            int value = lockedMonitors.length;
            for (int index = 0; index < value; index++) {
                MonitorInfo monitorInfo = lockedMonitors[index];
                Supplier supplier2 =
                        () -> {
                            String text = String.valueOf(monitorInfo.getLockedStackFrame());
                            String text2 = "\t\tLocked on: ";
                            String text3 = text;
                            return text2 + text3;
                        };
                packetLogger.logMessage(supplier2);
            }
        }
        if (threadInfo.getLockedSynchronizers().length != 0) {
            Supplier supplier3 = () -> "\tThread is waiting on lock(s):";
            packetLogger.logMessage(supplier3);
            LockInfo[] lockedSynchronizers = threadInfo.getLockedSynchronizers();
            int value2 = lockedSynchronizers.length;
            for (int index2 = 0; index2 < value2; index2++) {
                LockInfo lockInfo = lockedSynchronizers[index2];
                Supplier supplier4 =
                        () -> {
                            String text = lockInfo.toString();
                            String text2 = "\t\tLocked on: ";
                            String text3 = text;
                            return text2 + text3;
                        };
                packetLogger.logMessage(supplier4);
            }
        }
        Supplier supplier5 = () -> "\tStack:";
        packetLogger.logMessage(supplier5);
        int value3 = stackTraceElements.length;
        for (int index3 = 0; index3 < value3; index3++) {
            StackTraceElement stackTraceElement = stackTraceElements[index3];
            Supplier supplier6 =
                    () -> {
                        String text = String.valueOf(stackTraceElement);
                        String text2 = "\t\t";
                        String text3 = text;
                        return text2 + text3;
                    };
            packetLogger.logMessage(supplier6);
        }
    }

    public static class ChannelTiming {

        private final String name;

        private volatile long time;

        private volatile long time2;

        public ChannelTiming(String name) {
            this.name = name;
        }

        public long getTime() {
            return this.time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long getTime2() {
            return this.time2;
        }

        public void setTime2(long time2) {
            this.time2 = time2;
        }

        public String getName() {
            return this.name;
        }
    }
}
