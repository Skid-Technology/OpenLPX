package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class CreativeGCheck extends AbstractCheck<WrapperPlayClientCreativeInventoryAction> {

    public CreativeGCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        NBTCompound nBT = wrapperPlayClientCreativeInventoryAction.getItemStack().getNBT();
        if (nBT == null) {
            return CheckResult.ALLOW;
        }
        NBTCompound compoundTagOrNull = nBT.getCompoundTagOrNull("BlockEntityTag");
        if (compoundTagOrNull == null) {
            return CheckResult.ALLOW;
        }
        NBTNumber numberTagOrNull = compoundTagOrNull.getNumberTagOrNull("x");
        if (numberTagOrNull == null) {
            return CheckResult.ALLOW;
        }
        NBTNumber numberTagOrNull2 = compoundTagOrNull.getNumberTagOrNull("z");
        if (numberTagOrNull2 == null) {
            return CheckResult.ALLOW;
        }
        int value;
        int value2;
        try {
            value = numberTagOrNull.getAsInt();
            value2 = numberTagOrNull2.getAsInt();
        } catch (Exception exception) {
            return CheckResult.ALLOW;
        }
        int value3 = value / 16;
        int value4 = value2 / 16;
        World world = player.getWorld();
        if (!world.isChunkLoaded(value3, value4)) {
            FlagInfo[] flagInfos = new FlagInfo[5];
            flagInfos[0] = FlagInfo.of("world", world.getName());
            flagInfos[1] = FlagInfo.of("x", value);
            flagInfos[2] = FlagInfo.of("z", value2);
            flagInfos[3] = FlagInfo.of("chunk-x", value3);
            flagInfos[4] = FlagInfo.of("chunk-z", value4);
            this.flagViolation(wrapperPlayClientCreativeInventoryAction, flagInfos);
        }
        return CheckResult.ALLOW;
    }
}
