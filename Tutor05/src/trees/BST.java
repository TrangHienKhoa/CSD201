/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trees;
import java.util.Stack;

/**
 *
 * @author Hien Khoa
 */
public class BST<T extends Comparable<? super T>> {
    protected BSTNode<T> root = null;
    public BST(){ }
    public void clear(){root = null;}
    public boolean isEmpty(){return root ==null;}
    /*Hàm chèn phần tử mới KHÔNG DÙNG THUẬT ĐỆ QUY. Phần tử mới el sẽ được 
    chèn thành một nút lá mới trong cây và là con của một nút lá previous ĐÃ CÓ 
    ==>cần biết nút previous để cập nhật tham chiếu đến nút con mới này */
    public void insert(T el){
        if(root == null){ // Nếu cây cây trống thì el sẽ đưa vào nút gốc
           root = new BSTNode<T>(el);
           return;
        }
        // Nếu cây có rồi, tìm nút cha để chèn nút con
        BSTNode<T> p = root, prev = null; // p: biến tạm để chạy trước
        while(p!= null){ // tìm vị trí chèn nút mới
            prev = p;
            if(p.el.compareTo(el)<0) p = p.right;
            else p = p.left;
        }
        // Nếu nút cha có data < data của el ==> chèn vào bên phải
        if(prev.el.compareTo(el)<0) prev.right = new BSTNode<T>(el);
        // Ngược lại, chèn bên trái, chấp nhận nút trùng lặp
        // Nếu không muốn trị trùng lặp thì thêm if (prev.el.compareTo(el) > 0)
        else prev.left = new BSTNode<T>(el);
    }
    /* Chèn phần tử DÙNG KỸ THUẬT ĐỆ QUY - recursion. Nếu CÂY CON p trống thì cấp phát mới và trả về nút mới này. Nếu p không trống thì đệ quy tác vụ xuống nút con để chèn vào nút lá */
    protected BSTNode<T> recInsert(BSTNode<T> p, T el){
        if(p==null)p = new BSTNode<T>(el);
        else if(p.el.compareTo(el)<0) p.right = recInsert(p.right,el);
        // Nếu không muốn trị trùng lặp thì thêm if (prev.el.compareTo(el) >0)
        else p.left = recInsert(p.left,el);
        return p;
    }
    // Chèn phần tử el vào cây
    public void recInsert(T el){
        root = recInsert(root,el);
    }
    // vì data có thứ tự nên việc tìm kiếm được hiện thực dễ dàng nhờ vòng lặp
    // 0(logn) cho cây cân bằng, 0(n) cho cây suy thoái về 1 hướng, n: số nút
    protected T search(T el){
        BSTNode<T> p = root;
        while (p!=null)
            if(el.equals(p.el))return p.el;
            else if(el.compareTo(p.el)<0) p = p.left;
            else p= p.right;
        return null;
    }
    public boolean isInTree(T el){ // kiểm tra el có trong cây không
        return search(el) !=null;
    }
    // viếng nút p
    protected void visit(BSTNode<T> p){
        // code phù hợp với việc xử lý của bài toán
        System.out.println(p.el + " ");
    }
    // Các phép duyệt nút cơ bản
    protected void inorder(BSTNode<T> p){
        if(p!= null){
            inorder(p.left); // NODE
            visit(p);        // LEFT
            inorder(p.right);// RIGHT
        }
    }
    protected void preorder(BSTNode<T> p){
        if(p!=null){
            visit(p);        // NODE
            preorder(p.left);// LEFT
            preorder(p.right);//RIGHT
        }
    }
    protected void postorder(BSTNode<T> p){
        if(p!=null){
           postorder(p.left); // LEFT
           postorder(p.right);// RIGHT
           visit(p);          // NODE
            
        }
    }
    // Các tác vụ duyệt toàn bộ cây
    public void preorder(){ preorder(root);}
    public void inorder(){ inorder(root);}
    public void postorder(){ postorder(root);}
    // Xóa phần tử el bằng PHƯƠNG PHÁP TRỘN
    // Phương pháp này sẽ cập nhật các tham chiếu rồi xóa phần tử được chọn
    // sao cho thứ tự dữ liệu phải được bảo tồn
    // TÌNH HUỐNG NỨT BỊ XÓA CÓ 2 CÂY CON, TRỘN 2 CÂY CON NÀY THÀNH 1
    public void deleteByMerging(T el){
        // Đi tìm nút bị xóa p (ứng với el) và nút cha previous của nó
        BSTNode<T> p=root, prev=null;
        while(p!=null && !p.el.equals(el)){
            prev=p;
            if(p.el.compareTo(el) < 0) p=p.right;
            else p=p.left;
        }
        // Khởi tạo nút cần bảo tồn, node, là nút bị xóa p
        // sau đó cập nhật tùy tình huống
        BSTNode<T> node=p;
        BSTNode<T> tmp; // biến tạm, nút biên phải của cây con trái
        if(p!=null && p.el.equals(el)){ // có phần tử cần phải xóa
            // Nếu node không có con phải => gắn con trái của nó vào nút cha
            // => chuyển node về node.left để gắn vào previous sau này
            if(node.right == null) node = node.left;
            // Nếu node không có con trái => gắn con phải của nó vào nút cha
            // => chuyển node về node.right để gắn vào previous sau này
            else if(node.left == null) node = node.right;
            else{ // node có cả 2 con, trộn các cây con
                // tìm nút biên phải của cây con trái
                tmp=node.left;
                while(tmp.right!=null) tmp=tmp.right;
                // Gắn bên phải của node vào bên phải của tmp vì
                // chúng có trị lớn hơn
                tmp.right = node.right;
                // Giữ node.left để hiệu chỉnh vào previous
                node = node.left;
            }
            // Trường hợp nút bị xóa là nút gốc
            if(p==root) root = node; // node là gốc của cây kết quả
            // nếu xóa nút con trái của nút cha
            else if(prev.left == p) prev.left = node;
            // nếu xóa nút con phải của nút cha
            else prev.right = node;
        }
        else if(root!=null)
            System.out.println("Element " + el + " is not in the tree");
        else System.out.println("the tree is empty");
    }
    // Xóa phần tử el dựa trên phép sao chép
    // Copy dữ liệu của nút con BIÊN PHẢI của cây con trái vào nút bị xóa rồi
    // hủy nút biên phải này
    public void deleteByCopying(T el){
        // Đi tìm nút bị xóa p (ứng với el) và nút cha previous của nó
        BSTNode<T> p = root, prev = null;
        while(p!=null && !p.el.equals(el)){ // find the node p
            prev = p;                       // with element el;
            if(p.el.compareTo(el) < 0)
                p = p.right;
            else p = p.left;
        }
        // Khởi tạo nút cần bảo tồn là nút bị xóa sau đó cập nhật tùy tình huống
        BSTNode<T> node = p;
        if (p!=null && p.el.equals(el)){    // có nút bị xóa
            // node này không có con phải bảo tồn con trái
            if(node.right==null) node = node.left;
            // node này không có con trái bảo tồn con phải
            else if(node.left == null) node = node.right;
            else{ // node cần xóa có cả 2 con
                // Tìm tmp là nút biên phải của cây con trái của node
                BSTNode<T> previous = node;
                BSTNode<T> tmp = node.left;
                while(tmp.right != null){
                    previous=tmp; tmp=tmp.right;
                }
                // Chép data của nút biên phải vào nút cần xóa node
                node.el = tmp.el;
                // Nút biên trái không có cây con phải -> cập nhật previous.left
                if(previous==node) previous.left = tmp.left;
                // Nút biên phải có cây con phải, previous nằm dưới node
                // Móc temp.left(có trị lớn hơn) vào bên phải của previous ( trị nhỏ hơn)
                else previous.right = tmp.left;
            }
            // nếu nút bị xóa là nút gốc, thay gốc bằng node
            if(p == root) root = node;
            // nếu nút bị xóa là con trái của nút cha,
            // thay con trái của previous
            else if(prev.left == p) prev.left = node;
            // nếu nút bị xóa là con phải của nút cha, thay con phải của previous
            else prev.right = node;
        }
        else if (root != null) // không có nút bị xóa
            System.out.println("Element " + el + " is not in the tree");
        else System.out.println("the tree is empty");   
    }
    // Duyệt preorder bằng phép lặp dùng Stack
    public void iterativePreorder(){
        BSTNode<T> p = root;
        Stack<BSTNode<T>> stack = new Stack<BSTNode<T>>();
        if(p !=null){
            stack.push(p);
            while( !stack.isEmpty()){
                p = stack.pop();
                visit(p);       // NODE
                // Cất right vào trước để lấy ra sau
                if (p.right !=null) stack.push(p.right);
                // Cất left vào sau để lấy ra trước
                if (p.left != null) stack.push(p.left);
            }
        }
    }
    // Duyệt inorder bằng phép lặp dùng Stack
    public void iterativeInorder(){
        BSTNode<T> p = root;
        Stack<BSTNode<T>> stack = new Stack<BSTNode<T>>();
        while(p!=null){
            // Cất right vào stack trước để lấy ra sau
            if (p.right !=null) stack.push(p.right);
            stack.push(p); // Cất nút hiện hành sau để lấy ra trước right
            p=p.left; // chuyển sang trái để duyệt left trước
        }
        p=stack.pop(); // Lấy ra 1 nút trong stack
        // khi còn nút phải xét và nút này không có con phải
        while(!stack.isEmpty() && p.right ==null){
            visit(p);
            p=stack.pop();
        }
        visit(p); // viếng nút có con phải
        if(!stack.isEmpty()) p = stack.pop();
        else p=null;
    }

// Duyệt postorder dùng stack tự quản lý
// Cần 2 stack: stack để thao tác
// Giải thuật này đòi hỏi bộ nhớ phải đủ chứa toàn bộ các nút của cây
public void iterativePostorder2(){
BSTNode<T>p=root;
// stack giúp cất tạm các nút
Stack<BSTNode<T>> stack = new Stack<BSTNode<T>>();
// stack chứa thứ tự duyệt
Stack<BSTNode<T>> output= new Stack<BSTNode<T>>();
if(p!=null){ // left-to-right postorder = right-to-left preorder;
    stack.push(p);
    while(!stack.isEmpty()){
        p=stack.pop(); // lấy ra NODE
        output.push(p); // cất vào output
        if(p.left!=null) stack.push(p.left);
        if(p.right!=null) stack.push(p.right);
    }
    // duyệt từng nút theo thứ tự đã có
    while (!output.isEmpty()){
        p=output.pop();
        visit(p);
}
}
}
public void iterativePostorder(){
    BSTNode<T>p=root, q=root;
    Stack<BSTNode<T>> stack= new Stack<BSTNode<T>>();
    while(p!=null){
        // cất các left vào stack
        for(;p.left!=null;p=p.left) stack.push(p);
        while(p!=null && (p.right == null || p.right == q)){
            visit(p);
            q = p;
            if (stack.isEmpty())
                return;
            p = stack.pop();
        }
        stack.push(p);
        p=p.right;
        }
    }
public void breadthFirst(){
    BSTNode<T> p=root;
    MyQueue<BSTNode<T>> queue = new MyQueue<BSTNode<T>>();
    if(p!=null){
        queue.enqueue(p);
        while(!queue.isEmpty()){
            p = queue.dequeue();
            visit(p);
            if(p.left!=null)
                queue.enqueue(p.left);
            if(p.right!=null)
                queue.enqueue(p.right);
            }
    }
}
public void MorrisInorder(){
    BSTNode<T> p = root,tmp;
    while(p!=null)
        if(p.left == null){
            visit(p);
            p=p.right;
        }
        else{
            tmp=p.left;
            while(tmp.right != null && // go to the rightmost node of
                  tmp.right != p  ) // the left subtree or
                tmp = tmp.right;    // to the temporary parent of p;
            if(tmp.right == null){ // if 'true' rightmost node was
                tmp.right = p;     // reached, make it a temporary
                p = p.left;        // parent of the current root,
            }
            else {                 // else a temporary parent has been
                visit(p);          // found; visit node p and then cut
                tmp.right = null;  // the right pointer of the current
                p = p.right;       // parent, whereby it ceases to be
            }                      // a parent;
        }
     }
public void MorrisPreorder(){
    BSTNode<T> p = root, tmp;
    while(p!=null){
        if(p.left == null){
            visit(p);
            p = p.right;
        }
        else{
            tmp = p.left;
            while(tmp.right != null && // go to the rightmost node of
                  tmp.right != p  ) // the left subtree or
                tmp = tmp.right;    // to the temporary parent of p;
            if(tmp.right == null) { // if 'true' rightmost node was 
                visit(p);           // reached, visit the root and
                tmp.right = p;      // make the rightmost node a temporary
                p = p.left;         // parent of the current root,
            }
            else {                  // else a temporary parent has been
                tmp.right = null;   // found; cut the right pointer of
                p = p.right;        // the current parent, whereby  it ceases
            }                       // to be a parent;
        }
    }
}
public void MorrisPostorder(){
    BSTNode<T> p = new BSTNode<T>(), tmp, q, r, s;
    p.left = root;
    while(p!=null)
        if(p.left == null)
            p=p.right;
        else{
            tmp = p.left;
            while(tmp.right!=null &&  // go to the rightmost node of
                  tmp.right!=p  )  // the left subtree or
                tmp = tmp.right;   // to the temporary parent of p;
            if(tmp.right == null){ // if 'true rightmost node was
                tmp.right = p;     // reached, make it a temporary
                p = p.left;        // parent of the current root,
            }
            else{                   // else a temporary parent has been found;
                // process nodes between p.left (included) and p (excluded)
                // pretended to the right in modified tree in reverse order;
                // the first loop descends this chain of nodes and reverses
                // right pointers; the second loop goes back, visits nodes,
                // and reverses right pointers again to restore the pointers
                // to the original setting;
                for(q=p.left, r=q.right, s=r.right;
                        r!=p; q=r, r=s, s=s.right)
                    r.right=q;
                for(s=q.right; q!=p.left;
                        q.right = r, r=q, q=s, s=s.right)
                    visit(q);
                visit(p.left);    // visit node p.left and then cut
                tmp.right = null; // the right pointer of the current 
                                  // parent, whereby it ceases to be
                                  // a parent;
            }
        }
}
// Duyệt inorder dạng gióng hàng
private void visit_align(BSTNode p, int level){
    if(p==null) return;
    if(level>0){
        for(int i=0; i<level-1; i++)System.out.print("  ");
        System.out.println("|__");
        
    }
    System.out.println(p.el);       // NODE
    visit_align(p.left, level+1);   // left
    visit_align(p.right, level+1);  // right
}
// Xuất cây dạng gióng hàng
public void print_align(){
    visit_align(root,0);
}
// chèn 1 nhóm trị ĐÃ CÓ THỨ TỰ TĂNG từ vị trí first đến vị trí last
// vào cây để tạo cây cân bằng
public void insertBalance(T data[], int first, int last){
    if(first<=last){
        int middle = (first+last)/2;
        insert(data[middle]);
        insertBalance(data,first,middle-1);
        insertBalance(data,middle+1,last);
    }
}
// chèn cả nhóm trị ĐÃ CÓ THỨ TỰ TĂNG vào cây để tạo cây cân bằng
public void insertBalance(T data[]){
    insertBalance(data,0,data.length-1);
}
}
