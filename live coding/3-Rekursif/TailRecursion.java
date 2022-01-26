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
public class TailRecursion {
    public static void main(String[] args){
        //System.out.println(pangkat(2,1000));
        System.out.println(pangkatRek(2,30));
    }
    
    public static int pangkatRek(int x, int n){
        if(n == 0) return 1;
        return x * pangkatRek(x, n-1);
    }
    
    public static int pangkat(int x, int n){
        return pangkatTailRec(1, x, n);
    }
    
    private static int pangkatTailRec(int hasil, int x, int counter){
        if(counter == 0) return hasil;
        return pangkatTailRec(hasil*x, x, counter-1);
    }
    
    
}
