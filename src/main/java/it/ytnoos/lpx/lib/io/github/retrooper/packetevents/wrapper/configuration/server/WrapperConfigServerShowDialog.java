package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.Dialog;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerShowDialog;

public class WrapperConfigServerShowDialog
        extends WrapperCommonServerShowDialog<WrapperConfigServerShowDialog> {

    public WrapperConfigServerShowDialog(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerShowDialog(Dialog dialog) {
        super(PacketType.Configuration.Server.SHOW_DIALOG, dialog);
    }

    @Override
    public void read() {
        this.dialog = Dialog.readDirect(this);
    }

    @Override
    public void write() {
        Dialog.writeDirect(this, this.dialog);
    }
}
