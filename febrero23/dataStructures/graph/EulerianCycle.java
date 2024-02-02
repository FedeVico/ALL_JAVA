/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Computes Eulerian Cycle using Hierholzer's algorithm:
 * https://en.wikipedia.org/wiki/Eulerian_path
 */

package dataStructures.graph;

import dataStructures.list.*;
import java.util.Arrays;

public class EulerianCycle<V> {
    private List<V> eCycle;

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

    private static <V> boolean isEulerian(Graph<V> g) {
        for(V v : g.vertices())
            if(g.degree(v) % 2 != 0)
                return false;
        return true;
    }

    private static <V> void remove(Graph<V> g, V v, V u) {
        g.deleteEdge(v, u);
        for(V w : Arrays.asList(v, u)) {
            if(g.degree(w) == 0)
                g.deleteVertex(w);
        }
    }

    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        List<V> cycle = new LinkedList<>();
        cycle.prepend(v0);

        V v = v0;
        do {
            V u = g.successors(v).iterator().next();
            cycle.prepend(u);
            remove(g, v, u);
            v = u;
        } while(!v.equals(v0));
        return cycle;
    }

    private static <V> void addToCycle(List<V> xs, List<V> ys) {
        V y0 = ys.get(0);

        int p=0;
        for(V x : xs) {
            if(x.equals(y0))
                break;
            else
                p++;
        }

        if (!xs.isEmpty())
            xs.remove(p);

        for(V y : ys) {
            xs.insert(p,y);
            p++;
        }
    }

    private static <V> List<V> eulerianCycle(Graph<V> g) {
        if(!isEulerian(g))
            return null;

        List<V> eCycle = new ArrayList<>();
        V v = g.vertices().iterator().next();

        for(boolean done = false; !done; ) {
            List<V> cycle = extractCycle(g, v);
            addToCycle(eCycle, cycle);
            if(g.isEmpty())
                done = true;
            else
                for(V u : eCycle)
                    if(g.vertices().isElem(u)) {
                        v = u;
                        break;
                    }
        }
        return eCycle;
    }
}
