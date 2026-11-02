package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class AdvancedPie extends CustomChart {

    private final Callable<Map<String, Integer>> callable;

    public AdvancedPie(String text, Callable<Map<String, Integer>> callable2) {
        super(text);
        this.callable = callable2;
    }

    @Override
    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        Map<String, Integer> entries;
        if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
            boolean matches = true;
            Iterator<Entry<String, Integer>> iterator2 = entries.entrySet().iterator();
            while (iterator2.hasNext()) {
                Entry entry;
                if ((Integer) (entry = (Entry) iterator2.next()).getValue() != 0) {
                    matches = false;
                    jsonObjectBuilder.appendField(
                            (String) entry.getKey(), (Integer) entry.getValue());
                }
            }
            return matches
                    ? null
                    : new JsonObjectBuilder()
                            .appendField("values", jsonObjectBuilder.build())
                            .build();
        } else {
            return null;
        }
    }
}
