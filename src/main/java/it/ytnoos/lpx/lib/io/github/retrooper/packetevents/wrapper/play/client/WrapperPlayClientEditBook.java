package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WrapperPlayClientEditBook extends PacketWrapper<WrapperPlayClientEditBook> {

    @Deprecated public static final int MAX_BYTES_PER_CHAR = 4;

    private int slot;

    @Nullable private List<String> pages;

    @Nullable private String title;

    @Nullable private ItemStack itemStack;

    @Nullable private Boolean signing;

    public WrapperPlayClientEditBook(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientEditBook(int slot, List<String> pages, @Nullable String title) {
        super(PacketType.Play.Client.EDIT_BOOK);
        this.slot = slot;
        this.pages = pages;
        this.title = title;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17_1)) {
            boolean matches;
            int value =
                    (matches = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2))
                            ? 100
                            : 200;
            int value2 = matches ? 1024 : 8192;
            this.slot = this.readVarInt();
            int value3;
            if ((value3 = this.readVarInt()) > value) {
                throw new PacketLimitException(
                        PacketLimitException.Limit.BOOK_PAGES, value3, value);
            }
            this.pages = new ArrayList<>(value3);
            for (int index = 0; index < value3; index++) {
                this.pages.add(this.readString(value2));
            }
            boolean matches2 = matches;
            this.title =
                    this.readOptional(
                            result -> {
                                int value4 = matches2 ? 32 : 128;
                                return result.readString(value4);
                            });
        } else {
            this.itemStack = this.readItemStack();
            this.signing = this.readBoolean();
            this.slot = this.readVarInt();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17_1)) {
            boolean matches;
            int value =
                    (matches = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2))
                            ? 1024
                            : 8192;
            this.writeVarInt(this.slot);
            this.writeVarInt(this.pages.size());
            for (String text : this.pages) {
                this.writeString(text, value);
            }
            boolean matches2 = matches;
            this.writeOptional(
                    this.title,
                    (result, result2) -> {
                        int value2 = matches2 ? 32 : 128;
                        result.writeString(result2, value2);
                    });
        } else {
            this.writeItemStack(this.itemStack);
            this.writeBoolean(this.signing);
            this.writeVarInt(this.slot);
        }
    }

    public void copy(WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        this.slot = wrapperPlayClientEditBook.slot;
        this.pages = wrapperPlayClientEditBook.pages;
        this.title = wrapperPlayClientEditBook.title;
        this.itemStack = wrapperPlayClientEditBook.itemStack;
        this.signing = wrapperPlayClientEditBook.signing;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Nullable
    public List<String> getPages() {
        return this.pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    @Nullable
    public String getTitle() {
        return this.title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(@Nullable ItemStack itemStack2) {
        this.itemStack = itemStack2;
    }

    @Nullable
    public Boolean getSigning() {
        return this.signing;
    }

    public void setSigning(@Nullable Boolean signing) {
        this.signing = signing;
    }
}
