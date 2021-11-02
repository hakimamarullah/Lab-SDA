/* Tuliskan Nama, NPM, dan Kelasmu di sini:
** Nama Lengkap: Hakim Amarullah
** NPM         : 1906293051
** Kelas       : A
*/


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Scanner;

/**
 *
 * @author solic
 */


public class Soal01 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
		
        DLinkedList theList = new DLinkedList();
        
        char[] input = sc.next().toCharArray();
        
        theList.loadDataToList(input);
        theList.mid = theList.findMid();
        System.out.println(theList); //cetak theList jika ingin mengecek isi linked list-nya
        specificPrint(theList);
    }
    
    /*given method*/
    static void specificPrint(DLinkedList theList){
        
        /*cetak dari mid ke awal*/
        MyIterator midItr = new MyIterator(theList);
        midItr.toMid();
        while(midItr.isInList()){
            System.out.print(midItr.getData());
            midItr.retreat();
        }
        
        /*cetak dari last ke sebelum mid*/
        MyIterator backItr = new MyIterator(theList);
        backItr.toLast();        
        while(backItr.isInList() && backItr.current != theList.mid){
            System.out.print(backItr.getData());
            backItr.retreat();
        }
    }
}
class DLinkedList{
    DNode header = new DNode();
    DNode mid;
    DNode last;
    int size;
    
    /*Method TO DO*/
    void insertLast(char data){
        /*
        *Tulis code kamu di sini
        */
        if(header.next == null){
            header.next = new DNode(data, header, null);
            last = header.next;
            mid = header.next;
            size++;
        }
        else{
            DNode tmp = new DNode(data);
            tmp.next = null;
            tmp.prev = last;
            last.next = tmp;
            last = tmp;
            size++;

        }
    }
    DNode findMid(){
        DNode node1 = header;
        DNode node2= header;
        while(node1 != null && node1.next != null){
            node1 = node1.next.next;
            node2= node2.next;
        }
        if(this.size%2 == 0 && this.size > 5){
            return node2;
        }
        if(this.size%2 == 0){
            return node2.next;
        }
        
        return node2;
    }
    
    void loadDataToList(char[] inputList){
        for(char i : inputList){
            insertLast(i);
        }
    }
    
    boolean isEmpty(){
        return header.next == null;
    }
    
    @Override
    public String toString(){
        if(header.next != null)
            return header.next.toString();
        return "-";
    }
}

class DNode{
    char data;
    DNode prev;
    DNode next;
    
    DNode(){}
    
    DNode(char data){this.data = data;}
    
    DNode(char data, DNode prev, DNode next){
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
    
    @Override
    public String toString(){
        String result = "" + data;
        if(next != null)
            result += next.toString();
        return result;
    }
}

class MyIterator{
    DLinkedList theList;
    DNode current;
    
    MyIterator(DLinkedList theList){
        this.theList = theList;
        toFirst();
    }
    
    boolean isInList(){
        return current != null && current != theList.header;
    }
    
    char getData(){
        if(isInList())
            return current.data;
        return '-';
    }
    
    void toFirst(){
        if(!theList.isEmpty())
            current = theList.header.next;
    }
    
    void toLast(){
        if(!theList.isEmpty())
            current = theList.last;
    }
    
    void toMid(){
        if(!theList.isEmpty())
            current = theList.mid;
    }
    
    void advance(){
        current = current.next;
    }
    
    void retreat(){
        current = current.prev;
    }
}