package it.ytnoos.lpx.check.impl.flood;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;

import org.bukkit.configuration.file.FileConfiguration;

public class FloodDCheck extends AbstractCheck<ItemStackDecodeEvent> {

    private RollingIntervalCounter rollingIntervalCounter;

    private int limit;

    private RollingIntervalCounter rollingIntervalCounter2;

    public FloodDCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        FileConfiguration fileConfiguration = ((FileConfiguration) this.getCheckConfiguration());
        RollingIntervalCounter rollingIntervalCounter3 =
                new RollingIntervalCounter(
                        fileConfiguration.getInt(this.createText((String) "max"), 13));
        this.rollingIntervalCounter = rollingIntervalCounter3;
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        this.limit = checkConfiguration.getInt(this.createText((String) "big-threshold"), 4000);
        fileConfiguration = ((FileConfiguration) this.getCheckConfiguration());
        RollingIntervalCounter rollingIntervalCounter4 =
                new RollingIntervalCounter(
                        fileConfiguration.getInt(this.createText((String) "max-big"), 6));
        this.rollingIntervalCounter2 = rollingIntervalCounter4;
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        if (itemStack.getType() != ItemTypes.WRITABLE_BOOK) {
            return CheckResult.ALLOW;
        }
        if (this.rollingIntervalCounter.increment()) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {
                        FlagInfo.of("books-ps", this.rollingIntervalCounter.getMaxCounter())
                    };
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        if (this.rollingIntervalCounter.isOverLimit()) {
            return CheckResult.DENY;
        }
        NBTCompound nBT = itemStack.getNBT();
        if (nBT != null && nBT.getTags().containsKey("pages")) {
            int decodedSizeBytes = itemStack.getDecodedSizeBytes();
            if (decodedSizeBytes <= 0) {
                return CheckResult.ALLOW;
            }
            if (decodedSizeBytes > this.limit) {
                if (this.rollingIntervalCounter2.increment()) {
                    FlagInfo[] flagInfos2 =
                            new FlagInfo[] {
                                FlagInfo.of(
                                        "big-books-ps",
                                        this.rollingIntervalCounter2.getMaxCounter()),
                                null
                            };
                    flagInfos2[1] = FlagInfo.of("size", decodedSizeBytes);
                    this.flagViolation(itemStackDecodeEvent, flagInfos2);
                    return CheckResult.DENY;
                }
                if (this.rollingIntervalCounter2.isOverLimit()) {
                    return CheckResult.DENY;
                }
            }
            return CheckResult.ALLOW;
        } else {
            return CheckResult.ALLOW;
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
