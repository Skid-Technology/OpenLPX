package it.ytnoos.lpx.check.impl.book;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;

public class BookACheck extends AbstractCheck<WrapperPlayClientEditBook> {

    private int maxSize;

    public BookACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 15);
        this.maxSize = this.calculateLimit((String) "max", ((int) value));
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        String title = wrapperPlayClientEditBook.getTitle();
        if (title != null) {
            int size = title.length();
            if (size > this.maxSize) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("title", title), null, null};
                flagInfos[1] = FlagInfo.of("length", size);
                flagInfos[2] = FlagInfo.of("max", this.maxSize);
                this.flagViolation(wrapperPlayClientEditBook, flagInfos);
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }
}
