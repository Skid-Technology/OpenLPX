package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.jukebox.IJukeboxSong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.jukebox.JukeboxSong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.jukebox.JukeboxSongs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MaybeMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ItemJukeboxPlayable {

    private MaybeMappedEntity<IJukeboxSong> song;

    @Obsolete private boolean showInTooltip;

    public ItemJukeboxPlayable(IJukeboxSong jukeboxSong) {
        this(new MaybeMappedEntity<>(jukeboxSong));
    }

    @Obsolete
    public ItemJukeboxPlayable(MaybeMappedEntity<IJukeboxSong> maybeMappedEntity) {
        this(maybeMappedEntity, true);
    }

    @Deprecated
    @Obsolete
    public ItemJukeboxPlayable(
            @Nullable JukeboxSong jukeboxSong,
            @Nullable ResourceLocation resourceLocation,
            boolean matches) {
        this((IJukeboxSong) jukeboxSong, resourceLocation, matches);
    }

    @Deprecated
    @Obsolete
    public ItemJukeboxPlayable(
            @Nullable IJukeboxSong jukeboxSong,
            @Nullable ResourceLocation resourceLocation,
            boolean matches) {
        this(new MaybeMappedEntity<>(jukeboxSong, resourceLocation), matches);
    }

    @Obsolete
    public ItemJukeboxPlayable(
            MaybeMappedEntity<IJukeboxSong> maybeMappedEntity, boolean showInTooltip) {
        this.song = maybeMappedEntity;
        this.showInTooltip = showInTooltip;
    }

    public static ItemJukeboxPlayable read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            return new ItemJukeboxPlayable(IJukeboxSong.read(packetWrapper));
        }
        MaybeMappedEntity maybeMappedEntity =
                MaybeMappedEntity.read(
                        packetWrapper, JukeboxSongs.getRegistry(), IJukeboxSong::read);
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        || packetWrapper.readBoolean();
        return new ItemJukeboxPlayable(maybeMappedEntity, matches);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemJukeboxPlayable itemJukeboxPlayable) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            IJukeboxSong.write(packetWrapper, itemJukeboxPlayable.song.getValueOrThrow());
        } else {
            MaybeMappedEntity.write(packetWrapper, itemJukeboxPlayable.song, IJukeboxSong::write);
        }
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemJukeboxPlayable.showInTooltip);
        }
    }

    public MaybeMappedEntity<IJukeboxSong> getSongHolder() {
        return this.song;
    }

    public void setSongHolder(MaybeMappedEntity<IJukeboxSong> maybeMappedEntity) {
        this.song = maybeMappedEntity;
    }

    @Nullable
    public IJukeboxSong getJukeboxSong() {
        return this.song.getValue();
    }

    public void setJukeboxSong(@Nullable IJukeboxSong jukeboxSong) {
        this.song = new MaybeMappedEntity<>(jukeboxSong);
    }

    public void setJukeboxSong(@Nullable JukeboxSong jukeboxSong) {
        this.setJukeboxSong((IJukeboxSong) jukeboxSong);
    }

    @Deprecated
    @Nullable
    public JukeboxSong getSong() {
        IJukeboxSong jukeboxSong;
        if ((jukeboxSong = this.getJukeboxSong()) == null) {
            return null;
        } else {
            return jukeboxSong instanceof JukeboxSong
                    ? (JukeboxSong) jukeboxSong
                    : (JukeboxSong) jukeboxSong.copy(null);
        }
    }

    @Deprecated
    public void setSong(JukeboxSong jukeboxSong) {
        this.setJukeboxSong((IJukeboxSong) jukeboxSong);
    }

    @Nullable
    public ResourceLocation getSongKey() {
        return this.song.getName();
    }

    public void setSongKey(ResourceLocation resourceLocation) {
        this.song = new MaybeMappedEntity<>(resourceLocation);
    }

    @Obsolete
    public boolean isShowInTooltip() {
        return this.showInTooltip;
    }

    @Obsolete
    public void setShowInTooltip(boolean showInTooltip) {
        this.showInTooltip = showInTooltip;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof ItemJukeboxPlayable)) {
            return false;
        } else {
            ItemJukeboxPlayable itemJukeboxPlayable = (ItemJukeboxPlayable) result;
            if (this.showInTooltip != itemJukeboxPlayable.showInTooltip) {
                return false;
            } else {
                return !Objects.equals(this.song, itemJukeboxPlayable.song)
                        ? false
                        : Objects.equals(this.song, itemJukeboxPlayable.song);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.song, this.song, this.showInTooltip);
    }
}
