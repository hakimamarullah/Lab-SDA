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
public class CoinChangeLive {
    
    public static void main(String[] args){
        //System.out.println(hitungJumKoinMin(new int[]{1,5,7}, 10));//2
        //System.out.println(hitungJumKoinMin(new int[]{1,5,10,21,25}, 63));//3
        
        System.out.println(hitungJumKoinMinDP(new int[]{1,5,7}, 10));//2
        System.out.println(hitungJumKoinMinDP(new int[]{1,5,10,21,25}, 63));//3
    }
    
    //coinChange brute force
    //exponential
    public static int hitungJumKoinMin(int[] jenisKoin, int uang){
        int jumKoinMin = uang;//anggap ambil koin 1 semua
        
        
        //cari apakah bisa lebih kecil jumlah koinnya
        //loop semua jenis koin
        for(int i = 0; i < jenisKoin.length && jenisKoin[i] <= uang; i++){
            //koin ke i diambil
            int jumKoinSekarang = 1 + hitungJumKoinMin(jenisKoin, uang - jenisKoin[i]);
            if(jumKoinSekarang < jumKoinMin)
                jumKoinMin = jumKoinSekarang;
        }
        
        return jumKoinMin;
    }
    
    public static int hitungJumKoinMinDP(int[] jenisKoin, int uang){
        return hitungJumKoinMinDPHelper(jenisKoin, uang, new int[uang+1]);
    }
    
    //asumsiL array jenisKoin terurut
    //jenis koin: M buah koin
    //uangnya: N sen
    private static int hitungJumKoinMinDPHelper(int[] jenisKoin, int uang, int[] jumKoinMin){
        jumKoinMin[0] = 0;
        
        //pengisian tabel: array jumKoinMin
        //u adalah uang yang di baris sekarang
        for(int u = 1; u <= uang; u++){
            int jumKoinSekarang = u; //kasus terburuk kalau ambil koin 1 terus
            //kita akan mengisi tabel yaitu array di indeks ke i (uang sejumlah i)
            //loop semua jenis koin
            for(int j = 0; j < jenisKoin.length && jenisKoin[j] <= u; j++){
                if(1 + jumKoinMin[u - jenisKoin[j]] < jumKoinSekarang)
                    jumKoinSekarang = 1 + jumKoinMin[u - jenisKoin[j]];
            }
            
            jumKoinMin[u] = jumKoinSekarang;//ketika jumKoinSekarang udah paling minimum
            //baru kita update ke array (table)
        }
        
        return jumKoinMin[uang];
    }
}
