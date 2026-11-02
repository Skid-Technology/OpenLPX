package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffectTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class ApplyEffectsConsumeEffect extends ConsumeEffect<ApplyEffectsConsumeEffect> {

    private final List<PotionEffect> effects;

    private final float probability;

    public ApplyEffectsConsumeEffect(List<PotionEffect> effects, float probability) {
        super(ConsumeEffectTypes.APPLY_EFFECTS);
        this.effects = effects;
        this.probability = probability;
    }

    public static ApplyEffectsConsumeEffect read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(PotionEffect::read);
        float value = packetWrapper.readFloat();
        return new ApplyEffectsConsumeEffect(values, value);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ApplyEffectsConsumeEffect applyEffectsConsumeEffect) {
        packetWrapper.writeList(applyEffectsConsumeEffect.effects, PotionEffect::write);
        packetWrapper.writeFloat(applyEffectsConsumeEffect.probability);
    }

    public List<PotionEffect> getEffects() {
        return this.effects;
    }

    public float getProbability() {
        return this.probability;
    }
}
