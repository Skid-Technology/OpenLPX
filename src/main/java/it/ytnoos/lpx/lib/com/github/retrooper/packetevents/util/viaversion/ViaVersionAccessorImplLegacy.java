package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViaVersionAccessorImplLegacy implements ViaVersionAccessor {

    private Class<?> viaClass;

    private Class<?> bukkitDecodeHandlerClass;

    private Class<?> bukkitEncodeHandlerClass;

    private Field viaManagerField;

    private Method apiAccessor;

    private Method getPlayerVersionMethod;

    private Class<?> userConnectionClass;

    private void load() {
        if (this.viaClass == null) {
            try {
                ClassLoader classLoader =
                        PacketEvents.getAPI().getPlugin().getClass().getClassLoader();
                this.viaClass = classLoader.loadClass("us.myles.ViaVersion.api.Via");
                this.viaManagerField = this.viaClass.getDeclaredField("manager");
                this.bukkitDecodeHandlerClass =
                        classLoader.loadClass(
                                "us.myles.ViaVersion.bukkit.handlers.BukkitDecodeHandler");
                this.bukkitEncodeHandlerClass =
                        classLoader.loadClass(
                                "us.myles.ViaVersion.bukkit.handlers.BukkitEncodeHandler");
                Class<?> classValue = classLoader.loadClass("us.myles.ViaVersion.api.ViaAPI");
                this.apiAccessor = this.viaClass.getMethod("getAPI");
                this.getPlayerVersionMethod =
                        classValue.getMethod("getPlayerVersion", Object.class);
            } catch (ClassNotFoundException
                    | NoSuchMethodException
                    | NoSuchFieldException exception) {
                exception.printStackTrace();
            }
        }
        if (this.userConnectionClass == null) {
            try {
                ClassLoader classLoader2 =
                        PacketEvents.getAPI().getPlugin().getClass().getClassLoader();
                this.userConnectionClass =
                        classLoader2.loadClass("us.myles.ViaVersion.api.data.UserConnection");
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }

    @Override
    public int getProtocolVersion(Player player) {
        this.load();
        try {
            Object result = this.apiAccessor.invoke(null);
            return (Integer) this.getPlayerVersionMethod.invoke(result, player);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getProtocolVersion(User user) {
        try {
            Player player;
            int value;
            if (user.getUUID() != null
                    && (player = Bukkit.getPlayer(user.getUUID())) != null
                    && (value = this.getProtocolVersion(player)) != -1) {
                return value;
            }
            Object result;
            Object result2;
            ChannelHandler channelHandler;
            Object result3;
            return (result2 =
                                    Reflection.getField(
                                                    (result =
                                                                    Reflection.getField(
                                                                                    (result3 =
                                                                                                    Reflection
                                                                                                            .getField(
                                                                                                                    (channelHandler =
                                                                                                                                    ((Channel)
                                                                                                                                                    user
                                                                                                                                                            .getChannel())
                                                                                                                                            .pipeline()
                                                                                                                                            .get(
                                                                                                                                                    "via-encoder"))
                                                                                                                            .getClass(),
                                                                                                                    "connection")
                                                                                                            .get(
                                                                                                                    channelHandler))
                                                                                            .getClass(),
                                                                                    "protocolInfo")
                                                                            .get(result3))
                                                            .getClass(),
                                                    "protocolVersion")
                                            .get(result))
                            instanceof Integer
                    ? (Integer) result2
                    : ((ProtocolVersion) result2).getVersion();
        } catch (Exception exception) {
            PacketEvents.getAPI()
                    .getLogManager()
                    .warn("Unable to grab ViaVersion client version for player!");
            return -1;
        }
    }

    @Override
    public Class<?> getUserConnectionClass() {
        this.load();
        return this.userConnectionClass;
    }

    @Override
    public Class<?> getBukkitDecodeHandlerClass() {
        this.load();
        return this.bukkitDecodeHandlerClass;
    }

    @Override
    public Class<?> getBukkitEncodeHandlerClass() {
        this.load();
        return this.bukkitEncodeHandlerClass;
    }
}
