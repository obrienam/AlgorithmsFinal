//generic Node class
public class Node<T>
{
    private T data;
    private Node<T> parent;
    private Node<T> child;
    private Node<T> next;
    private Node<T> prev;
    private final double priority;
    private int numOfChildren = 0;
    private boolean isMarked;

    public Node(final T data, final double priority) {
        left = right = this;
        this.data = data;
        this.priority = priority;
    }

    public T getData() {
        return data;
    }

    public void setData(final T value) {
        data = value;
    }

    public double getPriority() {
        return priority;
    }

    public void setParent(final Node<T> parent) {
        this.parent = parent;
    }

    public void setChild(final Node<T> child) {
        this.child = child;
    }

    public void setNext(final Node<T> next) {
        this.next = next;
    }

    public void setPrev(final Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getChild() {
        return child;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setMarked(final boolean v) {
        isMarked = v;
    }

    public boolean getMarked() {
        return isMarked;
    }

    public int getDegree() {
        return numOfChildren;
    }

    public void setDegree(final int num) {
        numOfChildren = num;
    }

}
