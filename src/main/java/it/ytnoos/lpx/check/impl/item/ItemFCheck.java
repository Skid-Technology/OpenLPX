package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;

public class ItemFCheck extends AbstractCheck<NBTDecodeEvent> {

    private boolean replace;

    public ItemFCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Boolean matches = ((Boolean) (boolean) true);
        this.replace = this.testEnabled((String) "replace", ((boolean) matches));
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, NBTDecodeEvent nBTDecodeEvent) {
        if (!(nBTDecodeEvent.getNbt() instanceof NBTCompound)) {
            return CheckResult.ALLOW;
        }
        NBTList stringListTagOrNull =
                ((NBTCompound) nBTDecodeEvent.getNbt()).getStringListTagOrNull("pages");
        if (stringListTagOrNull == null) {
            return CheckResult.ALLOW;
        }
        if (!this.replace) {
            return CheckResult.ALLOW;
        }
        for (int index = 0; index < stringListTagOrNull.size(); index++) {
            NBTString nBTString = (NBTString) stringListTagOrNull.getTag(index);
            nBTString.setValue(nBTString.getValue().replace('.', '․'));
        }
        return CheckResult.CHANGE;
    }

    public boolean isPreVia() {
        return true;
    }
}
