package com.proishan11;

import javafx.util.Pair;

import java.util.*;

class pairComparator implements Comparator<Pair<Integer, Integer>>{

    @Override
    public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
        if(o1.getValue() >= o2.getValue()) return 1;
        else if(o1.getValue() < o2.getValue()) return -1;
        return 0;
    }
}


class Graph {
    static int infinity = 999999999;
    //private int vertex;
    private int vertex;
    private ArrayList<Pair<Integer, Integer>> adjList[];
    boolean directed;

    private int distance[];
    private int parent[];

    public Graph(int vertex, boolean directed) {
        this.vertex = vertex;
        this.directed = directed;

        adjList = new ArrayList[vertex+1];

        for(int i=1; i<=vertex; ++i)
            adjList[i] = new ArrayList<>();
    }

    public void addEdge(int u, int v, int w) {
        adjList[u].add(new Pair<>(v, w));
        if(!directed) {
            adjList[v].add(new Pair<>(u, w));
        }
    }

    public void printGraph() {
        for(int i=1; i<=vertex; ++i) {
            System.out.print(i + " -> ");
            for(Pair<Integer, Integer> v: adjList[i]) {
                System.out.print(" ( "+ v.getKey()+ " , " + v.getValue() + " ) ");
            }
            System.out.println();
        }
    }

    public void djikstra(Integer source) {
        this.distance = new int[vertex+1];
        this.parent = new int[vertex+1];

        distance[source] = 0;

        PriorityQueue<Pair<Integer, Integer>> Q = new PriorityQueue<>(new pairComparator());

        Q.add(new Pair<>(source, 0));

        for(int i=1; i<=vertex; ++i) {
            if(i != source)
                distance[i] =  infinity;
            parent[i] = -1;
        }


        while (!Q.isEmpty()) {
            Integer u = Q.poll().getKey();
            for(Pair<Integer, Integer> v : adjList[u]) {
                Integer weight = v.getValue();
                Integer neighbour = v.getKey();
                Integer temp = distance[u] + weight;
                if(temp < distance[neighbour]){
                    distance[neighbour] = temp;
                    parent[neighbour] = u;
                    Q.add(new Pair<>(neighbour, distance[neighbour]));
                }
            }
        }
    }

    Integer getDistance(int source, int target) {
        djikstra(source);
        if(distance[target] != infinity)
            retrievePath(target);
        return distance[target];
    }

    public void retrievePath(int target) {
        ArrayList<Integer> path = new ArrayList<>();
        int u = target;
        while(u>0) {
            if (parent[u] != -1) {
                //System.out.println(u + "here ");
                path.add(u);
                u = parent[u];
            }
            else break;
        }
        // add source to list
        path.add(u);

        for(int i=path.size()-1; i>=0; --i) {
            System.out.print(path.get(i)+" -> ");
        }
        System.out.println();
    }

    Integer distance(int destination) {
        return distance[destination];
    }

}



public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of vertex");
        Integer n = scan.nextInt();

        Graph graph = new Graph(n, false);

        while(true) {
            System.out.println("Enter the edge. Enter -1 to abort");
            Integer u = scan.nextInt();
            if(u==-1)
                break;
            Integer v = scan.nextInt();
            Integer w = scan.nextInt();
            graph.addEdge(u,v,w);
        }

        System.out.println("The adjacency list of graph is");
        graph.printGraph();

        //System.out.println("Enter 2 vertices to calculate the distance b/w them");
        System.out.println("Enter the source");
        Integer u = scan.nextInt();
        graph.djikstra(u);
        //Integer v = scan.nextInt();

        /*for(int i=1; i<=n; ++i){
            System.out.println(graph.getDistance(u, i));
        }*/

        //print all paths using distance vector
        for(int destination = 1; destination<=n; ++destination){
            if(destination != u) {
                graph.retrievePath(destination);
                System.out.println(graph.distance(destination));
            }
        }



        //System.out.println(graph.getDistance(u,v));
        /*graph.addEdge(1,2,5);
        graph.addEdge(2,3,1);
        graph.addEdge(3,1,1);
        graph.addEdge(5, 4, 88);
        graph.printGraph();
        System.out.println(graph.getDistance(1,3));
        */
    }
}
