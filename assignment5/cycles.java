import java.util.*;


class SortByArrayLength implements Comparator<ArrayList<Integer>> {
	public int compare (ArrayList<Integer> a, ArrayList<Integer> b) {
		return b.size() - a.size();
	}
}

// class Cycle {
// 	private ArrayList<Integer> cycle;

// 	public Cycle(ArrayList<Integer> cycle) {
// 		this.cycle = cycle;
// 	}

// 	public ArrayList<Integer> getCycle() {
// 		System.out.println(this.cycle + "hekk");
// 		return this.cycle;
// 	}

// 	public void hello() {
// 		System.out.println("Hello World");
// 	}
// }

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
}


class CycleUtility{


	private ArrayList<Integer> adjList[];
	private Integer noOfVertex;

	private ArrayList<ArrayList<Integer>> storage;

	private Boolean visited[];
	private ArrayList<Cycle> allCycles;
	private ArrayList<ArrayList<Integer>> filteredCycles;


	public CycleUtility(ArrayList<Integer>[] adjList) {
		this.adjList = adjList;
		storage = new ArrayList<ArrayList<Integer>>();
		filteredCycles = new ArrayList<ArrayList<Integer>>();
		noOfVertex = adjList.length-1;
		visited = new Boolean[noOfVertex+1];
		allCycles = new ArrayList<Cycle>();
		Arrays.fill(visited, Boolean.FALSE);
	}


	public void findAllCycles() {
		

		for(int i=1; i<=noOfVertex; ++i) {
			ArrayList<Integer> cycle = new ArrayList<Integer>();

			findCycle(i, cycle, i);
			
		}

		SortByArrayLength comp = new SortByArrayLength();

		Collections.sort(storage, comp);

		
	}

	
	public void findCycle(Integer fromNode, ArrayList<Integer> cycle, Integer current) {
		

		cycle.add(current);

		visited[current] = true;

		for(int i=0; i<adjList[current].size(); ++i) {

			if(adjList[current].get(i) == fromNode) {
				storage.add(new ArrayList<Integer> (cycle));
			}

			else{
				
				if(!visited[adjList[current].get(i)]) {
					
					findCycle(fromNode, cycle, adjList[current].get(i));
				}
			}
		}

		visited[current] = false;
		cycle.remove(cycle.size()-1);




	}

	public void filterCycles() {

		Boolean isSubset[] = new Boolean[storage.size()];
		Arrays.fill(isSubset, Boolean.FALSE);

		for(int i=0; i<storage.size()-1; ++i) {
			for(int j=i+1; j<storage.size(); ++j) {
				if(!isSubset[i]) {
					ArrayList<Integer> set = new ArrayList<Integer>();
					ArrayList<Integer> subSet = new ArrayList<Integer>();

					for(int x=0; x<storage.get(i).size(); ++x){
						set.add(storage.get(i).get(x));

					}

					for(int x = 0; x<storage.get(j).size(); ++x) {
						subSet.add(storage.get(j).get(x));
					}
					Collections.sort(set);
					Collections.sort(subSet);

					Boolean flag = true;

					for(int k=0; k<subSet.size(); ++k) {
						if(!set.contains(subSet.get(k))) {
							flag = false;
							break;
						}

						else
							continue;
					}

					if(flag) {
						isSubset[j] = true;
					}

				}
			}
		}

		for(int i=0; i<storage.size(); ++i) {
			if(!isSubset[i]) {
				filteredCycles.add(new ArrayList<Integer> (storage.get(i)));
			}
		}

		System.out.println("filtered cycles");
		for(int i=0; i<filteredCycles.size(); ++i) {
			System.out.println(filteredCycles.get(i));
		}
	}
	

	public void printAllCycles() {
		for(int j=0; j<storage.size(); ++j) {
			System.out.println(storage.get(j));
		}
	}


	public void printCycle(ArrayList<Integer> cycle) {
		for(int k=0; k<cycle.size(); ++k)
			System.out.print(cycle.get(k)+"  ");
		
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

		
		ArrayList<Integer>[] adjList = graph.getAdjList();
		CycleUtility cycleUtility = new CycleUtility(adjList);

		cycleUtility.findAllCycles();
		
		cycleUtility.filterCycles();


	}
}