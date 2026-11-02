package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class ComponentCompaction {

    @VisibleForTesting static final boolean SIMPLIFY_STYLE_FOR_BLANK_COMPONENTS = false;

    private ComponentCompaction() {}

    static Component compact(@NotNull Component component, @Nullable Style style2) {
        List<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values = component.children();
        Component component2 = component.children(Collections.emptyList());
        if (style2 != null) {
            component2 = component2.style(component.style().unmerge(style2));
        }
        int value;
        if ((value = values.size()) == 0) {
            if (isBlank(component2)) {
                component2 = component2.style(simplifyStyleForBlank(component2.style(), style2));
            }
            return component2;
        } else {
            if (value == 1
                    && isText(component2)
                    && ((TextComponent) component2).content().isEmpty()) {
                Component component3 = (Component) values.get(0);
                return component3
                        .style(
                                component3
                                        .style()
                                        .merge(
                                                component2.style(),
                                                Style.Merge.Strategy.IF_ABSENT_ON_TARGET))
                        .compact();
            }
            Style style3 = component2.style();
            if (style2 != null) {
                style3 = style3.merge(style2, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
            }
            ArrayList<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values2 =
                    new ArrayList<>(values.size());
            for (int index = 0; index < values.size(); index++) {
                Component component4;
                if (!(component4 = compact((Component) values.get(index), style3))
                                .children()
                                .isEmpty()
                        || !isText(component4)
                        || !((TextComponent) component4).content().isEmpty()) {
                    values2.add(component4);
                }
            }
            if (isText(component2)) {
                while (!values2.isEmpty()) {
                    Component component5;
                    Style style4 =
                            (component5 = (Component) values2.get(0))
                                    .style()
                                    .merge(style3, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
                    if (!isText(component5) || !Objects.equals(style4, style3)) {
                        break;
                    }
                    component2 = joinText((TextComponent) component2, (TextComponent) component5);
                    values2.remove(0);
                    values2.addAll(0, component5.children());
                }
            }
            int value2 = 0;
            while (value2 + 1 < values2.size()) {
                Component component6 = (Component) values2.get(value2);
                Component component7 = (Component) values2.get(value2 + 1);
                if (component6.children().isEmpty() && isText(component6) && isText(component7)) {
                    Style style5 =
                            component6
                                    .style()
                                    .merge(style3, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
                    Style style6 =
                            component7
                                    .style()
                                    .merge(style3, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
                    if (style5.equals(style6)) {
                        TextComponent textComponent =
                                joinText((TextComponent) component6, (TextComponent) component7);
                        values2.set(value2, textComponent);
                        values2.remove(value2 + 1);
                        continue;
                    }
                }
                value2++;
            }
            if (values2.isEmpty() && isBlank(component2)) {
                component2 = component2.style(simplifyStyleForBlank(component2.style(), style2));
            }
            return component2.children(values2);
        }
    }

    private static boolean isBlank(Component component) {
        if (isText(component)) {
            String text = ((TextComponent) component).content();
            for (int index = 0; index < text.length(); index++) {
                if (text.charAt(index) != 32) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    private static Style simplifyStyleForBlank(@NotNull Style style, @Nullable Style style2) {
        return style;
    }

    private static TextComponent joinText(
            TextComponent textComponent, TextComponent textComponent2) {
        return TextComponentImpl.create(
                textComponent2.children(),
                textComponent.style(),
                textComponent.content() + textComponent2.content());
    }

    private static boolean isText(Component component) {
        return component instanceof TextComponent && !(component instanceof VirtualComponent);
    }
}
