package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class WrapperConfigServerCodeOfConduct
        extends PacketWrapper<WrapperConfigServerCodeOfConduct> {

    private String codeOfConduct;

    public WrapperConfigServerCodeOfConduct(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerCodeOfConduct(String codeOfConduct) {
        super(PacketType.Configuration.Server.CODE_OF_CONDUCT);
        this.codeOfConduct = codeOfConduct;
    }

    @Override
    public void read() {
        this.codeOfConduct = this.readString();
    }

    @Override
    public void write() {
        this.writeString(this.codeOfConduct);
    }

    public void copy(WrapperConfigServerCodeOfConduct wrapperConfigServerCodeOfConduct) {
        this.codeOfConduct = wrapperConfigServerCodeOfConduct.codeOfConduct;
    }

    public String getCodeOfConduct() {
        return this.codeOfConduct;
    }

    public void setCodeOfConduct(String codeOfConduct) {
        this.codeOfConduct = codeOfConduct;
    }
}
