import lib280.list.LinkedIterator280;
import lib280.list.LinkedList280;


public class ListDemo {
	
	/**
	 * Prints out a parameter list. 
	 * By using an iterator, the list itself is only touched once. 
	 * We also don't have to move the list's internal cursor. 
	 * @param l The list to print.
	 */
	private static void printList(LinkedList280<Integer> l) {
		LinkedIterator280<Integer> i = new LinkedIterator280<Integer>(l);
		while (i.itemExists()) {
			System.out.print(i.item() + " ");
			i.goForth();
		}
		System.out.print("\n");
	}
	
	
	
	/**
	 * Demos the use of a LinkedList280 and a LinkedIterator280. 
	 * @param args Commandline args.  (Not used.)
	 */
	public static void main(String[] args) {
		
		// Make a list, using the list's cursor. 
		LinkedList280<Integer> l = new LinkedList280<Integer>();
		l.insert(5);
		l.insert(4);
		l.insert(2);
		l.insert(1);
		l.goFirst(); l.goForth(); l.goForth(); 
		l.insertBefore(3);
		l.insertBefore(3);
		l.insertBefore(3);
		
		// Print the list. 
		ListDemo.printList(l);
		
		// Add up the numbers in the list. (Demonstrates iteration using the cursor!)
		int sum=0;
		l.goFirst();			
		while(l.itemExists()){
			sum += l.item();
			l.goForth();
		}
		System.out.println("The numbers add up to " + sum + ".");
		
		// Add up the numbers in the again. (Demonstrates iteration using an iterator!)
		// This does the same thing, but without changing the internal cursor position.
		int sum=0;
		LinkedIterator280<Integer> iter = l.iterator();
		iter.goFirst();			
		while(iter.itemExists()){
			sum += iter.item();
			iter.goForth();
		}
		System.out.println("The numbers (again) add up to " + sum + ".");
		
		
		// Delete something in the list, at the list's cursor.  
		l.deleteItem();
		
		// Print the list again. 
		ListDemo.printList(l);
	}
	
	
	
}
