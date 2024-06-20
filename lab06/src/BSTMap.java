import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    Node root;
    int size;

    BSTMap(Node r) {
        root = r;
    }

    BSTMap() {
        root = null;
    }

    public class Node {
        K key;
        V value;
        Node parent;
        Node left = null, right = null;
        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node putHelper(K key, V value, Node n) {

        if (n == null) {
            return new Node(key, value);
        }
        else if (key.equals(n.key)) {
            n.value = value;
        }
        else if (n.key.compareTo(key) > 0) {
            n.left = putHelper(key, value,n.left);
            n.left.parent = n;
        }
        else if (n.key.compareTo(key) < 0) {
            n.right = putHelper(key, value,n.right);
            n.right.parent = n;
        }
        return n;
    }

    @Override
    public void put(K key, V value) {

        if (this.containsKey(key)) {
            size--;
        }
        size++;
        root = putHelper(key, value, root);
    }


    @Override
    public V get(K key) {
        Node n = this.getNode(key);
        if (n == null) {
            return null;
        }
        else return n.value;
    }

    private Node getNode(K key) {
        if (root == null) {
            return null;
        }
        if (root.key.equals(key)) {
            return root;
        }
        else if (root.key.compareTo(key) > 0) {
            BSTMap<K, V> subMap = new BSTMap<>(root.left);
            return subMap.getNode(key);
        }
        else if (root.key.compareTo(key) < 0) {
            BSTMap<K, V> subMap = new BSTMap<>(root.right);
            return subMap.getNode(key);
        }
        return root;
    }

    @Override
    public boolean containsKey(K key) {
        return this.getNode(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }


    @Override
    public Set<K> keySet() {
        if (root == null) {
            return null;
        }

        Set<K> set = new HashSet<>();
        set.add(root.key);
        BSTMap<K, V> leftMap = new BSTMap<>(root.left);
        BSTMap<K, V> rightMap = new BSTMap<>(root.right);
        if (leftMap.root != null)
            set.addAll(leftMap.keySet());
        if (rightMap.root != null)
            set.addAll(rightMap.keySet());

        return set;
    }

    private Node findSuccessor(Node n) {
        if (n == null) {
            return null;
        }
        else if (n.right == null) {
            return n;
        }
        else {
            n = n.right;
            while (n.left != null) {
                n = n.left;
            }
            return n;
        }
    }


    @Override
    public V remove(K key) {

        Node n = this.getNode(key);
        V value = this.get(key);

        if (n == null) {
            return null;
        }

        else if (n.left == null || n.right == null) {
            Node notNull;
            if (n.left != null) {
                notNull = n.left;
            }
            else {
                notNull = n.right;
            }
            if (n.parent == null) {
                root = notNull;
            }
            else if (n.parent.left == n) {
                n.parent.left = notNull;
            }
            else if (n.parent.right == n) {
                n.parent.right = notNull;
            }
            size--;
        }
        else {
            Node successor = this.findSuccessor(root);
            n.key = successor.key;
            n.value = successor.value;
            successor.key = key;
            remove(key);
        }
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
