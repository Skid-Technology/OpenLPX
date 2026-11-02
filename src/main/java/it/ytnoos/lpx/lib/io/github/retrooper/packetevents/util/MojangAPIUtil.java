package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.TextureProperty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class MojangAPIUtil {

    public static List<TextureProperty> requestPlayerTextureProperties(UUID uUID) {
        String text = UUIDUtil.toStringWithoutDashes(uUID);
        try {
            ArrayList<TextureProperty> values = new ArrayList<>();
            HttpURLConnection httpURLConnection;
            (httpURLConnection =
                            (HttpURLConnection)
                                    new URL(
                                                    "https://sessionserver.mojang.com/session/minecraft/profile/"
                                                            + text
                                                            + "?unsigned=false")
                                            .openConnection())
                    .setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() != 200) {
                throw new IllegalStateException(
                        "Failed to request texture properties with their UUID "
                                + text
                                + "! Response code: "
                                + httpURLConnection.getResponseCode());
            }
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String text2;
            while ((text2 = bufferedReader.readLine()) != null) {
                stringBuilder.append(text2);
            }
            bufferedReader.close();
            Iterator<JsonElement> iterator2 =
                    AdventureSerializer.serializer()
                            .gson()
                            .serializer()
                            .fromJson(stringBuilder.toString(), JsonObject.class)
                            .get("properties")
                            .getAsJsonArray()
                            .iterator();
            while (iterator2.hasNext()) {
                JsonObject jsonObject;
                String asString =
                        (jsonObject = ((JsonElement) iterator2.next()).getAsJsonObject())
                                .get("name")
                                .getAsString();
                String asString2 = jsonObject.get("value").getAsString();
                String text3 = null;
                if (jsonObject.has("signature")) {
                    text3 = jsonObject.get("signature").getAsString();
                }
                values.add(new TextureProperty(asString, asString2, text3));
            }
            return values;
        } catch (IOException oException) {
            oException.printStackTrace();
            return null;
        }
    }

    public static String requestPlayerName(UUID uUID) {
        String text = UUIDUtil.toStringWithoutDashes(uUID);
        try {
            HttpURLConnection httpURLConnection;
            (httpURLConnection =
                            (HttpURLConnection)
                                    new URL(
                                                    "https://sessionserver.mojang.com/session/minecraft/profile/"
                                                            + text)
                                            .openConnection())
                    .setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() != 200) {
                throw new IllegalStateException(
                        "Failed to request player name with their UUID "
                                + text
                                + "! Response code: "
                                + httpURLConnection.getResponseCode());
            }
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String text2;
            while ((text2 = bufferedReader.readLine()) != null) {
                stringBuilder.append(text2);
            }
            bufferedReader.close();
            return AdventureSerializer.serializer()
                    .gson()
                    .serializer()
                    .fromJson(stringBuilder.toString(), JsonObject.class)
                    .get("name")
                    .getAsString();
        } catch (IOException oException) {
            oException.printStackTrace();
            return null;
        }
    }

    public static UUID requestPlayerUUID(String text) {
        try {
            HttpURLConnection httpURLConnection;
            (httpURLConnection =
                            (HttpURLConnection)
                                    new URL(
                                                    "https://api.mojang.com/users/profiles/minecraft/"
                                                            + text)
                                            .openConnection())
                    .setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() != 200) {
                throw new IllegalStateException(
                        "Failed to request player UUID with their name: "
                                + text
                                + "! Response code: "
                                + httpURLConnection.getResponseCode());
            }
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String text2;
            while ((text2 = bufferedReader.readLine()) != null) {
                stringBuilder.append(text2);
            }
            bufferedReader.close();
            return UUIDUtil.fromStringWithoutDashes(
                    AdventureSerializer.serializer()
                            .gson()
                            .serializer()
                            .fromJson(stringBuilder.toString(), JsonObject.class)
                            .get("id")
                            .getAsString());
        } catch (IOException oException) {
            oException.printStackTrace();
            return null;
        }
    }
}
