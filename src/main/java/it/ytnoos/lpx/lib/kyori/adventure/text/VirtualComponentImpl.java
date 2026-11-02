package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class VirtualComponentImpl<C> extends TextComponentImpl implements VirtualComponent {

    private final Class<C> contextType;

    private final VirtualComponentRenderer<C> renderer;

    static <C> VirtualComponent createVirtual(
            @NotNull Class<C> classValue,
            @NotNull VirtualComponentRenderer<C> virtualComponentRenderer) {
        return createVirtual(
                classValue, virtualComponentRenderer, Collections.emptyList(), Style.empty());
    }

    static <C> VirtualComponent createVirtual(
            @NotNull Class<C> classValue,
            @NotNull VirtualComponentRenderer<C> virtualComponentRenderer,
            List<? extends ComponentLike> values,
            Style style) {
        List<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values2 =
                ComponentLike.asComponents(values, IS_NOT_EMPTY);
        return new VirtualComponentImpl(values2, style, "", classValue, virtualComponentRenderer);
    }

    private VirtualComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            @NotNull String text,
            @NotNull Class<C> classValue,
            @NotNull VirtualComponentRenderer<C> virtualComponentRenderer) {
        super(values, style, text);
        this.contextType = classValue;
        this.renderer = virtualComponentRenderer;
    }

    VirtualComponent create0(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull String text) {
        return new VirtualComponentImpl<>(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                style,
                text,
                this.contextType,
                this.renderer);
    }

    @NotNull
    @Override
    public Class<C> contextType() {
        return this.contextType;
    }

    @NotNull
    @Override
    public VirtualComponentRenderer<C> renderer() {
        return this.renderer;
    }

    @NotNull
    @Override
    public String content() {
        return this.renderer.fallbackString();
    }

    @NotNull
    @Override
    public TextComponent.Builder toBuilder() {
        return new VirtualComponentImpl.BuilderImpl<>(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof VirtualComponentImpl)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        VirtualComponentImpl virtualComponentImpl = (VirtualComponentImpl) result;
        return Objects.equals(this.contextType, virtualComponentImpl.contextType)
                && Objects.equals(this.renderer, virtualComponentImpl.renderer);
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        value = 31 * value + this.contextType.hashCode();
        return 31 * value + this.renderer.hashCode();
    }

    static final class BuilderImpl<C> extends TextComponentImpl.BuilderImpl {

        private final Class<C> contextType;

        private final VirtualComponentRenderer<C> renderer;

        BuilderImpl(VirtualComponentImpl<C> virtualComponentImpl) {
            super(virtualComponentImpl);
            this.contextType = virtualComponentImpl.contextType();
            this.renderer = virtualComponentImpl.renderer();
        }

        @NotNull
        @Override
        public TextComponent build() {
            return VirtualComponentImpl.createVirtual(
                    this.contextType, this.renderer, this.children, this.buildStyle());
        }
    }
}
