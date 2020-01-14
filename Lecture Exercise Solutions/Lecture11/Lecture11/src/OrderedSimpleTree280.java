
// Exercise 2: Class Header
public class OrderedSimpleTree280<I extends Comparable<? super I>> extends LinkedSimpleTree280<I>
implements Dispenser280<I>, Searchable280<I>
{
	// Exercise 2:  Instqnce variables

	/**	The current node as set by search. */
	protected BinaryNode280<I> cur;

	/**	The parent node of the current node as set by search. */
	protected BinaryNode280<I> parent;

	/**	Do searches continue?. */
	protected boolean searchesContinue = false;

	/**	Create an empty lib280.tree. <br>
	Analysis: Time = O(1) */
	public OrderedSimpleTree280()
	{
		super();
	}


	// Exercise 3 methods...

	/**	Is the current position below the bottom of the lib280.tree?. <br>
	Analysis: Time = O(1) */
	protected boolean below()
	{
		return (cur == null) && (parent != null || isEmpty());
	}
	
	/**	Is the current position above the root of the lib280.tree?. <br>
		Analysis: Time = O(1) */
	protected boolean above()
	{
		return (parent == null) && (cur == null);
	}
	
	/**	Is there a current node?. <br>
	Analysis : Time = O(1) */
	public boolean itemExists()
	{
		return cur != null;
	}

	public I item() throws NoCurrentItem280Exception
	{
		if (!itemExists())
			throw new NoCurrentItem280Exception("Cannot access item when it does not exist");
	
		return cur.item();
	}
	

	// Exercise 4 methods...

	/**	Go to item x, if it is in the lib280.tree.  If searchesContinue, continue in the right subtree. <br>
	Analysis : Time = O(h) worst case, where h = height of the lib280.tree */
	public void search(I x)
	{
		boolean found = false;
		if (!searchesContinue || above())
		{
			parent = null;
			cur = rootNode;
		}
		else if (!below())
		{
			parent = cur;
			cur = cur.rightNode();
		}
		while (!found && itemExists())
		{
			if (x.compareTo(item()) < 0)
			{
				parent = cur;
				cur = parent.leftNode();
			}
			else if (x.compareTo(item()) > 0)
			{
				parent = cur;
				cur = parent.rightNode();
			}
			else
				found = true;
		}
	}
	

	// Exercise 5 methods...

	/**	Does the lib280.tree contain x?. <br>
	Analysis : Time = O(h) worst case, where h = height of the lib280.tree */
	public boolean has(I x)
	{
		// Save the cursor position
		BinaryNode280<I> saveCur = cur;
		BinaryNode280<I> saveParent = parent;
		
		// Place cursor in above() position so that our search starts at the root 
		// regardless of the state of searchesContinue
		cur = null;
		parent = null;

		// Look for the item
		search(x);
		
		// And determine if a matching item was found.
		boolean result = itemExists();

		parent = saveParent;
		cur = saveCur;
		return result;
	}

	
	// Exercise 6 methods...

	/**	Insert x into the lib280.tree. <br>
	Analysis : Time = O(h) worst case, where h = height of the lib280.tree */
	public void insert(I x)
	{
		if (isEmpty())
			rootNode = createNewNode(x);
		else if (x.compareTo(rootItem()) < 0)
		{
			OrderedSimpleTree280<I> leftTree = rootLeftSubtree();
			leftTree.insert(x);
			setRootLeftSubtree(leftTree);
		}
		else
		{
			OrderedSimpleTree280<I> rightTree = rootRightSubtree();
			rightTree.insert(x);
			setRootRightSubtree(rightTree);
		}
	}

	public void deleteItem() throws NoCurrentItem280Exception {
		// TODO Auto-generated method stub
		// We will do this one in Lecture 12.
	}


	// Methods overridden from LinkedSimpleTree so we can avoid lots of typecasting.

	public OrderedSimpleTree280<I> rootLeftSubtree() throws ContainerEmpty280Exception
	{
		return (OrderedSimpleTree280<I>) super.rootLeftSubtree();
	}

	
	public OrderedSimpleTree280<I> rootRightSubtree() throws ContainerEmpty280Exception
	{
		return (OrderedSimpleTree280<I>) super.rootRightSubtree();
	}
	
	@Override
	public boolean membershipEquals(I x, I y) {
		return false;
	}

	@Override
	public void restartSearches() {
		this.searchesContinue = false;
	}

	@Override
	public void resumeSearches() {
		this.searchesContinue = true;
	}
	
	/** 
	 * Helper method for printing of the lib280.tree.
	 * @param i Current level of the lib280.tree.
	 * @return A string representation of the lib280.tree.
	 */
	protected String toStringByLevel(int i) 
	{
		// This is the toStringByLevel() method we saw in the lecture notes.
		
		StringBuffer blanks = new StringBuffer((i - 1) * 5);
		for (int j = 0; j < i - 1; j++)
			blanks.append("     ");
	  
		String result = new String();
		if (!isEmpty() && (!this.rootLeftSubtree().isEmpty() || !this.rootRightSubtree().isEmpty()))
			result += this.rootRightSubtree().toStringByLevel(i+1);
		
		result += "\n" + blanks + i + ": " ;
		if (this.isEmpty())
			result += "-";
		else 
		{
			result += this.rootItem();
			if (!this.rootLeftSubtree().isEmpty() || !this.rootRightSubtree().isEmpty())
				result += this.rootLeftSubtree().toStringByLevel(i+1);
		}
		return result;
	}

	/**
	 * 	String representation of the lib280.tree, level by level.
	 */
	public String toString()
	{
		return toStringByLevel(1);
	}
	
	
	// *** Exercise 4 ***
	
	public static void main(String args[]) {
		OrderedSimpleTree280<Integer> T = new OrderedSimpleTree280<Integer>();
		T.insert(50);
		T.insert(16);
		T.insert(67);
		T.insert(81);
		T.insert(22);
		T.insert(5);
		T.insert(17);
		T.insert(66);
		T.insert(42);
		
		System.out.println(T);
		
		T.search(50);
		if(T.itemExists()) {
			T.deleteItem();
			System.out.println("\n\nAfter deleting 50:\n" + T);
		}
		else System.out.println("\n\nThere was no element 50 in the lib280.tree to delete.");

		T.search(42);
		if(T.itemExists()) {
			T.deleteItem();
			System.out.println("\n\nAfter deleting 42:\n" + T);
		}
		else System.out.println("\n\nThere was no element 42 in the lib280.tree to delete.");

		T.search(16);
		if(T.itemExists()) {
			T.deleteItem();
			System.out.println("\n\nAfter deleting 16:\n" + T);
		}
		else System.out.println("\n\nThere was no element 16 in the lib280.tree to delete.");
		
		T.search(99);
		if(T.itemExists()) {
			T.deleteItem();
			System.out.println("\n\nAfter deleting 99:\n" + T);
		}
		else System.out.println("\n\nThere was no element 99 in the lib280.tree to delete.");

	}

	
	
	
}
