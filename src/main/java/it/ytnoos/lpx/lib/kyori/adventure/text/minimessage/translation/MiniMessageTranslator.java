package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgumentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.VirtualComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.VirtualComponentRenderer;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.MiniMessage;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.adventure.translation.Translator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public abstract class MiniMessageTranslator implements Translator {

    private final MiniMessage miniMessage;

    public MiniMessageTranslator() {
        this(MiniMessage.miniMessage());
    }

    public MiniMessageTranslator(@NotNull MiniMessage miniMessage2) {
        this.miniMessage = Objects.requireNonNull(miniMessage2, "miniMessage");
    }

    @Nullable
    protected abstract String getMiniMessageString(@NotNull String text, @NotNull Locale locale);

    @Nullable
    @Override
    public final MessageFormat translate(@NotNull String text, @NotNull Locale locale) {
        return null;
    }

    @Nullable
    @Override
    public final Component translate(
            @NotNull TranslatableComponent translatableComponent, @NotNull Locale locale) {
        String text2;
        if ((text2 = this.getMiniMessageString(translatableComponent.key(), locale)) == null) {
            return null;
        }
        Pointered pointered2 = new LocalePointered(locale);
        Component component;
        List<TranslationArgument> values;
        if ((values = translatableComponent.arguments()).isEmpty()) {
            component = this.miniMessage.deserialize(text2, pointered2);
        } else {
            TagResolver.Builder builder2 = TagResolver.builder();
            ArrayList<Tag> values2 = new ArrayList<>(values.size());
            boolean matches = false;
            Iterator<TranslationArgument> iterator2 = values.iterator();
            while (iterator2.hasNext()) {
                TranslationArgument translationArgument;
                Object result;
                if ((result =
                                (translationArgument = (TranslationArgument) iterator2.next())
                                        .value())
                        instanceof VirtualComponent) {
                    VirtualComponentRenderer virtualComponentRenderer;
                    if ((virtualComponentRenderer = ((VirtualComponent) result).renderer())
                            instanceof MiniMessageTranslatorTarget) {
                        if (matches) {
                            throw new IllegalArgumentException(
                                    "Multiple Argument.target() translation arguments have been set!");
                        }
                        pointered2 =
                                ((MiniMessageTranslatorTarget) virtualComponentRenderer)
                                        .pointered();
                        matches = true;
                        continue;
                    }
                    if (virtualComponentRenderer instanceof MiniMessageTranslatorArgument) {
                        MiniMessageTranslatorArgument miniMessageTranslatorArgument;
                        Object result2;
                        if ((result2 =
                                        (miniMessageTranslatorArgument =
                                                        (MiniMessageTranslatorArgument)
                                                                virtualComponentRenderer)
                                                .data())
                                instanceof TranslationArgumentLike) {
                            Tag tag2 = Tag.selfClosingInserting((TranslationArgumentLike) result2);
                            builder2.tag(miniMessageTranslatorArgument.name(), tag2);
                            values2.add(tag2);
                            continue;
                        }
                        if (result2 instanceof Tag) {
                            Tag tag3 = (Tag) result2;
                            builder2.tag(miniMessageTranslatorArgument.name(), tag3);
                            values2.add(tag3);
                            continue;
                        }
                        if (!(result2 instanceof TagResolver)) {
                            throw new IllegalArgumentException(
                                    "Unknown translator argument type: " + result2.getClass());
                        }
                        builder2.resolvers((TagResolver) result2);
                    }
                }
                values2.add(Tag.selfClosingInserting(translationArgument));
            }
            component =
                    this.miniMessage.deserialize(
                            text2, pointered2, new ArgumentTag(values2, builder2.build()));
        }
        Style style2;
        if (!(style2 = translatableComponent.style()).isEmpty()) {
            component = component.applyFallbackStyle(style2);
        }
        return component.append(translatableComponent.children());
    }
}
