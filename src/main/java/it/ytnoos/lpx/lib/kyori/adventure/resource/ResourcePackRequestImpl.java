package it.ytnoos.lpx.lib.kyori.adventure.resource;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.MonkeyBars;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

final class ResourcePackRequestImpl implements ResourcePackRequest {

    private final List<ResourcePackInfo> packs;

    private final ResourcePackCallback cb;

    private final boolean replace;

    private final boolean required;

    @Nullable private final Component prompt;

    ResourcePackRequestImpl(
            List<ResourcePackInfo> packs,
            ResourcePackCallback resourcePackCallback,
            boolean replace,
            boolean required,
            @Nullable Component component) {
        this.packs = packs;
        this.cb = resourcePackCallback;
        this.replace = replace;
        this.required = required;
        this.prompt = component;
    }

    @NotNull
    @Override
    public List<ResourcePackInfo> packs() {
        return this.packs;
    }

    @NotNull
    @Override
    public ResourcePackRequest packs(@NotNull Iterable<? extends ResourcePackInfoLike> iterable) {
        return this.packs.equals(iterable)
                ? this
                : new ResourcePackRequestImpl(
                        MonkeyBars.toUnmodifiableList(
                                ResourcePackInfoLike::asResourcePackInfo, iterable),
                        this.cb,
                        this.replace,
                        this.required,
                        this.prompt);
    }

    @NotNull
    @Override
    public ResourcePackCallback callback() {
        return this.cb;
    }

    @NotNull
    @Override
    public ResourcePackRequest callback(@NotNull ResourcePackCallback resourcePackCallback) {
        return resourcePackCallback == this.cb
                ? this
                : new ResourcePackRequestImpl(
                        this.packs,
                        Objects.requireNonNull(resourcePackCallback, "cb"),
                        this.replace,
                        this.required,
                        this.prompt);
    }

    @Override
    public boolean replace() {
        return this.replace;
    }

    @Override
    public boolean required() {
        return this.required;
    }

    @Nullable
    @Override
    public Component prompt() {
        return this.prompt;
    }

    @NotNull
    @Override
    public ResourcePackRequest replace(boolean matches) {
        return matches == this.replace
                ? this
                : new ResourcePackRequestImpl(
                        this.packs, this.cb, matches, this.required, this.prompt);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            ResourcePackRequestImpl resourcePackRequestImpl = (ResourcePackRequestImpl) result;
            return this.replace == resourcePackRequestImpl.replace
                    && Objects.equals(this.packs, resourcePackRequestImpl.packs)
                    && Objects.equals(this.cb, resourcePackRequestImpl.cb)
                    && this.required == resourcePackRequestImpl.required
                    && Objects.equals(this.prompt, resourcePackRequestImpl.prompt);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.packs, this.cb, this.replace, this.required, this.prompt);
    }

    @NotNull
    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("packs", this.packs),
                ExaminableProperty.of("callback", this.cb),
                ExaminableProperty.of("replace", this.replace),
                ExaminableProperty.of("required", this.required),
                ExaminableProperty.of("prompt", this.prompt));
    }

    static final class BuilderImpl implements ResourcePackRequest.Builder {

        private List<ResourcePackInfo> packs;

        private ResourcePackCallback cb;

        private boolean replace;

        private boolean required;

        @Nullable private Component prompt;

        BuilderImpl() {
            this.packs = Collections.emptyList();
            this.cb = ResourcePackCallback.noOp();
            this.replace = false;
        }

        BuilderImpl(@NotNull ResourcePackRequest resourcePackRequest) {
            this.packs = resourcePackRequest.packs();
            this.cb = resourcePackRequest.callback();
            this.replace = resourcePackRequest.replace();
            this.required = resourcePackRequest.required();
            this.prompt = resourcePackRequest.prompt();
        }

        @NotNull
        @Override
        public ResourcePackRequest.Builder packs(
                @NotNull ResourcePackInfoLike resourcePackInfoLike,
                @NotNull ResourcePackInfoLike... resourcePackInfoLike2) {
            this.packs =
                    MonkeyBars.nonEmptyArrayToList(
                            ResourcePackInfoLike::asResourcePackInfo,
                            resourcePackInfoLike,
                            resourcePackInfoLike2);
            return this;
        }

        @NotNull
        @Override
        public ResourcePackRequest.Builder packs(
                @NotNull Iterable<? extends ResourcePackInfoLike> iterable) {
            this.packs =
                    MonkeyBars.toUnmodifiableList(
                            ResourcePackInfoLike::asResourcePackInfo, iterable);
            return this;
        }

        @NotNull
        @Override
        public ResourcePackRequest.Builder callback(
                @NotNull ResourcePackCallback resourcePackCallback) {
            this.cb = Objects.requireNonNull(resourcePackCallback, "cb");
            return this;
        }

        @NotNull
        @Override
        public ResourcePackRequest.Builder replace(boolean replace) {
            this.replace = replace;
            return this;
        }

        @NotNull
        @Override
        public ResourcePackRequest.Builder required(boolean required) {
            this.required = required;
            return this;
        }

        @NotNull
        @Override
        public ResourcePackRequest.Builder prompt(@Nullable Component component) {
            this.prompt = component;
            return this;
        }

        @NotNull
        public ResourcePackRequest build() {
            return new ResourcePackRequestImpl(
                    this.packs, this.cb, this.replace, this.required, this.prompt);
        }
    }
}
