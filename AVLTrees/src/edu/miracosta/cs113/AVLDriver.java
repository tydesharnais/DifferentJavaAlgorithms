package edu.miracosta.cs113;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class AVLDriver {
    public static void main(String[] args) {
        Integer[] intArray = generateNumbers();
        BinarySearchTree<Integer> searchTree = new BinarySearchTree<>();
        AVLTree<Integer> avlTree = new AVLTree<>();

        for (Integer n : intArray){
            searchTree.add(n);
            avlTree.add(n);
        }

        System.out.println("Unbalanced: ");
        System.out.println("String Vision: ");
        System.out.println(searchTree.toString());
        System.out.println("Poly Vision: ");
        System.out.println(searchTree.toString2());
        System.out.println("-----------------");
        System.out.println("Balanced: ");
        System.out.println("String Vision: ");
        System.out.println(avlTree.toString());
        System.out.println("Poly Vision: ");
        System.out.println(avlTree.toString2());

    }

    public static Integer[] generateNumbers(){
        Random randGenerator = new Random();
        Set set = new HashSet<Integer>();

        while (set.size() < 25){
            set.add(randGenerator.nextInt(100));
        }

        return (Integer[]) set.toArray(new Integer[25]);
    }
}