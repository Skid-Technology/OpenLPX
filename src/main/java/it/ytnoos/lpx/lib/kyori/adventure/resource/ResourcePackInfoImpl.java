package it.ytnoos.lpx.lib.kyori.adventure.resource;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

final class ResourcePackInfoImpl implements ResourcePackInfo {

    private final UUID id;

    private final URI uri;

    private final String hash;

    ResourcePackInfoImpl(@NotNull UUID uUID, @NotNull URI uRI, @NotNull String text) {
        this.id = Objects.requireNonNull(uUID, "id");
        this.uri = Objects.requireNonNull(uRI, "uri");
        this.hash = Objects.requireNonNull(text, "hash");
    }

    @NotNull
    @Override
    public UUID id() {
        return this.id;
    }

    @NotNull
    @Override
    public URI uri() {
        return this.uri;
    }

    @NotNull
    @Override
    public String hash() {
        return this.hash;
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("id", this.id),
                ExaminableProperty.of("uri", this.uri),
                ExaminableProperty.of("hash", this.hash));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ResourcePackInfoImpl)) {
            return false;
        }
        ResourcePackInfoImpl resourcePackInfoImpl = (ResourcePackInfoImpl) result;
        return this.id.equals(resourcePackInfoImpl.id)
                && this.uri.equals(resourcePackInfoImpl.uri)
                && this.hash.equals(resourcePackInfoImpl.hash);
    }

    @Override
    public int hashCode() {
        int value = this.id.hashCode();
        value = 31 * value + this.uri.hashCode();
        return 31 * value + this.hash.hashCode();
    }

    static CompletableFuture<String> computeHash(URI uRI, Executor executor) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        executor.execute(
                () -> {
                    try {
                        URLConnection uRLConnection;
                        (uRLConnection = uRI.toURL().openConnection())
                                .addRequestProperty(
                                        "User-Agent",
                                        "adventure/"
                                                + ResourcePackInfoImpl.class
                                                        .getPackage()
                                                        .getSpecificationVersion()
                                                + " (pack-fetcher)");
                        InputStream inputStream = uRLConnection.getInputStream();
                        try {
                            MessageDigest instance = MessageDigest.getInstance("SHA-1");
                            byte[] values = new byte[8192];
                            int value;
                            while ((value = inputStream.read(values)) != -1) {
                                instance.update(values, 0, value);
                            }
                            completableFuture.complete(bytesToString(instance.digest()));
                        } catch (Throwable throwable) {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException | NoSuchAlgorithmException exception) {
                        completableFuture.completeExceptionally(exception);
                    }
                });
        return completableFuture;
    }

    static String bytesToString(byte[] values) {
        StringBuilder stringBuilder = new StringBuilder(values.length * 2);
        Formatter formatter = new Formatter(stringBuilder, Locale.ROOT);
        for (int index = 0; index < values.length; index++) {
            formatter.format("%02x", values[index] & 255);
        }
        return stringBuilder.toString();
    }

    static final class BuilderImpl implements ResourcePackInfo.Builder {

        private UUID id;

        private URI uri;

        private String hash;

        @NotNull
        @Override
        public ResourcePackInfo.Builder id(@NotNull UUID uUID) {
            this.id = Objects.requireNonNull(uUID, "id");
            return this;
        }

        @NotNull
        @Override
        public ResourcePackInfo.Builder uri(@NotNull URI uRI) {
            this.uri = Objects.requireNonNull(uRI, "uri");
            if (this.id == null) {
                this.id = UUID.nameUUIDFromBytes(uRI.toString().getBytes(StandardCharsets.UTF_8));
            }
            return this;
        }

        @NotNull
        @Override
        public ResourcePackInfo.Builder hash(@NotNull String text) {
            this.hash = Objects.requireNonNull(text, "hash");
            return this;
        }

        @NotNull
        @Override
        public ResourcePackInfo build() {
            return new ResourcePackInfoImpl(this.id, this.uri, this.hash);
        }

        @NotNull
        @Override
        public CompletableFuture<ResourcePackInfo> computeHashAndBuild(@NotNull Executor executor) {
            CompletableFuture<String> completableFuture =
                    ResourcePackInfoImpl.computeHash(
                            Objects.requireNonNull(this.uri, "uri"), executor);
            return completableFuture.thenApply(
                    result -> {
                        this.hash(result);
                        return this.build();
                    });
        }
    }
}
