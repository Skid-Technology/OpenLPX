package it.ytnoos.lpx.lib.kyori.adventure.inventory;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@NonExtendable
public interface Book extends Buildable<Book, Book.Builder>, Examinable {

    @NotNull
    static Book book(
            @NotNull Component component,
            @NotNull Component component2,
            @NotNull Collection<Component> values) {
        return new BookImpl(component, component2, new ArrayList<>(values));
    }

    @NotNull
    static Book book(
            @NotNull Component component,
            @NotNull Component component2,
            @NotNull Component... component3) {
        return book(component, component2, Arrays.asList(component3));
    }

    @NotNull
    static Book.Builder builder() {
        return new BookImpl.BuilderImpl();
    }

    @NotNull
    Component title();

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book title(@NotNull Component component);

    @NotNull
    Component author();

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book author(@NotNull Component component);

    @NotNull
    @Unmodifiable
    List<Component> pages();

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default Book pages(@NotNull Component... component) {
        return this.pages(Arrays.asList(component));
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book pages(@NotNull List<Component> values);

    @NotNull
    default Book.Builder toBuilder() {
        return builder().title(this.title()).author(this.author()).pages(this.pages());
    }

    interface Builder extends AbstractBuilder<Book>, Buildable.Builder<Book> {

        @Contract("_ -> this")
        @NotNull
        Book.Builder title(@NotNull Component component);

        @Contract("_ -> this")
        @NotNull
        Book.Builder author(@NotNull Component component);

        @Contract("_ -> this")
        @NotNull
        Book.Builder addPage(@NotNull Component component);

        @Contract("_ -> this")
        @NotNull
        Book.Builder pages(@NotNull Component... component);

        @Contract("_ -> this")
        @NotNull
        Book.Builder pages(@NotNull Collection<Component> values);

        @NotNull
        Book build();
    }
}
