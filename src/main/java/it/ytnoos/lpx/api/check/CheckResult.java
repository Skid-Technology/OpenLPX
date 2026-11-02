package it.ytnoos.lpx.api.check;

public enum CheckResult {
    ALLOW,
    CHANGE,
    DENY;

    public static CheckResult merge(CheckResult checkResult, CheckResult checkResult2) {
        if (checkResult == DENY || checkResult2 == DENY) {
            return DENY;
        } else {
            return checkResult != CHANGE && checkResult2 != CHANGE ? ALLOW : CHANGE;
        }
    }

    public CheckResult merge(CheckResult checkResult) {
        return merge(this, checkResult);
    }
}
