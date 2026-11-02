package it.ytnoos.lpx.netty;

import io.netty.buffer.ByteBuf;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.CheckResultInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.EntityScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.ProtocolPacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ReadSlotsEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCraftRecipeRequest;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCustomClickAction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPickItemFromBlock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPickItemFromEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSelectBundleItem;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSetDisplayedRecipe;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSetTestBlock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTestInstanceBlockAction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateSign;
import it.ytnoos.lpx.logging.FileLogger;
import it.ytnoos.lpx.player.ProtocolPlayerImpl;
import it.ytnoos.lpx.utils.PacketExceptionHandler;
import it.ytnoos.lpx.utils.PacketUtils;
import it.ytnoos.lpx.utils.TimeUtils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class PacketCheckProcessor {

    protected static final Map<PacketTypeCommon, Function<PacketReceiveEvent, PacketWrapper<?>>>
            ENTRIES;

    private final LPXBukkit lpx;

    private final Map<String, Long> entries;

    public PacketCheckProcessor(LPXBukkit lPXBukkit) {
        ConcurrentHashMap entries2 = new ConcurrentHashMap();
        this.entries = entries2;
        this.lpx = lPXBukkit;
    }

    void handlePacketReceive(PacketReceiveEvent packetReceiveEvent, boolean matches) {
        User user = packetReceiveEvent.getUser();
        ProtocolPlayer protocolPlayer =
                (ProtocolPlayer) this.lpx.getRegistry().getPlayer(user).orElse(null);
        if (protocolPlayer != null) {
            try {
                Boolean matches2 = ((Boolean) matches);
                this.handleEnabled(packetReceiveEvent, protocolPlayer, ((boolean) matches2));
            } catch (PacketCheckProcessor.PacketProcessingException packetProcessingException) {
                RuntimeException cause = packetProcessingException.getCause();
                Boolean matches3 = ((Boolean) matches);
                RuntimeException runtimeException2 = cause;
                cause = runtimeException2;
                RuntimeException runtimeException3 = cause;
                this.handleDecodeFailure(
                        packetReceiveEvent,
                        protocolPlayer,
                        runtimeException3,
                        ((boolean) matches3));
            } catch (RuntimeException runtimeException) {
                protocolPlayer.setLastReceivedPacket(null);
                String name = packetReceiveEvent.getPacketType().getName();
                String username = protocolPlayer.getUsername();
                LPXBukkit lPXBukkit = this.lpx;
                AlertManager alertManager = lPXBukkit.getAlertManager();
                FileLogger fileLogger = alertManager.getFileLogger();
                Runnable runnable =
                        () -> {
                            Map<String, Long> entries2 = this.entries;
                            long now = System.currentTimeMillis();
                            Long value = ((Long) now);
                            Map<String, Long> entries3 = entries2;
                            entries2 = entries3;
                            Map<String, Long> entries4 = entries2;
                            if (PacketExceptionHandler.testTime(
                                    entries4, username, ((long) value))) {
                                LPXBukkit lPXBukkit2 = this.lpx;
                                AlertManager alertManager2 = lPXBukkit2.getAlertManager();
                                FileLogger fileLogger2 = alertManager2.getFileLogger();
                                Supplier supplier =
                                        () -> {
                                            return (String) "Error while handling "
                                                    + name
                                                    + (String) " sent by "
                                                    + username;
                                        };
                                fileLogger2.handleThrowable(
                                        supplier, ((Throwable) runtimeException));
                            }
                        };
                fileLogger.execute(runnable);
            }
        }
    }

    private void handleEnabled(
            PacketReceiveEvent packetReceiveEvent, ProtocolPlayer protocolPlayer, boolean matches) {
        User user = packetReceiveEvent.getUser();
        if (matches && packetReceiveEvent.getPacketType() == Client.KEEP_ALIVE) {
            protocolPlayer.setPlayerPing(
                    System.currentTimeMillis() - protocolPlayer.getLastKeepAliveSent());
        }
        if (protocolPlayer.getPacketCount() < 10L) {
            protocolPlayer.incrementPacketCount();
        }
        if (matches || protocolPlayer.getLastReceivedPacket() == null) {
            String name = user.getName();
            ClientVersion clientVersion = user.getClientVersion();
            LPXBukkit lPXBukkit = this.lpx;
            NettyHealthMonitor nettyHealthMonitor = lPXBukkit.getNettyHealthMonitor();
            long tickScheduler = nettyHealthMonitor.getTickScheduler();
            PacketTypeCommon packetType = packetReceiveEvent.getPacketType();
            Object byteBuf = packetReceiveEvent.getByteBuf();
            ReceivedPacket receivedPacket =
                    new ReceivedPacket(
                            name,
                            clientVersion,
                            tickScheduler,
                            packetType,
                            PacketUtils.getSize(byteBuf),
                            null);
            protocolPlayer.setLastReceivedPacket(receivedPacket);
        }
        Object byteBuf2 = packetReceiveEvent.getByteBuf();
        Boolean matches2 = ((Boolean) matches);
        Class<ByteBuf> classValue = ByteBuf.class;
        PacketReceiveEvent packetReceiveEvent2 = packetReceiveEvent;
        ProtocolPlayer protocolPlayer2 = protocolPlayer;
        ProtocolPlayer protocolPlayer3 = protocolPlayer2;
        PacketReceiveEvent packetReceiveEvent3 = packetReceiveEvent2;
        if (this.handleChecks(
                        packetReceiveEvent3,
                        protocolPlayer3,
                        classValue,
                        byteBuf2,
                        ((boolean) matches2))
                == CheckResult.DENY) {
            protocolPlayer.setLastReceivedPacket(null);
        } else {
            Boolean matches3 = ((Boolean) matches);
            Class<PacketPlayReceiveEvent> classValue2 = PacketPlayReceiveEvent.class;
            packetReceiveEvent2 = packetReceiveEvent;
            protocolPlayer2 = protocolPlayer;
            ProtocolPlayer protocolPlayer4 = protocolPlayer2;
            PacketReceiveEvent packetReceiveEvent4 = packetReceiveEvent2;
            if (this.handleChecks(
                            packetReceiveEvent4,
                            protocolPlayer4,
                            classValue2,
                            packetReceiveEvent,
                            ((boolean) matches3))
                    == CheckResult.DENY) {
                protocolPlayer.setLastReceivedPacket(null);
            } else {
                PacketTypeCommon packetType2 = packetReceiveEvent.getPacketType();
                Object result = packetType2;
                if (packetType2 == Client.PLAYER_POSITION
                        || result == Client.PLAYER_POSITION_AND_ROTATION
                        || result == Client.PLAYER_ROTATION) {
                    result = Client.PLAYER_FLYING;
                }
                Function<PacketReceiveEvent, PacketWrapper<?>> function = ENTRIES.get(result);
                if (function != null) {
                    PacketWrapper packetWrapper;
                    try {
                        packetWrapper = (PacketWrapper) function.apply(packetReceiveEvent);
                    } catch (RuntimeException runtimeException) {
                        PacketCheckProcessor.PacketProcessingException packetProcessingException =
                                new PacketCheckProcessor.PacketProcessingException(
                                        runtimeException);
                        throw packetProcessingException;
                    }
                    if (protocolPlayer.getLastReceivedPacket().getPacket() == null) {
                        protocolPlayer.getLastReceivedPacket().setPacket(packetWrapper);
                    }
                    if (packetReceiveEvent.isCancelled()) {
                        protocolPlayer.setLastReceivedPacket(null);
                    } else {
                        Boolean matches4 = ((Boolean) matches);
                        PacketWrapper packetWrapper2 = packetWrapper;
                        Object result2 = result;
                        packetReceiveEvent2 = packetReceiveEvent;
                        protocolPlayer2 = protocolPlayer;
                        ProtocolPlayer protocolPlayer5 = protocolPlayer2;
                        PacketReceiveEvent packetReceiveEvent5 = packetReceiveEvent2;
                        if (this.handleChecks(
                                        packetReceiveEvent5,
                                        protocolPlayer5,
                                        result2,
                                        packetWrapper2,
                                        ((boolean) matches4))
                                == CheckResult.DENY) {
                            protocolPlayer.setLastReceivedPacket(null);
                        }
                    }
                }
            }
        }
    }

    CheckResult check(PacketEvent event, boolean matches) {
        ProtocolPacketEvent protocolPacketEvent;
        if (event instanceof ItemStackDecodeEvent) {
            protocolPacketEvent = ((ItemStackDecodeEvent) event).getEvent();
        } else if (event instanceof NBTDecodeEvent) {
            protocolPacketEvent = ((NBTDecodeEvent) event).getEvent();
        } else {
            if (!(event instanceof ReadSlotsEvent)) {
                return CheckResult.ALLOW;
            }
            protocolPacketEvent = ((ReadSlotsEvent) event).getEvent();
        }
        if (!(protocolPacketEvent instanceof PacketReceiveEvent packetReceiveEvent2)) {
            return CheckResult.ALLOW;
        } else {
            PacketReceiveEvent packetReceiveEvent = packetReceiveEvent2;
            if (packetReceiveEvent2.isCancelled()) {
                return CheckResult.ALLOW;
            }
            if (!(packetReceiveEvent instanceof PacketReceiveEvent)) {
                return CheckResult.ALLOW;
            }
            User user = packetReceiveEvent.getUser();
            if (user != null && user.getUUID() != null) {
                ProtocolPlayer protocolPlayer =
                        (ProtocolPlayer) this.lpx.getRegistry().getPlayer(user).orElse(null);
                if (protocolPlayer == null) {
                    return CheckResult.ALLOW;
                } else if (event instanceof ItemStackDecodeEvent) {
                    Boolean matches2 = ((Boolean) matches);
                    Class<ItemStackDecodeEvent> classValue = ItemStackDecodeEvent.class;
                    return this.handleChecks(
                            packetReceiveEvent,
                            protocolPlayer,
                            classValue,
                            event,
                            ((boolean) matches2));
                } else if (event instanceof NBTDecodeEvent) {
                    Boolean matches3 = ((Boolean) matches);
                    Class<NBTDecodeEvent> classValue2 = NBTDecodeEvent.class;
                    return this.handleChecks(
                            packetReceiveEvent,
                            protocolPlayer,
                            classValue2,
                            event,
                            ((boolean) matches3));
                } else {
                    Boolean matches4 = ((Boolean) matches);
                    Class<ReadSlotsEvent> classValue3 = ReadSlotsEvent.class;
                    return this.handleChecks(
                            packetReceiveEvent,
                            protocolPlayer,
                            classValue3,
                            event,
                            ((boolean) matches4));
                }
            } else {
                return CheckResult.ALLOW;
            }
        }
    }

    public CheckResult handleChecks(
            PacketReceiveEvent packetReceiveEvent,
            ProtocolPlayer protocolPlayer,
            Object result2,
            Object result3,
            boolean matches) {
        CheckResultInfo checkResultInfo = protocolPlayer.handleChecks(result2, result3, matches);
        CheckResult checkResult = checkResultInfo.result();
        if (checkResult == CheckResult.DENY) {
            packetReceiveEvent.setCancelled(true);
            PacketTypeCommon packetType = packetReceiveEvent.getPacketType();
            if (packetType == Client.CREATIVE_INVENTORY_ACTION
                    || packetType == Client.CLICK_WINDOW) {
                this.resyncInventory(protocolPlayer);
            }
            LPXBukkit lPXBukkit = this.lpx;
            ConfigManager configManager = lPXBukkit.getConfigManager();
            GeneralOptions options = configManager.getOptions();
            if (options.isEnabled16()) {
                Check check = checkResultInfo.lastCheck();
                String text;
                if (check != null) {
                    String text2 = String.valueOf(check.getType().getId());
                    text = (String) "[" + text2 + "] ";
                } else {
                    text = "";
                }
                String text3 = text;
                lPXBukkit = this.lpx;
                AlertManager alertManager = lPXBukkit.getAlertManager();
                FileLogger fileLogger = alertManager.getFileLogger();
                Supplier supplier =
                        () -> {
                            String name = packetReceiveEvent.getPacketType().getName();
                            String username = protocolPlayer.getUsername();
                            return text3
                                    + (String) "Cancelling "
                                    + name
                                    + (String) " sent by "
                                    + username;
                        };
                fileLogger.writeString(supplier);
            }
        } else if (checkResult == CheckResult.CHANGE) {
            packetReceiveEvent.markForReEncode(true);
        }
        return checkResult;
    }

    private void handleDecodeFailure(
            PacketReceiveEvent packetReceiveEvent,
            ProtocolPlayer protocolPlayer,
            RuntimeException runtimeException,
            boolean matches) {
        packetReceiveEvent.setCancelled(true);
        protocolPlayer.setLastReceivedPacket(null);
        if (!protocolPlayer.isKicked()) {
            PacketLimitException packetLimitException =
                    createPacketLimitException(((Throwable) runtimeException));
            if (packetLimitException != null) {
                Boolean matches2 = ((Boolean) matches);
                Class<PacketLimitException> classValue = PacketLimitException.class;
                this.handleChecks(
                        packetReceiveEvent,
                        protocolPlayer,
                        classValue,
                        packetLimitException,
                        ((boolean) matches2));
            } else {
                String name = packetReceiveEvent.getPacketType().getName();
                String username = protocolPlayer.getUsername();
                LPXBukkit lPXBukkit = this.lpx;
                ConfigManager configManager = lPXBukkit.getConfigManager();
                GeneralOptions options = configManager.getOptions();
                boolean enabled3 = options.isEnabled3();
                if (enabled3) {
                    LPXBukkit lPXBukkit2 = this.lpx;
                    ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                    Messages messages = configManager2.getMessages();
                    protocolPlayer.kick(messages.getKick());
                }
                lPXBukkit = this.lpx;
                AlertManager alertManager = lPXBukkit.getAlertManager();
                FileLogger fileLogger = alertManager.getFileLogger();
                Runnable runnable =
                        () -> {
                            if (!enabled3) {
                                Map<String, Long> entries2 = this.entries;
                                long now = System.currentTimeMillis();
                                Long value = ((Long) now);
                                Map<String, Long> entries3 = entries2;
                                entries2 = entries3;
                                Map<String, Long> entries4 = entries2;
                                if (!PacketExceptionHandler.testTime(
                                        entries4, username, ((long) value))) {
                                    return;
                                }
                            }
                            Logger logger = this.lpx.getLogger();
                            String text = enabled3 ? ", kicking" : ", cancelled";
                            String message = runtimeException.getMessage();
                            logger.warning(
                                    "Invalid "
                                            + name
                                            + " sent by "
                                            + username
                                            + text
                                            + ": "
                                            + message
                                            + " (full stacktrace saved in LPX logs)");
                            LPXBukkit lPXBukkit3 = this.lpx;
                            AlertManager alertManager2 = lPXBukkit3.getAlertManager();
                            FileLogger fileLogger2 = alertManager2.getFileLogger();
                            Supplier supplier =
                                    () -> {
                                        return (String) "Invalid "
                                                + name
                                                + (String) " sent by "
                                                + username;
                                    };
                            fileLogger2.handleThrowable(supplier, ((Throwable) runtimeException));
                        };
                fileLogger.execute(runnable);
            }
        }
    }

    @Nullable
    private static PacketLimitException createPacketLimitException(Throwable throwable) {
        for (Throwable throwable2 = throwable;
                throwable2 != null;
                throwable2 = throwable2.getCause()) {
            if (throwable2 instanceof PacketLimitException) {
                return (PacketLimitException) throwable2;
            }
        }
        return null;
    }

    private void resyncInventory(ProtocolPlayer protocolPlayer) {
        if (protocolPlayer instanceof ProtocolPlayerImpl protocolPlayerImpl4) {
            Player player = (Player) protocolPlayer.getPlayer().orElse(null);
            if (player != null) {
                if (protocolPlayerImpl4.isAtomicBoolean()) {
                    long time8 = protocolPlayerImpl4.getTime8();
                    Long value = ((Long) time8);
                    long value2 = 1000L - TimeUtils.calculateTime(value);
                    ProtocolPlayerImpl protocolPlayerImpl = protocolPlayerImpl4;
                    Runnable runnable =
                            () -> {
                                boolean matches = false;
                                boolean matches2 = matches;
                                Boolean matches3 = matches2;
                                Boolean matches4 = matches3;
                                boolean matches5 = matches4;
                                protocolPlayerImpl.handleEnabled(matches5);
                            };
                    if (value2 <= 0L) {
                        EntityScheduler entityScheduler = FoliaScheduler.getEntityScheduler();
                        LPXPlugin plugin = this.lpx.getPlugin();
                        ProtocolPlayerImpl protocolPlayerImpl2 = protocolPlayerImpl4;
                        entityScheduler.run(
                                player,
                                plugin,
                                result -> {
                                    player.updateInventory();
                                    boolean matches5 = (boolean) true;
                                    Boolean matches6 = matches5;
                                    protocolPlayerImpl2.handleEnabled(((boolean) matches6));
                                },
                                runnable);
                    } else {
                        EntityScheduler entityScheduler2 = FoliaScheduler.getEntityScheduler();
                        LPXPlugin plugin2 = this.lpx.getPlugin();
                        ProtocolPlayerImpl protocolPlayerImpl3 = protocolPlayerImpl4;
                        entityScheduler2.runDelayed(
                                player,
                                plugin2,
                                result -> {
                                    player.updateInventory();
                                    boolean matches = true;
                                    boolean matches2 = matches;
                                    Boolean matches3 = matches2;
                                    Boolean matches4 = matches3;
                                    boolean matches5 = matches4;
                                    protocolPlayerImpl3.handleEnabled(matches5);
                                },
                                runnable,
                                (value2 + 49L) / 50L);
                    }
                }
            }
        }
    }

    static {
        HashMap entries = new HashMap();
        ENTRIES = entries;
        ENTRIES.put(Client.CLICK_WINDOW, WrapperPlayClientClickWindow::new);
        ENTRIES.put(
                Client.CREATIVE_INVENTORY_ACTION, WrapperPlayClientCreativeInventoryAction::new);
        ENTRIES.put(Client.PLAYER_BLOCK_PLACEMENT, WrapperPlayClientPlayerBlockPlacement::new);
        ENTRIES.put(Client.UPDATE_SIGN, WrapperPlayClientUpdateSign::new);
        ENTRIES.put(Client.PLAYER_FLYING, WrapperPlayClientPlayerFlying::new);
        ENTRIES.put(Client.PLAYER_POSITION, WrapperPlayClientPlayerFlying::new);
        ENTRIES.put(Client.PLAYER_POSITION_AND_ROTATION, WrapperPlayClientPlayerFlying::new);
        ENTRIES.put(Client.PLAYER_ROTATION, WrapperPlayClientPlayerFlying::new);
        ENTRIES.put(Client.PLUGIN_MESSAGE, WrapperPlayClientPluginMessage::new);
        ENTRIES.put(Client.TAB_COMPLETE, WrapperPlayClientTabComplete::new);
        ENTRIES.put(Client.EDIT_BOOK, WrapperPlayClientEditBook::new);
        ENTRIES.put(Client.PLAYER_DIGGING, WrapperPlayClientPlayerDigging::new);
        ENTRIES.put(Client.CRAFT_RECIPE_REQUEST, WrapperPlayClientCraftRecipeRequest::new);
        ENTRIES.put(Client.SET_DISPLAYED_RECIPE, WrapperPlayClientSetDisplayedRecipe::new);
        ENTRIES.put(Client.SELECT_BUNDLE_ITEM, WrapperPlayClientSelectBundleItem::new);
        ENTRIES.put(Client.PICK_ITEM_FROM_BLOCK, WrapperPlayClientPickItemFromBlock::new);
        ENTRIES.put(Client.PICK_ITEM_FROM_ENTITY, WrapperPlayClientPickItemFromEntity::new);
        ENTRIES.put(
                Client.TEST_INSTANCE_BLOCK_ACTION, WrapperPlayClientTestInstanceBlockAction::new);
        ENTRIES.put(Client.SET_TEST_BLOCK, WrapperPlayClientSetTestBlock::new);
        ENTRIES.put(Client.CUSTOM_CLICK_ACTION, WrapperPlayClientCustomClickAction::new);
    }

    static final class PacketProcessingException extends RuntimeException {

        private PacketProcessingException(RuntimeException runtimeException) {
            super(runtimeException.getMessage(), runtimeException, false, false);
        }

        public synchronized RuntimeException getCause() {
            return (RuntimeException) super.getCause();
        }
    }
}
