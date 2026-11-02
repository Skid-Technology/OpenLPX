package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.advancements;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.Nullable;

import java.util.Map;

public final class AdvancementProgress {

    private Map<String, AdvancementProgress.CriterionProgress> criteria;

    public AdvancementProgress(Map<String, AdvancementProgress.CriterionProgress> entries) {
        this.criteria = entries;
    }

    public static AdvancementProgress read(PacketWrapper<?> packetWrapper) {
        Map entries =
                packetWrapper.readMap(
                        PacketWrapper::readString, AdvancementProgress.CriterionProgress::read);
        return new AdvancementProgress(entries);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, AdvancementProgress advancementProgress) {
        packetWrapper.writeMap(
                advancementProgress.getCriteria(),
                PacketWrapper::writeString,
                AdvancementProgress.CriterionProgress::write);
    }

    public Map<String, AdvancementProgress.CriterionProgress> getCriteria() {
        return this.criteria;
    }

    public void setCriteria(Map<String, AdvancementProgress.CriterionProgress> entries) {
        this.criteria = entries;
    }

    public static final class CriterionProgress {

        @Nullable private Long obtainedTimestamp;

        public CriterionProgress(@Nullable Long obtainedTimestamp) {
            this.obtainedTimestamp = obtainedTimestamp;
        }

        public static AdvancementProgress.CriterionProgress read(PacketWrapper<?> packetWrapper) {
            return new AdvancementProgress.CriterionProgress(
                    packetWrapper.readOptional(PacketWrapper::readLong));
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                AdvancementProgress.CriterionProgress criterionProgress) {
            packetWrapper.writeOptional(
                    criterionProgress.obtainedTimestamp, PacketWrapper::writeLong);
        }

        @Nullable
        public Long getObtainedTimestamp() {
            return this.obtainedTimestamp;
        }

        public void setObtainedTimestamp(@Nullable Long obtainedTimestamp) {
            this.obtainedTimestamp = obtainedTimestamp;
        }
    }
}
