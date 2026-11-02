package it.ytnoos.lpx.lib.kyori.examination;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ExaminableProperty {

    private ExaminableProperty() {}

    @NotNull
    public abstract String name();

    @NotNull
    public abstract <R> R examine(@NotNull Examiner<? extends R> examiner);

    @Override
    public String toString() {
        return "ExaminableProperty{" + this.name() + "}";
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, @Nullable final Object result) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(result);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, @Nullable final String text2) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(text2);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final boolean matches) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(matches);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final boolean[] matchess) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(matchess);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final byte value) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final byte[] values) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(values);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final char value) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final char[] values) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(values);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final double value) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final double[] values) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(values);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final float value) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final float[] values) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(values);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final int value) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final int[] values) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(values);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final long value) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final long[] values) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(values);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final short value) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull final String text, final short[] values) {
        return new ExaminableProperty() {

            @NotNull
            @Override
            public String name() {
                return text;
            }

            @NotNull
            @Override
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return (R) examiner.examine(values);
            }
        };
    }
}
