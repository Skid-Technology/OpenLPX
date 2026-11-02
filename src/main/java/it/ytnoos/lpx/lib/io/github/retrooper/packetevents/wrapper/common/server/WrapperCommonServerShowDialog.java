package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.Dialog;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperCommonServerShowDialog<T extends WrapperCommonServerShowDialog<T>>
        extends PacketWrapper<T> {

    protected Dialog dialog;

    public WrapperCommonServerShowDialog(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperCommonServerShowDialog(PacketTypeCommon packetTypeCommon, Dialog dialog2) {
        super(packetTypeCommon);
        this.dialog = dialog2;
    }

    @Override
    public void read() {
        this.dialog = Dialog.read(this);
    }

    @Override
    public void write() {
        Dialog.write(this, this.dialog);
    }

    public void copy(T t) {
        this.dialog = t.getDialog();
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    public void setDialog(Dialog dialog2) {
        this.dialog = dialog2;
    }
}
