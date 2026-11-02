package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.stream.Stream;

public interface BlockNBTComponent
        extends NBTComponent<BlockNBTComponent, BlockNBTComponent.Builder>,
                ScopedComponent<BlockNBTComponent> {

    @NotNull
    BlockNBTComponent.Pos pos();

    @Contract(pure = true)
    @NotNull
    BlockNBTComponent pos(@NotNull BlockNBTComponent.Pos pos2);

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent localPos(double value, double value2, double value3) {
        return this.pos(BlockNBTComponent.LocalPos.localPos(value, value2, value3));
    }

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent worldPos(
            BlockNBTComponent.WorldPos.@NotNull Coordinate coordinate,
            BlockNBTComponent.WorldPos.@NotNull Coordinate coordinate2,
            BlockNBTComponent.WorldPos.@NotNull Coordinate coordinate3) {
        return this.pos(BlockNBTComponent.WorldPos.worldPos(coordinate, coordinate2, coordinate3));
    }

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent absoluteWorldPos(int value, int value2, int value3) {
        return this.worldPos(
                BlockNBTComponent.WorldPos.Coordinate.absolute(value),
                BlockNBTComponent.WorldPos.Coordinate.absolute(value2),
                BlockNBTComponent.WorldPos.Coordinate.absolute(value3));
    }

    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent relativeWorldPos(int value, int value2, int value3) {
        return this.worldPos(
                BlockNBTComponent.WorldPos.Coordinate.relative(value),
                BlockNBTComponent.WorldPos.Coordinate.relative(value2),
                BlockNBTComponent.WorldPos.Coordinate.relative(value3));
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(ExaminableProperty.of("pos", this.pos())),
                NBTComponent.super.examinableProperties());
    }

    interface Builder extends NBTComponentBuilder<BlockNBTComponent, BlockNBTComponent.Builder> {

        @Contract("_ -> this")
        @NotNull
        BlockNBTComponent.Builder pos(@NotNull BlockNBTComponent.Pos pos2);

        @Contract("_, _, _ -> this")
        @NotNull
        default BlockNBTComponent.Builder localPos(double value, double value2, double value3) {
            return this.pos(BlockNBTComponent.LocalPos.localPos(value, value2, value3));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default BlockNBTComponent.Builder worldPos(
                BlockNBTComponent.WorldPos.@NotNull Coordinate coordinate,
                BlockNBTComponent.WorldPos.@NotNull Coordinate coordinate2,
                BlockNBTComponent.WorldPos.@NotNull Coordinate coordinate3) {
            return this.pos(
                    BlockNBTComponent.WorldPos.worldPos(coordinate, coordinate2, coordinate3));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default BlockNBTComponent.Builder absoluteWorldPos(int value, int value2, int value3) {
            return this.worldPos(
                    BlockNBTComponent.WorldPos.Coordinate.absolute(value),
                    BlockNBTComponent.WorldPos.Coordinate.absolute(value2),
                    BlockNBTComponent.WorldPos.Coordinate.absolute(value3));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default BlockNBTComponent.Builder relativeWorldPos(int value, int value2, int value3) {
            return this.worldPos(
                    BlockNBTComponent.WorldPos.Coordinate.relative(value),
                    BlockNBTComponent.WorldPos.Coordinate.relative(value2),
                    BlockNBTComponent.WorldPos.Coordinate.relative(value3));
        }
    }

    interface LocalPos extends BlockNBTComponent.Pos {

        @NotNull
        static BlockNBTComponent.LocalPos localPos(double value, double value2, double value3) {
            return new BlockNBTComponentImpl.LocalPosImpl(value, value2, value3);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        static BlockNBTComponent.LocalPos of(double value, double value2, double value3) {
            return new BlockNBTComponentImpl.LocalPosImpl(value, value2, value3);
        }

        double left();

        double up();

        double forwards();
    }

    interface Pos extends Examinable {

        @NotNull
        static BlockNBTComponent.Pos fromString(@NotNull String text)
                throws IllegalArgumentException {
            Matcher matcher2;
            if ((matcher2 = BlockNBTComponentImpl.Tokens.LOCAL_PATTERN.matcher(text)).matches()) {
                return BlockNBTComponent.LocalPos.localPos(
                        Double.parseDouble(matcher2.group(1)),
                        Double.parseDouble(matcher2.group(3)),
                        Double.parseDouble(matcher2.group(5)));
            } else {
                Matcher matcher3;
                if ((matcher3 = BlockNBTComponentImpl.Tokens.WORLD_PATTERN.matcher(text))
                        .matches()) {
                    return BlockNBTComponent.WorldPos.worldPos(
                            BlockNBTComponentImpl.Tokens.deserializeCoordinate(
                                    matcher3.group(1), matcher3.group(2)),
                            BlockNBTComponentImpl.Tokens.deserializeCoordinate(
                                    matcher3.group(3), matcher3.group(4)),
                            BlockNBTComponentImpl.Tokens.deserializeCoordinate(
                                    matcher3.group(5), matcher3.group(6)));
                } else {
                    throw new IllegalArgumentException(
                            "Cannot convert position specification '" + text + "' into a position");
                }
            }
        }

        @NotNull
        String asString();
    }

    interface WorldPos extends BlockNBTComponent.Pos {

        @NotNull
        static BlockNBTComponent.WorldPos worldPos(
                @NotNull BlockNBTComponent.WorldPos.Coordinate coordinate,
                @NotNull BlockNBTComponent.WorldPos.Coordinate coordinate2,
                @NotNull BlockNBTComponent.WorldPos.Coordinate coordinate3) {
            return new BlockNBTComponentImpl.WorldPosImpl(coordinate, coordinate2, coordinate3);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        static BlockNBTComponent.WorldPos of(
                @NotNull BlockNBTComponent.WorldPos.Coordinate coordinate,
                @NotNull BlockNBTComponent.WorldPos.Coordinate coordinate2,
                @NotNull BlockNBTComponent.WorldPos.Coordinate coordinate3) {
            return new BlockNBTComponentImpl.WorldPosImpl(coordinate, coordinate2, coordinate3);
        }

        @NotNull
        BlockNBTComponent.WorldPos.Coordinate x();

        @NotNull
        BlockNBTComponent.WorldPos.Coordinate y();

        @NotNull
        BlockNBTComponent.WorldPos.Coordinate z();

        interface Coordinate extends Examinable {

            @NotNull
            static BlockNBTComponent.WorldPos.Coordinate absolute(int value) {
                return coordinate(value, BlockNBTComponent.WorldPos.Coordinate.Type.ABSOLUTE);
            }

            @NotNull
            static BlockNBTComponent.WorldPos.Coordinate relative(int value) {
                return coordinate(value, BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE);
            }

            @NotNull
            static BlockNBTComponent.WorldPos.Coordinate coordinate(
                    int value, @NotNull BlockNBTComponent.WorldPos.Coordinate.Type type) {
                return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(value, type);
            }

            @Deprecated
            @ScheduledForRemoval(inVersion = "5.0.0")
            @NotNull
            static BlockNBTComponent.WorldPos.Coordinate of(
                    int value, @NotNull BlockNBTComponent.WorldPos.Coordinate.Type type) {
                return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(value, type);
            }

            int value();

            @NotNull
            BlockNBTComponent.WorldPos.Coordinate.Type type();

            enum Type {
                ABSOLUTE,
                RELATIVE
            }
        }
    }
}
