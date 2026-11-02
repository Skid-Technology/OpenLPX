package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
public class SignTextComponent {

    private final List<Component> messages;

    @Nullable private final List<Component> filteredMessages;

    private final DyeColor color;

    private final boolean glowing;

    public SignTextComponent(
            List<Component> values,
            @Nullable List<Component> values2,
            DyeColor dyeColor,
            boolean glowing) {
        this.messages = Collections.unmodifiableList(values);
        this.filteredMessages = values2 != null ? Collections.unmodifiableList(values2) : null;
        this.color = dyeColor;
        this.glowing = glowing;
    }

    public static SignTextComponent read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readFixedList(PacketWrapper::readComponent, 4);
        List values2 =
                packetWrapper.readOptional(
                        result -> result.readFixedList(PacketWrapper::readComponent, 4));
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        boolean matches = packetWrapper.readBoolean();
        return new SignTextComponent(values, values2, dyeColor, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, SignTextComponent signTextComponent) {
        packetWrapper.writeFixedList(signTextComponent.messages, PacketWrapper::writeComponent);
        packetWrapper.writeOptional(
                signTextComponent.filteredMessages,
                (result, result2) -> result.writeFixedList(result2, PacketWrapper::writeComponent));
        DyeColor.write(packetWrapper, signTextComponent.color);
        packetWrapper.writeBoolean(signTextComponent.glowing);
    }

    public List<Component> getMessages() {
        return this.messages;
    }

    @Nullable
    public List<Component> getFilteredMessages() {
        return this.filteredMessages;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public boolean isGlowing() {
        return this.glowing;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof SignTextComponent)) {
            return false;
        } else {
            SignTextComponent signTextComponent = (SignTextComponent) result;
            if (this.glowing != signTextComponent.glowing) {
                return false;
            } else if (!this.color.equals(signTextComponent.color)) {
                return false;
            } else {
                return !this.messages.equals(signTextComponent.messages)
                        ? false
                        : Objects.equals(this.filteredMessages, signTextComponent.filteredMessages);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.messages, this.filteredMessages, this.color, this.glowing);
    }
}
