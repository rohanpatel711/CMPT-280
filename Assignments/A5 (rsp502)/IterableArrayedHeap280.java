package lib280.tree;

public class IterableArrayedHeap280<I extends Comparable<? super I>> extends ArrayedHeap280<I> {

	/**
	 * Create an iterable heap with a given capacity.
	 * @param cap The maximum number of elements that can be in the heap.
	 */
	public IterableArrayedHeap280(int cap) {
		super(cap);
	}

	public ArrayedBinaryTreeIterator280<I> iterator() {
		return new ArrayedBinaryTreeIterator280<I>(this);
	}

	public void deleteAtPosition(ArrayedBinaryTreeIterator280<I> pos) {

		if( this.count > 1 && this.itemExists() ) {
			this.items[pos.currentNode] = this.items[count];
		}
		this.count--;

		if( this.count == 0) {
			this.currentNode = 0;
			return;
		}

		int position = pos.currentNode;

		while( findLeftChild(position) <= count ) {

			int lc = findLeftChild(position);
			if( lc + 1 <= count && items[lc].compareTo(items[lc+1]) < 0 ){
				lc++;
			}
			if( items[position].compareTo(items[lc]) < 0 ) {
				I swap = items[position];
				items[position] = items[lc];
				items[lc] = swap;
				position = lc;
			}
			else return;

		}
	}
}
