package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemLore;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.WrittenBookContent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Filterable;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.KeybindComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;

import org.bukkit.configuration.file.FileConfiguration;

public class ItemHCheck extends AbstractCheck<ItemStackDecodeEvent> {

    private int limit;

    private int limit2;

    public ItemHCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        this.limit = checkConfiguration.getInt(this.createText((String) "max-translate"), 1);
        checkConfiguration = this.getCheckConfiguration();
        this.limit2 = checkConfiguration.getInt(this.createText((String) "max-keybind"), 1);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (!protocolPlayer.usesComponents()) {
            return CheckResult.ALLOW;
        }
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        CheckResult checkResult = CheckResult.ALLOW;
        Component component =
                (Component) itemStack.getComponentOr(ComponentTypes.CUSTOM_NAME, null);
        if (component != null) {
            CheckResult checkResult2 =
                    this.check(component, (String) "custom_name", itemStackDecodeEvent);
            checkResult = checkResult2;
            if (checkResult2 == CheckResult.DENY) {
                return checkResult;
            }
        }
        Component component2 = (Component) itemStack.getComponentOr(ComponentTypes.ITEM_NAME, null);
        if (component2 != null) {
            CheckResult checkResult3 =
                    this.check(component2, (String) "item_name", itemStackDecodeEvent);
            checkResult = checkResult3;
            if (checkResult3 == CheckResult.DENY) {
                return checkResult;
            }
        }
        ItemLore itemLore = (ItemLore) itemStack.getComponentOr(ComponentTypes.LORE, null);
        if (itemLore != null) {
            for (int index = 0; index < itemLore.getLines().size(); index++) {
                Component component3 = itemLore.getLines().get(index);
                Component component4 = (Component) component3;
                int value = index;
                String text2 = (String) "lore[" + value + "]";
                Component component5 = component4;
                component4 = component5;
                Component component6 = component4;
                CheckResult checkResult4 = this.check(component6, text2, itemStackDecodeEvent);
                checkResult = checkResult4;
                if (checkResult4 == CheckResult.DENY) {
                    return checkResult;
                }
            }
        }
        WrittenBookContent writtenBookContent =
                (WrittenBookContent)
                        itemStack.getComponentOr(ComponentTypes.WRITTEN_BOOK_CONTENT, null);
        if (writtenBookContent != null) {
            for (int index2 = 0; index2 < writtenBookContent.getPages().size(); index2++) {
                Filterable filterable = (Filterable) writtenBookContent.getPages().get(index2);
                Object raw = filterable.getRaw();
                Component component7 = (Component) raw;
                int value2 = index2;
                String text3 = (String) "page[" + value2 + "]";
                String text4 = text3;
                Component component8 = component7;
                component7 = component8;
                Component component9 = component7;
                CheckResult checkResult5 = this.check(component9, text4, itemStackDecodeEvent);
                checkResult = checkResult5;
                if (checkResult5 == CheckResult.DENY) {
                    return checkResult;
                }
                if (filterable.getFiltered() != null) {
                    Object filtered = filterable.getFiltered();
                    component7 = (Component) filtered;
                    int value3 = index2;
                    text3 = (String) "page_filtered[" + value3 + "]";
                    String text5 = text3;
                    Component component10 = component7;
                    component7 = component10;
                    Component component11 = component7;
                    CheckResult checkResult6 = this.check(component11, text5, itemStackDecodeEvent);
                    checkResult = checkResult6;
                    if (checkResult6 == CheckResult.DENY) {
                        return checkResult;
                    }
                }
            }
        }
        return checkResult;
    }

    private CheckResult check(
            Component component, Object result, ItemStackDecodeEvent itemStackDecodeEvent) {
        int[] values = new int[2];
        this.handleLimits(component, values);
        boolean matches = values[0] > this.limit;
        boolean matches2 = values[1] > this.limit2;
        if (!matches && !matches2) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("identifier", result), null, null};
        flagInfos[1] = FlagInfo.of("translate", values[0]);
        flagInfos[2] = FlagInfo.of("keybind", values[1]);
        this.flagViolation(itemStackDecodeEvent, flagInfos);
        return CheckResult.DENY;
    }

    private boolean testLimits(int[] values) {
        return values[0] > this.limit || values[1] > this.limit2;
    }

    private void handleLimits(Component component, int[] values) {
        if (!this.testLimits(values)) {
            if (component instanceof TranslatableComponent translatableComponent) {
                values[(byte) 0]++;
                for (Component component2 : translatableComponent.args()) {
                    if (this.testLimits(values)) {
                        return;
                    }
                    this.handleLimits(component2, values);
                }
            }
            if (component instanceof KeybindComponent) {
                values[(byte) 1]++;
            }
            for (Component component3 : component.children()) {
                if (this.testLimits(values)) {
                    return;
                }
                this.handleLimits(component3, values);
            }
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
