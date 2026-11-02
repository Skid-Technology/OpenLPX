package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.Particle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.StaticSound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.WeightedList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3f;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class WrapperPlayServerExplosion extends PacketWrapper<WrapperPlayServerExplosion> {

    private Vector3d position;

    private float strength;

    private int blockCount;

    private List<Vector3i> records;

    @Nullable private Vector3d knockback;

    @Obsolete private Particle<?> smallParticle;

    private Particle<?> particle;

    @Obsolete private WrapperPlayServerExplosion.BlockInteraction blockInteraction;

    private Sound explosionSound;

    private WeightedList<WrapperPlayServerExplosion.ParticleInfo> blockParticles;

    private boolean playSound = true;

    public WrapperPlayServerExplosion(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerExplosion(
            Vector3d vector3d, float value, List<Vector3i> values, Vector3f vector3f) {
        this(
                vector3d,
                value,
                values,
                vector3f,
                new Particle<>(ParticleTypes.EXPLOSION),
                new Particle<>(ParticleTypes.EXPLOSION_EMITTER),
                WrapperPlayServerExplosion.BlockInteraction.DESTROY_BLOCKS,
                new ResourceLocation("minecraft:entity.generic.explode"),
                null);
    }

    public WrapperPlayServerExplosion(
            Vector3d vector3d,
            float value,
            List<Vector3i> values,
            Vector3f vector3f,
            Particle<?> particle,
            Particle<?> particle2,
            WrapperPlayServerExplosion.BlockInteraction blockInteraction,
            ResourceLocation resourceLocation,
            @Nullable Float value2) {
        this(
                vector3d,
                value,
                values,
                vector3f,
                particle,
                particle2,
                blockInteraction,
                new StaticSound(resourceLocation, value2));
    }

    @Deprecated
    public WrapperPlayServerExplosion(
            Vector3d vector3d,
            float value,
            List<Vector3i> values,
            Vector3f vector3f,
            Particle<?> particle,
            Particle<?> particle2,
            WrapperPlayServerExplosion.BlockInteraction blockInteraction,
            Sound sound) {
        this(
                vector3d,
                value,
                values,
                new Vector3d(vector3f.x, vector3f.y, vector3f.z),
                particle,
                particle2,
                blockInteraction,
                sound);
    }

    public WrapperPlayServerExplosion(
            Vector3d vector3d,
            float value,
            List<Vector3i> values,
            Vector3d vector3d2,
            Particle<?> particle,
            Particle<?> particle2,
            WrapperPlayServerExplosion.BlockInteraction blockInteraction,
            Sound sound) {
        this(
                vector3d,
                value,
                values,
                vector3d2,
                particle,
                particle2,
                blockInteraction,
                sound,
                new WeightedList<>());
    }

    public WrapperPlayServerExplosion(
            Vector3d vector3d,
            float strength,
            List<Vector3i> records,
            Vector3d vector3d2,
            Particle<?> particle2,
            Particle<?> particle3,
            WrapperPlayServerExplosion.BlockInteraction blockInteraction2,
            Sound sound,
            WeightedList<WrapperPlayServerExplosion.ParticleInfo> weightedList) {
        super(PacketType.Play.Server.EXPLOSION);
        this.position = vector3d;
        this.strength = strength;
        this.blockCount = records.size();
        this.records = records;
        this.knockback = vector3d2;
        this.smallParticle = particle2;
        this.particle = particle3;
        this.blockInteraction = blockInteraction2;
        this.explosionSound = sound;
        this.blockParticles = weightedList;
    }

    public WrapperPlayServerExplosion(Vector3d vector3d, @Nullable Vector3d vector3d2) {
        this(
                vector3d,
                vector3d2,
                new Particle<>(ParticleTypes.EXPLOSION_EMITTER),
                Sounds.ENTITY_GENERIC_EXPLODE);
    }

    public WrapperPlayServerExplosion(
            Vector3d vector3d, @Nullable Vector3d vector3d2, Particle<?> particle, Sound sound) {
        this(vector3d, 0.0F, 0, vector3d2, particle, sound, new WeightedList<>());
    }

    public WrapperPlayServerExplosion(
            Vector3d vector3d,
            float strength,
            int blockCount,
            @Nullable Vector3d vector3d2,
            Particle<?> particle2,
            Sound sound,
            WeightedList<WrapperPlayServerExplosion.ParticleInfo> weightedList) {
        super(PacketType.Play.Server.EXPLOSION);
        this.position = vector3d;
        this.strength = strength;
        this.blockCount = blockCount;
        this.knockback = vector3d2;
        this.particle = particle2;
        this.explosionSound = sound;
        this.blockParticles = weightedList;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            this.position = Vector3d.read(this);
        } else {
            this.position = new Vector3d(this.readFloat(), this.readFloat(), this.readFloat());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
                this.strength = this.readFloat();
                this.blockCount = this.readInt();
            }
            this.knockback = this.readOptional(Vector3d::read);
            this.particle = Particle.read(this);
            this.explosionSound = Sound.read(this);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
                this.blockParticles =
                        WeightedList.read(this, WrapperPlayServerExplosion.ParticleInfo::read);
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
                    this.playSound = this.readBoolean();
                }
            }
            this.blockInteraction = WrapperPlayServerExplosion.BlockInteraction.DESTROY_BLOCKS;
        } else {
            this.strength = this.readFloat();
            int value =
                    this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)
                            ? this.readVarInt()
                            : this.readInt();
            this.records = new ArrayList<>(value);
            Vector3i vector3i = this.toFloor(this.position);
            for (int index = 0; index < value; index++) {
                int value2 = this.readByte() + vector3i.getX();
                int value3 = this.readByte() + vector3i.getY();
                int value4 = this.readByte() + vector3i.getZ();
                this.records.add(new Vector3i(value2, value3, value4));
            }
            float value5 = this.readFloat();
            float value6 = this.readFloat();
            float value7 = this.readFloat();
            this.knockback = new Vector3d(value5, value6, value7);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
                this.blockInteraction =
                        WrapperPlayServerExplosion.BlockInteraction.values()[this.readVarInt()];
                this.smallParticle = Particle.read(this);
                this.particle = Particle.read(this);
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                    this.explosionSound = Sound.read(this);
                } else {
                    ResourceLocation resourceLocation = this.readIdentifier();
                    Float value8 = this.readOptional(PacketWrapper::readFloat);
                    this.explosionSound = new StaticSound(resourceLocation, value8);
                }
            } else {
                this.blockInteraction = WrapperPlayServerExplosion.BlockInteraction.DESTROY_BLOCKS;
                this.explosionSound = Sounds.INTENTIONALLY_EMPTY;
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            Vector3d.write(this, this.position);
        } else {
            this.writeFloat((float) this.position.getX());
            this.writeFloat((float) this.position.getY());
            this.writeFloat((float) this.position.getZ());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
                this.writeFloat(this.strength);
                this.writeInt(this.blockCount);
            }
            this.writeOptional(this.knockback, Vector3d::write);
            Particle.write(this, this.particle);
            Sound.write(this, this.explosionSound);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
                WeightedList.write(
                        this, this.blockParticles, WrapperPlayServerExplosion.ParticleInfo::write);
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
                    this.writeBoolean(this.playSound);
                }
            }
        } else {
            this.writeFloat(this.strength);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
                this.writeVarInt(this.records.size());
            } else {
                this.writeInt(this.records.size());
            }
            Vector3i vector3i = this.toFloor(this.position);
            for (Vector3i vector3i2 : this.records) {
                this.writeByte(vector3i2.x - vector3i.getX());
                this.writeByte(vector3i2.y - vector3i.getY());
                this.writeByte(vector3i2.z - vector3i.getZ());
            }
            this.writeFloat((float) this.knockback.x);
            this.writeFloat((float) this.knockback.y);
            this.writeFloat((float) this.knockback.z);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
                this.writeVarInt(this.blockInteraction.ordinal());
                Particle.write(this, this.smallParticle);
                Particle.write(this, this.particle);
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                    Sound.write(this, this.explosionSound);
                } else {
                    this.writeIdentifier(this.explosionSound.getSoundId());
                    this.writeOptional(this.explosionSound.getRange(), PacketWrapper::writeFloat);
                }
            }
        }
    }

    public void copy(WrapperPlayServerExplosion wrapperPlayServerExplosion) {
        this.position = wrapperPlayServerExplosion.position;
        this.strength = wrapperPlayServerExplosion.strength;
        this.blockCount = wrapperPlayServerExplosion.blockCount;
        this.records = wrapperPlayServerExplosion.records;
        this.knockback = wrapperPlayServerExplosion.knockback;
        this.smallParticle = wrapperPlayServerExplosion.smallParticle;
        this.particle = wrapperPlayServerExplosion.particle;
        this.blockInteraction = wrapperPlayServerExplosion.blockInteraction;
        this.explosionSound = wrapperPlayServerExplosion.explosionSound;
        this.blockParticles = wrapperPlayServerExplosion.blockParticles;
        this.playSound = wrapperPlayServerExplosion.playSound;
    }

    private Vector3i toFloor(Vector3d vector3d) {
        int value;
        int value2;
        int value3;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            value = (int) Math.floor(vector3d.x);
            value2 = (int) Math.floor(vector3d.y);
            value3 = (int) Math.floor(vector3d.z);
        } else {
            value = (int) vector3d.x;
            value2 = (int) vector3d.y;
            value3 = (int) vector3d.z;
        }
        return new Vector3i(value, value2, value3);
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public void setPosition(Vector3d vector3d) {
        this.position = vector3d;
    }

    public float getStrength() {
        return this.strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public int getBlockCount() {
        return this.serverVersion.isOlderThan(ServerVersion.V_1_21_9) && this.blockCount == 0
                ? this.records.size()
                : this.blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

    public List<Vector3i> getRecords() {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        return this.records;
    }

    public void setRecords(List<Vector3i> records) {
        this.records = records;
    }

    @Nullable
    public Vector3d getKnockback() {
        return this.knockback;
    }

    public void setKnockback(@Nullable Vector3d vector3d) {
        this.knockback = vector3d;
    }

    @Deprecated
    @Nullable
    public Vector3f getPlayerMotion() {
        return this.knockback == null
                ? null
                : new Vector3f(
                        (float) this.knockback.x,
                        (float) this.knockback.y,
                        (float) this.knockback.z);
    }

    @Deprecated
    public void setPlayerMotion(@Nullable Vector3f vector3f) {
        this.knockback = vector3f == null ? null : new Vector3d(vector3f.x, vector3f.y, vector3f.z);
    }

    @Obsolete
    public Particle<?> getSmallExplosionParticles() {
        return this.smallParticle == null
                ? new Particle<>(ParticleTypes.EXPLOSION)
                : this.smallParticle;
    }

    @Obsolete
    public void setSmallExplosionParticles(Particle<?> particle) {
        this.smallParticle = particle;
    }

    public Particle<?> getParticle() {
        return this.particle == null
                ? new Particle<>(ParticleTypes.EXPLOSION_EMITTER)
                : this.particle;
    }

    public void setParticle(Particle<?> particle2) {
        this.particle = particle2;
    }

    @Obsolete
    public Particle<?> getLargeExplosionParticles() {
        return this.getParticle();
    }

    @Obsolete
    public void setLargeExplosionParticles(Particle<?> particle) {
        this.setParticle(particle);
    }

    @Obsolete
    public WrapperPlayServerExplosion.BlockInteraction getBlockInteraction() {
        return this.blockInteraction;
    }

    @Obsolete
    public void setBlockInteraction(WrapperPlayServerExplosion.BlockInteraction blockInteraction2) {
        this.blockInteraction = blockInteraction2;
    }

    public ResourceLocation getExplosionSoundKey() {
        return this.explosionSound.getSoundId();
    }

    public void setExplosionSoundKey(ResourceLocation resourceLocation) {
        this.explosionSound = new StaticSound(resourceLocation, this.explosionSound.getRange());
    }

    @Nullable
    public Float getExplosionSoundRange() {
        return this.explosionSound.getRange();
    }

    public void setExplosionSoundRange(@Nullable Float value) {
        this.explosionSound = new StaticSound(this.explosionSound.getSoundId(), value);
    }

    public Sound getExplosionSound() {
        return this.explosionSound;
    }

    public void setExplosionSound(Sound sound) {
        this.explosionSound = sound;
    }

    public WeightedList<WrapperPlayServerExplosion.ParticleInfo> getBlockParticles() {
        return this.blockParticles;
    }

    public void setBlockParticles(
            WeightedList<WrapperPlayServerExplosion.ParticleInfo> weightedList) {
        this.blockParticles = weightedList;
    }

    public boolean isPlaySound() {
        return this.playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public enum BlockInteraction {
        KEEP_BLOCKS,
        DESTROY_BLOCKS,
        DECAY_DESTROYED_BLOCKS,
        TRIGGER_BLOCKS
    }

    public static final class ParticleInfo {

        private final Particle<?> particle;

        private final float scaling;

        private final float speed;

        public ParticleInfo(Particle<?> particle2, float scaling, float speed) {
            this.particle = particle2;
            this.scaling = scaling;
            this.speed = speed;
        }

        public static WrapperPlayServerExplosion.ParticleInfo read(PacketWrapper<?> packetWrapper) {
            Particle particle = Particle.read(packetWrapper);
            float value = packetWrapper.readFloat();
            float value2 = packetWrapper.readFloat();
            return new WrapperPlayServerExplosion.ParticleInfo(particle, value, value2);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                WrapperPlayServerExplosion.ParticleInfo particleInfo) {
            Particle.write(packetWrapper, particleInfo.particle);
            packetWrapper.writeFloat(particleInfo.scaling);
            packetWrapper.writeFloat(particleInfo.speed);
        }

        public Particle<?> getParticle() {
            return this.particle;
        }

        public float getScaling() {
            return this.scaling;
        }

        public float getSpeed() {
            return this.speed;
        }
    }
}
