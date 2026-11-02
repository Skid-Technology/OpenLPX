package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PEVersion implements Comparable<PEVersion> {

    private final int major;

    private final int minor;

    private final int patch;

    private final boolean snapshot;

    @Nullable private final String snapshotCommit;

    public PEVersion(
            int major, int minor, int patch, boolean snapshot, @Nullable String snapshotCommit) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.snapshot = snapshot;
        this.snapshotCommit = snapshotCommit;
    }

    public PEVersion(int value, int value2, int value3, @Nullable String text) {
        this(value, value2, value3, text != null, text);
    }

    public PEVersion(int value, int value2, int value3, boolean matches) {
        this(value, value2, value3, matches, null);
    }

    public PEVersion(int value, int value2, int value3) {
        this(value, value2, value3, false);
    }

    @Deprecated
    public PEVersion(int... value) {
        this(value[0], value[1], value[2]);
    }

    @Deprecated
    public PEVersion(@NotNull String text) {
        String[] texts;
        String text2;
        String[] texts2 =
                !(text2 =
                                        (texts = text.replace("-SNAPSHOT", "").split("\\+")).length
                                                        > 0
                                                ? texts[0].replaceFirst(
                                                        "[-.][A-Za-z][A-Za-z0-9._-]*$", "")
                                                : "")
                                .isEmpty()
                        ? text2.split("\\.")
                        : null;
        if (texts.length >= 1
                && texts.length <= 2
                && texts2 != null
                && texts2.length >= 2
                && texts2.length <= 3) {
            this.major = Integer.parseInt(texts2[0]);
            this.minor = Integer.parseInt(texts2[1]);
            this.patch = texts2.length > 2 ? Integer.parseInt(texts2[2]) : 0;
            this.snapshot = text.contains("-SNAPSHOT");
            this.snapshotCommit = texts.length > 1 ? texts[1] : null;
        } else {
            throw new IllegalArgumentException(
                    "Version string must be in the format 'major.minor[.patch][-qualifier][+commit][-SNAPSHOT]', found '"
                            + text
                            + "' instead");
        }
    }

    public static PEVersion fromString(@NotNull String text) {
        return new PEVersion(text);
    }

    public int major() {
        return this.major;
    }

    public int minor() {
        return this.minor;
    }

    public int patch() {
        return this.patch;
    }

    public boolean snapshot() {
        return this.snapshot;
    }

    @Nullable
    public String snapshotCommit() {
        return this.snapshotCommit;
    }

    public int compareTo(@NotNull PEVersion pEVersion) {
        int value;
        if ((value = Integer.compare(this.major, pEVersion.major)) != 0) {
            return value;
        }
        int value2;
        if ((value2 = Integer.compare(this.minor, pEVersion.minor)) != 0) {
            return value2;
        }
        int value3;
        return (value3 = Integer.compare(this.patch, pEVersion.patch)) != 0
                ? value3
                : Boolean.compare(pEVersion.snapshot, this.snapshot);
    }

    @Override
    public boolean equals(@NotNull Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof PEVersion)) {
            return false;
        }
        PEVersion pEVersion = (PEVersion) result;
        return this.major == pEVersion.major
                && this.minor == pEVersion.minor
                && this.patch == pEVersion.patch
                && this.snapshot == pEVersion.snapshot
                && Objects.equals(this.snapshotCommit, pEVersion.snapshotCommit);
    }

    public boolean isNewerThan(@NotNull PEVersion pEVersion) {
        return this.compareTo(pEVersion) > 0;
    }

    public boolean isOlderThan(@NotNull PEVersion pEVersion) {
        return this.compareTo(pEVersion) < 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.major, this.minor, this.patch, this.snapshot, this.snapshotCommit);
    }

    public PEVersion clone() {
        return new PEVersion(
                this.major, this.minor, this.patch, this.snapshot, this.snapshotCommit);
    }

    @Override
    public String toString() {
        return this.major
                + "."
                + this.minor
                + "."
                + this.patch
                + (this.snapshot && this.snapshotCommit != null
                        ? "+" + this.snapshotCommit + "-SNAPSHOT"
                        : "");
    }

    public String toStringWithoutSnapshot() {
        return this.major + "." + this.minor + "." + this.patch;
    }

    @Deprecated
    public int[] asArray() {
        return new int[] {this.major, this.minor, this.patch};
    }
}
