import lib280.graph.Vertex280;
import lib280.graph.WeightedEdge280;
import lib280.graph.WeightedGraphAdjListRep280;
import lib280.tree.ArrayedMinHeap280;

public class Kruskal {
	
	public static WeightedGraphAdjListRep280<Vertex280> minSpanningTree(WeightedGraphAdjListRep280<Vertex280> G) {

		WeightedGraphAdjListRep280<Vertex280> minimum = new WeightedGraphAdjListRep280<Vertex280>(G.capacity(),false);
		minimum.ensureVertices(G.numVertices());

		UnionFind280 unionFind = new UnionFind280(G.numVertices());

		ArrayedMinHeap280<WeightedEdge280<Vertex280>> heap = new ArrayedMinHeap280<WeightedEdge280<Vertex280>>(G.numVertices()* G.numVertices());

		G.goFirst();
		while(G.itemExists()){
			G.eGoFirst(G.item());
			while(G.eItemExists()){
				heap.insert(G.eItem());
				G.eGoForth();
			}
			G.goForth();
		}
		while(!heap.isEmpty()){
			WeightedEdge280<Vertex280> edge = heap.item();
			heap.deleteItem();
			int source = edge.firstItem().index();
			int destination = edge.secondItem().index();
			if(unionFind.find(source) != unionFind.find(destination)){
				minimum.addEdge(source,destination);
				minimum.setEdgeWeight(source,destination,edge.getWeight());
				unionFind.union(source,destination);
			}
		}
		return minimum;  // Remove this when you're ready -- it is just a placeholder to prevent a compiler error.
	}
	
	
	public static void main(String args[]) {
		WeightedGraphAdjListRep280<Vertex280> G = new WeightedGraphAdjListRep280<Vertex280>(1, false);
		// If you get a file not found error here and you're using eclipse just remove the 
		// 'Kruskal-template/' part from the path string.
		G.initGraphFromFile("D:\\Subjects (Winter 2019)\\CMPT 280\\Assignments\\A8 (rsp502)\\Kruskal-Template\\Kruskal-Template\\mst.graph");
		System.out.println(G);
		
		WeightedGraphAdjListRep280<Vertex280> minST = minSpanningTree(G);
		
		System.out.println(minST);
	}
}


