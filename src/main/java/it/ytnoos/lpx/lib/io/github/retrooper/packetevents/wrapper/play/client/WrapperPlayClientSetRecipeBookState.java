package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.book.BookType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSetRecipeBookState
        extends PacketWrapper<WrapperPlayClientSetRecipeBookState> {

    private BookType bookType;

    private boolean bookOpen;

    private boolean filterActive;

    public WrapperPlayClientSetRecipeBookState(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSetRecipeBookState(
            BookType bookType2, boolean bookOpen, boolean filterActive) {
        super(PacketType.Play.Client.SET_RECIPE_BOOK_STATE);
        this.bookType = bookType2;
        this.bookOpen = bookOpen;
        this.filterActive = filterActive;
    }

    @Override
    public void read() {
        this.bookType = BookType.getById(this.readVarInt());
        this.bookOpen = this.readBoolean();
        this.filterActive = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeVarInt(this.bookType.getId());
        this.writeBoolean(this.bookOpen);
        this.writeBoolean(this.filterActive);
    }

    public void copy(WrapperPlayClientSetRecipeBookState wrapperPlayClientSetRecipeBookState) {
        this.bookType = wrapperPlayClientSetRecipeBookState.bookType;
        this.bookOpen = wrapperPlayClientSetRecipeBookState.bookOpen;
        this.filterActive = wrapperPlayClientSetRecipeBookState.filterActive;
    }

    public BookType getBookType() {
        return this.bookType;
    }

    public void setBookType(BookType bookType2) {
        this.bookType = bookType2;
    }

    public boolean isBookOpen() {
        return this.bookOpen;
    }

    public void setBookOpen(boolean bookOpen) {
        this.bookOpen = bookOpen;
    }

    public boolean isFilterActive() {
        return this.filterActive;
    }

    public void setFilterActive(boolean filterActive) {
        this.filterActive = filterActive;
    }
}
