package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffectTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class ClearAllEffectsConsumeEffect extends ConsumeEffect<ClearAllEffectsConsumeEffect> {

    public static final ClearAllEffectsConsumeEffect INSTANCE = new ClearAllEffectsConsumeEffect();

    private ClearAllEffectsConsumeEffect() {
        super(ConsumeEffectTypes.CLEAR_ALL_EFFECTS);
    }

    public static ClearAllEffectsConsumeEffect read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            ClearAllEffectsConsumeEffect clearAllEffectsConsumeEffect) {}
}
