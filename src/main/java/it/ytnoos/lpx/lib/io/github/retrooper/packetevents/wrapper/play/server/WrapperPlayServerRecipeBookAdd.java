package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.RecipeDisplayEntry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class WrapperPlayServerRecipeBookAdd extends PacketWrapper<WrapperPlayServerRecipeBookAdd> {

    private List<WrapperPlayServerRecipeBookAdd.AddEntry> entries;

    private boolean replace;

    public WrapperPlayServerRecipeBookAdd(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerRecipeBookAdd(
            List<WrapperPlayServerRecipeBookAdd.AddEntry> entries, boolean replace) {
        super(PacketType.Play.Server.RECIPE_BOOK_ADD);
        this.entries = entries;
        this.replace = replace;
    }

    @Override
    public void read() {
        this.entries = this.readList(WrapperPlayServerRecipeBookAdd.AddEntry::read);
        this.replace = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeList(this.entries, WrapperPlayServerRecipeBookAdd.AddEntry::write);
        this.writeBoolean(this.replace);
    }

    public void copy(WrapperPlayServerRecipeBookAdd wrapperPlayServerRecipeBookAdd) {
        this.entries = wrapperPlayServerRecipeBookAdd.entries;
        this.replace = wrapperPlayServerRecipeBookAdd.replace;
    }

    public List<WrapperPlayServerRecipeBookAdd.AddEntry> getEntries() {
        return this.entries;
    }

    public void setEntries(List<WrapperPlayServerRecipeBookAdd.AddEntry> entries) {
        this.entries = entries;
    }

    public boolean isReplace() {
        return this.replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public static final class AddEntry {

        private RecipeDisplayEntry contents;

        private boolean notification;

        private boolean highlight;

        public AddEntry(RecipeDisplayEntry recipeDisplayEntry, byte value) {
            this(recipeDisplayEntry, (value & 1) != 0, (value & 2) != 0);
        }

        public AddEntry(
                RecipeDisplayEntry recipeDisplayEntry, boolean notification, boolean highlight) {
            this.contents = recipeDisplayEntry;
            this.notification = notification;
            this.highlight = highlight;
        }

        public static WrapperPlayServerRecipeBookAdd.AddEntry read(PacketWrapper<?> packetWrapper) {
            RecipeDisplayEntry recipeDisplayEntry = RecipeDisplayEntry.read(packetWrapper);
            byte value = packetWrapper.readByte();
            return new WrapperPlayServerRecipeBookAdd.AddEntry(recipeDisplayEntry, value);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, WrapperPlayServerRecipeBookAdd.AddEntry addEntry) {
            RecipeDisplayEntry.write(packetWrapper, addEntry.contents);
            packetWrapper.writeByte(addEntry.packFlags());
        }

        public RecipeDisplayEntry getContents() {
            return this.contents;
        }

        public void setContents(RecipeDisplayEntry recipeDisplayEntry) {
            this.contents = recipeDisplayEntry;
        }

        public boolean isNotification() {
            return this.notification;
        }

        public void setNotification(boolean notification) {
            this.notification = notification;
        }

        public boolean isHighlight() {
            return this.highlight;
        }

        public void setHighlight(boolean highlight) {
            this.highlight = highlight;
        }

        public byte packFlags() {
            return (byte) ((this.notification ? 1 : 0) | (this.highlight ? 2 : 0));
        }
    }
}
