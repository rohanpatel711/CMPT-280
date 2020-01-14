// *** Exercise 4 ***

public class ArrayedList<I> implements ListADT<I> {

    protected I[] listElements;
    protected int listTail;
    protected int capacity;

    public ArrayedList(int capacity) {
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return listTail == 0;
    }

    public boolean isFull() {
        return listTail == capacity;
    }

    // *** Exercise 5 ***
    public void insertFirst(I x) {
        if(this.isFull())
            throw new RuntimeException("Error: Cannot insert an item into a full list.");

        for(int i=listTail; i > 0; i--)
            this.listElements[i+1] = this.listElements[i];
        this.listElements[0] = x;
        this.listTail = this.listTail + 1;
    }

    // *** Exercise 6 ***
    public void deleteFirst()  {
        if(isEmpty())
            throw new RuntimeException("Error: cannot delete an item from an empty list.");

        for(int i =0; i < this.listTail; i++) {
            this.listElements[i] = this.listElements[i+1];
        }
        this.listTail--;
    }

    // *** Exercise 7 ***
    public I firstItem()  {
        if(this.isEmpty()) throw new RuntimeException("Error: cannot obtain an item from an empty list.");

        return this.listElements[0];
    }


}