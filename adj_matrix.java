//This is a java program to represent graph as a adjacency matrix
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
public class adj_matrix
{
    private final int vertices;
    private int[][] adjacency_matrix;
 
    public adj_matrix(int v) 
    {
        vertices = v;
        adjacency_matrix = new int[vertices + 1][vertices + 1];
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
        int v, e, count = 1, to = 0, from = 0;
        Scanner sc = new Scanner(System.in);
        adj_matrix graph;
        try 
        {
            System.out.println("Enter the number of vertices: ");
            v = sc.nextInt();
            System.out.println("Enter the number of edges: ");
            e = sc.nextInt();
            ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
            graph = new adj_matrix(v);
            while (count <= e) 
            {
                to = rand.nextInt(v);
                from = rand.nextInt(v);
                ArrayList<Integer> t = new ArrayList<Integer>();
                t.add(to);
                t.add(from);
                int weight = rand.nextInt((v*v))+1;
                if(!edges.contains(t))
                {
                    edges.add(t);
                    graph.makeEdge(to+1, from+1, weight);
                }
                else
                {
                    while(edges.contains(t))
                    {
                        to = rand.nextInt(v);
                        from = rand.nextInt(v);
                        t = new ArrayList<Integer>();
                        t.add(to);
                        t.add(from);
                    }
                    edges.add(t);
                    graph.makeEdge(to+1, from+1, weight);
                }              
                count++;
            }
            System.out.println("The adjacency matrix for the given graph is: ");
            System.out.print("  ");
            for (int i = 1; i <= v; i++)
                System.out.print(i + " ");
            System.out.println();
 
            for (int i = 1; i <= v; i++) 
            {
                System.out.print(i + " ");
                for (int j = 1; j <= v; j++) 
                    System.out.print(graph.getEdge(i, j) + " ");
                System.out.println();
            }
 
        }
        catch (Exception E) 
        {
            System.out.println("Somthing went wrong");
        }
 
        sc.close();
    }
}
