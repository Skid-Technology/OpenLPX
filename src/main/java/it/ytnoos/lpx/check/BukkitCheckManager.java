package it.ytnoos.lpx.check;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.impl.book.Book0Check;
import it.ytnoos.lpx.check.impl.book.BookACheck;
import it.ytnoos.lpx.check.impl.book.BookBCheck;
import it.ytnoos.lpx.check.impl.book.BookCCheck;
import it.ytnoos.lpx.check.impl.command.CommandACheck;
import it.ytnoos.lpx.check.impl.creative.CreativeACheck;
import it.ytnoos.lpx.check.impl.creative.CreativeBCheck;
import it.ytnoos.lpx.check.impl.creative.CreativeCCheck;
import it.ytnoos.lpx.check.impl.creative.CreativeDCheck;
import it.ytnoos.lpx.check.impl.creative.CreativeECheck;
import it.ytnoos.lpx.check.impl.creative.CreativeFCheck;
import it.ytnoos.lpx.check.impl.creative.CreativeGCheck;
import it.ytnoos.lpx.check.impl.creative.CreativeHCheck;
import it.ytnoos.lpx.check.impl.creative.CreativeICheck;
import it.ytnoos.lpx.check.impl.flood.FloodACheck;
import it.ytnoos.lpx.check.impl.flood.FloodBCheck;
import it.ytnoos.lpx.check.impl.flood.FloodCCheck;
import it.ytnoos.lpx.check.impl.flood.FloodDCheck;
import it.ytnoos.lpx.check.impl.flood.FloodFCheck;
import it.ytnoos.lpx.check.impl.flood.FloodGCheck;
import it.ytnoos.lpx.check.impl.item.Item0Check;
import it.ytnoos.lpx.check.impl.item.Item1Check;
import it.ytnoos.lpx.check.impl.item.Item2Check;
import it.ytnoos.lpx.check.impl.item.Item3Check;
import it.ytnoos.lpx.check.impl.item.ItemACheck;
import it.ytnoos.lpx.check.impl.item.ItemBCheck;
import it.ytnoos.lpx.check.impl.item.ItemCCheck;
import it.ytnoos.lpx.check.impl.item.ItemDCheck;
import it.ytnoos.lpx.check.impl.item.ItemECheck;
import it.ytnoos.lpx.check.impl.item.ItemFCheck;
import it.ytnoos.lpx.check.impl.item.ItemGCheck;
import it.ytnoos.lpx.check.impl.item.ItemHCheck;
import it.ytnoos.lpx.check.impl.item.ItemJCheck;
import it.ytnoos.lpx.check.impl.item.ItemKCheck;
import it.ytnoos.lpx.check.impl.netty.PacketLimitCheck;
import it.ytnoos.lpx.check.impl.netty.PacketSizeCheck;
import it.ytnoos.lpx.check.impl.payload.PayloadACheck;
import it.ytnoos.lpx.check.impl.payload.PayloadBCheck;
import it.ytnoos.lpx.check.impl.payload.PayloadCCheck;
import it.ytnoos.lpx.check.impl.payload.PayloadDirectionCheck;
import it.ytnoos.lpx.check.impl.payload.PayloadECheck;
import it.ytnoos.lpx.check.impl.payload.PayloadFCheck;
import it.ytnoos.lpx.check.impl.place.PlaceACheck;
import it.ytnoos.lpx.check.impl.place.PlaceBCheck;
import it.ytnoos.lpx.check.impl.place.PlaceCCheck;
import it.ytnoos.lpx.check.impl.position.PositionACheck;
import it.ytnoos.lpx.check.impl.position.PositionBCheck;
import it.ytnoos.lpx.check.impl.position.PositionCCheck;
import it.ytnoos.lpx.check.impl.position.PositionDCheck;
import it.ytnoos.lpx.check.impl.tab.TabACheck;
import it.ytnoos.lpx.check.impl.tab.TabBCheck;
import it.ytnoos.lpx.check.impl.window.WindowACheck;
import it.ytnoos.lpx.check.impl.window.WindowBCheck;
import it.ytnoos.lpx.check.impl.window.WindowCCheck;
import it.ytnoos.lpx.check.impl.window.WindowDCheck;
import it.ytnoos.lpx.check.impl.window.WindowECheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ReadSlotsEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.VersionComparison;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCraftRecipeRequest;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSetDisplayedRecipe;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class BukkitCheckManager extends CheckManager {

    public static final List<String> VALUES;

    public BukkitCheckManager(LPXBukkit lPXBukkit) {
        super(lPXBukkit.getPlugin());
    }

    public void registerChecks() {
        ServerVersion version = PacketEvents.getAPI().getServerManager().getVersion();
        boolean matches = version.is(VersionComparison.OLDER_THAN_OR_EQUALS, ServerVersion.V_1_8_8);
        this.register(
                ByteBuf.class,
                (result, result2) -> new PacketSizeCheck(result, result2, true),
                CheckType.NETTY_A);
        this.register(
                ByteBuf.class,
                (result, result2) -> new PacketSizeCheck(result, result2, false),
                CheckType.NETTY_C);
        this.register(
                PacketLimitException.class,
                (result, result2) -> new PacketLimitCheck(result, result2, true),
                CheckType.NETTY_B);
        this.register(
                PacketLimitException.class,
                (result, result2) -> new PacketLimitCheck(result, result2, false),
                CheckType.NETTY_B);
        if (matches) {
            this.register(Client.CLICK_WINDOW, WindowACheck::new, CheckType.WINDOW_A);
        }
        if (version.is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_12)) {
            this.register(Client.CLICK_WINDOW, WindowBCheck::new, CheckType.WINDOW_B);
        }
        this.register(Client.CLICK_WINDOW, WindowCCheck::new, CheckType.WINDOW_C);
        this.register(Client.CLICK_WINDOW, WindowDCheck::new, CheckType.WINDOW_D);
        this.register(Client.CLICK_WINDOW, WindowECheck::new, CheckType.WINDOW_E);
        this.register(NBTDecodeEvent.class, Item0Check::new, CheckType.ITEM_0);
        this.register(ItemStackDecodeEvent.class, Item1Check::new, CheckType.ITEM_1);
        this.register(Client.PICK_ITEM_FROM_BLOCK, Item2Check::new, CheckType.ITEM_2);
        this.register(Client.PICK_ITEM_FROM_ENTITY, Item3Check::new, CheckType.ITEM_3);
        this.register(ItemStackDecodeEvent.class, ItemACheck::new, CheckType.ITEM_A);
        this.register(NBTDecodeEvent.class, ItemBCheck::new, CheckType.ITEM_B);
        this.register(NBTDecodeEvent.class, ItemCCheck::new, CheckType.ITEM_C);
        this.register(ItemStackDecodeEvent.class, ItemDCheck::new, CheckType.ITEM_D);
        this.register(NBTDecodeEvent.class, ItemECheck::new, CheckType.ITEM_E);
        this.register(NBTDecodeEvent.class, ItemFCheck::new, CheckType.ITEM_F);
        this.register(NBTDecodeEvent.class, ItemGCheck::new, CheckType.ITEM_G);
        this.register(ItemStackDecodeEvent.class, ItemHCheck::new, CheckType.ITEM_H);
        this.register(ItemStackDecodeEvent.class, ItemJCheck::new, CheckType.ITEM_J);
        this.register(ItemStackDecodeEvent.class, ItemKCheck::new, CheckType.ITEM_K);
        this.register(PacketPlayReceiveEvent.class, CreativeACheck::new, CheckType.CREATIVE_A);
        this.register(Client.CREATIVE_INVENTORY_ACTION, CreativeBCheck::new, CheckType.CREATIVE_B);
        this.register(Client.CREATIVE_INVENTORY_ACTION, CreativeCCheck::new, CheckType.CREATIVE_C);
        this.register(Client.CREATIVE_INVENTORY_ACTION, CreativeDCheck::new, CheckType.CREATIVE_D);
        this.register(ItemStackDecodeEvent.class, CreativeECheck::new, CheckType.CREATIVE_E);
        this.register(Client.CREATIVE_INVENTORY_ACTION, CreativeFCheck::new, CheckType.CREATIVE_F);
        this.register(Client.CREATIVE_INVENTORY_ACTION, CreativeGCheck::new, CheckType.CREATIVE_G);
        this.register(PacketPlayReceiveEvent.class, CreativeHCheck::new, CheckType.CREATIVE_H);
        this.register(PacketPlayReceiveEvent.class, CreativeICheck::new, CheckType.CREATIVE_I);
        this.register(Client.PLAYER_BLOCK_PLACEMENT, PlaceACheck::new, CheckType.PLACE_A);
        this.register(Client.UPDATE_SIGN, PlaceBCheck::new, CheckType.PLACE_B);
        if (version == ServerVersion.V_1_16
                || version == ServerVersion.V_1_16_1
                || version == ServerVersion.V_1_16_2
                || version == ServerVersion.V_1_16_3
                || version == ServerVersion.V_1_16_4
                || version == ServerVersion.V_1_16_5) {
            this.register(Client.PLAYER_DIGGING, PlaceCCheck::new, CheckType.PLACE_C);
        }
        this.register(PacketPlayReceiveEvent.class, FloodACheck::new, CheckType.FLOOD_A);
        this.register(PacketPlayReceiveEvent.class, FloodBCheck::new, CheckType.FLOOD_B);
        this.register(ItemStackDecodeEvent.class, FloodCCheck::new, CheckType.FLOOD_C);
        this.register(ItemStackDecodeEvent.class, FloodDCheck::new, CheckType.FLOOD_D);
        this.register(PlayerCommandPreprocessEvent.class, FloodFCheck::new, CheckType.FLOOD_F);
        this.register(ReadSlotsEvent.class, FloodGCheck::new, CheckType.FLOOD_G);
        this.register(Client.PLAYER_FLYING, PositionACheck::new, CheckType.POSITION_A);
        this.register(Client.PLAYER_FLYING, PositionBCheck::new, CheckType.POSITION_B);
        this.register(Client.PLAYER_FLYING, PositionCCheck::new, CheckType.POSITION_C);
        this.register(Client.PLAYER_FLYING, PositionDCheck::new, CheckType.POSITION_D);
        this.register(Client.PLUGIN_MESSAGE, PayloadACheck::new, CheckType.PAYLOAD_A);
        this.register(Client.PLUGIN_MESSAGE, PayloadBCheck::new, CheckType.PAYLOAD_B);
        this.register(Client.PLUGIN_MESSAGE, PayloadCCheck::new, CheckType.PAYLOAD_C);
        this.register(
                Client.PLUGIN_MESSAGE,
                (result, result2) -> new PayloadDirectionCheck(result, result2, true),
                CheckType.PAYLOAD_D);
        this.register(
                Client.PLUGIN_MESSAGE,
                (result, result2) -> new PayloadDirectionCheck(result, result2, false),
                CheckType.PAYLOAD_G);
        if (matches) {
            this.register(Client.PLUGIN_MESSAGE, PayloadECheck::new, CheckType.PAYLOAD_E);
        }
        this.register(Client.PLUGIN_MESSAGE, PayloadFCheck::new, CheckType.PAYLOAD_F);
        this.register(Client.EDIT_BOOK, BookACheck::new, CheckType.BOOK_A);
        this.register(Client.EDIT_BOOK, BookBCheck::new, CheckType.BOOK_B);
        this.register(Client.EDIT_BOOK, BookCCheck::new, CheckType.BOOK_C);
        this.register(Client.EDIT_BOOK, Book0Check::new, CheckType.BOOK_0);
        this.register(Client.TAB_COMPLETE, TabACheck::new, CheckType.TAB_A);
        this.register(Client.TAB_COMPLETE, TabBCheck::new, CheckType.TAB_B);
        this.register(PlayerCommandPreprocessEvent.class, CommandACheck::new, CheckType.COMMAND_A);
        if (version == ServerVersion.V_1_21_2 || version == ServerVersion.V_1_21_3) {
            this.register(
                    Client.CRAFT_RECIPE_REQUEST,
                    (result2, result3) -> new BukkitCheckManager.RecipeACheck(result2, result3),
                    CheckType.RECIPE_A);
            this.register(
                    Client.SET_DISPLAYED_RECIPE,
                    (result2, result3) -> new BukkitCheckManager.RecipeBCheck(result2, result3),
                    CheckType.RECIPE_B);
        }
        this.sortByPriority();
    }

    static {
        String[] texts = new String[] {"MC|BEdit", null};
        texts[1] = "MC|BSign";
        VALUES = Lists.newArrayList(texts);
    }

    class RecipeACheck extends RecipeCheck<WrapperPlayClientCraftRecipeRequest> {

        RecipeACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
            super(protocolPlayer, checkOptions);
        }

        public int getRecipeId(
                WrapperPlayClientCraftRecipeRequest wrapperPlayClientCraftRecipeRequest) {
            return wrapperPlayClientCraftRecipeRequest.getRecipeId().getId();
        }
    }

    class RecipeBCheck extends RecipeCheck<WrapperPlayClientSetDisplayedRecipe> {

        RecipeBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
            super(protocolPlayer, checkOptions);
        }

        public int getRecipeId(
                WrapperPlayClientSetDisplayedRecipe wrapperPlayClientSetDisplayedRecipe) {
            return wrapperPlayClientSetDisplayedRecipe.getRecipeId().getId();
        }
    }
}
