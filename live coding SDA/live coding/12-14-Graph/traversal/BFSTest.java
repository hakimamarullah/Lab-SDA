/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.bfs.live;

import java.util.*;

/**
 *
 * @author solic
 */
public class BFSTest {
    
    public static void main(String[] args){
        Graph graf = new Graph(10); //node 0, 1, 2, dst ... 9

        //inisiasi nodes dan edge nya
        graf.adjacencyData[0].destList.add(8);
        
        int[] destList = new int[]{3,7,9,2};
        for(int dest : destList)
            graf.adjacencyData[1].destList.add(dest);

        destList = new int[]{8,1,4};
        for(int dest : destList)
            graf.adjacencyData[2].destList.add(dest);
        
        destList = new int[]{4,5,1};
        for(int dest : destList)
            graf.adjacencyData[3].destList.add(dest);
        
        destList = new int[]{2,3};
        for(int dest : destList)
            graf.adjacencyData[4].destList.add(dest);
        
        destList = new int[]{3,6};
        for(int dest : destList)
            graf.adjacencyData[5].destList.add(dest);
        
        destList = new int[]{7,5};
        for(int dest : destList)
            graf.adjacencyData[6].destList.add(dest);
        
        destList = new int[]{1,6};
        for(int dest : destList)
            graf.adjacencyData[7].destList.add(dest);
        
        destList = new int[]{2,0,9};
        for(int dest : destList)
            graf.adjacencyData[8].destList.add(dest);
        
        destList = new int[]{1,8};
        for(int dest : destList)
            graf.adjacencyData[9].destList.add(dest);
        
        //panggil cetakBFS
        graf.cetakBFS(2);
    }
}

class Graph{
    int numberOfVertex;
    AdjacencyList[] adjacencyData;
    
    Graph(int num){
        this.numberOfVertex = num;
        
        adjacencyData = new AdjacencyList[num];
        
        for(int i = 0; i < num; i++)
            adjacencyData[i] = new AdjacencyList(i);
    }
    
    void cetakBFS(int s){
        int[] flagTable = new int[numberOfVertex];
        
        Queue<Integer> myQueue = new LinkedList();
        
        myQueue.add(s);
        flagTable[s] = 1;
        
        while(myQueue.isEmpty() == false){
            Integer now = myQueue.poll();
            System.out.print( now + " ");
            
            //cari semua yang adjacent dengan now
            for(Integer dest : adjacencyData[now].destList){
                if(flagTable[dest] == 0){
                    myQueue.add(dest);
                    flagTable[dest] = 1;
                }
            }
        }
    }
}

class AdjacencyList{
    Integer node;
    List<Integer> destList;
    
    AdjacencyList(int i){
        node = i;
        destList = new ArrayList();
    }
}