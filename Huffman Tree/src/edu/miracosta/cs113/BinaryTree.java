package edu.miracosta.cs113;

import java.io.Serializable;
import java.util.Scanner;


public class BinaryTree<E> implements Serializable {

    protected Node<E> root;

    public BinaryTree() {
        root = null;
    }
    protected BinaryTree(Node<E> root) {
        this.root = root;
    }
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        // Set root to a new Node with the given data
        root = new Node<E>(data);

        // Set left subtree
        if (leftTree != null) {
            root.left = leftTree.root;
        }
        else {
            root.left = null;
        }

        // Set right subtree
        if (rightTree != null) {
            root.right = rightTree.root;
        }
        else {
            root.right = null;
        }
    }
    public BinaryTree<E> getLeftSubtree() {
        if (root != null && root.left != null) {
            return new BinaryTree<E>(root.left);
        }
        else {
            return null;
        }
    }
    public BinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            return new BinaryTree<E>(root.right);
        }
        else {
            return null;
        }
    }
    public boolean isLeaf() {
        return (root.left == null && root.right == null);
    }
    public E getData() {
        return root.data;
    }

    public static BinaryTree<String> readBinaryTree(Scanner scan) {
        // Read a line and trim leading and trailing spaces.
        if (!scan.hasNext()) {
            return null;
        }
        else {
            String data = scan.next();

            if (data.equals("null")) {
                return null;
            }
            else {
                BinaryTree<String> leftTree = readBinaryTree(scan);
                BinaryTree<String> rightTree = readBinaryTree(scan);

                return new BinaryTree<String>(data, leftTree, rightTree);
            }
        }
    }
    private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append(" ");
        }
        if (node == null) {
            sb.append("null\n");
        }
        else {
            sb.append(node.toString());
            sb.append("\n");

            preOrderTraverse(node.left, depth+1, sb);
            preOrderTraverse(node.right, depth+1, sb);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, 1, sb);
        return sb.toString();
    }

    protected static class Node<E> implements Serializable {
        /** The constituent data for this Node. */
        protected E data;
        /** The Node's left subtree. */
        protected Node<E> left;
        /** The Node's right subtree. */
        protected Node<E> right;

        public Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }

    } // End of class Node

} // End of class BinaryTree