/*
 * java.util.Queue là interface
 * ==> Xây dựng lớp cho Queue, dùng Linked List đã có
 * Lớp này được dùng trong phép duyệt cây BREADTH-FIRST
 */
package trees;
import java.util.LinkedList;

/**
 *
 * @author Hien Khoa
 */
public class MyQueue<E> extends LinkedList<E> {
    public MyQueue(){super();}
    // thêm vào cuối hàng đợi
    public void enqueue(E x){ this.addLast(x);}
    public E dequeue(){return this.poll();}
    
}
