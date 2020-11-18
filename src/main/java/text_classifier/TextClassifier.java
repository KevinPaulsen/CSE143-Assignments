package text_classifier;

public class TextClassifier {

    private final Vectorizer vectorizer;
    private final Splitter splitter;

    public TextClassifier(Vectorizer vectorizer, Splitter splitter) {
        this.vectorizer = vectorizer;
        this.splitter = splitter;
    }

    /**
     * Returns a boolean representing the predicted label for the given
     * text by recursively traversing the tree.
     * @param text
     * @return
     */
    public boolean classify(String text) {
        return false;
    }

    /**
     * Prints a Java code representation of this decision tree in if/else
     * statement format without braces and with 1 additional indentation
     * space per level in the decision tree. Leaf nodes should print
     * “return true;” or “return false;” depending on the label value.
     */
    public void print() {

    }

    /**
     * Prunes this tree to the given depth. Each pruned subtree is replaced
     * with a new node representing the subtree’s majority label. For example,
     * pruning the above decision tree to depth 1 would result in the following structure.
     * @param depth
     */
    public void prune(int depth) {

    }


    // An internal node or a leaf node in the decision tree.
    private static class Node {
        public Split split;
        public boolean label;
        public Node left;
        public Node right;

        // Constructs a new leaf node with the given label.
        public Node(boolean label) {
            this(null, label, null, null);
        }

        // Constructs a new internal node with the given split, label, and left and right nodes.
        public Node(Split split, boolean label, Node left, Node right) {
            this.split = split;
            this.label = label;
            this.left = left;
            this.right = right;
        }

        // Returns true if and only if this node is a leaf node.
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }
}
