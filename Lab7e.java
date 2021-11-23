import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;


public class Lab7e {
    private static InputReader in;
    public static OutputStream outputStream = System.out;
    public static PrintWriter out = new PrintWriter(outputStream);
    public static Map<String,Integer> edge = new HashMap<String,Integer>();
    public static boolean identic = true;

   
    
    // TODO: method to initialize graph
    public static void createGraph(int N) {

    }

    // TODO: method to create an edge with type==T that connects vertex U and vertex V in a graph

    // TODO: Handle teman X Y K
    public static int canMudik(int X, int Y, int K) {
      return 1;
    }

    public static void addEdge(int src, int dest, int tipe){
        edge.put(Integer.toString(src) + Integer.toString(dest), tipe);
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);

       

        int N = in.nextInt();
        int M = in.nextInt();
        int Q = in.nextInt();
        Graph cityMap = new Graph(N);

     
        
        for (int i=0;i < M;i++) {
            int U = in.nextInt();
            int V = in.nextInt();
            int T = in.nextInt();
            cityMap.adjacencyData[U].destList.add(V);
            addEdge(U, V, T);
            identic = identic && T == 1 ? true : false;
            cityMap.adjacencyData[V].destList.add(U);
            addEdge(V, U, T);


         
            
        }
        while(Q-- > 0) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int K = in.nextInt();
            if(identic){cityMap.cetakBFS(X,Y, K);
             out.println(cityMap.cetakPath(X, Y,K));}
          
            else{
                cityMap.cetakBFS2(X,Y,K);
            out.println(cityMap.cetakPath2(X, Y,K));}
            
         
        }

        out.flush();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
    static class Graph{
    int numberOfVertex;
    AdjacencyList[] adjacencyData;
    int[] pred;
    
    Graph(int num){
        this.numberOfVertex = num + 1;
        
        adjacencyData = new AdjacencyList[numberOfVertex];
        pred = new int [numberOfVertex];
        
        for(int i = 0; i < numberOfVertex; i++)
            adjacencyData[i] = new AdjacencyList(i);
    }
    
    void cetakPathHelper(ArrayList<Integer> path, int now){
        if(pred[now] != -1)
            cetakPathHelper(path, pred[now]);
        path.add(now);
        // Lab7e.out.print(now + " - ");
    }
     int cetakPath2(int src, int dest, int tiket){
        ArrayList<Integer> path = new ArrayList<Integer>();
        cetakPathHelper(path, dest);
        if(path.size() <= 1){
            return 0;
        }

        else if(path.get(0) == src && path.get(path.size()-1) == dest){

            return 1;
        }
        return 0;

    }
  
    int cetakPath(int src, int dest, int tiket){
        ArrayList<Integer> path = new ArrayList<Integer>();
        cetakPathHelper(path, dest);
        if(path.size() <= 1){
            return 0;
        }

        else if(path.get(0) == src && path.get(path.size()-1) == dest){
            if(path.size()-1 > tiket){
                return 0;
            }
            return 1;
        }
        return 0;

    }
   
    
    void cetakBFS(int s, int dst, int tiket){
        int[] flagTable = new int[numberOfVertex];
        int[] jarakDariSource = new int[numberOfVertex];
        
        for(int i = 0; i < numberOfVertex; i++)
            pred[i] = -1;
        
        Queue<Integer> myQueue = new LinkedList<Integer>();
        
        myQueue.add(s);
        flagTable[s] = 1;
        jarakDariSource[s] = 0;//ga usah gapapa
        Integer now = s;
        while(myQueue.isEmpty() == false){
            now = myQueue.poll();
            //Lab7e.out.print( now + " (" + jarakDariSource[now] + ") - ");
            //Collections.sort(adjacencyData[now].destList);
            //cari semua yang adjacent dengan now
            for(Integer dest : adjacencyData[now].destList){
                //Lab7e.out.println("DEBUG " + adjacencyData[now].destList);
                if(flagTable[dest] == 0){
                    // if(tiket - Lab7e.edge.get(Integer.toString(now)+Integer.toString(dest)) < 0){
                    //     //myQueue.add(dest);
                    //     continue;
                    // }
                    myQueue.add(dest);
                    flagTable[dest] = 1;
                    jarakDariSource[dest] = jarakDariSource[now] + 1;
                    pred[dest] = now;
                    String tmp = dest+""+dst;
                    String tmp2 = now+""+dst;
                    // if(Lab7e.edge.get(tmp) == null && Lab7e.edge.get(tmp2) != null){
                    //     continue;
                    // }
                    // else if(Lab7e.edge.get(tmp) == null && Lab7e.edge.get(tmp2) == null){
                    //     continue;
                    // }
                    //tiket = tiket - Lab7e.edge.get(Integer.toString(now)+Integer.toString(dest));
                }
            }
        }
        // Lab7e.out.print("");
        // Lab7e.out.print(Arrays.toString(pred));
        // Lab7e.out.println(Arrays.toString(jarakDariSource));
        //cetakPath(dst);
    }
    
    void cetakBFS2(int s, int dst, int tiket){
        int[] flagTable = new int[numberOfVertex];
        int[] jarakDariSource = new int[numberOfVertex];
        
        for(int i = 0; i < numberOfVertex; i++)
            pred[i] = -1;
        
        Queue<Integer> myQueue = new LinkedList<Integer>();
        
        myQueue.add(s);
        flagTable[s] = 1;
        jarakDariSource[s] = 0;//ga usah gapapa
        Integer now = s;
        while(myQueue.isEmpty() == false){
            now = myQueue.poll();
            
            for(Integer dest : adjacencyData[now].destList){
              

                if(flagTable[dest] == 0){
                    if(tiket - Lab7e.edge.get(Integer.toString(now)+Integer.toString(dest)) < 0){
                        
                        continue;
                    }
                    myQueue.add(dest);
                    flagTable[dest] = 1;
                    jarakDariSource[dest] = jarakDariSource[now] + 1;
                    pred[dest] = now;
                    String tmp = dest+""+dst;
                    String tmp2 = now+""+dst;
                    if(Lab7e.edge.get(tmp) == null && Lab7e.edge.get(tmp2) != null){
                        continue;
                    }
                    else if(Lab7e.edge.get(tmp) == null && Lab7e.edge.get(tmp2) == null){
                        continue;
                    }
                    tiket = tiket - Lab7e.edge.get(Integer.toString(now)+Integer.toString(dest));
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
        Lab7e.out.println();
    }
    
    void cetakDFSRek(int[] flagTable, int now){
        flagTable[now] = 1;
        //Lab7e.out.print( now + " - ");
        
        for(Integer dest : adjacencyData[now].destList){
            if(flagTable[dest] != 1){
                pred[dest] = now;
                cetakDFSRek(flagTable, dest);
            }
        }
    }
    
}

    static class AdjacencyList{
    Integer node;
    List<Integer> destList;
    
    AdjacencyList(int i){
        node = i;
        destList = new ArrayList<Integer>();
    }
}
}