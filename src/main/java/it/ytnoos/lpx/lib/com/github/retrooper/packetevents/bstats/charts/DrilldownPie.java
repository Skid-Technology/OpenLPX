package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class DrilldownPie extends CustomChart {

    private final Callable<Map<String, Map<String, Integer>>> callable;

    public DrilldownPie(String text, Callable<Map<String, Map<String, Integer>>> callable2) {
        super(text);
        this.callable = callable2;
    }

    @Override
    public JsonObjectBuilder.JsonObject getChartData() throws Exception {
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        Map<String, Map<String, Integer>> entries;
        if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
            boolean matches = true;
            for (Entry<String, Map<String, Integer>> entry : entries.entrySet()) {
                JsonObjectBuilder jsonObjectBuilder2 = new JsonObjectBuilder();
                boolean matches2 = true;
                for (Entry<Object, Object> entry2 :
                        ((Iterable<Entry<Object, Object>>)
                                ((Iterable<?>) ((Map) entries.get(entry.getKey())).entrySet()))) {
                    jsonObjectBuilder2.appendField(
                            (String) entry2.getKey(), (Integer) entry2.getValue());
                    matches2 = false;
                }
                if (!matches2) {
                    matches = false;
                    jsonObjectBuilder.appendField(
                            (String) entry.getKey(), jsonObjectBuilder2.build());
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
