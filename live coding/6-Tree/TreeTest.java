/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.Stack;

/**
 *
 * @author solic
 */
public class TreeTest {
    public static void main(String[] args){
        BT myTree = new BT();
        
        System.out.println(myTree.height());//-1
        
        myTree.root = new BTNode("C");
        System.out.println(myTree.height());//0
        
        myTree.root.left = new BTNode("H");
        System.out.println(myTree.height());//
        
        myTree.root.left.left = new BTNode("I");
        myTree.root.left.right = new BTNode("F");
        myTree.root.right = new BTNode("F");
        myTree.root.right.right = new BTNode("O");
        myTree.root.right.right.left = new BTNode("N");
        
        myTree.printPreOrder();
        myTree.printInOrder();
        myTree.printPostOrder();
        myTree.printPostOrderWithStack();
    }
}

class BT{
    BTNode root;
    
    BT(){
        root = null;
    }
    
    int height(){
        return height(root);
    }
    
    int height(BTNode node){
        if (node == null) return -1;
        return Math.max(height(node.left) + 1, height(node.right) + 1);
    }
    
    void printPreOrder(){
        if(root != null)
            root.printPreOrder();
        System.out.println("");
    }
    
    void printInOrder(){
        if(root != null)
            root.printInOrder();
        System.out.println("");
    }
    
    void printPostOrder(){
        if(root != null)
            root.printPostOrder();
        System.out.println("");
    }
    
    void printPostOrderWithStack(){
        Stack myStack = new Stack();
        
        if(root!= null)
            myStack.add(new Pair(root, 0));
        else
            return;
        
        while(myStack.isEmpty() == false){
            Pair now = (Pair)myStack.pop();
            
            switch (now.status){
                case 0:
                    now.status = 1;
                    myStack.add(now);
                    if(now.node.left != null)
                        myStack.add(new Pair(now.node.left, 0));
                    break;
                case 1:
                    now.status = 2;
                    myStack.add(now);
                    if(now.node.right != null)
                        myStack.add(new Pair(now.node.right, 0));
                    break;
                case 2:
                    System.out.print(now.node.data);
            }
        }
        System.out.println("");
    }
}

class Pair{
    BTNode node;
    int status;
    
    Pair(BTNode node, int status){
        this.node = node;
        this.status = status;
    }
}

class BTNode{
    String data;
    BTNode left;
    BTNode right;
    
    BTNode(String data){
        this.data = data;
    }
    
    void printPreOrder(){
        System.out.print(data);// node yang sekarang
        
        //subtree kiri dulu
        if(left != null) left.printPreOrder();
        
        //subtree kanan
        if(right != null) right.printPreOrder();
    }
    
    void printInOrder(){
        //subtree kiri dulu
        if(left != null) left.printInOrder();
        
        System.out.print(data);// node yang sekarang
        
        //subtree kanan
        if(right != null) right.printInOrder();
    }
    
    void printPostOrder(){
        //subtree kiri dulu
        if(left != null) left.printPostOrder();
        
        //subtree kanan
        if(right != null) right.printPostOrder();
        
        System.out.print(data);// node yang sekarang
    }
}
