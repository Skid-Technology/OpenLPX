package it.ytnoos.lpx.api.check.abstraction;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.PersonalCheckOptions;

public interface Check<T> {

    CheckType getType();

    CheckResult handle(T t);

    PersonalCheckOptions getOptions();

    default void reload() {
        this.reset();
        this.loadOptions();
    }

    default boolean isPreVia() {
        return false;
    }

    void reset();

    void loadOptions();
}
