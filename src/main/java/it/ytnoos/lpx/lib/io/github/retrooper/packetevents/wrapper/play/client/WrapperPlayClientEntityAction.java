package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayClientEntityAction extends PacketWrapper<WrapperPlayClientEntityAction> {

    private int entityID;

    private WrapperPlayClientEntityAction.Action action;

    private int jumpBoost;

    public WrapperPlayClientEntityAction(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientEntityAction(
            int entityID, WrapperPlayClientEntityAction.Action action2, int jumpBoost) {
        super(PacketType.Play.Client.ENTITY_ACTION);
        this.entityID = entityID;
        this.action = action2;
        this.jumpBoost = jumpBoost;
    }

    @Override
    public void read() {
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8);
        this.entityID = newerThanOrEquals ? this.readVarInt() : this.readInt();
        int value = newerThanOrEquals ? this.readVarInt() : this.readByte();
        this.action = WrapperPlayClientEntityAction.Action.getById(this.serverVersion, value);
        this.jumpBoost = newerThanOrEquals ? this.readVarInt() : this.readInt();
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeVarInt(this.entityID);
            int id = this.action.getId(this.serverVersion);
            this.writeVarInt(id);
            this.writeVarInt(this.jumpBoost);
        } else {
            this.writeInt(this.entityID);
            int id2 = this.action.getId(this.serverVersion);
            this.writeByte(id2);
            this.writeInt(this.jumpBoost);
        }
    }

    public void copy(WrapperPlayClientEntityAction wrapperPlayClientEntityAction) {
        this.entityID = wrapperPlayClientEntityAction.entityID;
        this.action = wrapperPlayClientEntityAction.action;
        this.jumpBoost = wrapperPlayClientEntityAction.jumpBoost;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public WrapperPlayClientEntityAction.Action getAction() {
        return this.action;
    }

    public void setAction(WrapperPlayClientEntityAction.Action action2) {
        this.action = action2;
    }

    public int getJumpBoost() {
        return this.jumpBoost;
    }

    public void setJumpBoost(int jumpBoost) {
        this.jumpBoost = jumpBoost;
    }

    public enum Action {
        @Obsolete
        START_SNEAKING {

            @Override
            public int getId(ServerVersion serverVersion) {
                return serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)
                        ? -1
                        : (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)
                                ? 1
                                : this.ordinal());
            }
        },
        @Obsolete
        STOP_SNEAKING {

            @Override
            public int getId(ServerVersion serverVersion) {
                return serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)
                        ? -1
                        : (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)
                                ? 2
                                : this.ordinal());
            }
        },
        LEAVE_BED {

            @Override
            public int getId(ServerVersion serverVersion) {
                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    return this.ordinal() - 2;
                } else {
                    return serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)
                            ? 3
                            : this.ordinal();
                }
            }
        },
        START_SPRINTING {

            @Override
            public int getId(ServerVersion serverVersion) {
                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    return this.ordinal() - 2;
                } else {
                    return serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)
                            ? 4
                            : this.ordinal();
                }
            }
        },
        STOP_SPRINTING {

            @Override
            public int getId(ServerVersion serverVersion) {
                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    return this.ordinal() - 2;
                } else {
                    return serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)
                            ? 5
                            : this.ordinal();
                }
            }
        },
        START_JUMPING_WITH_HORSE {

            @Override
            public int getId(ServerVersion serverVersion) {
                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    return this.ordinal() - 2;
                } else {
                    return serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)
                            ? (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_5) ? -1 : 6)
                            : this.ordinal();
                }
            }
        },
        STOP_JUMPING_WITH_HORSE {

            @Override
            public int getId(ServerVersion serverVersion) {
                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    return this.ordinal() - 2;
                } else {
                    return serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                            ? this.ordinal()
                            : -1;
                }
            }
        },
        OPEN_HORSE_INVENTORY {

            @Override
            public int getId(ServerVersion serverVersion) {
                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    return this.ordinal() - 2;
                } else {
                    return serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                            ? this.ordinal()
                            : this.ordinal() - 1;
                }
            }
        },
        START_FLYING_WITH_ELYTRA {

            @Override
            public int getId(ServerVersion serverVersion) {
                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    return this.ordinal() - 2;
                } else {
                    return serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                            ? this.ordinal()
                            : -1;
                }
            }
        };

        private static final WrapperPlayClientEntityAction.Action[] VALUES = values();

        Action() {}

        public abstract int getId(ServerVersion serverVersion);

        public static WrapperPlayClientEntityAction.Action getById(
                ServerVersion serverVersion, int value) {
            WrapperPlayClientEntityAction.Action[] actions = VALUES;
            int value2 = VALUES.length;
            for (int index = 0; index < value2; index++) {
                WrapperPlayClientEntityAction.Action action;
                if ((action = actions[index]).getId(serverVersion) == value) {
                    return action;
                }
            }
            throw new IllegalStateException(
                    "Invalid entity action id " + value + " for " + serverVersion);
        }
    }
}
