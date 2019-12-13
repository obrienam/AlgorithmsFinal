import java.util.*;

public class FibHeap{

    public Node root; // also considered min of heap
    public int numOfNodes;

    public FibHeap() {
        root = null;
        numOfNodes = 0;
    }

    // insert method with a specified priority
    public void insert(int value, double priority) {
        checkPriority(priority);

        Node result = new Node(value, priority);
        root = mergeLists(root, result);
        ++numOfNodes;
    }

    // returns min of the heap, aka root
    public Node findMin() {
        if (root == null)
            System.out.println("Heap is empty");
        return root;

    }

    // getter for numOfNodes
    public int getNumOfNodes() {
        return numOfNodes;
    }

    // given two heaps, returns a new heap containing all of the elements
    // heaps given will be empty after completion of the method.
    public FibHeap merge(final FibHeap one, final FibHeap two) {
        final FibHeap result = new FibHeap();
        result.root = mergeLists(one.root, two.root);
        result.numOfNodes = one.numOfNodes + two.numOfNodes;
        one.numOfNodes = two.numOfNodes = 0;
        one.root = null;
        two.root = null;

        return result;

    }

    // dequeues and returns min element of fib heap.
    public Node removeMin() {
        if (root == null)
            System.out.println("Heap is empty.");

        --numOfNodes;

        final Node minElement = root;

        if (root.getNext() == root)
            root = null;
        else {
            root.getPrev().setNext(root.getNext());
            root.getNext().setPrev(root.getPrev());
            root = root.getNext();
        }
        if (minElement.getChild() == null) {
            final Node curr = minElement.getChild();
            do {
                curr.setParent(null);
            } while (curr != minElement.getChild());
        }
        root = mergeLists(root, minElement.getChild());
        if (root == null)
            return minElement;
        final List<Node> treeTable = new ArrayList<Node>();
        final List<Node> toVisit = new ArrayList<Node>();

        for (Node curr = root; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.getNext())
            toVisit.add(curr);

        for (Node curr : toVisit) {
            while (true) {
                while (curr.getDegree() >= treeTable.size())
                    treeTable.add(null);
                if (treeTable.get(curr.getDegree()) == null) {
                    treeTable.set(curr.getDegree(), curr);
                    break;
                }

                final Node other = treeTable.get(curr.getDegree());
                treeTable.set(curr.getDegree(), null);

                final Node min = (other.getPriority() < curr.getPriority()) ? other : curr;
                final Node max = (other.getPriority() < curr.getPriority()) ? curr : other;

                max.getNext().setPrev(max.getPrev());
                max.getPrev().setNext(max.getNext());

                max.setNext(max);
                max.setPrev(max);
                min.setChild(mergeLists(min.getChild(), max));

                max.setParent(min);
                max.setMarked(false);
                min.setDegree(min.getDegree() + 1);
                curr = min;
            }
            if (curr.getPriority() <= root.getPriority())
                root = curr;
        }
        return minElement;
    }

    // decreases key to specified priority.
    public void decreaseKey(final Node entry, final double newP) {
        checkPriority(newP);
        if (newP > entry.getPriority())
            throw new IllegalArgumentException("New priority exceeds old.");

        decreaseKeyUnchecked(entry, newP);
    }

    // deletes value from the heap.
    public void delete(final Node value) {
        decreaseKeyUnchecked(value, Double.NEGATIVE_INFINITY);
        removeMin();
    }

    // utility function that checks whether priority is a valid double
    private void checkPriority(final double priority) {
        if (Double.isNaN(priority))
            throw new IllegalArgumentException(priority + "is invalid");
    }

    /**
     * Utility function which, given two pointers into disjoint circularly- linked
     * lists, merges the two lists together into one circularly-linked list in O(1)
     * time. Because the lists may be empty, the return value is the only pointer
     * that's guaranteed to be to an element of the resulting list.
     *
     * This function assumes that one and two are the minimum elements of the lists
     * they are in, and returns a pointer to whichever is smaller. If this condition
     * does not hold, the return value is some arbitrary pointer into the
     * doubly-linked list.
     */

    private Node mergeLists(final Node one, final Node two) {
        if (one == null && two == null)
            return null;
        else if (one != null && two == null)
            return one;
        else if (one == null && two != null)
            return two;
        else {
            final Node oneNext = one.getNext();
            one.getNext().setPrev(one);
            two.setNext(oneNext);
            two.getNext().setPrev(two);
            return one.getPriority() < two.getPriority() ? one : two;

        }
    }

    /*
     * Decreases the key of a node in the tree wihtout doind any checking to ensure
     * that the priority is valid.
     */
    private void decreaseKeyUnchecked(final Node key, final double priority) {
        key.setPriority(priority);

        if (key.getParent() != null && key.getPriority() <= key.getParent().getPriority())
            cut(key);

        if (key.getPriority() <= root.getPriority())
            root = key;

    }

    public void increaseKey() {

    }
    public int size()
    {
        return numOfNodes;
    }
    /*
     * Cuts a node from its parent. If the parent is already marked, recursively
     * cuts that node from its parent as well.
     */
    private void cut(final Node value) {
        value.setMarked(false);

        /* basecase: if the node has no parent, were done */
        if (value.getParent() == null)
            return;
        /* rewire the node's siblings around it */
        if (value.getNext() != value) {
            value.getNext().setPrev(value.getPrev());
            value.getPrev().setNext(value.getNext());
        }
        if (value.getParent().getChild() == value) {
            if (value.getNext() != value)
                value.getParent().setChild(value.getNext());
            else
                value.getParent().setChild(null);
        }

        /* Decrease the degree of the parent, since it just lost a child. */
        value.getParent().setDegree(value.getParent().getDegree() - 1);

        value.setPrev(value);
        value.setNext(value);

        root = mergeLists(root, value);

        /*
         * Mark the parent and recursively cut it if it's already been marked.
         */
        if (value.getParent().getMarked())
            cut(value.getParent());
        else
            value.getParent().setMarked(true);

        /* Clear the relocated node's parent; it's now a root. */
        value.setParent(null);
    }
    
        
    

}