#include<iostream>
#include<bits/stdc++.h>
using namespace std;


int INF = 999999999;


struct Edge {
	int w;
	int v;

	Edge(int vertex, int weight){
		w = weight;
		v = vertex;
	}

	bool operator<(Edge const& other) const {
		return w<other.w;
	}
};

typedef pair<int, int> p_int;

class Graph { 

private:
	int n;
	bool directed;
	vector<Edge> *adj;
	int adj_Mat[100][100];

public:

	Graph(int n) {
		this->n = n;
		adj = new vector<Edge> [n+1];
	}

	void addEdge(int u, int v, int w) {

		adj[u].push_back(Edge(v, w));
		adj[v].push_back(Edge(u, w));
		adj_Mat[u][v] = w;
		adj_Mat[v][u] = w;
	}

	void printGraph() {
		for(int i=1; i<=n; ++i) {
			cout<<i<<"-->  ";
			//for (Edge e : adj[i]) {
			for(int j=0; j<adj[i].size(); ++j) {
				Edge e = adj[i][j];
				cout<<"("<<e.v<<","<<e.w<<") ";
			}
			
			cout<<endl;
		}
	}

	void prim (int v) {

		priority_queue<p_int, vector<p_int>, greater<p_int> > q;

		int src = 1;
		vector<int> c(n+1, INF);
		vector<int> parent(n+1, -1);
		vector<bool> mstArray(n+1, false);

		q.push(make_pair(0, src));

		c[src] = 0;

		while(!q.empty()) {

			int u = q.top().second;
			q.pop();

			mstArray[u] = true;

			//for(Edge edge : adj[u]) {
			for(int i=0; i<adj[u].size(); ++i) {
				Edge edge = adj[u][i];
				int v = edge.v;
				int w = edge.w;

				if(mstArray[v] == false && c[v] > w) {
					c[v] = w;
					q.push(make_pair(c[v], v));
					parent[v] = u;
				}
			}

		}

		int weight=0;

		for(int i=1; i<=n; ++i) {
			cout<<"vertex : " << i<< " parent " <<parent[i]<<endl;
			weight = weight + adj_Mat[i][parent[i]];
		}

		cout<<" weight " <<  weight << endl;




	}

};

int main() {
	//Graph g(4);

	int n;
	cout<<"Enter no of vertex \n";
	cin>>n;

	Graph g(n);

	int u, v, w;
	
	while(1) {
		cout<<"Enter edge u, v, w. Enter -1 to abort\n";

		cin>>u;
		if(u==-1)
			break;
		cin>>v>>w;
		g.addEdge(u,v,w);
	}


	g.printGraph();
	// g.addEdge(1,2,1);
	// g.addEdge(1,3,1);
	// g.addEdge(1,4,3);
	// g.addEdge(3,2,2);
	// g.addEdge(3,4,4);
	// g.addEdge(2,4,1);

	g.prim(1);
	//g.addEdge(1,2,1);
	//g.addEdge(1,3,1);
	//g.addEdge(2,3,2);


}