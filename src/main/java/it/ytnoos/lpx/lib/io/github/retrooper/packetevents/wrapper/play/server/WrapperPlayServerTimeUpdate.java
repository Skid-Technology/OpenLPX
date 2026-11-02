package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.ClockNetworkState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClocks;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NullMarked
public class WrapperPlayServerTimeUpdate extends PacketWrapper<WrapperPlayServerTimeUpdate> {

    private static final ClockNetworkState FALLBACK_CLOCK = new ClockNetworkState(0L, true);

    private long gameTime;

    @MonotonicNonNull private Map<WorldClock, ClockNetworkState> clockUpdates;

    public WrapperPlayServerTimeUpdate(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Obsolete
    public WrapperPlayServerTimeUpdate(long value, long value2) {
        this(value, value2, value2 >= 0L);
    }

    @Obsolete
    public WrapperPlayServerTimeUpdate(long value, long value2, boolean matches) {
        this(value, Collections.emptyMap());
        this.clockUpdates.put(WorldClocks.OVERWORLD, new ClockNetworkState(value2, matches));
    }

    public WrapperPlayServerTimeUpdate(long gameTime, Map<WorldClock, ClockNetworkState> entries) {
        super(PacketType.Play.Server.TIME_UPDATE);
        this.gameTime = gameTime;
        this.clockUpdates = new HashMap<>(entries);
    }

    @Override
    public void read() {
        this.gameTime = this.readLong();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_1)) {
            this.clockUpdates = this.readMap(WorldClock::read, ClockNetworkState::read);
        } else {
            this.clockUpdates = new HashMap<>(1);
            this.clockUpdates.put(WorldClocks.OVERWORLD, ClockNetworkState.read(this));
        }
    }

    @Override
    public void write() {
        this.writeLong(this.gameTime);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_1)) {
            this.writeMap(this.clockUpdates, WorldClock::write, ClockNetworkState::write);
        } else {
            ClockNetworkState orDefault =
                    this.clockUpdates.getOrDefault(WorldClocks.OVERWORLD, FALLBACK_CLOCK);
            ClockNetworkState.write(this, orDefault);
        }
    }

    public void copy(WrapperPlayServerTimeUpdate wrapperPlayServerTimeUpdate) {
        this.gameTime = wrapperPlayServerTimeUpdate.gameTime;
        this.clockUpdates = new HashMap<>(wrapperPlayServerTimeUpdate.clockUpdates);
    }

    public long getWorldAge() {
        return this.gameTime;
    }

    public void setWorldAge(long gameTime) {
        this.gameTime = gameTime;
    }

    public Map<WorldClock, ClockNetworkState> getClockUpdates() {
        return this.clockUpdates;
    }

    public void setClockUpdates(Map<WorldClock, ClockNetworkState> entries) {
        this.clockUpdates = entries;
    }

    @Nullable
    public ClockNetworkState getClockState(WorldClock worldClock) {
        return this.clockUpdates.get(this.replaceRegistry(WorldClocks.getRegistry(), worldClock));
    }

    public void setClockState(WorldClock worldClock, ClockNetworkState clockNetworkState) {
        this.clockUpdates.put(
                this.replaceRegistry(WorldClocks.getRegistry(), worldClock), clockNetworkState);
    }

    @Obsolete
    public ClockNetworkState getClockState() {
        WorldClock worldClock =
                this.replaceRegistry(WorldClocks.getRegistry(), WorldClocks.OVERWORLD);
        return this.clockUpdates.computeIfAbsent(worldClock, result -> FALLBACK_CLOCK);
    }

    @Obsolete
    public void setClockState(ClockNetworkState clockNetworkState) {
        WorldClock worldClock =
                this.replaceRegistry(WorldClocks.getRegistry(), WorldClocks.OVERWORLD);
        this.clockUpdates.put(worldClock, clockNetworkState);
    }

    @Obsolete
    public long getTimeOfDay() {
        return this.getClockState().getTotalTicks();
    }

    @Obsolete
    public void setTimeOfDay(long value) {
        this.setClockState(this.getClockState().withTotalTicks(value));
    }

    @Obsolete
    public boolean isTickTime() {
        return !this.getClockState().isPaused();
    }

    @Obsolete
    public void setTickTime(boolean matches) {
        this.setClockState(this.getClockState().withPaused(!matches));
    }
}
