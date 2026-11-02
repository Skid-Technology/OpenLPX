package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Stream;

@Unmodifiable
final class DecorationMap extends AbstractMap<TextDecoration, TextDecoration.State>
        implements Examinable {

    static final TextDecoration[] DECORATIONS = TextDecoration.values();

    private static final TextDecoration.State[] STATES = TextDecoration.State.values();

    private static final int MAP_SIZE = DECORATIONS.length;

    private static final TextDecoration.State[] EMPTY_STATE_ARRAY = new TextDecoration.State[0];

    static final DecorationMap EMPTY = new DecorationMap(0);

    private static final DecorationMap.KeySet KEY_SET = new DecorationMap.KeySet();

    private final int bitSet;

    private volatile DecorationMap.EntrySet entrySet = null;

    private volatile DecorationMap.Values values = null;

    static DecorationMap fromMap(Map<TextDecoration, TextDecoration.State> entries) {
        if (entries instanceof DecorationMap) {
            return (DecorationMap) entries;
        }
        int value = 0;
        TextDecoration[] textDecorations = DECORATIONS;
        int value2 = DECORATIONS.length;
        for (int index = 0; index < value2; index++) {
            TextDecoration textDecoration = textDecorations[index];
            value |=
                    entries.getOrDefault(textDecoration, TextDecoration.State.NOT_SET).ordinal()
                            * offset(textDecoration);
        }
        return withBitSet(value);
    }

    static DecorationMap merge(
            Map<TextDecoration, TextDecoration.State> entries,
            Map<TextDecoration, TextDecoration.State> entries2) {
        int value = 0;
        TextDecoration[] textDecorations = DECORATIONS;
        int value2 = DECORATIONS.length;
        for (int index = 0; index < value2; index++) {
            TextDecoration textDecoration = textDecorations[index];
            value |=
                    entries.getOrDefault(
                                            textDecoration,
                                            entries2.getOrDefault(
                                                    textDecoration, TextDecoration.State.NOT_SET))
                                    .ordinal()
                            * offset(textDecoration);
        }
        return withBitSet(value);
    }

    private static DecorationMap withBitSet(int value) {
        return value == 0 ? EMPTY : new DecorationMap(value);
    }

    private static int offset(TextDecoration textDecoration) {
        return 1 << textDecoration.ordinal() * 2;
    }

    private DecorationMap(int bitSet) {
        this.bitSet = bitSet;
    }

    @NotNull
    public DecorationMap with(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(textDecoration, "decoration");
        int value = offset(textDecoration);
        return withBitSet(this.bitSet & ~(3 * value) | state.ordinal() * value);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        Stream<it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration> stream2 =
                Arrays.stream(DECORATIONS);
        return stream2.map(result -> ExaminableProperty.of(result.toString(), this.get(result)));
    }

    public TextDecoration.State get(Object result) {
        return result instanceof TextDecoration
                ? STATES[this.bitSet >> ((TextDecoration) result).ordinal() * 2 & 3]
                : null;
    }

    @Override
    public boolean containsKey(Object result) {
        return result instanceof TextDecoration;
    }

    @Override
    public int size() {
        return MAP_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NotNull
    @Override
    public Set<Entry<TextDecoration, TextDecoration.State>> entrySet() {
        if (this.entrySet == null) {
            synchronized (this) {
                if (this.entrySet == null) {
                    this.entrySet = new DecorationMap.EntrySet();
                }
            }
        }
        return this.entrySet;
    }

    @NotNull
    @Override
    public Set<TextDecoration> keySet() {
        return KEY_SET;
    }

    @NotNull
    @Override
    public Collection<TextDecoration.State> values() {
        if (this.values == null) {
            synchronized (this) {
                if (this.values == null) {
                    this.values = new DecorationMap.Values();
                }
            }
        }
        return this.values;
    }

    @Override
    public boolean equals(Object result) {
        if (result == this) {
            return true;
        } else {
            return result != null && result.getClass() == DecorationMap.class
                    ? this.bitSet == ((DecorationMap) result).bitSet
                    : false;
        }
    }

    @Override
    public int hashCode() {
        return this.bitSet;
    }

    final class EntrySet extends AbstractSet<Entry<TextDecoration, TextDecoration.State>> {

        @NotNull
        @Override
        public Iterator<Entry<TextDecoration, TextDecoration.State>> iterator() {
            return new Iterator<Entry<TextDecoration, TextDecoration.State>>() {

                private final Iterator<TextDecoration> decorations =
                        DecorationMap.KEY_SET.iterator();

                private final Iterator<TextDecoration.State> states =
                        DecorationMap.this.values().iterator();

                @Override
                public boolean hasNext() {
                    return this.decorations.hasNext() && this.states.hasNext();
                }

                public Entry<TextDecoration, TextDecoration.State> next() {
                    if (this.hasNext()) {
                        return new SimpleImmutableEntry<>(
                                this.decorations.next(), this.states.next());
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        }

        @Override
        public int size() {
            return DecorationMap.MAP_SIZE;
        }
    }

    static final class KeySet extends AbstractSet<TextDecoration> {

        @Override
        public boolean contains(Object result) {
            return result instanceof TextDecoration;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Object @NotNull [] toArray() {
            return Arrays.copyOf(DecorationMap.DECORATIONS, DecorationMap.MAP_SIZE, Object[].class);
        }

        @Override
        public <T> T @NotNull [] toArray(T @NotNull [] ts) {
            if (ts.length < DecorationMap.MAP_SIZE) {
                return (T[])
                        Arrays.copyOf(
                                DecorationMap.DECORATIONS,
                                DecorationMap.MAP_SIZE,
                                (Class<? extends T[]>) ts.getClass());
            }
            System.arraycopy(DecorationMap.DECORATIONS, 0, ts, 0, DecorationMap.MAP_SIZE);
            if (ts.length > DecorationMap.MAP_SIZE) {
                ts[DecorationMap.MAP_SIZE] = null;
            }
            return (T[]) ts;
        }

        @NotNull
        @Override
        public Iterator<TextDecoration> iterator() {
            return Spliterators.iterator(Arrays.spliterator(DecorationMap.DECORATIONS));
        }

        @Override
        public int size() {
            return DecorationMap.MAP_SIZE;
        }
    }

    final class Values extends AbstractCollection<TextDecoration.State> {

        @NotNull
        @Override
        public Iterator<TextDecoration.State> iterator() {
            return Spliterators.iterator(
                    Arrays.spliterator(this.toArray(DecorationMap.EMPTY_STATE_ARRAY)));
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Object @NotNull [] toArray() {
            Object[] results = new Object[DecorationMap.MAP_SIZE];
            for (int index = 0; index < DecorationMap.MAP_SIZE; index++) {
                results[index] = DecorationMap.this.get(DecorationMap.DECORATIONS[index]);
            }
            return results;
        }

        @Override
        public <T> T @NotNull [] toArray(T @NotNull [] ts) {
            if (ts.length < DecorationMap.MAP_SIZE) {
                return (T[])
                        Arrays.copyOf(
                                this.toArray(),
                                DecorationMap.MAP_SIZE,
                                (Class<? extends T[]>) ts.getClass());
            }
            System.arraycopy(this.toArray(), 0, ts, 0, DecorationMap.MAP_SIZE);
            if (ts.length > DecorationMap.MAP_SIZE) {
                ts[DecorationMap.MAP_SIZE] = null;
            }
            return (T[]) ts;
        }

        @Override
        public boolean contains(Object result) {
            return result instanceof TextDecoration.State && super.contains(result);
        }

        @Override
        public int size() {
            return DecorationMap.MAP_SIZE;
        }
    }
}
