/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.mst.prim.live;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author solic
 */
public class PrimTest {
    public static void main(String[] args){
        Graph graf = new Graph(8); 
        //node 0 abaikan
        //node 0, 1, 2, dst ... 9

        //inisiasi nodes dan edge nya
        graf.addEdge(1,2,2);
        graf.addEdge(1,3,4);
        graf.addEdge(1,4,1);
        graf.addEdge(2,4,3);
        graf.addEdge(2,5,10);
        graf.addEdge(3,4,2);
        graf.addEdge(3,6,5);
        graf.addEdge(4,5,7);
        graf.addEdge(4,6,8);
        graf.addEdge(4,7,4);
        graf.addEdge(5,7,6);
        graf.addEdge(6,7,1);
        
        graf.cetakMSTusingPrim();
    }
}

class Graph{
    int numberOfVertex;
    Vertex[] vertexList;
    
    Graph(int num){
        this.numberOfVertex = num;
        
        vertexList = new Vertex[num];
        
        for(int i = 0; i < num; i++)
            vertexList[i] = new Vertex(i);
    }
    
    void addEdge(int from, int to, int weight){
        vertexList[from].edgeList.add(new Edge(to, weight));
        vertexList[to].edgeList.add(new Edge(from, weight));
    }

    void cetakMSTusingPrim() {
        Queue<Vertex> myPQueue = new PriorityQueue();
        
        vertexList[1].cost = 0;
        myPQueue.add(vertexList[1]);
        
        for(int known = 1; known < numberOfVertex; known++){
            Vertex v = myPQueue.poll();
            v.flag = 1;//si v masuk ke MST
            
            if(known > 1)
                System.out.println(v.pred + "-" + v.node);
            
            //expand si v
            for(Edge e : v.edgeList){
                Vertex w = vertexList[e.dest];
                
                if(w.flag == 0 && (e.weight < w.cost)){
                    w.cost = e.weight;
                    w.pred = v.node;
                    
                    myPQueue.remove(w);
                    myPQueue.add(w);
                }
            }
        }
    }
    
    
}

class Vertex implements Comparable{
    Integer node;
    int pred;
    int cost;//dv
    int flag;
        //0 dan belum di-enqueue: black area
        //0 tapi sudah di-enqueue: grey area
        //1: white cloud
    
    List<Edge> edgeList;
    
    Vertex(int i){
        node = i;
        pred = -1;
        cost = Integer.MAX_VALUE;
        flag = 0;
        edgeList = new ArrayList();
    }
    
    @Override
    public int compareTo(Object o) {
        int thatCost = ((Vertex)o).cost;
        return cost < thatCost ? -1 :
                cost > thatCost ? 1 : 0;
    }
}

class Edge{
    int dest;
    int weight;
    
    Edge(int dest, int weight){
        this.dest = dest;
        this.weight = weight;
    }
}



    

