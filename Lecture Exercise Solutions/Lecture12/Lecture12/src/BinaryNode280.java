// Exercise 1:  A cloneable version of BinaryNode280

public class BinaryNode280<I> implements Cloneable
{
	/** Contents of the node. */
	protected I item;

	/** The left node. */
	protected BinaryNode280<I> leftNode;

	/** The right node. */
	protected BinaryNode280<I> rightNode;

		
	/** Construct a new node with item x. 
	 *  @timing Time = O(1)
	 *  @param x the item placed in the new node */	
	public BinaryNode280(I x)
	{
		this.item = x;
	}

	/** Contents of the node.
	 *  @timing Time = O(1) */
	public I item()
	{
		return item;
	}

	/** The left node.
	 *  @timing Time = O(1) */
	public BinaryNode280<I> leftNode()
	{
		return leftNode;
	}
	
	/** The left node.
	 *  @timing Time = O(1) */
	public BinaryNode280<I> rightNode()
	{
		return rightNode;
	}
		
	/**
	 * Set the item contained in the node.
	 * @param x The new item to store in the node.
	 * @timing Time = O(1)
	 * */	
	public void setItem(I x) {
		this.item = x;
	}
	
	/**
	 * Set the left child of this node
	 * @param n The new left child of this node.
	 */
	public void setLeftNode(BinaryNode280<I> n) {
		this.leftNode = n;
	}
	
	/**
	 * Set the right child of this node.
	 * @param n The new right child of this node.
	 */
	public void setRightNode(BinaryNode280<I> n) {
		this.rightNode = n;
	}
	
	/**
	 * Returns a string representation of the item contained within the node.
	 */
	public String toString() {
		return this.item.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public BinaryNode280<I> clone() throws CloneNotSupportedException {
		return (BinaryNode280<I>)super.clone();
	}
	
}