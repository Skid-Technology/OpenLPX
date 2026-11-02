package it.ytnoos.lpx.lib.kyori.adventure.chat;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ChatTypeImpl implements ChatType {

    private final Key key;

    ChatTypeImpl(@NotNull Key key2) {
        this.key = key2;
    }

    @NotNull
    @Override
    public Key key() {
        return this.key;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class BoundImpl implements ChatType.Bound {

        private final ChatType chatType;

        private final Component name;

        @Nullable private final Component target;

        BoundImpl(ChatType chatType2, Component component, @Nullable Component component2) {
            this.chatType = chatType2;
            this.name = component;
            this.target = component2;
        }

        @NotNull
        @Override
        public ChatType type() {
            return this.chatType;
        }

        @NotNull
        @Override
        public Component name() {
            return this.name;
        }

        @Nullable
        @Override
        public Component target() {
            return this.target;
        }

        @Override
        public String toString() {
            return Internals.toString(this);
        }
    }
}
