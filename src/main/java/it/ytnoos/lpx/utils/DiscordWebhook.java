package it.ytnoos.lpx.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class DiscordWebhook {

    private final String url;

    private final List<DiscordWebhook.EmbedObject> embeds;

    private String content;

    private String username;

    private String avatarUrl;

    private boolean tts;

    private String json;

    public DiscordWebhook(String url) {
        ArrayList values = new ArrayList();
        this.embeds = values;
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTts(boolean tts) {
        this.tts = tts;
    }

    public void addEmbed(Object result) {
        this.embeds.add((DiscordWebhook.EmbedObject) result);
    }

    public void execute() throws IOException {
        if (this.content == null && this.embeds.isEmpty()) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Set content or add at least one EmbedObject");
            throw illegalArgumentException;
        }
        DiscordWebhook.JSONObject jSONObject = new DiscordWebhook.JSONObject();
        String text = this.content;
        jSONObject.put((String) "content", text);
        String text2 = this.username;
        jSONObject.put((String) "username", text2);
        String text3 = this.avatarUrl;
        jSONObject.put((String) "avatar_url", text3);
        Boolean matches = this.tts;
        jSONObject.put((String) "tts", matches);
        if (!this.embeds.isEmpty()) {
            ArrayList<it.ytnoos.lpx.utils.DiscordWebhook.JSONObject> values = new ArrayList<>();
            for (DiscordWebhook.EmbedObject embedObject : this.embeds) {
                DiscordWebhook.JSONObject jSONObject2 = new DiscordWebhook.JSONObject();
                String title = embedObject.getTitle();
                jSONObject2.put((String) "title", title);
                String description = embedObject.getDescription();
                jSONObject2.put((String) "description", description);
                String url2 = embedObject.getUrl();
                jSONObject2.put((String) "url", url2);
                String timestamp = embedObject.getTimestamp();
                jSONObject2.put((String) "timestamp", timestamp);
                if (embedObject.getColor() != null) {
                    Color color = embedObject.getColor();
                    int value = ((color.getRed() << 8) + color.getGreen() << 8) + color.getBlue();
                    Integer value2 = ((Integer) value);
                    jSONObject2.put((String) "color", value2);
                }
                DiscordWebhook.EmbedObject.Footer footer = embedObject.getFooter();
                DiscordWebhook.EmbedObject.Image image = embedObject.getImage();
                DiscordWebhook.EmbedObject.Thumbnail thumbnail = embedObject.getThumbnail();
                DiscordWebhook.EmbedObject.Author author = embedObject.getAuthor();
                List<DiscordWebhook.EmbedObject.Field> fields = embedObject.getFields();
                if (footer != null) {
                    DiscordWebhook.JSONObject jSONObject3 = new DiscordWebhook.JSONObject();
                    String text4 = footer.getText();
                    jSONObject3.put((String) "text", text4);
                    String iconUrl = footer.getIconUrl();
                    jSONObject3.put((String) "icon_url", iconUrl);
                    jSONObject2.put((String) "footer", jSONObject3);
                }
                if (image != null) {
                    DiscordWebhook.JSONObject jSONObject4 = new DiscordWebhook.JSONObject();
                    String url3 = image.getUrl();
                    jSONObject4.put((String) "url", url3);
                    jSONObject2.put((String) "image", jSONObject4);
                }
                if (thumbnail != null) {
                    DiscordWebhook.JSONObject jSONObject5 = new DiscordWebhook.JSONObject();
                    String url4 = thumbnail.getUrl();
                    jSONObject5.put((String) "url", url4);
                    jSONObject2.put((String) "thumbnail", jSONObject5);
                }
                if (author != null) {
                    DiscordWebhook.JSONObject jSONObject6 = new DiscordWebhook.JSONObject();
                    String name = author.getName();
                    jSONObject6.put((String) "name", name);
                    String url5 = author.getUrl();
                    jSONObject6.put((String) "url", url5);
                    String iconUrl2 = author.getIconUrl();
                    jSONObject6.put((String) "icon_url", iconUrl2);
                    jSONObject2.put((String) "author", jSONObject6);
                }
                ArrayList<it.ytnoos.lpx.utils.DiscordWebhook.JSONObject> values2 =
                        new ArrayList<>();
                for (DiscordWebhook.EmbedObject.Field field : fields) {
                    DiscordWebhook.JSONObject jSONObject7 = new DiscordWebhook.JSONObject();
                    String name2 = field.getName();
                    jSONObject7.put((String) "name", name2);
                    String value3 = field.getValue();
                    jSONObject7.put((String) "value", value3);
                    Boolean inline = field.isInline();
                    jSONObject7.put((String) "inline", inline);
                    values2.add(jSONObject7);
                }
                Object[] results = values2.toArray();
                jSONObject2.put((String) "fields", results);
                values.add(jSONObject2);
            }
            Object[] results2 = values.toArray();
            jSONObject.put((String) "embeds", results2);
        }
        URL uRL = new URL(this.url);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRL.openConnection();
        httpsURLConnection.addRequestProperty("Content-Type", "application/json");
        httpsURLConnection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setRequestMethod("POST");
        OutputStream outputStream = httpsURLConnection.getOutputStream();
        this.json = jSONObject.toString();
        outputStream.write(jSONObject.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        httpsURLConnection.getInputStream().close();
        httpsURLConnection.disconnect();
    }

    public String getJson() {
        return this.json;
    }

    public static class EmbedObject {

        private final List<DiscordWebhook.EmbedObject.Field> fields;

        private String title;

        private String description;

        private String url;

        private Color color;

        private DiscordWebhook.EmbedObject.Footer footer;

        private String timestamp;

        private DiscordWebhook.EmbedObject.Thumbnail thumbnail;

        private DiscordWebhook.EmbedObject.Image image;

        private DiscordWebhook.EmbedObject.Author author;

        public EmbedObject() {
            ArrayList values = new ArrayList();
            this.fields = values;
        }

        public String getTitle() {
            return this.title;
        }

        public DiscordWebhook.EmbedObject setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getDescription() {
            return this.description;
        }

        public DiscordWebhook.EmbedObject setDescription(String text) {
            this.description = text.replace("\n", "\\n").replace("\\_", "_");
            return this;
        }

        public String getUrl() {
            return this.url;
        }

        public DiscordWebhook.EmbedObject setUrl(String url) {
            this.url = url;
            return this;
        }

        public Color getColor() {
            return this.color;
        }

        public DiscordWebhook.EmbedObject setColor(Color color2) {
            this.color = color2;
            return this;
        }

        public DiscordWebhook.EmbedObject.Footer getFooter() {
            return this.footer;
        }

        public String getTimestamp() {
            return this.timestamp;
        }

        public DiscordWebhook.EmbedObject setTimestamp(TemporalAccessor temporalAccessor) {
            this.timestamp = temporalAccessor.toString();
            return this;
        }

        public DiscordWebhook.EmbedObject.Thumbnail getThumbnail() {
            return this.thumbnail;
        }

        public DiscordWebhook.EmbedObject setThumbnail(String text) {
            DiscordWebhook.EmbedObject.Thumbnail thumbnail2 =
                    new DiscordWebhook.EmbedObject.Thumbnail(text);
            this.thumbnail = thumbnail2;
            return this;
        }

        public DiscordWebhook.EmbedObject.Image getImage() {
            return this.image;
        }

        public DiscordWebhook.EmbedObject setImage(String text) {
            DiscordWebhook.EmbedObject.Image image2 = new DiscordWebhook.EmbedObject.Image(text);
            this.image = image2;
            return this;
        }

        public DiscordWebhook.EmbedObject.Author getAuthor() {
            return this.author;
        }

        public List<it.ytnoos.lpx.utils.DiscordWebhook.EmbedObject.Field> getFields() {
            return this.fields;
        }

        public DiscordWebhook.EmbedObject setFooter(String text, String text2) {
            DiscordWebhook.EmbedObject.Footer footer2 =
                    new DiscordWebhook.EmbedObject.Footer(text, text2);
            this.footer = footer2;
            return this;
        }

        public DiscordWebhook.EmbedObject setAuthor(String text, String text2, String text3) {
            DiscordWebhook.EmbedObject.Author author2 =
                    new DiscordWebhook.EmbedObject.Author(text, text2, text3);
            this.author = author2;
            return this;
        }

        public DiscordWebhook.EmbedObject addField(String text, String text2, boolean matches) {
            List<it.ytnoos.lpx.utils.DiscordWebhook.EmbedObject.Field> values = this.fields;
            DiscordWebhook.EmbedObject.Field field =
                    new DiscordWebhook.EmbedObject.Field(text, text2, matches);
            values.add(field);
            return this;
        }

        static class Author {

            private final String name;

            private final String url;

            private final String iconUrl;

            private Author(String name, String url, String iconUrl) {
                this.name = name;
                this.url = url;
                this.iconUrl = iconUrl;
            }

            private String getName() {
                return this.name;
            }

            private String getUrl() {
                return this.url;
            }

            private String getIconUrl() {
                return this.iconUrl;
            }
        }

        static class Field {

            private final String name;

            private final String value;

            private final boolean inline;

            private Field(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            private String getName() {
                return this.name;
            }

            private String getValue() {
                return this.value;
            }

            private boolean isInline() {
                return this.inline;
            }
        }

        static class Footer {

            private final String text;

            private final String iconUrl;

            private Footer(String text, String iconUrl) {
                this.text = text;
                this.iconUrl = iconUrl;
            }

            private String getText() {
                return this.text;
            }

            private String getIconUrl() {
                return this.iconUrl;
            }
        }

        static class Image {

            private final String url;

            private Image(String url) {
                this.url = url;
            }

            private String getUrl() {
                return this.url;
            }
        }

        static class Thumbnail {

            private final String url;

            private Thumbnail(String url) {
                this.url = url;
            }

            private String getUrl() {
                return this.url;
            }
        }
    }

    static class JSONObject {

        private final HashMap<String, Object> map;

        private JSONObject() {
            HashMap entries = new HashMap();
            this.map = entries;
        }

        void put(Object result, Object result2) {
            if (result2 != null) {
                this.map.put((String) result, result2);
            }
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            Set<Entry<String, Object>> values = this.map.entrySet();
            stringBuilder.append("{");
            int value = 0;
            for (Entry entry : values) {
                Object value2 = entry.getValue();
                String text = (String) entry.getKey();
                stringBuilder.append(this.createText(text)).append(":");
                if (value2 instanceof String) {
                    String text2 = String.valueOf(value2);
                    stringBuilder.append(this.createText(text2));
                } else if (value2 instanceof Integer) {
                    stringBuilder.append(Integer.valueOf(String.valueOf(value2)));
                } else if (value2 instanceof Boolean) {
                    stringBuilder.append(value2);
                } else if (value2 instanceof DiscordWebhook.JSONObject) {
                    stringBuilder.append(value2);
                } else if (value2.getClass().isArray()) {
                    stringBuilder.append("[");
                    int length = Array.getLength(value2);
                    for (int index = 0; index < length; index++) {
                        stringBuilder
                                .append(Array.get(value2, index).toString())
                                .append(index != length - 1 ? "," : "");
                    }
                    stringBuilder.append("]");
                }
                value++;
                stringBuilder.append(value == values.size() ? "}" : ",");
            }
            return stringBuilder.toString();
        }

        private String createText(String text) {
            return "\"" + text + "\"";
        }
    }
}
