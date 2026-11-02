package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

interface MappableResolver {

    boolean contributeToMap(@NotNull Map<String, Tag> entries);
}
