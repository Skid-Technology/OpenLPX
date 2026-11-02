package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.settings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.InjectEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLimiter;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.TimeStampMode;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PacketEventsSettings {

    private TimeStampMode timestampMode = TimeStampMode.MILLIS;

    private boolean defaultReencode = true;

    private boolean checkForUpdates = true;

    private boolean downsampleColors = false;

    private boolean debugEnabled = false;

    private boolean fullStackTraceEnabled = false;

    private boolean kickOnPacketExceptionEnabled = true;

    private boolean ignoreFailures = false;

    private boolean kickIfTerminated = true;

    private int maxItemDepth = 3;

    private int maxItemsPerPacket = 1458;

    private int maxPayloadMultiplier = 1;

    private int maxUpdateSignLineLength = 384;

    private int maxReceivedNbtElementsPerPacket = Integer.MAX_VALUE;

    private int maxReceivedNbtDepth = NBTLimiter.DEFAULT_MAX_DEPTH;

    private Map<String, InjectEvent> injectEvents = new HashMap<>();

    private BiConsumer<String, Throwable> packetExceptionHandler;

    private Function<String, InputStream> resourceProvider =
            result -> PacketEventsSettings.class.getClassLoader().getResourceAsStream(result);

    @Internal
    public PacketEventsSettings timeStampMode(TimeStampMode timeStampMode2) {
        this.timestampMode = timeStampMode2;
        return this;
    }

    @Internal
    public PacketEventsSettings reEncodeByDefault(boolean defaultReencode) {
        this.defaultReencode = defaultReencode;
        return this;
    }

    @Internal
    public PacketEventsSettings checkForUpdates(boolean checkForUpdates) {
        this.checkForUpdates = checkForUpdates;
        return this;
    }

    @Internal
    public PacketEventsSettings downsampleColors(boolean downsampleColors) {
        this.downsampleColors = downsampleColors;
        return this;
    }

    @Deprecated
    public PacketEventsSettings bStats(boolean matches) {
        return this;
    }

    @Internal
    public PacketEventsSettings debug(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
        return this;
    }

    @Internal
    public PacketEventsSettings fullStackTrace(boolean fullStackTraceEnabled) {
        this.fullStackTraceEnabled = fullStackTraceEnabled;
        return this;
    }

    @Internal
    public PacketEventsSettings kickOnPacketException(boolean kickOnPacketExceptionEnabled) {
        this.kickOnPacketExceptionEnabled = kickOnPacketExceptionEnabled;
        return this;
    }

    public PacketEventsSettings ignoreFailures(boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
        return this;
    }

    @Internal
    public PacketEventsSettings kickIfTerminated(boolean kickIfTerminated) {
        this.kickIfTerminated = kickIfTerminated;
        return this;
    }

    @Internal
    public PacketEventsSettings maxItemDepth(int maxItemDepth) {
        this.maxItemDepth = maxItemDepth;
        return this;
    }

    public PacketEventsSettings maxItemsPerPacket(int maxItemsPerPacket) {
        this.maxItemsPerPacket = maxItemsPerPacket;
        return this;
    }

    @Internal
    public PacketEventsSettings maxPayloadMultiplier(int maxPayloadMultiplier) {
        this.maxPayloadMultiplier = maxPayloadMultiplier;
        return this;
    }

    public PacketEventsSettings maxUpdateSignLineLength(int maxUpdateSignLineLength) {
        this.maxUpdateSignLineLength = maxUpdateSignLineLength;
        return this;
    }

    @Internal
    public PacketEventsSettings maxReceivedNbtElementsPerPacket(
            int maxReceivedNbtElementsPerPacket) {
        this.maxReceivedNbtElementsPerPacket = maxReceivedNbtElementsPerPacket;
        return this;
    }

    @Internal
    public PacketEventsSettings maxReceivedNbtDepth(int maxReceivedNbtDepth) {
        this.maxReceivedNbtDepth = maxReceivedNbtDepth;
        return this;
    }

    @Internal
    public PacketEventsSettings customResourceProvider(Function<String, InputStream> function) {
        this.resourceProvider = function;
        return this;
    }

    @Internal
    public PacketEventsSettings addInjectEvent(String text, InjectEvent injectEvent) {
        this.injectEvents.put(text, injectEvent);
        return this;
    }

    @Internal
    public PacketEventsSettings removeInjectEvent(String text) {
        this.injectEvents.remove(text);
        return this;
    }

    @Internal
    public List<InjectEvent> getInjectEvents() {
        return new ArrayList<>(this.injectEvents.values());
    }

    @Internal
    public PacketEventsSettings packetExceptionHandler(BiConsumer<String, Throwable> biConsumer) {
        this.packetExceptionHandler = biConsumer;
        return this;
    }

    public boolean reEncodeByDefault() {
        return this.defaultReencode;
    }

    public boolean shouldCheckForUpdates() {
        return this.checkForUpdates;
    }

    public boolean shouldDownsampleColors() {
        return this.downsampleColors;
    }

    public boolean shouldIgnoreFailures() {
        return this.ignoreFailures;
    }

    @Deprecated
    public boolean isbStatsEnabled() {
        return true;
    }

    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    public boolean isFullStackTraceEnabled() {
        return this.fullStackTraceEnabled;
    }

    public boolean isKickOnPacketExceptionEnabled() {
        return this.kickOnPacketExceptionEnabled;
    }

    public boolean isKickIfTerminated() {
        return this.kickIfTerminated;
    }

    public int getMaxItemDepth() {
        return this.maxItemDepth;
    }

    public int getMaxItemsPerPacket() {
        return this.maxItemsPerPacket;
    }

    public int getMaxPayloadMultiplier() {
        return this.maxPayloadMultiplier;
    }

    public int getMaxUpdateSignLineLength() {
        return this.maxUpdateSignLineLength;
    }

    public int getMaxReceivedNbtElementsPerPacket() {
        return this.maxReceivedNbtElementsPerPacket;
    }

    public int getMaxReceivedNbtDepth() {
        return this.maxReceivedNbtDepth;
    }

    public Function<String, InputStream> getResourceProvider() {
        return this.resourceProvider;
    }

    public BiConsumer<String, Throwable> getPacketExceptionHandler() {
        return this.packetExceptionHandler;
    }

    public TimeStampMode getTimeStampMode() {
        return this.timestampMode;
    }
}
