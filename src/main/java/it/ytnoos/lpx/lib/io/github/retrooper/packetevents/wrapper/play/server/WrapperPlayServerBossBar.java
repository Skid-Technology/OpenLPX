package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.bossbar.BossBar;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import java.util.EnumSet;
import java.util.UUID;

public class WrapperPlayServerBossBar extends PacketWrapper<WrapperPlayServerBossBar> {

    private UUID uuid;

    private WrapperPlayServerBossBar.Action action;

    private Component title;

    private float health;

    private BossBar.Color color;

    private BossBar.Overlay overlay;

    private EnumSet<BossBar.Flag> flags;

    public WrapperPlayServerBossBar(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerBossBar(UUID uuid, WrapperPlayServerBossBar.Action action2) {
        super(PacketType.Play.Server.BOSS_BAR);
        this.uuid = uuid;
        this.action = action2;
    }

    @Override
    public void read() {
        this.uuid = this.readUUID();
        this.action = this.readEnum(WrapperPlayServerBossBar.Action.class);
        switch (this.action) {
            case ADD:
                this.title = this.readComponent();
                this.health = this.readFloat();
                this.color = this.readEnum(BossBar.Color.class);
                this.overlay = this.readEnum(BossBar.Overlay.class);
                this.flags = this.getFlagsFromBytes(this.readUnsignedByte());
            case REMOVE:
            default:
                break;
            case UPDATE_HEALTH:
                this.health = this.readFloat();
                break;
            case UPDATE_TITLE:
                this.title = this.readComponent();
                break;
            case UPDATE_STYLE:
                this.color = this.readEnum(BossBar.Color.class);
                this.overlay = this.readEnum(BossBar.Overlay.class);
                break;
            case UPDATE_FLAGS:
                this.flags = this.getFlagsFromBytes(this.readUnsignedByte());
        }
    }

    @Override
    public void write() {
        this.writeUUID(this.uuid);
        this.writeEnum(this.action);
        switch (this.action) {
            case ADD:
                this.writeComponent(this.title);
                this.writeFloat(this.health);
                this.writeEnum(this.color);
                this.writeEnum(this.overlay);
                this.writeByte(this.convertFlagsToBytes());
            case REMOVE:
            default:
                break;
            case UPDATE_HEALTH:
                this.writeFloat(this.health);
                break;
            case UPDATE_TITLE:
                this.writeComponent(this.title);
                break;
            case UPDATE_STYLE:
                this.writeEnum(this.color);
                this.writeEnum(this.overlay);
                break;
            case UPDATE_FLAGS:
                this.writeByte(this.convertFlagsToBytes());
        }
    }

    public void copy(WrapperPlayServerBossBar wrapperPlayServerBossBar) {
        this.uuid = wrapperPlayServerBossBar.uuid;
        this.action = wrapperPlayServerBossBar.action;
        this.title = wrapperPlayServerBossBar.title;
        this.health = wrapperPlayServerBossBar.health;
        this.color = wrapperPlayServerBossBar.color;
        this.overlay = wrapperPlayServerBossBar.overlay;
        this.flags = wrapperPlayServerBossBar.flags;
    }

    private EnumSet<BossBar.Flag> getFlagsFromBytes(short value) {
        EnumSet values = EnumSet.noneOf(BossBar.Flag.class);
        if ((value & 1) != 0) {
            values.add(BossBar.Flag.DARKEN_SCREEN);
        }
        if ((value & 2) != 0) {
            values.add(BossBar.Flag.PLAY_BOSS_MUSIC);
        }
        if ((value & 4) != 0) {
            values.add(BossBar.Flag.CREATE_WORLD_FOG);
        }
        return values;
    }

    private byte convertFlagsToBytes() {
        byte value = 0;
        for (BossBar.Flag flag : this.flags) {
            byte value2;
            switch (flag) {
                case DARKEN_SCREEN:
                    value2 = 1;
                    break;
                case PLAY_BOSS_MUSIC:
                    value2 = 2;
                    break;
                case CREATE_WORLD_FOG:
                    value2 = 4;
                    break;
                default:
                    value2 = 0;
            }
            value |= value2;
        }
        return (byte) value;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public WrapperPlayServerBossBar.Action getAction() {
        return this.action;
    }

    public void setAction(WrapperPlayServerBossBar.Action action2) {
        this.action = action2;
    }

    public Component getTitle() {
        return this.title;
    }

    public void setTitle(Component component) {
        this.title = component;
    }

    public float getHealth() {
        return this.health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public BossBar.Color getColor() {
        return this.color;
    }

    public void setColor(BossBar.Color color2) {
        this.color = color2;
    }

    public BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    public void setOverlay(BossBar.Overlay overlay2) {
        this.overlay = overlay2;
    }

    public EnumSet<BossBar.Flag> getFlags() {
        return this.flags;
    }

    public void setFlags(EnumSet<BossBar.Flag> flags) {
        this.flags = flags;
    }

    public enum Action {
        ADD,
        REMOVE,
        UPDATE_HEALTH,
        UPDATE_TITLE,
        UPDATE_STYLE,
        UPDATE_FLAGS
    }
}
