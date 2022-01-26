/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree.live.heightvar;

/**
 *
 * @author solic
 */
public class AVlTest {
    public static void main(String[] args){
        AVL myAVL = new AVL();
        
        System.out.println("insert 10:");
        myAVL.insert(10);
        myAVL.printInOrder();
        myAVL.printPreOrder();
        
        System.out.println("\ninsert 5:");
        myAVL.insert(5);
        myAVL.printInOrder();
        myAVL.printPreOrder();
        
        System.out.println("\ninsert 7:");
        myAVL.insert(7);
        myAVL.printInOrder();
        myAVL.printPreOrder();
        
        System.out.println("\ninsert 12:");
        myAVL.insert(12);
        myAVL.printInOrder();
        myAVL.printPreOrder();
        
        System.out.println("\ninsert 11:");
        myAVL.insert(11);
        myAVL.printInOrder();
        myAVL.printPreOrder();
        //System.out.println(myAVL.findMin());
        //myAVL.remove(10);
        //myAVL.printInOrder();
    }
}

class AVL{
    Node root;
    
    void insert(Integer data){
        root = insert(root, data);
// perlu assign ke root, karena kalau root tadinya masih null, root tidak akan pernah ke-update
//kalau root tadinya sudah tidak null, ga masalah di-assign apa tidak karena current juga sudah bisa merefer ke root asli
//(hence bisa memodif juga keseluruhan tree, seperti current pada linked list)
//kalau mau lebih rapi, bikin if else, jika root null maka pake assignment, jika tidak null tinggal panggil aja ga harus assign
    }
    
    //mereturn Node current yang sudah di-update subtree-nya
    Node insert(Node current, Integer X){
        if(current == null)
            current = new Node(X);
        else{ 
            if(X < current.data){
                current.left = insert(current.left, X);
                
                //cek tinggi subtree kiri dan kanan
                int left_height = current.left == null ? -1 : current.left.height;
                int right_height = current.right == null ? -1 : current.right.height;
                
                if(Math.abs(left_height - right_height) == 2 ){
                    //balancing, tapi tentukan dulu case-nya
                    //case LL
                    if (X < current.left.data)
                        current = singleRotateWithLeftChild(current);
                    else//case LR
                        current = doubleRotateWithLeftChild(current);
                }
            }
            else if(X > current.data){
                current.right = insert(current.right,X);
                
                //cek tinggi subtree kiri dan kanan
                int left_height = current.left == null ? -1 : current.left.height;
                int right_height = current.right == null ? -1 : current.right.height;
                
                if(Math.abs(left_height - right_height) == 2 ){
                    //balancing, tapi tentukan dulu case-nya
                    //case RR
                    if (X > current.right.data)
                        current = singleRotateWithRightChild(current);
                    else//case RL
                        current = doubleRotateWithRightChild(current);
                }
            
            }
            else
                System.out.println( X + " sudah ada.");
        }
        //update tinggi current
        int left_height = current.left == null ? -1 : current.left.height;
        int right_height = current.right == null ? -1 : current.right.height;
        current.height = Math.max(left_height, right_height) + 1;
        
        return current;
    }
    
    //untuk kasus LL
    Node singleRotateWithLeftChild(Node k2){
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        
        //update tinggi k2
        int left_height = k2.left == null ? -1 : k2.left.height;
        int right_height = k2.right == null ? -1 : k2.right.height;
        k2.height = Math.max(left_height, right_height) + 1;
        
        //update tinggi k1
        left_height = k1.left == null ? -1 : k1.left.height;
        right_height = k1.right == null ? -1 : k1.right.height;
        k1.height = Math.max(left_height, right_height) + 1;
        
        return k1;
    }
    
    //untuk kasus LR
    Node doubleRotateWithLeftChild(Node k3){
        //rotasi 1
        k3.left = singleRotateWithRightChild(k3.left);
                
        //rotasi 2
        return singleRotateWithLeftChild(k3);
    }
    
    //untuk kasus RR
    Node singleRotateWithRightChild(Node k1){
        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        
        //update tinggi k2
        int left_height = k2.left == null ? -1 : k2.left.height;
        int right_height = k2.right == null ? -1 : k2.right.height;
        k2.height = Math.max(left_height, right_height) + 1;
        
        //update tinggi k1
        left_height = k1.left == null ? -1 : k1.left.height;
        right_height = k1.right == null ? -1 : k1.right.height;
        k1.height = Math.max(left_height, right_height) + 1;
        
        return k2;
    }
    
    //untuk kasus RL
    Node doubleRotateWithRightChild(Node k1){
        //rotasi 1
        k1.right = singleRotateWithLeftChild(k1.right);
        
        //rotasi 2
        return singleRotateWithRightChild(k1);
    }
    
    void printInOrder(){
        if(root != null)
            root.printInOrder();
        System.out.println("");
    }
    
    void printPreOrder(){
        if(root != null)
            root.printPreOrder();
        System.out.println("");
    }
    
    Integer findMin(){
        return findMin(root);
    }
    
    Integer findMin(Node current){
        if(current == null)
            return null;
        
        while(current.left != null)
            current = current.left;
        
        return current.data;
    }
    
    //tidak perlu di-assign ke root, karena kalaupun root null, ga ngaruh. 
    //dan kalau root tidak null, current juga sudah bisa merefer ke root asli
    //(hence bisa memodif juga keseluruhan tree, seperti current pada linked list)
    void removeMin(){
        removeMin(root);
    }
    
    //me-return Node current yang sudah ter-update subtreenya
    Node removeMin(Node current){
        if(current == null)
            return null;
        
        //masih ada left-nya, berarti current bukan yang minimum, cari lagi ke kiri
        if(current.left != null){
            current.left = removeMin(current.left);
            return current;
        }
        //sudah ga ada left-nya, jadi current adalah yang minimum,
        //tapi bisa jadi dia punya subtree kanan, jadi langsung balikin aja subtree kanannya menggantikan current (by-pass current)
        else
            return current.right;
    }
    
    void remove(Integer X){
        remove (root, X);
    }
    
    //successor in order
    Node remove(Node current, Integer X){
        if(current == null)
            return null;
        
        if(X < current.data)
            current.left = remove(current.left, X);
        else if(X > current.data)
            current.right = remove(current.right, X);
        else if(current.left != null && current.right != null){//case 2 dulu
            //successor in-order
            current.data = findMin(current.right);//copy angkanya aja, dari subtree kanan ke node current
            current.right = removeMin(current.right);//remove node nya yang di subtree kanan
        }
        else//case 0 atau case 1
            current = current.right != null ? current.right : current.left;
    
        return current;
    }
    
}

class Node{
    Integer data;
    Node left;
    Node right;
    int height;
    
    Node(Integer data){
        this.data = data;
    }
    
    void printInOrder(){
        if(left != null)
            left.printInOrder();
        
        System.out.print(data + " ");
        
        if(right != null)
            right.printInOrder();
    }
    
    void printPreOrder(){
        System.out.print(data + " ");
        
        if(left != null)
            left.printPreOrder();
        
        if(right != null)
            right.printPreOrder();
    }
}
