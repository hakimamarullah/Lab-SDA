// Java to shortest path from a given source vertex 's' to
// a given destination vertex 't'. Expected time complexity
// is O(V+E).
import java.util.*;

class GFG
{

	// This class represents a directed graph using adjacency
	// list representation
	static class Graph
	{
		int V; // No. of vertices
		Vector<Integer>[] adj; // No. of vertices

		static int level;
		static int node;

		// Constructor
		@SuppressWarnings("unchecked")
		Graph(int V)
		{
			this.V = V;
			this.adj = new Vector[2 * V];

			for (int i = 0; i < 2 * V; i++)
				this.adj[i] = new Vector<>();
		}

		// adds an edge
		public void addEdge(int v, int w, int weight)
		{

			// split all edges of weight 2 into two
			// edges of weight 1 each. The intermediate
			// vertex number is maximum vertex number + 1,
			// that is V.
			weight = weight+1;
			if (weight == 2)
			{
				adj[v].add(v + this.V);
				adj[v + this.V].add(w);
			} else // Weight is 1
				adj[v].add(w); // Add w to v's list.
		}

		// print shortest path from a source vertex 's' to
		// destination vertex 'd'.
		public int[] printShortestPath(int[] parent, int s, int d)
		{
			level = 0;
			node =0;

			// If we reached root of shortest path tree
			if (parent[s] == -1)
			{
				System.out.printf("Shortest Path between "+
								"%d and %d is %s ", s, d, s);
				node++;
				return new int[]{level, node};
			}

			printShortestPath(parent, parent[s], d);

			level++;
			if (s <= this.V){
				node++;
				System.out.printf("%d ", s);}

			return new int[]{level, node};
		}

		// finds shortest path from source vertex 's' to
		// destination vertex 'd'.

		// This function mainly does BFS and prints the
		// shortest path from src to dest. It is assumed
		// that weight of every edge is 1
		public int[] findShortestPath(int src, int dest)
		{
			boolean[] visited = new boolean[2 * this.V];
			int[] parent = new int[2 * this.V];

			// Initialize parent[] and visited[]
			for (int i = 0; i < 2 * this.V; i++)
			{
				visited[i] = false;
				parent[i] = -1;
			}

			// Create a queue for BFS
			Queue<Integer> queue = new LinkedList<>();

			// Mark the current node as visited and enqueue it
			visited[src] = true;
			queue.add(src);

			while (!queue.isEmpty())
			{

				// Dequeue a vertex from queue and print it
				int s = queue.peek();

				if (s == dest){
					//System.out.println("DONE");
					return printShortestPath(parent, s, dest);
				}
				queue.poll();

				// Get all adjacent vertices of the dequeued vertex s
				// If a adjacent has not been visited, then mark it
				// visited and enqueue it
				for (int i : this.adj[s])
				{
					if (!visited[i])
					{
						visited[i] = true;
						queue.add(i);
						parent[i] = s;
					}
				}
			}
			return new int[]{0,0};
		}
	}

	// Driver Code
	public static void main(String[] args)
	{

		// Create a graph given in the above diagram
		int V = 9;
		Graph g = new Graph(V);
		g.addEdge(1, 2, 0);
g.addEdge(2, 3, 0);
g.addEdge(3, 4, 0);
g.addEdge(4, 1, 0);
g.addEdge(4, 5, 1);
g.addEdge(6, 5, 1);
g.addEdge(5, 7, 0);
g.addEdge(7, 9, 1);
g.addEdge(9, 8, 0);
g.addEdge(7, 8, 0);

		int src = 1, dest = 9;
		int[] res = g.findShortestPath(src, dest);
		int node = res[1]-1;
		int distance = res[0];

		for(int i=distance/2; i>=0; i--){
			try{
				if(i*2 + (node-i)*1 == distance){
					System.out.println("\nJUMLAH TOL " + i);
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
		
	}
}

// This code is contributed by
// sanjeev2552
