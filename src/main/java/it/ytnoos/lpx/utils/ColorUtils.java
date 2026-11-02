package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.MiniMessage;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;

import java.util.HashMap;
import java.util.Map;

public final class ColorUtils {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private static final GsonComponentSerializer GSON = GsonComponentSerializer.gson();

    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER =
            LegacyComponentSerializer.legacySection();

    private static final PlainTextComponentSerializer PLAIN_TEXT_COMPONENT_SERIALIZER =
            PlainTextComponentSerializer.plainText();

    private static final Map<Character, String> ENTRIES = createEntries();

    private ColorUtils() {}

    public static Component deserialize(String text) {
        String text2 = text == null ? "" : text;
        return MINI_MESSAGE.deserialize(createText3(text2));
    }

    public static String escapeTags(String text) {
        return MINI_MESSAGE.escapeTags(text == null ? "" : text);
    }

    public static BaseComponent[] createBaseComponents(String text) {
        return ComponentSerializer.parse((String) GSON.serialize(deserialize(text)));
    }

    public static String createText(String text) {
        return PLAIN_TEXT_COMPONENT_SERIALIZER.serialize(deserialize(text));
    }

    public static String createText2(String text) {
        return LEGACY_COMPONENT_SERIALIZER.serialize(deserialize(text));
    }

    public static void send(CommandSender sender, String text) {
        if (sender instanceof Player player2) {
            Player player = player2;
            handleText(player, text);
        } else {
            sender.sendMessage(createText(text));
        }
    }

    public static void handleText(Player player, String text) {
        Spigot spigot2 = player.spigot();
        spigot2.sendMessage(createBaseComponents(text));
    }

    static String createText3(String text) {
        StringBuilder stringBuilder = new StringBuilder(text.length());
        for (int index = 0; index < text.length(); index++) {
            char value = text.charAt(index);
            if ((value == 167 || value == '&') && index + 1 < text.length()) {
                char value2 = Character.toLowerCase(text.charAt(index + 1));
                String text2 = ENTRIES.get(value2);
                if (text2 != null) {
                    stringBuilder.append(text2);
                    index++;
                    continue;
                }
            }
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }

    private static Map createEntries() {
        HashMap entries = new HashMap();
        entries.put('0', "<black>");
        entries.put('1', "<dark_blue>");
        entries.put('2', "<dark_green>");
        entries.put('3', "<dark_aqua>");
        entries.put('4', "<dark_red>");
        entries.put('5', "<dark_purple>");
        entries.put('6', "<gold>");
        entries.put('7', "<gray>");
        entries.put('8', "<dark_gray>");
        entries.put('9', "<blue>");
        entries.put('a', "<green>");
        entries.put('b', "<aqua>");
        entries.put('c', "<red>");
        entries.put('d', "<light_purple>");
        entries.put('e', "<yellow>");
        entries.put('f', "<white>");
        entries.put('k', "<obfuscated>");
        entries.put('l', "<bold>");
        entries.put('m', "<strikethrough>");
        entries.put('n', "<underlined>");
        entries.put('o', "<italic>");
        entries.put('r', "<reset>");
        return entries;
    }
}
