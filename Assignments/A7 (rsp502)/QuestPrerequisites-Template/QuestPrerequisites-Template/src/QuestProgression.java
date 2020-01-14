import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import lib280.base.CursorPosition280;
import lib280.graph.Edge280;
import lib280.graph.GraphMatrixRep280;
import lib280.list.LinkedList280;
import lib280.tree.ArrayedHeap280;

public class QuestProgression {

	// File format for quest data:
	// First line: Number of quests N
	// Next N lines consist of the following items, separated by commas:
	//     quest ID, quest name, quest area, quest XP
	//     (Quest ID's must be between 1 and N, but the line for each quest IDs may appear in any order).
	// Remaining lines consist of a comma separated pair of id's i and j where i and j are quest IDs indicating
	// that quest i must be done before quest j (i.e. that (i,j) is an edge in the quest graph).
	/**
	 * Read the quest data from a text file and build a graph of quest prerequisites.
	 * @param filename Filename from which to read quest data.
	 * @return A graph representing quest prerequisites.  If quest with id i must be done before a quest with id j, then there is an edge in the graph from vertex i to vertex j.
	 */
	public static GraphMatrixRep280<QuestVertex, Edge280<QuestVertex>> readQuestFile(String filename) {
		Scanner infile;
		// Attempt to open the input filename.
		try {
			infile = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Error: Unable to open" + filename);
			e.printStackTrace();
			return null;
		}
		// Set the delimiters for parsing to commas, and vertical whitespace.
		infile.useDelimiter("[,\\v]");
		// Read the number of quests for which there is data.
		int numQuests = infile.nextInt();
		// read the quest data for each quest.
		LinkedList280<Quest> questList = new LinkedList280<Quest>();
		for(int i=0; i < numQuests; i++) {
			int qId = infile.nextInt();
			String qName = infile.next();
			String qArea = infile.next();
			int qXp = infile.nextInt();
			questList.insertLast(new Quest(qId, qName, qArea, qXp));
		}
		// Make a graph with the vertices we created from the quest data.
		GraphMatrixRep280<QuestVertex, Edge280<QuestVertex>> questGraph =
				new GraphMatrixRep280<QuestVertex, Edge280<QuestVertex>> (numQuests, true, "QuestVertex", "lib280.graph.Edge280");
		// Add enough vertices for all of our quests.
		questGraph.ensureVertices(numQuests);
		// Store each quest in a different vertex.  The quest with id i gets stored vertex i.
		questList.goFirst();
		while(questList.itemExists()) {
			questGraph.vertex(questList.item().id()).setQuest(questList.item());
			questList.goForth();
		}
		// Continue reading the input file for the quest prerequisite informaion and add an edge to the graph
		// for each prerequisite.
		while(infile.hasNext()) {
			questGraph.addEdge(infile.nextInt(), infile.nextInt());
		}
		infile.close();
		return questGraph;
	}

	/**
	 * To check if the vertex v has incoming edges or not
	 * @param G The graph
	 * @param v Integer identifier of a node in G
	 * @return Returns true if the vertex has incoming edges and if not returns false.
	 */
	public static boolean hasNoIncomingEdges(GraphMatrixRep280<QuestVertex,Edge280<QuestVertex>> G, int v) {
		CursorPosition280 position = G.currentPosition();
		boolean hasNoIncomingEdge = true;
		for(int i=1; i <= G.numVertices() && hasNoIncomingEdge; i++) {
			G.eSearch(G.vertex(i), G.vertex(v));
			hasNoIncomingEdge = hasNoIncomingEdge && !G.eItemExists();
		}
		G.goPosition(position);
		return hasNoIncomingEdge;
	}

	/**
	 * Perform a topological sort with the given priority.
	 * @param G The graph
	 * @return List of quests that is the result of the topological sort.
	 */
	public static LinkedList280<Quest> questProgression(GraphMatrixRep280<QuestVertex,Edge280<QuestVertex>> G) {

		LinkedList280<Quest> list = new LinkedList280<Quest>();

		ArrayedHeap280<Quest> heap = new ArrayedHeap280<Quest>(G.numVertices());
		// Check the heap with quests whose corresponding nodes have no incoming edges.
		for(int i=1; i <= G.numVertices(); i++) {
			if(hasNoIncomingEdges(G, i))
				heap.insert(G.vertex(i).quest());
		}
		while(!heap.isEmpty()) {
			Quest current = heap.item();
			heap.deleteItem();

			// Add the item to the end of the list.
			list.insertLast(current);

			// Delete all of the outgoing edges from the graph node
			G.eGoFirst(G.vertex(current.id()));
			while(G.eItemExists()) {
				int x = G.eItemAdjacentIndex();
				G.deleteEItem();

				// If the node of the deleted edge doesn't have any incoming edges then add it to the heap
				if( hasNoIncomingEdges(G, x) ) {
					heap.insert(G.vertex(x).quest());
				}
			}
		}
		// If the graph has any remaining edges , then throw a exception
		if(G.numEdges() > 0 ) throw new RuntimeException("There is a cycle remaining in the graph.");
		return list;
	}

	public static void main(String args[]) {
		// If you get an error reading the file here and you're using Eclipse,
		// remove the 'QuestPrerequisites-Template/' portion of the filename.
		GraphMatrixRep280<QuestVertex,Edge280<QuestVertex>> questGraph = readQuestFile("D:\\Subjects (Winter 2019)\\CMPT 280\\Assignments\\A7 (rsp502)\\QuestPrerequisites-Template\\QuestPrerequisites-Template\\quests16.txt");
		// Perform a topological sort on the graph.
		LinkedList280<Quest> questListForMaxXp = questProgression(questGraph);
		// Display the quests to be completed in the order determined by the topologial sort.
		questListForMaxXp.goFirst();
		while(questListForMaxXp.itemExists()) {
			System.out.println(questListForMaxXp.item());
			questListForMaxXp.goForth();
		}

	}
}
