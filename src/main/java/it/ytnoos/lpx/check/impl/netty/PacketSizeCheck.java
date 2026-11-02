package it.ytnoos.lpx.check.impl.netty;

import io.netty.buffer.ByteBuf;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;

public class PacketSizeCheck extends AbstractCheck<ByteBuf> {

    private int maxSize;

    private final boolean preVia;

    public PacketSizeCheck(
            ProtocolPlayer protocolPlayer, CheckOptions checkOptions, boolean preVia) {
        super(protocolPlayer, checkOptions);
        this.preVia = preVia;
    }

    @Override
    public void loadOptions() {
        Boolean matches = ((Boolean) (boolean) true);
        String text = (String) "automatic";
        String text2 = text;
        int value;
        if (this.testEnabled(text2, ((boolean) matches))) {
            value = this.getMaxSize();
        } else {
            Integer value2 = ((Integer) 98304);
            String text3 = "max";
            text = text3;
            String text4 = text;
            value = this.calculateLimit(text4, ((int) value2));
        }
        this.maxSize = value;
    }

    private int getMaxSize() {
        ServerVersion version = PacketEvents.getAPI().getServerManager().getVersion();
        if (version.isOlderThanOrEquals(ServerVersion.V_1_8_8)) {
            return 8448;
        } else {
            return version.isOlderThanOrEquals(ServerVersion.V_1_12_2) ? 24576 : 98304;
        }
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, ByteBuf byteBuf) {
        long size = protocolPlayer.getLastReceivedPacket().getSize();
        long value = this.maxSize;
        if (!this.preVia) {
            value *= 2L;
        }
        if (size >= 0L && size < value) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("size", size), null, null};
        flagInfos[1] = FlagInfo.of("temp-max-size", value);
        flagInfos[2] = FlagInfo.of("max-size", this.maxSize);
        this.flagViolation(byteBuf, flagInfos);
        return CheckResult.DENY;
    }

    public boolean isPreVia() {
        return this.preVia;
    }
}
