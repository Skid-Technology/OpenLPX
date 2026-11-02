package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.SoundCategory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.concurrent.ThreadLocalRandom;

public class WrapperPlayServerEntitySoundEffect
        extends PacketWrapper<WrapperPlayServerEntitySoundEffect> {

    private Sound sound;

    private SoundCategory soundCategory;

    private int entityId;

    private float volume;

    private float pitch;

    private long seed;

    public WrapperPlayServerEntitySoundEffect(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntitySoundEffect(
            int value, SoundCategory soundCategory, int value2, float value3, float value4) {
        this(
                Sounds.getById(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                        value),
                soundCategory,
                value2,
                value3,
                value4);
    }

    public WrapperPlayServerEntitySoundEffect(
            Sound sound, SoundCategory soundCategory, int value, float value2, float value3) {
        this(sound, soundCategory, value, value2, value3, ThreadLocalRandom.current().nextLong());
    }

    public WrapperPlayServerEntitySoundEffect(
            Sound sound2,
            SoundCategory soundCategory2,
            int entityId,
            float volume,
            float pitch,
            long seed) {
        super(PacketType.Play.Server.ENTITY_SOUND_EFFECT);
        this.sound = sound2;
        this.soundCategory = soundCategory2;
        this.entityId = entityId;
        this.volume = volume;
        this.pitch = pitch;
        this.seed = seed;
    }

    @Override
    public void read() {
        this.sound =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)
                        ? Sound.read(this)
                        : Sounds.getById(this.serverVersion.toClientVersion(), this.readVarInt());
        this.soundCategory = SoundCategory.fromId(this.readVarInt());
        this.entityId = this.readVarInt();
        this.volume = this.readFloat();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_10)) {
            this.pitch = this.readFloat();
        } else {
            this.pitch = this.readUnsignedByte() / 63.5F;
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.seed = this.readLong();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            Sound.write(this, this.sound);
        } else {
            this.writeVarInt(this.sound.getId(this.serverVersion.toClientVersion()));
        }
        this.writeVarInt(this.soundCategory.ordinal());
        this.writeVarInt(this.entityId);
        this.writeFloat(this.volume);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_10)) {
            this.writeFloat(this.pitch);
        } else {
            this.writeByte((int) (this.pitch * 63.5F));
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.writeLong(this.seed);
        }
    }

    public void copy(WrapperPlayServerEntitySoundEffect wrapperPlayServerEntitySoundEffect) {
        this.sound = wrapperPlayServerEntitySoundEffect.sound;
        this.soundCategory = wrapperPlayServerEntitySoundEffect.soundCategory;
        this.entityId = wrapperPlayServerEntitySoundEffect.entityId;
        this.volume = wrapperPlayServerEntitySoundEffect.volume;
        this.pitch = wrapperPlayServerEntitySoundEffect.pitch;
        this.seed = wrapperPlayServerEntitySoundEffect.seed;
    }

    public Sound getSound() {
        return this.sound;
    }

    public void setSound(Sound sound2) {
        this.sound = sound2;
    }

    @Deprecated
    public int getSoundId() {
        return this.getSound().getId(this.serverVersion.toClientVersion());
    }

    @Deprecated
    public void setSoundId(int value) {
        this.setSound(Sounds.getById(this.serverVersion.toClientVersion(), value));
    }

    public SoundCategory getSoundCategory() {
        return this.soundCategory;
    }

    public void setSoundCategory(SoundCategory soundCategory2) {
        this.soundCategory = soundCategory2;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
