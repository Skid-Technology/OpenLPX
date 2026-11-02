package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class DebugGoalInfo {

    private final List<DebugGoalInfo.DebugGoal> goals;

    public DebugGoalInfo(List<DebugGoalInfo.DebugGoal> goals) {
        this.goals = goals;
    }

    public static DebugGoalInfo read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(DebugGoalInfo.DebugGoal::read);
        return new DebugGoalInfo(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugGoalInfo debugGoalInfo) {
        packetWrapper.writeList(debugGoalInfo.goals, DebugGoalInfo.DebugGoal::write);
    }

    public List<DebugGoalInfo.DebugGoal> getGoals() {
        return this.goals;
    }

    public static final class DebugGoal {

        private final int priority;

        private final boolean running;

        private final String name;

        public DebugGoal(int priority, boolean running, String name) {
            this.priority = priority;
            this.running = running;
            this.name = name;
        }

        public static DebugGoalInfo.DebugGoal read(PacketWrapper<?> packetWrapper) {
            int value = packetWrapper.readVarInt();
            boolean matches = packetWrapper.readBoolean();
            String text = packetWrapper.readString(255);
            return new DebugGoalInfo.DebugGoal(value, matches, text);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, DebugGoalInfo.DebugGoal debugGoal) {
            packetWrapper.writeVarInt(debugGoal.priority);
            packetWrapper.writeBoolean(debugGoal.running);
            packetWrapper.writeString(debugGoal.name, 255);
        }

        public int getPriority() {
            return this.priority;
        }

        public boolean isRunning() {
            return this.running;
        }

        public String getName() {
            return this.name;
        }
    }
}
