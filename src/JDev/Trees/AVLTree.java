package JDev.Trees;

import java.util.ArrayList;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 */
public class AVLTree {

    private AVLNode root;

    /**
     * Add a new element with key "x" into the tree.
     * 
     * @param x
     *            The key of the new node.
     */
    public void insert(Comparable x) {
        // create new node
        AVLNode n = new AVLNode(x);
        // start recursive procedure for inserting the node
        insert(this.root, n);
    }

    /**
     * Recursive method to insert a node into a tree.
     * 
     * @param node The node currently compared, usually you start with the root.
     * @param newNode The node to be inserted.
     */
    private void insert(AVLNode node, AVLNode newNode) {
        // If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
        if (node == null) {
            this.root = newNode;
        } else {

            // If compare node is smaller, continue with the left node
            int result = newNode.data.compareTo(node.data);

            if (result < 0) {
                if (node.left == null) {
                    node.left = newNode;
                    newNode.parent = node;

                    // Node is inserted now, continue checking the balance
                    recursiveBalance(node);
                } else {
                    insert(node.left, newNode);
                }

            } else if (result > 0) {
                if (node.right == null) {
                    node.right = newNode;
                    newNode.parent = node;

                    // Node is inserted now, continue checking the balance
                    recursiveBalance(node);
                } else {
                    insert(node.right, newNode);
                }
            } else {
                // do nothing: This node already exists
            }
        }
    }

    /**
     * Check the balance for each node recursivly and call required methods for balancing the tree until the root is reached.
     * 
     * @param node : The node to check the balance for, usually you start with the parent of a leaf.
     */
    private void recursiveBalance(AVLNode node) {

        // we do not use the balance in this class, but the store it anyway
        setBalance(node);
        int balance = node.balance;

        // check the balance
        if (balance == -2) {

            if (height(node.left.left) >= height(node.left.right)) {
                node = rotateRight(node);
            } else {
                node = doubleRotateLeftRight(node);
            }
        } else if (balance == 2) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node = doubleRotateRightLeft(node);
            }
        }

        // we did not reach the root yet
        if (node.parent != null) {
            recursiveBalance(node.parent);
        } else {
            this.root = node;
        }
    }

    /**
     * Removes a node from the tree, if it is existent.
     * @param x The KEY of node to remove.
     */
    public void remove(Comparable x) {
        // First we must find the node, after this we can delete it.
        remove(this.root, x);
    }

    /**
     * Removes all element
     */
    public void removeAll() {
        root = null;
    }

    /**
     * Check tree is empty
     * @return
     */
    public boolean isEmpty() {
        return root == null ? true : false;
    }

    /**
     * Finds a node and calls a method to remove the node.
     * 
     * @param node The node to start the search.
     * @param x The KEY of node to remove.
     */
    private void remove(AVLNode node, Comparable x) {
        if (node == null) {
            // der Wert existiert nicht in diesem Baum, daher ist nichts zu tun
            return;
        } else {
            int result = node.data.compareTo(x);
            if (result > 0) {
                remove(node.left, x);
            } else if (result < 0) {
                remove(node.right, x);
            } else if (result == 0) {
                // we found the node in the tree.. now lets go on!
                remove(node);
            }
        }
    }

    /**
     * Removes a node from a AVL-Tree, while balancing will be done if necessary.
     * 
     * @param node The node to be removed.
     */
    private void remove(AVLNode node) {
        AVLNode vicTim;
        // at least one child of q, q will be removed directly
        if (node.left == null || node.right == null) {
            // the root is deleted
            if (node.parent == null) {
                this.root = null;
                node = null;
                return;
            }
            vicTim = node;
        } else {
            // q has two children –> will be replaced by successor
            vicTim = find(node);
            node.data = vicTim.data;
        }


        AVLNode p;
        if (vicTim.left != null) {
            p = vicTim.left;
        } else {
            p = vicTim.right;
        }

        if (p != null) {
            p.parent = vicTim.parent;
        }

        if (vicTim.parent == null) {
            this.root = p;
        } else {
            if (vicTim == vicTim.parent.left) {
                vicTim.parent.left = p;
            } else {
                vicTim.parent.right = p;
            }
            // balancing must be done until the root is reached.
            recursiveBalance(vicTim.parent);
        }
        vicTim = null;
    }

    /**
     * Left rotation using the given node.
     * 
     * 
     * @param node The node for the rotation.
     * 
     * @return The root of the rotated tree.
     */
    private AVLNode rotateLeft(AVLNode node) {

        AVLNode v = node.right;
        v.parent = node.parent;

        node.right = v.left;

        if (node.right != null) {
            node.right.parent = node;
        }

        v.left = node;
        node.parent = v;

        if (v.parent != null) {
            if (v.parent.right == node) {
                v.parent.right = v;
            } else if (v.parent.left == node) {
                v.parent.left = v;
            }
        }

        setBalance(node);
        setBalance(v);

        return v;
    }

    /**
     * Right rotation using the given node.
     * 
     * @param node The node for the rotation
     * 
     * @return The root of the new rotated tree.
     */
    private AVLNode rotateRight(AVLNode node) {

        AVLNode v = node.left;
        v.parent = node.parent;

        node.left = v.right;

        if (node.left != null) {
            node.left.parent = node;
        }

        v.right = node;
        node.parent = v;


        if (v.parent != null) {
            if (v.parent.right == node) {
                v.parent.right = v;
            } else if (v.parent.left == node) {
                v.parent.left = v;
            }
        }

        setBalance(node);
        setBalance(v);

        return v;
    }

    /**
     * 
     * @param node The node for the rotation.
     * @return The root after the double rotation.
     */
    private AVLNode doubleRotateLeftRight(AVLNode node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    /**
     * 
     * @param node The node for the rotation.
     * @return The root after the double rotation.
     */
    private AVLNode doubleRotateRightLeft(AVLNode node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    /**
     * Returns the successor of a given node in the tree (search recursivly).
     * 
     * @param node The predecessor.
     * @return The successor of node q.
     */
    private AVLNode find(AVLNode node) {
        if (node.right != null) {
            AVLNode r = node.right;
            while (r.left != null) {
                r = r.left;
            }
            return r;
        } else {
            AVLNode p = node.parent;
            while (p != null && node == p.right) {
                node = p;
                p = node.parent;
            }
            return p;
        }
    }

    /**
     * Calculating the "height" of a node.
     * 
     * @param node
     * @return The height of a node (-1, if node is not existent eg. NULL).
     */
    private int height(AVLNode node) {
        if (node == null) {
            return -1;
        }
        if (node.left == null && node.right == null) {
            return 0;
        } else if (node.left == null) {
            return 1 + height(node.right);
        } else if (node.right == null) {
            return 1 + height(node.left);
        } else {
            return 1 + maximum(height(node.left), height(node.right));
        }
    }

    /**
     * Return the maximum of two integers.
     */
    private int maximum(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * Find element
     * @param x Key to find.
     * @return Key if found, null if not found. (if x is a basic type, method will throw exception if not found)
     */
    public Comparable find(Comparable x) {
        return find(root, x);
    }

    /**
     * Find element if it is a basic type
     * @param x Key to find
     * @param isBasicDataType true or false is oke
     * @return @return true if exist (false if not exist)
     */
    public boolean find(Comparable x, boolean isBasicDataType) {
        try {
            Comparable temp = find(root, x);
            return temp == null ? false : true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Find element
     * @param x Key to find
     * @param node First node to find
     * @return Key if exist (null if not exist)
     */
    private Comparable find(AVLNode node, Comparable x) {
        int result = 0;
        AVLNode cur = node;
        while (cur != null) {
            result = x.compareTo(cur.data);

            if (result == 0) {
                return cur.data;
            } else if (result > 0) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return null;
    }

    /**
     * Get ArrayList of element in tree
     * @param visitMode 
     * @return ArrayList of element in tree
     */
    public ArrayList toArrayList(VisitMode visitMode) {
        ArrayList arrayList = new ArrayList();

        Visit(visitMode, arrayList);

        return arrayList;
    }

    /**
     * Get Array of element in tree
     * @param visitMode 
     * @return Array of element in tree
     */
    public Object[] toArray(VisitMode visitMode) {
        return toArrayList(visitMode).toArray();
    }

    /**
     * Get Array of element in tree
     * @param visitMode 
     * @param  a array of element type
     * @return Array of element in tree
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

    private void LeftRootRightVisit(AVLNode node, ArrayList arrayList) {
        if (node == null) {
            return;
        }

        LeftRootRightVisit(node.left, arrayList);
        arrayList.add(node.data);
        LeftRootRightVisit(node.right, arrayList);
    }

    private void RightRootLeftVisit(AVLNode node, ArrayList arrayList) {
        if (node == null) {
            return;
        }

        RightRootLeftVisit(node.right, arrayList);
        arrayList.add(node.data);
        RightRootLeftVisit(node.left, arrayList);
    }

    private void RootLeftRightVisit(AVLNode node, ArrayList arrayList) {
        if (node == null) {
            return;
        }
        arrayList.add(node.data);

        RootLeftRightVisit(node.left, arrayList);

        RootLeftRightVisit(node.right, arrayList);
    }

    /** 
     * Only for debugging purposes. Gives all information about a node.
  
     * @param n The node to write information about.
     */
// public void debug(AVLNode n) {
//  int l = 0;
//  int r = 0;
//  int p = 0;
//  if(n.left!=null) {
//   l = n.left.key;
//  }
//  if(n.right!=null) {
//   r = n.right.key;
//  }
//  if(n.parent!=null) {
//   p = n.parent.key;
//  }
//  
//  System.out.println("Left: "+l+" Key: "+n+" Right: "+r+" Parent: "+p+" Balance: "+n.balance);
//  
//  if(n.left!=null) {
//   debug(n.left);
//  }
//  if(n.right!=null) {
//   debug(n.right);
//  }
// }
    private void setBalance(AVLNode node) {
        node.balance = height(node.right) - height(node.left);
    }
}