package it.ytnoos.lpx.netty;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventLoopMetrics {

    private final ConcurrentLinkedQueue<BigDecimal> concurrentLinkedQueue;

    private final Set<UUID> players;

    private final RollingAverage rollingAverage;

    private final RollingAverage rollingAverage2;

    private final RollingAverage rollingAverage3;

    public EventLoopMetrics(int value) {
        ConcurrentLinkedQueue concurrentLinkedQueue2 = new ConcurrentLinkedQueue();
        this.concurrentLinkedQueue = concurrentLinkedQueue2;
        this.players = ConcurrentHashMap.newKeySet();
        int value2 = value * 10;
        RollingAverage rollingAverage4 = new RollingAverage(value2);
        this.rollingAverage = rollingAverage4;
        int value3 = value * 10;
        RollingAverage rollingAverage5 = new RollingAverage(value3);
        this.rollingAverage2 = rollingAverage5;
        int value4 = value * 10;
        RollingAverage rollingAverage6 = new RollingAverage(value4);
        this.rollingAverage3 = rollingAverage6;
    }

    public void handleTime(long value) {
        ConcurrentLinkedQueue concurrentLinkedQueue2 = this.concurrentLinkedQueue;
        BigDecimal bigDecimal = new BigDecimal(value);
        concurrentLinkedQueue2.add(bigDecimal);
    }

    public void addPlayer(Object result) {
        this.players.add((UUID) result);
    }

    public void handleValue(Object result) {
        this.players.remove(result);
    }

    public int getPlayers() {
        return this.players.size();
    }

    public ConcurrentLinkedQueue getConcurrentLinkedQueue() {
        return this.concurrentLinkedQueue;
    }

    public void handleBigDecimal(BigDecimal bigDecimal, Object result, BigDecimal bigDecimal2) {
        this.rollingAverage.add(bigDecimal);
        this.rollingAverage2.add(bigDecimal);
        this.rollingAverage3.add(bigDecimal2);
    }

    public BigDecimal createBigDecimal() {
        RollingAverage rollingAverage2 = this.rollingAverage;
        Double value = ((Double) (double) 10.0);
        return rollingAverage2.getPercentile(((double) value));
    }

    public BigDecimal createBigDecimal2() {
        RollingAverage rollingAverage2 = this.rollingAverage;
        Double value = ((Double) (double) 90.0);
        return rollingAverage2.getPercentile(((double) value));
    }

    public RollingAverage getRollingAverage() {
        return this.rollingAverage;
    }
}
