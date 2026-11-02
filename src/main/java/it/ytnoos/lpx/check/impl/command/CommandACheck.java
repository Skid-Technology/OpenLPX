package it.ytnoos.lpx.check.impl.command;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.utils.string.StringUtils;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandACheck extends AbstractCheck<PlayerCommandPreprocessEvent> {

    private static final String[] TEXTS;

    private List<String> values;

    public CommandACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        String[] texts = TEXTS;
        this.values = this.createValues((String) "commands", texts);
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer,
            PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        String message = playerCommandPreprocessEvent.getMessage();
        if (message != null && this.values != null) {
            for (String text : this.values) {
                CharSequence charSequence = ((CharSequence) message);
                if (StringUtils.testCharSequence(charSequence, ((CharSequence) text))) {
                    playerCommandPreprocessEvent.setCancelled(true);
                    FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("command", message)};
                    this.flagViolation(playerCommandPreprocessEvent, flagInfos);
                    return CheckResult.DENY;
                }
            }
            return CheckResult.ALLOW;
        } else {
            return CheckResult.ALLOW;
        }
    }

    static {
        String[] texts = new String[22];
        texts[0] = "//calc";
        texts[1] = "//calculate";
        texts[2] = "//eval";
        texts[3] = "//evaluate";
        texts[4] = "//solve";
        texts[5] = "/hd readtext";
        texts[6] = "/holo readtext";
        texts[7] = "/hologram readtext";
        texts[8] = "/holograms readtext";
        texts[9] = "/holographicdisplays readtext";
        texts[10] = "/pex promote";
        texts[11] = "/pex demote";
        texts[12] = "/promote";
        texts[13] = "/demote";
        texts[14] = "/execute";
        texts[15] = "/mv ^";
        texts[16] = "/mv help ^";
        texts[17] = "/mvhelp ^";
        texts[18] = "/mv <";
        texts[19] = "/mv help <";
        texts[20] = "/mvhelp <";
        texts[21] = "/$";
        TEXTS = texts;
    }
}
