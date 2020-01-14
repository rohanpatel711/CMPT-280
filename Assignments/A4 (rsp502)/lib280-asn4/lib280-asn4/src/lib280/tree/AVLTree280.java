package lib280.tree;

import lib280.base.Container280;
import lib280.base.Dispenser280;
import lib280.base.Searchable280;
import lib280.exception.*;


public class AVLTree280<I extends Comparable<? super I>>
        extends OrderedSimpleTree280<I>
        implements Dispenser280<I>, Searchable280<I> {

    protected AVLNode280<I> rootNode;

    public AVLTree280(){
        rootNode = null;
    }


    /**	Create a lib280.tree with lt, r, and rt being the left subtree, root item, and right subtree
     respectively
    */
    public AVLTree280(AVLTree280<I> lt, I r, AVLTree280<I> rt)
    {
        rootNode = createNewNode(r);
        setRootLeftSubtree(lt);
        setRootRightSubtree(rt);
    }

    /**	Create a new node */
    protected AVLNode280<I> createNewNode(I item)
    {
        return new AVLNode280<I>(item);
    }

    /** This functions returns root node */
    protected AVLNode280<I> rootNode(){
        return this.rootNode;
    }

    /**	Contents of the root item. */
    public I rootItem() throws ContainerEmpty280Exception
    {
        if (isEmpty()) {
            throw new ContainerEmpty280Exception("Cannot access the root of an empty AVL Tree");
        }
        return rootNode.item();
    }

    /**	Set contents of the root to x. */
    public void setRootItem(I x) throws ContainerEmpty280Exception
    {
        if (isEmpty())
            throw new ContainerEmpty280Exception("Can't set the root of an empty lib280.tree.");

        rootNode.setItem(x);
    }

    /**	Set root node to new node. */
    protected void setRootNode(AVLNode280<I> newNode)
    {
        rootNode = newNode;
    }

    /**	@return tree's left subtree */
    public AVLTree280<I> rootLeftSubtree() throws ContainerEmpty280Exception
    {
        if (isEmpty())
            throw new ContainerEmpty280Exception("the subtree doesn't exist as the AVL tree is empty.");
        AVLTree280<I> r = this.clone();
        r.clear();
        r.setRootNode(rootNode.leftNode());
        return r;
    }

    /**	@return tree's right subtree */
    public AVLTree280<I> rootRightSubtree() throws ContainerEmpty280Exception
    {
        if (isEmpty())
            throw new ContainerEmpty280Exception("the subtree doesn't exist as the AVL tree is empty.");
        AVLTree280<I> r = this.clone();
        r.clear();
        r.setRootNode(rootNode.rightNode());
        return r;
    }
    
    /**	Delete all items from the data structure. */ 
    public void clear()
    {
        setRootNode(null);
    }


    /**	Set the left subtree to t */
    public void setRootLeftSubtree(AVLTree280<I>  tree) throws ContainerEmpty280Exception
    {
        if (isEmpty())
            throw new ContainerEmpty280Exception("Can't set subtree of an AVL tree is empty.");

        if (tree != null)
            rootNode.setLeftNode(tree.rootNode());
        else
            rootNode.setLeftNode(null);
    }



    /**	Set the right subtree */
    public void setRootRightSubtree(AVLTree280<I> t) throws ContainerEmpty280Exception
    {
        if (isEmpty())
            throw new ContainerEmpty280Exception("Cannot set subtree of an empty lib280.tree.");

        if (t != null)
            rootNode.setRightNode(t.rootNode());
        else
            rootNode.setRightNode(null);
    }


    /**	Insert x into the lib280.tree. <br>
     Analysis : Time = O(h) worst case, where h = height of the lib280.tree */
    public void insert(I data){
        if (isEmpty()) {
            rootNode = createNewNode(data);
        }
        else{
            insert(rootNode, data);
        }
    }

    /**	Insert data into AVL tree */
    protected void insert(AVLNode280<I> cur, I data) {
        if (data.compareTo(cur.item()) < 0) {
            if (cur.leftNode() == null) {
                AVLNode280<I> item = createNewNode(data);
                cur.setLeftNode(item);
            }
            else
                insert(cur.leftNode(), data);

            cur.l_Height = height(cur.leftNode);

        }
        else {
            if(cur.rightNode() == null){
                AVLNode280<I> newItem = createNewNode(data);
                cur.setRightNode(newItem);
            }
            else
                insert(cur.rightNode(), data);

            cur.r_Height = height(cur.rightNode);

        }
        restoreAVL(cur, cur.parent);
    }



    /**	Delete the current item, making its replacement the current item. */
    public void deleteItem(I data) throws ContainerEmpty280Exception{
        if(!itemExists())
            throw new NoCurrentItem280Exception("Item doesn't exist");
        else if(this.isEmpty()){
            throw new ContainerEmpty280Exception("Tree is empty");
        }
        else{
                search(data);
                deleteItem(data);
        }
    }

    @Override
    public void deleteItem() throws NoCurrentItem280Exception {
        if(!itemExists())
            throw new NoCurrentItem280Exception("Item doesn't exist");

        boolean foundReplacement = false ;
        BinaryNode280<I> replacementNode = null;

        if (cur.rightNode() == null)
        {
            replacementNode = cur.leftNode();
            foundReplacement = true;
        }
        else if (cur.leftNode() == null)
        {
            replacementNode = cur.rightNode();
            foundReplacement = true;
        }
        else
            foundReplacement = false;

        if (foundReplacement)
        {
            if (parent == null)
                setRootNode(replacementNode);
            else if (parent.leftNode() == cur)
                parent.setLeftNode(replacementNode);
            else
                parent.setRightNode(replacementNode);
            cur = replacementNode;

            AVLNode280<I> newParent = (AVLNode280) parent;
            AVLNode280<I> newCurrent = (AVLNode280) cur;

            newCurrent.r_Height = height(newCurrent.rightNode);
            newCurrent.l_Height = height(newCurrent.leftNode);

            if(cur != rootNode){
                newParent.r_Height = height(newParent.rightNode);
                newParent.l_Height = height(newParent.leftNode);
            }

            restoreAVL(newCurrent, newParent);
        }
        else
        {
            BinaryNode280<I> replacementParent = cur;
            BinaryNode280<I> replacementCurrent = replacementParent.rightNode();
            while (replacementCurrent.leftNode() != null)
            {
                replacementParent = replacementCurrent;
                replacementCurrent = replacementParent.leftNode();
            }
            cur.setItem(replacementCurrent.item());
            BinaryNode280<I> saveParent = parent;
            BinaryNode280<I> saveCur = cur;
            parent = replacementParent;
            cur = replacementCurrent;
            deleteItem();
            parent = saveParent;
            cur = saveCur;

            AVLNode280<I> parent2 = (AVLNode280) parent;
            AVLNode280<I> child2 = (AVLNode280) cur;

            child2.r_Height = height(child2.rightNode);
            child2.l_Height = height(child2.leftNode);

            if(cur != rootNode){
                parent2.r_Height = height(parent2.rightNode);
                parent2.l_Height = height(parent2.leftNode);
            }

            restoreAVL(child2, parent2);

        }

    }

    @Override
    public void search(I data) {
        boolean found = false;
        if (!searchesContinue || above()) {
            parent = null;
            cur = rootNode;
        }
        else if (!below()) {
            parent = cur;
            cur = cur.rightNode();
        }
        while (!found && itemExists()) {
            if (data.compareTo(item()) < 0) {
                parent = cur;
                cur = parent.leftNode();
            }
            else if (data.compareTo(item()) > 0) {
                parent = cur;
                cur = parent.rightNode();
            }
            else
                found = true;
        }
    }

    /** @return true if cur is leaf, false otherwise **/
    public boolean isLeaf(AVLNode280<I> cur){
        return (cur.leftNode() == null && cur.rightNode() == null);
    }

    /**	Is the lib280.tree empty?. **/
    public boolean isEmpty() {
        return rootNode == null;
    }

    /**	Implements a left rotation **/
    protected void leftRotate(AVLNode280<I> critical, AVLNode280<I> top){
        AVLNode280<I> newRootNode = critical.rightNode;
        critical.rightNode = newRootNode.leftNode;
        critical.r_Height = height(critical.rightNode);

        if(newRootNode.leftNode != null){
            newRootNode.leftNode.parent = critical;

        }

        newRootNode.parent = critical.parent;

        if(top==null)
            this.rootNode = newRootNode;
        else{
            if (top.leftNode.item() == critical.item()) {
                critical.parent.leftNode = newRootNode;
                top.l_Height = height(top.leftNode);
            }
            else {
                critical.parent.rightNode = newRootNode;
                top.r_Height = height(top.rightNode);
            }
        }

        newRootNode.leftNode = critical;
        critical.parent = newRootNode;
        newRootNode.l_Height = height(newRootNode.leftNode);
    }

    /**	Implements a right rotation on a critical tree with LL imbalance **/
    protected void rightRotate(AVLNode280<I> critical, AVLNode280<I> top){

        AVLNode280<I> newRootNode = critical.leftNode;
        critical.leftNode = newRootNode.rightNode;
        critical.l_Height = height(critical.leftNode);

        if(newRootNode.rightNode != null){
            newRootNode.rightNode.parent = critical;
        }
        newRootNode.parent = critical.parent;

        if(top==null){
            this.rootNode = newRootNode;
        }
        else{
            if (top.leftNode.item() == critical.item()) {
                critical.parent.leftNode = newRootNode;
                top.l_Height = height(top.leftNode);
            } else {
                critical.parent.rightNode = newRootNode;
                top.r_Height = height(top.rightNode);
            }
        }

        newRootNode.rightNode = critical;
        critical.parent = newRootNode;

        newRootNode.r_Height = height(newRootNode.rightNode);
    }

    /**	Implements a double left rotation **/
    protected void doubleLeft(AVLNode280<I> critical, AVLNode280<I> p){
        rightRotate(critical.rightNode, p);
        leftRotate(critical, p);
    }


    @Override
    protected String toStringByLevel(int i) {
        StringBuffer blanks = new StringBuffer((i - 1) * 5);
        for (int j = 0; j < i - 1; j++)
            blanks.append("     ");

        String result = new String();
        if (!isEmpty() && (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty()))
            result += rootRightSubtree().toStringByLevel(i+1);

        result += "\n" + blanks + i + ": " ;
        if (isEmpty())
            result += "-";
        else
        {
            result += " [" + rootItem() + "]   L:" +rootNode.l_Height + " R:" + rootNode.r_Height;
            if (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty())
                result += rootLeftSubtree().toStringByLevel(i+1);
        }
        return result;
    }


    /**	Implements a double right rotation **/
    protected void doubleRight(AVLNode280<I> critical, AVLNode280<I> p){
        leftRotate(critical.leftNode, p);
        rightRotate(critical, p);
    }

    /**	Retrieves height of a tree **/
    public int height(AVLNode280<I> root){
        if(root == null){
            return 0;
        }
        else {
            return 1 + Math.max(root.l_Height, root.r_Height);
        }
    }
    

    @SuppressWarnings("unchecked")
    public int getImbalance(AVLNode280<I> critical){
        /* if positive, left heavy. if negative, right heavy*/
        return height(critical.leftNode()) - height( critical.rightNode());
    }


    /**	Finds the critical node and calls appropriate rotation to correct tree **/
    protected void restoreAVL(AVLNode280<I> critical, AVLNode280<I> parent){
        int imbalance = getImbalance(critical);
        if(parent == null){
            return;
        }

        else{
            if ( Math.abs(imbalance) <= 1){
                return;
            }
            if (imbalance == 2){
                if(getImbalance(critical.leftNode()) >= 0){
                    rightRotate(critical, parent);
                }
                else{
                    leftRotate(critical.leftNode(), parent);
                    rightRotate(critical, parent);
                }
            }
            else{
                if (getImbalance(critical.rightNode()) <=0){
                    leftRotate(critical, parent);
                }
                else{
                    rightRotate(critical.rightNode(), parent);
                    leftRotate(critical, parent);
                }
            }
        }
    }

    public AVLTree280<I> clone()
    {
        return (AVLTree280<I>) super.clone();
    }

    public static void main(String args[]) {
        System.out.println("----------Testing Begins----------");
        System.out.println("<Level number> : [ item ]     L: l_Height   R: r_Height ");
        System.out.println("---------Testing of Insert Function---------");
        AVLTree280<Integer> T = new AVLTree280<Integer>();
        System.out.println("Testing insert functions");
        System.out.println("Inserting 16 ");
        T.insert(16);
        System.out.println(T.toStringByLevel());
        System.out.println("--------------------------------------------");
        
        System.out.println("Inserting 18 ");
        T.insert(18);
        System.out.println(T.toStringByLevel());
        System.out.println("=====");
        System.out.println();

        System.out.println("Inserting 22 ");
        T.insert(22);
        System.out.println(T.toStringByLevel());
        System.out.println("=====");
        System.out.println();

        System.out.println("Inserting 11 ");
        T.insert(11);
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");
        System.out.println();

        System.out.println("Inserting 8 ");
        T.insert(8);
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");
        System.out.println();

        
        System.out.println("Testing for insertion of duplicates ");
        System.out.println("Inserting 11 ");
        T.insert(11);
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");
        System.out.println();

        System.out.println("Inserting 34 ");
        T.insert(34);
        System.out.println("This is tree T");
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");

        System.out.println("Inserting 34 ");
        T.insert(34);
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");

        System.out.println("Testing right rotation");
        T.insert(148);
        T.insert(148);
        T.insert(147);
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");

        System.out.println("Testing for RL imbalance or double left");
        T.insert(240);
        T.insert(120);
        System.out.println("After double left");
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");


        System.out.println("Testing for LR imbalance or double right");
        System.out.println("Using tree test2 for this test");
        AVLTree280<Integer> test2 = new AVLTree280<Integer>();
        test2.insert(110);
        test2.insert(180);
        test2.insert(80);
        test2.insert(120);
        System.out.println("This tree test2");
        System.out.println(T.toStringByLevel());
        System.out.println("-------------------------------------");
        test2.insert(110);
        test2.insert(130);
        System.out.println("After double right");
        System.out.println(T.toStringByLevel());


        System.out.println("Testing search functions");
        System.out.println("-------------------------------------");

        System.out.println("Searching for 180");
        T.search(18);
        if(T.item() == 18) {
            System.out.println("The item searched is: " + T.item());
        }

        System.out.println("Testing Delete Function");
        System.out.println("-------------------------------------");


        T.deleteItem(240);
        System.out.println(T.toStringByLevel());

    }

}