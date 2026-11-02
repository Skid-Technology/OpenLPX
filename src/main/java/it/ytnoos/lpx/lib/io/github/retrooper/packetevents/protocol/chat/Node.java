package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class Node {

    public static final byte TYPE_MASK = 3;

    public static final byte TYPE_ROOT = 0;

    public static final byte TYPE_LITERAL = 1;

    public static final byte TYPE_ARGUMENT = 2;

    public static final byte FLAG_MASK = -4;

    public static final byte FLAG_EXECUTABLE = 4;

    public static final byte FLAG_REDIRECT = 8;

    public static final byte FLAG_CUSTOM_SUGGESTIONS = 16;

    public static final byte FLAG_RESTRICTED = 32;

    private byte flags;

    private List<Integer> children;

    private int redirectNodeIndex;

    private Optional<String> name;

    private Optional<Parsers.Parser> parser;

    private Optional<List<Object>> properties;

    private Optional<ResourceLocation> suggestionsType;

    public Node(
            byte value,
            List<Integer> values,
            int value2,
            @Nullable String text,
            @Nullable Integer value3,
            @Nullable List<Object> values2,
            @Nullable ResourceLocation resourceLocation) {
        this(
                value,
                values,
                value2,
                text,
                value3 == null
                        ? null
                        : Parsers.getById(
                                PacketEvents.getAPI()
                                        .getServerManager()
                                        .getVersion()
                                        .toClientVersion(),
                                value3),
                values2,
                resourceLocation);
    }

    public Node(
            byte flags,
            List<Integer> children,
            int redirectNodeIndex,
            @Nullable String text,
            @Nullable Parsers.Parser parser2,
            @Nullable List<Object> values2,
            @Nullable ResourceLocation resourceLocation) {
        this.flags = flags;
        this.children = children;
        this.redirectNodeIndex = redirectNodeIndex;
        this.name = Optional.ofNullable(text);
        this.parser = Optional.ofNullable(parser2);
        this.properties = Optional.ofNullable(values2);
        this.suggestionsType = Optional.ofNullable(resourceLocation);
    }

    public byte getFlags() {
        return this.flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public List<Integer> getChildren() {
        return this.children;
    }

    public void setChildren(List<Integer> children) {
        this.children = children;
    }

    public int getRedirectNodeIndex() {
        return this.redirectNodeIndex;
    }

    public void setRedirectNodeIndex(int redirectNodeIndex) {
        this.redirectNodeIndex = redirectNodeIndex;
    }

    public Optional<String> getName() {
        return this.name;
    }

    public void setName(Optional<String> optional) {
        this.name = optional;
    }

    public Optional<Parsers.Parser> getParser() {
        return this.parser;
    }

    public void setParser(Optional<Parsers.Parser> optional) {
        this.parser = optional;
    }

    public Optional<Integer> getParserID() {
        return this.parser.map(
                result ->
                        result.getId(
                                PacketEvents.getAPI()
                                        .getServerManager()
                                        .getVersion()
                                        .toClientVersion()));
    }

    public void setParserID(Optional<Integer> optional) {
        this.parser =
                optional.map(
                        result ->
                                Parsers.getById(
                                        PacketEvents.getAPI()
                                                .getServerManager()
                                                .getVersion()
                                                .toClientVersion(),
                                        result));
    }

    public Optional<List<Object>> getProperties() {
        return this.properties;
    }

    public void setProperties(Optional<List<Object>> optional) {
        this.properties = optional;
    }

    public Optional<ResourceLocation> getSuggestionsType() {
        return this.suggestionsType;
    }

    public void setSuggestionsType(Optional<ResourceLocation> optional) {
        this.suggestionsType = optional;
    }
}
