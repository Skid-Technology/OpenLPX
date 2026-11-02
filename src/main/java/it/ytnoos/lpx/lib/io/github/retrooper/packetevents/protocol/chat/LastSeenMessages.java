package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class LastSeenMessages {

    public static final LastSeenMessages EMPTY = new LastSeenMessages(new ArrayList<>());

    private final List<LastSeenMessages.Entry> entries;

    public LastSeenMessages(List<LastSeenMessages.Entry> entries) {
        this.entries = entries;
    }

    public void updateHash(DataOutput dataOutput) throws IOException {
        Iterator<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat
                                .LastSeenMessages.Entry>
                iterator2 = this.entries.iterator();
        while (iterator2.hasNext()) {
            LastSeenMessages.Entry entry;
            UUID uUID = (entry = (LastSeenMessages.Entry) iterator2.next()).getUUID();
            byte[] lastVerifier = entry.getLastVerifier();
            dataOutput.writeByte(70);
            dataOutput.writeLong(uUID.getMostSignificantBits());
            dataOutput.writeLong(uUID.getLeastSignificantBits());
            dataOutput.write(lastVerifier);
        }
    }

    public List<LastSeenMessages.Entry> getEntries() {
        return this.entries;
    }

    public static class Entry {

        private final UUID uuid;

        private final byte[] signature;

        public Entry(UUID uuid, byte[] signature) {
            this.uuid = uuid;
            this.signature = signature;
        }

        public UUID getUUID() {
            return this.uuid;
        }

        public byte[] getLastVerifier() {
            return this.signature;
        }
    }

    public static class LegacyUpdate {

        private final LastSeenMessages lastSeenMessages;

        @Nullable private final LastSeenMessages.Entry lastReceived;

        public LegacyUpdate(
                LastSeenMessages lastSeenMessages2, @Nullable LastSeenMessages.Entry entry) {
            this.lastSeenMessages = lastSeenMessages2;
            this.lastReceived = entry;
        }

        public LastSeenMessages getLastSeenMessages() {
            return this.lastSeenMessages;
        }

        @Nullable
        public LastSeenMessages.Entry getLastReceived() {
            return this.lastReceived;
        }
    }

    public static class Packed {

        private List<MessageSignature.Packed> packedMessageSignatures;

        public Packed(List<MessageSignature.Packed> packedMessageSignatures) {
            this.packedMessageSignatures = packedMessageSignatures;
        }

        public List<MessageSignature.Packed> getPackedMessageSignatures() {
            return this.packedMessageSignatures;
        }

        public void setPackedMessageSignatures(
                List<MessageSignature.Packed> packedMessageSignatures) {
            this.packedMessageSignatures = packedMessageSignatures;
        }
    }

    public static class Update {

        private final int offset;

        private final BitSet acknowledged;

        private final byte checksum;

        @Obsolete
        public Update(int value, BitSet bitSet) {
            this(value, bitSet, (byte) 0);
        }

        public Update(int offset, BitSet bitSet, byte checksum) {
            this.offset = offset;
            this.acknowledged = bitSet;
            this.checksum = checksum;
        }

        public int getOffset() {
            return this.offset;
        }

        public BitSet getAcknowledged() {
            return this.acknowledged;
        }

        public byte getChecksum() {
            return this.checksum;
        }
    }
}
