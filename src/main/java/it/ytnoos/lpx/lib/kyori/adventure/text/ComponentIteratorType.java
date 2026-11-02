package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.List;
import java.util.Set;

@FunctionalInterface
@NonExtendable
public interface ComponentIteratorType {

    ComponentIteratorType DEPTH_FIRST =
            (result, result2, result3) -> {
                List<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values2;
                HoverEvent hoverEvent2;
                List<it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument> values;
                if (result3.contains(ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS)
                        && result instanceof TranslatableComponent) {
                    for (int value2 =
                                    (values = ((TranslatableComponent) result).arguments()).size()
                                            - 1;
                            value2 >= 0;
                            value2--) {
                        result2.addFirst(((ComponentLike) values.get(value2)).asComponent());
                    }
                }
                if ((hoverEvent2 = result.hoverEvent()) != null) {
                    HoverEvent.Action action2 = hoverEvent2.action();
                    if (result3.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME)
                            && action2 == HoverEvent.Action.SHOW_ENTITY) {
                        result2.addFirst(((HoverEvent.ShowEntity) hoverEvent2.value()).name());
                    } else if (result3.contains(
                                    ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT)
                            && action2 == HoverEvent.Action.SHOW_TEXT) {
                        result2.addFirst((Component) hoverEvent2.value());
                    }
                }
                for (int value3 = (values2 = result.children()).size() - 1; value3 >= 0; value3--) {
                    result2.addFirst((Component) values2.get(value3));
                }
            };

    ComponentIteratorType BREADTH_FIRST =
            (result, result2, result3) -> {
                HoverEvent hoverEvent2;
                if (result3.contains(ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS)
                        && result instanceof TranslatableComponent) {
                    for (TranslationArgument translationArgument :
                            ((TranslatableComponent) result).arguments()) {
                        result2.add(translationArgument.asComponent());
                    }
                }
                if ((hoverEvent2 = result.hoverEvent()) != null) {
                    HoverEvent.Action action2 = hoverEvent2.action();
                    if (result3.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME)
                            && action2 == HoverEvent.Action.SHOW_ENTITY) {
                        result2.addLast(((HoverEvent.ShowEntity) hoverEvent2.value()).name());
                    } else if (result3.contains(
                                    ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT)
                            && action2 == HoverEvent.Action.SHOW_TEXT) {
                        result2.addLast((Component) hoverEvent2.value());
                    }
                }
                result2.addAll(result.children());
            };

    void populate(
            @NotNull Component component,
            @NotNull Deque<Component> values,
            @NotNull Set<ComponentIteratorFlag> values2);
}
