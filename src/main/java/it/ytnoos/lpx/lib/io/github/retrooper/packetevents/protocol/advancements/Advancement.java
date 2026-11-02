package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.advancements;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class Advancement {

    @Nullable private ResourceLocation parent;

    @Nullable private AdvancementDisplay display;

    private List<String> criteria;

    private List<List<String>> requirements;

    private boolean sendsTelemetryData;

    public Advancement(
            @Nullable ResourceLocation resourceLocation,
            @Nullable AdvancementDisplay advancementDisplay,
            List<List<String>> values,
            boolean matches) {
        this(resourceLocation, advancementDisplay, Collections.emptyList(), values, matches);
    }

    @Obsolete
    public Advancement(
            @Nullable ResourceLocation resourceLocation,
            @Nullable AdvancementDisplay advancementDisplay,
            List<String> criteria,
            List<List<String>> requirements,
            boolean sendsTelemetryData) {
        this.parent = resourceLocation;
        this.display = advancementDisplay;
        this.criteria = criteria;
        this.requirements = requirements;
        this.sendsTelemetryData = sendsTelemetryData;
    }

    public static Advancement read(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readOptional(ResourceLocation::read);
        AdvancementDisplay advancementDisplay =
                packetWrapper.readOptional(AdvancementDisplay::read);
        List values =
                packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_2)
                        ? packetWrapper.readList(PacketWrapper::readString)
                        : null;
        List values2 =
                packetWrapper.readList(result -> packetWrapper.readList(PacketWrapper::readString));
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20)
                        && packetWrapper.readBoolean();
        return new Advancement(resourceLocation, advancementDisplay, values, values2, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, Advancement advancement) {
        packetWrapper.writeOptional(advancement.parent, ResourceLocation::write);
        packetWrapper.writeOptional(advancement.display, AdvancementDisplay::write);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_2)) {
            packetWrapper.writeList(advancement.criteria, PacketWrapper::writeString);
        }
        packetWrapper.writeList(
                advancement.getRequirements(),
                (result, result2) -> result.writeList(result2, PacketWrapper::writeString));
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20)) {
            packetWrapper.writeBoolean(advancement.sendsTelemetryData);
        }
    }

    @Nullable
    public ResourceLocation getParent() {
        return this.parent;
    }

    public void setParent(@Nullable ResourceLocation resourceLocation) {
        this.parent = resourceLocation;
    }

    @Nullable
    public AdvancementDisplay getDisplay() {
        return this.display;
    }

    public void setDisplay(@Nullable AdvancementDisplay advancementDisplay) {
        this.display = advancementDisplay;
    }

    public List<String> getCriteria() {
        return this.criteria;
    }

    public void setCriteria(List<String> criteria) {
        this.criteria = criteria;
    }

    public List<List<String>> getRequirements() {
        return this.requirements;
    }

    public void setRequirements(List<List<String>> requirements) {
        this.requirements = requirements;
    }

    public boolean isSendsTelemetryData() {
        return this.sendsTelemetryData;
    }

    public void setSendsTelemetryData(boolean sendsTelemetryData) {
        this.sendsTelemetryData = sendsTelemetryData;
    }
}
