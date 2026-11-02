package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public abstract class ConsumeEffect<T extends ConsumeEffect<?>> {

    protected final ConsumeEffectType<T> type;

    public ConsumeEffect(ConsumeEffectType<T> consumeEffectType) {
        this.type = consumeEffectType;
    }

    public static ConsumeEffect<?> readFull(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(ConsumeEffectTypes.getRegistry()).read(packetWrapper);
    }

    public static <T extends ConsumeEffect<?>> void writeFull(
            PacketWrapper<?> packetWrapper, ConsumeEffect<T> consumeEffect) {
        packetWrapper.writeMappedEntity(consumeEffect.getType());
        consumeEffect.getType().write(packetWrapper, (T) consumeEffect);
    }

    public ConsumeEffectType<T> getType() {
        return this.type;
    }
}
