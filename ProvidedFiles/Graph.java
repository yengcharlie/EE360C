
import java.util.*;
/*
 * Name: Charlie Yeng
 * cy5438
 */

public class Graph implements Program2{
	// n is the number of ports

	private int n;
	Integer myInf = Integer.MAX_VALUE;
	Set<Vertex> shortestPaths;
    
	// Edge is the class to represent an edge between two nodes
	// node is the destination node this edge connected to
	// time is the travel time of this edge
	// capacity is the capacity of this edge
	// Use of this class is optional. You may make your own, and comment
	// this one out.
	private class Edge{
		public int node;
		public int time;
		public int capacity;
		public Edge(int n, int t, int c){
			node = n;
			time = t;
			capacity = c;
		}

		// prints out an Edge.
		public String toString() {
			return "" + node;
		}
	}
	List<List<Edge>> graphList;
	// Here you have to define your own data structure that you want to use
	// to represent the graph
	// Hint: This include an ArrayList or many ArrayLists?
	// ....


	// This function is the constructor of the Graph. Do not change the parameters
	// of this function.
	//Hint: Do you need other functions here?
	public Vertex extractMin(Set<Vertex> vertices){
		int min = myInf;
		Vertex returnVertex = new Vertex(0,0,null);
		for(Vertex vertex: vertices){
			if(vertex.getDistance()<min){
				min = vertex.getDistance();
				returnVertex = vertex;
			}
		}
		return returnVertex;
	}
	public Vertex extractMax(Set<Vertex> vertices){
		int max = -myInf;
		Vertex returnVertex = new Vertex(0,0,null);
		for(Vertex vertex: vertices){
			if(vertex.getDistance()>max){
				max = vertex.getDistance();
				returnVertex = vertex;
			}
		}
		return returnVertex;
	}

	public Set<Vertex> initialize(Graph graph){
		Set<Vertex> vertices = new LinkedHashSet<Vertex>();
		for(int i = 0; i<n; i++){
			vertices.add(new Vertex(i, myInf, null));
		}
		return vertices;
	}
	public Set<Vertex> initializeWide(Graph graph){
		Set<Vertex> vertices = new LinkedHashSet<Vertex>();
		for(int i = 0; i<n; i++){
			vertices.add(new Vertex(i, myInf*(-1), null));
		}
		return vertices;
	}

	public Vertex getVertex(int vertexNode, Set<Vertex> vertices){
		for(Vertex vertex: vertices){
			if(vertex.getNode() == vertexNode) {
				return vertex;
			}
		}
		return null;
	}

	public Graph(int x) {
		n = x;
		graphList = new ArrayList(n);
		for(int i = 0; i<n; i++){
			graphList.add(i, new ArrayList());
		}

	}
    
	// This function is called by Driver. The input is an edge in the graph.
	// Your job is to fix this function to generate your graph.
	// Do not change its parameters or return type.
	// Hint: Here is the place to build the graph with the data structure you defined.
	public void inputEdge(int port1, int port2, int time, int capacity) {
		Edge edge = new Edge(port2, time, capacity);
		graphList.get(port1).add(edge);
		edge = new Edge(port1, time, capacity);
		graphList.get(port2).add(edge);
		return;
	}

	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		shortestPaths = initialize(this);//initializes vertices distance to infinity
		for(Vertex vertex: shortestPaths) {
			if (vertex.getNode() == sourcePort) {
				vertex.setDistance(0);
				break;
			}
		}
			Set<Vertex> vertices = new HashSet<>(shortestPaths);
			while(!vertices.isEmpty()){
				Vertex u = extractMin(vertices);
				if(u.getDistance()==myInf){
					break;
				}
				vertices.remove(u);
				if(u.getNode() == destPort){
					break;
				}

				for(Edge edge: graphList.get(u.getNode()) ){
					int alt = u.getDistance() + edge.time;
					Vertex v = getVertex(edge.node, shortestPaths);
					if(alt<v.getDistance()){
						v.setDistance(alt);
						v.setPreviousVertex(u);
					}

				}

			}


		return getVertex(destPort, shortestPaths).getDistance();
	}

	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		shortestPaths = initializeWide(this);//initializes vertices distance to infinity
		for(Vertex vertex: shortestPaths) {
			if (vertex.getNode() == sourcePort) {
				vertex.setDistance(myInf);
				break;
			}
		}
		Set<Vertex> vertices = new HashSet<>(shortestPaths);
		while(!vertices.isEmpty()){
			Vertex u = extractMax(vertices);
			if(u.getDistance()==myInf*(-1)){
				break;
			}
			vertices.remove(u);
			if(u.getNode() == destPort){
				break;
			}

			for(Edge edge: graphList.get(u.getNode()) ){
				int alt;
				if(edge.capacity>u.getDistance())
					alt = u.getDistance();
				else
					alt = edge.capacity;
				Vertex v = getVertex(edge.node, shortestPaths);
				if(alt>v.getDistance()){
					v.setDistance(alt);
					v.setPreviousVertex(u);
				}

			}

		}


		return getVertex(destPort, shortestPaths).getDistance();
	}

	// This function returns the neighboring ports of node.
	// This function is used to test if you have contructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		List<Edge> adjacencyList = graphList.get(node);
		for(Edge edge: adjacencyList){
			edges.add(edge.node);
		}
		return edges;
	}

	public int getNumPorts() {
		return n;
	}
}
