package it.ytnoos.lpx.lib.kyori.adventure.identity;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointer;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@NonExtendable
public interface Identity extends Examinable, Identified {

    Pointer<String> NAME = Pointer.pointer(String.class, Key.key("adventure", "name"));

    Pointer<UUID> UUID = Pointer.pointer(UUID.class, Key.key("adventure", "uuid"));

    Pointer<Component> DISPLAY_NAME =
            Pointer.pointer(Component.class, Key.key("adventure", "display_name"));

    Pointer<Locale> LOCALE = Pointer.pointer(Locale.class, Key.key("adventure", "locale"));

    @NotNull
    static Identity nil() {
        return NilIdentity.INSTANCE;
    }

    @NotNull
    static Identity identity(@NotNull UUID uUID) {
        return uUID.equals(NilIdentity.NIL_UUID) ? NilIdentity.INSTANCE : new IdentityImpl(uUID);
    }

    @NotNull
    UUID uuid();

    @NotNull
    @Override
    default Identity identity() {
        return this;
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("uuid", this.uuid()));
    }
}
