package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClocks;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@NullMarked
public class StaticTimeline extends AbstractMappedEntity implements Timeline {

    private final MappedEntityRef<WorldClock> clock;

    @Nullable private final Integer periodTicks;

    private final Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> tracks;

    private final Map<ResourceLocation, TimeMarkerInfo> timeMarkers;

    @Obsolete
    public StaticTimeline(
            @Nullable Integer value, Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> entries) {
        this(
                null,
                new MappedEntityRef.Static<>(WorldClocks.OVERWORLD),
                value,
                entries,
                Collections.emptyMap());
    }

    public StaticTimeline(
            WorldClock worldClock,
            @Nullable Integer value,
            Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> entries,
            Map<ResourceLocation, TimeMarkerInfo> entries2) {
        this(null, new MappedEntityRef.Static<>(worldClock), value, entries, entries2);
    }

    public StaticTimeline(
            MappedEntityRef<WorldClock> mappedEntityRef,
            @Nullable Integer value,
            Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> entries,
            Map<ResourceLocation, TimeMarkerInfo> entries2) {
        this(null, mappedEntityRef, value, entries, entries2);
    }

    @Internal
    public StaticTimeline(
            @Nullable TypesBuilderData typesBuilderData,
            MappedEntityRef<WorldClock> mappedEntityRef,
            @Nullable Integer periodTicks,
            Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> entries,
            Map<ResourceLocation, TimeMarkerInfo> entries2) {
        super(typesBuilderData);
        this.clock = mappedEntityRef;
        this.periodTicks = periodTicks;
        this.tracks = Collections.unmodifiableMap(entries);
        this.timeMarkers = Collections.unmodifiableMap(entries2);
    }

    public Timeline copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticTimeline(
                typesBuilderData, this.clock, this.periodTicks, this.tracks, this.timeMarkers);
    }

    @Override
    public WorldClock getClock() {
        return this.clock.get();
    }

    @Override
    public MappedEntityRef<WorldClock> getClockRef() {
        return this.clock;
    }

    @Override
    @Nullable
    public Integer getPeriodTicks() {
        return this.periodTicks;
    }

    @Override
    public Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> getTracks() {
        return this.tracks;
    }

    @Override
    public Map<ResourceLocation, TimeMarkerInfo> getTimeMarkers() {
        return this.timeMarkers;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            StaticTimeline staticTimeline = (StaticTimeline) result;
            if (!this.clock.equals(staticTimeline.clock)) {
                return false;
            } else if (!Objects.equals(this.periodTicks, staticTimeline.periodTicks)) {
                return false;
            } else {
                return !this.tracks.equals(staticTimeline.tracks)
                        ? false
                        : this.timeMarkers.equals(staticTimeline.timeMarkers);
            }
        } else {
            return false;
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.clock, this.periodTicks, this.tracks, this.timeMarkers);
    }
}
