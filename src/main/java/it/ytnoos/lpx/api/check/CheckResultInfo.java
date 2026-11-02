package it.ytnoos.lpx.api.check;

import it.ytnoos.lpx.api.check.abstraction.Check;

import org.jspecify.annotations.Nullable;

public class CheckResultInfo {

    private final CheckResult result;

    @Nullable private final Check<?> lastCheck;

    public CheckResultInfo(CheckResult checkResult, @Nullable Check<?> check) {
        this.result = checkResult;
        this.lastCheck = check;
    }

    public CheckResultInfo(CheckResult checkResult) {
        this(checkResult, null);
    }

    public CheckResult result() {
        return this.result;
    }

    public CheckResultInfo result(CheckResult checkResult) {
        return new CheckResultInfo(checkResult, this.lastCheck);
    }

    @Nullable
    public Check<?> lastCheck() {
        return this.lastCheck;
    }

    public CheckResultInfo lastCheck(@Nullable Check<?> check) {
        return new CheckResultInfo(this.result, check);
    }
}
