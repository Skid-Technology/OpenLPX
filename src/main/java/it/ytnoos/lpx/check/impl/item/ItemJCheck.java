package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.FireworkExplosion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemFireworks;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ItemJCheck extends AbstractCheck<ItemStackDecodeEvent> {

    private int limit;

    private int limit2;

    public ItemJCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        this.limit = checkConfiguration.getInt(this.createText((String) "max-list"), 12);
        checkConfiguration = this.getCheckConfiguration();
        this.limit2 = checkConfiguration.getInt(this.createText((String) "max-array"), 12);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        CheckResult checkResult;
        if (protocolPlayer.usesComponents()) {
            checkResult = this.check(itemStack, itemStackDecodeEvent);
        } else {
            checkResult = this.check2(itemStack, itemStackDecodeEvent);
        }
        return checkResult;
    }

    private CheckResult check(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        FireworkExplosion fireworkExplosion =
                (FireworkExplosion)
                        itemStack.getComponentOr(ComponentTypes.FIREWORK_EXPLOSION, null);
        if (fireworkExplosion != null) {
            return this.check(fireworkExplosion, itemStackDecodeEvent);
        }
        ItemFireworks itemFireworks =
                (ItemFireworks) itemStack.getComponentOr(ComponentTypes.FIREWORKS, null);
        if (itemFireworks == null) {
            return CheckResult.ALLOW;
        }
        List<FireworkExplosion> explosions = itemFireworks.getExplosions();
        if (explosions.size() > this.limit) {
            FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("explosions", explosions.size())};
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        for (FireworkExplosion fireworkExplosion2 : explosions) {
            CheckResult checkResult = this.check(fireworkExplosion2, itemStackDecodeEvent);
            if (checkResult != CheckResult.ALLOW) {
                return checkResult;
            }
        }
        return CheckResult.ALLOW;
    }

    private CheckResult check(
            FireworkExplosion fireworkExplosion, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (fireworkExplosion.getColors().size() > this.limit2) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {FlagInfo.of("colors", fireworkExplosion.getColors().size())};
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        } else if (fireworkExplosion.getFadeColors().size() > this.limit2) {
            FlagInfo[] flagInfos2 =
                    new FlagInfo[] {
                        FlagInfo.of("fade-colors", fireworkExplosion.getFadeColors().size())
                    };
            this.flagViolation(itemStackDecodeEvent, flagInfos2);
            return CheckResult.DENY;
        } else {
            return CheckResult.ALLOW;
        }
    }

    private CheckResult check2(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        NBTCompound nBT = itemStack.getNBT();
        if (nBT == null) {
            return CheckResult.ALLOW;
        }
        NBTCompound compoundTagOrNull = nBT.getCompoundTagOrNull("Explosion");
        if (compoundTagOrNull != null) {
            return this.check(compoundTagOrNull, itemStackDecodeEvent);
        }
        NBTCompound compoundTagOrNull2 = nBT.getCompoundTagOrNull("Fireworks");
        if (compoundTagOrNull2 == null) {
            return CheckResult.ALLOW;
        }
        NBTList compoundListTagOrNull = compoundTagOrNull2.getCompoundListTagOrNull("Explosions");
        if (compoundListTagOrNull == null) {
            return CheckResult.ALLOW;
        }
        if (compoundListTagOrNull.size() > this.limit) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {FlagInfo.of("explosions", compoundListTagOrNull.size())};
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        for (NBTCompound nBTCompound :
                ((Iterable<NBTCompound>) ((Iterable<?>) compoundListTagOrNull.getTags()))) {
            CheckResult checkResult = this.check(nBTCompound, itemStackDecodeEvent);
            if (checkResult != CheckResult.ALLOW) {
                return checkResult;
            }
        }
        return CheckResult.ALLOW;
    }

    private CheckResult check(NBTCompound nBTCompound, ItemStackDecodeEvent itemStackDecodeEvent) {
        NBTCompound nBTCompound2 = nBTCompound;
        String text = (String) "Colors";
        String text2 = text;
        NBTCompound nBTCompound3 = nBTCompound2;
        CheckResult checkResult = this.check(nBTCompound3, text2, itemStackDecodeEvent);
        if (checkResult != CheckResult.ALLOW) {
            return checkResult;
        }
        String text3 = "FadeColors";
        nBTCompound2 = nBTCompound;
        text = text3;
        String text4 = text;
        NBTCompound nBTCompound4 = nBTCompound2;
        return this.check(nBTCompound4, text4, itemStackDecodeEvent);
    }

    private CheckResult check(
            NBTCompound nBTCompound, String text, ItemStackDecodeEvent itemStackDecodeEvent) {
        NBT tagOrNull = nBTCompound.getTagOrNull(text);
        if (tagOrNull instanceof NBTIntArray) {
            int value = ((NBTIntArray) tagOrNull).getValue().length;
            if (value > this.limit2) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of(text.toLowerCase(), value)};
                this.flagViolation(itemStackDecodeEvent, flagInfos);
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }

    public boolean isPreVia() {
        return true;
    }
}
