package it.ytnoos.lpx.netty;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.commands.LPXCommand;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.config.ItemStripOptions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerAbstract;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.VersionComparison;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Server;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTabComplete;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PacketCheckListener extends PacketListenerAbstract {

    private final LPXBukkit lpx;

    private final PacketCheckProcessor listener;

    private final boolean enabled;

    private final boolean enabled2;

    private static final String TEXT = "/lpx";

    public PacketCheckListener(LPXBukkit lPXBukkit, PacketCheckProcessor packetCheckProcessor) {
        this.lpx = lPXBukkit;
        this.listener = packetCheckProcessor;
        this.enabled =
                PacketEvents.getAPI()
                        .getServerManager()
                        .getVersion()
                        .is(VersionComparison.OLDER_THAN_OR_EQUALS, ServerVersion.V_1_12_2);
        this.enabled2 =
                PacketEvents.getAPI()
                        .getServerManager()
                        .getVersion()
                        .isNewerThan(ServerVersion.V_1_20_4);
    }

    public void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {
        PacketCheckProcessor packetCheckProcessor = this.listener;
        Boolean matches = ((Boolean) (boolean) false);
        packetCheckProcessor.handlePacketReceive(packetReceiveEvent, ((boolean) matches));
    }

    public void onPacketEventExternal(PacketEvent event) {
        PacketCheckProcessor packetCheckProcessor = this.listener;
        Boolean matches = ((Boolean) (boolean) false);
        packetCheckProcessor.check(event, ((boolean) matches));
    }

    public void onPacketSend(PacketSendEvent packetSendEvent) {
        if (packetSendEvent.getPacketType() == Server.KEEP_ALIVE) {
            this.lpx
                    .getRegistry()
                    .getPlayer(packetSendEvent.getUser())
                    .ifPresent(result -> result.setLastKeepAliveSent(System.currentTimeMillis()));
        }
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        ItemStripOptions itemStripOptions = options.getItemStripOptions();
        if (itemStripOptions.isEnabled()) {
            this.strip(itemStripOptions, packetSendEvent);
        }
        if (this.enabled) {
            if (packetSendEvent.getPacketType() == Server.TAB_COMPLETE) {
                User user = packetSendEvent.getUser();
                if (user != null && user.getUUID() != null) {
                    Optional<it.ytnoos.lpx.api.player.ProtocolPlayer> player2 =
                            this.lpx.getRegistry().getPlayer(user);
                    player2.ifPresent(
                            result2 -> {
                                Player player3 = (Player) result2.getPlayer().orElse(null);
                                if (player3 != null) {
                                    LPXBukkit lPXBukkit2 = this.lpx;
                                    LPXCommand command = lPXBukkit2.getCommand();
                                    CommandSender sender = (CommandSender) player3;
                                    if (command.testCommandSender(sender)) {
                                        return;
                                    }
                                }
                                WrapperPlayServerTabComplete wrapperPlayServerTabComplete =
                                        new WrapperPlayServerTabComplete(packetSendEvent);
                                if (wrapperPlayServerTabComplete
                                        .getCommandMatches()
                                        .removeIf(result -> result.getText().startsWith(TEXT))) {
                                    packetSendEvent.markForReEncode(true);
                                }
                            });
                }
            }
        }
    }

    private void strip(ItemStripOptions itemStripOptions, PacketSendEvent packetSendEvent) {
        if (packetSendEvent.getPacketType() == Server.WINDOW_ITEMS) {
            WrapperPlayServerWindowItems wrapperPlayServerWindowItems =
                    new WrapperPlayServerWindowItems(packetSendEvent);
            List<ItemStack> items = wrapperPlayServerWindowItems.getItems();
            boolean matches = false;
            for (int index = 0; index < items.size(); index++) {
                ItemStack itemStack = (ItemStack) items.get(index);
                ItemStack itemStack2 = itemStack;
                itemStack = itemStack2;
                ItemStack itemStack3 = itemStack;
                ItemStack itemStack4 = this.stripItem(itemStack3, itemStripOptions);
                if (itemStack4 != null) {
                    items.set(index, itemStack4);
                    matches = true;
                }
            }
            ItemStack itemStack5 =
                    (ItemStack) wrapperPlayServerWindowItems.getCarriedItem().orElse(null);
            if (itemStack5 != null) {
                ItemStack itemStack6 = this.stripItem(itemStack5, itemStripOptions);
                if (itemStack6 != null) {
                    wrapperPlayServerWindowItems.setCarriedItem(itemStack6);
                    matches = true;
                }
            }
            if (matches) {
                packetSendEvent.markForReEncode(true);
            }
        } else {
            if (itemStripOptions.isSetSlot()
                    && packetSendEvent.getPacketType() == Server.SET_SLOT) {
                WrapperPlayServerSetSlot wrapperPlayServerSetSlot =
                        new WrapperPlayServerSetSlot(packetSendEvent);
                ItemStack item2 = wrapperPlayServerSetSlot.getItem();
                ItemStack itemStack7 = this.stripItem(item2, itemStripOptions);
                if (itemStack7 != null) {
                    wrapperPlayServerSetSlot.setItem(itemStack7);
                    packetSendEvent.markForReEncode(true);
                }
            }
        }
    }

    private ItemStack stripItem(ItemStack itemStack, ItemStripOptions itemStripOptions) {
        if (itemStack != null && !itemStack.isEmpty()) {
            if (this.enabled2) {
                if (itemStack.getComponents() != null
                        && itemStack.getComponents().getPatches() != null) {
                    Set<
                                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol
                                                    .component.ComponentType<
                                            ?>>
                            values = itemStack.getComponents().getPatches().keySet();
                    if (values.removeIf(
                            result -> {
                                return itemStripOptions
                                        .getValues()
                                        .contains(result.getName().getKey());
                            })) {
                        return itemStack;
                    }
                }
            } else {
                NBTCompound nBT = itemStack.getNBT();
                if (nBT != null) {
                    boolean matches = false;
                    for (String text :
                            (Iterable<String>) ((Iterable) new ArrayList(nBT.getTags().keySet()))) {
                        if (itemStripOptions.getValues2().contains(text)) {
                            nBT.removeTag(text);
                            matches = true;
                        }
                    }
                    if (matches) {
                        return itemStack;
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
