package lib280.tree;
import lib280.base.NDPoint280;
import lib280.list.LinkedList280;
import lib280.exception.*;


/**
 * KDTree supports range searching
 */
public class KDTree280<I extends Comparable<? super I>> extends OrderedSimpleTree280 {
    private KDNode280<I> rootNode;  // Root node stored in the current tree
    private int dimension; // Dimension of this KDTree

    /**
     * Constructor method creates KDTree280.
     * @param pArray: Array that contains KDNodes
     */
    public KDTree280(KDNode280[] pArray) {
        if (pArray.length == 0) {
            return;
        }
        this.setRootNode(KDTree280(pArray, 0, pArray.length - 1, 0));
    }

    /**
     * Constructs our KDTree representation. This will create our KDTree according to the given pArray.
     * @param pArray: an array of KDNodes280 
     * @param left: offset of start of the sub-array
     * @param right: offset of the end of sub-array
     * @param depth: Current of the current tree.
     * @return Constructed tree from the given set of pArray
     */
    private KDNode280<I> KDTree280(KDNode280[] pArray, int left, int right, int depth) {
        this.dimension = pArray[0].getDimension();
        if (right < left) {
            return null;
        }
        int d = depth % this.getDimension();

        // Set the middle element of pArray to the position
        jSmallest(pArray, left, right, d, (left+right)/2);

        // Create a new node, and construct the subtree based on the output of jSmallest
        KDNode280<I> newNode = new KDNode280<>(this.dimension);
        newNode.setItem(pArray[(left+right)/2].item());
        newNode.setLeftNode(KDTree280(pArray, left, ((left+right)/2) - 1, depth + 1));
        newNode.setRightNode(KDTree280(pArray, ((left+right)/2) + 1, right, depth + 1));
        // Return the built tree
        return newNode; 
    }


    /**
     * Attempt to position the smallest nodePoints in our KDlist to its appropriate position
     * @param KDList: A List containing KDNodes to be inserted into our KDTree
     * @param left: Value of the left most offset. Starts at 0
     * @param right: Value of th right most offset. Starts at KDList.length - 1
     * @param depth: Starting depth (level - 1) within the tree.
     * @param j: index in which we want to update for index j to hold the appropriate value.
     */
    private void jSmallest(KDNode280[] KDList, int left, int right, int depth, int j) {
        // Get the value for dimension Depending on dimension of KDTree
        int dem = depth % this.getDimension();
        if (right > left) {
            int pivot = partition(KDList, left, right, dem);
            if (j > pivot) {
                jSmallest(KDList, pivot + 1, right,depth,j);
            }
            else if (j < pivot) {
                jSmallest(KDList, left, pivot-1, depth, j);
            }
        }
    }

    /**
     * Partition a sub-array using its last element as a pivot.
     * @param KDList: a list containing nodes to be partitioned
     * @param left: Leftmost Offset.
     * @param right" Rightmost offset.
     * @param d: index of the value to be compared,
     * @return: Offset in which the element ended up in
     */
    private int partition(KDNode280[] KDList, int left, int right, int d) {
        // The pivot is set to the rightmost offset.
        KDNode280 pivot = KDList[right];
        // The swap offset is set to the leftmost offset
        int swap= left;
        for (int i = left; i < right; i++) {
            // If the pivot is similar to the element at index i, then there is a duplicate.
            if (KDList[i].item().compareTo(pivot.item()) == 0) {
                throw new DuplicateItems280Exception("Each point has to be unique and cannot have duplicates for any points.");
            }
            // If the pivot item is greater than or equal to item in index i in respect to the dimension
            if (pivot.item().compareByDim(d, KDList[i].item()) >= 0) {
                // Swap the current swapOffset item with the item at offset i
                swap(KDList, i, swap);
                // Move swap to the right
                swap += 1;
            }
        }
        // Switch the right item with the item contained in index swapOffset
        swap(KDList, right, swap);
        // Return the offset where pivot was at the last
        return swap;
    }


    /**
     * Swaps value in index with value in swapOffset
     * @param KDList list containing the items
     * @param index index of item
     * @param swap index of item in which index is to be swapped with
     */
    private void swap(KDNode280[] KDList, int index, int swap) {
        KDNode280 holder = KDList[index];
        KDList[index] = KDList[swap];
        KDList[swap] = holder;
    }

    /**
     * Public version of SearchRange to preserve encapsulation of the actual code
     * @param h Starting range.
     * @param l End range.
     * @return a String showing all the values between lo and hi
     */
    public String searchRange(KDNode280<I> l, KDNode280<I> h) {
        // CreateS a linked list that will hold items
        LinkedList280<KDNode280> range = new LinkedList280<>();
        // Performs the range search
        searchRange(this.rootNode, l, h, 0, range);
        // Convert the stuff in linked list to string
        range.goFirst();
        String result = "";
        while (range.itemExists()) {
            result += range.item() + "\n";
            range.goForth();
        }
        // Returns the result in string format
        return result;
    }

    /**
     * searchRange method that performs the actual range search.
     * @param root: Root of the node to be inspected
     * @param l: lower bound value of the first set of nodes
     * @param h: Higher bound value of the second set of node
     * @param depth: Current level being inspected
     * @param range: A linked list to store the results in
     */
    private void searchRange(KDNode280<I> root,KDNode280<I> l,KDNode280<I> h, int depth,LinkedList280<KDNode280> range) {
        // If the root is null, return the empty set
        if (root == null) {
            return;
        }
        // The point to be checked. If 0, then the point being inspected is the x value
        int currentDepth = depth % dimension;
        // get the nth point stored in the NDPoint of the root
        Double splitValue = root.item().idx(currentDepth);
        // Get the lower bound
        Double min = root.item().idx(currentDepth);
        // Get the upper bound
        Double max = root.item().idx(currentDepth);
        // If lower bound is greater than the split value, then recurse to the left side 
        if (min >= splitValue) {
           searchRange(root.getLeftNode(),l,h,depth + 1, range);
        }
        // If high bound is less than or equal the split value, recurse right side
        if (max <= splitValue) { 
           searchRange(root.getRightNode(),l,h,depth + 1, range);
        }
        if (withinRange(l.item(), root.item(), h.item())) {
           range.insert(root);
       }
    }

    /**
     * Checks if y is within range of x and z
     * @param x 1st point
     * @param y 2nd point
     * @param z 3rd point
     * @return true if points of y are in range
     */
    private
    boolean withinRange(NDPoint280 x, NDPoint280 y, NDPoint280 z) {
        boolean withInRange = true;
        for (int i = 0; i < this.dimension; i++) {
            if (x.compareByDim(i, y) <= 0 && y.compareByDim(i, z) <= 0) {
                withInRange = true;
            }
            else {
                withInRange = false;
            }
            if (!withInRange) {break;}
        }
        return withInRange;
    }
    
    /**
     * Sets root of this KDTree
     * @param root: Node to be set as this.rootNode
     */
    private void setRootNode(KDNode280<I> root){
        this.rootNode = root;
    }

    /**
     * @return dimension of the KDTree
     */
    private int getDimension() {
        return this.dimension;
    }

    /**
     * toString method will print the KDTree in proper format
     * @return to string representation of our tree. To string by level is inside KDNode.
     */
    @Override
    public String toString() {
        return this.rootNode.toStringByLevel(1);
    }

    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("    Testing Creation of KDTree using 2 dimensional points    ");
        System.out.println("-------------------------------------------------------------");

        KDNode280[] t1 = new KDNode280[12];
        Double[][] setofDouble = {{6.0, 3.0}, {10.0, 11.0}, {12.0, 2.0},{5.0, 4.0},
                {3.0, 13.0}, {4.0, 8.0}, {2.0, 6.0}, {4.2, 12.2},{7.0, 4.3},{8.0, 5.1} ,{8.1, 1.8} ,{10.1, 11.4} };
        // Initialize all the nodes contained in our list to kd nodes of dimension 2
        for (int k = 0; k < t1.length; k++) {
            t1[k] = new KDNode280<>(setofDouble[k]);
        }
        System.out.println("-----------------------");
        System.out.println("    Input 2D Points    ");
        System.out.println("-----------------------");
        for (int k = 0; k<12; k++) {
            System.out.println(k+1 + "-> " + t1[k]);
        }
        System.out.println("----------------------------------------------");
        System.out.println("    The 2D tree built from these points is    ");
        System.out.println("----------------------------------------------");

        KDTree280 testTree1 = new KDTree280(t1);
        System.out.println(testTree1);

        System.out.println("-------------------------------------------------------------");
        System.out.println("          Sample 2 KDTree using 2 dimensional points         ");
        System.out.println("-------------------------------------------------------------");

        KDNode280[] t2 = new KDNode280[7];
        Double[][] setOfDouble2 = {{6.0, 3.0}, {10.0, 11.0}, {12.0, 2.0},{5.0, 4.0},
                {3.0, 13.0}, {4.0, 8.0}, {2.0, 6.0}};
        // Initialize all the nodes contained in our list to kd nodes of dimension 2
        for (int k = 0; k < t2.length; k++) {
            t2[k] = new KDNode280<>(setOfDouble2[k]);
        }
        System.out.println("-----------------------");
        System.out.println("    Input 2D Points    ");
        System.out.println("-----------------------");
        for (int k = 0; k<7; k++) {
            System.out.println(k+1 + "-> " + t2[k]);
        }
        System.out.println("----------------------------------------------");
        System.out.println("    The 2D tree built from these points is    ");
        System.out.println("----------------------------------------------");

        KDTree280 testTree2 = new KDTree280(t2);
        System.out.println(testTree2);

        System.out.println("-------------------------------------------------------------");
        System.out.println("    Testing Creation of KDTree using 3 dimensional points    ");
        System.out.println("-------------------------------------------------------------");
        KDNode280[] t3d1 = new KDNode280[8];
        Double[][] setOf3Doubles = {{1.0, 12.0, 1.0},{18.0, 1.0, 2.0}, {3.0, 13.0, 16.0},{7.0, 3.0, 3.0}, {4.0, 8.0, 5.0},
                                    {16.0, 4.0, 4.0}, {4.0, 6.0, 1.0}, {5.0, 5.0, 17.0}};
        for (int k = 0; k < t3d1.length; k++) {
            t3d1[k] = new KDNode280<>(setOf3Doubles[k]);
        }
        System.out.println("-----------------------");
        System.out.println("    Input 3D Points    ");
        System.out.println("-----------------------");
        for (int k = 0; k<8; k++) {
            System.out.println(k+1 + "-> " + t3d1[k]);
        }
        System.out.println("----------------------------------------------");
        System.out.println("    The 3D tree built from these points is    ");
        System.out.println("----------------------------------------------");

        KDTree280 testTree3D1 = new KDTree280(t3d1);
        System.out.println(testTree3D1);

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("   Looking for points between (0.0 , 1.0 , 0.0) and (4.0 , 6.0 , 3.0)  ");
        System.out.println("-----------------------------------------------------------------------");
        Double[] l = {0.0 , 1.0 , 0.0};
        Double[] h = {4.0 , 6.0 , 3.0};
        KDNode280 lowNode = new KDNode280(l);
        KDNode280 highNode = new KDNode280(h);
        System.out.println(testTree3D1.searchRange(lowNode, highNode));

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("    Looking for points between (0.0 , 1.0 , 0.0) and (8.0 , 7.0 , 4.0).  ");
        System.out.println("-------------------------------------------------------------------------");
        Double[] h2 = {8.0, 7.0, 4.0};
        KDNode280 newHigh = new KDNode280(h2);
        System.out.println(testTree3D1.searchRange(lowNode, newHigh));

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("    Looking for points between (0.0 , 1.0 , 0.0) and (17.0 , 9.0 , 10.0).   ");
        System.out.println("----------------------------------------------------------------------------");
        Double[] h3 = {17.0, 10.0, 11.0};
        KDNode280 newHigh1 = new KDNode280(h3);
        System.out.println(testTree3D1.searchRange(lowNode, newHigh1));


        System.out.println("----------------------------------------------------------------");
        System.out.println("    Testing Creation of 2nd KDTree using 3 dimensional points   ");
        System.out.println("----------------------------------------------------------------");
        KDNode280[] t3d2 = new KDNode280[8];
        Double[][] setOf3Doubles2 = {{1.0, 12.0, 0.0},{18.0, 1.0, 2.0}, {2.0, 13.0, 16.0},{7.0, 3.0, 3.0}, {4.0, 8.0, 5.0},
                {16.0, 4.0, 4.0}, {4.0, 6.0, 1.0}, {5.0, 5.0, 17.0}};
        for (int k = 0; k < t3d2.length; k++) {
            t3d2[k] = new KDNode280<>(setOf3Doubles2[k]);
        }

        System.out.println("-----------------------");
        System.out.println("    Input 3D Points    ");
        System.out.println("-----------------------");
        for (int k = 0; k<8; k++) {
            System.out.println(k+1 + "-> " + t3d2[k]);
        }
        System.out.println("----------------------------------------------");
        System.out.println("    The 3D tree built from these points is    ");
        System.out.println("----------------------------------------------");

        KDTree280 testTree3D2 = new KDTree280(t3d2);
        System.out.println(testTree3D2);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("    Looking for points between (0.0 , 1.0 , 0.0) and (4.0 , 6.0 , 3.0)   ");
        System.out.println("-------------------------------------------------------------------------");
        Double[] l2 = {0.0 , 1.0 , 0.0};
        Double[] h4 = {4.0 , 6.0 , 3.0};
        KDNode280 lowNode2 = new KDNode280(l2);
        KDNode280 highNode2 = new KDNode280(h4);
        System.out.println(testTree3D1.searchRange(lowNode2, highNode2));

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("    Looking for points between (0.0 , 1.0 , 0.0) and (8.0 , 7.0 , 4.0).  ");
        System.out.println("-------------------------------------------------------------------------");
        Double[] h5 = {8.0, 7.0, 4.0};
        KDNode280 newHigh2 = new KDNode280(h5);
        System.out.println(testTree3D1.searchRange(lowNode2, newHigh2));

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("    Looking for points between (0.0 , 1.0 , 0.0) and (17.0 , 9.0 , 10.0).   ");
        System.out.println("----------------------------------------------------------------------------");
        Double[] h6 = {17.0, 10.0, 11.0};
        KDNode280 newHigh3 = new KDNode280(h6);
        System.out.println(testTree3D1.searchRange(lowNode2, newHigh3));

    }

}
