package it.ytnoos.lpx.api.check.enums;

public enum CheckCategory {
    NETTY("netty"),
    WINDOW("window"),
    ITEM("item"),
    CREATIVE("creative"),
    PLACE("place"),
    FLOOD("flood"),
    POSITION("position"),
    PAYLOAD("payload"),
    BOOK("book"),
    TAB("tab"),
    COMMAND("command"),
    RECIPE("recipe");

    private final String name;

    CheckCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getFormattedName() {
        return capitalise(this.toString());
    }

    private static String capitalise(String text) {
        if (text == null) {
            return null;
        } else {
            return text.isEmpty() ? "" : Character.toTitleCase(text.charAt(0)) + text.substring(1);
        }
    }
}
