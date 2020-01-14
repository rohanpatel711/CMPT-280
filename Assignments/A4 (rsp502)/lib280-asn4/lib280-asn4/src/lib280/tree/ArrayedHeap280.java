package lib280.tree;

import lib280.base.Dispenser280;
import lib280.exception.*;

public class ArrayedHeap280<I extends Comparable<? super I>> extends ArrayedBinaryTree280<I> implements Dispenser280<I> {

    /**
     * Constructor.
     *
     * @param cap Maximum number of elements that can be in the lib280.tree.
     */
    public ArrayedHeap280(int cap) {
        super(cap);
        capacity = cap;
        currentNode = 1;
        count = 0;
        items = (I[]) new Comparable[capacity + 1];
    }

    private boolean hasHeapProperty() {
        for(int i=1; i <= count; i++) {
            if( findRightChild(i) <= count ) {  // if i Has two children...
                // ... and i is smaller than either of them, , then the heap property is violated.
                if( items[i].compareTo(items[findRightChild(i)]) < 0 ) return false;
                if( items[i].compareTo(items[findLeftChild(i)]) < 0 ) return false;
            }
            else if( findLeftChild(i) <= count ) {  // if n has one child...
                // ... and i is smaller than it, then the heap property is violated.
                if( items[i].compareTo(items[findLeftChild(i)]) < 0 ) return false;
            }
            else break;  // Neither child exists.  So we're done.
        }
        return true;
    }

    protected void swap(int up, int down){
        if (up==down){
            throw new DuplicateItems280Exception("No need to swap, the values are already same");
        }
        else{
            I temp;
            temp = items[up];
            items[up] = items[down];
            items[down] = temp;
        }
    }

    @Override
    public void insert(I x) throws ContainerFull280Exception, DuplicateItems280Exception {
        if (this.isFull()) {
            throw new ContainerFull280Exception("Cannot insert a item, Heap is Full");
        } else {
            count++;
            items[count] = x;

            int parent = findParent(count);
            int n = count;

            while (parent > 0 && items[n].compareTo(items[parent]) >= 1) {
                swap(parent, n);

                n = parent;
                parent = findParent(n);
            }
        }
    }

    @Override
    public void deleteItem() throws NoCurrentItem280Exception {
        if(isEmpty()) {
            throw new ContainerEmpty280Exception("The heap is empty and can't delete an element from an empty heap.");
        }
        if( count > 1 ) {
            currentNode = 1;
            items[currentNode] = items[count];
        }
        count--;

        // If we are deleting the last element of the heap
        if( count == 0) {
            currentNode = 0;
            return;
        }

        // Propagate the new root
        int n = 1;

        // While offset n has a left child...
        while( findLeftChild(n) <= count ) {
            // Select the left child.
            int l = findLeftChild(n);

            // If there is a right child for the left child of n and is larger, select it instead.
            if( l + 1 <= count && items[l].compareTo(items[l+1]) < 0 )
                l++;

            // If the parent is smaller than the root...
            if( items[n].compareTo(items[l]) < 0 ) {
                // Swap them.
                I temp = items[n];
                items[n] = items[l];
                items[l] = temp;
                n = l;
            }
            else return;
        }
    }
    public static void main(String[] args) {

        ArrayedHeap280<Integer> H = new ArrayedHeap280<Integer>(10);

        // Empty heap should have the heap property.
        if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");

        // Insert items 1 through 10, checking after each insertion that
        // the heap property is retained, and that the top of the heap is correctly i.
        for(int i = 1; i <= 10; i++) {
            H.insert(i);
            if(H.item() != i) System.out.println("Expected current item to be " + i + ", got " + H.item());
            if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");
        }

        // Remove the elements 10 through 1 from the heap, chekcing
        // after each deletion that the heap property is retained and that
        // the correct item is at the top of the heap.
        for(int i = 10; i >= 1; i--) {
            // Remove the element i.
            H.deleteItem();
            // If we've removed item 1, the heap should be empty.
            if(i==1) {
                if( !H.isEmpty() ) System.out.println("Expected the heap to be empty, but it wasn't.");
            }
            else {
                // Otherwise, the item left at the top of the heap should be equal to i-1.
                if(H.item() != i-1) System.out.println("Expected current item to be " + i + ", got " + H.item());
                if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");
            }
        }

        System.out.println("Regression Test Complete.");
    }
}