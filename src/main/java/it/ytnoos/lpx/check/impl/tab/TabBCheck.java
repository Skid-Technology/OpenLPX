package it.ytnoos.lpx.check.impl.tab;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;

import java.util.ArrayList;
import java.util.List;

public class TabBCheck extends AbstractCheck<WrapperPlayClientTabComplete> {

    private static final String[] TEXTS;

    private List<String> values;

    private int maxLength;

    public TabBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
        ArrayList values2 = new ArrayList();
        this.values = values2;
    }

    @Override
    public void loadOptions() {
        Integer value = Integer.valueOf((short) 256);
        String text = (String) "max-length";
        String text2 = text;
        this.maxLength = this.calculateLimit(text2, ((int) value));
        String[] texts = TEXTS;
        String text3 = "characters";
        text = text3;
        String text4 = text;
        this.values = this.createValues(text4, texts);
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientTabComplete wrapperPlayClientTabComplete) {
        String text = wrapperPlayClientTabComplete.getText();
        if (text.length() > this.maxLength) {
            return CheckResult.DENY;
        }
        for (String text2 : this.values) {
            if (text.contains(text2)) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("text", text), null};
                flagInfos[1] = FlagInfo.of("invalid-char", text2);
                this.flagViolation(wrapperPlayClientTabComplete, flagInfos);
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }

    static {
        String[] texts = new String[7];
        texts[0] = "[";
        texts[1] = "]";
        texts[2] = "{";
        texts[3] = "}";
        texts[4] = "@";
        texts[5] = "=";
        texts[6] = "nbt";
        TEXTS = texts;
    }
}
