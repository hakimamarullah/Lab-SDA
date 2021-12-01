import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.max;
import java.math.BigInteger;

public class TP3 {
    private static InputReader in;
    public static OutputStream outputStream = System.out;
    public static PrintWriter out = new PrintWriter(outputStream);
    public static Vector<Karyawan> listKaryawan = new Vector<Karyawan>();
    public static Network perusahaan;
    public static Karyawan[] karyawanPangkat;

    
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        

        int N = in.nextInt();//Jumlah karyawan
        int M = in.nextInt();//Jumlah edge pertemanan
        int Q = in.nextInt();//Jumlah command
        perusahaan = new Network(N);
        karyawanPangkat = new Karyawan[N+1];
       
        for(int i=1; i<N+1; i++){
            int pangkat = in.nextInt();
            listKaryawan.addElement(new Karyawan(i, pangkat));
            perusahaan.networkData[i] = listKaryawan.get(i-1);
            karyawanPangkat[pangkat] = listKaryawan.get(i-1);
        }




        while(M-- > 0){
            int source = in.nextInt(); // source berteman dengan dest
            int dest = in.nextInt();
            listKaryawan.get(source-1).addFriend(listKaryawan.get(dest-1));
        }

        while(Q-- > 0){
            int command = in.nextInt();

            switch(command){
                case 1://TAMBAH
                    int source = in.nextInt(); // source berteman dengan dest
                    int dest = in.nextInt();
                    listKaryawan.get(source-1).addFriend(listKaryawan.get(dest-1));
                    break;
                case 2://RESIGN
                    int nomorKaryawan = in.nextInt();
                    listKaryawan.get(nomorKaryawan-1).resign();
                  
                    N--;
                    break;
                case 3://CARRY
                    int nomorKaryawanCarry = in.nextInt();
                    out.println(listKaryawan.get(nomorKaryawanCarry-1).carry());
                
                    break;
                case 4://BOSS
                    int networkKaryawan = in.nextInt();
                    out.println(perusahaan.boss(listKaryawan.get(networkKaryawan-1)));
                  
                    //perusahaan.cetakPath()
                    break;
                case 5://SEBAR
                    int nomorKaryawanAsal = in.nextInt();
                    int nomorKaryawanTujuan = in.nextInt();
                    
                    break;
                case 6://SIMULASI
                    out.println(simulasi(karyawanPangkat, N));
                    break;
                case 7://NETWORKING
                    break;
            }
        }

     out.flush();   
    }

    public static int simulasi(Karyawan[] karyawanPangkat, int N){
        int counter = N;
        for(Karyawan curr: karyawanPangkat){
            if(curr != null && curr.friendsList != null){
                if(curr.friendsList.getMax().pangkat > curr.pangkat)
                    counter--;
            }
        }
        return counter;
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

class Karyawan extends TP3 implements Comparable<Karyawan>{
    int pangkat;
    int nomor;
    int heapIndex;
    VectorHeap friendsList;
    List<Karyawan> destList;

    public Karyawan(int nomor, int pangkat){
        this.pangkat = pangkat;
        this.nomor = nomor;
        this.friendsList = new VectorHeap();
        this.destList = new ArrayList<Karyawan>();
    }
    
    int carry(){
        VectorHeap s = this.friendsList;

        if(s.size() != 0 && TP3.listKaryawan.get(s.getMax().nomor-1).pangkat != 0){
            return s.getMax().pangkat;
        }
        if(TP3.listKaryawan.get(s.getMax().nomor-1).pangkat == 0){
            s.percolateDown(s.getMax().heapIndex);
            return s.getMax().pangkat;
        }
        return 0;
    }

    void resign(){

        
        this.pangkat = 0;
        this.friendsList = null;
        this.destList = null;
    }
    void addFriend(Karyawan dest){
        this.destList.add(dest);
        this.friendsList.insert(dest);
        dest.destList.add(this);
        dest.friendsList.insert(this);
    }
    @Override
    public String toString(){
        return Integer.toString(this.nomor);
    }
    @Override
    public int compareTo(Karyawan obj){
        if(this.pangkat > obj.pangkat){
            return 1;
        }
        else if(this.pangkat < obj.pangkat){
            return -1;
        }
        return 0;
    }

}

class VectorHeap{
    Vector<Karyawan> storage;

    public VectorHeap(){
        storage = new Vector<Karyawan>();
    }

    public void insert(Karyawan karyawan){
        storage.addElement(karyawan);

        percolateUp(storage.size()-1);
    }

   private void percolateUp(int leaf){
            int parent = parentOf(leaf);
            
            //value yang tadi di leaf, kita simpan dulu ke temp
            Karyawan value = storage.get(leaf);
            int now = leaf;
            
            //traverse dari leaf ke atas, maksimal nanti sampai root
            while(now > 0 && storage.get(parent).compareTo(value) == -1){
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
    public void percolateDown(int root){
            //simpan si root yang sekarang di temp
            Karyawan value = storage.get(root);
            
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
                    if(rightChildPos < heapSize && storage.get(rightChildPos).compareTo(storage.get(leastChildPos)) == 1)
                        leastChildPos = rightChildPos;
                    
                    //di sini, kita sudah tau jalur percolate down nya ke kiri ataukah ke kanan, yaitu ke leastChildPos
                    //compare si least tadi dengan value
                    if(storage.get(leastChildPos).compareTo(value) == 1){
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

    // public void changePriority(int newHeight, int heapIndex){
    //     if(storage.get(heapIndex).pangkat < newHeight){
    //         storage.get(heapIndex).setHeight(newHeight);
    //         //System.out.println("YES1");
    //         percolateDown(heapIndex);
    //     }

    //     else if(storage.get(heapIndex).getHeight() > newHeight){
    //         storage.get(heapIndex).setHeight(newHeight);
    //         //System.out.println("YES2");
    //         percolateUp(heapIndex);
    //     }
    //     else
    //         storage.get(heapIndex).setHeight(newHeight);
    // }

   

  

    public Karyawan getMax(){
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
    public int size(){
        return this.storage.size();
    }

}

class Network extends TP3{
    int numberOfKaryawan;
    Karyawan[] networkData;
    Karyawan[] pred;
    
    Network(int num){
        this.numberOfKaryawan = num + 1;
        
        networkData = new Karyawan[numberOfKaryawan];
        pred = new Karyawan[numberOfKaryawan];
        
        for(int i = 0; i < numberOfKaryawan; i++)
            networkData[i] = new Karyawan(i,0);
    }

    void addNetwork(Karyawan baru){
        this.networkData[baru.nomor] = baru;
    }
    
    void cetakPathHelper(ArrayList<Karyawan> path, Karyawan now){
        if(pred[now.nomor] != null)
            cetakPathHelper(path, pred[now.nomor]);
        path.add(now);
        TP3.out.print(now.nomor + " - ");
    }
     
  
    void cetakPath(Karyawan src, Karyawan dest){
        ArrayList<Karyawan> path = new ArrayList<Karyawan>();
        cetakPathHelper(path, dest);
        TP3.out.println();

    }
   
    
    int boss(Karyawan s){
        int[] flagTable = new int[numberOfKaryawan];
        int[] jarakDariSource = new int[numberOfKaryawan];
        if(networkData[s.nomor].destList.size() ==0){
            return 0;
        }
        
        for(int i = 0; i < numberOfKaryawan; i++)
            pred[i] = null;
        
        Queue<Karyawan> myQueue = new LinkedList<Karyawan>();
        
        myQueue.add(s);
        flagTable[s.nomor] = 1;
        Karyawan pangkatTertinggi = s;
        while(myQueue.isEmpty() == false){
            Karyawan now = myQueue.poll();
            //TP3.out.print(now.pangkat + " - ");
            if(now.pangkat > pangkatTertinggi.pangkat){
                pangkatTertinggi = now;
            }
            //cari semua yang adjacent dengan now
            for(Karyawan dest : networkData[now.nomor].destList){
                
                if(flagTable[dest.nomor] == 0){
                    myQueue.add(dest);
                    flagTable[dest.nomor] = 1;
                    jarakDariSource[dest.nomor] = jarakDariSource[now.nomor] + 1;
                    pred[dest.nomor] = now;
                    
                }
            }
        }
        return pangkatTertinggi == s? 0: pangkatTertinggi.pangkat;
    }
    void cetakDFS(Karyawan s){
        //flagtable
        int[] flagTable = new int[numberOfKaryawan];
        
        for(int i = 0; i < numberOfKaryawan; i++)
            pred[i] = null;
        
        cetakDFSRek(flagTable, s);
        //TP3.out.println();
    }
    
    void cetakDFSRek(int[] flagTable, Karyawan now){
        flagTable[now.nomor] = 1;
        //TP3.out.print( now + " - ");
        
        for(Karyawan dest : networkData[now.nomor].destList){
            if(flagTable[dest.nomor] != 1){
                pred[dest.nomor] = now;
                cetakDFSRek(flagTable, dest);
            }
        }
    }
    
}