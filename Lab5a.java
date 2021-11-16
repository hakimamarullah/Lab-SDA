import java.io.*;
import java.util.*;

public class Lab5a {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    public static AVL listHarga = new AVL();

    public static void main(String[] args) {
       
        //Menginisialisasi kotak sebanyak N
        int N = in.nextInt();
        for(int i = 0; i < N; i++){
            String nama = in.next();
            int harga = in.nextInt();
            int tipe = in.nextInt();
            handleStock(nama, harga, tipe);
        }

        //Query 
        //(method dan argumennya boleh diatur sendiri, sesuai kebutuhan)
        int NQ = in.nextInt();
        for(int i = 0; i < NQ; i++){
            String Q = in.next();
            if (Q.equals("BELI")){
                int L = in.nextInt();
                int R = in.nextInt();
                out.println(handleBeli(L, R));

            }else if(Q.equals("STOCK")){
                String nama = in.next();
                int harga = in.nextInt();
                int tipe = in.nextInt();
                handleStock(nama, harga, tipe);

            }else{ //SOLD_OUT
                String nama = in.next();
                handleSoldOut(nama);

            }
        }

        out.flush();
    }

    //TODO
    static String handleBeli(int L, int R){
        ArrayList<Integer> tmp = listHarga.greaterThan(L,R);
        if(tmp.size() <= 1){
            //System.out.println(tmp);
            return "-1 -1";
        }
        else{
            Collections.sort(tmp);
            //System.out.println(tmp);
            return tmp.get(0) + " " + tmp.get(tmp.size() -1 );
        }
    }

    //TODO
    static void handleStock(String nama, int harga, int tipe){
        listHarga.insert(harga);
    }

    //TODO
    static void handleSoldOut(String nama){

    }


    // taken from https://codeforces.com/submissions/Petr
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
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
                if(Math.abs(height(current.left) - height(current.right)) == 2 ){
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
                if(Math.abs(height(current.left) - height(current.right)) == 2 ){
                    //balancing, tapi tentukan dulu case-nya
                    //case RR
                    if (X > current.right.data)
                        current = singleRotateWithRightChild(current);
                    else//case RL
                        current = doubleRotateWithRightChild(current);
                }
            
            }
            // else
            //     System.out.println( X + " sudah ada.");
        }
        return current;
    }
    
    int height(Node node){
        if(node == null) return -1;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    ArrayList<Integer> greaterThan(Integer x, Integer y){
        ArrayList<Integer> out = new ArrayList<Integer>();
        greaterThan(x, y, out);
        return out;
    }
    void greaterThan(Integer x, Integer y, ArrayList<Integer> out){
        greaterThan(x, y, out, root);
    }

    void greaterThan(Integer x, Integer y, ArrayList<Integer> out, Node curr){
        if(curr== null){
            return;
        }
        if(curr.data >= x && curr.data <= y){
            out.add(curr.data);
            if(curr.left != null){
                if(curr.left.data >= x && curr.left.data <= y){
                    curr.left.traverse2(out, x,y);
                }
            }
            if(curr.right != null)
                curr.right.traverse(out);
            //greaterThan(x, out, curr.left.right);
        }
        else if(curr.data < x){
            curr = search(curr, x);
            if(curr != null){
                out.add(curr.data);
                if(curr.left != null){
                    if(curr.data >= x && curr.data <= y){
                        curr.left.traverse2(out, x, y);
                    }
                }
                if(curr.right != null)
                    curr.right.traverse(out);
            }
        }
    }
    //https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
    public Node search(Node root, int data){
    // Base Cases: root is null or data is present at root
    if (root==null || root.data==data)
        return root;
 
    // Key is greater than root's data
    if (root.data < data)
       return search(root.right, data);
 
    // Key is smaller than root's data
    return search(root.left, data);
}
  

    
    
    //untuk kasus LL
    Node singleRotateWithLeftChild(Node k2){
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        
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
    void traverse(ArrayList<Integer> out){
        if(left != null){
            left.traverse(out);
        }
         out.add(data);
         if(right != null)
            right.traverse(out);
    }

    void traverse2(ArrayList<Integer> out, Integer x, Integer y){
        if(left != null){
            if(right.data >= x && right.data <= y)
            left.traverse(out);
        }
        out.add(data);
         if(right != null){
            if(right.data >= x && right.data <= y)
            right.traverse(out);
        }
    }
}