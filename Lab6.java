import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab6 {
    private static InputReader in;
    public static OutputStream outputStream = System.out;
    public static PrintWriter out = new PrintWriter(outputStream);
    
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);

        ArrayList<Dataran> dataran = new ArrayList<Dataran>();
        VectorHeap heap = new VectorHeap();
        
        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int height = in.nextInt();
            addNewDataran(height, dataran, heap);
        }

        int Q = in.nextInt();
        while(Q-- > 0) {
            String query = in.next();
            if (query.equals("A")) {
                // TODO: Handle query A
                int newHeight = in.nextInt();
                addNewDataran(newHeight, dataran, heap);
            } else if (query.equals("U")) {
                // TODO: Handle query U
                int index = in.nextInt();
                int newHeight = in.nextInt();
                alterHeight(dataran.get(index), heap, newHeight);
            } else {
                // TODO: Handle query R
                heap.doR();
            }
        }
        //  for(Dataran x: heap.toArray()){
        //     System.out.print(x.getHeight() + " " + x.index + ", ");
        //     //System.out.print(x.getHeight() + " ");
        // }
        // System.out.println();

        out.flush();
    }

    public static void addNewDataran(int height, ArrayList<Dataran> dataran, VectorHeap heap){
        if(dataran.size() ==0){
            Dataran baru = new Dataran(height, null);
            dataran.add(baru);
            baru.index = dataran.size() - 1;
            heap.insert(baru);
        }
        else{
            Dataran baru = new Dataran(height, null);
            dataran.get(dataran.size()-1).next = baru;
            baru.prev = dataran.get(dataran.size()-1);
            dataran.add(baru);
            baru.index = dataran.size() - 1;
            heap.insert(baru);
        }
    }

    public static void alterHeight(Dataran dataran, VectorHeap heap, int newHeight){
        heap.changePriority(newHeight, dataran.heapIndex);
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
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

class Dataran extends Lab6 implements Comparable<Dataran>{
    Dataran next;
    Dataran prev;
    int height;
    int index;
    int heapIndex;

    public Dataran(int height){
        this.height = height;
    }
    public Dataran(int height, Dataran next){
        this.height =height;
        this.next = next;
    }

    public int getHeight(){
        return this.height;
    }

    public static Dataran findMax(Dataran a, Dataran b, Dataran c){
        if (a.getHeight() >= b.getHeight() && a.getHeight() >= c.getHeight())
             return a;
        else if ( b.getHeight() >= a.getHeight() && b.getHeight() >= c.getHeight())
             return b;
        else
             return c;
    }

    public static Dataran findMax(Dataran a, Dataran b){
        if (a.getHeight() >= b.getHeight())
             return a;
        else
            return b;

    }
    public void setHeight(int height){
        this.height = height;
    }

    

    public static void print(Dataran head){
        Dataran curr = head;
        while(curr != null){
            Lab6.out.print(curr.getHeight() + " ");
            curr = curr.next;
        }
        Lab6.out.println();
    }

    @Override
    public String toString(){
        return Integer.toString(this.height);
    }
    @Override
    public int compareTo(Dataran obj){
        if(this.height > obj.height){
            return 1;
        }
        else if(this.height < obj.height){
            return -1;
        }
        else if(this.height == obj.height){
            if(this.index > obj.index){
                return 1;
            }
            else
                return -1;
        }
        return 0;
    }

}

class VectorHeap{
    Vector<Dataran> storage;

    public VectorHeap(){
        storage = new Vector<Dataran>();
    }

    public void insert(Dataran dataran){
        storage.addElement(dataran);

        percolateUp(storage.size()-1);
    }

   private void percolateUp(int leaf){
            int parent = parentOf(leaf);
            
            //value yang tadi di leaf, kita simpan dulu ke temp
            Dataran value = storage.get(leaf);
            int now = leaf;
            
            //traverse dari leaf ke atas, maksimal nanti sampai root
            while(now > 0 && storage.get(parent).compareTo(value) == 1){
                //data di parent digeser ke bawah
                storage.setElementAt(storage.get(parent), now);
                storage.get(parent).heapIndex = now;
                now = parent;
                parent = parentOf(now);
            }
            
            //sudah ketemu posisinya
            storage.setElementAt(value, now);
            value.heapIndex = now;
        }
    private void percolateDown(int root){
            //simpan si root yang sekarang di temp
            Dataran value = storage.get(root);
            
            //simpan heap size supaya mudah saat pengecekan berikutnya
            int heapSize = storage.size();
            
            int now = root;// now ini seperti pointer current
            while(now < heapSize){
                //mesti cari jalur percolate down nya, mana yang lebih kecil
                //dari anak kiri atau anak kanan
                
                int leastChildPos = left(now);
                if(leastChildPos < heapSize){
                    //cari anak kanan
                    int rightChildPos = leastChildPos + 1;
                    if(rightChildPos < heapSize && storage.get(rightChildPos).compareTo(storage.get(leastChildPos)) == -1)
                        leastChildPos = rightChildPos;
                    
                    //di sini, kita sudah tau jalur percolate down nya ke kiri ataukah ke kanan, yaitu ke leastChildPos
                    //compare si least tadi dengan value
                    if(storage.get(leastChildPos).compareTo(value) == -1){
                        //kalo lebih kecil dari si value, maka geser ke atas
                        storage.setElementAt(storage.get(leastChildPos), now);
                        storage.get(leastChildPos).heapIndex = now;
                        now = leastChildPos; // si now turun lagi
                    }
                    else{// kasus ketika percolate down nya berhenti sebelum mencapai leaf
                        
                            storage.setElementAt(value, now);
                            value.heapIndex = now;
                            return;
                    }
                        
                }
                else{//si now itu sudah merupakan leaf
                    storage.setElementAt(value, now);
                    value.heapIndex = now;
                    return;
                }
            }
        }

    public void changePriority(int newHeight, int heapIndex){
        if(storage.get(heapIndex).getHeight() < newHeight){
            storage.get(heapIndex).setHeight(newHeight);
            //System.out.println("YES1");
            percolateDown(heapIndex);
        }

        else if(storage.get(heapIndex).getHeight() > newHeight){
            storage.get(heapIndex).setHeight(newHeight);
            //System.out.println("YES2");
            percolateUp(heapIndex);
        }
        else
            storage.get(heapIndex).setHeight(newHeight);
    }

    public void doR(){
        Dataran lowest = this.getMin();
        Dataran highest;
        int maxHeight = lowest.getHeight();
        int minIndex = lowest.index;

        if(lowest.next != null && lowest.prev != null){
            highest = Dataran.findMax(lowest, lowest.next, lowest.prev);

            this.changePriority(highest.getHeight(), lowest.heapIndex);
            this.changePriority(highest.getHeight(), lowest.next.heapIndex);
            this.changePriority(highest.getHeight(), lowest.prev.heapIndex);
            maxHeight = highest.getHeight();
        }

        else if(lowest.next == null && lowest.prev != null){
            highest = Dataran.findMax(lowest, lowest.prev);

            this.changePriority(highest.getHeight(), lowest.heapIndex);
            this.changePriority(highest.getHeight(), lowest.prev.heapIndex);
            maxHeight = highest.getHeight();
        }

        else if(lowest.next != null && lowest.prev == null){
            highest = Dataran.findMax(lowest, lowest.next);

            this.changePriority(highest.getHeight(), lowest.heapIndex);
            this.changePriority(highest.getHeight(), lowest.next.heapIndex);
            maxHeight = highest.getHeight();
        }
        else{
            this.changePriority(lowest.getHeight(), lowest.heapIndex);
        }
        Lab6.out.println(maxHeight + " " + minIndex);
    }

  

    public Dataran getMin(){
        return storage.get(0);
    }

    public int parentOf(int i){
        return (i-1)/2;
    }

    public int left(int i){
        return (2*i) + 1;
    }

    public int right(int i){
        return (2*i) + 2;
    }


    public Dataran[] toArray() {
        return storage.toArray(new Dataran[storage.size()]);
    }

}