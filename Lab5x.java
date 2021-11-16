import java.io.*;
import java.util.*;

public class Lab5x {
    private static InputReader in = new InputReader(System.in);
    public static PrintWriter out = new PrintWriter(System.out);
    public static Map<String, Box> boxMap = new HashMap<String, Box>();
    public static LinkedBox boxList = new LinkedBox();

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
        boxList.sort();
        Box[] arrayBox = boxList.toArray();
        //boxList.sketsa();
        System.out.println(Arrays.toString(arrayBox));
        int indexMin = lowerIndex(arrayBox, L);
        int indexMax = upperIndex(arrayBox, R);
        //System.out.println(indexMin + " " + indexMax);
        // boxList.sort();
        // boxList.sketsa();
        return "";
        //return indexMin + " " + indexMax;
    }

    //TODO
    static void handleStock(String nama, int harga, int tipe){
        boxList.addLast(nama, harga, tipe);
        boxMap.put(nama, boxList.header.next);

    }

    //TODO
    static void handleSoldOut(String nama){
        boxList.remove(boxMap.get(nama));

    }

    static int lowerIndex(Box[] arr, int x){
        int l = 0, h = arr.length - 1;
        while (l <= h)
        {
            int mid = (l + h) / 2;
            if (arr[mid].getHarga() >= x){
                h = mid - 1;
            }
            else
                l = mid + 1;
        }
        return l;
    }

    //https://www.geeksforgeeks.org/queries-counts-array-elements-values-given-range/ 
    // function to find last index <= y
    static int upperIndex(Box[] array, int y){
        System.out.print(" DEBUG ");
        int l = 0, h = array.length - 1;
        while (l <= h)
        {
            int mid = (l + h) / 2;
            if (array[mid].getHarga() <= y){
                System.out.print(array[mid].getHarga() + " ");
                l = mid + 1;
            }
            else
                h = mid - 1;
        }
        System.out.println();
        return h;
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

class Box {
    Box next;
    Box prev;
    int harga;
    int tipe;
    String nama;

    public Box(String nama, int harga, int tipe){
        this.harga = harga;
        this.tipe = tipe;
        this.nama = nama;
    }

    public Box(String nama, int harga, int tipe, Box next){
        this.harga = harga;
        this.tipe = tipe;
        this.next = next;
        this.nama = nama;
    }

    public int getTipe(){
        return this.tipe;
    }

    public int getHarga(){
        return this.harga;
    }
    public String getNama(){
        return this.nama;
    }
    @Override
    public String toString(){
        
        return Integer.toString(this.harga);
    }

}


// TODO - class untuk LinkedBox
class LinkedBox extends Lab5x{
    Box header;
    Box last;
    int size;

    public LinkedBox() {
        this.header = new Box(null, -1, -1, null);
        this.header.prev = null;
        this.last = header;

    }

    public void addLast(String nama, int harga, int tipe){
        if(header.next == null){
            header.next = new Box(nama, harga, tipe);
            header.next.prev = header;
            this.size++;
        }
        else{
            Box box = new Box(nama, harga, tipe, header.next);
            box.prev = header;
            header.next.prev = box;
            header.next = box;
            
            //last.next = box ;
            //box.prev = last;
            //last = box;
            this.size++;
            
        }
    }

    public void remove(Box box){
        if(box.next == null){
            box.prev.next = null;

        }
        else if (box.next != null) {
            box.next.prev = box.prev;
            box.prev.next = box.next;
           
        }
        this.size--;
        
    }

    public void sketsa(){
        // TODO - handle SKETSA
        Box current = header.next;
        while(current != null){
            Lab5x.out.print(current.getHarga() + " ");
            current = current.next;
        }
        Lab5x.out.println();
    }

    public Box[] toArray(){
        Box[] array = new Box[this.size];
        Box curr = this.header.next;
        int counter =0;
        while(curr != null){
            array[counter] = curr;
            curr = curr.next;
            counter++;
        }
        return array;
    }

    public void sort(){
        Box temp = mergeSort(this.header.next);
        this.header.next = temp;
    }

    Box split(Box head) {
        Box fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        Box temp = slow.next;
        slow.next = null;
        return temp;
    }
 
    Box mergeSort(Box box) {
        if (box == null || box.next == null) {
            return box;
        }
        Box second = split(box);
 
        // Recur for left and right halves
        box = mergeSort(box);
        second = mergeSort(second);
 
        // Merge the two sorted halves
        return merge(box, second);
    }
 
    // Function to merge two linked lists
    Box merge(Box first, Box second) {
        // If first linked list is empty
        if (first == null) {
            return second;
        }
 
        // If second linked list is empty
        if (second == null) {
            return first;
        }
 
        // Pick the smaller value
        if (first.harga < second.harga) {
            first.next = merge(first.next, second);
            first.next.prev = first;
            first.prev = null;
            return first;
        } else {
            second.next = merge(first, second.next);
            second.next.prev = second;
            second.prev = null;
            return second;
        }
    }

}