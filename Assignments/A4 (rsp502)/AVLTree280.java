//Rohan Patel
//rsp502

package lib280.tree;
import lib280.base.Dispenser280;
import lib280.base.Searchable280;
import lib280.exception.*;

/**
 * AVL Tree is a self balancing tree
 */

public class AVLTree280<I extends Comparable<? super I>> extends OrderedSimpleTree280<I> implements Searchable280<I>, Dispenser280<I> {
    /**
     * Constructor Method that creates the AVL Tree
     */
    public AVLTree280() {
        super();
    }

    /**
     * Insert method for user to use. Encapsulates the actual code that makes up insert
     */
    @Override
    public void insert(I item) {
        this.rootNode = insert(item, this.rootNode());
    }

    public void delete(I item) {
        this.rootNode = delete(item, this.rootNode());
    }


    /**
     * @return root AVL Node of the given tree
     */
    @Override
    protected AVLTreeNode280<I> rootNode() {
        return (AVLTreeNode280<I>) super.rootNode();
    }

    /**
     * Method that performs a left rotation to a given rootNode to fix RR imbalance
     */
    private AVLTreeNode280<I> rotateLeft(AVLTreeNode280<I> root) {
        AVLTreeNode280<I> s = root.rightNode();
        root.setRightNode(s.leftNode());
        s.setLeftNode(root);
        root.setHeight(Math.max(getHeight(root.leftNode()), getHeight(root.rightNode())) + 1);
        s.setHeight(Math.max(getHeight(s.leftNode()), getHeight(s.rightNode())) + 1);
        return s;
    }

    /**
     * Rotates the given node to the right, to fix LL imbalance
     */
    private AVLTreeNode280<I> rotateRight(AVLTreeNode280<I> root) {
        AVLTreeNode280<I> r = root.leftNode();
        root.setLeftNode(r.rightNode());
        r.setRightNode(root);
        root.setHeight(Math.max(getHeight(root.leftNode()), getHeight(root.rightNode())) + 1);
        r.setHeight(Math.max(getHeight(r.leftNode()), getHeight(r.rightNode())) + 1);
        return r;
    }

    /**
     * Restores the tree's imbalance according to the balance factors of each nodes
     */
    private AVLTreeNode280<I> restoreAVLProperty(AVLTreeNode280<I> root) {
        int imbalance = getImbalance(root);
        if (Math.abs(imbalance) <= 1) {
            return root;
        }
        if (imbalance == 2) {
            if (getImbalance(root.leftNode()) >= 0) {
                return rotateRight(root);
            } else {
                root.leftNode = rotateLeft(root.leftNode()); 
                return rotateRight(root);   
            }
        } else {
            if (getImbalance(root.rightNode()) <= 0) {
                return rotateLeft(root);
            } else {
                root.rightNode = rotateRight(root.rightNode());   
                return rotateLeft(root);
            }
        }
    }

    /**
     * Inserts the given item into the
     */
    private AVLTreeNode280<I> insert(I item, AVLTreeNode280<I> root) throws DuplicateItems280Exception {
        if (root == null) {
            root = new AVLTreeNode280<>(item);  
        } else if (root.item == item) {
            throw new DuplicateItems280Exception("Item already exist! Cannot insert item that is already in a tree!");
        } else {
            if (root.item.compareTo(item) > 0) {
                root.leftNode = insert(item, root.leftNode());   
            } else {
                root.rightNode = insert(item, root.rightNode()); 
            }
        }
        root.setHeight(Math.max(getHeight(root.leftNode()), getHeight(root.rightNode())) + 1);
        return restoreAVLProperty(root);
    }

    /**
     * Deletes an item off the AVL Tree
     * @throws ItemNotFound280Exception Item is not found
     */

    private AVLTreeNode280<I> delete(I item, AVLTreeNode280<I> root) throws ItemNotFound280Exception {
        if (root == null) {
            throw new ItemNotFound280Exception("Item not Found!");
        }
        if (root.item == item) {
            if (isLeaf(root)) {
                return null;        
            } else if (root.leftNode == null) {
                return root.rightNode();  
            } else if (root.rightNode == null) {
                return root.leftNode(); 
            } else {
                I successor = findSuccessor(root);
                root.setItem(successor);
                root.setRightNode(delete(successor, root.rightNode()));

            }
        } else if (root.item.compareTo(item) > 0) {
            root.leftNode = delete(item, root.leftNode());
        } else {
            root.rightNode = delete(item, root.rightNode()); 
        }
        root.setHeight(Math.max(getHeight(root.leftNode()), getHeight(root.rightNode())) + 1);
        return restoreAVLProperty(root); 
    }

    /**
     * @return height of the node
     */
    private int getHeight(AVLTreeNode280<I> node) {
        if (node == null) {
            return 0;
        }
        return node.h;
    }

    /**
     * @return the left subtree of the given tree
     */
    @Override
    public AVLTree280<I> rootLeftSubtree() throws ContainerEmpty280Exception {
        return (AVLTree280<I>) super.rootLeftSubtree();
    }

    /**
     * @return true if the given node is a leaf
     */
    private boolean isLeaf(AVLTreeNode280<I> anode) throws NullPointerException {
        if (anode == null) {
            throw new NullPointerException("Given node is null, and does not contain any items");
        }
        return anode.leftNode == null && anode.rightNode == null;
    }

    /**
     * @return the right subtree of the given tree
     * @throws ContainerEmpty280Exception tree cannot be empty
     */
    @Override
    public AVLTree280<I> rootRightSubtree() throws ContainerEmpty280Exception {
        return (AVLTree280<I>) super.rootRightSubtree();
    }

    /**
     * @return the imbalance of the node (Balance Factor)
     */
    private int getImbalance(AVLTreeNode280<I> node) {
        return getHeight(node.leftNode()) - getHeight(node.rightNode());
    }

    /**
     * Check if the AVL tree contains an element x.
     */
    @Override
    public boolean has(I x) {
        return super.has(x);
    }

    /**
     * Prints out the tree in pre-order format
     */
    public void preOrder() {
        preOrder(this.rootNode());
    }

    /**
     * Finds the in-order Successor of the given node
     */
    private I findSuccessor(AVLTreeNode280<I> anode) {
        AVLTreeNode280<I> rightNode = anode.rightNode();  // Step one node to the right
        if (isLeaf(rightNode)) {    
            return rightNode.item;
        } else {
            while (rightNode.leftNode != null) {  
                rightNode = rightNode.leftNode(); 
            }
            return rightNode.item;
        }
    }

    /**
     * preOrder method
     */
    private void preOrder(AVLTreeNode280<I> root) {
        if (root == null) {
            return;
        } else {
            System.out.println(root);                         // Visit node
            preOrder((AVLTreeNode280<I>) root.leftNode);     // Traverse left
            preOrder((AVLTreeNode280<I>) root.rightNode);   // Traverse right
        }
    }

    public static void main(String[] args) {
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("                       Testing insert method                                  ");
        System.out.println("------------------------------------------------------------------------------");
        AVLTree280<Integer> test_tree = new AVLTree280<>();
        if (!test_tree.isEmpty()) {
            System.out.println("Tree should be empty!");
        }
        test_tree.insert(11);
        if (test_tree.rootNode().item != 11) {
            System.out.println("Item should now be 11!");
        }
        System.out.println("****Inserting to form an RR Imbalance on root Node****");
        test_tree.insert(21);
        test_tree.insert(41);
        if (test_tree.rootLeftSubtree().rootNode().item != 11) {
            System.out.println("LL Rotation should set the left subtree to now be 11");
        }
        if (test_tree.rootRightSubtree().rootNode().item != 41) {
            System.out.println("LL Rotation should set the right subtree to now be 41");
        }
        if (test_tree.rootNode().item != 21) {
            System.out.println("LL Rotation should set the right subtree to now be 21");
        }

        test_tree.preOrder();
        System.out.println(test_tree);

        System.out.println("------------------------------------------------------------------------------");
        AVLTree280<Integer> Right = new AVLTree280<>();
        System.out.println("Performing insertion using for loop that will generate a right degenerate tree ");
        for (int i = 1; i < 10; i++) {
            Right.insert(i);
        }

        Right.preOrder();
        System.out.println(Right);

        System.out.println("------------------------------------------------------------------------------");
        AVLTree280<Integer> Left = new AVLTree280<>();
        System.out.println("Performing insertion using for loop that will generate a right degenerate tree");
        for (int i = 10; i > 1; i--) {
            Left.insert(i);
        }

        Left.preOrder();
        System.out.println(Left);

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("   Performing insertion to which item to be inserted is already in the tree    ");
        try {
            Left.insert(5);
        } catch (DuplicateItems280Exception d) {
            System.out.println();
        }

        Left.preOrder();
        System.out.println(Left);

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("                          Testing Delete methods                              ");
        System.out.println("------------------------------------------------------------------------------");

        System.out.println("\n    Testing deletion on nodes with no children (Using  Left Tree)  ");
        Left.delete(2);
        if (Left.has(2)) {
            System.out.println("2 Should now be deleted!");
        }
        Left.preOrder();
        System.out.println(Left);

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("  Testing deletion on nodes with 1 child on the right node (Using Left)");
        Left.delete(3);
        if (Left.has(3)) {
            System.out.println("3 Should now be deleted!");
        }

        Left.preOrder();
        System.out.println(Left);

        System.out.println("------------------------------------------------------------------------------");
        System.out.println(" Testing deletion on nodes with 2 child on the left node (Using Left) ");
        Left.delete(10);
        Left.delete(9);
        if (Left.has(9) || Left.has(10)) {
            System.out.println("9 and 10 Should now be deleted!");
        }

        Left.preOrder();
        System.out.println(Left);

        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("        Testing deletion on nodes with 2 children (Using Right) ");
        Right.delete(4);
        if (Right.has(4)) {
            System.out.println("4 should now be deleted!");
        }

        Right.delete(8);
        if (Right.has(8)) {
            System.out.println("8 Should now be deleted!");
        }

        Right.delete(5);
        if (Right.has(5)) {
            System.out.println("5 should now be deleted");
        }

        Right.preOrder();
        System.out.println(Right);

        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("            Deleting all items on the left (Using Right)");
        Right.delete(2);
        if (Right.has(2)) {
            System.out.println("2 should now be deleted!");
        }

        Right.delete(3);
        if (Right.has(3)) {
            System.out.println("8 Should now be deleted!");
        }

        Right.delete(1);
        if (Right.has(1)) {
            System.out.println("1 should now be deleted");
        }

        Right.preOrder();
        System.out.println(Right);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("                       Deleting all items ");
        Right.delete(7);
        if (Right.has(7)) {
            System.out.println("7 should now be deleted!");
        }

        Right.delete(9);
        if (Right.has(9)) {
            System.out.println("9 Should now be deleted!");
        }

        Right.delete(6);
        if (Right.has(6)) {
            System.out.println("6 should now be deleted");
        }

        Right.preOrder();
        System.out.println(Right);

        if (!Right.isEmpty()) {
            System.out.println("Tree should now be empty!");
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("       Performing deletion to item that isn't in the tree ");
        try {
            Left.delete(42);
        } catch (ItemNotFound280Exception i) {
            System.out.println();
        }
        Left.preOrder();
        System.out.println(Left);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("                       Regression Test Complete ");
        System.out.println("-------------------------------------------------------------------------");

    }
}