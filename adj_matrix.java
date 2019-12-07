//This is a java program to represent graph as a adjacency matrix
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
public class adj_matrix
{
    public final int vertices;
    private int[][] adjacency_matrix;
 
    public adj_matrix(int v) 
    {
        vertices = v;
        adjacency_matrix = new int[vertices][vertices];
    }
    
    public void makeEdge(int to, int from, int edge) 
    {
        try 
        {
            adjacency_matrix[to][from] = edge;
        }
        catch (ArrayIndexOutOfBoundsException index) 
        {
            System.out.println("No such vertex!");
        }
    }
 
    public int getEdge(int to, int from) 
    {
        try 
        {
            return adjacency_matrix[to][from];
        }
        catch (ArrayIndexOutOfBoundsException index) 
        {
            System.out.println("No such vertex!");
        }
        return -1;
    }
 
    public static void main(String args[]) 
    {
        Random rand = new Random();
        int v, e, count = 1;
        Scanner sc = new Scanner(System.in);
        adj_matrix graph;
        try 
        {
            System.out.println("Enter the number of vertices: ");
            v = sc.nextInt();
            System.out.println("Enter the number of edges: ");
            e = sc.nextInt();
            ArrayList<Integer> initialSet = new ArrayList<Integer>();
            ArrayList<Integer> visitedSet = new ArrayList<Integer>();
            ArrayList<Integer> vertices = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
            graph = new adj_matrix(v);
            for(int i = 0; i < v; i++)
            {
                initialSet.add(i);
                vertices.add(i);
            }
            int curVertex = 0;
            initialSet.remove(new Integer(curVertex));
            visitedSet.add(curVertex);
            while (count <= e) 
            {
                ArrayList<Integer> t = new ArrayList<Integer>();
                if(count < v)
                {
                    int adj=rand.nextInt(initialSet.size());
                    int adjVertex=initialSet.get(adj);
                    int weight = rand.nextInt(25)+1;
                    t.add(curVertex);
                    t.add(adjVertex);
                    edges.add(t);
                    graph.makeEdge(curVertex, adjVertex,weight);
                    initialSet.remove(new Integer(adjVertex));
                    visitedSet.add(adjVertex);
                    curVertex=adjVertex;
                    
                    count++;
                }
                else
                {
                    int to = rand.nextInt(v);
                    int from = rand.nextInt(v);
                    t.add(to);
                    t.add(from);
                    while(edges.contains(t))
                    {
                        to=rand.nextInt(v);
                        from=rand.nextInt(v);
                        t = new ArrayList<Integer>();
                        t.add(to);
                        t.add(from);
                    }
                    int weight = rand.nextInt(25)+1;
                    graph.makeEdge(to, from,weight);
                    edges.add(t);
                    count++;
                }
            }
            System.out.println("The adjacency matrix for the given graph is: ");
            System.out.print("  ");
            for (int i = 0; i < v; i++)
                System.out.print(i + " ");
            System.out.println();
 
            for (int i = 0; i < v; i++) 
            {
                System.out.print(i + " ");
                for (int j = 0; j < v; j++) 
                    System.out.print(graph.getEdge(i, j) + " ");
                System.out.println();
            }
 
        }
        catch (Exception E) 
        {
            System.out.println(E.toString());
        }
 
        sc.close();
    }
}
