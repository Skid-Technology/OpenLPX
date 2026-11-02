package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map.Entry;

public class ItemGCheck extends AbstractCheck<NBTDecodeEvent> {

    private int limit;

    private int limit2;

    private boolean enabled;

    public ItemGCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        this.limit = checkConfiguration.getInt(this.createText((String) "max-translate"), 1);
        checkConfiguration = this.getCheckConfiguration();
        this.limit2 = checkConfiguration.getInt(this.createText((String) "max-keybind"), 1);
        this.enabled =
                this.player.getUser().getClientVersion().isOlderThan(ClientVersion.V_1_16)
                        && PacketEvents.getAPI()
                                .getServerManager()
                                .getVersion()
                                .isNewerThanOrEquals(ServerVersion.V_1_16);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, NBTDecodeEvent nBTDecodeEvent) {
        if (protocolPlayer.usesComponents()) {
            return CheckResult.ALLOW;
        }
        NBT nbt = nBTDecodeEvent.getNbt();
        return this.check((String) "", nbt, nBTDecodeEvent);
    }

    private CheckResult check(String text, NBT nBT, NBTDecodeEvent nBTDecodeEvent) {
        if (nBT instanceof NBTString nBTString2) {
            NBTString nBTString = nBTString2;
            return this.check(text, nBTString, nBTDecodeEvent);
        } else if (nBT instanceof NBTList) {
            for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) ((NBTList) nBT).getTags()))) {
                CheckResult checkResult = this.check(text, nBT2, nBTDecodeEvent);
                if (checkResult == CheckResult.DENY) {
                    return checkResult;
                }
            }
            return CheckResult.ALLOW;
        } else if (nBT instanceof NBTCompound) {
            for (Entry<String, NBT> entry : ((NBTCompound) nBT).getTags().entrySet()) {
                String text2 = (String) entry.getKey();
                Object value = entry.getValue();
                NBT nBT3 = (NBT) value;
                NBT nBT4 = nBT3;
                String text3 = text2;
                text2 = text3;
                nBT3 = nBT4;
                NBT nBT5 = nBT3;
                String text4 = text2;
                CheckResult checkResult2 = this.check(text4, nBT5, nBTDecodeEvent);
                if (checkResult2 == CheckResult.DENY) {
                    return checkResult2;
                }
            }
            return CheckResult.ALLOW;
        } else {
            return CheckResult.ALLOW;
        }
    }

    private CheckResult check(String text, NBTString nBTString, NBTDecodeEvent nBTDecodeEvent) {
        String value = nBTString.getValue();
        if (value.isEmpty() || value.charAt(0) == '"') {
            return CheckResult.ALLOW;
        }
        if (value.contains("{") && value.contains("}")) {
            int value2 = this.limit + 1;
            Integer value3 = ((Integer) value2);
            String text2 = value;
            String text3 = (String) "translate";
            String text4 = text3;
            String text5 = text2;
            int value4 = this.calculateLimit(text5, text4, ((int) value3));
            if (value4 == -1) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("identifier", text), null};
                flagInfos[1] = FlagInfo.of("visited", "translate");
                this.flagViolation(nBTDecodeEvent, flagInfos);
                return CheckResult.DENY;
            }
            if (value4 > this.limit) {
                int value5 = this.limit + 1;
                Integer value6 = ((Integer) value5);
                String text6 = "with";
                text2 = value;
                text3 = text6;
                String text7 = text3;
                String text8 = text2;
                int value7 = this.calculateLimit(text8, text7, ((int) value6));
                if (value7 == -1) {
                    FlagInfo[] flagInfos2 = new FlagInfo[] {FlagInfo.of("identifier", text), null};
                    flagInfos2[1] = FlagInfo.of("visited", "with");
                    this.flagViolation(nBTDecodeEvent, flagInfos2);
                    return CheckResult.DENY;
                }
                if (value7 > this.limit) {
                    FlagInfo[] flagInfos3 =
                            new FlagInfo[] {FlagInfo.of("identifier", text), null, null};
                    flagInfos3[1] = FlagInfo.of("translate", value4);
                    flagInfos3[2] = FlagInfo.of("with", value7);
                    this.flagViolation(nBTDecodeEvent, flagInfos3);
                    return CheckResult.DENY;
                }
            }
            int value8 = this.limit2 + 1;
            Integer value9 = ((Integer) value8);
            String text9 = "keybind";
            text2 = value;
            text3 = text9;
            String text10 = text3;
            String text11 = text2;
            int value10 = this.calculateLimit(text11, text10, ((int) value9));
            if (value10 == -1) {
                FlagInfo[] flagInfos4 = new FlagInfo[] {FlagInfo.of("identifier", text), null};
                flagInfos4[1] = FlagInfo.of("visited", "keybind");
                this.flagViolation(nBTDecodeEvent, flagInfos4);
                return CheckResult.DENY;
            } else if (value10 > this.limit2) {
                FlagInfo[] flagInfos5 = new FlagInfo[] {FlagInfo.of("identifier", text), null};
                flagInfos5[1] = FlagInfo.of("keybind", value10);
                this.flagViolation(nBTDecodeEvent, flagInfos5);
                return CheckResult.DENY;
            } else {
                return CheckResult.ALLOW;
            }
        } else {
            return CheckResult.ALLOW;
        }
    }

    private int calculateLimit(String text, String text2, int value) {
        int value2 = 0;
        int value3 = 0;
        int value4 = 0;
        int size = text2.length();
        while (true) {
            int value5 = text.indexOf(text2, value3);
            value3 = value5;
            if (value5 == -1) {
                return value2;
            }
            if (++value4 > 200) {
                return -1;
            }
            char value6 = value3 > 0 ? text.charAt(value3 - 1) : '{';
            char value7 = value3 + size < text.length() ? text.charAt(value3 + size) : ' ';
            boolean matches = value6 == '"' && (value7 == '"' || value7 == '\\');
            boolean matches2 = matches;
            if (!matches && this.enabled) {
                matches2 = value6 == '\'' && value7 == '\'' || testState() && value7 == ':';
            }
            if (matches2) {
                if (++value2 >= value) {
                    return value2;
                }
            }
            value3 += size;
        }
    }

    private static boolean testState() {
        return false;
    }

    public boolean isPreVia() {
        return true;
    }
}
