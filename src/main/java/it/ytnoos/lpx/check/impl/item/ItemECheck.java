package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.player.ProtocolPlayerImpl;
import it.ytnoos.lpx.utils.string.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class ItemECheck extends AbstractCheck<NBTDecodeEvent> {

    private static final String[] TEXTS;

    private boolean clear;

    private Map<String, Integer> entries;

    public ItemECheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Boolean matches = ((Boolean) (boolean) true);
        this.clear = this.testEnabled((String) "clear", ((boolean) matches));
        HashMap entries2 = new HashMap();
        this.entries = entries2;
        String[] texts = TEXTS;
        for (String text :
                (Iterable<String>) ((Iterable) this.createValues((String) "occurrences", texts))) {
            String[] texts2 = text.split(",");
            if (texts2.length < 2) {
                ProtocolPlayerImpl protocolPlayerImpl = (ProtocolPlayerImpl) this.player;
                Logger logger = protocolPlayerImpl.getLpx().getLogger();
                logger.warning(() -> text + " is not a valid ItemE parameter!");
            } else {
                try {
                    this.entries.put(texts2[0], Integer.parseInt(texts2[1]));
                } catch (NumberFormatException numberFormatException) {
                    ProtocolPlayerImpl protocolPlayerImpl2 = (ProtocolPlayerImpl) this.player;
                    protocolPlayerImpl2
                            .getLpx()
                            .getLogger()
                            .severe("Second parameter for ItemE must be a number!");
                }
            }
        }
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
        for (int index = 0; index < stringListTagOrNull.size(); index++) {
            NBTString nBTString = (NBTString) stringListTagOrNull.getTag(index);
            String value = nBTString.getValue();
            for (Entry<String, Integer> entry : this.entries.entrySet()) {
                Object key = entry.getKey();
                CharSequence charSequence = (CharSequence) key;
                CharSequence charSequence2 = ((CharSequence) value);
                int value2 = StringUtils.calculateLimit(charSequence2, charSequence);
                if (value2 > (Integer) entry.getValue()) {
                    if (this.clear) {
                        nBTString.setValue("");
                    }
                    FlagInfo[] flagInfos = new FlagInfo[5];
                    flagInfos[0] = FlagInfo.of("pages", stringListTagOrNull.size());
                    flagInfos[1] = FlagInfo.of("page-index", index);
                    flagInfos[2] = FlagInfo.of("page-length", value.getBytes().length);
                    flagInfos[3] = FlagInfo.of("occurrence", entry.getKey());
                    flagInfos[4] = FlagInfo.of("matches", value2);
                    this.flagViolation(nBTDecodeEvent, flagInfos);
                    return CheckResult.DENY;
                }
            }
        }
        return CheckResult.ALLOW;
    }

    public boolean isPreVia() {
        return true;
    }

    static {
        String[] texts = new String[4];
        texts[0] = "translate,3";
        texts[1] = "with,3";
        texts[2] = "extra,15";
        texts[3] = "{,15";
        TEXTS = texts;
    }
}
