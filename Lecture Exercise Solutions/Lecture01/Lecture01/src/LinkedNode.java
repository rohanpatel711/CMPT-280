// *** Exercise 1 ***

public class LinkedNode<I> implements Cloneable {

    protected I item;
    protected LinkedNode<I> nextNode;

    public LinkedNode(I x) {
        this.item = x;
        this.nextNode = null;
    }


    public I getItem() {
        return item;
    }

    public void setItem(I item) {
        this.item = item;
    }

    public LinkedNode<I> getNextNode() {
        return nextNode;
    }

    public void setNextNode(LinkedNode<I> nextNode) {
        this.nextNode = nextNode;
    }
}// *** Exercise 1 ***
