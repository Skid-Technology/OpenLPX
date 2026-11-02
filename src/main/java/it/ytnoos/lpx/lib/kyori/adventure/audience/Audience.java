package it.ytnoos.lpx.lib.kyori.adventure.audience;

import it.ytnoos.lpx.lib.kyori.adventure.bossbar.BossBar;
import it.ytnoos.lpx.lib.kyori.adventure.chat.ChatType;
import it.ytnoos.lpx.lib.kyori.adventure.chat.SignedMessage;
import it.ytnoos.lpx.lib.kyori.adventure.dialog.DialogLike;
import it.ytnoos.lpx.lib.kyori.adventure.identity.Identified;
import it.ytnoos.lpx.lib.kyori.adventure.identity.Identity;
import it.ytnoos.lpx.lib.kyori.adventure.inventory.Book;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.resource.ResourcePackInfo;
import it.ytnoos.lpx.lib.kyori.adventure.resource.ResourcePackInfoLike;
import it.ytnoos.lpx.lib.kyori.adventure.resource.ResourcePackRequest;
import it.ytnoos.lpx.lib.kyori.adventure.resource.ResourcePackRequestLike;
import it.ytnoos.lpx.lib.kyori.adventure.sound.Sound;
import it.ytnoos.lpx.lib.kyori.adventure.sound.SoundStop;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.title.Title;
import it.ytnoos.lpx.lib.kyori.adventure.title.TitlePart;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;

public interface Audience extends Pointered {

    @NotNull
    static Audience empty() {
        return EmptyAudience.INSTANCE;
    }

    @NotNull
    static Audience audience(@NotNull Audience... audience2) {
        int value;
        if ((value = audience2.length) == 0) {
            return empty();
        } else {
            return value == 1 ? audience2[0] : audience(Arrays.asList(audience2));
        }
    }

    @NotNull
    static ForwardingAudience audience(@NotNull Iterable<? extends Audience> iterable) {
        return () -> ((Iterable) iterable);
    }

    @NotNull
    static Collector<? super Audience, ?, ForwardingAudience> toAudience() {
        return Audiences.COLLECTOR;
    }

    @NotNull
    default Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
        return predicate.test(this) ? this : empty();
    }

    default void forEachAudience(@NotNull Consumer<? super Audience> consumer) {
        consumer.accept(this);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull ComponentLike componentLike) {
        this.sendMessage(componentLike.asComponent());
    }

    default void sendMessage(@NotNull Component component) {
        this.sendMessage(component, MessageType.SYSTEM);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ScheduledForRemoval(inVersion = "5.0.0")
    default void sendMessage(
            @NotNull ComponentLike componentLike, @NotNull MessageType messageType) {
        this.sendMessage(componentLike.asComponent(), messageType);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ScheduledForRemoval(inVersion = "5.0.0")
    default void sendMessage(@NotNull Component component, @NotNull MessageType messageType) {
        this.sendMessage(Identity.nil(), component, messageType);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull Identified identified, @NotNull ComponentLike componentLike) {
        this.sendMessage(identified, componentLike.asComponent());
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull Identity identity, @NotNull ComponentLike componentLike) {
        this.sendMessage(identity, componentLike.asComponent());
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull Identified identified, @NotNull Component component) {
        this.sendMessage(identified, component, MessageType.CHAT);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull Identity identity, @NotNull Component component) {
        this.sendMessage(identity, component, MessageType.CHAT);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ScheduledForRemoval(inVersion = "5.0.0")
    default void sendMessage(
            @NotNull Identified identified,
            @NotNull ComponentLike componentLike,
            @NotNull MessageType messageType) {
        this.sendMessage(identified, componentLike.asComponent(), messageType);
    }

    @Deprecated
    @ForwardingAudienceOverrideNotRequired
    @ScheduledForRemoval(inVersion = "5.0.0")
    default void sendMessage(
            @NotNull Identity identity,
            @NotNull ComponentLike componentLike,
            @NotNull MessageType messageType) {
        this.sendMessage(identity, componentLike.asComponent(), messageType);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    default void sendMessage(
            @NotNull Identified identified,
            @NotNull Component component,
            @NotNull MessageType messageType) {
        this.sendMessage(identified.identity(), component, messageType);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    default void sendMessage(
            @NotNull Identity identity,
            @NotNull Component component,
            @NotNull MessageType messageType) {}

    default void sendMessage(@NotNull Component component, ChatType.@NotNull Bound bound) {
        this.sendMessage(component, MessageType.CHAT);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull ComponentLike componentLike, ChatType.@NotNull Bound bound) {
        this.sendMessage(componentLike.asComponent(), bound);
    }

    default void sendMessage(@NotNull SignedMessage signedMessage, ChatType.@NotNull Bound bound) {
        Component component =
                signedMessage.unsignedContent() != null
                        ? signedMessage.unsignedContent()
                        : Component.text(signedMessage.message());
        if (signedMessage.isSystem()) {
            this.sendMessage(component);
        } else {
            this.sendMessage(signedMessage.identity(), component, MessageType.CHAT);
        }
    }

    @ForwardingAudienceOverrideNotRequired
    default void deleteMessage(@NotNull SignedMessage signedMessage) {
        if (signedMessage.canDelete()) {
            this.deleteMessage(Objects.requireNonNull(signedMessage.signature()));
        }
    }

    default void deleteMessage(SignedMessage.@NotNull Signature signature) {}

    @ForwardingAudienceOverrideNotRequired
    default void sendActionBar(@NotNull ComponentLike componentLike) {
        this.sendActionBar(componentLike.asComponent());
    }

    default void sendActionBar(@NotNull Component component) {}

    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListHeader(@NotNull ComponentLike componentLike) {
        this.sendPlayerListHeader(componentLike.asComponent());
    }

    default void sendPlayerListHeader(@NotNull Component component) {
        this.sendPlayerListHeaderAndFooter(component, Component.empty());
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListFooter(@NotNull ComponentLike componentLike) {
        this.sendPlayerListFooter(componentLike.asComponent());
    }

    default void sendPlayerListFooter(@NotNull Component component) {
        this.sendPlayerListHeaderAndFooter(Component.empty(), component);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListHeaderAndFooter(
            @NotNull ComponentLike componentLike, @NotNull ComponentLike componentLike2) {
        this.sendPlayerListHeaderAndFooter(
                componentLike.asComponent(), componentLike2.asComponent());
    }

    default void sendPlayerListHeaderAndFooter(
            @NotNull Component component, @NotNull Component component2) {}

    @ForwardingAudienceOverrideNotRequired
    default void showTitle(@NotNull Title title2) {
        Title.Times times2;
        if ((times2 = title2.times()) != null) {
            this.sendTitlePart(TitlePart.TIMES, times2);
        }
        this.sendTitlePart(TitlePart.SUBTITLE, title2.subtitle());
        this.sendTitlePart(TitlePart.TITLE, title2.title());
    }

    default <T> void sendTitlePart(@NotNull TitlePart<T> titlePart, @NotNull T t) {}

    default void clearTitle() {}

    default void resetTitle() {}

    default void showBossBar(@NotNull BossBar bossBar) {}

    default void hideBossBar(@NotNull BossBar bossBar) {}

    default void playSound(@NotNull Sound sound) {}

    default void playSound(@NotNull Sound sound, double value, double value2, double value3) {}

    default void playSound(@NotNull Sound sound, Sound.@NotNull Emitter emitter) {}

    @ForwardingAudienceOverrideNotRequired
    default void stopSound(@NotNull Sound sound) {
        this.stopSound(Objects.requireNonNull(sound, "sound").asStop());
    }

    default void stopSound(@NotNull SoundStop soundStop) {}

    @ForwardingAudienceOverrideNotRequired
    default void openBook(Book.@NotNull Builder builder) {
        this.openBook(builder.build());
    }

    default void openBook(@NotNull Book book) {}

    @ForwardingAudienceOverrideNotRequired
    default void sendResourcePacks(
            @NotNull ResourcePackInfoLike resourcePackInfoLike,
            @NotNull ResourcePackInfoLike... resourcePackInfoLike2) {
        this.sendResourcePacks(
                ResourcePackRequest.addingRequest(resourcePackInfoLike, resourcePackInfoLike2));
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendResourcePacks(@NotNull ResourcePackRequestLike resourcePackRequestLike) {
        this.sendResourcePacks(resourcePackRequestLike.asResourcePackRequest());
    }

    default void sendResourcePacks(@NotNull ResourcePackRequest resourcePackRequest) {}

    @ForwardingAudienceOverrideNotRequired
    default void removeResourcePacks(@NotNull ResourcePackRequestLike resourcePackRequestLike) {
        this.removeResourcePacks(resourcePackRequestLike.asResourcePackRequest());
    }

    @ForwardingAudienceOverrideNotRequired
    default void removeResourcePacks(@NotNull ResourcePackRequest resourcePackRequest) {
        List<ResourcePackInfo> values;
        if ((values = resourcePackRequest.packs()).size() == 1) {
            this.removeResourcePacks(((ResourcePackInfo) values.get(0)).id());
        } else if (values.isEmpty()) {
            return;
        }
        UUID[] uUIDs = new UUID[values.size() - 1];
        for (int index = 0; index < uUIDs.length; index++) {
            uUIDs[index] = ((ResourcePackInfo) values.get(index + 1)).id();
        }
        this.removeResourcePacks(((ResourcePackInfo) values.get(0)).id(), uUIDs);
    }

    @ForwardingAudienceOverrideNotRequired
    default void removeResourcePacks(
            @NotNull ResourcePackInfoLike resourcePackInfoLike,
            @NotNull ResourcePackInfoLike... resourcePackInfoLike2) {
        UUID[] uUIDs = new UUID[resourcePackInfoLike2.length];
        for (int index = 0; index < resourcePackInfoLike2.length; index++) {
            uUIDs[index] = resourcePackInfoLike2[index].asResourcePackInfo().id();
        }
        this.removeResourcePacks(resourcePackInfoLike.asResourcePackInfo().id(), uUIDs);
    }

    default void removeResourcePacks(@NotNull Iterable<UUID> iterable) {
        Iterator<UUID> iterator2;
        if ((iterator2 = iterable.iterator()).hasNext()) {
            UUID uUID = (UUID) iterator2.next();
            UUID[] uUIDs;
            if (!iterator2.hasNext()) {
                uUIDs = new UUID[0];
            } else if (iterable instanceof Collection) {
                uUIDs = new UUID[((Collection) iterable).size() - 1];
                for (int index = 0; index < uUIDs.length; index++) {
                    uUIDs[index] = (UUID) iterator2.next();
                }
            } else {
                ArrayList<UUID> values = new ArrayList<>();
                while (iterator2.hasNext()) {
                    values.add((UUID) iterator2.next());
                }
                uUIDs = values.toArray(new UUID[0]);
            }
            this.removeResourcePacks(uUID, uUIDs);
        }
    }

    default void removeResourcePacks(@NotNull UUID uUID, @NotNull UUID... uUID2) {}

    default void clearResourcePacks() {}

    default void showDialog(@NotNull DialogLike dialogLike) {}

    default void closeDialog() {}
}
