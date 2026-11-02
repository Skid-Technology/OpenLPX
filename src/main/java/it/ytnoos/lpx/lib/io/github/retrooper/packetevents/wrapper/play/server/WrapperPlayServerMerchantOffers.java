package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.MerchantOffer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;

public class WrapperPlayServerMerchantOffers
        extends PacketWrapper<WrapperPlayServerMerchantOffers> {

    private int containerId;

    private List<MerchantOffer> merchantOffers;

    private int villagerLevel;

    private int villagerXp;

    private boolean showProgress;

    private boolean canRestock;

    public WrapperPlayServerMerchantOffers(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerMerchantOffers(
            int containerId,
            List<MerchantOffer> merchantOffers,
            int villagerLevel,
            int villagerXp,
            boolean showProgress,
            boolean canRestock) {
        super(PacketType.Play.Server.MERCHANT_OFFERS);
        this.containerId = containerId;
        this.merchantOffers = merchantOffers;
        this.villagerLevel = villagerLevel;
        this.villagerXp = villagerXp;
        this.showProgress = showProgress;
        this.canRestock = canRestock;
    }

    @Override
    public void read() {
        this.containerId =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)
                        ? this.readContainerId()
                        : this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.merchantOffers = this.readList(PacketWrapper::readMerchantOffer);
        } else {
            int value = this.readByte() & 255;
            this.merchantOffers = new ArrayList<>(value);
            for (int index = 0; index < value; index++) {
                this.merchantOffers.add(this.readMerchantOffer());
            }
        }
        this.villagerLevel = this.readVarInt();
        this.villagerXp = this.readVarInt();
        this.showProgress = this.readBoolean();
        this.canRestock = this.readBoolean();
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeContainerId(this.containerId);
        } else {
            this.writeVarInt(this.containerId);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.writeList(this.merchantOffers, PacketWrapper::writeMerchantOffer);
        } else {
            this.writeByte(this.merchantOffers.size() & 0xFF);
            for (MerchantOffer merchantOffer : this.merchantOffers) {
                this.writeMerchantOffer(merchantOffer);
            }
        }
        this.writeVarInt(this.villagerLevel);
        this.writeVarInt(this.villagerXp);
        this.writeBoolean(this.showProgress);
        this.writeBoolean(this.canRestock);
    }

    public void copy(WrapperPlayServerMerchantOffers wrapperPlayServerMerchantOffers) {
        this.containerId = wrapperPlayServerMerchantOffers.containerId;
        this.merchantOffers = wrapperPlayServerMerchantOffers.merchantOffers;
        this.villagerLevel = wrapperPlayServerMerchantOffers.villagerLevel;
        this.villagerXp = wrapperPlayServerMerchantOffers.villagerXp;
        this.showProgress = wrapperPlayServerMerchantOffers.showProgress;
        this.canRestock = wrapperPlayServerMerchantOffers.canRestock;
    }

    public int getContainerId() {
        return this.containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public List<MerchantOffer> getMerchantOffers() {
        return this.merchantOffers;
    }

    public void setMerchantOffers(List<MerchantOffer> merchantOffers) {
        this.merchantOffers = merchantOffers;
    }

    public int getVillagerLevel() {
        return this.villagerLevel;
    }

    public void setVillagerLevel(int villagerLevel) {
        this.villagerLevel = villagerLevel;
    }

    public int getVillagerXp() {
        return this.villagerXp;
    }

    public void setVillagerXp(int villagerXp) {
        this.villagerXp = villagerXp;
    }

    public boolean isShowProgress() {
        return this.showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCanRestock() {
        return this.canRestock;
    }

    public void setCanRestock(boolean canRestock) {
        this.canRestock = canRestock;
    }
}
