/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package first.sda.LL;

/**
 *
 * @author solic
 */
public class LinkedListTestLive {
    public static void main(String[] args){
        LinkedList myLL = new LinkedList();
        
        //isi linked list nya dengan node 5
        for(int i = 0; i < 10; i++)
            myLL.addFirst(i);
        
        myLL.cetak();
        System.out.println(listSize(myLL));
        
        System.out.println(listSizeByIterator(myLL));
    }
    
    //by Tegar
    public static int listSize(LinkedList theList){
        int counter = 0;
        Node nextPointer = theList.header.next;
        while(nextPointer != null) {
            counter++;
            nextPointer = nextPointer.next;
        }
        return counter;
    }
    
    public static int listSizeByIterator(LinkedList theList){
        int counter = 0;
        //Node nextPointer = theList.header.next; //current menunjuk ke Node pertama (bukan header)
        
        //pakai iterator
        LLItr myItr = new LLItr(theList);
        
        while(myItr.isInList()) {
            counter++;
            myItr.advance();
        }
        return counter;
    }
}

class LLItr{
    LinkedList theList;
    Node current;
    
    public LLItr(LinkedList theList){
        this.theList = theList;
        first();
    }
    
    public boolean isInList(){
        return current == null ? false : true;
    }
    
    public void first(){
        if(theList.isEmpty() == false)
            current = theList.header.next;
    }
    
    public void advance(){
        current = current.next;
    }
    
    
}

class LinkedList{
    Node header;
    //private Node first; //Node pertama
    
    public LinkedList(){
        //first = null;
        header = new Node(0,null);
    }
    
    public boolean isEmpty(){
        return header.next == null ? true : false;
    }
    
    public void addFirst(int data){
        //Node tmp = new Node(data);
        //tmp.next = first;
        //first = tmp;
        
        //jadi dua baris
        //Node tmp = new Node(data, first);
        //first = tmp;
        
        //sebaris aja
        //first = new Node(data, first);
        
        header.next = new Node(data, header.next);
    }
    
    public void cetak(){
        if(header.next != null) header.next.cetak();
        System.out.println("");
    }
}

class Node{
    int data;
    Node next;
    
    Node(){}
    
    Node(int data){
        this.data = data;
        next = null;
    }
    
    Node(int data, Node next){
        this.data = data;
        this.next = next;
    }
    
    public void cetak(){
        //cetak datanya sendiri
        System.out.print(" " + data + " ");
        
        //panggil method cetak node berikutnya
        if(next != null) next.cetak();
    }
}

