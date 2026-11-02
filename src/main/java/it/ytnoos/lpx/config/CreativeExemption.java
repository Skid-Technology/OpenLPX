package it.ytnoos.lpx.config;

public enum CreativeExemption {
    CREATIVE,
    ALL,
    SMART,
    NONE;

    public static final CreativeExemption DEFAULT = SMART;

    public static CreativeExemption fromString(String text) {
        try {
            return valueOf(text.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            return DEFAULT;
        }
    }
}
