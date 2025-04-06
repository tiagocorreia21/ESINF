package graph;

import graph.map.MapGraph;
import graph.matrix.MatrixGraph;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BinaryOperator;

public class Metro {

    private Graph<String, LinkedList<String>> g = new MatrixGraph<>(false);

    public static LinkedList<String> pathSameLine(Graph<String, LinkedList<String>> g, String stOrig, String stDest, String line) {

        LinkedList<String> path = new LinkedList<>();

        pathSameLineRec(g, stOrig, stDest, line, path);

        return path;
    }

    private static void pathSameLineRec(Graph<String, LinkedList<String>> g, String stOrig, String stDest, String line, LinkedList<String> path) {

        path.push(stOrig);

        for (String stAdj : g.adjVertices(stOrig)) {

            if (g.edge(stOrig, stAdj).getWeight().contains(line)) {

                if (stAdj.equals(stDest)) {

                    path.push(stAdj);
                    return;

                } else if (!path.contains(stAdj)) {

                    // search other stations
                    pathSameLineRec(g, stAdj, stDest, line, path);
                }
            }
        }
    }

    public <V, E> Graph<V, E> relativeNeighbGraph(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum, E zero) {

        Graph<V, E> graph = new MapGraph<>(false);

        E dest, dest1, dest2, max;

        LinkedList<V> path = new LinkedList<>();

        // adding all vertex
        for (V v : g.vertices()) {
            graph.addVertex(v);
        }

        for (V v1 : g.vertices()) {

            for (V v2 : g.vertices()) {

                if (!v1.equals(v2)) {

                    dest = Algorithms.shortestPath(g, v1, v2, ce, sum, zero, path);
                    boolean insEdge = true;

                    for (V v3 : g.vertices()) {

                        if (!v1.equals(v3) && !v2.equals(v3)) {

                            dest1 = Algorithms.shortestPath(g, v1, v3, ce, sum, zero, path);
                            dest2 = Algorithms.shortestPath(g, v2, v3, ce, sum, zero, path);

                            max = ce.compare(dest1, dest2) > 0 ? dest1 : dest2;

                            if (ce.compare(dest, max) > 0) {
                                
                                insEdge = false;
                                break;
                            }
                        }
                    }
                    if (insEdge) {

                        graph.addEdge(v1, v2, dest);
                    }
                }
            }
        }
        return graph;
    }
}
