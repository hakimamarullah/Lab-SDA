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
public class Kompleksitas {
    
    public static void main(String[] args){
        //System.out.println(jumlahBit(128));
        System.out.println(jumlahBitRek(128));
    }
    
    //O (Log N)
    public static int jumlahBit(int N){
        int jumlahBit = 0;
        for(int i = N; i >= 1; i /= 2)
            jumlahBit++;
        
        return jumlahBit;
    }
    
    //O (Log N)
    public static int jumlahBitRek(int N){
        if(N == 1)
            return 1;
        return 1+ jumlahBitRek(N/2);
    }
    
    //O (N)
    public static int hitung(int N){
        if(N == 1)
            return 1;
        return N + hitung(N-1);
    }
    
    //O (N)
    public static int hitungFor(int N){
        int result = 0;
        for(int i = 1; i <= N; i++)
            result += i;
        return result;
    }
    
    public static void algo0(){
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");
    }
    
    //O (N^2)
    public static void algo1(int[] array){
        int N = array.length;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; i++){
                System.out.println(array[i]);
                System.out.println(array[i]);
                algo2(array);
            }
        }
    }
    
    public static void algo2(int[] array){
        
        //N^2, dengan N adalah ukuran array
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length; i++)
                System.out.println(array[i]);
        }
        
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length; i++)
                System.out.println(array[i]);
        }
        
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length; i++)
                System.out.println(array[i]);
        }
        
        //N, dengan N adalah ukuran array
        for(int i = 0; i < array.length; i++)
            System.out.println(array[i]);
        
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
    
}
