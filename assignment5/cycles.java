import java.util.*;


class Graph {

	private int noOfVertex;
	private ArrayList<Integer> adjList[];
	boolean directed;

	
	public Graph(int noOfVertex, boolean directed) {
		this.noOfVertex = noOfVertex;
		this.directed = directed;

		adjList = new ArrayList[noOfVertex+1];

		for(int i=1; i<=noOfVertex; ++i)
			adjList[i] = new ArrayList<Integer>();
	}


	public ArrayList<Integer>[] getAdjList() {
		return adjList;
	}

	
	public void addEdge(int u, int v) {
		adjList[u].add(v);
		if(!directed)
			adjList[v].add(u);
	}

	
	public void printGraph() {
		for(int i=1; i<=noOfVertex; ++i) {
			System.out.print(i + " -> ");
			for(int j=0; j<adjList[i].size(); ++j) {
				System.out.print(" " + adjList[i].get(j) + " ");
			}
			System.out.println();
		}
	}

	public void getAllCycles() {

	}

}


class CycleUtility{


	// graph variables
	private ArrayList<Integer> adjList[];
	private Integer noOfVertex;

	
	// cycle finding variables
	private Boolean visited[];
	private ArrayList<ArrayList<Integer>> allCycles;
	private ArrayList<ArrayList<Integer>> filteredCycles;


	public CycleUtility(ArrayList<Integer>[] adjList) {
		this.adjList = adjList;
		noOfVertex = adjList.length-1;
		visited = new Boolean[noOfVertex+1];

		allCycles = new ArrayList<ArrayList<Integer>>();
		filteredCycles = new ArrayList<ArrayList<Integer>>();

		Arrays.fill(visited, Boolean.FALSE);
	}


	public void findAllCycles() {
		for(int i=1; i<=noOfVertex; ++i) {
			ArrayList<Integer> cycle = new ArrayList<Integer>();

			findCycle(i, cycle, i);
		}


		System.out.println(allCycles.size());
		for(int i=0; i<allCycles.size(); ++i) {
			for(int j=0; j<allCycles.get(i).size(); ++j){
				System.out.print(allCycles.get(i).get(j) + "  ");
			}
		}
		//printCycles(allCycles);
	}


	public void findCycle(Integer fromNode, ArrayList<Integer> cycle, Integer current) {
		cycle.add(current);

		//for(int i=0; i<cycle.size(); ++i) {
		//	System.out.print(cycle.get(i)+"  ");
		//}
		//System.out.println();

		visited[current] = true;

		for(int i=0; i<adjList[current].size(); ++i) {

			//System.out.println(adjList[current].get(i) + "  " + current);

			if(adjList[current].get(i) == fromNode) {
				//System.out.println("here");
				for(int k=0; k<cycle.size(); ++k) {
					System.out.print(cycle.get(k)+"  ");
				}
				System.out.println();
				allCycles.add(cycle);
			}

			else{
				//System.out.println("else");
				//System.out.println(adjList[current].get(i));
				//System.out.println(visited.length);
				if(!visited[adjList[current].get(i)]) {
					//System.out.println("inside if else");
					findCycle(fromNode, cycle, adjList[current].get(i));
				}
			}
		}

		visited[current] = false;
		cycle.remove(cycle.size()-1);

	}

	public void printCycles(ArrayList<ArrayList<Integer>> cycles) {
		for(int i=0; i<cycles.size(); ++i) {
			for(int j=0; j<cycles.get(i).size(); ++j){
				System.out.print(cycles.get(i).get(j) + "  ");
			}
		}

		System.out.println();
	}
	
}


public class cycles {

	public static void main (String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter the no of vertex");
		Integer n = scan.nextInt();

		Graph graph = new Graph(n, true);

		while(true) {
			System.out.println("Enter the edge. Enter -1 to abort");
			Integer u = scan.nextInt();
			if(u == -1)
				break;
			Integer v = scan.nextInt();
			graph.addEdge(u, v);
		}

		graph.printGraph();

		// feature test working fine
		ArrayList<Integer>[] adjList = graph.getAdjList();


		CycleUtility cycleUtility = new CycleUtility(adjList);
		//cycleUtility.printGraph();
		
		cycleUtility.findAllCycles();

	}
}

 
















