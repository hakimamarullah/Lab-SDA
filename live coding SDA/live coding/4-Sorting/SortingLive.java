/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package first.sda;

/**
 *
 * @author solic
 */
public class SortingLive {
    public static void main(String[] args){
        int[] array = new int[]{7,5,8,6,9,2,1,0,10,6,3};
        array = new int[]{10,9,8,7,6,5,4,3,2,1};
        cetak(array);
        mergeSort(array);
        cetak(array);
    }
    
    public static void cetak(int[] array){
        for(int i : array)
            System.out.print(i + " ");
        System.out.println("");
    }
    
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
        while(i <= akhir && kiri <= kiriLength-1 && kanan <= kananLength-1)
            array[i++] = arrayKiri[kiri] < arrayKanan[kanan] ? arrayKiri[kiri++] : arrayKanan[kanan++];
        
        //copy sisa dari arrayKiri jika ada
        while(kiri <= kiriLength-1)
            array[i++] = arrayKiri[kiri++];
        
        //copy sisa dari arrayKanan jika ada
        while(kanan <= kananLength-1)
            array[i++] = arrayKanan[kanan++];
    }
    
    //ascending
    public static void bubbleSort(int[] array){
        //i adalah pembatas antara unsorted part dg sorted part
        for(int i = array.length - 1; i > 0; i--){
            boolean swap = false;
            //bubbling
            for(int j = 0; j < i ; j++){
                if(array[j] > array[j+1]){//kalau di j lebih besar dari di kanannya maka di-swap
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                    swap = true;
                }   
            }
            
            //cek apakah tadi terjadi swap
            if(swap == false)
                return;
        }    
    }
    
    //sorted part ada di sebelah kanan array
    //ascending, select max dari unsorted part, taruh di awal sorted part
    public static void selectionSort(int[] array){
        //i untuk pembatas sorted dan unsorted
        for(int i = array.length - 1; i > 0 ;i--){
            //sorted list 
            int idxMax = 0;
            //scanning dari 0 sampai pembatas (i)
            //effort untuk select
            for(int j = 1; j <= i; j++)
                if(array[j] > array[idxMax])
                    idxMax = j;
            
            //di sini, idxMax menunuk ke elemen maximum di dalam unsorted part
            //taruh ke sorted part artinya ke index i
            //swap
            int temp = array[i];
            array[i] = array[idxMax];
            array[idxMax] = temp;
        }       
    }
    
    public static void insertionSort(int[] array){
        //i adalah pembatas (i masuk ke unsorted)
        for(int i = 1; i < array.length; i++){
            int picked = array[i];
            //mau insert ke sorted part
            //effornya yang linear (O (N)) pada saat insert ini
            int j = i-1;
            for(; j >= 0 && array[j] > picked; j--)
                //maka j perlu geser ke kanan sejauh 1
                array[j+1] = array[j];
            
            //sudah ketemu tempat yang tepat untuk si picked
            array[j+1] = picked;
        }
    }
}
