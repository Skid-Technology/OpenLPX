package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import java.util.Map;

public abstract class MapDiff<K, V> implements Diff<Map<K, V>> {

    private final K key;

    public MapDiff(K k) {
        this.key = (K) k;
    }

    public K getKey() {
        return this.key;
    }

    public abstract void applyTo(Map<K, V> entries);

    public static class Addition<K, V> extends MapDiff<K, V> {

        private final V value;

        public Addition(K k, V v) {
            super((K) k);
            this.value = (V) v;
        }

        public V getValue() {
            return this.value;
        }

        @Override
        public void applyTo(Map<K, V> entries) {
            entries.put(this.getKey(), this.getValue());
        }

        @Override
        public String toString() {
            return "+ " + this.getKey() + " : " + this.getValue();
        }
    }

    public static class Removal<K, V> extends MapDiff<K, V> {

        public Removal(K k) {
            super((K) k);
        }

        @Override
        public void applyTo(Map<K, V> entries) {
            entries.remove(this.getKey());
        }

        @Override
        public String toString() {
            return "- " + this.getKey();
        }
    }
}
