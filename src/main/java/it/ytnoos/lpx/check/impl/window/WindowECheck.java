package it.ytnoos.lpx.check.impl.window;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.HashedStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class WindowECheck extends AbstractCheck<WrapperPlayClientClickWindow> {

    private int limit;

    private int limit2;

    public WindowECheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = Integer.valueOf((byte) 90);
        String text = (String) "max-hashed-slot-changes";
        String text2 = text;
        this.limit = this.calculateLimit(text2, ((int) value));
        Integer value2 = Integer.valueOf((short) 128);
        String text3 = "max-hashed-components-per-stack";
        text = text3;
        String text4 = text;
        this.limit2 = this.calculateLimit(text4, ((int) value2));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        Map<Integer, Optional<HashedStack>> hashedSlots =
                wrapperPlayClientClickWindow.getHashedSlots();
        if (hashedSlots != null) {
            if (hashedSlots.size() > this.limit) {
                FlagInfo[] flagInfos =
                        new FlagInfo[] {
                            FlagInfo.of("hashed-slot-changes", hashedSlots.size()), null, null
                        };
                flagInfos[1] = FlagInfo.of("max-hashed-slot-changes", this.limit);
                flagInfos[2] =
                        FlagInfo.of(
                                "click-type",
                                wrapperPlayClientClickWindow.getWindowClickType().name());
                this.flagViolation(wrapperPlayClientClickWindow, flagInfos);
                return CheckResult.DENY;
            }
            for (Entry<Integer, Optional<HashedStack>> entry : hashedSlots.entrySet()) {
                String text = String.valueOf(entry.getKey());
                String text2 = text;
                text = (String) "slot[" + text2 + "]";
                HashedStack hashedStack = (HashedStack) ((Optional) entry.getValue()).orElse(null);
                String text3 = text;
                CheckResult checkResult =
                        this.check(wrapperPlayClientClickWindow, text3, hashedStack);
                if (checkResult == CheckResult.DENY) {
                    return checkResult;
                }
            }
        }
        Optional<HashedStack> carriedHashedStack =
                wrapperPlayClientClickWindow.getCarriedHashedStack();
        HashedStack hashedStack2 =
                carriedHashedStack == null ? null : (HashedStack) carriedHashedStack.orElse(null);
        return this.check(wrapperPlayClientClickWindow, (String) "carried", hashedStack2);
    }

    private CheckResult check(
            WrapperPlayClientClickWindow wrapperPlayClientClickWindow,
            Object result,
            HashedStack hashedStack) {
        if (hashedStack == null) {
            return CheckResult.ALLOW;
        } else {
            int size2 = hashedStack.getComponents().getAddedComponents().size();
            int size3 = hashedStack.getComponents().getRemovedComponents().size();
            int value = size2 + size3;
            if (value > this.limit2) {
                FlagInfo[] flagInfos = new FlagInfo[6];
                flagInfos[0] = FlagInfo.of("identifier", result);
                flagInfos[1] = FlagInfo.of("item", hashedStack.getItem().getName().toString());
                flagInfos[2] = FlagInfo.of("added", size2);
                flagInfos[3] = FlagInfo.of("removed", size3);
                flagInfos[4] = FlagInfo.of("component-changes", value);
                flagInfos[5] = FlagInfo.of("max-component-changes", this.limit2);
                this.flagViolation(wrapperPlayClientClickWindow, flagInfos);
                return CheckResult.DENY;
            } else {
                return CheckResult.ALLOW;
            }
        }
    }
}
