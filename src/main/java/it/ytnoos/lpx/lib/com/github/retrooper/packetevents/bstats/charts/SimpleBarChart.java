package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class SimpleBarChart extends CustomChart {

    private final Callable<Map<String, Integer>> callable;

    public SimpleBarChart(String text, Callable<Map<String, Integer>> callable2) {
        super(text);
        this.callable = callable2;
    }

    @Override
    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        Map<String, Integer> entries;
        if ((entries = this.callable.call()) != null && !entries.isEmpty()) {
            for (Entry<String, Integer> entry : entries.entrySet()) {
                jsonObjectBuilder.appendField(
                        (String) entry.getKey(), new int[] {(Integer) entry.getValue()});
            }
            return new JsonObjectBuilder().appendField("values", jsonObjectBuilder.build()).build();
        } else {
            return null;
        }
    }
}
