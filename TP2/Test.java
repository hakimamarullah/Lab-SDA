/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author solic
 */
public class Test {
    public static void main(String[] args){
        Graph graf = new Graph(7);
    
        graf.addEdge(0,1,2);
        graf.addEdge(0,3,1);
        graf.addEdge(1,3,3);
        graf.addEdge(1,4,10);
        graf.addEdge(2,0,5);
        graf.addEdge(2,3,2);
        graf.addEdge(2,5,5);
        graf.addEdge(3,4,2);
        graf.addEdge(3,5,8);
        graf.addEdge(3,6,4);
        graf.addEdge(4,6,1);
        graf.addEdge(6,5,1);
        
        graf.setDistanceUsingDijkstra(2);
        graf.cetakDistances();
        System.out.println("shortest path dari 2 ke 6");
        graf.cetakPath(6);
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
    
    void cetakDistances(){
        for(Vertex v : vertexList)
            System.out.println("vertex " + v.node + ": " + v.dist);
    }
    
    void addEdge(int from, int to, int weight){
        vertexList[from].edgeList.add(new Edge(to, weight));
    }
    
    void cetakPath(int now){
        if(vertexList[now].pred != -1)
            cetakPath(vertexList[now].pred);
        System.out.print(now + " - ");
    }
    
    //return true kalau berhasil,
    //return false kalau ada edge yang negatif
    boolean setDistanceUsingDijkstra(int s){       
        Queue<Path> myPQueue = new PriorityQueue<Path>();
        
        myPQueue.add(new Path(s,0));
        vertexList[s].dist = 0;
        
        for(int whites = 0; whites < numberOfVertex ; whites++){
            //vertex yang sudah di area hijau/white cloud
            //harus di-dequeue
            Path currentPath;
            int now;
            
            //dequeue terus sampai dapat vertex yang belum masuk area hijau
            do{
                if(myPQueue.isEmpty() == true)
                    return true;
                currentPath = myPQueue.poll();
                now = currentPath.dest;
            }
            while(vertexList[now].flag == 1);
        
            vertexList[now].flag = 1; //masukkan ke area hijau
            
            //cari semua yang adjacent dengan now
            for(Edge e : vertexList[now].edgeList){
                int oldDist = vertexList[e.dest].dist;
                int newDist = vertexList[now].dist + e.weight;
                
                if(newDist < oldDist){
                    vertexList[e.dest].dist = newDist;
                    vertexList[e.dest].pred = now;
                    myPQueue.add(new Path(e.dest, newDist));
                }
            }
        }
        return true;
    }
}

class Vertex{
    Integer node;
    List<Edge> edgeList;
    int dist;
    int flag;
    int pred;
        //0 dan belum dimasukkan ke queue: black area
        //0 dan sudah dimasukkan ke queue: grey area
        //1 sudah di white cloud (area hijau di slide)
    
    Vertex(int i){
        node = i;
        dist = Integer.MAX_VALUE;
        flag = 0;
        pred = -1;
        edgeList = new ArrayList<Edge>();
    }
}

class Edge{
    Integer dest;
    int weight;

    Edge(int to, int weight) {
        this.dest = to;
        this.weight = weight;
    }
}

class Path implements Comparable<Path>{
    Integer dest;
    int cost;
    
    public Path(int dest, int cost){
        this.dest = dest;
        this.cost = cost;
    }

    @Override
    public int compareTo(Path o) {

        if(this.cost > o.cost)
            return 1;
        else if(this.cost < o.cost){
            return -1;
        }
        else
            return 0;
    }
}