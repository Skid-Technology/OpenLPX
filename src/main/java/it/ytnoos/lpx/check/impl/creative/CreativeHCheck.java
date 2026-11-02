package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.CreativeCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;

public class CreativeHCheck extends CreativeCheck {

    public CreativeHCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions, Client.TEST_INSTANCE_BLOCK_ACTION);
    }
}
