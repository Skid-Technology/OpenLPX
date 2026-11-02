package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.util.concurrent.Callable;

public class SimplePie extends CustomChart {

    private final Callable<String> callable;

    public SimplePie(String text, Callable<String> callable2) {
        super(text);
        this.callable = callable2;
    }

    @Override
    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        String text;
        return (text = this.callable.call()) != null && !text.isEmpty()
                ? new JsonObjectBuilder().appendField("value", text).build()
                : null;
    }
}
