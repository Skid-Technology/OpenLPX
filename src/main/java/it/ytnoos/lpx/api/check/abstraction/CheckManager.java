package it.ytnoos.lpx.api.check.abstraction;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.check.RegisteredCheck;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class CheckManager {

    public static final CheckContainer<?> EMPTY_CONTAINER = new CheckContainer();

    private final LPXPlugin plugin;

    private final Map<Object, List<RegisteredCheck<?>>> checks = Maps.newHashMap();

    public CheckManager(LPXPlugin lPXPlugin) {
        this.plugin = lPXPlugin;
    }

    public void register(Object result, CheckFunction checkFunction, CheckType checkType) {
        this.checks
                .computeIfAbsent(result, result2 -> Lists.newArrayList())
                .add(
                        new RegisteredCheck(
                                checkType,
                                new CheckOptions(this.plugin, checkType),
                                checkFunction));
    }

    public void sortByPriority() {
        Iterator<List<RegisteredCheck<?>>> iterator2 = this.checks.values().iterator();
        while (iterator2.hasNext()) {
            ((List) iterator2.next())
                    .sort(
                            Comparator.comparingInt(
                                    (RegisteredCheck<?> registeredCheck) ->
                                            registeredCheck.getType().getPriority()));
        }
    }

    public Map<Object, CheckContainer<?>> createPlayerChecks(ProtocolPlayer protocolPlayer) {
        HashMap<Object, CheckContainer<?>> entries = Maps.newHashMap();
        this.checks.forEach(
                (result, result2) -> {
                    CheckContainer checkContainer = new CheckContainer();
                    for (RegisteredCheck registeredCheck :
                            (Iterable<RegisteredCheck>) ((Iterable) result2)) {
                        checkContainer.add(
                                registeredCheck
                                        .getFunction()
                                        .apply(protocolPlayer, registeredCheck.getOptions()));
                    }
                    entries.put(result, checkContainer);
                });
        return entries;
    }

    public Map<Object, List<RegisteredCheck<?>>> getChecks() {
        return this.checks;
    }

    public abstract void registerChecks();
}
