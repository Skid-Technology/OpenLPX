package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.Particle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.LegacyConvertible;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.LegacyParticleData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3f;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayServerParticle extends PacketWrapper<WrapperPlayServerParticle> {

    private Particle<?> particle;

    private boolean longDistance;

    private Vector3d position;

    private Vector3f offset;

    private Vector3f maxSpeed;

    private int particleCount;

    private boolean alwaysShow;

    private WrapperPlayServerParticle.RandomizationType randomizationType =
            WrapperPlayServerParticle.RandomizationType.DEFAULT;

    public WrapperPlayServerParticle(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerParticle(
            Particle<?> particle,
            boolean matches,
            Vector3d vector3d,
            Vector3f vector3f,
            float value,
            int value2) {
        this(particle, matches, vector3d, vector3f, value, value2, false);
    }

    public WrapperPlayServerParticle(
            Particle<?> particle2,
            boolean longDistance,
            Vector3d vector3d,
            Vector3f vector3f,
            float value,
            int particleCount,
            boolean alwaysShow) {
        super(PacketType.Play.Server.PARTICLE);
        this.particle = particle2;
        this.longDistance = longDistance;
        this.position = vector3d;
        this.offset = vector3f;
        this.maxSpeed = new Vector3f(value, value, value);
        this.particleCount = particleCount;
        this.alwaysShow = alwaysShow;
    }

    public WrapperPlayServerParticle(
            Particle<?> particle2,
            boolean longDistance,
            Vector3d vector3d,
            Vector3f vector3f,
            Vector3f vector3f2,
            int particleCount,
            boolean alwaysShow,
            WrapperPlayServerParticle.RandomizationType randomizationType2) {
        super(PacketType.Play.Server.PARTICLE);
        this.particle = particle2;
        this.longDistance = longDistance;
        this.position = vector3d;
        this.offset = vector3f;
        this.maxSpeed = vector3f2;
        this.particleCount = particleCount;
        this.alwaysShow = alwaysShow;
        this.randomizationType = randomizationType2;
    }

    @Override
    public void read() {
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5);
        if (newerThanOrEquals) {
            this.particle = Particle.read(this);
        }
        byte value = 0;
        ParticleType particleType = null;
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            String text = this.readString(64);
            particleType = ParticleTypes.getByName("minecraft:" + text);
        } else if (!newerThanOrEquals2) {
            value =
                    (byte)
                            (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                                    ? this.readVarInt()
                                    : this.readInt());
            particleType = ParticleTypes.getById(this.serverVersion.toClientVersion(), value);
        }
        this.longDistance = this.readBoolean();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
            this.alwaysShow = this.readBoolean();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15)) {
            this.position = Vector3d.read(this);
        } else {
            this.position = new Vector3d(this.readFloat(), this.readFloat(), this.readFloat());
        }
        this.offset = Vector3f.read(this);
        if (newerThanOrEquals) {
            this.maxSpeed = Vector3f.read(this);
        } else {
            float value2 = this.readFloat();
            this.maxSpeed = new Vector3f(value2, value2, value2);
        }
        this.particleCount = newerThanOrEquals ? this.readVarInt() : this.readInt();
        if (newerThanOrEquals) {
            this.randomizationType =
                    this.readEnum(WrapperPlayServerParticle.RandomizationType.values());
        } else if (newerThanOrEquals2) {
            this.particle = Particle.read(this);
        } else {
            ParticleData particleData;
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
                particleData = particleType.readData(this);
            } else {
                particleData = ParticleData.emptyData();
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
                    particleData = LegacyParticleData.read(this, value);
                }
            }
            this.particle = new Particle<>(particleType, particleData);
        }
    }

    @Override
    public void write() {
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3);
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeString(this.particle.getType().getName().getKey(), 64);
        } else if (this.serverVersion.isOlderThan(ServerVersion.V_1_20_5)) {
            int id = this.particle.getType().getId(this.serverVersion.toClientVersion());
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                this.writeVarInt(id);
            } else {
                this.writeInt(id);
            }
        }
        if (newerThanOrEquals) {
            Particle.write(this, this.particle);
        }
        this.writeBoolean(this.longDistance);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
            this.writeBoolean(this.alwaysShow);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15)) {
            Vector3d.write(this, this.position);
        } else {
            this.writeFloat((float) this.position.getX());
            this.writeFloat((float) this.position.getY());
            this.writeFloat((float) this.position.getZ());
        }
        Vector3f.write(this, this.offset);
        if (newerThanOrEquals) {
            Vector3f.write(this, this.maxSpeed);
            this.writeVarInt(this.particleCount);
            this.writeEnum(this.randomizationType);
        } else {
            this.writeFloat(this.maxSpeed.x);
            this.writeInt(this.particleCount);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                Particle.write(this, this.particle);
            } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
                ((ParticleType<ParticleData>) this.particle.getType())
                        .writeData(this, this.particle.getData());
            } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
                int id2 = this.particle.getType().getId(this.serverVersion.toClientVersion());
                LegacyParticleData legacyParticleData =
                        this.particle.getData() instanceof LegacyConvertible
                                ? ((LegacyConvertible) this.particle.getData())
                                        .toLegacy(this.serverVersion.toClientVersion())
                                : LegacyParticleData.nullValue(id2);
                LegacyParticleData.write(this, id2, legacyParticleData);
            }
        }
    }

    public void copy(WrapperPlayServerParticle wrapperPlayServerParticle) {
        this.particle = wrapperPlayServerParticle.particle;
        this.longDistance = wrapperPlayServerParticle.longDistance;
        this.position = wrapperPlayServerParticle.position;
        this.offset = wrapperPlayServerParticle.offset;
        this.maxSpeed = wrapperPlayServerParticle.maxSpeed;
        this.particleCount = wrapperPlayServerParticle.particleCount;
        this.alwaysShow = wrapperPlayServerParticle.alwaysShow;
        this.randomizationType = wrapperPlayServerParticle.randomizationType;
    }

    public Particle<?> getParticle() {
        return this.particle;
    }

    public void setParticle(Particle<?> particle2) {
        this.particle = particle2;
    }

    public boolean isLongDistance() {
        return this.longDistance;
    }

    public void setLongDistance(boolean longDistance) {
        this.longDistance = longDistance;
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public void setPosition(Vector3d vector3d) {
        this.position = vector3d;
    }

    public Vector3f getOffset() {
        return this.offset;
    }

    public void setOffset(Vector3f vector3f) {
        this.offset = vector3f;
    }

    @Obsolete
    public float getMaxSpeed() {
        return this.maxSpeed.x;
    }

    public void setMaxSpeed(float value) {
        this.maxSpeed = new Vector3f(value, value, value);
    }

    public Vector3f getMaxSpeedVec() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(Vector3f vector3f) {
        this.maxSpeed = vector3f;
    }

    public int getParticleCount() {
        return this.particleCount;
    }

    public void setParticleCount(int particleCount) {
        this.particleCount = particleCount;
    }

    public boolean isAlwaysShow() {
        return this.alwaysShow;
    }

    public void setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public WrapperPlayServerParticle.RandomizationType getRandomizationType() {
        return this.randomizationType;
    }

    public void setRandomizationType(
            WrapperPlayServerParticle.RandomizationType randomizationType2) {
        this.randomizationType = randomizationType2;
    }

    public enum RandomizationType {
        DEFAULT,
        ALTERNATIVE,
        ALTERNATIVE_WITH_SPEED
    }
}
