package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.config.CreativeExemption;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.utils.ItemDecodeLimits;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Item1Check extends AbstractCheck<ItemStackDecodeEvent> {

    private static final String[] TEXTS;

    private CreativeExemption creativeExemption;

    private ItemDecodeLimits itemDecodeLimits;

    private Set<String> values;

    public Item1Check(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        String string =
                this.getOptions()
                        .getCheckOptions()
                        .getConfig()
                        .getString(
                                "options.disable-item-decoding", CreativeExemption.DEFAULT.name());
        this.creativeExemption = CreativeExemption.fromString(string);
        this.itemDecodeLimits = ItemDecodeLimits.defaults();
        HashSet values2 = new HashSet();
        this.values = values2;
        this.values.addAll(
                this.getOptions().getCheckOptions().getConfig().isSet("options.creative-exempts")
                        ? this.getOptions()
                                .getCheckOptions()
                                .getConfig()
                                .getStringList("options.creative-exempts")
                        : Arrays.asList(TEXTS));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player != null && !protocolPlayer.isProcessingPayload()) {
            boolean matches = player.getGameMode() == GameMode.CREATIVE;
            int decodedSizeBytes = itemStackDecodeEvent.getItemStack().getDecodedSizeBytes();
            boolean matches2 =
                    decodedSizeBytes >= this.itemDecodeLimits.calculateLimit(protocolPlayer);
            switch (this.creativeExemption) {
                case NONE:
                    return CheckResult.ALLOW;
                case SMART:
                    if (!matches2 || matches) {
                        return CheckResult.ALLOW;
                    }
                    break;
                case ALL:
                    if (matches) {
                        return CheckResult.ALLOW;
                    }
                case CREATIVE:
            }
            ItemStack itemStack = itemStackDecodeEvent.getItemStack();
            itemStack.setNBT(null);
            if (matches && this.creativeExemption == CreativeExemption.CREATIVE) {
                Set<
                                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol
                                                .component.ComponentType<
                                        ?>>
                        values2 = itemStack.getComponents().getPatches().keySet();
                values2.removeIf(result -> !this.values.contains(result.getName().getKey()));
            } else {
                itemStack.setComponents(null);
            }
            itemStackDecodeEvent.setItemStack(itemStack);
            return CheckResult.CHANGE;
        } else {
            return CheckResult.ALLOW;
        }
    }

    public boolean isPreVia() {
        return true;
    }

    static {
        String[] texts = new String[] {"custom_name", null, null};
        texts[1] = "lore";
        texts[2] = "enchantments";
        TEXTS = texts;
    }
}
