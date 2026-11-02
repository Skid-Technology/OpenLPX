package it.ytnoos.lpx.check.impl.position;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import it.ytnoos.lpx.utils.TimeUtils;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class PositionBCheck extends AbstractCheck<WrapperPlayClientPlayerFlying> {

    private int delay;

    private boolean enabled;

    private boolean enabled2;

    private int limit;

    private int limit2;

    private String text = "";

    public PositionBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = Integer.valueOf((short) 2000);
        String text = (String) "delay";
        String text2 = text;
        this.delay = this.calculateLimit(text2, ((int) value));
        Boolean matches = ((Boolean) (boolean) false);
        String text3 = "hard";
        text = text3;
        String text4 = text;
        this.enabled = this.testEnabled(text4, ((boolean) matches));
        Boolean matches2 = ((Boolean) (boolean) true);
        String text5 = "ignore-fly";
        text = text5;
        String text6 = text;
        this.enabled2 = this.testEnabled(text6, ((boolean) matches2));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (!wrapperPlayClientPlayerFlying.hasPositionChanged()) {
            return CheckResult.ALLOW;
        }
        if (!this.enabled2 || !player.isFlying()) {
            long lastTeleport = protocolPlayer.getLastTeleport();
            Long value = ((Long) lastTeleport);
            if (TimeUtils.calculateTime(value) >= this.delay) {
                int value2 = (int) wrapperPlayClientPlayerFlying.getLocation().getX() / 16;
                int value3 = (int) wrapperPlayClientPlayerFlying.getLocation().getZ() / 16;
                World world = player.getWorld();
                String name = world.getName();
                boolean matches = true;
                if (!world.isChunkLoaded(value2, value3)) {
                    if ((this.limit != value2 || this.limit2 != value3) && this.text.equals(name)) {
                        FlagInfo[] flagInfos = new FlagInfo[7];
                        flagInfos[0] =
                                FlagInfo.of(
                                        "x", wrapperPlayClientPlayerFlying.getLocation().getX());
                        flagInfos[1] =
                                FlagInfo.of(
                                        "z", wrapperPlayClientPlayerFlying.getLocation().getZ());
                        flagInfos[2] = FlagInfo.of("chunk-x", value2);
                        flagInfos[3] = FlagInfo.of("chunk-z", value3);
                        flagInfos[4] = FlagInfo.of("last-chunk-x", this.limit);
                        flagInfos[5] = FlagInfo.of("last-chunk-z", this.limit2);
                        flagInfos[6] = FlagInfo.of("world", name);
                        this.increaseBuffer(wrapperPlayClientPlayerFlying, flagInfos);
                        if (this.enabled) {
                            matches = false;
                        }
                    } else {
                        this.decayBuffer();
                    }
                }
                this.handleWrapperPlayClientPlayerFlying(player, wrapperPlayClientPlayerFlying);
                return matches ? CheckResult.ALLOW : CheckResult.DENY;
            }
        }
        this.handleWrapperPlayClientPlayerFlying(player, wrapperPlayClientPlayerFlying);
        return CheckResult.ALLOW;
    }

    private void handleWrapperPlayClientPlayerFlying(
            Player player, WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        this.text = player.getWorld().getName();
        this.limit = (int) wrapperPlayClientPlayerFlying.getLocation().getX() / 16;
        this.limit2 = (int) wrapperPlayClientPlayerFlying.getLocation().getZ() / 16;
    }
}
