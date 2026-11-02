package it.ytnoos.lpx.check;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.check.options.PersonalCheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemBees.BeeEntry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractCheck<T> implements Check<T> {

    protected final ProtocolPlayer player;

    protected final PersonalCheckOptions options;

    private static final String TEXT = "options.";

    public AbstractCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        this.player = protocolPlayer;
        PersonalCheckOptions personalCheckOptions = new PersonalCheckOptions(checkOptions);
        this.options = personalCheckOptions;
        this.loadOptions();
    }

    private void flag(Object result, FlagInfo[] flagInfos) {
        this.player.flag(this, result, flagInfos);
    }

    protected boolean flagViolation(Object result, FlagInfo[] flagInfos) {
        this.options.increaseVl();
        this.options.setBuffer(
                this.options.getBuffer() * this.options.getCheckOptions().getBufferMultiplier());
        this.flag(result, flagInfos);
        if (this.options.getVl() >= this.options.getCheckOptions().getMaxVL()) {
            if (this.options.getCheckOptions().isPunish()) {
                this.player.punish(this);
            }
            this.options.setVl(0);
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.options.reset();
    }

    protected boolean increaseBuffer(Object result, FlagInfo[] flagInfos) {
        Double value = ((Double) (double) 1.0);
        double value2 = ((double) value);
        return this.increaseBuffer(result, value2, flagInfos);
    }

    protected boolean increaseBuffer(Object result, double value, FlagInfo[] flagInfos) {
        this.options.setBuffer(
                Math.min(
                        this.options.getCheckOptions().getMaxBuffer(),
                        this.options.getBuffer() + value));
        if (this.options.getBuffer() >= this.options.getCheckOptions().getMaxBuffer()) {
            this.flagViolation(result, flagInfos);
            return true;
        } else {
            return false;
        }
    }

    protected void decayBuffer() {
        if (!(this.options.getBuffer() <= 0.0)) {
            this.options.setBuffer(
                    Math.max(
                            0.0,
                            this.options.getBuffer()
                                    - this.options.getCheckOptions().getBufferDecay()));
        }
    }

    protected abstract CheckResult check(ProtocolPlayer protocolPlayer, T t);

    public void loadOptions() {}

    public CheckResult handle(T t) {
        if (this.options.getCheckOptions().isEnabled() && !this.player.ignoreChecks()) {
            ProtocolPlayer protocolPlayer = this.player;
            Object result = ((Object) t);
            return this.check(protocolPlayer, ((T) result));
        } else {
            return CheckResult.ALLOW;
        }
    }

    public PersonalCheckOptions getOptions() {
        return this.options;
    }

    public CheckType getType() {
        return this.options.getCheckOptions().getType();
    }

    protected String createText(String text) {
        String path = this.options.getCheckOptions().getPath();
        String text2 = TEXT;
        return path + text2 + text;
    }

    protected FileConfiguration getCheckConfiguration() {
        return this.options.getCheckOptions().getConfig();
    }

    protected boolean testEnabled(String text, boolean matches) {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        return checkConfiguration.getBoolean(this.createText(text), matches);
    }

    protected int calculateLimit(String text, int value) {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        return checkConfiguration.getInt(this.createText(text), value);
    }

    protected long calculateTime(String text, long value) {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        return checkConfiguration.getLong(this.createText(text), value);
    }

    protected double calculateValue(String text, double value) {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        return checkConfiguration.getDouble(this.createText(text), value);
    }

    protected List<String> createValues(String text, String[] texts) {
        String text2 = this.createText(text);
        if (this.getCheckConfiguration().isSet(text2)) {
            return this.getCheckConfiguration().getStringList(text2);
        } else {
            return new ArrayList<>(Arrays.asList(texts));
        }
    }

    protected void handleNBT(ItemStack itemStack, Consumer consumer) {
        if (itemStack != null) {
            NBTCompound nBT = itemStack.getNBT();
            if (nBT != null) {
                consumer.accept(nBT);
            }
            itemStack.getComponent(ComponentTypes.CUSTOM_DATA).ifPresent(consumer);
            itemStack.getComponent(ComponentTypes.ENTITY_DATA).ifPresent(consumer);
            itemStack.getComponent(ComponentTypes.BUCKET_ENTITY_DATA).ifPresent(consumer);
            itemStack.getComponent(ComponentTypes.BLOCK_ENTITY_DATA).ifPresent(consumer);
            Optional<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                    .builtin.item.ItemBees>
                    component2 = itemStack.getComponent(ComponentTypes.BEES);
            component2.ifPresent(
                    result -> {
                        for (BeeEntry beeEntry : result.getBees()) {
                            consumer.accept(beeEntry.getEntityData());
                        }
                    });
            itemStack.getComponent(ComponentTypes.DEBUG_STICK_STATE).ifPresent(result2 -> {});
        }
    }
}
