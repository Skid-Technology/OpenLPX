package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

import java.util.HashSet;
import java.util.Set;

public class ItemStripOptions extends ConfigSection {

    private final boolean enabled;

    private final boolean setSlot;

    private final Set<String> values;

    private final Set<String> values2;

    public ItemStripOptions(LPXPlugin lPXPlugin, String text, Messages messages) {
        super(lPXPlugin, text + ".item-strip", messages);
        this.enabled = this.testText((String) "enabled");
        this.setSlot = this.testText((String) "set-slot");
        HashSet values3 = new HashSet(this.createValues((String) "remove-components"));
        this.values = values3;
        HashSet values4 = new HashSet(this.createValues((String) "remove-nbt"));
        this.values2 = values4;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isSetSlot() {
        return this.setSlot;
    }

    public Set<String> getValues() {
        return this.values;
    }

    public Set<String> getValues2() {
        return this.values2;
    }
}
