package it.ytnoos.lpx.lib.kyori.adventure.inventory;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

final class BookImpl implements Book {

    private final Component title;

    private final Component author;

    private final List<Component> pages;

    BookImpl(
            @NotNull Component component,
            @NotNull Component component2,
            @NotNull List<Component> values) {
        this.title = Objects.requireNonNull(component, "title");
        this.author = Objects.requireNonNull(component2, "author");
        this.pages = Collections.unmodifiableList(Objects.requireNonNull(values, "pages"));
    }

    @NotNull
    @Override
    public Component title() {
        return this.title;
    }

    @NotNull
    @Override
    public Book title(@NotNull Component component) {
        return new BookImpl(Objects.requireNonNull(component, "title"), this.author, this.pages);
    }

    @NotNull
    @Override
    public Component author() {
        return this.author;
    }

    @NotNull
    @Override
    public Book author(@NotNull Component component) {
        return new BookImpl(this.title, Objects.requireNonNull(component, "author"), this.pages);
    }

    @NotNull
    @Override
    public List<Component> pages() {
        return this.pages;
    }

    @NotNull
    @Override
    public Book pages(@NotNull List<Component> values) {
        return new BookImpl(
                this.title, this.author, new ArrayList<>(Objects.requireNonNull(values, "pages")));
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("title", this.title),
                ExaminableProperty.of("author", this.author),
                ExaminableProperty.of("pages", this.pages));
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof BookImpl)) {
            return false;
        }
        BookImpl bookImpl = (BookImpl) result;
        return this.title.equals(bookImpl.title)
                && this.author.equals(bookImpl.author)
                && this.pages.equals(bookImpl.pages);
    }

    @Override
    public int hashCode() {
        int value = this.title.hashCode();
        value = 31 * value + this.author.hashCode();
        return 31 * value + this.pages.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class BuilderImpl implements Book.Builder {

        private Component title = Component.empty();

        private Component author = Component.empty();

        private final List<Component> pages = new ArrayList<>();

        @NotNull
        @Override
        public Book.Builder title(@NotNull Component component) {
            this.title = Objects.requireNonNull(component, "title");
            return this;
        }

        @NotNull
        @Override
        public Book.Builder author(@NotNull Component component) {
            this.author = Objects.requireNonNull(component, "author");
            return this;
        }

        @NotNull
        @Override
        public Book.Builder addPage(@NotNull Component component) {
            this.pages.add(Objects.requireNonNull(component, "page"));
            return this;
        }

        @NotNull
        @Override
        public Book.Builder pages(@NotNull Collection<Component> values) {
            this.pages.addAll(Objects.requireNonNull(values, "pages"));
            return this;
        }

        @NotNull
        @Override
        public Book.Builder pages(@NotNull Component... component) {
            Collections.addAll(this.pages, component);
            return this;
        }

        @NotNull
        @Override
        public Book build() {
            return new BookImpl(this.title, this.author, new ArrayList<>(this.pages));
        }
    }
}
