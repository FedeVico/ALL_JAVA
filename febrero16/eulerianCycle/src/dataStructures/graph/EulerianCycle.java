/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import java.util.Iterator;

import dataStructures.list.*;
import dataStructures.set.Set;

public class EulerianCycle<V> {
    private List<V> eCycle;

    @SuppressWarnings("unchecked")
    public EulerianCycle(Graph<V> g) {
        Graph<V> graph = (Graph<V>) g.clone();
        eCycle = eulerianCycle(graph);
    }

    public boolean isEulerian() {
        return eCycle != null;
    }

    public List<V> eulerianCycle() {
        return eCycle;
    }

    // J.1
    private static <V> boolean isEulerian(Graph<V> g) {
        // TO DO
        boolean esEuleriano = true;
        for (V vertice : g.vertices()) {
            if (g.degree(vertice) % 2 != 0) {
                esEuleriano = false;
            }
        }
        return esEuleriano;
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {
        // TO DO
        g.deleteEdge(v, u);
        if (g.degree(v) == 0) {
            g.deleteVertex(v);
        }
        if (g.degree(u) == 0) {
            g.deleteVertex(u);
        }
    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        // TO DO
        List<V> ciclo = new ArrayList<V>();
        ciclo.append(v0);
        Set<V> suc = g.successors(v0);
        Iterator<V> it = suc.iterator();
        V u = it.next();
        ciclo.append(u);
        remove(g, v0, u);
        while (u != ciclo.get(0) && !g.isEmpty() && !g.successors(u).isEmpty()) {
            suc = g.successors(u);
            it = suc.iterator();
            v0 = u;
            u = it.next();
            ciclo.append(u);
            remove(g, v0, u);
        }

        return ciclo;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
        // TO DO
        for (int i = 0; i < xs.size(); i++) {
            if (xs.get(i) == ys.get(0)) {
                for (int j = 0; j < ys.size() - 1; j++) {
                    xs.insert(i, ys.get(j));
                    i++;
                }
            }
        }
    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
        // TO DO
        boolean encontrado = false;
        V v = null;
        int i = 0;

        while (!encontrado && i < cycle.size()) {
            Set<V> suc = g.successors(cycle.get(i));
            if (!suc.isEmpty()) {
                encontrado = true;
                v = suc.iterator().next();
            } else {
                i++;
            }
        }
        return v;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {
        // TO DO
        if (!isEulerian(g)) {
            throw new IllegalArgumentException("no es euleriano");
        }
        Set<V> listaVertices = g.vertices();
        V v0 = listaVertices.iterator().next();

        List<V> ciclo = extractCycle(g, v0);
        while (!g.isEmpty() && vertexInCommon(g, ciclo) != null) {
            listaVertices = g.vertices();
            List<V> aux = extractCycle(g, vertexInCommon(g, ciclo));
            connectCycles(ciclo, aux);
        }

        return ciclo;
    }
}
