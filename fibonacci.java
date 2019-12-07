import java.util.*;

public class FibHeap<T> {

    private Node<T> root; // also considered min of heap
    private int numOfNodes;

    public FibHeap() {
        root = null;
        numOfNodes = 0;
    }

    // insert method with a specified priority
    public void insert(final T value, final double priority) {
        checkPriority(priority);

        final Node<T> result = new Node<T>(value, priority);
        root = mergeLists(root, result);
        ++numOfNodes;
    }

    // returns min of the heap, aka root
    public Node<T> findMin() {
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
    public <T> FibHeap<T> merge(final FibHeap<T> one, final FibHeap<T> two) {
        final FibHeap<T> result = new FibHeap<T>();
        result.root = mergeLists(one.root, two.loop);
        result.numOfNodes = one.numOfNodes + two.numOfNodes;
        one.numOfNodes = two.numOfNodes = 0;
        one.root = null;
        two.root = null;

        return result;

    }

    // dequeues and returns min element of fib heap.
    public Node<T> removeMin() {
        if (root == null)
            System.out.println("Heap is empty.");

        --numOfNodes;

        final Node<T> minElement = root;

        if (root.getNext() == root)
            root = null;
        else {
            root.getPrev().setNext(root.getNext());
            root.getNext().setPret(root.getPrev());
            root = root.getNext();
        }
        if (minElement.getChild() == null) {
            final Node<?> curr = minElement.getChild();
            do {
                curr.setParent(null);
            } while (curr != minElement.getChild());
        }
        root = mergeLists(root, minElement.getChild());
        if (root == null)
            return minElement;
        final List<Node<T>> treeTable = new ArrayList<Node<T>>();
        final List<Node<T>> toVisit = new ArrayList<Node<T>>();

        for (Node<T> curr = root; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.getNext())
            toVisit.add(curr);

        for (Node<T> curr : toVisit) {
            while (true) {
                while (curr.getDegree() >= treeTable.size())
                    treeTable.add(null);
                if (treeTable.get(curr.getDegee()) == null) {
                    treeTable.set(curr.getDegree(), curr);
                    break;
                }

                final Node<T> other = treeTable.get(curr.getDegree());
                treeTable.set(curr.getDegree, null);

                final Node<T> min = (other.getPriority() < curr.getPriority()) ? other : curr;
                final Node<T> max = (other.getPriority() < curr.getPriority()) ? curr : other;

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
    public void decreaseKey(final Node<T> entry, final double newP) {
        checkPriority(newP);
        if (newP > entry.getPriority())
            throw new IllegalArgumentException("New priority exceeds old.");

        decreaseKeyUnchecked(entry, newP);
    }

    // deletes value from the heap.
    public void delete(final Node<T> value) {
        decreaseKeyUnchecked(entry, Double.NEGATIVE_INFINITY);
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

    private <T> Node<T> mergeLists(final Node<T> one, final Entry<T> two) {
        if (one == null && two == null)
            return null;
        else if (one != null && two == null)
            return one;
        else if (one == null && two != null)
            return two;
        else {
            final Node<T> oneNext = one.getNext();
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
    private void decreaseKeyUnchecked(final Node<T> key, final double priority) {
        key.setPriority(priority);

        if (key.getParent != null && key.getPriority() <= key.getParent().getPriority())
            cut(key);

        if (key.getPriority() <= root.getPriority())
            root = key;

    }

    public void increaseKey() {

    }

    /*
     * Cuts a node from its parent. If the parent is already marked, recursively
     * cuts that node from its parent as well.
     */
    private void cut(final Node<T> value) {
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
        if (value.getParent().isMarked())
            cut(value.getParent());
        else
            value.getParent.setMarked(true);

        /* Clear the relocated node's parent; it's now a root. */
        value.setParent(null);
    }
    
        
    

}



