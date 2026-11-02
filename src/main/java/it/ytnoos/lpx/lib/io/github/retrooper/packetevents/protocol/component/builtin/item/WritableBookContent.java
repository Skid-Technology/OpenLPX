package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Filterable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class WritableBookContent {

    private List<Filterable<String>> pages;

    public WritableBookContent(List<Filterable<String>> pages) {
        this.pages = pages;
    }

    public static WritableBookContent read(PacketWrapper<?> packetWrapper) {
        List values =
                packetWrapper.readList(
                        result -> Filterable.read(result, result2 -> result2.readString(1024)));
        return new WritableBookContent(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, WritableBookContent writableBookContent) {
        packetWrapper.writeList(
                writableBookContent.pages,
                (result, result2) ->
                        Filterable.write(
                                result,
                                result2,
                                (result3, result4) -> result3.writeString(result4, 1024)));
    }

    @Nullable
    public Filterable<String> getPage(int value) {
        return value >= 0 && value < this.pages.size() ? this.pages.get(value) : null;
    }

    public void addPage(Filterable<String> filterable) {
        this.pages.add(filterable);
    }

    public List<Filterable<String>> getPages() {
        return this.pages;
    }

    public void setPages(List<Filterable<String>> pages) {
        this.pages = pages;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof WritableBookContent)) {
            return false;
        }
        WritableBookContent writableBookContent = (WritableBookContent) result;
        return this.pages.equals(writableBookContent.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.pages);
    }
}
