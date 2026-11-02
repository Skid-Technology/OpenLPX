package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.util.ShadyPines;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

final class BlockNBTComponentImpl
        extends NBTComponentImpl<BlockNBTComponent, BlockNBTComponent.Builder>
        implements BlockNBTComponent {

    private final BlockNBTComponent.Pos pos;

    static BlockNBTComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            String text,
            boolean matches,
            @Nullable ComponentLike componentLike,
            @NotNull BlockNBTComponent.Pos pos) {
        return new BlockNBTComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(text, "nbtPath"),
                matches,
                ComponentLike.unbox(componentLike),
                Objects.requireNonNull(pos, "pos"));
    }

    BlockNBTComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            String text,
            boolean matches,
            @Nullable Component component,
            @NotNull BlockNBTComponent.Pos pos2) {
        super(values, style, text, matches, component);
        this.pos = pos2;
    }

    @NotNull
    public BlockNBTComponent nbtPath(@NotNull String text) {
        return Objects.equals(this.nbtPath, text)
                ? this
                : create(this.children, this.style, text, this.interpret, this.separator, this.pos);
    }

    @NotNull
    public BlockNBTComponent interpret(boolean matches) {
        return this.interpret == matches
                ? this
                : create(
                        this.children, this.style, this.nbtPath, matches, this.separator, this.pos);
    }

    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }

    @NotNull
    public BlockNBTComponent separator(@Nullable ComponentLike componentLike) {
        return create(
                this.children, this.style, this.nbtPath, this.interpret, componentLike, this.pos);
    }

    @NotNull
    @Override
    public BlockNBTComponent.Pos pos() {
        return this.pos;
    }

    @NotNull
    @Override
    public BlockNBTComponent pos(@NotNull BlockNBTComponent.Pos pos2) {
        return create(
                this.children, this.style, this.nbtPath, this.interpret, this.separator, pos2);
    }

    @NotNull
    public BlockNBTComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(values, this.style, this.nbtPath, this.interpret, this.separator, this.pos);
    }

    @NotNull
    public BlockNBTComponent style(@NotNull Style style2) {
        return create(
                this.children, style2, this.nbtPath, this.interpret, this.separator, this.pos);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof BlockNBTComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        BlockNBTComponent blockNBTComponent = (BlockNBTComponent) result;
        return Objects.equals(this.pos, blockNBTComponent.pos());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        return 1064457403 * value + this.pos.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    public BlockNBTComponent.@NotNull Builder toBuilder() {
        return new BlockNBTComponentImpl.BuilderImpl(this);
    }

    static final class BuilderImpl
            extends AbstractNBTComponentBuilder<BlockNBTComponent, BlockNBTComponent.Builder>
            implements BlockNBTComponent.Builder {

        @Nullable private BlockNBTComponent.Pos pos;

        BuilderImpl() {}

        BuilderImpl(@NotNull BlockNBTComponent blockNBTComponent) {
            super(blockNBTComponent);
            this.pos = blockNBTComponent.pos();
        }

        @Override
        public BlockNBTComponent.@NotNull Builder pos(@NotNull BlockNBTComponent.Pos pos2) {
            this.pos = Objects.requireNonNull(pos2, "pos");
            return this;
        }

        @NotNull
        public BlockNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            } else if (this.pos == null) {
                throw new IllegalStateException("pos must be set");
            } else {
                return BlockNBTComponentImpl.create(
                        this.children,
                        this.buildStyle(),
                        this.nbtPath,
                        this.interpret,
                        this.separator,
                        this.pos);
            }
        }
    }

    static final class LocalPosImpl implements BlockNBTComponent.LocalPos {

        private final double left;

        private final double up;

        private final double forwards;

        LocalPosImpl(double left, double up, double forwards) {
            this.left = left;
            this.up = up;
            this.forwards = forwards;
        }

        @Override
        public double left() {
            return this.left;
        }

        @Override
        public double up() {
            return this.up;
        }

        @Override
        public double forwards() {
            return this.forwards;
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("left", this.left),
                    ExaminableProperty.of("up", this.up),
                    ExaminableProperty.of("forwards", this.forwards));
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            }
            BlockNBTComponent.LocalPos localPos;
            return !(result instanceof BlockNBTComponent.LocalPos)
                    ? false
                    : ShadyPines.equals(
                                    (localPos = (BlockNBTComponent.LocalPos) result).left(),
                                    this.left())
                            && ShadyPines.equals(localPos.up(), this.up())
                            && ShadyPines.equals(localPos.forwards(), this.forwards());
        }

        @Override
        public int hashCode() {
            int value = Double.hashCode(this.left);
            value = 31 * value + Double.hashCode(this.up);
            return 31 * value + Double.hashCode(this.forwards);
        }

        @Override
        public String toString() {
            return String.format("^%f ^%f ^%f", this.left, this.up, this.forwards);
        }

        @NotNull
        @Override
        public String asString() {
            return BlockNBTComponentImpl.Tokens.serializeLocal(this.left)
                    + ' '
                    + BlockNBTComponentImpl.Tokens.serializeLocal(this.up)
                    + ' '
                    + BlockNBTComponentImpl.Tokens.serializeLocal(this.forwards);
        }
    }

    static final class Tokens {

        static final Pattern LOCAL_PATTERN =
                Pattern.compile(
                        "^\\^(-?\\d+(\\.\\d+)?) \\^(-?\\d+(\\.\\d+)?) \\^(-?\\d+(\\.\\d+)?)$");

        static final Pattern WORLD_PATTERN =
                Pattern.compile("^(~?)(-?\\d+) (~?)(-?\\d+) (~?)(-?\\d+)$");

        static final String LOCAL_SYMBOL = "^";

        static final String RELATIVE_SYMBOL = "~";

        static final String ABSOLUTE_SYMBOL = "";

        private Tokens() {}

        static BlockNBTComponent.WorldPos.Coordinate deserializeCoordinate(
                String text, String text2) {
            int value = Integer.parseInt(text2);
            if (text.equals("")) {
                return BlockNBTComponent.WorldPos.Coordinate.absolute(value);
            } else if (text.equals("~")) {
                return BlockNBTComponent.WorldPos.Coordinate.relative(value);
            } else {
                throw new AssertionError();
            }
        }

        static String serializeLocal(double value) {
            return "^" + value;
        }

        static String serializeCoordinate(BlockNBTComponent.WorldPos.Coordinate coordinate) {
            return (coordinate.type() == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE
                            ? "~"
                            : "")
                    + coordinate.value();
        }
    }

    static final class WorldPosImpl implements BlockNBTComponent.WorldPos {

        private final BlockNBTComponent.WorldPos.Coordinate x;

        private final BlockNBTComponent.WorldPos.Coordinate y;

        private final BlockNBTComponent.WorldPos.Coordinate z;

        WorldPosImpl(
                BlockNBTComponent.WorldPos.Coordinate coordinate,
                BlockNBTComponent.WorldPos.Coordinate coordinate2,
                BlockNBTComponent.WorldPos.Coordinate coordinate3) {
            this.x = Objects.requireNonNull(coordinate, "x");
            this.y = Objects.requireNonNull(coordinate2, "y");
            this.z = Objects.requireNonNull(coordinate3, "z");
        }

        @NotNull
        @Override
        public BlockNBTComponent.WorldPos.Coordinate x() {
            return this.x;
        }

        @NotNull
        @Override
        public BlockNBTComponent.WorldPos.Coordinate y() {
            return this.y;
        }

        @NotNull
        @Override
        public BlockNBTComponent.WorldPos.Coordinate z() {
            return this.z;
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("x", this.x),
                    ExaminableProperty.of("y", this.y),
                    ExaminableProperty.of("z", this.z));
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof BlockNBTComponent.WorldPos)) {
                return false;
            }
            BlockNBTComponent.WorldPos worldPos = (BlockNBTComponent.WorldPos) result;
            return this.x.equals(worldPos.x())
                    && this.y.equals(worldPos.y())
                    && this.z.equals(worldPos.z());
        }

        @Override
        public int hashCode() {
            int value = this.x.hashCode();
            value = 31 * value + this.y.hashCode();
            return 31 * value + this.z.hashCode();
        }

        @Override
        public String toString() {
            return this.x.toString() + ' ' + this.y.toString() + ' ' + this.z.toString();
        }

        @NotNull
        @Override
        public String asString() {
            return BlockNBTComponentImpl.Tokens.serializeCoordinate(this.x())
                    + ' '
                    + BlockNBTComponentImpl.Tokens.serializeCoordinate(this.y())
                    + ' '
                    + BlockNBTComponentImpl.Tokens.serializeCoordinate(this.z());
        }

        static final class CoordinateImpl implements BlockNBTComponent.WorldPos.Coordinate {

            private final int value;

            private final BlockNBTComponent.WorldPos.Coordinate.Type type;

            CoordinateImpl(int value, @NotNull BlockNBTComponent.WorldPos.Coordinate.Type type2) {
                this.value = value;
                this.type = Objects.requireNonNull(type2, "type");
            }

            @Override
            public int value() {
                return this.value;
            }

            @NotNull
            @Override
            public BlockNBTComponent.WorldPos.Coordinate.Type type() {
                return this.type;
            }

            @NotNull
            @Override
            public Stream<? extends ExaminableProperty> examinableProperties() {
                return Stream.of(
                        ExaminableProperty.of("value", this.value),
                        ExaminableProperty.of("type", this.type));
            }

            @Override
            public boolean equals(@Nullable Object result) {
                if (this == result) {
                    return true;
                }
                if (!(result instanceof BlockNBTComponent.WorldPos.Coordinate)) {
                    return false;
                }
                BlockNBTComponent.WorldPos.Coordinate coordinate =
                        (BlockNBTComponent.WorldPos.Coordinate) result;
                return this.value() == coordinate.value() && this.type() == coordinate.type();
            }

            @Override
            public int hashCode() {
                int value2 = this.value;
                return 31 * value2 + this.type.hashCode();
            }

            @Override
            public String toString() {
                return (this.type == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE ? "~" : "")
                        + this.value;
            }
        }
    }
}
