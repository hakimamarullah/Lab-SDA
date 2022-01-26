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
public class MCSSLive {
    public static void main(String[] args){
        int[] array = {2, -4, 13, -4, 11, -2};
        int[] array1 = {2, -4, 13, -4, 11, -8, 20};
        
        System.out.println(kubik(array1));
        System.out.println(kuadratik(array1));
        System.out.println(linear(array1));
    }
    
    //O (N^3)
    public static int kubik(int[] array){
        int N = array.length;
        int maxSum = 0;
        
        for(int i = 0; i < N; i++)//i: awal dari subsequence
            for(int j = i; j < N; j++){//j: akhir dari subsequence
                // di sini kita punya i dan j
                int thisSum = 0;
                //hitung sum untuk subsequence dari index i sampai j
                for(int k = i; k <= j; k++)//mau nge-sum
                    thisSum += array[k];
                
                if(thisSum > maxSum)
                    maxSum = thisSum;
            }
        return maxSum;       
    }
    
    //O (N^2)
    public static int kuadratik(int[] array){
        int N = array.length;
        int maxSum = 0;
        
        for(int i = 0; i < N; i++){//i: awal dari subsequence
            int thisSum = 0;
            for(int j = i; j < N; j++){//j: akhir dari subsequence
                //hitung sum untuk subsequence dari index i sampai j
                thisSum += array[j];
                
                if(thisSum > maxSum)
                    maxSum = thisSum;
            }
        }
        return maxSum;       
    }
    
    //O (N): dia tidak men-generate semua kemungkinan subsequence
    public static int linear(int[] array){
        int N = array.length;
        int maxSum = 0;
        int thisSum = 0;
        
        for(int i = 0; i < N; i++){//i: awal dari subsequence
            //hitung sum untuk subsequence dari 0 
            //atau dari terakhir reset 
            //sampai i
            thisSum += array[i];
                
            if(thisSum > maxSum)
                maxSum = thisSum;
            else if(thisSum < 0)//reset ke nol jika thisSum negatif
                thisSum = 0;
        }
        return maxSum;       
    }
    
    /*
    9 -4 -6 7 3 -5 4 8
    
    maxSum = 0 -> 9 -> 10 -> 17
    thisSum = 0 -> 9 -> 5 -> -1 -> 0 -> 7 -> 10 -> 5
                -> 9 -> 17
    Linear:
    9 -4 -6 7 3 -5 4 8
    
    9
    9 -4
    9 -4 -6
    
    7
    7 3
    7 3 -5
    7 3 -5 4
    7 3 -5 4 8
    
    */
    
    /*
    Kubik ataupun Kuadratik, mengenerate semua kemungkinan subsequence
    9
    9 -4
    9 -4 -6
    9 -4 -6 7
    .
    .
    9 -4 -6 7 3 -5 4 8
    
    -4
    .
    .
    -4 -6 7 3 -5
    -4 -6 7 3 -5 4
    -4 -6 7 3 -5 4 8
    
    */
}
