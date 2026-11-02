package it.ytnoos.lpx.api.check;

public class FlagInfo {

    private final String name;

    private final Object value;

    public FlagInfo(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public static FlagInfo of(String text, Object result) {
        return new FlagInfo(text, result);
    }

    @Override
    public String toString() {
        return this.name + "=" + this.value;
    }
}
