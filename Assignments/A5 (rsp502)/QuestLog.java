import com.opencsv.CSVReader;
import lib280.base.Pair280;
import lib280.hashtable.KeyedChainedHashTable280;
import lib280.list.LinkedIterator280;
import lib280.list.LinkedList280;
import lib280.tree.OrderedSimpleTree280;
import java.util.Arrays;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;




// This project uses a JAR called opencsv which is a library for reading and
// writing CSV (comma-separated value) files.
//
// You don't need to do this for this project, because it's already done, but
// if you want to use opencsv in other projects on your own, here's the process:
//
// 1. Download opencsv-3.1.jar from http://sourceforge.net/projects/opencsv/
// 2. Drag opencsv-3.1.jar into your project.
// 3. Right-click on the project in the package explorer, select "Properties" (at bottom of popup menu)
// 4. Choose the "Libraries" tab
// 5. Click "Add JARs"
// 6. Select the opencsv-3.1.jar from within your project from the list.
// 7. At the top of your .java file add the following imports:
//        import java.io.FileReader;
//        import com.opencsv.CSVReader;
//
// Reference documentation for opencsv is here:
// http://opencsv.sourceforge.net/apidocs/overview-summary.html


public class QuestLog extends KeyedChainedHashTable280<String, QuestLogEntry> {

	public QuestLog() {
		super();
	}

	/**
	 * Obtain an array of the keys (quest names) from the quest log.  There is
	 * no expectation of any particular ordering of the keys.
	 *
	 * @return The array of keys (quest names) from the quest log.
	 */
	public String[] keys() {
		String[] keyList = new String[this.count()];
		this.goFirst();
		int i = 0;
		while (this.itemExists()) {
			keyList[i] = this.itemKey();
			this.goForth();
			i++;
		}
		Arrays.sort(keyList);
		return keyList;  // Remove this line you're ready.  It's just to prevent compiler errors.
	}

	/**
	 * Format the quest log as a string which displays the quests in the log in
	 * alphabetical order by name.
	 *
	 * @return A nicely formatted quest log.
	 */
	public String toString() {
		String[] questNames = this.keys();
		String output = "";
		for (int i = 0; i < questNames.length; i++) {
			output += this.obtain(questNames[i]).toString() + "\n";
		}
		return output;
	}

	/**
	 * Obtain the quest with name k, while simultaneously returning the number of
	 * items examined while searching for the quest.
	 *
	 * @param k Name of the quest to obtain.
	 * @return A pair in which the first item is the QuestLogEntry for the quest named k, and the
	 * second item is the number of items examined during the search for the quest named k.
	 * Note: if no quest named k is found, then the first item of the pair should be null.
	 */
	public Pair280<QuestLogEntry, Integer> obtainWithCount(String k) {
		int keyItemOfHash = this.hashPos(k);
		int itemsProcessed = 0;
		LinkedList280<QuestLogEntry> hash = this.hashArray[keyItemOfHash];
		itemsProcessed++;
		if (hash==null){
			return new Pair280<>(null, 0);
		}
		LinkedIterator280<QuestLogEntry> iterator = hash.iterator();
		iterator.goFirst();
		if(iterator.item().key().compareTo(k) != 0){
			while(iterator.itemExists() && iterator.item().key().compareTo(k)!=0){
				itemsProcessed++;
				iterator.goForth();
			}
		}
		return new Pair280<>(iterator.item(), itemsProcessed);
	}


	public static void main(String args[]) {
		// Make a new Quest Log
		QuestLog hashQuestLog = new QuestLog();

		// Make a new ordered binary lib280.tree.
		OrderedSimpleTree280<QuestLogEntry> treeQuestLog =
				new OrderedSimpleTree280<QuestLogEntry>();


		// Read the quest data from a CSV (comma-separated value) file.
		// To change the file read in, edit the argument to the FileReader constructor.
		CSVReader inFile;
		try {
			//input filename on the next line - path must be relative to the working directory reported above.
			inFile = new CSVReader(new FileReader("D:\\Subjects (Winter 2019)\\CMPT 280\\Assignments\\A5(rsp502)\\lib280-asn5\\QuestLog-Template\\QuestLog-Template\\quests100000.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			return;
		}

		String[] nextQuest;
		try {
			// Read a row of data from the CSV file
			while ((nextQuest = inFile.readNext()) != null) {
				// If the read succeeded, nextQuest is an array of strings containing the data from
				// each field in a row of the CSV file.  The first field is the quest name,
				// the second field is the quest region, and the next two are the recommended
				// minimum and maximum level, which we convert to integers before passing them to the
				// constructor of a QuestLogEntry object.
				QuestLogEntry newEntry = new QuestLogEntry(nextQuest[0], nextQuest[1],
						Integer.parseInt(nextQuest[2]), Integer.parseInt(nextQuest[3]));
				// Insert the new quest log entry into the quest log.
				hashQuestLog.insert(newEntry);
				treeQuestLog.insert(newEntry);
			}
		} catch (IOException e) {
			System.out.println("Something bad happened while reading the quest information.");
			e.printStackTrace();
		}

		// Print out the hashed quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
		System.out.println(hashQuestLog);

		// Print out the lib280.tree quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
		System.out.println(treeQuestLog.toStringInorder());


		String[] hashQuests = hashQuestLog.keys();

		int totalNoOfItems = hashQuests.length;
		double c = 0.0;
		for (String key : hashQuests) {    // For each key in our key list
			c += hashQuestLog.obtainWithCount(key).secondItem();
		}
		double hashAverage = c / totalNoOfItems;
		System.out.println("Average of items processed in hashQuests:" + hashAverage);

		double treeAverage = 0.0;
		for (int i = 0; i < hashQuests.length; i++) {
			treeAverage += treeQuestLog.searchCount(hashQuestLog.obtain(hashQuests[i]));
		}
		treeAverage /= hashQuests.length;
		System.out.println("Average of items processed in treeQuestLog:" + treeAverage);


	}


}
