/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jeremy Luu
 */
package JDev.Trees;

// Basic node stored in unbalanced binary search trees
// Note that this class is not accessible outside
// of package DataStructures
public class BinaryNode {
    // Constructors

    public BinaryNode(Comparable x) {
        this(x, null, null);
    }

    public BinaryNode(Comparable theElement, BinaryNode lt, BinaryNode rt) {
        data = theElement;
        left = lt;
        right = rt;
    }
    // Friendly data; accessible by other package routines
    public Comparable data;      // The data in the node
    public BinaryNode left;         // Left child
    public BinaryNode right;        // Right child
}