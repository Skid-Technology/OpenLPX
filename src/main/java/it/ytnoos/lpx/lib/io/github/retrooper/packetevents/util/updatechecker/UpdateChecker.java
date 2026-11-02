package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.updatechecker;

import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.PEVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Internal
public class UpdateChecker {

    @Internal
    public String checkLatestReleasedVersion() {
        try {
            URLConnection uRLConnection;
            (uRLConnection =
                            new URL(
                                            "https://api.github.com/repos/retrooper/packetevents/releases/latest")
                                    .openConnection())
                    .addRequestProperty("User-Agent", "Mozilla/4.0");
            BufferedReader bufferedReader;
            String text =
                    (bufferedReader =
                                    new BufferedReader(
                                            new InputStreamReader(uRLConnection.getInputStream())))
                            .readLine();
            bufferedReader.close();
            return AdventureSerializer.serializer()
                    .gson()
                    .serializer()
                    .fromJson(text, JsonObject.class)
                    .get("name")
                    .getAsString();
        } catch (IOException oException) {
            throw new IllegalStateException("Failed to parse packetevents version!", oException);
        }
    }

    @Internal
    public UpdateChecker.UpdateCheckerStatus checkForUpdate(
            @Nullable Consumer<PEVersion> consumer) {
        PEVersion version = PacketEvents.getAPI().getVersion();
        PEVersion pEVersion;
        try {
            pEVersion = PEVersion.fromString(this.checkLatestReleasedVersion());
            if (consumer != null) {
                consumer.accept(pEVersion);
            }
        } catch (Exception exception) {
            PacketEvents.getAPI().getLogManager().warn("Failed to check for updates", exception);
            return UpdateChecker.UpdateCheckerStatus.FAILED;
        }
        if (version.isOlderThan(pEVersion)) {
            PacketEvents.getAPI()
                    .getLogManager()
                    .warn(
                            "There is an update available! Your version: "
                                    + version
                                    + " | Latest release: "
                                    + pEVersion);
            return UpdateChecker.UpdateCheckerStatus.OUTDATED;
        } else if (version.isNewerThan(pEVersion)) {
            PacketEvents.getAPI()
                    .getLogManager()
                    .info(
                            "You are running a development build. Your version: "
                                    + version
                                    + " | Latest release: "
                                    + pEVersion);
            return UpdateChecker.UpdateCheckerStatus.PRE_RELEASE;
        } else if (version.equals(pEVersion)) {
            PacketEvents.getAPI()
                    .getLogManager()
                    .info("You are running the latest release: " + version);
            return UpdateChecker.UpdateCheckerStatus.UP_TO_DATE;
        } else {
            PacketEvents.getAPI()
                    .getLogManager()
                    .warn("Failed to check for updates for version " + version);
            return UpdateChecker.UpdateCheckerStatus.FAILED;
        }
    }

    @Internal
    public UpdateChecker.UpdateCheckerStatus checkForUpdate() {
        return this.checkForUpdate(null);
    }

    @Deprecated
    @Internal
    public void handleUpdateCheck(@Nullable Runnable runnable) {
        new Thread(
                        () -> {
                            PacketEvents.getAPI()
                                    .getLogManager()
                                    .info("Checking for updates, please wait...");
                            this.checkForUpdate();
                            if (runnable != null) {
                                runnable.run();
                            }
                        },
                        "packetevents-update-check-thread")
                .start();
    }

    @Internal
    public void handleUpdateCheck(
            @Nullable BiConsumer<PEVersion, UpdateChecker.UpdateCheckerStatus> biConsumer) {
        new Thread(
                        () -> {
                            PacketEvents.getAPI()
                                    .getLogManager()
                                    .info("Checking for updates, please wait...");
                            AtomicReference atomicReference;
                            AtomicReference atomicReference2 =
                                    atomicReference = new AtomicReference();
                            Consumer consumer = atomicReference2::set;
                            UpdateChecker.UpdateCheckerStatus updateCheckerStatus =
                                    this.checkForUpdate(consumer);
                            if (((BiConsumer) biConsumer) != null) {
                                ((BiConsumer) biConsumer)
                                        .accept(
                                                (PEVersion) atomicReference.get(),
                                                updateCheckerStatus);
                            }
                        },
                        "packetevents-update-check-thread")
                .start();
    }

    @Internal
    public void handleUpdateCheck() {
        if (!Boolean.getBoolean("packetevents.disable-update-checker")) {
            this.handleUpdateCheck((Runnable) null);
        }
    }

    public enum UpdateCheckerStatus {
        OUTDATED,
        PRE_RELEASE,
        UP_TO_DATE,
        FAILED
    }
}
