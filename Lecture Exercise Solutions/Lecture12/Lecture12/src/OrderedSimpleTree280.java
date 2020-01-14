public class OrderedSimpleTree280<I extends Comparable<? super I>> extends LinkedSimpleTree280<I> 
implements Dispenser280<I>, Searchable280<I>
{
	/**	The current node as set by search. */
	protected BinaryNode280<I> cur;

	/**	The parent node of the current node as set by search. */
	protected BinaryNode280<I> parent;

	/**	Create an empty lib280.tree. <br>
	Analysis: Time = O(1) */
	public OrderedSimpleTree280()
	{
		super();
	}

	/**	Do searches continue?. */
	protected boolean searchesContinue = false;

	protected boolean objectReferenceComparison = false;

	
	// *** Exercises 2 and 3 ***

	public void deleteItem() throws NoCurrentItem280Exception
	{
		if(!itemExists())
			throw new NoCurrentItem280Exception("No current item to delete");

		boolean foundReplacement = false;
		BinaryNode280<I> replaceNode = null;

		/*	Test if there is only one child so it can replace the root. */
		if (cur.rightNode() == null)
		{
			replaceNode = cur.leftNode();
			foundReplacement = true;
		}
		else if (cur.leftNode() == null)
		{
			replaceNode = cur.rightNode();
			foundReplacement = true;
		}
		else
			foundReplacement = false;

		if (foundReplacement)
		{
			/*	Set parent node to refer to the replacement node. */
			if (parent == null)
				setRootNode(replaceNode);
			else if (parent.leftNode() == cur)
				parent.setLeftNode(replaceNode);
			else
				parent.setRightNode(replaceNode);
			cur = replaceNode;
		}
		else
		{
			/*	Replace the current item by its inorder successor and
				then delete the inorder successor from its original place. */

			/*	Find the position (replaceParent and replaceCur) of the inorder successor. */
			BinaryNode280<I> replaceParent = cur;
			BinaryNode280<I> replaceCur = replaceParent.rightNode();
			while (replaceCur.leftNode() != null)
			{
				replaceParent = replaceCur;
				replaceCur = replaceParent.leftNode();
			}

			/*	Replace the current item (to be deleted) by the inorder successor. */
			cur.setItem(replaceCur.item());
			/*	Delete the inorder successor from its original place. */
			BinaryNode280<I> saveParent = parent;
			BinaryNode280<I> saveCur = cur;
			parent = replaceParent;
			cur = replaceCur;
			deleteItem();
			parent = saveParent;
			cur = saveCur;
		}
	}

	// *** End exercise 2/3 ***
	
	
	/**	Is the current position below the bottom of the lib280.tree?. <br>
	Analysis: Time = O(1) */
	public boolean below()
	{
		return (cur == null) && (parent != null || isEmpty());
	}
	
	/**	Is the current position above the root of the lib280.tree?. <br>
		Analysis: Time = O(1) */
	public boolean above()
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

	
	
	public OrderedSimpleTree280<I> rootLeftSubtree() throws ContainerEmpty280Exception
	{
		return (OrderedSimpleTree280<I>) super.rootLeftSubtree();
	}

	
	public OrderedSimpleTree280<I> rootRightSubtree() throws ContainerEmpty280Exception
	{
		return (OrderedSimpleTree280<I>) super.rootRightSubtree();
	}
	
	/**	Test whether x equals y using the current comparison mode. <br>
	Analysis: Time = O(1) */
	@Override
	public boolean membershipEquals(I x, I y)
	{
		if (objectReferenceComparison)
			return x == y;
		else if ((x instanceof Comparable) && (y instanceof Comparable))
			return 0 == x.compareTo(y);
		else if (x.equals(y))
			return true;
		else 
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
