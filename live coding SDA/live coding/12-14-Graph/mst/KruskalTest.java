/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.mst.kruskal.live;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author solic
 */
public class KruskalTest {
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
        
        graf.cetakMSTusingKruskal();
    }
}

class Graph{
    int numberOfVertex;
    Vertex[] vertexList;
    Queue<Edge> edgeList;
    
    Graph(int num){
        this.numberOfVertex = num;
        
        vertexList = new Vertex[num];
        edgeList = new PriorityQueue();
        
        for(int i = 0; i < num; i++)
            vertexList[i] = new Vertex(i);
    }
    
    void addEdge(int v, int w, int weight){
        Edge e = new Edge(v, w, weight);
        edgeList.add(e);
        
        vertexList[v].edgeList.add(e);
        vertexList[w].edgeList.add(e);
    }

    void cetakMSTusingKruskal() {
        Graph graf2 = new Graph(8);
        UnionFindDisjointSet mySet = new UnionFindDisjointSet(numberOfVertex);
        
        int edgeCounter = 1;//gara-gara kita abaikan verteks 0
        
        while(edgeCounter < numberOfVertex-1){
            Edge e = edgeList.poll();
            
            
            //cek apakah e akan membuat cycle
            if(mySet.isSameSet(e.v, e.w) == false){//kalau tidak membuat cycle
                System.out.println(e.label);
                graf2.addEdge(e.v, e.w, e.weight);
                mySet.unionSet(e.v, e.w);
                edgeCounter++;
            }
        }
    }
    
    class UnionFindDisjointSet{
        int parent[];
        
        UnionFindDisjointSet(int size){
            parent = new int[size];
            
            for(int i = 0; i < numberOfVertex; i++)
                parent[i] = i;
        }
        
        int findSet(int v){
            if(parent[v] == v)
                return v;
            return parent[v] = findSet(parent[v]);
        }
        
        boolean isSameSet(int v, int w){
            return findSet(v) == findSet(w);
        }
        
        void unionSet(int v, int w){
            parent[findSet(v)] = findSet(w);
        }
    }
    
    
}

class Vertex{
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
}

class Edge implements Comparable{
    int v;
    int w;
    int weight;
    String label;
    
    Edge(int v, int w, int weight){
        label = v + "-" + w;
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    
    @Override
    public int compareTo(Object o) {
        int thatWeight = ((Edge)o).weight;
        return weight < thatWeight ? -1 :
                weight > thatWeight ? 1 : 0;
    }
}



    

