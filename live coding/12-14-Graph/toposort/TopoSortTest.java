/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.toposort.live;

import java.util.*;

/**
 *
 * @author solic
 */
public class TopoSortTest {
    public static void main(String[] args){
        Graph graf = new Graph(7);
        
        graf.addEdge(0, 1);
        graf.addEdge(0, 3);
        graf.addEdge(1, 3);
        graf.addEdge(1, 4);
        graf.addEdge(2, 0);
        graf.addEdge(2, 3);
        graf.addEdge(2, 5);
        graf.addEdge(3, 4);
        graf.addEdge(3, 5);
        graf.addEdge(3, 6);
        graf.addEdge(4, 6);
        graf.addEdge(6, 5);
        
        graf.cetakTopoSort();
    }
}

class Graph{
    int numberOfVertex;
    Vertex[] vertexList;// Vertex[] 
    
    Graph(int num){
        this.numberOfVertex = num;
        vertexList = new Vertex[num];
                
        for(int i = 0; i < num; i++)
            vertexList[i] = new Vertex(i);
    }
    
    void addEdge(int v, int w){
        vertexList[v].adj.add(w);
        vertexList[v].outDeg++;
        vertexList[w].inDeg++;
    }
    
    void cetakTopoSort(){
        //copy grafnya
        Graph graf2 = new Graph(numberOfVertex);
        copyGraph(graf2);
        
        //antrikan ke priority queue
        Queue<Vertex> topoQueue = new PriorityQueue();
        for(Vertex v : graf2.vertexList)
            topoQueue.add(v);
        
        //dequeue v dan update inDeg dari semua adjacent vertices-nya
        while(!topoQueue.isEmpty()){
            Vertex now = topoQueue.poll();
            System.out.print(now.label + " -> ");
            
            for(int w : now.adj){
                graf2.vertexList[w].inDeg--;
                now.outDeg--;
                
                topoQueue.remove(graf2.vertexList[w]);
                topoQueue.add(graf2.vertexList[w]);
            }
        }
    }
    
    void copyGraph(Graph graf){
        for(int i = 0; i < numberOfVertex; i++){
            Vertex v = vertexList[i];
            graf.vertexList[i] = new Vertex(v.label, v.inDeg, v.outDeg, v.adj);
        }
    }
}

class Vertex implements Comparable{
    int label;
    int inDeg;
    int outDeg;
    List<Integer> adj;
    
    Vertex(int label){
        this.label = label;
        adj = new ArrayList();
    }
    
    Vertex(int label, int inDeg, int outDeg, List<Integer> adj){
        this.label = label;
        this.inDeg = inDeg;
        this.outDeg = outDeg;
        this.adj = new ArrayList();
        
        for(int w : adj)
            this.adj.add(w);
    }
    
    @Override
    public int compareTo(Object o) {
        int thatInDeg = ((Vertex)o).inDeg;
        return inDeg < thatInDeg ? -1 :
                inDeg > thatInDeg ? 1 : 0;
    }
}