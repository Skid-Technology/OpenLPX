package it.ytnoos.lpx.lib.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedHashTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {

    private static final Comparator<Comparable> NATURAL_ORDER =
            new Comparator<Comparable>() {

                public int compare(Comparable comparable, Comparable comparable2) {
                    return comparable.compareTo(comparable2);
                }
            };

    Comparator<? super K> comparator;

    LinkedHashTreeMap.Node<K, V>[] table;

    final LinkedHashTreeMap.Node<K, V> header;

    int size = 0;

    int modCount = 0;

    int threshold;

    private LinkedHashTreeMap<K, V>.EntrySet entrySet;

    private LinkedHashTreeMap<K, V>.KeySet keySet;

    public LinkedHashTreeMap() {
        this((Comparator) NATURAL_ORDER);
    }

    public LinkedHashTreeMap(Comparator<? super K> comparator2) {
        this.comparator = (Comparator) (comparator2 != null ? comparator2 : NATURAL_ORDER);
        this.header = new LinkedHashTreeMap.Node<>();
        this.table = new LinkedHashTreeMap.Node[16];
        this.threshold = this.table.length / 2 + this.table.length / 4;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public V get(Object result) {
        LinkedHashTreeMap.Node node;
        return (((V) ((node = this.findByObject(result)) != null ? node.value : null)));
    }

    @Override
    public boolean containsKey(Object result) {
        return this.findByObject(result) != null;
    }

    @Override
    public V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        LinkedHashTreeMap.Node node;
        V v2 = ((V) (node = this.find((K) k, true)).value);
        node.value = (V) v;
        return (V) v2;
    }

    @Override
    public void clear() {
        Arrays.fill(this.table, null);
        this.size = 0;
        this.modCount++;
        LinkedHashTreeMap.Node node = this.header;
        LinkedHashTreeMap.Node node2 = this.header.next;
        while (node2 != node) {
            LinkedHashTreeMap.Node node3 = node2.next;
            node2.next = node2.prev = null;
            node2 = node3;
        }
        node.next = node.prev = node;
    }

    @Override
    public V remove(Object result) {
        LinkedHashTreeMap.Node node;
        return (((V) ((node = this.removeInternalByKey(result)) != null ? node.value : null)));
    }

    LinkedHashTreeMap.Node<K, V> find(K k, boolean matches) {
        Comparator comparator2 = this.comparator;
        LinkedHashTreeMap.Node[] nodes = this.table;
        int value;
        int value2 = (value = secondaryHash(k.hashCode())) & nodes.length - 1;
        LinkedHashTreeMap.Node node = nodes[value2];
        byte value3 = 0;
        if (node != null) {
            Comparable comparable = comparator2 == NATURAL_ORDER ? (Comparable) k : null;
            while (true) {
                if ((value3 =
                                (byte)
                                        (comparable != null
                                                ? comparable.compareTo(node.key)
                                                : comparator2.compare(k, node.key)))
                        == 0) {
                    return node;
                }
                LinkedHashTreeMap.Node node2 = value3 < 0 ? node.left : node.right;
                LinkedHashTreeMap.Node node3 = value3 < 0 ? node.left : node.right;
                if (node2 == null) {
                    break;
                }
                node = node3;
            }
        }
        if (!matches) {
            return null;
        }
        LinkedHashTreeMap.Node node4 = this.header;
        LinkedHashTreeMap.Node node5;
        if (node == null) {
            if (comparator2 == NATURAL_ORDER && !(k instanceof Comparable)) {
                throw new ClassCastException(k.getClass().getName() + " is not Comparable");
            }
            node5 = new LinkedHashTreeMap.Node<>(node, k, value, node4, node4.prev);
            nodes[value2] = node5;
        } else {
            node5 = new LinkedHashTreeMap.Node<>(node, k, value, node4, node4.prev);
            if (value3 < 0) {
                node.left = node5;
            } else {
                node.right = node5;
            }
            this.rebalance(node, true);
        }
        if (this.size++ > this.threshold) {
            this.doubleCapacity();
        }
        this.modCount++;
        return node5;
    }

    LinkedHashTreeMap.Node<K, V> findByObject(Object result) {
        try {
            return result != null ? this.find((K) result, false) : null;
        } catch (ClassCastException classCastException) {
            return null;
        }
    }

    LinkedHashTreeMap.Node<K, V> findByEntry(Entry<?, ?> entry) {
        LinkedHashTreeMap.Node node;
        return (node = this.findByObject(entry.getKey())) != null
                        && this.equal(node.value, entry.getValue())
                ? node
                : null;
    }

    private boolean equal(Object result, Object result2) {
        return result == result2 || result != null && result.equals(result2);
    }

    private static int secondaryHash(int value) {
        int value2;
        return value
                ^ value >>> 20
                ^ value >>> 12
                ^ (value2 = value ^ value >>> 20 ^ value >>> 12) >>> 7
                ^ value2 >>> 4;
    }

    void removeInternal(LinkedHashTreeMap.Node<K, V> node, boolean matches) {
        if (matches) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = node.prev = null;
        }
        LinkedHashTreeMap.Node node2 = node.left;
        LinkedHashTreeMap.Node node3 = node.right;
        LinkedHashTreeMap.Node node4 = node.parent;
        if (node2 != null && node3 != null) {
            LinkedHashTreeMap.Node node5 =
                    node2.height > node3.height ? node2.last() : node3.first();
            this.removeInternal(node5, false);
            int value = 0;
            node2 = node.left;
            if (node.left != null) {
                value = node2.height;
                node5.left = node2;
                node2.parent = node5;
                node.left = null;
            }
            int value2 = 0;
            node3 = node.right;
            if (node.right != null) {
                value2 = node3.height;
                node5.right = node3;
                node3.parent = node5;
                node.right = null;
            }
            node5.height = Math.max(value, value2) + 1;
            this.replaceInParent(node, node5);
        } else {
            if (node2 != null) {
                this.replaceInParent(node, node2);
                node.left = null;
            } else if (node3 != null) {
                this.replaceInParent(node, node3);
                node.right = null;
            } else {
                this.replaceInParent(node, null);
            }
            this.rebalance(node4, false);
            this.size--;
            this.modCount++;
        }
    }

    LinkedHashTreeMap.Node<K, V> removeInternalByKey(Object result) {
        LinkedHashTreeMap.Node node;
        if ((node = this.findByObject(result)) != null) {
            this.removeInternal(node, true);
        }
        return node;
    }

    private void replaceInParent(
            LinkedHashTreeMap.Node<K, V> node, LinkedHashTreeMap.Node<K, V> node2) {
        LinkedHashTreeMap.Node node3 = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = node3;
        }
        if (node3 != null) {
            if (node3.left == node) {
                node3.left = node2;
            } else {
                assert node3.right == node;
                node3.right = node2;
            }
        } else {
            int value = node.hash & this.table.length - 1;
            this.table[value] = node2;
        }
    }

    private void rebalance(LinkedHashTreeMap.Node<K, V> node, boolean matches) {
        for (LinkedHashTreeMap.Node node2 = node; node2 != null; node2 = node2.parent) {
            LinkedHashTreeMap.Node node3 = node2.left;
            LinkedHashTreeMap.Node node4 = node2.right;
            int value = node3 != null ? node3.height : 0;
            int value2 = node4 != null ? node4.height : 0;
            int value3;
            if ((value3 = value - value2) == -2) {
                LinkedHashTreeMap.Node node5 = node4.left;
                LinkedHashTreeMap.Node node6 = node4.right;
                int value4 = node4.right != null ? node6.height : 0;
                int value5;
                if ((value5 = (node5 != null ? node5.height : 0) - value4) != -1
                        && (value5 != 0 || matches)) {
                    assert value5 == 1;
                    this.rotateRight(node4);
                    this.rotateLeft(node2);
                } else {
                    this.rotateLeft(node2);
                }
                if (matches) {
                    break;
                }
            } else if (value3 == 2) {
                LinkedHashTreeMap.Node node7 = node3.left;
                LinkedHashTreeMap.Node node8 = node3.right;
                int value6 = node3.right != null ? node8.height : 0;
                int value7;
                if ((value7 = (node7 != null ? node7.height : 0) - value6) != 1
                        && (value7 != 0 || matches)) {
                    assert value7 == -1;
                    this.rotateLeft(node3);
                    this.rotateRight(node2);
                } else {
                    this.rotateRight(node2);
                }
                if (matches) {
                    break;
                }
            } else if (value3 == 0) {
                node2.height = value + 1;
                if (matches) {
                    break;
                }
            } else {
                assert value3 == -1 || value3 == 1;
                node2.height = Math.max(value, value2) + 1;
                if (!matches) {
                    break;
                }
            }
        }
    }

    private void rotateLeft(LinkedHashTreeMap.Node<K, V> node) {
        LinkedHashTreeMap.Node node2 = node.left;
        LinkedHashTreeMap.Node node3 = node.right;
        LinkedHashTreeMap.Node node4 = node.right.left;
        LinkedHashTreeMap.Node node5 = node3.right;
        node.right = node4;
        if (node4 != null) {
            node4.parent = node;
        }
        this.replaceInParent(node, node3);
        node3.left = node;
        node.parent = node3;
        node.height =
                Math.max(node2 != null ? node2.height : 0, node4 != null ? node4.height : 0) + 1;
        node3.height = Math.max(node.height, node5 != null ? node5.height : 0) + 1;
    }

    private void rotateRight(LinkedHashTreeMap.Node<K, V> node) {
        LinkedHashTreeMap.Node node2 = node.left;
        LinkedHashTreeMap.Node node3 = node.right;
        LinkedHashTreeMap.Node node4 = node2.left;
        LinkedHashTreeMap.Node node5 = node2.right;
        node.left = node5;
        if (node5 != null) {
            node5.parent = node;
        }
        this.replaceInParent(node, node2);
        node2.right = node;
        node.parent = node2;
        node.height =
                Math.max(node3 != null ? node3.height : 0, node5 != null ? node5.height : 0) + 1;
        node2.height = Math.max(node.height, node4 != null ? node4.height : 0) + 1;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        LinkedHashTreeMap.EntrySet entrySet2 = this.entrySet;
        return this.entrySet != null
                ? entrySet2
                : (this.entrySet = new LinkedHashTreeMap.EntrySet());
    }

    @Override
    public Set<K> keySet() {
        LinkedHashTreeMap.KeySet keySet2 = this.keySet;
        return this.keySet != null ? keySet2 : (this.keySet = new LinkedHashTreeMap.KeySet());
    }

    private void doubleCapacity() {
        this.table = doubleCapacity(this.table);
        this.threshold = this.table.length / 2 + this.table.length / 4;
    }

    static <K, V> LinkedHashTreeMap.Node<K, V>[] doubleCapacity(
            LinkedHashTreeMap.Node<K, V>[] nodes) {
        int value;
        LinkedHashTreeMap.Node[] nodes2 = new LinkedHashTreeMap.Node[(value = nodes.length) * 2];
        LinkedHashTreeMap.AvlIterator avlIterator = new LinkedHashTreeMap.AvlIterator();
        LinkedHashTreeMap.AvlBuilder avlBuilder = new LinkedHashTreeMap.AvlBuilder();
        LinkedHashTreeMap.AvlBuilder avlBuilder2 = new LinkedHashTreeMap.AvlBuilder();
        for (int index = 0; index < value; index++) {
            LinkedHashTreeMap.Node node;
            if ((node = nodes[index]) != null) {
                avlIterator.reset(node);
                int value2 = 0;
                int value3 = 0;
                LinkedHashTreeMap.Node node2;
                while ((node2 = avlIterator.next()) != null) {
                    if ((node2.hash & value) == 0) {
                        value2++;
                    } else {
                        value3++;
                    }
                }
                avlBuilder.reset(value2);
                avlBuilder2.reset(value3);
                avlIterator.reset(node);
                while ((node2 = avlIterator.next()) != null) {
                    if ((node2.hash & value) == 0) {
                        avlBuilder.add(node2);
                    } else {
                        avlBuilder2.add(node2);
                    }
                }
                nodes2[index] = value2 > 0 ? avlBuilder.root() : null;
                nodes2[index + value] = value3 > 0 ? avlBuilder2.root() : null;
            }
        }
        return nodes2;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap<>(this);
    }

    static final class AvlBuilder<K, V> {

        private LinkedHashTreeMap.Node<K, V> stack;

        private int leavesToSkip;

        private int leavesSkipped;

        private int size;

        void reset(int value) {
            int value2 = Integer.highestOneBit(value) * 2 - 1;
            this.leavesToSkip = value2 - value;
            this.size = 0;
            this.leavesSkipped = 0;
            this.stack = null;
        }

        void add(LinkedHashTreeMap.Node<K, V> node) {
            node.left = node.parent = node.right = null;
            node.height = 1;
            if (this.leavesToSkip > 0 && (this.size & 1) == 0) {
                this.size++;
                this.leavesToSkip--;
                this.leavesSkipped++;
            }
            node.parent = this.stack;
            this.stack = node;
            this.size++;
            if (this.leavesToSkip > 0 && (this.size & 1) == 0) {
                this.size++;
                this.leavesToSkip--;
                this.leavesSkipped++;
            }
            for (byte index = 4; (this.size & index - 1) == index - 1; index *= 2) {
                if (this.leavesSkipped == 0) {
                    LinkedHashTreeMap.Node node2 = this.stack;
                    LinkedHashTreeMap.Node node3 = this.stack.parent;
                    LinkedHashTreeMap.Node node4 = this.stack.parent.parent;
                    node3.parent = node4.parent;
                    this.stack = node3;
                    node3.left = node4;
                    node3.right = node2;
                    node3.height = node2.height + 1;
                    node4.parent = node3;
                    node2.parent = node3;
                } else if (this.leavesSkipped == 1) {
                    LinkedHashTreeMap.Node node5 = this.stack;
                    LinkedHashTreeMap.Node node6 = this.stack.parent;
                    this.stack = node6;
                    node6.right = node5;
                    node6.height = node5.height + 1;
                    node5.parent = node6;
                    this.leavesSkipped = 0;
                } else if (this.leavesSkipped == 2) {
                    this.leavesSkipped = 0;
                }
            }
        }

        LinkedHashTreeMap.Node<K, V> root() {
            LinkedHashTreeMap.Node node = this.stack;
            if (this.stack.parent != null) {
                throw new IllegalStateException();
            } else {
                return node;
            }
        }
    }

    static class AvlIterator<K, V> {

        private LinkedHashTreeMap.Node<K, V> stackTop;

        void reset(LinkedHashTreeMap.Node<K, V> node) {
            LinkedHashTreeMap.Node node2 = null;
            for (LinkedHashTreeMap.Node node3 = node; node3 != null; node3 = node3.left) {
                node3.parent = node2;
                node2 = node3;
            }
            this.stackTop = node2;
        }

        public LinkedHashTreeMap.Node<K, V> next() {
            LinkedHashTreeMap.Node node = this.stackTop;
            if (this.stackTop == null) {
                return null;
            }
            LinkedHashTreeMap.Node node2 = node;
            node = node.parent;
            node2.parent = null;
            for (LinkedHashTreeMap.Node node3 = node2.right; node3 != null; node3 = node3.left) {
                node3.parent = node;
                node = node3;
            }
            this.stackTop = node;
            return node2;
        }
    }

    final class EntrySet extends AbstractSet<Entry<K, V>> {

        @Override
        public int size() {
            return LinkedHashTreeMap.this.size;
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new LinkedHashTreeMap<K, V>.LinkedTreeMapIterator<Entry<K, V>>() {

                public Entry<K, V> next() {
                    return this.nextNode();
                }
            };
        }

        @Override
        public boolean contains(Object result) {
            return result instanceof Entry
                    && LinkedHashTreeMap.this.findByEntry((Entry<?, ?>) result) != null;
        }

        @Override
        public boolean remove(Object result) {
            if (!(result instanceof Entry)) {
                return false;
            }
            LinkedHashTreeMap.Node node;
            if ((node = LinkedHashTreeMap.this.findByEntry((Entry<?, ?>) result)) == null) {
                return false;
            }
            LinkedHashTreeMap.this.removeInternal(node, true);
            return true;
        }

        @Override
        public void clear() {
            LinkedHashTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {

        @Override
        public int size() {
            return LinkedHashTreeMap.this.size;
        }

        @Override
        public Iterator<K> iterator() {
            return new LinkedHashTreeMap<K, V>.LinkedTreeMapIterator<K>() {

                @Override
                public K next() {
                    return this.nextNode().key;
                }
            };
        }

        @Override
        public boolean contains(Object result) {
            return LinkedHashTreeMap.this.containsKey(result);
        }

        @Override
        public boolean remove(Object result) {
            return LinkedHashTreeMap.this.removeInternalByKey(result) != null;
        }

        @Override
        public void clear() {
            LinkedHashTreeMap.this.clear();
        }
    }

    private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {

        LinkedHashTreeMap.Node<K, V> next;

        LinkedHashTreeMap.Node<K, V> lastReturned;

        int expectedModCount;

        LinkedTreeMapIterator() {
            this.next = LinkedHashTreeMap.this.header.next;
            this.lastReturned = null;
            this.expectedModCount = LinkedHashTreeMap.this.modCount;
        }

        @Override
        public final boolean hasNext() {
            return this.next != LinkedHashTreeMap.this.header;
        }

        final LinkedHashTreeMap.Node<K, V> nextNode() {
            LinkedHashTreeMap.Node node = this.next;
            if (this.next == LinkedHashTreeMap.this.header) {
                throw new NoSuchElementException();
            }
            if (LinkedHashTreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = node.next;
            return this.lastReturned = node;
        }

        @Override
        public final void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            LinkedHashTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedHashTreeMap.this.modCount;
        }
    }

    static final class Node<K, V> implements Entry<K, V> {

        LinkedHashTreeMap.Node<K, V> parent;

        LinkedHashTreeMap.Node<K, V> left;

        LinkedHashTreeMap.Node<K, V> right;

        LinkedHashTreeMap.Node<K, V> next;

        LinkedHashTreeMap.Node<K, V> prev;

        final K key;

        final int hash;

        V value;

        int height;

        Node() {
            this.key = null;
            this.hash = -1;
            this.next = this.prev = this;
        }

        Node(
                LinkedHashTreeMap.Node<K, V> node,
                K k,
                int hash,
                LinkedHashTreeMap.Node<K, V> node2,
                LinkedHashTreeMap.Node<K, V> node3) {
            this.parent = node;
            this.key = (K) k;
            this.hash = hash;
            this.height = 1;
            this.next = node2;
            this.prev = node3;
            node3.next = this;
            node2.prev = this;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            V v2 = this.value;
            this.value = (V) v;
            return (V) v2;
        }

        @Override
        public boolean equals(Object result) {
            if (!(result instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) result;
            return (this.key == null ? entry.getKey() == null : this.key.equals(entry.getKey()))
                    && (this.value == null
                            ? entry.getValue() == null
                            : this.value.equals(entry.getValue()));
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode())
                    ^ (this.value == null ? 0 : this.value.hashCode());
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }

        public LinkedHashTreeMap.Node<K, V> first() {
            LinkedHashTreeMap.Node node = this;
            for (LinkedHashTreeMap.Node node2 = this.left; node2 != null; node2 = node2.left) {
                node = node2;
            }
            return node;
        }

        public LinkedHashTreeMap.Node<K, V> last() {
            LinkedHashTreeMap.Node node = this;
            for (LinkedHashTreeMap.Node node2 = this.right; node2 != null; node2 = node2.right) {
                node = node2;
            }
            return node;
        }
    }
}
