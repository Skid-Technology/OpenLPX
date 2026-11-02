package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.examination.string.StringExaminer;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Deprecated
@ScheduledForRemoval(inVersion = "5.0.0")
@Renderer(
        text = "this.debuggerString()",
        childrenArray = "this.children().toArray()",
        hasChildren = "!this.children().isEmpty()")
public abstract class AbstractComponent implements Component {

    protected final List<Component> children;

    protected final Style style;

    protected AbstractComponent(
            @NotNull List<? extends ComponentLike> values, @NotNull Style style2) {
        this.children = ComponentLike.asComponents(values, IS_NOT_EMPTY);
        this.style = style2;
    }

    @NotNull
    @Override
    public final List<Component> children() {
        return this.children;
    }

    @NotNull
    @Override
    public final Style style() {
        return this.style;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof AbstractComponent)) {
            return false;
        }
        AbstractComponent abstractComponent = (AbstractComponent) result;
        return Objects.equals(this.children, abstractComponent.children)
                && Objects.equals(this.style, abstractComponent.style);
    }

    @Override
    public int hashCode() {
        int value = this.children.hashCode();
        return 31 * value + this.style.hashCode();
    }

    @Override
    public abstract String toString();

    @NotNull
    @Override
    public ComponentBuilder<?, ?> toBuilder() {
        return ((BuildableComponent) this).toBuilder();
    }

    private String debuggerString() {
        Stream<? extends it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty> stream =
                this.examinableProperties().filter(result -> !result.name().equals("children"));
        return StringExaminer.simpleEscaping().examine(this.examinableName(), stream);
    }
}
