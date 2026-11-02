package it.ytnoos.lpx.check.impl.place;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateSign;

import java.nio.charset.StandardCharsets;

public class PlaceBCheck extends AbstractCheck<WrapperPlayClientUpdateSign> {

    private double max;

    public PlaceBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Double value = ((Double) (double) 70.0);
        this.max = this.calculateValue((String) "max", ((double) value));
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientUpdateSign wrapperPlayClientUpdateSign) {
        String[] textLines = wrapperPlayClientUpdateSign.getTextLines();
        int value = textLines.length;
        for (int index = 0; index < value; index++) {
            String text = textLines[index];
            long value2 = text.getBytes(StandardCharsets.UTF_8).length;
            if (value2 > this.max) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("line", text), null};
                flagInfos[1] = FlagInfo.of("line-size", value2);
                this.flagViolation(wrapperPlayClientUpdateSign, flagInfos);
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }
}
