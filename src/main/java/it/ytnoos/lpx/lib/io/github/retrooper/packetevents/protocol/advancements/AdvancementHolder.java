package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.advancements;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class AdvancementHolder {

    private ResourceLocation identifier;

    private Advancement advancement;

    private float x;

    private float y;

    @Obsolete
    public AdvancementHolder(ResourceLocation resourceLocation, Advancement advancement2) {
        this.identifier = resourceLocation;
        this.advancement = advancement2;
        AdvancementDisplay advancementDisplay;
        if ((advancementDisplay = advancement2.getDisplay()) != null) {
            this.x = advancementDisplay.getX();
            this.y = advancementDisplay.getY();
        }
    }

    public AdvancementHolder(
            ResourceLocation resourceLocation, Advancement advancement2, float x, float y) {
        this.identifier = resourceLocation;
        this.advancement = advancement2;
        this.x = x;
        this.y = y;
    }

    public static AdvancementHolder read(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readIdentifier();
        Advancement advancement = Advancement.read(packetWrapper);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_3)) {
            return new AdvancementHolder(resourceLocation, advancement);
        }
        float value = packetWrapper.readFloat();
        float value2 = packetWrapper.readFloat();
        AdvancementDisplay advancementDisplay;
        if ((advancementDisplay = advancement.getDisplay()) != null) {
            advancementDisplay.setX(value);
            advancementDisplay.setY(value2);
        }
        return new AdvancementHolder(resourceLocation, advancement, value, value2);
    }

    public static void write(PacketWrapper<?> packetWrapper, AdvancementHolder advancementHolder) {
        packetWrapper.writeIdentifier(advancementHolder.identifier);
        Advancement.write(packetWrapper, advancementHolder.advancement);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            packetWrapper.writeFloat(advancementHolder.x);
            packetWrapper.writeFloat(advancementHolder.y);
        }
    }

    public ResourceLocation getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(ResourceLocation resourceLocation) {
        this.identifier = resourceLocation;
    }

    public Advancement getAdvancement() {
        return this.advancement;
    }

    public void setAdvancement(Advancement advancement2) {
        this.advancement = advancement2;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
