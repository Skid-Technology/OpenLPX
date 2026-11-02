package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Filterable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class WrittenBookContent {

    private Filterable<String> title;

    private String author;

    private int generation;

    private List<Filterable<Component>> pages;

    private boolean resolved;

    public WrittenBookContent(
            Filterable<String> filterable,
            String author,
            int generation,
            List<Filterable<Component>> pages,
            boolean resolved) {
        this.title = filterable;
        this.author = author;
        this.generation = generation;
        this.pages = pages;
        this.resolved = resolved;
    }

    public static WrittenBookContent read(PacketWrapper<?> packetWrapper) {
        Filterable filterable = Filterable.read(packetWrapper, result -> result.readString(32));
        String text = packetWrapper.readString();
        int value = packetWrapper.readVarInt();
        List values =
                packetWrapper.readList(
                        result -> Filterable.read(result, PacketWrapper::readComponent));
        boolean matches = packetWrapper.readBoolean();
        return new WrittenBookContent(filterable, text, value, values, matches);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, WrittenBookContent writtenBookContent) {
        Filterable.write(
                packetWrapper,
                writtenBookContent.title,
                (result, result2) -> result.writeString(result2, 32));
        packetWrapper.writeString(writtenBookContent.author);
        packetWrapper.writeVarInt(writtenBookContent.generation);
        packetWrapper.writeList(
                writtenBookContent.pages,
                (result, result2) ->
                        Filterable.write(result, result2, PacketWrapper::writeComponent));
        packetWrapper.writeBoolean(writtenBookContent.resolved);
    }

    public Filterable<String> getTitle() {
        return this.title;
    }

    public void setTitle(Filterable<String> filterable) {
        this.title = filterable;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getGeneration() {
        return this.generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    @Nullable
    public Filterable<Component> getPage(int value) {
        return value >= 0 && value < this.pages.size() ? this.pages.get(value) : null;
    }

    public void addPage(Filterable<Component> filterable) {
        this.pages.add(filterable);
    }

    public List<Filterable<Component>> getPages() {
        return this.pages;
    }

    public void setPages(List<Filterable<Component>> pages) {
        this.pages = pages;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof WrittenBookContent)) {
            return false;
        } else {
            WrittenBookContent writtenBookContent = (WrittenBookContent) result;
            if (this.generation != writtenBookContent.generation) {
                return false;
            } else if (this.resolved != writtenBookContent.resolved) {
                return false;
            } else if (!this.title.equals(writtenBookContent.title)) {
                return false;
            } else {
                return !this.author.equals(writtenBookContent.author)
                        ? false
                        : this.pages.equals(writtenBookContent.pages);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author, this.generation, this.pages, this.resolved);
    }
}
