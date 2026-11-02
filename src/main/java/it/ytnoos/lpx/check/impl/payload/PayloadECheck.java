package it.ytnoos.lpx.check.impl.payload;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class PayloadECheck extends AbstractCheck<WrapperPlayClientPluginMessage> {

    public PayloadECheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (!wrapperPlayClientPluginMessage.getChannelName().equals("REGISTER")) {
            return CheckResult.ALLOW;
        }
        String text = "";
        try {
            String text2 = new String(wrapperPlayClientPluginMessage.getData());
            text = text2;
            String[] texts = text2.split("\u0000");
            int value = texts.length;
            for (int index = 0; index < value; index++) {
                String text3 = texts[index];
                Class[] classs = new Class[] {String.class};
                Method method = CraftPlayer.class.getMethod("addChannel", classs);
                Object[] results = new Object[] {text3};
                method.invoke(player, results);
            }
        } catch (Exception exception) {
            FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("channels", text)};
            this.flagViolation(wrapperPlayClientPluginMessage, flagInfos);
        }
        return CheckResult.DENY;
    }
}
