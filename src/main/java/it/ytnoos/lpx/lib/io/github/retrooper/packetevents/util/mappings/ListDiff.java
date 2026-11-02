package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import java.util.List;

public abstract class ListDiff<T> implements Diff<List<T>> {

    private final int index;

    public ListDiff(int index) {
        this.index = index;
    }

    public abstract void applyTo(List<T> values);

    public int getIndex() {
        return this.index;
    }

    public static class Addition<T> extends ListDiff<T> {

        private final List<T> values;

        public Addition(int value, List<T> values) {
            super(value);
            this.values = values;
        }

        public List<T> getValues() {
            return this.values;
        }

        @Override
        public void applyTo(List<T> values) {
            values.addAll(this.getIndex(), this.getValues());
        }

        @Override
        public String toString() {
            return "+ " + this.getIndex() + " : " + this.getValues();
        }
    }

    public static class Changed<T> extends ListDiff<T> {

        private final int oldSize;

        private final List<T> newValue;

        public Changed(int value, int oldSize, List<T> newValue) {
            super(value);
            this.oldSize = oldSize;
            this.newValue = newValue;
        }

        public int getOldSize() {
            return this.oldSize;
        }

        public List<T> getNewValue() {
            return this.newValue;
        }

        @Override
        public String toString() {
            return "* " + this.getIndex() + " : " + this.getOldSize() + " -> " + this.getNewValue();
        }

        @Override
        public void applyTo(List<T> values) {
            values.subList(this.getIndex(), this.getIndex() + this.oldSize).clear();
            values.addAll(this.getIndex(), this.getNewValue());
        }
    }

    public static class Removal<T> extends ListDiff<T> {

        private final int size;

        public Removal(int value, int size) {
            super(value);
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }

        @Override
        public void applyTo(List<T> values) {
            values.subList(this.getIndex(), this.getIndex() + this.size).clear();
        }

        @Override
        public String toString() {
            return "- " + this.getIndex() + " : " + this.getSize();
        }
    }
}
