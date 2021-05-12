package edu.miracosta.cs113;

import java.util.Scanner;

public class BinaryTree<E> {

    protected Node<E> root;

    public BinaryTree(){
        root = null;
    }

    /**
     * Constructor creates a tree with given node at root.
     * @param root Node object
     */
    protected BinaryTree(Node<E> root){
        this.root = root;
    }

    /**
     * Constructor builds a tree from a data value and two trees.
     * @param data E data type
     * @param leftTree BinaryTree that will be fixed on the left
     * @param rightTree BinaryTree that will be fixed on the right
     */
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree){
        root = new Node<E>(data);
        if(leftTree != null){
            root.left = leftTree.root;
        }
        else{
            root.left = null;
        }
        if(rightTree != null){
            root.right = rightTree.root;
        }
        else{
            root.right = null;
        }
    }

    /**
     * Accessor method retrieves the left subtree by
     * making the left node of the root into a BinaryTree.
     * @return the left node as a BinaryTree or null if it doesn't have a left
     */
    public BinaryTree<E> getLeftSubtree(){
        if(root != null && root.left != null){
            return new BinaryTree<E>(root.left);
        }
        else{
            return null;
        }
    }

    /**
     *
     * @return
     */
    public BinaryTree<E> getRightSubtree(){
        if(root != null && root.right != null){
            return new BinaryTree<E>(root.right);
        }
        else{
            return null;
        }
    }

    public E getData(){
        return root.data;
    }

    public boolean isLeaf(){
        return root.left == null && root.right == null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, 1, sb);
        return sb.toString();
    }

    private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb){
        for(int i = 1; i < depth; i++){
            sb.append(" ");
        }
        if(node == null){
            sb.append("null\n");
        }
        else{
            sb.append(node.toString() + "\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }

    public static BinaryTree<String> readBinaryTree(Scanner scan){
        String data = scan.next();
        if(data.equals("null")){
            return null;
        }
        else{
            BinaryTree<String> leftTree = readBinaryTree(scan);
            BinaryTree<String> rightTree = readBinaryTree(scan);
            return new BinaryTree<>(data, leftTree, rightTree);
        }
    }


    //Inner Node class
    protected static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        public Node(E data){
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public String toString(){
            return data.toString();
        }
    }
}

