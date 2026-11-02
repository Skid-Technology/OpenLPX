package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.instrument.Instrument;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.instrument.Instruments;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MaybeMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Objects;

public class ItemInstrument {

    private MaybeMappedEntity<Instrument> instrument;

    public ItemInstrument(Instrument instrument) {
        this(new MaybeMappedEntity<>(instrument));
    }

    @Obsolete
    public ItemInstrument(MaybeMappedEntity<Instrument> maybeMappedEntity) {
        this.instrument = maybeMappedEntity;
    }

    public static ItemInstrument read(PacketWrapper<?> packetWrapper) {
        return !packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)
                        && !packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)
                ? new ItemInstrument(
                        MaybeMappedEntity.read(
                                packetWrapper, Instruments.getRegistry(), Instrument::read))
                : new ItemInstrument(Instrument.read(packetWrapper));
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemInstrument itemInstrument) {
        if (!packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)
                && !packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            MaybeMappedEntity.write(packetWrapper, itemInstrument.instrument, Instrument::write);
        } else {
            Instrument.write(packetWrapper, itemInstrument.instrument.getValueOrThrow());
        }
    }

    public MaybeMappedEntity<Instrument> getInstrument() {
        return this.instrument;
    }

    public void setInstrument(MaybeMappedEntity<Instrument> maybeMappedEntity) {
        this.instrument = maybeMappedEntity;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemInstrument)) {
            return false;
        }
        ItemInstrument itemInstrument = (ItemInstrument) result;
        return this.instrument.equals(itemInstrument.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.instrument);
    }
}
