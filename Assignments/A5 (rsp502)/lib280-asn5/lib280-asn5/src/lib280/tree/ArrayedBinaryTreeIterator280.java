package lib280.tree;

import lib280.base.LinearIterator280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class ArrayedBinaryTreeIterator280<I> extends ArrayedBinaryTreePosition280 implements LinearIterator280<I> {

	ArrayedBinaryTree280<I> binarytree;
	
	/**
	 * Create a new iterator from a given heap.
	 * @param t The heap for which to create a new iterator.
	 */
	public ArrayedBinaryTreeIterator280(ArrayedBinaryTree280<I> t) {
		super(t.currentNode);
		this.binarytree = t;
	}

	// TODO - Complete the following methods which are required by LinearIterator280<I>

	@Override
	public boolean before() {
		return this.currentNode == 0;
	}

	@Override
	public boolean after() {
		return this.currentNode > binarytree.count;
	}

	@Override
	public void goForth() throws AfterTheEnd280Exception {
		if(this.after()) {
			throw new AfterTheEnd280Exception("Can't move the cursor in the after position");
		}
		this.currentNode++;
	}

	@Override
	public void goFirst() throws ContainerEmpty280Exception {
		if( binarytree.isEmpty()){
			throw new ContainerEmpty280Exception("The tree is empty so can't move it to the first position because it doesn't exist");
		}
		this.currentNode = 1;
	}

	@Override
	public void goBefore() {
		this.currentNode = 0;
	}

	@Override
	public void goAfter() {
		this.currentNode = binarytree.count + 1;
	}

	@Override
	public I item() throws NoCurrentItem280Exception {
		return binarytree.items[this.currentNode];
	}

	@Override
	public boolean itemExists() {
		return binarytree.count > 0 && (this.currentNode > 0 && this.currentNode <= binarytree.count);
	}


}
