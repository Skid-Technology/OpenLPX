package it.ytnoos.lpx.check.impl.tab;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import it.ytnoos.lpx.utils.string.StringUtils;

import java.util.List;
import java.util.function.Predicate;

public class TabACheck extends AbstractCheck<WrapperPlayClientTabComplete> {

    private static final String[] TEXTS;

    private static final String[] TEXTS2;

    private List<String> values;

    private List<String> contains;

    public TabACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        String[] texts = TEXTS;
        String text = (String) "starts";
        String text2 = text;
        this.values = this.createValues(text2, texts);
        String[] texts2 = TEXTS2;
        String text3 = "contains";
        text = text3;
        String text4 = text;
        this.contains = this.createValues(text4, texts2);
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientTabComplete wrapperPlayClientTabComplete) {
        String text = wrapperPlayClientTabComplete.getText();
        List values2 = this.values;
        Predicate predicate =
                result -> {
                    String text2 = ((String) result);
                    String text3 = text2;
                    String text4 = text3;
                    String text5 = text4;
                    String text6 = text;
                    String text7 = text6;
                    String text8 = text7;
                    String text9 = text8;
                    CharSequence charSequence = text9;
                    CharSequence charSequence2 = text5;
                    CharSequence charSequence3 = charSequence;
                    return StringUtils.testCharSequence(charSequence3, charSequence2);
                };
        List values3 = values2;
        values2 = values3;
        List values4 = values2;
        boolean matches = this.testPredicate(values4, predicate);
        boolean matches2 = matches;
        if (!matches) {
            values2 = this.contains;
            Predicate predicate2 =
                    result -> {
                        String text2 = ((String) result);
                        String text3 = text2;
                        String text4 = text3;
                        String text5 = text4;
                        String text6 = text;
                        String text7 = text6;
                        String text8 = text7;
                        String text9 = text8;
                        CharSequence charSequence = text9;
                        CharSequence charSequence2 = text5;
                        CharSequence charSequence3 = charSequence;
                        return StringUtils.testCharSequence2(charSequence3, charSequence2);
                    };
            List values5 = values2;
            values2 = values5;
            List values6 = values2;
            matches2 = this.testPredicate(values6, predicate2);
        }
        if (matches2) {
            FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("text", text)};
            this.flagViolation(wrapperPlayClientTabComplete, flagInfos);
            return CheckResult.DENY;
        } else {
            return CheckResult.ALLOW;
        }
    }

    private boolean testPredicate(List values, Predicate predicate) {
        for (String text : (Iterable<String>) ((Iterable) values)) {
            if (predicate.test(text)) {
                return true;
            }
        }
        return false;
    }

    static {
        String[] texts = new String[4];
        texts[0] = "/to ";
        texts[1] = "//to";
        texts[2] = "/calc ";
        texts[3] = "//calc";
        TEXTS = texts;
        String[] texts2 = new String[4];
        texts2[0] = "targetoffset";
        texts2[1] = "for(";
        texts2[2] = "^(.";
        texts2[3] = "*.";
        TEXTS2 = texts2;
    }
}
