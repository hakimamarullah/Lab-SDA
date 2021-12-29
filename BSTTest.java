public class BSTTest {
    public static void main(String[] args){
        BST myBST = new BST();
        
        myBST.insert(10);
        myBST.insert(5);
        myBST.insert(2);
        myBST.insert(1);

        myBST.cst();
        
        
    }
}

class BST{
    Node root;
    
    void insert(Integer data){
        root = insert(root, data);
// perlu assign ke root, karena kalau root tadinya masih null, root tidak akan pernah ke-update
//kalau root tadinya sudah tidak null, ga masalah di-assign apa tidak karena current juga sudah bisa merefer ke root asli
//(hence bisa memodif juga keseluruhan tree, seperti current pada linked list)
//kalau mau lebih rapi, bikin if else, jika root null maka pake assignment, jika tidak null tinggal panggil aja ga harus assign
    }
    void cst(){
        cst(root);
    }

    void cst(Node n){
        if(n.left != null){
            cst(n.left);
            
        }
        System.out.print(n.data + ", ");
    }
    
    //mereturn Node current yang sudah di-update subtree-nya
    Node insert(Node current, Integer X){
        if(current == null)
            current = new Node(X);
        else{ 
            if(X < current.data)
                current.left = insert(current.left, X);
            else if(X > current.data)
                current.right = insert(current.right,X);
            else
                System.out.println( X + " sudah ada.");
        }
        return current;
    }
    
    void printInOrder(){
        if(root != null)
            root.printInOrder();
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
}