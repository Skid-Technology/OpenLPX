package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.SoundCategory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.StaticSound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class WrapperPlayServerSoundEffect extends PacketWrapper<WrapperPlayServerSoundEffect> {

    private Sound sound;

    private SoundCategory soundCategory;

    private Vector3d position;

    private float volume;

    private float pitch;

    private long seed;

    public WrapperPlayServerSoundEffect(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Deprecated
    public WrapperPlayServerSoundEffect(
            int value, SoundCategory soundCategory, Vector3i vector3i, float value2, float value3) {
        this(
                value,
                soundCategory,
                vector3i,
                value2,
                value3,
                ThreadLocalRandom.current().nextLong());
    }

    @Deprecated
    public WrapperPlayServerSoundEffect(
            int value,
            SoundCategory soundCategory,
            Vector3i vector3i,
            float value2,
            float value3,
            long value4) {
        this(
                Sounds.getById(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                        value),
                soundCategory,
                vector3i,
                value2,
                value3,
                value4);
    }

    @Deprecated
    public WrapperPlayServerSoundEffect(
            Sound sound,
            SoundCategory soundCategory,
            Vector3i vector3i,
            float value,
            float value2) {
        this(sound, soundCategory, vector3i, value, value2, ThreadLocalRandom.current().nextLong());
    }

    @Deprecated
    public WrapperPlayServerSoundEffect(
            Sound sound2,
            SoundCategory soundCategory2,
            Vector3i vector3i,
            float volume,
            float pitch,
            long seed) {
        super(PacketType.Play.Server.SOUND_EFFECT);
        this.sound = sound2;
        this.soundCategory = soundCategory2;
        this.position = new Vector3d(vector3i.x / 8.0F, vector3i.y / 8.0F, vector3i.z / 8.0F);
        this.volume = volume;
        this.pitch = pitch;
        this.seed = seed;
    }

    public WrapperPlayServerSoundEffect(
            int value, SoundCategory soundCategory, Vector3d vector3d, float value2, float value3) {
        this(
                value,
                soundCategory,
                vector3d,
                value2,
                value3,
                ThreadLocalRandom.current().nextLong());
    }

    public WrapperPlayServerSoundEffect(
            int value,
            SoundCategory soundCategory,
            Vector3d vector3d,
            float value2,
            float value3,
            long value4) {
        this(
                Sounds.getById(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                        value),
                soundCategory,
                vector3d,
                value2,
                value3,
                value4);
    }

    public WrapperPlayServerSoundEffect(
            Sound sound,
            SoundCategory soundCategory,
            Vector3d vector3d,
            float value,
            float value2) {
        this(sound, soundCategory, vector3d, value, value2, ThreadLocalRandom.current().nextLong());
    }

    public WrapperPlayServerSoundEffect(
            Sound sound2,
            SoundCategory soundCategory2,
            Vector3d vector3d,
            float volume,
            float pitch,
            long seed) {
        super(PacketType.Play.Server.SOUND_EFFECT);
        this.sound = sound2;
        this.soundCategory = soundCategory2;
        this.position = vector3d;
        this.volume = volume;
        this.pitch = pitch;
        this.seed = seed;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            this.sound = Sound.read(this);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.sound = Sounds.getById(this.serverVersion.toClientVersion(), this.readVarInt());
        } else {
            ResourceLocation resourceLocation;
            Sound byName = Sounds.getByName((resourceLocation = this.readIdentifier()).toString());
            this.sound = byName == null ? new StaticSound(resourceLocation, null) : byName;
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.soundCategory = SoundCategory.fromId(this.readVarInt());
        }
        this.position =
                new Vector3d(this.readInt() / 8.0F, this.readInt() / 8.0F, this.readInt() / 8.0F);
        this.volume = this.readFloat();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_10)) {
            this.pitch = this.readFloat();
        } else {
            this.pitch = this.readUnsignedByte() / 63.0F;
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.seed = this.readLong();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            Sound.write(this, this.sound);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeVarInt(this.sound.getId(this.serverVersion.toClientVersion()));
        } else {
            this.writeString(this.sound.getSoundId().getKey());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeVarInt(this.soundCategory.ordinal());
        }
        this.writeInt((int) (this.position.x * 8.0));
        this.writeInt((int) (this.position.y * 8.0));
        this.writeInt((int) (this.position.z * 8.0));
        this.writeFloat(this.volume);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_10)) {
            this.writeFloat(this.pitch);
        } else {
            this.writeByte((int) (this.pitch * 63.0F));
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.writeLong(this.seed);
        }
    }

    public void copy(WrapperPlayServerSoundEffect wrapperPlayServerSoundEffect) {
        this.sound = wrapperPlayServerSoundEffect.sound;
        this.soundCategory = wrapperPlayServerSoundEffect.soundCategory;
        this.position = wrapperPlayServerSoundEffect.position;
        this.volume = wrapperPlayServerSoundEffect.volume;
        this.pitch = wrapperPlayServerSoundEffect.pitch;
        this.seed = wrapperPlayServerSoundEffect.seed;
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

    @Nullable
    public SoundCategory getSoundCategory() {
        return this.soundCategory;
    }

    public void setSoundCategory(SoundCategory soundCategory2) {
        this.soundCategory = soundCategory2;
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public void setPosition(Vector3d vector3d) {
        this.position = vector3d;
    }

    @Deprecated
    public Vector3i getEffectPosition() {
        return new Vector3i(
                (int) (this.position.x * 8.0),
                (int) (this.position.y * 8.0),
                (int) (this.position.z * 8.0));
    }

    @Deprecated
    public void setEffectPosition(Vector3i vector3i) {
        this.position = new Vector3d(vector3i.x / 8.0F, vector3i.y / 8.0F, vector3i.z / 8.0F);
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
