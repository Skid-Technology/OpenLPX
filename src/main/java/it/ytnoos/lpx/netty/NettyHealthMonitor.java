package it.ytnoos.lpx.netty;

import io.netty.channel.EventLoop;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.scheduler.BukkitTickScheduler;
import it.ytnoos.lpx.scheduler.PaperTickScheduler;
import it.ytnoos.lpx.scheduler.TickScheduler;
import it.ytnoos.lpx.utils.MinecraftVersionParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NettyHealthMonitor {

    private boolean running = false;

    private final RollingAverage rollingAverage;

    private final RollingAverage rollingAverage2;

    private final RollingAverage rollingAverage3;

    private final RollingAverage rollingAverage4;

    private final RollingAverage rollingAverage5;

    private final RollingAverage rollingAverage6;

    private final RollingAverage rollingAverage7;

    private final Map<EventLoop, Integer> entries;

    private final Map<EventLoop, EventLoopMetrics> entries2;

    private final LPXBukkit lpx;

    private final TickScheduler tickScheduler;

    private static final String TEXT = "com.destroystokyo.paper.event.server.ServerTickStartEvent";

    public NettyHealthMonitor(LPXBukkit lPXBukkit) {
        RollingAverage rollingAverage8 = new RollingAverage((short) 200);
        this.rollingAverage = rollingAverage8;
        RollingAverage rollingAverage9 = new RollingAverage((short) 1200);
        this.rollingAverage2 = rollingAverage9;
        RollingAverage rollingAverage10 = new RollingAverage((short) 6000);
        this.rollingAverage3 = rollingAverage10;
        RollingAverage rollingAverage11 = new RollingAverage((short) 200);
        this.rollingAverage4 = rollingAverage11;
        RollingAverage rollingAverage12 = new RollingAverage((short) 1200);
        this.rollingAverage5 = rollingAverage12;
        RollingAverage rollingAverage13 = new RollingAverage((short) 200);
        this.rollingAverage6 = rollingAverage13;
        RollingAverage rollingAverage14 = new RollingAverage((short) 1200);
        this.rollingAverage7 = rollingAverage14;
        ConcurrentHashMap entries3 = new ConcurrentHashMap();
        this.entries = entries3;
        ConcurrentHashMap entries4 = new ConcurrentHashMap();
        this.entries2 = entries4;
        this.lpx = lPXBukkit;
        this.tickScheduler = this.createTickScheduler();
        TickScheduler tickScheduler2 = this.tickScheduler;
        TickScheduler.TickCallback tickCallback = result -> this.update();
        tickScheduler2.handleValue(tickCallback);
    }

    public void start() {
        if (!this.running) {
            this.running = true;
            this.tickScheduler.start();
        }
    }

    public void stop() {
        if (this.running) {
            this.tickScheduler.close();
            this.running = false;
        }
    }

    public boolean isRunning() {
        return this.running;
    }

    private EventLoopMetrics getOrCreateAccumulator(Thread thread, EventLoop eventLoop) {
        this.entries.computeIfAbsent(
                eventLoop,
                result -> {
                    String name = thread.getName();
                    String result3 = name;
                    String text = result3;
                    return MinecraftVersionParser.calculateLimit(text);
                });
        return this.entries2.computeIfAbsent(
                eventLoop,
                result2 -> {
                    byte result3 = 20;
                    return new EventLoopMetrics(result3);
                });
    }

    public void handleEventLoop(long value, EventLoop eventLoop) {
        if (this.running) {
            Thread thread = Thread.currentThread();
            Thread thread2 = thread;
            thread = thread2;
            Thread thread3 = thread;
            EventLoopMetrics orCreateAccumulator = this.getOrCreateAccumulator(thread3, eventLoop);
            Long value2 = ((Long) value);
            orCreateAccumulator.handleTime(value2);
        }
    }

    public void handleEventLoop(UUID uUID, Thread thread, EventLoop eventLoop) {
        EventLoopMetrics orCreateAccumulator = this.getOrCreateAccumulator(thread, eventLoop);
        orCreateAccumulator.addPlayer(uUID);
    }

    public void handleEventLoop2(UUID uUID, Thread thread, EventLoop eventLoop) {
        EventLoopMetrics orCreateAccumulator = this.getOrCreateAccumulator(thread, eventLoop);
        orCreateAccumulator.handleValue(uUID);
    }

    public void update() {
        ArrayList<BigDecimal> values2 = new ArrayList<>();
        for (EventLoopMetrics eventLoopMetrics : this.entries2.values()) {
            ArrayList<BigDecimal> values3 = new ArrayList<>();
            while (true) {
                BigDecimal bigDecimal =
                        (BigDecimal) eventLoopMetrics.getConcurrentLinkedQueue().poll();
                if (bigDecimal == null) {
                    if (values3.isEmpty()) {
                        BigDecimal bigDecimal2 = BigDecimal.ZERO;
                        BigDecimal bigDecimal3 = BigDecimal.ZERO;
                        BigDecimal bigDecimal4 = BigDecimal.ZERO;
                        eventLoopMetrics.handleBigDecimal(bigDecimal4, bigDecimal3, bigDecimal2);
                    } else {
                        BigDecimal[] bigDecimals = values3.toArray(new BigDecimal[0]);
                        Arrays.sort(bigDecimals);
                        BigDecimal bigDecimal5 = this.createBigDecimal(bigDecimals);
                        Double value = ((Double) (double) 95.0);
                        BigDecimal bigDecimal6 = this.createBigDecimal(bigDecimals, value);
                        BigDecimal bigDecimal7 = bigDecimals[bigDecimals.length - 1];
                        eventLoopMetrics.handleBigDecimal(bigDecimal5, bigDecimal6, bigDecimal7);
                    }
                    break;
                }
                values3.add(bigDecimal);
                values2.add(bigDecimal);
            }
        }
        if (values2.isEmpty()) {
            BigDecimal bigDecimal8 = BigDecimal.ZERO;
            BigDecimal bigDecimal9 = BigDecimal.ZERO;
            BigDecimal bigDecimal10 = BigDecimal.ZERO;
            this.handleBigDecimal(bigDecimal10, bigDecimal9, bigDecimal8);
        } else {
            BigDecimal[] bigDecimals2 = values2.toArray(new BigDecimal[0]);
            Arrays.sort(bigDecimals2);
            BigDecimal bigDecimal11 = this.createBigDecimal(bigDecimals2);
            Double value2 = ((Double) (double) 95.0);
            BigDecimal bigDecimal12 = this.createBigDecimal(bigDecimals2, value2);
            BigDecimal bigDecimal13 = bigDecimals2[bigDecimals2.length - 1];
            this.handleBigDecimal(bigDecimal11, bigDecimal12, bigDecimal13);
        }
    }

    private void handleBigDecimal(
            BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3) {
        this.rollingAverage.add(bigDecimal);
        this.rollingAverage2.add(bigDecimal);
        this.rollingAverage3.add(bigDecimal);
        this.rollingAverage4.add(bigDecimal2);
        this.rollingAverage5.add(bigDecimal2);
        this.rollingAverage6.add(bigDecimal3);
        this.rollingAverage7.add(bigDecimal3);
    }

    private BigDecimal createBigDecimal(BigDecimal[] bigDecimals) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        int value = bigDecimals.length;
        for (int index = 0; index < value; index++) {
            BigDecimal bigDecimal2 = bigDecimals[index];
            bigDecimal = bigDecimal.add(bigDecimal2);
        }
        BigDecimal bigDecimal3 = new BigDecimal(bigDecimals.length);
        return bigDecimal.divide(bigDecimal3, 30, RoundingMode.HALF_UP);
    }

    private BigDecimal createBigDecimal(BigDecimal[] bigDecimals, double value) {
        int value2 = (int) Math.ceil(value / 100.0 * bigDecimals.length);
        return bigDecimals[Math.max(0, Math.min(value2 - 1, bigDecimals.length - 1))];
    }

    public BigDecimal getRollingAverage() {
        return this.rollingAverage.getMin();
    }

    public BigDecimal getRollingAverage2() {
        return this.rollingAverage2.getMin();
    }

    public BigDecimal getRollingAverage3() {
        return this.rollingAverage3.getMin();
    }

    public BigDecimal createBigDecimal() {
        RollingAverage rollingAverage2 = this.rollingAverage;
        Double value = ((Double) (double) 50.0);
        return rollingAverage2.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal2() {
        RollingAverage rollingAverage = this.rollingAverage2;
        Double value = ((Double) (double) 50.0);
        return rollingAverage.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal3() {
        RollingAverage rollingAverage = this.rollingAverage3;
        Double value = ((Double) (double) 50.0);
        return rollingAverage.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal4() {
        RollingAverage rollingAverage2 = this.rollingAverage;
        Double value = ((Double) (double) 10.0);
        return rollingAverage2.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal5() {
        RollingAverage rollingAverage2 = this.rollingAverage;
        Double value = ((Double) (double) 90.0);
        return rollingAverage2.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal6() {
        RollingAverage rollingAverage = this.rollingAverage4;
        Double value = ((Double) (double) 95.0);
        return rollingAverage.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal7() {
        RollingAverage rollingAverage = this.rollingAverage5;
        Double value = ((Double) (double) 95.0);
        return rollingAverage.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal8() {
        RollingAverage rollingAverage = this.rollingAverage3;
        Double value = ((Double) (double) 95.0);
        return rollingAverage.getPercentile(((double) value));
    }

    public BigDecimal getRollingAverage6() {
        return this.rollingAverage6.getMax();
    }

    public BigDecimal getRollingAverage7() {
        return this.rollingAverage7.getMax();
    }

    public BigDecimal getRollingAverage32() {
        return this.rollingAverage3.getMax();
    }

    public int getTickScheduler() {
        TickScheduler tickScheduler2 = this.tickScheduler;
        return tickScheduler2.calculateLimit();
    }

    public Map createEntries() {
        HashMap entries3 = new HashMap();
        for (Entry<EventLoop, Integer> entry : this.entries.entrySet()) {
            EventLoop eventLoop = (EventLoop) entry.getKey();
            Integer value = (Integer) entry.getValue();
            EventLoopMetrics eventLoopMetrics = this.entries2.get(eventLoop);
            if (eventLoopMetrics != null) {
                BigDecimal bigDecimal = eventLoopMetrics.createBigDecimal2();
                NettyHealthMonitor.Sample sample =
                        new NettyHealthMonitor.Sample(bigDecimal, eventLoopMetrics.getPlayers());
                entries3.put(value, sample);
            }
        }
        return entries3;
    }

    private TickScheduler createTickScheduler() {
        String text = TEXT;
        return testText(text)
                ? new PaperTickScheduler(this.lpx.getPlugin())
                : new BukkitTickScheduler(this.lpx.getPlugin());
    }

    private static boolean testText(String text) {
        try {
            Class.forName(text);
            return true;
        } catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    public static class Sample {

        private final BigDecimal bigDecimal;

        private final int limit;

        public Sample(BigDecimal bigDecimal2, int limit) {
            this.bigDecimal = bigDecimal2;
            this.limit = limit;
        }

        public BigDecimal getBigDecimal() {
            return this.bigDecimal;
        }

        public int getLimit() {
            return this.limit;
        }
    }
}
