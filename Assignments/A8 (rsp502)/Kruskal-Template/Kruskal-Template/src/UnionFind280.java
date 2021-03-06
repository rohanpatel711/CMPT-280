

import lib280.graph.Edge280;
import lib280.graph.GraphAdjListRep280;
import lib280.graph.Vertex280;


public class UnionFind280 {
	GraphAdjListRep280<Vertex280, Edge280<Vertex280>> G;
	
	/**
	 * Create a new union-find structure.
	 * 
	 * @param numElements Number of elements (numbered 1 through numElements, inclusive) in the set.
	 * @postcond The structure is initialized such that each element is in its own subset.
	 */
	public UnionFind280(int numElements) {
		G = new GraphAdjListRep280<Vertex280, Edge280<Vertex280>>(numElements, true);
		G.ensureVertices(numElements);		
	}
	
	/**
	 * Return the representative element (equivalence class) of a given element.
	 * @param id The elements whose equivalence class we wish to find.
	 * @return The representative element (equivalence class) of the element 'id'.
	 */
	public int find(int id) {

		int result=id;
		G.eGoFirst(G.vertex(id)); // Move the cursor to the vertex through the id and Move the edge cursor to the first edge
		while(G.eItemExists()){
			result = find(G.eItemAdjacentIndex());
		}
		return result; // The resulting index value
	}
	
	/**
	 * Merge the subsets containing two items, id1 and id2, making them, and all of the other elemnets in both sets, "equivalent".
	 * @param id1 First element.
	 * @param id2 Second element.
	 */
	public void union(int id1, int id2) {
		int v1Root = find(id1);
		int v2Root = find(id2);
		if(v1Root != v2Root){
			G.addEdge(v1Root,v2Root);
		}
	}
	
	public static void main(String[] args){
		UnionFind280 unionFind = new UnionFind280(8);
		unionFind.G.addEdge(1,2);
		unionFind.G.addEdge(3,2);
		unionFind.G.addEdge(7,3);
		unionFind.G.addEdge(4,6);
		unionFind.G.addEdge(6,5);

		System.out.println("7th Item is:" + unionFind.find(7));
		System.out.println("4th Item is:" + unionFind.find(4));
		System.out.println("6th Item is:" + unionFind.find(6));
		System.out.println("1st Item is:" + unionFind.find(1));
		System.out.println("8th Item 9is:" + unionFind.find(8));
	}
	
}
