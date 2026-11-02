package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WrapperPlayServerTags extends PacketWrapper<WrapperPlayServerTags> {

    private Map<ResourceLocation, List<WrapperPlayServerTags.Tag>> tags;

    public WrapperPlayServerTags(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerTags(Map<ResourceLocation, List<WrapperPlayServerTags.Tag>> entries) {
        super(PacketType.Play.Server.TAGS);
        this.tags = entries;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.tags =
                    this.readMap(
                            PacketWrapper::readIdentifier,
                            result -> result.readList(WrapperPlayServerTags.Tag::read));
        } else {
            this.tags = new HashMap<>(4);
            this.tags.put(
                    ResourceLocation.minecraft("block"),
                    this.readList(WrapperPlayServerTags.Tag::read));
            this.tags.put(
                    ResourceLocation.minecraft("item"),
                    this.readList(WrapperPlayServerTags.Tag::read));
            this.tags.put(
                    ResourceLocation.minecraft("fluid"),
                    this.readList(WrapperPlayServerTags.Tag::read));
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
                this.tags.put(
                        ResourceLocation.minecraft("entity_type"),
                        this.readList(WrapperPlayServerTags.Tag::read));
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.writeMap(
                    this.tags,
                    PacketWrapper::writeIdentifier,
                    (result, result2) ->
                            result.writeList(result2, WrapperPlayServerTags.Tag::write));
        } else {
            this.writeList(
                    this.tags.get(ResourceLocation.minecraft("block")),
                    WrapperPlayServerTags.Tag::write);
            this.writeList(
                    this.tags.get(ResourceLocation.minecraft("item")),
                    WrapperPlayServerTags.Tag::write);
            this.writeList(
                    this.tags.get(ResourceLocation.minecraft("fluid")),
                    WrapperPlayServerTags.Tag::write);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
                this.writeList(
                        this.tags.get(ResourceLocation.minecraft("entity_type")),
                        WrapperPlayServerTags.Tag::write);
            }
        }
    }

    public void copy(WrapperPlayServerTags wrapperPlayServerTags) {
        this.tags = wrapperPlayServerTags.tags;
    }

    public Map<ResourceLocation, List<WrapperPlayServerTags.Tag>> getTagMap() {
        return this.tags;
    }

    public void setTagMap(Map<ResourceLocation, List<WrapperPlayServerTags.Tag>> entries) {
        this.tags = entries;
    }

    @Deprecated
    public Map<String, List<WrapperPlayServerTags.Tag>> getTags() {
        if (this.tags == null) {
            return null;
        }
        HashMap entries = new HashMap(this.tags.size());
        for (Entry<
                        ResourceLocation,
                        List<
                                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play
                                        .server.WrapperPlayServerTags.Tag>>
                entry : this.tags.entrySet()) {
            entries.put(((ResourceLocation) entry.getKey()).toString(), (List) entry.getValue());
        }
        return Collections.unmodifiableMap(entries);
    }

    @Deprecated
    public void setTags(HashMap<String, List<WrapperPlayServerTags.Tag>> entries) {
        if (entries == null) {
            this.tags = null;
        } else {
            this.tags = new HashMap<>(entries.size());
            for (Entry<
                            String,
                            List<
                                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play
                                            .server.WrapperPlayServerTags.Tag>>
                    entry : entries.entrySet()) {
                this.tags.put(
                        new ResourceLocation((String) entry.getKey()),
                        (List<WrapperPlayServerTags.Tag>) entry.getValue());
            }
        }
    }

    public static class Tag {

        private ResourceLocation key;

        private List<Integer> values;

        public Tag(String text, List<Integer> values) {
            this(new ResourceLocation(text), values);
        }

        public Tag(ResourceLocation resourceLocation, List<Integer> values) {
            this.key = resourceLocation;
            this.values = values;
        }

        public static WrapperPlayServerTags.Tag read(PacketWrapper<?> packetWrapper) {
            ResourceLocation resourceLocation = packetWrapper.readIdentifier();
            List values = packetWrapper.readList(PacketWrapper::readVarInt);
            return new WrapperPlayServerTags.Tag(resourceLocation, values);
        }

        public static void write(PacketWrapper<?> packetWrapper, WrapperPlayServerTags.Tag tag) {
            packetWrapper.writeIdentifier(tag.key);
            packetWrapper.writeList(tag.values, PacketWrapper::writeVarInt);
        }

        public String getName() {
            return this.key.toString();
        }

        public void setName(String text) {
            this.key = new ResourceLocation(text);
        }

        public ResourceLocation getKey() {
            return this.key;
        }

        public void setKey(ResourceLocation resourceLocation) {
            this.key = resourceLocation;
        }

        public List<Integer> getValues() {
            return this.values;
        }

        public void setValues(List<Integer> values) {
            this.values = values;
        }
    }
}
