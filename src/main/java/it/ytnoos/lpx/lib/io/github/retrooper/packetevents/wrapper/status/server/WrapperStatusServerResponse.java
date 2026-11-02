package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.status.server;

import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperStatusServerResponse extends PacketWrapper<WrapperStatusServerResponse> {

    private String componentJson;

    public WrapperStatusServerResponse(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperStatusServerResponse(JsonObject jsonObject) {
        this(jsonObject.toString());
    }

    public WrapperStatusServerResponse(String componentJson) {
        super(PacketType.Status.Server.RESPONSE);
        this.componentJson = componentJson;
    }

    @Override
    public void read() {
        this.componentJson = this.readString();
    }

    @Override
    public void write() {
        this.writeString(this.componentJson);
    }

    public void copy(WrapperStatusServerResponse wrapperStatusServerResponse) {
        this.componentJson = wrapperStatusServerResponse.componentJson;
    }

    public JsonObject getComponent() {
        return this.getSerializers()
                .gson()
                .serializer()
                .fromJson(this.componentJson, JsonObject.class);
    }

    public void setComponent(JsonObject jsonObject) {
        this.componentJson = this.getSerializers().gson().serializer().toJson(jsonObject);
    }

    public String getComponentJson() {
        return this.componentJson;
    }

    public void setComponentJson(String componentJson) {
        this.componentJson = componentJson;
    }
}
