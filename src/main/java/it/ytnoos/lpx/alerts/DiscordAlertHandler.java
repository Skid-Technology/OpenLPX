package it.ytnoos.lpx.alerts;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.DiscordOptions;
import it.ytnoos.lpx.utils.DiscordWebhook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class DiscordAlertHandler {

    private static final Logger log = LogManager.getLogger(DiscordAlertHandler.class);

    private static final Pattern PATTERN =
            Pattern.compile(
                    "(?:https?://)?(?:\\w+\\.)?\\w+\\.\\w+/api(?:/v\\d+)?/webhooks/(\\d+)/([\\w-]+)(?:/(?:\\w+)?)?");

    private final LPXBukkit lpx;

    private Color color;

    private String url;

    public DiscordAlertHandler(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    public void load() {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        DiscordOptions discord = configManager.getDiscord();
        if (discord.isEnabled()) {
            String url2 = discord.getUrl();
            if (url2 != null && !url2.isEmpty()) {
                if (!PATTERN.matcher(url2).matches()) {
                    IllegalArgumentException illegalArgumentException =
                            new IllegalArgumentException(
                                    (String) "Webhook URL is not valid! " + url2);
                    throw illegalArgumentException;
                }
                this.color = Color.decode(discord.getColor());
                this.url = url2;
            }
        }
    }

    public void handleFlagInfos(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfos) {
        if (this.url != null) {
            StringBuilder stringBuilder = new StringBuilder();
            LPXBukkit lPXBukkit = this.lpx;
            ConfigManager configManager = lPXBukkit.getConfigManager();
            DiscordOptions discord = configManager.getDiscord();
            for (String text : (Iterable<String>) ((Iterable) discord.getContent())) {
                stringBuilder.append(text).append("\n");
            }
            lPXBukkit = this.lpx;
            AlertManager alertManager = lPXBukkit.getAlertManager();
            String text2 = stringBuilder.toString();
            String text3 =
                    alertManager
                            .replace(text2, protocolPlayer, check, flagInfos)
                            .replaceAll("§[a-zA-Z0-9]", "")
                            .replace("_", "\\_");
            DiscordWebhook discordWebhook = new DiscordWebhook(this.url);
            DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
            DiscordWebhook.EmbedObject embedObject2 =
                    embedObject.setImage((String) "https://i.stack.imgur.com/Fzh0w.png");
            String username = protocolPlayer.getUsername();
            String text4 = (String) "https://crafthead.net/helm/" + username;
            DiscordWebhook.EmbedObject embedObject3 = embedObject2.setThumbnail(text4);
            Color color2 = this.color;
            DiscordWebhook.EmbedObject embedObject4 = embedObject3.setColor(color2);
            DiscordWebhook.EmbedObject embedObject5 = embedObject4.setTitle((String) "**LPX**");
            DiscordWebhook.EmbedObject embedObject6 = embedObject5.setDescription(text3);
            Instant instant = Instant.now();
            DiscordWebhook.EmbedObject embedObject7 = embedObject6.setTimestamp(instant);
            discordWebhook.addEmbed(embedObject7);
            try {
                discordWebhook.execute();
            } catch (IOException oException) {
                if (oException.getMessage() == null || !oException.getMessage().contains("429")) {
                    java.util.logging.Logger logger = this.lpx.getLogger();
                    Level level = Level.WARNING;
                    String message = oException.getMessage();
                    String json = discordWebhook.getJson();
                    logger.log(
                            level,
                            (String) "Unable to send alert to Discord due to IOException: "
                                    + message
                                    + (String) ", Payload: "
                                    + json,
                            oException);
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                java.util.logging.Logger logger2 = this.lpx.getLogger();
                Level level2 = Level.WARNING;
                String message2 = illegalArgumentException.getMessage();
                String json2 = discordWebhook.getJson();
                logger2.log(
                        level2,
                        (String) "Unable to send alert to Discord: "
                                + message2
                                + (String) ", Payload: "
                                + json2,
                        illegalArgumentException);
            } catch (Exception exception) {
                java.util.logging.Logger logger3 = this.lpx.getLogger();
                Level level3 = Level.WARNING;
                String json3 = discordWebhook.getJson();
                logger3.log(
                        level3,
                        (String) "An unexpected error occurred while trying to send Discord alert: "
                                + json3,
                        exception);
            }
        }
    }
}
