// Lớp mô tả cho một nút trong cây nhị phân các số nguyên
package trees;

/**
 *
 * @author Hien Khoa
 */
public class IntBinTreeNode {
    int key; // khóa giúp phân biệt dữ liệu trong nút
    IntBinTreeNode left, right;
    public IntBinTreeNode(){left=right=null;}
    public IntBinTreeNode(int k){key=k; left=right=null;}
    // tạo 1 nút với 2 con đã biết
    public IntBinTreeNode(int k, IntBinTreeNode left, IntBinTreeNode right){
        key=k; this.left=left; this.right=right;}
    //Getters, setters

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public IntBinTreeNode getLeft() {
        return left;
    }

    public void setLeft(IntBinTreeNode left) {
        this.left = left;
    }

    public IntBinTreeNode getRight() {
        return right;
    }

    public void setRight(IntBinTreeNode right) {
        this.right = right;
    }
    
    }

