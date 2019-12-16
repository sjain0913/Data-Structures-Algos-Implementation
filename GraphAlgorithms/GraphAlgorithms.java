import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Saumya Jain
 * @version 1.0
 * @userid sjain395
 * @GTID 903407158
 *
 * Collaborators: Collaboration with Ritesh Malpani and Colton Hazelton
 *
 * Resources: StackOverflow, Saikrishna Slides
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The inputs cannot be null!");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("The start vertex does"
                    + " not exist in graph!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList =
                graph.getAdjList();
        List<Vertex<T>> vertices = new ArrayList<>();
        vertices.add(start);
        Queue<Vertex<T>> nodes = new LinkedList<>();
        nodes.add(start);
        while (!nodes.isEmpty()) {
            Vertex<T> search = nodes.remove();
            List<VertexDistance<T>> adjVertices =
                    adjList.get(search);
            for (int i = 0; i < adjVertices.size(); i++) {
                VertexDistance<T> dist = adjVertices.get(i);
                Vertex<T> child = dist.getVertex();
                if (!vertices.contains(child)) {
                    nodes.add(child);
                    vertices.add(child);
                }
            }
        }
        return vertices;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as
     * they are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The inputs cannot be null!");
        }

        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("The start vertex"
                    + " does not exist in graph!");
        }
        List<Vertex<T>> vertices = new ArrayList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList =
                graph.getAdjList();
        Set<Vertex<T>> vertexSet = new HashSet<>();
        dfs(adjList, start, vertexSet, vertices);
        return vertices;
    }

    /**
     * Private helper method for Depth-First Search
     *
     * @param adjList   Adjacency List
     * @param start     Vertex to start from
     * @param vertexSet Set of vertices visited
     * @param vertices  Vertices visited
     * @param <T>       Type of elements stored
     */
    private static <T> void dfs(Map<Vertex<T>,
            List<VertexDistance<T>>> adjList, Vertex<T> start,
                                Set<Vertex<T>> vertexSet,
                                List<Vertex<T>> vertices) {
        if (!vertexSet.contains(start)) {
            vertexSet.add(start);
            vertices.add(start);
            List<VertexDistance<T>> vdpairList = adjList.get(start);
            for (VertexDistance<T> pair : vdpairList) {
                Vertex<T> child = pair.getVertex();
                dfs(adjList, child, vertexSet, vertices);
            }
        }

    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest
     * distance to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The inputs cannot be null!");
        }

        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start node does not exist in "
                    + "graph");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList =
                graph.getAdjList();
        Map<Vertex<T>, Integer> map = new HashMap<>();
        for (Vertex<T> vertex : adjList.keySet()) {
            map.put(vertex, Integer.MAX_VALUE);
        }
        map.put(start, 0);
        PriorityQueue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new VertexDistance<T>(start, 0));
        while (!priorityQueue.isEmpty()) {
            VertexDistance<T> curr = priorityQueue.remove();
            List<VertexDistance<T>> vdpairs =
                    graph.getAdjList().get(curr.getVertex());
            for (VertexDistance<T> vd : vdpairs) {
                int distance = curr.getDistance() + vd.getDistance();
                if (distance < map.get(vd.getVertex())) {
                    map.put(vd.getVertex(), distance);
                    priorityQueue.add(new VertexDistance<>(vd.getVertex(),
                            distance));
                }
            }
        }
        return map;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The inputs cannot be null!");
        }
        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>();
        DisjointSet<Vertex<T>> disjointSet =
                new DisjointSet<>(graph.getVertices());
        Set<Edge<T>> mst = new HashSet<>();
        for (Edge<T> edge: graph.getEdges()) {
            priorityQueue.add(edge);
        }
        while (!priorityQueue.isEmpty()
                && mst.size() != 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = priorityQueue.remove();
            if (disjointSet.find(edge.getU())
                    != disjointSet.find(edge.getV())) {
                mst.add(edge);
                Edge<T> back =
                        new Edge<>(edge.getV(), edge.getU(), edge.getWeight());
                mst.add(back);
                disjointSet.union(edge.getU(), edge.getV());
            }
        }
        if (mst.size() == 2 * (graph.getVertices().size() - 1)) {
            return mst;
        } else {
            return null;
        }
    }
}
