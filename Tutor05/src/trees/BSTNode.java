/*Lớp cho một nút TỔNG QUÁT trong cây BST
 *Vì cây có thứ tự nên dữ liệu trên cây phải có khả năng so sánh
 *=> nút thực tế phải hiện thực interface: java.lang.Comparable
  <T extends Comparable>
  và có lớp chặn dưới là lớp T này <T extends Comparable<? superT>>
*/
package trees;

/**
 *
 * @author Hien Khoa
 */
public class BSTNode<T extends Comparable<? super T>> {
    protected T el;
    protected BSTNode<T> left, right;
    public BSTNode(){left=right=null;}
    public BSTNode(T el){this(el,null,null);}
    public BSTNode(T el, BSTNode<T> lt, BSTNode<T> rt ){
        this.el = el; left = lt; right = rt;
    }
    
}
