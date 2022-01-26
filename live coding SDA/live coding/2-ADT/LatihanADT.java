/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package first.sda;

import java.util.*;

/**
 *
 * @author solic
 */
public class LatihanADT {
    public static void main(String[] args){
        mapTest();
    }
    
    public static void mapTest(){
        Map<String,String> myMap = new HashMap<>();
        
        myMap.put("a", "apple");
        myMap.put("b", "blueberry");
        myMap.put("b", "blackberry");
        
        System.out.println(myMap.get("b"));
        System.out.println(myMap.get("b1"));
    }
    
    public static void queueTest(){
        //Queue myQ = new LinkedList();
        Queue myQ = new PriorityQueue();
        
        myQ.add("b");
        myQ.add("a");
        myQ.add("c");
        
        System.out.println(myQ);
        
        while(myQ.isEmpty() == false)
            System.out.println(myQ.poll());
    }
    
    public static void stackTest(){
        Stack myStack = new Stack();
        
        myStack.push("apple");
        myStack.push("cherry");
        myStack.push("cherry");
        myStack.push("blueberry");
        
        System.out.println(myStack.peek());
        
        while(myStack.isEmpty() == false)
            System.out.println(myStack.pop());
        
        myStack.pop();
    }
    
    public static void setTest(){
        //Set mySet = new HashSet();
        Set mySet = new TreeSet();
        
        mySet.add("apple");
        mySet.add("cherry");
        mySet.add("cherry");
        mySet.add("blueberry");
        
        System.out.println(mySet);
    }
    
    public static void listTest(){
        List myList = new ArrayList();
        
        myList.add("apple");//ke indeks 0
        myList.add("cherry");//ke indeks 1
        myList.add("cherry");//ke indeks 2
        //myList.remove(1);
        myList.add(1, "blueberry");
        
        System.out.println(myList);
    }
    
    
}

/*class Antrian implements Comparable{
    int jumlahHalaman;
    
    @Override
    public int compareTo(Object object){
        if(object.)
    }
}*/
