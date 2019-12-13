//generic Node class
import java.util.*;
public class Node implements Comparator<Node>
{
    private int data;
    private Node parent;
    private Node child;
    private Node next;
    private Node prev;
    private double priority;
    private int numOfChildren = 0;
    private boolean isMarked;
    public int cost;
    public int node;

    public Node() {

    }

    public Node(int data, double priority) {
        this.data = data;
        this.priority = priority;
    }

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    public int getData() {
        return data;
    }

    public void setData(int value) {
        data = value;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double p) {
        priority = p;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getParent() {
        return parent;
    }

    public Node getChild() {
        return child;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setMarked(boolean v) {
        isMarked = v;
    }

    public boolean getMarked() {
        return isMarked;
    }

    public int getDegree() {
        return numOfChildren;
    }

    public void setDegree(int num) {
        numOfChildren = num;
    }

    @Override
    public int compare(Node node1, Node node2) 
    { 
        if (node1.cost < node2.cost) 
            return -1; 
        if (node1.cost > node2.cost) 
            return 1; 
        return 0; 
    } 

}
