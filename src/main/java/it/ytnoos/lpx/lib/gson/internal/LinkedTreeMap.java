package it.ytnoos.lpx.lib.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {

    private static final Comparator<Comparable> NATURAL_ORDER =
            new Comparator<Comparable>() {

                public int compare(Comparable comparable, Comparable comparable2) {
                    return comparable.compareTo(comparable2);
                }
            };

    Comparator<? super K> comparator;

    LinkedTreeMap.Node<K, V> root;

    int size = 0;

    int modCount = 0;

    final LinkedTreeMap.Node<K, V> header = new LinkedTreeMap.Node<>();

    private LinkedTreeMap<K, V>.EntrySet entrySet;

    private LinkedTreeMap<K, V>.KeySet keySet;

    public LinkedTreeMap() {
        this((Comparator) NATURAL_ORDER);
    }

    public LinkedTreeMap(Comparator<? super K> comparator2) {
        this.comparator = (Comparator) (comparator2 != null ? comparator2 : NATURAL_ORDER);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public V get(Object result) {
        LinkedTreeMap.Node node;
        return ((V) (((V) ((node = this.findByObject(result)) != null ? node.value : null))));
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
        LinkedTreeMap.Node node;
        V v2 = ((V) (node = this.find((K) k, true)).value);
        node.value = (V) v;
        return (V) v2;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
        this.modCount++;
        this.header.next = this.header.prev = this.header;
    }

    @Override
    public V remove(Object result) {
        LinkedTreeMap.Node node;
        return ((V)
                (((V) ((node = this.removeInternalByKey(result)) != null ? node.value : null))));
    }

    LinkedTreeMap.Node<K, V> find(K k, boolean matches) {
        Comparator comparator2 = this.comparator;
        LinkedTreeMap.Node node = this.root;
        byte value = 0;
        if (node != null) {
            Comparable comparable = comparator2 == NATURAL_ORDER ? (Comparable) k : null;
            while (true) {
                if ((value =
                                (byte)
                                        (comparable != null
                                                ? comparable.compareTo(node.key)
                                                : comparator2.compare(k, node.key)))
                        == 0) {
                    return node;
                }
                LinkedTreeMap.Node node2 = value < 0 ? node.left : node.right;
                LinkedTreeMap.Node node3 = value < 0 ? node.left : node.right;
                if (node2 == null) {
                    break;
                }
                node = node3;
            }
        }
        if (!matches) {
            return null;
        }
        LinkedTreeMap.Node node4 = this.header;
        LinkedTreeMap.Node node5;
        if (node == null) {
            if (comparator2 == NATURAL_ORDER && !(k instanceof Comparable)) {
                throw new ClassCastException(k.getClass().getName() + " is not Comparable");
            }
            node5 = new LinkedTreeMap.Node<>(node, k, node4, node4.prev);
            this.root = node5;
        } else {
            node5 = new LinkedTreeMap.Node<>(node, k, node4, node4.prev);
            if (value < 0) {
                node.left = node5;
            } else {
                node.right = node5;
            }
            this.rebalance(node, true);
        }
        this.size++;
        this.modCount++;
        return node5;
    }

    LinkedTreeMap.Node<K, V> findByObject(Object result) {
        try {
            return result != null ? this.find((K) result, false) : null;
        } catch (ClassCastException classCastException) {
            return null;
        }
    }

    LinkedTreeMap.Node<K, V> findByEntry(Entry<?, ?> entry) {
        LinkedTreeMap.Node node;
        return (node = this.findByObject(entry.getKey())) != null
                        && this.equal(node.value, entry.getValue())
                ? node
                : null;
    }

    private boolean equal(Object result, Object result2) {
        return result == result2 || result != null && result.equals(result2);
    }

    void removeInternal(LinkedTreeMap.Node<K, V> node, boolean matches) {
        if (matches) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        LinkedTreeMap.Node node2 = node.left;
        LinkedTreeMap.Node node3 = node.right;
        LinkedTreeMap.Node node4 = node.parent;
        if (node2 != null && node3 != null) {
            LinkedTreeMap.Node node5 = node2.height > node3.height ? node2.last() : node3.first();
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

    LinkedTreeMap.Node<K, V> removeInternalByKey(Object result) {
        LinkedTreeMap.Node node;
        if ((node = this.findByObject(result)) != null) {
            this.removeInternal(node, true);
        }
        return node;
    }

    private void replaceInParent(LinkedTreeMap.Node<K, V> node, LinkedTreeMap.Node<K, V> node2) {
        LinkedTreeMap.Node node3 = node.parent;
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
            this.root = node2;
        }
    }

    private void rebalance(LinkedTreeMap.Node<K, V> node, boolean matches) {
        for (LinkedTreeMap.Node node2 = node; node2 != null; node2 = node2.parent) {
            LinkedTreeMap.Node node3 = node2.left;
            LinkedTreeMap.Node node4 = node2.right;
            int value = node3 != null ? node3.height : 0;
            int value2 = node4 != null ? node4.height : 0;
            int value3;
            if ((value3 = value - value2) == -2) {
                LinkedTreeMap.Node node5 = node4.left;
                LinkedTreeMap.Node node6 = node4.right;
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
                LinkedTreeMap.Node node7 = node3.left;
                LinkedTreeMap.Node node8 = node3.right;
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

    private void rotateLeft(LinkedTreeMap.Node<K, V> node) {
        LinkedTreeMap.Node node2 = node.left;
        LinkedTreeMap.Node node3 = node.right;
        LinkedTreeMap.Node node4 = node.right.left;
        LinkedTreeMap.Node node5 = node3.right;
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

    private void rotateRight(LinkedTreeMap.Node<K, V> node) {
        LinkedTreeMap.Node node2 = node.left;
        LinkedTreeMap.Node node3 = node.right;
        LinkedTreeMap.Node node4 = node2.left;
        LinkedTreeMap.Node node5 = node2.right;
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
        LinkedTreeMap.EntrySet entrySet2 = this.entrySet;
        return this.entrySet != null ? entrySet2 : (this.entrySet = new LinkedTreeMap.EntrySet());
    }

    @Override
    public Set<K> keySet() {
        LinkedTreeMap.KeySet keySet2 = this.keySet;
        return this.keySet != null ? keySet2 : (this.keySet = new LinkedTreeMap.KeySet());
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap<>(this);
    }

    class EntrySet extends AbstractSet<Entry<K, V>> {

        @Override
        public int size() {
            return LinkedTreeMap.this.size;
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<Entry<K, V>>() {

                public Entry<K, V> next() {
                    return this.nextNode();
                }
            };
        }

        @Override
        public boolean contains(Object result) {
            return result instanceof Entry
                    && LinkedTreeMap.this.findByEntry((Entry<?, ?>) result) != null;
        }

        @Override
        public boolean remove(Object result) {
            if (!(result instanceof Entry)) {
                return false;
            }
            LinkedTreeMap.Node node;
            if ((node = LinkedTreeMap.this.findByEntry((Entry<?, ?>) result)) == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal(node, true);
            return true;
        }

        @Override
        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {

        @Override
        public int size() {
            return LinkedTreeMap.this.size;
        }

        @Override
        public Iterator<K> iterator() {
            return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<K>() {

                @Override
                public K next() {
                    return this.nextNode().key;
                }
            };
        }

        @Override
        public boolean contains(Object result) {
            return LinkedTreeMap.this.containsKey(result);
        }

        @Override
        public boolean remove(Object result) {
            return LinkedTreeMap.this.removeInternalByKey(result) != null;
        }

        @Override
        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {

        LinkedTreeMap.Node<K, V> next;

        LinkedTreeMap.Node<K, V> lastReturned;

        int expectedModCount;

        LinkedTreeMapIterator() {
            this.next = LinkedTreeMap.this.header.next;
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }

        @Override
        public final boolean hasNext() {
            return this.next != LinkedTreeMap.this.header;
        }

        final LinkedTreeMap.Node<K, V> nextNode() {
            LinkedTreeMap.Node node = this.next;
            if (this.next == LinkedTreeMap.this.header) {
                throw new NoSuchElementException();
            }
            if (LinkedTreeMap.this.modCount != this.expectedModCount) {
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
            LinkedTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }
    }

    static final class Node<K, V> implements Entry<K, V> {

        LinkedTreeMap.Node<K, V> parent;

        LinkedTreeMap.Node<K, V> left;

        LinkedTreeMap.Node<K, V> right;

        LinkedTreeMap.Node<K, V> next;

        LinkedTreeMap.Node<K, V> prev;

        final K key;

        V value;

        int height;

        Node() {
            this.key = null;
            this.next = this.prev = this;
        }

        Node(
                LinkedTreeMap.Node<K, V> node,
                K k,
                LinkedTreeMap.Node<K, V> node2,
                LinkedTreeMap.Node<K, V> node3) {
            this.parent = node;
            this.key = (K) k;
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

        public LinkedTreeMap.Node<K, V> first() {
            LinkedTreeMap.Node node = this;
            for (LinkedTreeMap.Node node2 = this.left; node2 != null; node2 = node2.left) {
                node = node2;
            }
            return node;
        }

        public LinkedTreeMap.Node<K, V> last() {
            LinkedTreeMap.Node node = this;
            for (LinkedTreeMap.Node node2 = this.right; node2 != null; node2 = node2.right) {
                node = node2;
            }
            return node;
        }
    }
}
