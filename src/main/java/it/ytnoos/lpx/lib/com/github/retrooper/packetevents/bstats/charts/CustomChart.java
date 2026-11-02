package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import java.util.function.BiConsumer;

public abstract class CustomChart {

    private final String chartId;

    protected CustomChart(String chartId) {
        if (chartId == null) {
            throw new IllegalArgumentException("chartId must not be null");
        }
        this.chartId = chartId;
    }

    public JsonObjectBuilder.JsonObject getRequestJsonObject(
            BiConsumer<String, Throwable> biConsumer, boolean matches) {
        JsonObjectBuilder jsonObjectBuilder;
        (jsonObjectBuilder = new JsonObjectBuilder()).appendField("chartId", this.chartId);
        try {
            JsonObjectBuilder.JsonObject jsonObject;
            if ((jsonObject = this.getChartData()) == null) {
                return null;
            }
            jsonObjectBuilder.appendField("data", jsonObject);
        } catch (Throwable throwable) {
            if (matches) {
                biConsumer.accept(
                        "Failed to get data for custom chart with id " + this.chartId, throwable);
            }
            return null;
        }
        return jsonObjectBuilder.build();
    }

    protected abstract JsonObjectBuilder.JsonObject getChartData() throws Exception;
}
