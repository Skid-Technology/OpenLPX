package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class WrapperPlayClientSetGameRule extends PacketWrapper<WrapperPlayClientSetGameRule> {

    @MonotonicNonNull private List<WrapperPlayClientSetGameRule.Entry> entries;

    public WrapperPlayClientSetGameRule(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSetGameRule(List<WrapperPlayClientSetGameRule.Entry> entries) {
        super(PacketType.Play.Client.SET_GAME_RULE);
        this.entries = entries;
    }

    @Override
    public void read() {
        this.entries = this.readList(WrapperPlayClientSetGameRule.Entry::read);
    }

    @Override
    public void write() {
        this.writeList(this.entries, WrapperPlayClientSetGameRule.Entry::write);
    }

    public void copy(WrapperPlayClientSetGameRule wrapperPlayClientSetGameRule) {
        this.entries = wrapperPlayClientSetGameRule.entries;
    }

    public List<WrapperPlayClientSetGameRule.Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(List<WrapperPlayClientSetGameRule.Entry> entries) {
        this.entries = entries;
    }

    public static final class Entry {

        private final ResourceLocation gameRule;

        private final String value;

        public Entry(ResourceLocation resourceLocation, String value) {
            this.gameRule = resourceLocation;
            this.value = value;
        }

        public static WrapperPlayClientSetGameRule.Entry read(PacketWrapper<?> packetWrapper) {
            ResourceLocation resourceLocation = ResourceLocation.read(packetWrapper);
            String text = packetWrapper.readString();
            return new WrapperPlayClientSetGameRule.Entry(resourceLocation, text);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, WrapperPlayClientSetGameRule.Entry entry) {
            ResourceLocation.write(packetWrapper, entry.gameRule);
            packetWrapper.writeString(entry.value);
        }

        public ResourceLocation getGameRule() {
            return this.gameRule;
        }

        public String getValue() {
            return this.value;
        }
    }
}
