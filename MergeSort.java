public class MergeSort{
    public static void mergeSort(int[] array){
        mergeSort(array, 0, array.length - 1);
    }
    
    //awal dan akhir menandakan subarray-nya
    public static void mergeSort(int[] array, int awal, int akhir){
        
        int length = (akhir - awal) + 1;
        int tengah = awal + (akhir - awal)/2;
        
        if(length <= 1)
            return;
        
        //divide lalu rekursif ke kiri dan ke kanan
        mergeSort(array,awal,tengah);//kiri
        mergeSort(array,tengah+1,akhir);//kanan
        
        merge(array,awal,tengah,akhir);// O(N)
    }
    
    public static void merge(int[] array, int awal, int tengah, int akhir){
        int kiriLength = tengah - awal + 1;
        int kananLength = akhir - tengah;
        
        //copy dulu sub-array kiri dan kanan
        int[] arrayKiri = new int[kiriLength];
        int[] arrayKanan = new int[kananLength];
        for(int kr = 0, kiriAsli = awal; kr < kiriLength; kr++, kiriAsli++)
            arrayKiri[kr] = array[kiriAsli];
        for(int kn = 0, kananAsli = tengah + 1; kn < kananLength; kn++, kananAsli++)
            arrayKanan[kn] = array[kananAsli];
        
        //merge arrayKiri dan arrayKanan, hasilnya masukkan ke array asli
        //3 buah counter: i adalah indeks subarray yang terurut
        int kiri = 0, kanan = 0, i = awal;
        //compare-compare kiri dan kanan, siapa yang di-copy ke array asli
        while(i <= akhir && kiri <= kiriLength-1 && kanan <= kananLength-1){
            if(arrayKiri[kiri]< arrayKanan[kanan]){
                 array[i++] = arrayKiri[kiri++];
            }
           else if(arrayKiri[kiri] > arrayKanan[kanan]){
            array[i++] = arrayKanan[kanan++];
            }
           
        }
        
        //copy sisa dari arrayKiri jika ada
        while(kiri <= kiriLength-1)
            array[i++] = arrayKiri[kiri++];
        
        //copy sisa dari arrayKanan jika ada
        while(kanan <= kananLength-1)
            array[i++] = arrayKanan[kanan++];
    }
}