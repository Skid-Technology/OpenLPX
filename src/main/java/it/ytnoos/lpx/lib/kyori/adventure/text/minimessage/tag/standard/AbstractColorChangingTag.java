package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.VirtualComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.VirtualComponentRenderer;
import it.ytnoos.lpx.lib.kyori.adventure.text.flattener.ComponentFlattener;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.TagNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.ValueNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Inserting;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Modifying;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tree.Node;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Collections;
import java.util.PrimitiveIterator.OfInt;
import java.util.function.Consumer;
import java.util.stream.Stream;

abstract class AbstractColorChangingTag implements Modifying, Examinable {

    private static final ComponentFlattener LENGTH_CALCULATOR =
            ComponentFlattener.builder()
                    .mapper(TextComponent.class, TextComponent::content)
                    .unknownMapper(result -> "_")
                    .build();

    private boolean visited;

    private int size = 0;

    private int disableApplyingColorDepth = -1;

    private final boolean emitVirtuals;

    AbstractColorChangingTag(Context context) {
        this.emitVirtuals = context.emitVirtuals();
    }

    protected final int size() {
        return this.size;
    }

    @Override
    public final void visit(@NotNull Node node, int value2) {
        if (this.visited) {
            throw new IllegalStateException(
                    "Color changing tag instances cannot be re-used, return a new one for each resolve");
        }
        if (node instanceof ValueNode) {
            String text = ((ValueNode) node).value();
            this.size = this.size + text.codePointCount(0, text.length());
        } else {
            TagNode tagNode;
            if (node instanceof TagNode && (tagNode = (TagNode) node).tag() instanceof Inserting) {
                ComponentFlattener componentFlattener = LENGTH_CALCULATOR;
                Component component = ((Inserting) tagNode.tag()).value();
                componentFlattener.flatten(
                        component,
                        result ->
                                this.size = this.size + result.codePointCount(0, result.length()));
            }
        }
    }

    @Override
    public final void postVisit() {
        this.visited = true;
        this.init();
    }

    @Override
    public final Component apply(@NotNull Component component, int disableApplyingColorDepth) {
        if (this.emitVirtuals && disableApplyingColorDepth == 0) {
            return Component.virtual(
                    Void.class,
                    new AbstractColorChangingTag.TagInfoHolder(this.preserveData(), component),
                    component.style());
        }
        if ((this.disableApplyingColorDepth == -1
                        || disableApplyingColorDepth <= this.disableApplyingColorDepth)
                && component.style().color() == null) {
            this.disableApplyingColorDepth = -1;
            if (component instanceof VirtualComponent) {
                this.skipColorForLengthOf(((VirtualComponent) component).content());
                return component.children(Collections.emptyList());
            }
            if (component instanceof TextComponent
                    && ((TextComponent) component).content().length() > 0) {
                String text2 = ((TextComponent) component).content();
                TextComponent.Builder builder = Component.text();
                int[] values = new int[1];
                OfInt ofInt = text2.codePoints().iterator();
                while (ofInt.hasNext()) {
                    values[0] = ofInt.nextInt();
                    TextComponent textComponent =
                            Component.text(
                                    new String(values, 0, 1),
                                    component.style().color(this.color()));
                    this.advanceColor();
                    builder.append(textComponent);
                }
                return builder.build();
            } else if (!(component instanceof TextComponent)) {
                Component component2 =
                        component.children(Collections.emptyList()).colorIfAbsent(this.color());
                this.advanceColor();
                return component2;
            } else {
                return Component.empty().mergeStyle(component);
            }
        } else {
            if (this.disableApplyingColorDepth == -1
                    || disableApplyingColorDepth < this.disableApplyingColorDepth) {
                this.disableApplyingColorDepth = disableApplyingColorDepth;
            }
            if (component instanceof TextComponent) {
                this.skipColorForLengthOf(((TextComponent) component).content());
            }
            return component.children(Collections.emptyList());
        }
    }

    private void skipColorForLengthOf(String text) {
        int value = text.codePointCount(0, text.length());
        for (int index = 0; index < value; index++) {
            this.advanceColor();
        }
    }

    protected abstract void init();

    protected abstract void advanceColor();

    protected abstract TextColor color();

    @NotNull
    protected abstract Consumer<TokenEmitter> preserveData();

    @NotNull
    @Override
    public abstract Stream<? extends ExaminableProperty> examinableProperties();

    @NotNull
    @Override
    public final String toString() {
        return Internals.toString(this);
    }

    @Override
    public abstract boolean equals(@Nullable Object result);

    @Override
    public abstract int hashCode();

    @Nullable
    static Emitable claimComponent(Component component) {
        if (!(component instanceof VirtualComponent)) {
            return null;
        }
        VirtualComponentRenderer virtualComponentRenderer;
        return !((virtualComponentRenderer = ((VirtualComponent) component).renderer())
                        instanceof AbstractColorChangingTag.TagInfoHolder)
                ? null
                : (AbstractColorChangingTag.TagInfoHolder) virtualComponentRenderer;
    }

    static final class TagInfoHolder implements VirtualComponentRenderer<Void>, Emitable {

        private final Consumer<TokenEmitter> output;

        private final Component originalComp;

        TagInfoHolder(Consumer<TokenEmitter> consumer, Component component) {
            this.output = consumer;
            this.originalComp = component;
        }

        @UnknownNullability
        public ComponentLike apply(@NotNull Void voidValue) {
            return this.originalComp;
        }

        @NotNull
        @Override
        public String fallbackString() {
            return "";
        }

        @Override
        public void emit(@NotNull TokenEmitter tokenEmitter) {
            this.output.accept(tokenEmitter);
        }

        @Nullable
        @Override
        public Component substitute() {
            return this.originalComp;
        }
    }
}
