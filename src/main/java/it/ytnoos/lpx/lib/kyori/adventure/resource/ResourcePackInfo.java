package it.ytnoos.lpx.lib.kyori.adventure.resource;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

@NonExtendable
public interface ResourcePackInfo extends Examinable, ResourcePackInfoLike {

    @NotNull
    static ResourcePackInfo resourcePackInfo(
            @NotNull UUID uUID, @NotNull URI uRI, @NotNull String text) {
        return new ResourcePackInfoImpl(uUID, uRI, text);
    }

    @NotNull
    static ResourcePackInfo.Builder resourcePackInfo() {
        return new ResourcePackInfoImpl.BuilderImpl();
    }

    @NotNull
    UUID id();

    @NotNull
    URI uri();

    @NotNull
    String hash();

    @NotNull
    @Override
    default ResourcePackInfo asResourcePackInfo() {
        return this;
    }

    @NonExtendable
    interface Builder extends AbstractBuilder<ResourcePackInfo>, ResourcePackInfoLike {

        @Contract("_ -> this")
        @NotNull
        ResourcePackInfo.Builder id(@NotNull UUID uUID);

        @Contract("_ -> this")
        @NotNull
        ResourcePackInfo.Builder uri(@NotNull URI uRI);

        @Contract("_ -> this")
        @NotNull
        ResourcePackInfo.Builder hash(@NotNull String text);

        @NotNull
        ResourcePackInfo build();

        @NotNull
        default CompletableFuture<ResourcePackInfo> computeHashAndBuild() {
            return this.computeHashAndBuild(ForkJoinPool.commonPool());
        }

        @NotNull
        CompletableFuture<ResourcePackInfo> computeHashAndBuild(@NotNull Executor executor);

        @NotNull
        @Override
        default ResourcePackInfo asResourcePackInfo() {
            return this.build();
        }
    }
}
