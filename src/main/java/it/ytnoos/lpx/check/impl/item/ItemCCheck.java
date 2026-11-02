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
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;

public class ItemCCheck extends AbstractCheck<NBTDecodeEvent> {

    private boolean enabled;

    public ItemCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    private static String normalizeEnchantmentName(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < text.length(); index++) {
            char value = text.charAt(index);
            Integer value2 = Integer.valueOf(value);
            if (testLimit(((int) value2))) {
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    private static boolean testLimit(int value) {
        if (value < 32) {
            return false;
        }
        return switch (Character.getType((char) value)) {
            case 0, 15, 16, 18, 19 -> false;
            default -> true;
        };
    }

    private static boolean testText(String text) {
        for (int index = 0; index < text.length(); index++) {
            char value = text.charAt(index);
            Integer value2 = Integer.valueOf(value);
            if (!testLimit(((int) value2))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void loadOptions() {
        Boolean matches = ((Boolean) (boolean) true);
        this.enabled = this.testEnabled((String) "remove-ascii", ((boolean) matches));
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
        boolean matches = false;
        for (int index = 0; index < stringListTagOrNull.size(); index++) {
            NBTString nBTString = (NBTString) stringListTagOrNull.getTag(index);
            String value = nBTString.getValue();
            String value2 = nBTString.getValue();
            if (!testText(value2)) {
                FlagInfo[] flagInfos =
                        new FlagInfo[] {
                            FlagInfo.of("pages", stringListTagOrNull.size()), null, null
                        };
                flagInfos[1] = FlagInfo.of("page-index", index);
                flagInfos[2] = FlagInfo.of("page-length", value.length());
                if (this.flagViolation(nBTDecodeEvent, flagInfos)) {
                    return CheckResult.DENY;
                }
                if (this.enabled) {
                    nBTString.setValue(normalizeEnchantmentName(value));
                    matches = true;
                }
            }
        }
        return matches ? CheckResult.CHANGE : CheckResult.ALLOW;
    }

    public boolean isPreVia() {
        return true;
    }
}
