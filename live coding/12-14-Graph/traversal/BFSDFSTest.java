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
public class BFSDFSTest {
    
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
//        graf.cetakBFS(2);
//        
//        System.out.println("\npath dari 2 ke 5");
//        graf.cetakPath(5);//dari source yang tadi yaitu 2
        
        graf.cetakDFS(2);
        System.out.println("\npath dari 2 ke 5");
        graf.cetakPath(5);//dari source yang tadi yaitu 2
    }
}

class Graph{
    int numberOfVertex;
    AdjacencyList[] adjacencyData;
    int[] pred;
    
    Graph(int num){
        this.numberOfVertex = num;
        
        adjacencyData = new AdjacencyList[num];
        pred = new int [numberOfVertex];
        
        for(int i = 0; i < num; i++)
            adjacencyData[i] = new AdjacencyList(i);
    }
    
    void cetakPath(int now){
        if(pred[now] != -1)
            cetakPath(pred[now]);
        System.out.print(now + " - ");
    }
    
    void cetakBFS(int s){
        int[] flagTable = new int[numberOfVertex];
        int[] jarakDariSource = new int[numberOfVertex];
        
        for(int i = 0; i < numberOfVertex; i++)
            pred[i] = -1;
        
        Queue<Integer> myQueue = new LinkedList();
        
        myQueue.add(s);
        flagTable[s] = 1;
        jarakDariSource[s] = 0;//ga usah gapapa
        
        while(myQueue.isEmpty() == false){
            Integer now = myQueue.poll();
            System.out.print( now + " (" + jarakDariSource[now] + ") - ");
            
            //cari semua yang adjacent dengan now
            for(Integer dest : adjacencyData[now].destList){
                if(flagTable[dest] == 0){
                    myQueue.add(dest);
                    flagTable[dest] = 1;
                    jarakDariSource[dest] = jarakDariSource[now] + 1;
                    pred[dest] = now;
                }
            }
        }
    }
    
    void cetakDFS(int s){
        //flagtable
        int[] flagTable = new int[numberOfVertex];
        
        for(int i = 0; i < numberOfVertex; i++)
            pred[i] = -1;
        
        cetakDFSRek(flagTable, s);
    }
    
    void cetakDFSRek(int[] flagTable, int now){
        flagTable[now] = 1;
        System.out.print( now + " - ");
        
        for(Integer dest : adjacencyData[now].destList){
            if(flagTable[dest] != 1){
                pred[dest] = now;
                cetakDFSRek(flagTable, dest);
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