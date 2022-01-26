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
public class Rekursif {
    public static void main(String[] args){
        System.out.println(fibBiasa(50));
    }
    
    public static int fibBiasa(int n){
        if(n<=1) return n;
        return fibBiasa(n-1)+fibBiasa(n-2);
    }
    
    //fib ke 0: 0, ke 1: 1, dst
    public int fibDP(int n){
        if ( n <= 1) return n;
        //hitung dulu fib ke 0 dst sampai fib ke n ketemu
        //tabel untuk menyimpan nilai fib
        int[] nilaiFib = new int[n+1]; 
        
        nilaiFib[0] = 0;
        nilaiFib[1] = 1;
        
        for(int i = 2; i <= n; i++)
            nilaiFib[i] = nilaiFib[i-1] + nilaiFib[i-2];
        
        return nilaiFib[n];
    }
    
    //fib ke 0: 0, ke 1: 1, dst
    public int fibDP2(int n){
        if ( n <= 1) return n;
        
        //hitung dulu fib ke 0 dst sampai fib ke n ketemu
        //tabel untuk menyimpan nilai fib
        int fibSebelumnyaLagi = 0;
        int fibSebelumnya = 1;
        int fib = 0;
        
        for(int i = 2; i <= n; i++){
            fib = fibSebelumnya + fibSebelumnyaLagi;
            fibSebelumnyaLagi = fibSebelumnya;
            fibSebelumnya = fib;
        }
        
        return fib;
    }
    
    //rekursif: tail recurvise
    //O (n)
    public int fib(int n){// fib(0)
        return fibTailRec(0,1,n); // 0, 1, 100
    }
    
    // O (counter)
    private int fibTailRec(int sebelumnyaLagi, int sebelumnya, int counter){
        if (counter == 0) return sebelumnyaLagi; //atau 0
        else if (counter == 1) return sebelumnya;
        
        return fibTailRec(sebelumnya, sebelumnyaLagi + sebelumnya, counter -1); //1 , 1, 99
                                                                                //1 , 2, 98
                                                                                //2 , 3, 97
                                                                                //3 , 5, 96
                                                                                //... , ..., 1
    }
}
