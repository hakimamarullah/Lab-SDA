/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash.live;

/**
 *
 * @author solic
 */
public class HashTest {
    public static void main(String[] args){
        HashTable myTable = new HashTable(11);
        
        myTable.add(19);
        myTable.add(12);
        myTable.add(14);
        myTable.add(13);
        myTable.add(35);
    }
}

class HashTable{
    HashEntry[] table;
    int tableSize;
    int occupied;// lamda = occupied/tableSize
    int dataSize;
    
    HashTable(int size){
        table = new HashEntry[size];
        tableSize = size;
    }
    
    boolean add(Integer x){
        int currentPos = findPos(x);
        //3 kemungkinan indeks yang di-return:
        //sel masih null
        //sel tidak null, data sama, inactive
        //sel tidak null, data sama, active
        
        //case kalau x sudah ada dan aktif
        if(isActive(currentPos))
            return false;
        
        if(table[currentPos] == null)
            occupied++;
        
        table[currentPos] = new HashEntry(x);
        System.out.println(x + " diletakkan di indeks: " + currentPos);
        dataSize++;
        
        return true;
    }
    
    int findPos(Integer x){
        return findPosLin(x);
    }
    
    //cari posisi si x
    //jika ada, kembalikan index si x
    //jika tidak ada, kembalikan posisi kosong atau inActive yang pertama
    //Linear
    int findPosLin(Integer x){
        int currentPos = hashCode(x);
        
        while(table[currentPos] != null){
            if(table[currentPos].data == x)
                break;
            
            //probing linear
            currentPos++;
        }
        
        return currentPos;
    }
    
    boolean isActive(int currentPos){
        return table[currentPos] != null && table[currentPos].isActive;
    }
    
    int hashCode(Integer x){
        return mod(x, tableSize);
    }
    
    int mod(int x, int m){
        return ((x % m) + m) % m;
    }
}

class HashEntry{
    boolean isActive;
    Integer data;
    
    HashEntry(Integer data){
        this.data = data;
        isActive = true;
    }
}
