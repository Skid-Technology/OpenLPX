package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.VersionComparison;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ClientVersion {
    V_1_7_2(4),
    V_1_7_10(5),
    V_1_8(47),
    V_1_9(107),
    V_1_9_1(108),
    V_1_9_2(109),
    V_1_9_3(110),
    V_1_10(210),
    V_1_11(315),
    V_1_11_1(316),
    V_1_12(335),
    V_1_12_1(338),
    V_1_12_2(340),
    V_1_13(393),
    V_1_13_1(401),
    V_1_13_2(404),
    V_1_14(477),
    V_1_14_1(480),
    V_1_14_2(485),
    V_1_14_3(490),
    V_1_14_4(498),
    V_1_15(573),
    V_1_15_1(575),
    V_1_15_2(578),
    V_1_16(735),
    V_1_16_1(736),
    V_1_16_2(751),
    V_1_16_3(753),
    V_1_16_4(754),
    V_1_17(755),
    V_1_17_1(756),
    V_1_18(757),
    V_1_18_2(758),
    V_1_19(759),
    V_1_19_1(760),
    V_1_19_3(761),
    V_1_19_4(762),
    V_1_20(763),
    V_1_20_2(764),
    V_1_20_3(765),
    V_1_20_5(766),
    V_1_21(767),
    V_1_21_2(768),
    V_1_21_4(769),
    V_1_21_5(770),
    V_1_21_6(771),
    V_1_21_7(772),
    V_1_21_9(773),
    V_1_21_11(774),
    V_26_1(775),
    V_26_2(776),
    V_26_3(777),
    @Deprecated
    LOWER_THAN_SUPPORTED_VERSIONS(V_1_7_2.protocolVersion - 1, true),
    @Deprecated
    HIGHER_THAN_SUPPORTED_VERSIONS(V_26_3.protocolVersion + 1, true),
    UNKNOWN(-1, true);

    private static final ClientVersion[] VALUES = values();

    private static final ClientVersion[] REVERSED_VALUES;

    private static final int LOWEST_SUPPORTED_PROTOCOL_VERSION;

    private static final int HIGHEST_SUPPORTED_PROTOCOL_VERSION;

    private final int protocolVersion;

    private final String name;

    private ServerVersion serverVersion;

    ClientVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
        this.name = this.name().substring(2).replace("_", ".");
    }

    ClientVersion(int protocolVersion, boolean matches) {
        this.protocolVersion = protocolVersion;
        if (matches) {
            this.name = this.name();
        } else {
            this.name = this.name().substring(2).replace("_", ".");
        }
    }

    public static boolean isPreRelease(int value) {
        return getLatest().protocolVersion <= value || getOldest().protocolVersion >= value;
    }

    public static boolean isRelease(int value) {
        return value <= getLatest().protocolVersion && value >= getOldest().protocolVersion;
    }

    public boolean isPreRelease() {
        return isPreRelease(this.protocolVersion);
    }

    public boolean isRelease() {
        return isRelease(this.protocolVersion);
    }

    public String getReleaseName() {
        return this.name;
    }

    @NotNull
    public static ClientVersion getById(int value) {
        if (value < LOWEST_SUPPORTED_PROTOCOL_VERSION) {
            return getOldest();
        }
        if (value > HIGHEST_SUPPORTED_PROTOCOL_VERSION) {
            return getLatest();
        }
        ClientVersion[] clientVersions = VALUES;
        int value2 = VALUES.length;
        ClientVersion clientVersion;
        for (int index = 0;
                index < value2 && (clientVersion = clientVersions[index]).protocolVersion <= value;
                index++) {
            if (clientVersion.protocolVersion == value) {
                return clientVersion;
            }
        }
        return UNKNOWN;
    }

    public static ClientVersion getLatest() {
        return REVERSED_VALUES[3];
    }

    public static ClientVersion getOldest() {
        return VALUES[0];
    }

    @Deprecated
    public ServerVersion toServerVersion() {
        if (this.serverVersion == null) {
            this.serverVersion = ServerVersion.getById(this.protocolVersion);
        }
        return this.serverVersion;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    public boolean isNewerThan(ClientVersion clientVersion) {
        return this.protocolVersion > clientVersion.protocolVersion;
    }

    public boolean isNewerThanOrEquals(ClientVersion clientVersion) {
        return this.protocolVersion >= clientVersion.protocolVersion;
    }

    public boolean isOlderThan(ClientVersion clientVersion) {
        return this.protocolVersion < clientVersion.protocolVersion;
    }

    public boolean isOlderThanOrEquals(ClientVersion clientVersion) {
        return this.protocolVersion <= clientVersion.protocolVersion;
    }

    public boolean is(
            @NotNull VersionComparison versionComparison, @NotNull ClientVersion clientVersion) {
        switch (versionComparison) {
            case EQUALS:
                return this.protocolVersion == clientVersion.protocolVersion;
            case NEWER_THAN:
                return this.isNewerThan(clientVersion);
            case NEWER_THAN_OR_EQUALS:
                return this.isNewerThanOrEquals(clientVersion);
            case OLDER_THAN:
                return this.isOlderThan(clientVersion);
            case OLDER_THAN_OR_EQUALS:
                return this.isOlderThanOrEquals(clientVersion);
            default:
                return false;
        }
    }

    static {
        List<it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion>
                values2;
        Collections.reverse(values2 = Arrays.asList(values()));
        REVERSED_VALUES = values2.toArray(new ClientVersion[0]);
        LOWEST_SUPPORTED_PROTOCOL_VERSION = LOWER_THAN_SUPPORTED_VERSIONS.protocolVersion + 1;
        HIGHEST_SUPPORTED_PROTOCOL_VERSION = HIGHER_THAN_SUPPORTED_VERSIONS.protocolVersion - 1;
    }
}
