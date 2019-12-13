// Java implementation of Dijkstra's algorithm
// uses Fibonacci Heap as priority queue

import java.util.*;

public class Dijkstra {
    private int dist[];
    private Set<Integer> settled;
    private FibHeap<Node<Integer>> pq;
    private int V;
    List<List<Node<Integer>>> adj;

    public Dijkstra(int V) {
        this.V = V;
        dist = new int[V];
        settled = new HashSet<Integer>();
        pq = new FibHeap();
    }

}