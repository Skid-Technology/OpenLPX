package it.ytnoos.lpx.lib.kyori.adventure.resource;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@NonExtendable
public interface ResourcePackRequest extends Examinable, ResourcePackRequestLike {

    @NotNull
    static ResourcePackRequest addingRequest(
            @NotNull ResourcePackInfoLike resourcePackInfoLike,
            @NotNull ResourcePackInfoLike... resourcePackInfoLike2) {
        return resourcePackRequest()
                .packs(resourcePackInfoLike, resourcePackInfoLike2)
                .replace(false)
                .build();
    }

    @NotNull
    static ResourcePackRequest.Builder resourcePackRequest() {
        return new ResourcePackRequestImpl.BuilderImpl();
    }

    @NotNull
    static ResourcePackRequest.Builder resourcePackRequest(
            @NotNull ResourcePackRequest resourcePackRequest2) {
        return new ResourcePackRequestImpl.BuilderImpl(
                Objects.requireNonNull(resourcePackRequest2, "existing"));
    }

    @NotNull
    List<ResourcePackInfo> packs();

    @NotNull
    ResourcePackRequest packs(@NotNull Iterable<? extends ResourcePackInfoLike> iterable);

    @NotNull
    ResourcePackCallback callback();

    @NotNull
    ResourcePackRequest callback(@NotNull ResourcePackCallback resourcePackCallback);

    boolean replace();

    @NotNull
    ResourcePackRequest replace(boolean matches);

    boolean required();

    @Nullable
    Component prompt();

    @NotNull
    @Override
    default ResourcePackRequest asResourcePackRequest() {
        return this;
    }

    @NonExtendable
    interface Builder extends AbstractBuilder<ResourcePackRequest>, ResourcePackRequestLike {

        @Contract("_, _ -> this")
        @NotNull
        ResourcePackRequest.Builder packs(
                @NotNull ResourcePackInfoLike resourcePackInfoLike,
                @NotNull ResourcePackInfoLike... resourcePackInfoLike2);

        @Contract("_ -> this")
        @NotNull
        ResourcePackRequest.Builder packs(
                @NotNull Iterable<? extends ResourcePackInfoLike> iterable);

        @Contract("_ -> this")
        @NotNull
        ResourcePackRequest.Builder callback(@NotNull ResourcePackCallback resourcePackCallback);

        @Contract("_ -> this")
        @NotNull
        ResourcePackRequest.Builder replace(boolean matches);

        @Contract("_ -> this")
        @NotNull
        ResourcePackRequest.Builder required(boolean matches);

        @Contract("_ -> this")
        @NotNull
        ResourcePackRequest.Builder prompt(@Nullable Component component);

        @NotNull
        @Override
        default ResourcePackRequest asResourcePackRequest() {
            return this.build();
        }
    }
}
