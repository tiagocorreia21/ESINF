package graph;

import graph.map.MapGraph;
import graph.matrix.MatrixGraph;

import java.util.*;
import java.util.function.BinaryOperator;

public class Algorithms {

    /**
     * Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g    Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {

        if (!g.vertices().contains(vert)) return null;

        LinkedList<V> result = new LinkedList<>();
        LinkedList<V> aux = new LinkedList<>();

        result.add(vert);
        aux.add(vert);

        while (!aux.isEmpty()) {

            V current = aux.removeFirst();

            for (V vertices : g.adjVertices(current)) {

                if (!result.contains(vertices)) {
                    result.add(vertices);
                    aux.add(vertices);
                }
            }
        }
        return result;
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g       Graph instance
     * @param vOrig   vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs    return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        int vOrigin = g.key(vOrig);

        if (vOrigin == -1 || visited[vOrigin]) return;

        qdfs.add(vOrig);
        visited[vOrigin] = true;

        for (V adjacent : g.adjVertices(vOrig)) {
            DepthFirstSearch(g, adjacent, visited, qdfs);
        }
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g    Graph instance
     * @param vert vertex of graph g that will be the source of the search
     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {

        if (g == null || !g.vertices().contains(vert)) return null;

        LinkedList<V> res = new LinkedList<>();

        boolean[] visited = new boolean[g.numVertices()];

        DepthFirstSearch(g, vert, visited, res);

        return res;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        int vOrigin = g.key(vOrig);

        visited[vOrigin] = true;

        path.add(vOrig);

        if (vOrig.equals(vDest)) {

            paths.add(new LinkedList<>());

        } else {

            for (V adjancent : g.adjVertices(vOrig)) {

                int adjIndex = g.key(adjancent);

                if (!visited[adjIndex]) {

                    allPaths(g, adjancent, vDest, visited, path, paths);
                }
            }
        }
        path.removeLast();
        visited[vOrigin] = false;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {

        ArrayList<LinkedList<V>> paths = new ArrayList<>();

        LinkedList<V> path = new LinkedList<>();

        boolean[] visited = new boolean[g.numVertices()];

        allPaths(g, vOrig, vDest, visited, path, paths);

        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, V[] pathKeys, E[] dist) {

        if (g == null || vOrig == null || ce == null || sum == null || zero == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        Arrays.fill(dist, null);
        Arrays.fill(pathKeys, null);
        Arrays.fill(visited, false);

        dist[g.key(vOrig)] = zero;

        PriorityQueue<V> priorityQueue = new PriorityQueue<>(Comparator.comparing(v -> dist[g.key(v)], ce));
        priorityQueue.add(vOrig);

        while (!priorityQueue.isEmpty()) {

            V u = priorityQueue.poll();

            int uKey = g.key(u);

            if (visited[uKey]) continue;

            visited[uKey] = true;

            for (V adjecent : g.adjVertices(u)) {

                int vKey = g.key(adjecent);

                if (!visited[vKey]) {

                    E alt = sum.apply(dist[uKey], g.edge(u, adjecent).getWeight());

                    if (dist[vKey] == null || ce.compare(alt, dist[vKey]) < 0) {

                        dist[vKey] = alt;
                        pathKeys[vKey] = u;
                        priorityQueue.add(adjecent);
                    }
                }
            }
        }
    }

    /**
     * Shortest-path between two vertices
     *
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {

        if (!g.vertices().contains(vOrig) || !g.vertices().contains(vDest)) return null;

        if (vOrig.equals(vDest)) {
            shortPath.add(vOrig);
            return zero;
        }

        int numVertices = g.numVertices();

        boolean[] visited = new boolean[numVertices];

        V[] pathKeys = (V[]) new Object[numVertices];

        E[] dist = (E[]) new Object[numVertices];

        Arrays.fill(dist, null);
        Arrays.fill(visited, false);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        if (dist[g.key(vDest)] == zero) return null;

        shortPath.clear();

        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return dist[g.key(vDest)];
    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param g     graph
     * @param vOrig start vertex
     * @param ce    comparator between elements of type E
     * @param sum   sum two elements of type E
     * @param zero  neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {

        if (!g.vertices().contains(vOrig)) return false;

        int numVertices = g.numVertices();

        boolean[] visited = new boolean[numVertices];

        V[] pathKeys = (V[]) new Object[numVertices];

        E[] dist = (E[]) new Object[numVertices];

        Arrays.fill(dist, zero);
        Arrays.fill(visited, false);

        PriorityQueue<V> priorityQueue = new PriorityQueue<>(Comparator.comparing(v -> dist[g.key(v)], ce));

        dist[g.key(vOrig)] = zero;

        priorityQueue.add(vOrig);

        while (!priorityQueue.isEmpty()) {

            V u = priorityQueue.poll();

            int uIndex = g.key(u);

            if (visited[uIndex]) continue;

            visited[uIndex] = true;

            for (V v : g.adjVertices(u)) {

                int vIndex = g.key(v);

                E edgeWeight = g.edge(u, v).getWeight();

                E newDist = sum.apply(dist[uIndex], edgeWeight);

                if (!visited[vIndex] && (dist[vIndex] == zero || ce.compare(newDist, dist[vIndex]) < 0)) {

                    dist[vIndex] = newDist;
                    pathKeys[vIndex] = u;
                    priorityQueue.add(v);
                }
            }
        }

        paths.clear();
        dists.clear();

        for (int i = 0; i < numVertices; i++) {

            LinkedList<V> path = new LinkedList<>();

            if (dist[i] != zero) {
                getPath(g, vOrig, g.vertex(i), pathKeys, path);

            } else if (g.vertex(i).equals(vOrig)) {
                path.add(vOrig);
            }
            paths.add(path);
            dists.add(dist[i]);
        }
        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V[] pathKeys, LinkedList<V> path) {

        if (g == null || !g.validVertex(vOrig) || !g.validVertex(vDest)) return;

        if (vOrig.equals(vDest) && path.isEmpty()) {
            path.add(vOrig);
            return;
        }

        V vertex = vDest;

        while (vertex != null && !vertex.equals(vOrig)) {
            path.addFirst(vertex);
            vertex = pathKeys[g.key(vertex)];
        }

        if (vertex == null) {
            path.clear();

        } else {
            path.addFirst(vOrig);
        }
    }

    /**
     * Calculates the minimum distance graph using Floyd-Warshall
     *
     * @param g   initial graph
     * @param ce  comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V, E> MatrixGraph<V, E> minDistGraph(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static <V, E> Graph<V, E> kruskal(Graph<V, E> g, Comparator<E> ce) {

        Graph<V, E> mst = new MapGraph<>(false);

        if (g == null) return null;

        if (g.isDirected()) return null;

        List<Edge<V, E>> edges = new ArrayList<>();

        for (Edge<V, E> edge : g.edges()) {
            edges.add(edge);
        }

        edges.sort(Comparator.comparing(Edge::getWeight, ce));

        for (Edge<V, E> edge : edges) {

            LinkedList<V> connectedVertices = DepthFirstSearch(mst, edge.getVOrig());

            if (!connectedVertices.contains(edge.getVDest())) {
                mst.addEdge(edge.getVOrig(), edge.getVDest(), edge.getWeight());
            }
        }
        return mst;
    }
}