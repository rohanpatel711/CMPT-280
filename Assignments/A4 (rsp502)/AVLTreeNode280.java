//Rohan Patel
//rsp502

package lib280.tree;

/**
 * AVL Tree Node extends a binary node to help us implement the AVL tree
 */

public class AVLTreeNode280<I extends Comparable<? super I>> extends BinaryNode280<I> {

    int h; //Height

    /**
     * Creates a new AVL Node. Height defaults to 1
     */
    public AVLTreeNode280(I item) {
        super(item);
        h = 1;
    }

    /**
     * Set height of the current node
     */
    protected void setHeight(int x) {
        this.h = x;
    }

    /**
     * @return height of the left sub node
     */
    protected int getLHeight() {
        if (this.leftNode == null) {
            return 0;
        }
        return ((AVLTreeNode280<I>) this.leftNode).h;
    }

    /**
     * @return height of right sub node
     */
    protected int getRHeight() {
        if (this.rightNode == null) {
            return 0;
        }
        return ((AVLTreeNode280<I>) this.rightNode).h;
    }

    /**
     * @return left node of thee avl node
     */
    @Override
    public AVLTreeNode280<I> leftNode() {
        return (AVLTreeNode280<I>) super.leftNode();
    }

    /**
     * @return right avl node of the avl node
     */
    @Override
    public AVLTreeNode280<I> rightNode() {
        return (AVLTreeNode280<I>) super.rightNode();
    }

    /**
     * @return String containing crucial information regarding the node to be printed
     */
    @Override
    public String toString() {
        return "Item: " + this.item + "  | Height: " + this.h + " | Left Height: " +
                this.getLHeight() + " | Right Height: " + this.getRHeight();
    }
}
