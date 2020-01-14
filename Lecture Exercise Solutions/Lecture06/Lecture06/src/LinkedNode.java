// Exercise 1:  Class must be redeclared to implement the Cloneable interface.

public class LinkedNode<I> implements Cloneable {
	protected I item;
	protected LinkedNode<I> nextNode;
	
	public LinkedNode(I x)
	{
		setItem(x);
	}
	
	// Getters/setters are automatically generated using eclipse.
	// Use: Source menu -> Generate Getters and Setters

	public I item() {
		return item;
	}
	
	public void setItem(I item) {
		this.item = item;
	}
	
	public LinkedNode<I> nextNode() {
		return nextNode;
	}
	
	public void setNextNode(LinkedNode<I> nextNode) {
		this.nextNode = nextNode;
	}

	// Exercise 1: Add a public shallow clone() method.
	@SuppressWarnings("unchecked")
	public LinkedNode<I> clone() throws CloneNotSupportedException {
		/* This was acceptable for exercise 1 - a shallow clone
		return super.clone();
		*/

		// Exercise 2: A deep clone.  Copy each instance variable,
		// clone()-ing the ones that are references to other objects.
		LinkedNode<I> newNode = (LinkedNode<I>)super.clone();
		if(newNode.nextNode != null)
			newNode.nextNode = this.nextNode.clone();

		// Clone the item if we can.  Types that encapsulate primitive types like
		// Double, Integer, etc. are not cloneable because they are immutable.
		// But since they are immutable, this causes no problems for our list.
		try {
			this.item = (I) this.item.getClass().getMethod("clone").invoke(this.item);
		} catch (Exception e) {
		}
		return newNode;
	}

}
