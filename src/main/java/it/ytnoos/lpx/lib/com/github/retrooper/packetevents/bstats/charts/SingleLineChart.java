package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.util.concurrent.Callable;

public class SingleLineChart extends CustomChart {

    private final Callable<Integer> callable;

    public SingleLineChart(String text, Callable<Integer> callable2) {
        super(text);
        this.callable = callable2;
    }

    @Override
    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        int value;
        return (value = this.callable.call()) == 0
                ? null
                : new JsonObjectBuilder().appendField("value", value).build();
    }
}
