package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffectTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class RemoveEffectsConsumeEffect extends ConsumeEffect<RemoveEffectsConsumeEffect> {

    private final MappedEntitySet<PotionType> effects;

    public RemoveEffectsConsumeEffect(MappedEntitySet<PotionType> mappedEntitySet) {
        super(ConsumeEffectTypes.REMOVE_EFFECTS);
        this.effects = mappedEntitySet;
    }

    public static RemoveEffectsConsumeEffect read(PacketWrapper<?> packetWrapper) {
        MappedEntitySet<PotionType> mappedEntitySet =
                MappedEntitySet.read(packetWrapper, PotionTypes.getRegistry());
        return new RemoveEffectsConsumeEffect(mappedEntitySet);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, RemoveEffectsConsumeEffect removeEffectsConsumeEffect) {
        MappedEntitySet.write(packetWrapper, removeEffectsConsumeEffect.effects);
    }

    public MappedEntitySet<PotionType> getEffects() {
        return this.effects;
    }
}
