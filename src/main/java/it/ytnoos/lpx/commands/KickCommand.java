package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.utils.ColorUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class KickCommand extends SubCommand {

    public KickCommand(LPXBukkit lPXBukkit) {
        super(
                lPXBukkit,
                "kick",
                "lpx.kick",
                "Force close player connection",
                false,
                "<player> <message>");
    }

    private static String createText(String[] texts) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 2; index < texts.length; index++) {
            stringBuilder.append(texts[index]);
            if (index != texts.length - 1) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static void runCommand(LPXBukkit lPXBukkit, CommandSender sender, String[] texts) {
        if (texts.length < 2) {
            ConfigManager configManager = lPXBukkit.getConfigManager();
            Messages messages = configManager.getMessages();
            String text =
                    messages.getPrefix() + ChatColor.RED + " Syntax: /lpx kick <player> <message>";
            ColorUtils.send(sender, text);
        } else {
            Player playerExact = Bukkit.getPlayerExact(texts[1]);
            if (playerExact == null) {
                ConfigManager configManager2 = lPXBukkit.getConfigManager();
                Messages messages2 = configManager2.getMessages();
                String prefix = messages2.getPrefix();
                String text2 = String.valueOf(ChatColor.RED);
                String text3 = texts[1];
                String text4 = prefix + text2 + (String) " " + text3 + " not found!";
                ColorUtils.send(sender, text4);
            } else {
                String text5;
                if (texts.length > 2) {
                    text5 = createText(texts);
                } else {
                    ConfigManager configManager3 = lPXBukkit.getConfigManager();
                    Messages messages3 = configManager3.getMessages();
                    text5 = messages3.getKick();
                }
                Logger logger = lPXBukkit.getLogger();
                logger.info(
                        () -> {
                            String name = playerExact.getName();
                            return (String) "Force kicking " + name + "...";
                        });
                String text6 = text5;
                lPXBukkit.kick(playerExact, text6);
            }
        }
    }

    @Override
    public void execute(Object result, String[] texts) {
        CommandSender sender = (CommandSender) result;
        LPXBukkit lPXBukkit = this.lpx;
        runCommand(lPXBukkit, sender, texts);
    }

    @Override
    public List<String> createValues(CommandSender sender, String[] texts) {
        if (texts.length != 2) {
            return List.of();
        }
        ArrayList<String> values = new ArrayList<>();
        String text = texts[1];
        for (Player player : Bukkit.getOnlinePlayers()) {
            if ((!(sender instanceof Player) || ((Player) sender).canSee(player))
                    && player.getName().regionMatches(true, 0, text, 0, text.length())) {
                values.add(player.getName());
            }
        }
        return values;
    }
}
