package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable;

import org.jetbrains.annotations.NotNull;

public final class LinearComponents {

    private LinearComponents() {}

    @NotNull
    public static Component linear(
            @NotNull ComponentBuilderApplicable... componentBuilderApplicable) {
        int value;
        if ((value = componentBuilderApplicable.length) == 0) {
            return Component.empty();
        }
        if (value == 1) {
            ComponentBuilderApplicable componentBuilderApplicable2;
            if ((componentBuilderApplicable2 = componentBuilderApplicable[0])
                    instanceof ComponentLike) {
                return ((ComponentLike) componentBuilderApplicable2).asComponent();
            } else {
                throw nothingComponentLike();
            }
        } else {
            TextComponentImpl.BuilderImpl builderImpl = new TextComponentImpl.BuilderImpl();
            Style.Builder builder = null;
            for (int index = 0; index < value; index++) {
                ComponentBuilderApplicable componentBuilderApplicable3;
                if ((componentBuilderApplicable3 = componentBuilderApplicable[index])
                        instanceof StyleBuilderApplicable) {
                    if (builder == null) {
                        builder = Style.style();
                    }
                    builder.apply((StyleBuilderApplicable) componentBuilderApplicable3);
                } else if (builder != null
                        && componentBuilderApplicable3 instanceof ComponentLike) {
                    builderImpl.applicableApply(
                            ((ComponentLike) componentBuilderApplicable3)
                                    .asComponent()
                                    .style(builder));
                } else {
                    builderImpl.applicableApply(componentBuilderApplicable3);
                }
            }
            int value2;
            if ((value2 = builderImpl.children.size()) == 0) {
                throw nothingComponentLike();
            } else {
                return value2 == 1 && !builderImpl.hasStyle()
                        ? builderImpl.children.get(0)
                        : builderImpl.build();
            }
        }
    }

    private static IllegalStateException nothingComponentLike() {
        return new IllegalStateException(
                "Cannot build component linearly - nothing component-like was given");
    }
}
