package ed.lab;

import java.util.Comparator;

public class E02AVLTree<T> {

    private final Comparator<T> comparator;

    private Node<T> node;
    private int size;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.node = null;
        this.size = 0;
    }

    public T minimo(Node<T> nodo) {
        if (nodo == null) {
            return null;
        }

        Node<T> actual = nodo;
        while (actual.left != null) {
            actual = actual.left;
        }
        return actual.value;
    }


    public void delete(T value) {
        this.node = delete(this.node, value);
    }


    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        int compare = comparator.compare(value, node.value);

        if (compare < 0) {
            node.left = delete(node.left, value);
        }
        else if (compare > 0) {
            node.right = delete(node.right, value);
        }
        else {
            if (node.left == null) {
                this.size--;
                return node.right;
            }
            if (node.right == null) {
                this.size--;
                return node.left;
            }

            T min = minimo(node.right);
            node.value = min;
            node.right = delete(node.right, min);
        }

        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

            int balance = getBalance(node);

            if (balance < -1 && getBalance(node.left) <= 0) {
                return rotateRight(node);
            }

            if (balance < -1 && getBalance(node.left) > 0) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }

            if (balance > 1 && getBalance(node.right) >= 0) {
                return rotateLeft(node);
            }

            if (balance > 1 && getBalance(node.right) < 0) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    public T search(T value) {
        Node<T> root = search(this.node, value);

        if (root == null) {
            return null;
        }

        return root.value;
    }

    public int height() {
        if (this.node == null)
            return 0;
        return this.node.height;
    }

    public int size() {
        return this.size;
    }

    private Node<T> search(Node<T> root, T value) {
        if (root == null) {
            return null;
        }

        int compare = comparator.compare(value, root.value);

        if (compare == 0) {
            return root;
        }

        if (compare < 0) {
            return search(root.left, value);
        }

        return search(root.right, value);
    }
    public void insert(T value) {
        this.node = insert(node, value);
    }

    private Node<T> insert(Node<T> root, T value) {
        if (root == null) {
            this.size++;
            return new Node<>(value);
        }

        int compare = comparator.compare(value, root.value);

        if (compare < 0) {
            root.left = insert(root.left, value);
        }
        else if (compare > 0) {
            root.right = insert(root.right, value);
        } else {
            return root;
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));

        int balance = getBalance(root);

        if (balance < -1 && comparator.compare(value, root.left.value) > 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance > 1 && comparator.compare(value, root.right.value) < 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        if (balance < -1) {
            return rotateRight(root);
        }

        if (balance > 1) {
            return rotateLeft(root);
        }

        return root;
    }

    private int getBalance(Node<T> root) {
        if (root == null)
            return 0;

        return getHeight(root.right) - getHeight(root.left);
    }

    private Node<T> rotateLeft(Node<T> root) {
        if (root == null) return null;

        Node<T> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node<T> rotateRight(Node<T> root) {
        if (root == null) return null;

        Node<T> newRoot = root.left; //nuevo root
        root.left = newRoot.right;  // los hijos derechos del sucesor pasan al predecesor
        newRoot.right = root;       // el sucesor se vuelve padre del predecesor

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private void updateHeight(Node<T> root) {
        if (root == null) return;

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }

    private int getHeight(Node<T> root) {
        if (root == null)
            return 0;

        return root.height;
    }

    private static class Node<T> {
        protected T value;
        protected Node<T> left;
        protected Node<T> right;
        protected int height;

        public Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }
}
