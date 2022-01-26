/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash.lat;

/**
 *
 * @author solic
 */
public class HashTest {
    public static void main(String[] args){
        HashSet mySet = new HashSet(11);
        
        mySet.add(19);
        mySet.add(12);
        mySet.add(14);
        mySet.add(13);
        mySet.add(35);
    }
}

class HashSet{
    HashEntry[] hashTable;
    int tableSize;
    int occupied;//jumlah sel yang ditempati, baik active maupun inactive
    int dataSize;
    
    HashSet(int size){
        tableSize = size;
        hashTable = new HashEntry[size];
    }
    
    private static class HashEntry{
        boolean isActive;
        Integer data;
        
        HashEntry(Integer i){
            data = i;
            isActive = true;
        }
    }
    
    public int findPos(Integer x){
        return findPosLin(x);
    }
    
    public int getIndex(Integer x){
        int currentPos = findPos(x);
        //3 kemungkinan indeks yang di-return:
        //sel masih null
        //sel tidak null, data sama, inactive
        //sel tidak null, data sama, active
        
        if(isActive(currentPos))
            return currentPos;
        return -1;
    }
    
    public boolean remove(Integer x){
        int currentPos = findPos(x);
        //3 kemungkinan indeks yang di-return:
        //sel masih null
        //sel tidak null, data sama, inactive
        //sel tidak null, data sama, active
        
        //berarti belum di-insert
        if(isActive(currentPos) == false)
            return false;
        
        hashTable[currentPos].isActive = false;
        dataSize--;
        
        return true;
    }
    
    public boolean add(Integer x){
        int currentPos = findPos(x);
        
        if(isActive(currentPos))
            return false;//x sudah ada
        
        //jika tadinya null, berarti menambah sel baru yang occupied
        if(hashTable[currentPos] == null)
            occupied++;
        hashTable[currentPos] = new HashEntry(x);
        System.out.println(x + " di indeks: " + currentPos);
        dataSize++;
        
        //rehash jika load factor lebih dari 0.5
        if(occupied > tableSize/2)
            rehash();
        
        return true;
    }
    
    public void rehash(){
        HashSet newHash = new HashSet(4*tableSize);
        
        //rehash ke newHash
        for(int i = 0; i < tableSize; i++)
            if(isActive(i))
                newHash.add(hashTable[i].data);
        
        //copy ke oldHash
        tableSize = newHash.tableSize;
        dataSize = newHash.dataSize;
        occupied = newHash.occupied;
        hashTable = newHash.hashTable;
    }
    
    public boolean isActive(int currentPos){
        return hashTable[currentPos] != null && hashTable[currentPos].isActive == true;
    }
    
    public int findPosLin(Integer x){
        int currentPos = hashCode(x);
        
        //cari index sebenarnya dari x
        while(hashTable[currentPos] != null){//jika sel null maka return
            //cek apakah isinya adalah x --> exist
            if(hashTable[currentPos].data == x)
                break;//sel tidak null, data sama (mungkin active mungkin inactive)
                       //juga return
            
            //jika bukan, berarti data lain dan harus probing
            currentPos ++;
            
            if(currentPos >= tableSize)
                currentPos -= tableSize;
        }
        
        return currentPos;
        //3 kemungkinan indeks yang di-return:
        //sel masih null
        //sel tidak null, data sama, inactive
        //sel tidak null, data sama, active
    }
    
    //f(i) = i^2 = f(i-1) + 2(i-1)
    public int findPosQuad(Integer x){
        int offset = 1;
        
        int currentPos = hashCode(x);
        
        //cari index sebenarnya dari x
        while(hashTable[currentPos] != null){
            //cek apakah isinya adalah x --> exist
            if(hashTable[currentPos].data == x)
                break;
            
            //jika bukan, berarti data lain dan harus probing
            currentPos += offset; //bil. kuadrat yang sekarang
            offset += 2; //untuk cari bil. kuadrat berikutnya
            
            if(currentPos >= tableSize)
                currentPos -= tableSize;
        }
        
        return currentPos;
    }
    
    public int findPosDou(Integer x){
        int hash2Val = hash2(x);
        
        int currentPos = hashCode(x);
        
        //cari index sebenarnya dari x
        for(int i = 1; hashTable[currentPos] != null; i++){
            //cek apakah isinya adalah x --> exist
            if(hashTable[currentPos].data == x)
                break;
            
            //jika bukan, berarti data lain dan harus probing
            currentPos += i*hash2Val;
            
            if(currentPos >= tableSize)
                currentPos -= tableSize;
        }
        
        return currentPos;
    }
    
    public int mod(int x, int m){
        return ((x % m) + m) % m;
    }
    
    public int hash2(Integer x){
        return 1 + (mod(mod(x, 19), tableSize));
    }
    
    public int hashCode(Integer x){
        return mod(x, tableSize);
    }
}
