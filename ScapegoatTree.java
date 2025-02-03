import java.util.ArrayList;
import java.util.Iterator;

/**
 *  The {@code ScapegoatTree} class represents an ordered map from keys to values.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>containsKey</em>,
 *  <em>size</em> and <em>isEmpty</em> methods.
 *  It does not support <em>remove</em>, however.
 *  It also supports <em>min</em> and <em>max</em> for finding the
 *  smallest and largest keys in the map.
 *  It also provides a <em>iterator</em> method for iterating over all of the keys.
 *  <p>
 *  This implementation uses a scapegoat tree. It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Nick Smallbone
 *  @author Christian Sattler
 *  @author You!
 */
public class ScapegoatTree<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    // How unbalanced the tree may become (must be at least 1).
    final double alpha = 2;

    // Returns the binary logarithm of a number.
    private static double log2(int n) {
        return Math.log(n) / Math.log(2);
    }

    // In addition to being a binary search tree,
    // a scapegoat tree satisfies the following invariant at every node:
    //   height <= alpha * log_2(size + 1).

    private Node root;             // root of BST
    private int rebuilds;          // number of times rebuild() was called, for statistics

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree
        private int height;        // height of subtree

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
            this.size = 1;
            this.height = 1;
        }
    }

    /**
     * Initializes an empty map.
     */
    public ScapegoatTree() {
    }

    /**
     * @return {@code true} if this map is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return the number of key-value pairs in this map
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.size;
    }

    /**
     * Does this map contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this map contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean containsKey(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to containsKey() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the map
     *         and {@code null} if the key is not in the map
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null)
            throw new IllegalArgumentException("calls get() with a null key");
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    /**
     * Inserts the specified key-value pair into the map, overwriting the old
     * value with the new value if the map already contains the specified key.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null)
            throw new IllegalArgumentException("calls put() with a null key");

        root = put(root, key, val);
        assert check();
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null)
            return new Node(key, val);

        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = put(node.left,  key, val);
        else if (cmp > 0)
            node.right = put(node.right, key, val);
        else
            node.val = val;

        node.size = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));

        if (node.height > (alpha * log2(node.size + 1))) {
            return rebuild(node);
        }
        return node;
    }

    // Rebuild a tree to make it perfectly balanced.
    // You do not need to change this method, but need to define 'inorder' and 'balanceNodes'.
    //
    // Important: this method *returns* the rebuilt tree!
    // So after you call this method, make sure to use the return value,
    // not the node that you passed in as a parameter.
    private Node rebuild(Node node) {
        rebuilds++; // update statistics

        ArrayList<Node> nodes = new ArrayList<Node>(size(node));
        inorder(node, nodes);
        return balanceNodes(nodes, 0, nodes.size() - 1);
    }

    // Perform an in-order traversal of the subtree rooted at 'node'.
    // The nodes encountered are stored in the ArrayList 'nodes'.
    private void inorder(Node node, ArrayList<Node> nodes) {
        if (node == null) {
            return;
        }
        inorder(node.left, nodes);
        nodes.add(node);
        inorder(node.right, nodes);
    }

    // Construct a balanced BST out of the nodes in `nodes` between `lo` and `hi`.
    // This range is inclusive on both sides, i.e., the subarray is nodes[lo]..nodes[hi].
    private Node balanceNodes(ArrayList<Node> nodes, int lo, int hi) {
        // Base case: empty subarray.
        if (lo > hi)
            return null;

        int mid = (lo + hi) / 2;

        Node leftChild = balanceNodes(nodes, lo, mid - 1);
        Node rightChild = balanceNodes(nodes, mid + 1, hi);

        Node root = nodes.get(mid);

        root.left = leftChild;
        root.right = rightChild;

        root.size = 1 + size(leftChild) + size(rightChild);
        root.height = 1 + Math.max(height(leftChild), height(rightChild));

        return root;
    }

    /**
     * Returns all keys in the map as an {@code Iterable}.
     * To iterate over all of the keys in the map named {@code st},
     * use the foreach notation: {@code for (Key key : st)}.
     *
     * @return all keys in the map
     */
    public Iterator<Key> iterator() {
        ArrayList<Key> queue = new ArrayList<Key>();
        keys(root, queue);
        return queue.iterator();
    }

    private void keys(Node x, ArrayList<Key> queue) {
        if (x == null)
            return;

        keys(x.left, queue);
        queue.add(x.key);
        keys(x.right, queue);
    }

    /**
     * Returns the height of the binary tree.
     *
     * @return the height of the binary tree (an empty tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null)
            return 0;
        return x.height;
    }

    /**
     * Returns the number of times the rebuild method has been called
     * (for debugging only).
     * @return the number of times the rebuild method has been called
     */
    public int rebuilds() {
        return rebuilds;
    }

    /**
     * Returns some statistics about the BST (for debugging).
     *
     * @return information about the BST.
     */
    public String statistics() {
        return String.format("scapegoat tree, size %d, height %d, rebuilds %d", size(), height(), rebuilds());
    }

    /*************************************************************************
    *  Check integrity of scapegoat data structure.
    *************************************************************************/
    private boolean check() {
        if (!isBST())              System.out.println("Not in symmetric order");
        if (!isSizeConsistent())   System.out.println("Subtree counts not consistent");
        if (!isHeightConsistent()) System.out.println("Heights not consistent");
        if (!isBalanced())         System.out.println("Not balanced");
        return isBST() && isSizeConsistent() && isHeightConsistent() && isBalanced();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null)
            return min == null || max == null || min.compareTo(max) < 0;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null)
            return true;
        return x.size == size(x.left) + size(x.right) + 1
            && isSizeConsistent(x.left)
            && isSizeConsistent(x.right);
    }

    // are the height fields correct?
    private boolean isHeightConsistent() {
        return isHeightConsistent(root);
    }

    private boolean isHeightConsistent(Node x) {
        if (x == null)
            return true;

        return x.height == 1 + Math.max(height(x.left), height(x.right))
            && isHeightConsistent(x.left)
            && isHeightConsistent(x.right);
    }

    // is the tree sufficiently balanced?
    private boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node x) {
        if (x == null)
            return true;
        return x.height <= alpha * log2(x.size + 1)
            && isBalanced(x.left)
            && isBalanced(x.right);
    }

}
