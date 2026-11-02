package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Attachment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Axis;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.CopperGolemPose;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.CreakingHeartState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.East;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Face;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Half;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Hinge;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Instrument;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Leaves;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Mode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.North;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Orientation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Part;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.PotentSulfurState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.SculkSensorPhase;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Shape;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.SideChainPart;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.South;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Thickness;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Tilt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.TrialSpawnerState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Type;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.VaultState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.VerticalDirection;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.West;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public enum StateValue {
    AGE("age", int.class, Integer::parseInt),
    ATTACHED("attached", boolean.class, Boolean::parseBoolean),
    ATTACHMENT("attachment", Attachment.class, Attachment::valueOf),
    AXIS("axis", Axis.class, Axis::valueOf),
    BERRIES("berries", boolean.class, Boolean::parseBoolean),
    BITES("bites", int.class, Integer::parseInt),
    BLOOM("bloom", boolean.class, Boolean::parseBoolean),
    BOTTOM("bottom", boolean.class, Boolean::parseBoolean),
    CANDLES("candles", int.class, Integer::parseInt),
    CAN_SUMMON("can_summon", boolean.class, Boolean::parseBoolean),
    CHARGES("charges", int.class, Integer::parseInt),
    CONDITIONAL("conditional", boolean.class, Boolean::parseBoolean),
    CRACKED("cracked", boolean.class, Boolean::parseBoolean),
    CRAFTING("crafting", boolean.class, Boolean::parseBoolean),
    DELAY("delay", int.class, Integer::parseInt),
    DISARMED("disarmed", boolean.class, Boolean::parseBoolean),
    DISTANCE("distance", int.class, Integer::parseInt),
    DOWN("down", boolean.class, Boolean::parseBoolean),
    DRAG("drag", boolean.class, Boolean::parseBoolean),
    DUSTED("dusted", int.class, Integer::parseInt),
    EAST("east", East.class, East::valueOf),
    EGGS("eggs", int.class, Integer::parseInt),
    ENABLED("enabled", boolean.class, Boolean::parseBoolean),
    EXTENDED("extended", boolean.class, Boolean::parseBoolean),
    EYE("eye", boolean.class, Boolean::parseBoolean),
    FACE("face", Face.class, Face::valueOf),
    FACING("facing", BlockFace.class, BlockFace::valueOf),
    FLOWER_AMOUNT("flower_amount", int.class, Integer::parseInt),
    HALF("half", Half.class, Half::valueOf),
    HANGING("hanging", boolean.class, Boolean::parseBoolean),
    HAS_BOOK("has_book", boolean.class, Boolean::parseBoolean),
    HAS_BOTTLE_0("has_bottle_0", boolean.class, Boolean::parseBoolean),
    HAS_BOTTLE_1("has_bottle_1", boolean.class, Boolean::parseBoolean),
    HAS_BOTTLE_2("has_bottle_2", boolean.class, Boolean::parseBoolean),
    HAS_RECORD("has_record", boolean.class, Boolean::parseBoolean),
    HATCH("hatch", int.class, Integer::parseInt),
    HINGE("hinge", Hinge.class, Hinge::valueOf),
    HONEY_LEVEL("honey_level", int.class, Integer::parseInt),
    IN_WALL("in_wall", boolean.class, Boolean::parseBoolean),
    INSTRUMENT("instrument", Instrument.class, Instrument::valueOf),
    INVERTED("inverted", boolean.class, Boolean::parseBoolean),
    LAYERS("layers", int.class, Integer::parseInt),
    LEAVES("leaves", Leaves.class, Leaves::valueOf),
    LEVEL("level", int.class, Integer::parseInt),
    LIT("lit", boolean.class, Boolean::parseBoolean),
    TIP("tip", boolean.class, Boolean::parseBoolean),
    LOCKED("locked", boolean.class, Boolean::parseBoolean),
    MODE("mode", Mode.class, Mode::valueOf),
    MOISTURE("moisture", int.class, Integer::parseInt),
    NORTH("north", North.class, North::valueOf),
    NOTE("note", int.class, Integer::parseInt),
    OCCUPIED("occupied", boolean.class, Boolean::parseBoolean),
    OMINOUS("ominous", boolean.class, Boolean::parseBoolean),
    OPEN("open", boolean.class, Boolean::parseBoolean),
    ORIENTATION("orientation", Orientation.class, Orientation::valueOf),
    PART("part", Part.class, Part::valueOf),
    PERSISTENT("persistent", boolean.class, Boolean::parseBoolean),
    PICKLES("pickles", int.class, Integer::parseInt),
    POWER("power", int.class, Integer::parseInt),
    POWERED("powered", boolean.class, Boolean::parseBoolean),
    ROTATION("rotation", int.class, Integer::parseInt),
    SCULK_SENSOR_PHASE("sculk_sensor_phase", SculkSensorPhase.class, SculkSensorPhase::valueOf),
    SHAPE("shape", Shape.class, Shape::valueOf),
    SHORT("short", boolean.class, Boolean::parseBoolean),
    SHRIEKING("shrieking", boolean.class, Boolean::parseBoolean),
    SIGNAL_FIRE("signal_fire", boolean.class, Boolean::parseBoolean),
    SLOT_0_OCCUPIED("slot_0_occupied", boolean.class, Boolean::parseBoolean),
    SLOT_1_OCCUPIED("slot_1_occupied", boolean.class, Boolean::parseBoolean),
    SLOT_2_OCCUPIED("slot_2_occupied", boolean.class, Boolean::parseBoolean),
    SLOT_3_OCCUPIED("slot_3_occupied", boolean.class, Boolean::parseBoolean),
    SLOT_4_OCCUPIED("slot_4_occupied", boolean.class, Boolean::parseBoolean),
    SLOT_5_OCCUPIED("slot_5_occupied", boolean.class, Boolean::parseBoolean),
    SNOWY("snowy", boolean.class, Boolean::parseBoolean),
    STAGE("stage", int.class, Integer::parseInt),
    SOUTH("south", South.class, South::valueOf),
    THICKNESS("thickness", Thickness.class, Thickness::valueOf),
    TILT("tilt", Tilt.class, Tilt::valueOf),
    TRIAL_SPAWNER_STATE("trial_spawner_state", TrialSpawnerState.class, TrialSpawnerState::valueOf),
    TRIGGERED("triggered", boolean.class, Boolean::parseBoolean),
    TYPE("type", Type.class, Type::valueOf),
    UNSTABLE("unstable", boolean.class, Boolean::parseBoolean),
    UP("up", boolean.class, Boolean::parseBoolean),
    VAULT_STATE("vault_state", VaultState.class, VaultState::valueOf),
    VERTICAL_DIRECTION("vertical_direction", VerticalDirection.class, VerticalDirection::valueOf),
    WATERLOGGED("waterlogged", boolean.class, Boolean::parseBoolean),
    WEST("west", West.class, West::valueOf),
    @Obsolete
    CREAKING("creaking", CreakingHeartState.class, CreakingHeartState::valueOf),
    @Obsolete
    ACTIVE("active", boolean.class, Boolean::parseBoolean),
    NATURAL("natural", boolean.class, Boolean::parseBoolean),
    SEGMENT_AMOUNT("segment_amount", int.class, Integer::parseInt),
    CREAKING_HEART_STATE(
            "creaking_heart_state", CreakingHeartState.class, CreakingHeartState::valueOf),
    MAP("map", boolean.class, Boolean::parseBoolean),
    HYDRATION("hydration", int.class, Integer::parseInt),
    SIDE_CHAIN("side_chain", SideChainPart.class, SideChainPart::valueOf),
    COPPER_GOLEM_POSE("copper_golem_pose", CopperGolemPose.class, CopperGolemPose::valueOf),
    POTENT_SULFUR_STATE("potent_sulfur_state", PotentSulfurState.class, PotentSulfurState::valueOf);

    public static final Index<String, StateValue> NAME_INDEX =
            Index.create(StateValue.class, StateValue::getName);

    private final String name;

    private final Class<?> dataClass;

    private final Function<String, Object> parser;

    StateValue(String name, Class<?> classValue, Function<String, Object> function) {
        this.name = name;
        this.dataClass = classValue;
        this.parser = function;
    }

    @Nullable
    public static StateValue byName(String text) {
        return NAME_INDEX.value(text);
    }

    public Object parse(String text) {
        return this.parser.apply(text);
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getDataClass() {
        return this.dataClass;
    }

    public Function<String, Object> getParser() {
        return this.parser;
    }
}
