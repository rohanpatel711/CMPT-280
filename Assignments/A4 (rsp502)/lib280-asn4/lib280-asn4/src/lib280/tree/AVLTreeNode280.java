package lib280.tree;

class AVLTreeNode280<I extends Comparable<? super I>> extends BinaryNode280<I> implements Cloneable, Comparable<BinaryNode280<I>>
{
    protected AVLTreeNode280<I> parent;

    protected int r_Height;
    protected int l_Height;
    protected AVLTreeNode280<I> leftNode;
    protected AVLTreeNode280<I> rightNode;


    public int compareTo(AVLTreeNode280<I> x) {
        return this.item.compareTo(x.item);
    }

    /** Construct a new node with item x.
     *  @timing Time = O(1)
     *  @param x the item placed in the new node */
    public AVLNode280(I x) {
        super(x);
        this.r_Height=0;
        this.l_Height=0;
        this.parent= null;
        this.leftNode = null;
        this.rightNode = null;
    }

    /** Contents of the node.*/
    public I item()
    {
        return item;
    }

    /** return the left node.*/
    public AVLNode280<I> leftNode()
    {
        return leftNode;
    }

    /** returns the right node */
    public AVLNode280<I> rightNode(){
        return rightNode;
    }


    /** Sets the value of the node */
    public void setItem(I x) {
        this.item = x;
    }

    /**
     * This function sets left child of current node
     */
    public void setLeftNode(AVLNode280<I> n) {
        this.leftNode = n;
        n.parent = this;
    }

    /**
     * Set the right child of this node.
     */
    public void setRightNode(AVLNode280<I> n) {
        this.rightNode = n;
        n.parent = this;
    }

}



