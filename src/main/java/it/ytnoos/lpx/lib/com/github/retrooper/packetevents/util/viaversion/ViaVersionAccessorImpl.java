package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.ViaChannelHandler;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.handlers.BukkitDecodeHandler;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;

@NullMarked
public class ViaVersionAccessorImpl implements ViaVersionAccessor {

    private static final boolean HAS_INFO_PROTOCOL_VERSION =
            Reflection.getClassByNameWithoutException(
                            "com.viaversion.viaversion.api.protocol.version.VersionType")
                    != null;

    private static final boolean HAS_VIA_CHANNEL_HANDLER =
            Reflection.getClassByNameWithoutException(
                            "com.viaversion.viaversion.api.platform.ViaChannelHandler")
                    != null;

    @MonotonicNonNull private static Field CONNECTION_FIELD = null;

    @Override
    public int getProtocolVersion(Player player) {
        return Via.getAPI().getPlayerVersion(player);
    }

    @Override
    public int getProtocolVersion(User user) {
        ViaInjector injector = Via.getManager().getInjector();
        ChannelHandler channelHandler =
                ((Channel) user.getChannel()).pipeline().get(injector.getEncoderName());
        UserConnection userConnection = null;
        if (HAS_VIA_CHANNEL_HANDLER) {
            if (channelHandler instanceof ViaChannelHandler) {
                userConnection = ((ViaChannelHandler) channelHandler).connection();
            }
        } else {
            if (CONNECTION_FIELD == null) {
                CONNECTION_FIELD =
                        Reflection.getField(channelHandler.getClass(), UserConnection.class, 0);
            }
            if (CONNECTION_FIELD != null) {
                try {
                    userConnection = (UserConnection) CONNECTION_FIELD.get(channelHandler);
                } catch (IllegalAccessException illegalAccessException) {
                }
            }
        }
        return userConnection != null ? this.getVersion(userConnection) : -1;
    }

    private int getVersion(UserConnection userConnection) {
        if (HAS_INFO_PROTOCOL_VERSION) {
            ProtocolVersion version;
            return (version = userConnection.getProtocolInfo().protocolVersion()).isKnown()
                    ? version.getVersion()
                    : -1;
        } else {
            return userConnection.getProtocolInfo().getProtocolVersion();
        }
    }

    @Override
    public Class<?> getUserConnectionClass() {
        return UserConnection.class;
    }

    @Override
    public Class<?> getBukkitDecodeHandlerClass() {
        return BukkitDecodeHandler.class;
    }

    @Override
    public Class<?> getBukkitEncodeHandlerClass() {
        return BukkitEncodeHandler.class;
    }
}
