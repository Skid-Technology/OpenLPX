package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

public class ItemBCheck extends AbstractCheck<NBTDecodeEvent> {

    private int limit;

    private int limit2;

    public ItemBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = Integer.valueOf((byte) 50);
        String text = (String) "max";
        String text2 = text;
        this.limit = this.calculateLimit(text2, ((int) value));
        Integer value2 = Integer.valueOf((byte) 100);
        String text3 = "max-modern";
        text = text3;
        String text4 = text;
        this.limit2 = this.calculateLimit(text4, ((int) value2));
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, NBTDecodeEvent nBTDecodeEvent) {
        NBT nbt = nBTDecodeEvent.getNbt();
        if (!(nbt instanceof NBTCompound)) {
            return CheckResult.ALLOW;
        }
        NBTList stringListTagOrNull = ((NBTCompound) nbt).getStringListTagOrNull("pages");
        if (stringListTagOrNull == null) {
            return CheckResult.ALLOW;
        }
        boolean newerThanOrEquals =
                protocolPlayer
                        .getUser()
                        .getClientVersion()
                        .isNewerThanOrEquals(ClientVersion.V_1_14);
        int value = newerThanOrEquals ? this.limit2 : this.limit;
        if (stringListTagOrNull.size() <= value) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos =
                new FlagInfo[] {FlagInfo.of("pages", stringListTagOrNull.size()), null, null};
        flagInfos[1] = FlagInfo.of("max", value);
        flagInfos[2] = FlagInfo.of("modern", newerThanOrEquals);
        this.flagViolation(nBTDecodeEvent, flagInfos);
        return CheckResult.DENY;
    }

    public boolean isPreVia() {
        return true;
    }
}
