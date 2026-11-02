package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class AdvancedBarChart extends CustomChart {

    private final Callable<Map<String, int[]>> callable;

    public AdvancedBarChart(String text, Callable<Map<String, int[]>> callable2) {
        super(text);
        this.callable = callable2;
    }

    @Override
    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        Map<String, int[]> entries;
        if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
            boolean matches = true;
            Iterator<Entry<String, int[]>> iterator2 = entries.entrySet().iterator();
            while (iterator2.hasNext()) {
                Entry entry;
                if (((int[]) (entry = (Entry) iterator2.next()).getValue()).length != 0) {
                    matches = false;
                    jsonObjectBuilder.appendField(
                            (String) entry.getKey(), (int[]) entry.getValue());
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
