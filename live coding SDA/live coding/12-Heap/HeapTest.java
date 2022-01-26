/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap.live;

import java.util.Vector;

/**
 *
 * @author solic
 */
public class HeapTest {
    public static void main(String[] args){
        Heap myHeap = new Heap();
        
        int[] input = new int[]{100, 50, 40, 120, -5, 45, 30};
        
        for(Integer x : input)
            myHeap.add(x);
        
        System.out.println(myHeap.remove());
        System.out.println(myHeap.remove());
        System.out.println(myHeap.remove());
        System.out.println(myHeap.remove());
        System.out.println(myHeap.remove());
        System.out.println(myHeap.remove());
        System.out.println(myHeap.remove());
    }
}

class Heap{
    Vector<Integer> data = new Vector();
    
    public int parentOf(int i){
        return (int) Math.floor((i-1)/2);
    }
    
    public int leftChildOf(int i){
        return (2*i) + 1;
    }
    
    public int rightChildOf(int i){
        return (2*i) + 2;
    }
    
    public void add(Integer value){
        data.add(value);
        percolateUp(data.size() - 1);
    }
    
        private void percolateUp(int leaf){
            int parent = parentOf(leaf);
            
            //value yang tadi di leaf, kita simpan dulu ke temp
            Integer value = data.get(leaf);
            int now = leaf;
            
            //traverse dari leaf ke atas, maksimal nanti sampai root
            while(now > 0 && data.get(parent) > value){
                //data di parent digeser ke bawah
                data.setElementAt(data.get(parent), now);
                now = parent;
                parent = parentOf(now);
            }
            
            //sudah ketemu posisinya
            data.setElementAt(value, now);
        }

    public Integer peek(){
        return data.get(0);
    }
    
    public Integer remove(){
        Integer minVal = peek();
        
        //perbaiki heap nya supaya tetap complete tree
        data.setElementAt(data.get(data.size() - 1), 0);
        data.setSize(data.size() - 1);
        
        //maintain heap order dengan percolate down
        if(data.size() > 1) pushDownRoot(0);
        
        return minVal;
    }
    
        private void pushDownRoot(int root){
            //simpan si root yang sekarang di temp
            Integer value = data.get(root);
            
            //simpan heap size supaya mudah saat pengecekan berikutnya
            int heapSize = data.size();
            
            int now = root;// now ini seperti pointer current
            while(now < heapSize){
                //mesti cari jalur percolate down nya, mana yang lebih kecil
                //dari anak kiri atau anak kanan
                
                int leastChildPos = leftChildOf(now);
                if(leastChildPos < heapSize){
                    //cari anak kanan
                    int rightChildPos = leastChildPos + 1;
                    if(rightChildPos < heapSize && data.get(rightChildPos) < data.get(leastChildPos))
                        leastChildPos = rightChildPos;
                    
                    //di sini, kita sudah tau jalur percolate down nya ke kiri ataukah ke kanan, yaitu ke leastChildPos
                    //compare si least tadi dengan value
                    if(data.get(leastChildPos) < value){
                        //kalo lebih kecil dari si value, maka geser ke atas
                        data.setElementAt(data.get(leastChildPos), now);
                        now = leastChildPos; // si now turun lagi
                    }
                    else{// kasus ketika percolate down nya berhenti sebelum mencapai leaf
                        data.setElementAt(value, now);
                        return;
                    }
                        
                }
                else{//si now itu sudah merupakan leaf
                    data.setElementAt(value, now);
                    return;
                }
            }
        }
}