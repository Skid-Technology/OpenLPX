package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class Particle<T extends ParticleData> {

    public static final NbtCodec<Particle<?>> CODEC =
            (new NbtMapCodec<Particle<?>>() {

                        public Particle<?> decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            ClientVersion clientVersion =
                                    packetWrapper.getServerVersion().toClientVersion();
                            ParticleType particleType;
                            ParticleType particleType2 =
                                    particleType =
                                            nBTCompound.getOrThrow(
                                                    "type", ParticleTypes.CODEC, packetWrapper);
                            ParticleData particleData =
                                    particleType.decodeData(nBTCompound, clientVersion);
                            return new Particle<>(particleType2, particleData);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                Particle<?> particle)
                                throws NbtCodecException {
                            ParticleType particleType = particle.type;
                            nBTCompound.setTag(
                                    "type", new NBTString(particleType.getName().toString()));
                            particleType.encodeData(
                                    particle.getData(),
                                    packetWrapper.getServerVersion().toClientVersion(),
                                    nBTCompound);
                        }
                    })
                    .codec();

    private ParticleType<T> type;

    private T data;

    public Particle(ParticleType<T> particleType, T t) {
        this.type = particleType;
        this.data = (T) t;
    }

    public Particle(ParticleType<T> particleType) {
        this(particleType, ParticleData.emptyData());
    }

    public static Particle<?> read(PacketWrapper<?> packetWrapper) {
        ParticleType particleType = packetWrapper.readMappedEntity(ParticleTypes::getById);
        return new Particle<>(particleType, particleType.readData(packetWrapper));
    }

    public static <T extends ParticleData> void write(
            PacketWrapper<?> packetWrapper, Particle<T> particle) {
        packetWrapper.writeMappedEntity(particle.type);
        particle.getType().writeData(packetWrapper, particle.data);
    }

    @Deprecated
    public static Particle<?> decode(NBT nBT, ClientVersion clientVersion) {
        NBTCompound nBTCompound;
        NBT nBT2;
        ParticleType particleType;
        ParticleData particleData =
                (particleType =
                                (nBT2 = (nBTCompound = (NBTCompound) nBT).getTagOrThrow("type"))
                                                instanceof NBTNumber
                                        ? ParticleTypes.getById(
                                                clientVersion, ((NBTNumber) nBT2).getAsInt())
                                        : ParticleTypes.getByName(((NBTString) nBT2).getValue()))
                        .decodeData(nBTCompound, clientVersion);
        return new Particle<>(particleType, particleData);
    }

    @Deprecated
    public static <T extends ParticleData> NBT encode(
            Particle<T> particle, ClientVersion clientVersion) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("type", new NBTString(particle.type.getName().toString()));
        particle.type.encodeData((T) particle.getData(), clientVersion, nBTCompound);
        return nBTCompound;
    }

    public ParticleType<T> getType() {
        return this.type;
    }

    public void setType(ParticleType<T> particleType) {
        this.type = particleType;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = (T) t;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof Particle)) {
            return false;
        }
        Particle particle = (Particle) result;
        return !this.type.equals(particle.type) ? false : this.data.equals(particle.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.data);
    }

    @Override
    public String toString() {
        return "Particle[" + this.type.getName() + ", " + this.data + 93;
    }
}
