
package JDev.Trees;

import java.util.ArrayList;

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree {

    /**
     * Construct the tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert(Comparable x) {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove(Comparable x) {
        root = remove(x, root);
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    private Comparable findMin() {
        return elementAt(findMin(root));
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    private Comparable findMax() {
        return elementAt(findMax(root));
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return the matching item or null if not found.
     */
    public Comparable find(Comparable x) {
        return elementAt(find(x, root));
    }

     /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @param isBasicDataType true or false is oke
     * @return @return true if found or false if not found
     */
    public Comparable find(Comparable x, boolean isBasicType) {
        try {
            Comparable t = find(x);

            return t != null ? true : false;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Remove all item in the tree
     */
    public void removeAll() {
        root = null;
    }

     /**
     * Get ArrayList of item in tree
     * @param visitMode 
     * @return ArrayList of item in tree
     */
    public ArrayList toArrayList(VisitMode visitMode) {
        ArrayList arrayList = new ArrayList();

        Visit(visitMode, arrayList);

        return arrayList;
    }

     /**
     * Get Array of item in tree
     * @param visitMode 
     * @return Array of item in tree
     */
    public Object[] toArray(VisitMode visitMode) {
        return toArrayList(visitMode).toArray();
    }

        /**
     * Get Array of item in tree
     * @param visitMode 
     * @param  a array of item type
     * @return Array of item in tree
     */
    public <T> T[] toArray(VisitMode visitMode, T[] a) {
        return (T[]) toArrayList(visitMode).toArray(a);
    }

    private void Visit(VisitMode visitMode, ArrayList arrayList) {
        if (visitMode == VisitMode.LEFT_ROOT_RIGHT) {
            LeftRootRightVisit(root, arrayList);
        }

        if (visitMode == VisitMode.RIGHT_ROOT_LEFT) {
            RightRootLeftVisit(root, arrayList);
        }

        if (visitMode == VisitMode.ROOT_LEFT_RIGHT) {
            RootLeftRightVisit(root, arrayList);
        }
    }

    private void LeftRootRightVisit(BinaryNode node, ArrayList arrayList) {
        if (node == null) {
            return;
        }

        LeftRootRightVisit(node.left, arrayList);
        arrayList.add(node.data);
        LeftRootRightVisit(node.right, arrayList);
    }

    private void RightRootLeftVisit(BinaryNode node, ArrayList arrayList) {
        if (node == null) {
            return;
        }

        RightRootLeftVisit(node.right, arrayList);
        arrayList.add(node.data);
        RightRootLeftVisit(node.left, arrayList);
    }

    private void RootLeftRightVisit(BinaryNode node, ArrayList arrayList) {
        if (node == null) {
            return;
        }
        arrayList.add(node.data);

        RootLeftRightVisit(node.left, arrayList);

        RootLeftRightVisit(node.right, arrayList);
    }

   
    /**
     * Check tree is empty
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Internal method to get element field.
     * @param t the node.
     * @return the element field or null if t is null.
     */
    private Comparable elementAt(BinaryNode t) {
        return t == null ? null : t.data;
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private BinaryNode insert(Comparable x, BinaryNode t) {
        if (t == null) {
            t = new BinaryNode(x, null, null);
        } else if (x.compareTo(t.data) < 0) {
            t.left = insert(x, t.left);
        } else if (x.compareTo(t.data) > 0) {
            t.right = insert(x, t.right);
        } else {
        }// Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private BinaryNode remove(Comparable x, BinaryNode t) {
        if (t == null) {
            return t;   // Item not found; do nothing
        }
        if (x.compareTo(t.data) < 0) {
            t.left = remove(x, t.left);
        } else if (x.compareTo(t.data) > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) // Two children
        {
            t.data = findMin(t.right).data;
            t.right = remove(t.data, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private BinaryNode findMin(BinaryNode t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private BinaryNode findMax(BinaryNode t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return node containing the matched item.
     */
    private BinaryNode find(Comparable x, BinaryNode t) {
        if (t == null) {
            return null;
        }
        if (x.compareTo(t.data) < 0) {
            return find(x, t.left);
        } else if (x.compareTo(t.data) > 0) {
            return find(x, t.right);
        } else {
            return t;    // Match
        }
    }
    private BinaryNode root;
}
