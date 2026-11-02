package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.llama.LlamaVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.llama.LlamaVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class LlamaVariantComponent {

    private LlamaVariant variant;

    public LlamaVariantComponent(LlamaVariant llamaVariant) {
        this.variant = llamaVariant;
    }

    public static LlamaVariantComponent read(PacketWrapper<?> packetWrapper) {
        LlamaVariant llamaVariant = packetWrapper.readMappedEntity(LlamaVariants.getRegistry());
        return new LlamaVariantComponent(llamaVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, LlamaVariantComponent llamaVariantComponent) {
        packetWrapper.writeMappedEntity(llamaVariantComponent.variant);
    }

    public LlamaVariant getVariant() {
        return this.variant;
    }

    public void setVariant(LlamaVariant llamaVariant) {
        this.variant = llamaVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof LlamaVariantComponent)) {
            return false;
        }
        LlamaVariantComponent llamaVariantComponent = (LlamaVariantComponent) result;
        return this.variant.equals(llamaVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
