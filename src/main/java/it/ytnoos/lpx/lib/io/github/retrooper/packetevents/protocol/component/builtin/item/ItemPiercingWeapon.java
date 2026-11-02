package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemPiercingWeapon {

    private boolean dealsKnockback;

    private boolean dismounts;

    @Nullable private Sound sound;

    @Nullable private Sound hitSound;

    public ItemPiercingWeapon(
            boolean dealsKnockback,
            boolean dismounts,
            @Nullable Sound sound2,
            @Nullable Sound sound3) {
        this.dealsKnockback = dealsKnockback;
        this.dismounts = dismounts;
        this.sound = sound2;
        this.hitSound = sound3;
    }

    public static ItemPiercingWeapon read(PacketWrapper<?> packetWrapper) {
        boolean matches = packetWrapper.readBoolean();
        boolean matches2 = packetWrapper.readBoolean();
        Sound sound = packetWrapper.readOptional(Sound::read);
        Sound sound2 = packetWrapper.readOptional(Sound::read);
        return new ItemPiercingWeapon(matches, matches2, sound, sound2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemPiercingWeapon itemPiercingWeapon) {
        packetWrapper.writeBoolean(itemPiercingWeapon.dealsKnockback);
        packetWrapper.writeBoolean(itemPiercingWeapon.dismounts);
        packetWrapper.writeOptional(itemPiercingWeapon.sound, Sound::write);
        packetWrapper.writeOptional(itemPiercingWeapon.hitSound, Sound::write);
    }

    public boolean isDealsKnockback() {
        return this.dealsKnockback;
    }

    public void setDealsKnockback(boolean dealsKnockback) {
        this.dealsKnockback = dealsKnockback;
    }

    public boolean isDismounts() {
        return this.dismounts;
    }

    public void setDismounts(boolean dismounts) {
        this.dismounts = dismounts;
    }

    @Nullable
    public Sound getSound() {
        return this.sound;
    }

    public void setSound(@Nullable Sound sound2) {
        this.sound = sound2;
    }

    @Nullable
    public Sound getHitSound() {
        return this.hitSound;
    }

    public void setHitSound(@Nullable Sound sound) {
        this.hitSound = sound;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemPiercingWeapon itemPiercingWeapon = (ItemPiercingWeapon) result;
            if (this.dealsKnockback != itemPiercingWeapon.dealsKnockback) {
                return false;
            } else if (this.dismounts != itemPiercingWeapon.dismounts) {
                return false;
            } else {
                return !Objects.equals(this.sound, itemPiercingWeapon.sound)
                        ? false
                        : Objects.equals(this.hitSound, itemPiercingWeapon.hitSound);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dealsKnockback, this.dismounts, this.sound, this.hitSound);
    }
}
