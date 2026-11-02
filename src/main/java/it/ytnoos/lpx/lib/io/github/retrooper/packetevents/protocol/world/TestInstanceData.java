package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

public class TestInstanceData {

    @Nullable private ResourceLocation test;

    private Vector3i size;

    private StructureRotation rotation;

    private boolean ignoreEntities;

    private TestInstanceData.Status status;

    @Nullable private Component errorMessage;

    public TestInstanceData(
            @Nullable ResourceLocation resourceLocation,
            Vector3i vector3i,
            StructureRotation structureRotation,
            boolean ignoreEntities,
            TestInstanceData.Status status2,
            @Nullable Component component) {
        this.test = resourceLocation;
        this.size = vector3i;
        this.rotation = structureRotation;
        this.ignoreEntities = ignoreEntities;
        this.status = status2;
        this.errorMessage = component;
    }

    public static TestInstanceData read(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readOptional(ResourceLocation::read);
        Vector3i vector3i = Vector3i.read(packetWrapper);
        StructureRotation structureRotation = packetWrapper.readEnum(StructureRotation.class);
        boolean matches = packetWrapper.readBoolean();
        TestInstanceData.Status status = packetWrapper.readEnum(TestInstanceData.Status.class);
        Component component = packetWrapper.readOptional(PacketWrapper::readComponent);
        return new TestInstanceData(
                resourceLocation, vector3i, structureRotation, matches, status, component);
    }

    public static void write(PacketWrapper<?> packetWrapper, TestInstanceData testInstanceData) {
        packetWrapper.writeOptional(testInstanceData.test, ResourceLocation::write);
        Vector3i.write(packetWrapper, testInstanceData.size);
        packetWrapper.writeEnum(testInstanceData.rotation);
        packetWrapper.writeBoolean(testInstanceData.ignoreEntities);
        packetWrapper.writeEnum(testInstanceData.status);
        packetWrapper.writeOptional(testInstanceData.errorMessage, PacketWrapper::writeComponent);
    }

    @Nullable
    public ResourceLocation getTest() {
        return this.test;
    }

    public void setTest(@Nullable ResourceLocation resourceLocation) {
        this.test = resourceLocation;
    }

    public Vector3i getSize() {
        return this.size;
    }

    public void setSize(Vector3i vector3i) {
        this.size = vector3i;
    }

    public StructureRotation getRotation() {
        return this.rotation;
    }

    public void setRotation(StructureRotation structureRotation) {
        this.rotation = structureRotation;
    }

    public boolean isIgnoreEntities() {
        return this.ignoreEntities;
    }

    public void setIgnoreEntities(boolean ignoreEntities) {
        this.ignoreEntities = ignoreEntities;
    }

    public TestInstanceData.Status getStatus() {
        return this.status;
    }

    public void setStatus(TestInstanceData.Status status2) {
        this.status = status2;
    }

    @Nullable
    public Component getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(@Nullable Component component) {
        this.errorMessage = component;
    }

    public enum Status {
        CLEARED,
        RUNNING,
        FINISHED
    }
}
