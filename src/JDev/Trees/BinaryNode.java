
package JDev.Trees;

public class BinaryNode {

    public BinaryNode(Comparable x) {
        this(x, null, null);
    }

    public BinaryNode(Comparable theElement, BinaryNode lt, BinaryNode rt) {
        data = theElement;
        left = lt;
        right = rt;
    }

    public Comparable data;     
    public BinaryNode left;         
    public BinaryNode right;        
}