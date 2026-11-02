package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Arrays;

@Internal
public class VersionMapper {

    private final ClientVersion[] versions;

    private final ClientVersion[] reversedVersions;

    public VersionMapper(ClientVersion... clientVersion) {
        this.versions = (ClientVersion[]) clientVersion.clone();
        Arrays.sort(this.versions);
        this.reversedVersions = new ClientVersion[this.versions.length];
        int value = this.versions.length - 1;
        for (int index = 0; value >= 0; index++) {
            this.reversedVersions[index] = this.versions[value];
            value--;
        }
    }

    public VersionMapper withExtra(ClientVersion clientVersion) {
        if (Arrays.binarySearch(this.versions, clientVersion) >= 0) {
            return this;
        }
        ClientVersion[] clientVersions;
        ClientVersion[] clientVersions2 =
                clientVersions = Arrays.copyOf(this.versions, this.versions.length + 1);
        clientVersions2[clientVersions2.length - 1] = clientVersion;
        return new VersionMapper(clientVersions);
    }

    public ClientVersion[] getVersions() {
        return this.versions;
    }

    public ClientVersion[] getReversedVersions() {
        return this.reversedVersions;
    }

    public int getIndex(ClientVersion clientVersion) {
        int value = this.reversedVersions.length - 1;
        ClientVersion[] clientVersions = this.reversedVersions;
        int value2 = this.reversedVersions.length;
        for (int index = 0; index < value2; index++) {
            ClientVersion clientVersion2 = clientVersions[index];
            if (clientVersion.isNewerThanOrEquals(clientVersion2)) {
                return value;
            }
            value--;
        }
        return 0;
    }

    public int size() {
        return this.versions.length;
    }
}
