import java.io.*;
import java.util.*;

public class Lab5 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    public static HashMap<String, BoxPermen> stock = new HashMap<String, BoxPermen>();

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
                // out.print("DEBUG 1 ");
                // out.println(box);

            }
        }
        // out.println(box);
        // out.println(rangeHarga);


        out.flush();
    }

    //TODO
    static String handleBeli(int L, int R){
        // ArrayList<BoxPermen> arrayList = new ArrayList<BoxPermen>();
        // mapToArrayList(stock, arrayList);
        // BoxPermen[] temp = arrayList.toArray(new BoxPermen[arrayList.size()]);
        Collection <BoxPermen> sourceMap = stock.values();
        BoxPermen[] temp = sourceMap.toArray(new BoxPermen[sourceMap.size()]);
        mergeSort(temp);
        int indexMin = lowerIndex(temp, L);
        int indexMax = upperIndex(temp, R);
        // // for(int i=indexMin; i<= indexMax; i++){
        // //     System.out.print(temp[i].getHarga() + " ");
        // // }
        // // System.out.println();
        if(indexMin > indexMax || indexMax == indexMin || (indexMin == indexMax -1 && BoxPermen.isSameType(temp[indexMin], temp[indexMax]))){
            return "-1 -1";
        }

        // //ArrayList<Pair> pair = new ArrayList<Pair>();
       
        for(int i= indexMin; i<indexMax; i++){
            if(!BoxPermen.isSameType(temp[indexMax], temp[i])){
                //pair.add(new Pair(temp[i].getHarga(), temp[indexMax].getHarga()));
                
                return Integer.toString(temp[i].getHarga()) + " " + Integer.toString(temp[indexMax].getHarga());
            }
        }
        // Collections.sort(pair);
        // System.out.println(pair.get(pair.size()-1).toString());
        return "-1 -1";
   
        //return Integer.toString(temp[indexMin].getHarga()) + " " + Integer.toString(temp[indexMax].getHarga());
    }

    static void mapToArrayList(Map<String,BoxPermen> box, ArrayList<BoxPermen> target){
        for(String x : box.keySet()){
            if(box.get(x).getHarga() > 0)
                target.add(box.get(x));
        }
    }
    //https://www.geeksforgeeks.org/queries-counts-array-elements-values-given-range/
    static int lowerIndex(BoxPermen[] arr, int x){
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
    static int upperIndex(BoxPermen[] array, int y){
        int l = 0, h = array.length - 1;
        while (l <= h)
        {
            int mid = (l + h) / 2;
            if (array[mid].getHarga() <= y){
                l = mid + 1;
            }
            else
                h = mid - 1;
        }
        return h;
    }

    //TODO
    static void handleStock(String nama, int harga, int tipe){
        BoxPermen newBox = new BoxPermen(nama, harga, Integer.toString(tipe));
        stock.put(newBox.getNama(), newBox);

    }

    //TODO
    static void handleSoldOut(String nama){
        stock.get(nama).harga = -1;
        //stock.put(nama, null);

    }

    public static void mergeSort(BoxPermen[] array, int awal, int akhir){
        
        int length = (akhir - awal) + 1;
        int tengah = awal + (akhir - awal)/2;
        
        if(length <= 1)
            return;
        
        //divide lalu rekursif ke kiri dan ke kanan
        mergeSort(array,awal,tengah);//kiri
        mergeSort(array,tengah+1,akhir);//kanan
        
        merge(array,awal,tengah,akhir);// O(N)
    }
    public static void mergeSort(BoxPermen[] array){
        mergeSort(array, 0, array.length - 1);
    }
    
    public static void merge(BoxPermen[] array, int awal, int tengah, int akhir){
        int kiriLength = tengah - awal + 1;
        int kananLength = akhir - tengah;
        
        //copy dulu sub-array kiri dan kanan
        BoxPermen[] arrayKiri = new BoxPermen[kiriLength];
        BoxPermen[] arrayKanan = new BoxPermen[kananLength];
        for(int kr = 0, kiriAsli = awal; kr < kiriLength; kr++, kiriAsli++)
            arrayKiri[kr] = array[kiriAsli];
        for(int kn = 0, kananAsli = tengah + 1; kn < kananLength; kn++, kananAsli++)
            arrayKanan[kn] = array[kananAsli];
        
        //merge arrayKiri dan arrayKanan, hasilnya masukkan ke array asli
        //3 buah counter: i adalah indeks subarray yang terurut
        int kiri = 0, kanan = 0, i = awal;
        //compare-compare kiri dan kanan, siapa yang di-copy ke array asli
        while(i <= akhir && kiri <= kiriLength-1 && kanan <= kananLength-1)
            array[i++] = arrayKiri[kiri].getHarga() < arrayKanan[kanan].getHarga() ? arrayKiri[kiri++] : arrayKanan[kanan++];
        
        //copy sisa dari arrayKiri jika ada
        while(kiri <= kiriLength-1)
            array[i++] = arrayKiri[kiri++];
        
        //copy sisa dari arrayKanan jika ada
        while(kanan <= kananLength-1)
            array[i++] = arrayKanan[kanan++];
    }
    static class Pair implements Comparable<Pair>{
        int total;
        int harga_1;
        int harga_2;

        public Pair(int harga_1, int harga_2){
            this.harga_1 = harga_1;
            this.harga_2 = harga_2;
            this.total = harga_1 + harga_2;
        }

        public int compareTo(Pair obj){
            if(this.total > obj.total){
                return 1;
            }
            else if(this.total < obj.total){
                return -1;
            }
            else{
                return 0;
            }
        }
        @Override
        public String toString(){
            return Integer.toString(this.harga_1) + " " + Integer.toString(this.harga_2);
        }

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

class BoxPermen{
    String nama;
    String tipe;
    int harga;

    public BoxPermen(String nama, int harga, String tipe){
        this.nama = nama;
        this.harga = harga;
        this.tipe = tipe;
    }

    public String getNama(){
        return this.nama;
    }

    public String getTipe(){
        return this.tipe;
    }

    public int getHarga(){
        return this.harga;
    }

    static boolean isSameType(BoxPermen a, BoxPermen b){
        return a.getTipe().equals(b.getTipe());
    }
    @Override
    public String toString(){
        return Double.toString(harga) + this.tipe;
    }
}