package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

public class SignedCommandArgument {

    private String argument;

    private MessageSignature signature;

    public SignedCommandArgument(String argument, MessageSignature messageSignature) {
        this.argument = argument;
        this.signature = messageSignature;
    }

    public String getArgument() {
        return this.argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public MessageSignature getSignature() {
        return this.signature;
    }

    public void setSignature(MessageSignature messageSignature) {
        this.signature = messageSignature;
    }
}
