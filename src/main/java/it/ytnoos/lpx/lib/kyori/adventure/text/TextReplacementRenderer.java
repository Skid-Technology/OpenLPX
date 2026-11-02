package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.renderer.ComponentRenderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class TextReplacementRenderer implements ComponentRenderer<TextReplacementRenderer.State> {

    static final TextReplacementRenderer INSTANCE = new TextReplacementRenderer();

    private TextReplacementRenderer() {}

    @NotNull
    public Component render(
            @NotNull Component component2, @NotNull TextReplacementRenderer.State state) {
        if (!state.running) {
            return component2;
        }
        boolean matches = state.firstMatch;
        state.firstMatch = true;
        List<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values;
        int size2 = (values = component2.children()).size();
        Style style2 = component2.style();
        ArrayList values2 = null;
        Component component3 = component2;
        if (component2 instanceof TextComponent) {
            String text2 = ((TextComponent) component2).content();
            Matcher matcher2 = state.pattern.matcher(text2);
            int value2 = 0;
            while (matcher2.find()) {
                PatternReplacementResult patternReplacementResult;
                if ((patternReplacementResult =
                                state.continuer.shouldReplace(
                                        matcher2, ++state.matchCount, state.replaceCount))
                        != PatternReplacementResult.CONTINUE) {
                    if (patternReplacementResult == PatternReplacementResult.STOP) {
                        state.running = false;
                        break;
                    }
                    if (matcher2.start() == 0) {
                        if (matcher2.end() == text2.length()) {
                            ComponentLike componentLike;
                            if ((component3 =
                                                    (componentLike =
                                                                            state.replacement.apply(
                                                                                    matcher2,
                                                                                    Component.text()
                                                                                            .content(
                                                                                                    matcher2
                                                                                                            .group())
                                                                                            .style(
                                                                                                    component2
                                                                                                            .style())))
                                                                    == null
                                                            ? Component.empty()
                                                            : componentLike.asComponent())
                                            .style()
                                            .hoverEvent()
                                    != null) {
                                style2 = style2.hoverEvent(null);
                            }
                            component3 =
                                    component3.style(
                                            component3
                                                    .style()
                                                    .merge(
                                                            component2.style(),
                                                            Style.Merge.Strategy
                                                                    .IF_ABSENT_ON_TARGET));
                            if (values2 == null) {
                                (values2 = new ArrayList(size2 + component3.children().size()))
                                        .addAll(component3.children());
                            }
                        } else {
                            component3 = Component.text("", component2.style());
                            ComponentLike componentLike2;
                            if ((componentLike2 =
                                            state.replacement.apply(
                                                    matcher2,
                                                    Component.text().content(matcher2.group())))
                                    != null) {
                                if (values2 == null) {
                                    values2 = new ArrayList(size2 + 1);
                                }
                                values2.add(componentLike2.asComponent());
                            }
                        }
                    } else {
                        if (values2 == null) {
                            values2 = new ArrayList(size2 + 2);
                        }
                        if (state.firstMatch) {
                            component3 =
                                    ((TextComponent) component2)
                                            .content(text2.substring(0, matcher2.start()));
                        } else if (value2 < matcher2.start()) {
                            values2.add(Component.text(text2.substring(value2, matcher2.start())));
                        }
                        ComponentLike componentLike3;
                        if ((componentLike3 =
                                        state.replacement.apply(
                                                matcher2,
                                                Component.text().content(matcher2.group())))
                                != null) {
                            values2.add(componentLike3.asComponent());
                        }
                    }
                    state.replaceCount++;
                    state.firstMatch = false;
                    value2 = matcher2.end();
                }
            }
            if (value2 < text2.length() && value2 > 0) {
                if (values2 == null) {
                    values2 = new ArrayList(size2);
                }
                values2.add(Component.text(text2.substring(value2)));
            }
        } else if (component3 instanceof TranslatableComponent) {
            List<it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument> values3 =
                    ((TranslatableComponent) component3).arguments();
            ArrayList<it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument> values4 = null;
            int value3 = 0;
            for (int size3 = values3.size(); value3 < size3; value3++) {
                TranslationArgument translationArgument;
                TranslationArgument translationArgument2;
                if ((translationArgument =
                                        (translationArgument2 =
                                                                        (TranslationArgument)
                                                                                values3.get(value3))
                                                                .value()
                                                        instanceof Component
                                                ? TranslationArgument.component(
                                                        this.render(
                                                                (Component)
                                                                        translationArgument2
                                                                                .value(),
                                                                state))
                                                : translationArgument2)
                                != translationArgument2
                        && values4 == null) {
                    values4 = new ArrayList(size3);
                    if (value3 > 0) {
                        values4.addAll(values3.subList(0, value3));
                    }
                }
                if (values4 != null) {
                    values4.add(translationArgument);
                }
            }
            if (values4 != null) {
                component3 = ((TranslatableComponent) component3).arguments(values4);
            }
        }
        if (state.running) {
            HoverEvent hoverEvent2;
            if (state.replaceInsideHoverEvents && (hoverEvent2 = style2.hoverEvent()) != null) {
                HoverEvent hoverEvent3 = hoverEvent2.withRenderedValue(this, state);
                if (hoverEvent2 != hoverEvent3) {
                    component3 = component3.style(result -> result.hoverEvent(hoverEvent3));
                }
            }
            boolean matches2 = true;
            for (int index = 0; index < size2; index++) {
                Component component4 = (Component) values.get(index);
                Component component5;
                if ((component5 = this.render(component4, state)) != component4) {
                    if (values2 == null) {
                        values2 = new ArrayList(size2);
                    }
                    if (matches2) {
                        values2.addAll(values.subList(0, index));
                    }
                    matches2 = false;
                }
                if (values2 != null) {
                    values2.add(component5);
                    matches2 = false;
                }
            }
        } else if (values2 != null) {
            values2.addAll(values);
        }
        state.firstMatch = matches;
        return values2 != null ? component3.children(values2) : component3;
    }

    static final class State {

        final Pattern pattern;

        final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement;

        final TextReplacementConfig.Condition continuer;

        final boolean replaceInsideHoverEvents;

        boolean running = true;

        int matchCount = 0;

        int replaceCount = 0;

        boolean firstMatch = true;

        State(
                @NotNull Pattern pattern2,
                @NotNull
                        BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike>
                                biFunction,
                TextReplacementConfig.@NotNull Condition condition,
                boolean replaceInsideHoverEvents) {
            this.pattern = pattern2;
            this.replacement = biFunction;
            this.continuer = condition;
            this.replaceInsideHoverEvents = replaceInsideHoverEvents;
        }
    }
}
