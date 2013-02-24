
package JDev.Trees;


public class AVLNode {

    public AVLNode left;
    public AVLNode right;
    public AVLNode parent;
    public Comparable data;
    public int balance;

    public AVLNode(Comparable x) {
        left = right = parent = null;
        balance = 0;
        data = x;
    }

    protected AVLNode(Comparable x, AVLNode lt, AVLNode rt, AVLNode par) {
        left = lt;
        right = rt;
        parent = par;
        balance = 0;
        data = x;
    }
}