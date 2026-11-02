package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.advancements;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

public final class AdvancementDisplay {

    public static final int FLAG_HAS_BACKGROUND = 1;

    public static final int FLAG_SHOW_TOAST = 2;

    public static final int FLAG_HIDDEN = 4;

    private Component title;

    private Component description;

    private ItemStack icon;

    private AdvancementType type;

    private boolean showToast;

    private boolean hidden;

    @Nullable private ResourceLocation background;

    private float x;

    private float y;

    public AdvancementDisplay(
            Component component,
            Component component2,
            ItemStack itemStack,
            AdvancementType advancementType,
            @Nullable ResourceLocation resourceLocation,
            boolean showToast,
            boolean hidden) {
        this.title = component;
        this.description = component2;
        this.icon = itemStack;
        this.type = advancementType;
        this.showToast = showToast;
        this.hidden = hidden;
        this.background = resourceLocation;
    }

    @Obsolete
    public AdvancementDisplay(
            Component component,
            Component component2,
            ItemStack itemStack,
            AdvancementType advancementType,
            @Nullable ResourceLocation resourceLocation,
            boolean showToast,
            boolean hidden,
            float x,
            float y) {
        this.title = component;
        this.description = component2;
        this.icon = itemStack;
        this.type = advancementType;
        this.showToast = showToast;
        this.hidden = hidden;
        this.background = resourceLocation;
        this.x = x;
        this.y = y;
    }

    public static AdvancementDisplay read(PacketWrapper<?> packetWrapper) {
        Component component = packetWrapper.readComponent();
        Component component2 = packetWrapper.readComponent();
        ItemStack itemStack = ItemStackSerialization.readTemplate(packetWrapper);
        AdvancementType advancementType = packetWrapper.readEnum(AdvancementType.class);
        int value;
        ResourceLocation resourceLocation =
                ((value = packetWrapper.readInt()) & 1) != 0
                        ? ResourceLocation.read(packetWrapper)
                        : null;
        boolean matches = (value & 2) != 0;
        boolean matches2 = (value & 4) != 0;
        float value2 = 0.0F;
        float value3 = 0.0F;
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_3)) {
            value2 = packetWrapper.readFloat();
            value3 = packetWrapper.readFloat();
        }
        return new AdvancementDisplay(
                component,
                component2,
                itemStack,
                advancementType,
                resourceLocation,
                matches,
                matches2,
                value2,
                value3);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, AdvancementDisplay advancementDisplay) {
        packetWrapper.writeComponent(advancementDisplay.title);
        packetWrapper.writeComponent(advancementDisplay.description);
        ItemStackSerialization.writeTemplate(packetWrapper, advancementDisplay.icon);
        packetWrapper.writeEnum(advancementDisplay.type);
        packetWrapper.writeInt(advancementDisplay.packFlags());
        if (advancementDisplay.background != null) {
            ResourceLocation.write(packetWrapper, advancementDisplay.background);
        }
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_3)) {
            packetWrapper.writeFloat(advancementDisplay.x);
            packetWrapper.writeFloat(advancementDisplay.y);
        }
    }

    public int packFlags() {
        byte value = 0;
        if (this.background != null) {
            value = 1;
        }
        if (this.showToast) {
            value |= 2;
        }
        if (this.hidden) {
            value |= 4;
        }
        return value;
    }

    public Component getTitle() {
        return this.title;
    }

    public void setTitle(Component component) {
        this.title = component;
    }

    public Component getDescription() {
        return this.description;
    }

    public void setDescription(Component component) {
        this.description = component;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public void setIcon(ItemStack itemStack) {
        this.icon = itemStack;
    }

    public AdvancementType getType() {
        return this.type;
    }

    public void setType(AdvancementType advancementType) {
        this.type = advancementType;
    }

    public boolean isShowToast() {
        return this.showToast;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Nullable
    public ResourceLocation getBackground() {
        return this.background;
    }

    public void setBackground(@Nullable ResourceLocation resourceLocation) {
        this.background = resourceLocation;
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
