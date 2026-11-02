package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMask;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public class ChatMessage_v1_21_5 extends ChatMessage_v1_19_3 {

    int globalIndex;

    public ChatMessage_v1_21_5(
            int globalIndex,
            UUID uUID,
            int value2,
            byte[] values,
            String text,
            Instant instant,
            long value3,
            LastSeenMessages.Packed packed,
            @Nullable Component component,
            FilterMask filterMask,
            ChatType.Bound bound) {
        super(uUID, value2, values, text, instant, value3, packed, component, filterMask, bound);
        this.globalIndex = globalIndex;
    }

    public int getGlobalIndex() {
        return this.globalIndex;
    }

    public void setGlobalIndex(int globalIndex) {
        this.globalIndex = globalIndex;
    }
}
