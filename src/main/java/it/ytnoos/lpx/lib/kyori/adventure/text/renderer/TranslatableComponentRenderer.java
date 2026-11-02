package it.ytnoos.lpx.lib.kyori.adventure.text.renderer;

import it.ytnoos.lpx.lib.kyori.adventure.text.BlockNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.BuildableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.EntityNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.KeybindComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.NBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.NBTComponentBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.ScoreComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.SelectorComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.StorageNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;
import it.ytnoos.lpx.lib.kyori.adventure.text.VirtualComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.translation.Translator;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.AttributedCharacterIterator;
import java.text.MessageFormat;
import java.text.MessageFormat.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public abstract class TranslatableComponentRenderer<C> extends AbstractComponentRenderer<C> {

    private static final Set<Style.Merge> MERGES;

    @NotNull
    public static TranslatableComponentRenderer<Locale> usingTranslationSource(
            @NotNull final Translator translator) {
        Objects.requireNonNull(translator, "source");
        return new TranslatableComponentRenderer<Locale>() {

            @Nullable
            protected MessageFormat translate(@NotNull String result, @NotNull Locale locale) {
                return translator.translate(result, locale);
            }

            @NotNull
            protected Component renderTranslatableInner(
                    @NotNull TranslatableComponent result, @NotNull Locale locale) {
                if (translator.hasAnyTranslations() == TriState.FALSE) {
                    return result;
                }
                Component component;
                if (translator.canTranslate(result.key(), locale)) {
                    component = translator.translate(result, locale);
                } else {
                    component = null;
                }
                return component != null
                        ? this.render(component, locale)
                        : super.renderTranslatableInner(result, locale);
            }
        };
    }

    @Nullable
    protected MessageFormat translate(@NotNull String text, @NotNull C c) {
        return null;
    }

    @Nullable
    protected MessageFormat translate(@NotNull String text, @Nullable String text2, @NotNull C c) {
        return this.translate(text, (C) c);
    }

    @NotNull
    @Override
    protected Component renderBlockNbt(@NotNull BlockNBTComponent blockNBTComponent, @NotNull C c) {
        BlockNBTComponent.Builder builder =
                ((BlockNBTComponent.Builder)
                                this.nbt((C) c, Component.blockNBT(), blockNBTComponent))
                        .pos(blockNBTComponent.pos());
        return this.mergeStyleAndOptionallyDeepRender(blockNBTComponent, builder, (C) c);
    }

    @NotNull
    @Override
    protected Component renderEntityNbt(
            @NotNull EntityNBTComponent entityNBTComponent, @NotNull C c) {
        EntityNBTComponent.Builder builder =
                ((EntityNBTComponent.Builder)
                                this.nbt((C) c, Component.entityNBT(), entityNBTComponent))
                        .selector(entityNBTComponent.selector());
        return this.mergeStyleAndOptionallyDeepRender(entityNBTComponent, builder, (C) c);
    }

    @NotNull
    @Override
    protected Component renderStorageNbt(
            @NotNull StorageNBTComponent storageNBTComponent, @NotNull C c) {
        StorageNBTComponent.Builder builder =
                ((StorageNBTComponent.Builder)
                                this.nbt((C) c, Component.storageNBT(), storageNBTComponent))
                        .storage(storageNBTComponent.storage());
        return this.mergeStyleAndOptionallyDeepRender(storageNBTComponent, builder, (C) c);
    }

    protected <O extends NBTComponent<O, B>, B extends NBTComponentBuilder<O, B>> B nbt(
            @NotNull C c, B b, O o) {
        b.nbtPath(o.nbtPath()).interpret(o.interpret());
        Component component;
        if ((component = o.separator()) != null) {
            b.separator(this.render(component, (C) c));
        }
        return (B) b;
    }

    @NotNull
    @Override
    protected Component renderKeybind(@NotNull KeybindComponent keybindComponent, @NotNull C c) {
        KeybindComponent.Builder builder = Component.keybind().keybind(keybindComponent.keybind());
        return this.mergeStyleAndOptionallyDeepRender(keybindComponent, builder, (C) c);
    }

    @NotNull
    @Override
    protected Component renderScore(@NotNull ScoreComponent scoreComponent, @NotNull C c) {
        ScoreComponent.Builder builder =
                Component.score()
                        .name(scoreComponent.name())
                        .objective(scoreComponent.objective())
                        .value(scoreComponent.value());
        return this.mergeStyleAndOptionallyDeepRender(scoreComponent, builder, (C) c);
    }

    @NotNull
    @Override
    protected Component renderSelector(@NotNull SelectorComponent selectorComponent, @NotNull C c) {
        SelectorComponent.Builder builder =
                Component.selector().pattern(selectorComponent.pattern());
        return this.mergeStyleAndOptionallyDeepRender(selectorComponent, builder, (C) c);
    }

    @NotNull
    @Override
    protected Component renderText(@NotNull TextComponent textComponent, @NotNull C c) {
        TextComponent.Builder builder = Component.text().content(textComponent.content());
        return this.mergeStyleAndOptionallyDeepRender(textComponent, builder, (C) c);
    }

    @NotNull
    @Override
    protected Component renderTranslatable(
            @NotNull TranslatableComponent translatableComponent, @NotNull C c) {
        List<TranslationArgument> values = translatableComponent.arguments();
        List<Component> values2 = translatableComponent.children();
        if (!values.isEmpty() || !values2.isEmpty()) {
            TranslatableComponent.Builder builder = translatableComponent.toBuilder();
            if (!values.isEmpty()) {
                ArrayList values3 = new ArrayList(values);
                for (int index = 0; index < values3.size(); index++) {
                    TranslationArgument translationArgument;
                    if ((translationArgument = (TranslationArgument) values3.get(index)).value()
                                    instanceof Component
                            && !(translationArgument.value() instanceof VirtualComponent)) {
                        values3.set(
                                index,
                                TranslationArgument.component(
                                        this.render(
                                                (Component) translationArgument.value(), (C) c)));
                    }
                }
                builder.arguments(values3);
            }
            translatableComponent = builder.build();
        }
        return this.renderTranslatableInner(translatableComponent, (C) c);
    }

    @NotNull
    protected Component renderTranslatableInner(
            @NotNull TranslatableComponent translatableComponent, @NotNull C c) {
        MessageFormat messageFormat;
        if ((messageFormat =
                        this.translate(
                                translatableComponent.key(),
                                translatableComponent.fallback(),
                                (C) c))
                == null) {
            return this.optionallyRenderChildrenAndStyle(translatableComponent, (C) c);
        }
        List<TranslationArgument> values = translatableComponent.arguments();
        TextComponent.Builder builder = Component.text();
        this.mergeStyle(translatableComponent, builder, (C) c);
        if (values.isEmpty()) {
            builder.content(
                    messageFormat.format((Object[]) null, new StringBuffer(), null).toString());
            return this.optionallyRenderChildrenAppendAndBuild(
                    translatableComponent.children(), builder, (C) c);
        }
        Object[] results = new Object[values.size()];
        StringBuffer stringBuffer = messageFormat.format(results, new StringBuffer(), null);
        AttributedCharacterIterator attributedCharacterIterator =
                messageFormat.formatToCharacterIterator(results);
        while (attributedCharacterIterator.getIndex() < attributedCharacterIterator.getEndIndex()) {
            int runLimit = attributedCharacterIterator.getRunLimit();
            Integer value;
            if ((value = (Integer) attributedCharacterIterator.getAttribute(Field.ARGUMENT))
                    != null) {
                TranslationArgument translationArgument = (TranslationArgument) values.get(value);
                builder.append(translationArgument.asComponent());
            } else {
                builder.append(
                        Component.text(
                                stringBuffer.substring(
                                        attributedCharacterIterator.getIndex(), runLimit)));
            }
            attributedCharacterIterator.setIndex(runLimit);
        }
        return this.optionallyRenderChildrenAppendAndBuild(
                translatableComponent.children(), builder, (C) c);
    }

    protected Component optionallyRenderChildrenAndStyle(Component component, C c) {
        HoverEvent hoverEvent2;
        if ((hoverEvent2 = component.hoverEvent()) != null) {
            component = component.hoverEvent(hoverEvent2.withRenderedValue(this, (C) c));
        }
        List<Component> values;
        if ((values = component.children()).isEmpty()) {
            return component;
        }
        ArrayList<Component> values2 = new ArrayList<>(values.size());
        values.forEach(result -> values2.add(this.render(result, (C) ((Object) c))));
        return component.children(values2);
    }

    protected <O extends BuildableComponent<O, B>, B extends ComponentBuilder<O, B>>
            O mergeStyleAndOptionallyDeepRender(Component component, B b, C c) {
        this.mergeStyle(component, (B) b, (C) c);
        return this.optionallyRenderChildrenAppendAndBuild(component.children(), (B) b, (C) c);
    }

    protected <O extends BuildableComponent<O, B>, B extends ComponentBuilder<O, B>>
            O optionallyRenderChildrenAppendAndBuild(List<Component> values, B b, C c) {
        if (!values.isEmpty()) {
            values.forEach(
                    result -> ((ComponentBuilder) b).append(this.render(result, (C) ((Object) c))));
        }
        return (O) b.build();
    }

    protected <B extends ComponentBuilder<?, ?>> void mergeStyle(Component component, B b, C c) {
        b.mergeStyle(component, MERGES);
        b.clickEvent(component.clickEvent());
        HoverEvent hoverEvent2;
        if ((hoverEvent2 = component.hoverEvent()) != null) {
            b.hoverEvent(hoverEvent2.withRenderedValue(this, (C) c));
        }
    }

    static {
        EnumSet values;
        (values = EnumSet.allOf(Style.Merge.class)).remove(Style.Merge.EVENTS);
        MERGES = Collections.unmodifiableSet(values);
    }
}
