package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClocks;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

@NullMarked
public interface Timeline extends MappedEntity, CopyableEntity<Timeline>, DeepComparableEntity {

    NbtCodec<Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>>> TRACK_CODEC =
            ((NbtCodec<Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>>>)
                    NbtMapCodec.codecOfMap(
                                    NbtCodecs.forRegistry(EnvironmentAttributes.getRegistry()),
                                    EnvironmentAttribute::isSynced,
                                    TimelineTrack::codec)
                            .codec());

    NbtCodec<Map<ResourceLocation, TimeMarkerInfo>> TIME_MARKER_CODEC =
            NbtMapCodec.codecOfMap(ResourceLocation.CODEC, TimeMarkerInfo.CODEC).codec();

    NbtCodec<Timeline> CODEC =
            (new NbtMapCodec<Timeline>() {

                        private final NbtCodec<MappedEntityRef<WorldClock>> clockCodec =
                                MappedEntityRef.codec(WorldClock.CODEC);

                        public Timeline decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            MappedEntityRef mappedEntityRef =
                                    packetWrapper
                                                    .getServerVersion()
                                                    .isOlderThan(ServerVersion.V_26_1)
                                            ? new MappedEntityRef.Static<>(WorldClocks.OVERWORLD)
                                            : nBTCompound.getOrThrow(
                                                    "clock", this.clockCodec, packetWrapper);
                            Integer orNull =
                                    nBTCompound.getOrNull(
                                            "period_ticks", NbtCodecs.INT, packetWrapper);
                            Map or =
                                    nBTCompound.getOr(
                                            "tracks",
                                            Timeline.TRACK_CODEC,
                                            Collections.emptyMap(),
                                            packetWrapper);
                            Map<
                                            ResourceLocation,
                                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents
                                                    .protocol.world.attributes.timelines
                                                    .TimeMarkerInfo>
                                    entries =
                                            packetWrapper
                                                            .getServerVersion()
                                                            .isOlderThan(ServerVersion.V_26_1)
                                                    ? Collections.emptyMap()
                                                    : nBTCompound.getOr(
                                                            "time_markers",
                                                            Timeline.TIME_MARKER_CODEC,
                                                            Collections.emptyMap(),
                                                            packetWrapper);
                            return new StaticTimeline(mappedEntityRef, orNull, or, entries);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                Timeline timeline)
                                throws NbtCodecException {
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_26_1)) {
                                nBTCompound.set(
                                        "clock",
                                        timeline.getClockRef(),
                                        this.clockCodec,
                                        packetWrapper);
                            }
                            Integer value;
                            if ((value = timeline.getPeriodTicks()) != null) {
                                nBTCompound.setTag("period_ticks", new NBTInt(value));
                            }
                            Map<
                                            EnvironmentAttribute<?>,
                                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents
                                                            .protocol.world.attributes.timelines
                                                            .TimelineTrack<
                                                    ?, ?>>
                                    entries;
                            if (!(entries = timeline.getTracks()).isEmpty()) {
                                nBTCompound.set(
                                        "tracks", entries, Timeline.TRACK_CODEC, packetWrapper);
                            }
                            Map<
                                            ResourceLocation,
                                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents
                                                    .protocol.world.attributes.timelines
                                                    .TimeMarkerInfo>
                                    entries2;
                            if (!(entries2 = timeline.getTimeMarkers()).isEmpty()) {
                                nBTCompound.set(
                                        "time_markers",
                                        entries2,
                                        Timeline.TIME_MARKER_CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    WorldClock getClock();

    MappedEntityRef<WorldClock> getClockRef();

    @Nullable Integer getPeriodTicks();

    Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> getTracks();

    Map<ResourceLocation, TimeMarkerInfo> getTimeMarkers();
}
