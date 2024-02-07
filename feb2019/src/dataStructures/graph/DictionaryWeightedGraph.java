/**----------------------------------------------
 * -- Estructuras de Datos.  2018/19
 * -- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
 * -- Escuela Técnica Superior de Ingeniería en Informática. UMA
 * --
 * -- Examen 4 de febrero de 2019
 * --
 * -- ALUMNO/NAME:
 * -- GRADO/STUDIES:
 * -- NÚM. MÁQUINA/MACHINE NUMBER:
 * --
 * ----------------------------------------------
 */

package dataStructures.graph;

import java.util.Iterator;
import java.util.Objects;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;

import dataStructures.set.Set;
import dataStructures.set.AVLSet;
import dataStructures.set.HashSet;
import dataStructures.tuple.Tuple2;

public class DictionaryWeightedGraph<V, W extends Comparable<? super W>> implements WeightedGraph<V, W> {

    static class WE<V1, W1 extends Comparable<? super W1>> implements WeightedEdge<V1, W1> {

        V1 src, dst;
        W1 wght;

        WE(V1 s, V1 d, W1 w) {
            src = s;
            dst = d;
            wght = w;
        }

        public V1 source() {
            return src;
        }

        public V1 destination() {
            return dst;
        }

        public W1 weight() {
            return wght;
        }

        public String toString() {
            return "WE(" + src + "," + dst + "," + wght + ")";
        }

        public int hashCode() {
            // COMPLETAR
            return Objects.hash(src, dst, wght);
        }

        public boolean equals(Object obj) {
            // COMPLETAR
            boolean res = false;
            if (obj instanceof WeightedEdge<?, ?>) {
                WeightedEdge<V1, W1> we = (WeightedEdge<V1, W1>) obj;
                res = (src.equals(we.source()) && dst.equals(we.destination()) &&
                        wght.equals(we.weight()));
            }
            return res;
        }

        public int compareTo(WeightedEdge<V1, W1> o) {
            // COMPLETAR
            return wght.compareTo(o.weight());
        }
    }

    /**
     * Each vertex is associated to a dictionary containing associations
     * from each successor to its weight
     */
    protected Dictionary<V, Dictionary<V, W>> graph;

    public DictionaryWeightedGraph() {
        graph = new HashDictionary<>();
    }

    public void addVertex(V v) {
        // COMPLETAR
        Dictionary<V, W> dic = new HashDictionary<>();
        graph.insert(v, dic);
    }

    public void addEdge(V src, V dst, W w) {
        // COMPLETAR
        if (!graph.isDefinedAt(src) || !graph.isDefinedAt(dst))
            throw new GraphException();
        Dictionary<V, W> dic = graph.valueOf(src);
        dic.insert(dst, w);
    }

    public Set<Tuple2<V, W>> successors(V v) {
        // COMPLETAR
        if (!graph.isDefinedAt(v))
            throw new GraphException();
        Set<Tuple2<V, W>> res = new HashSet<>();
        Dictionary<V, W> dic = graph.valueOf(v);
        for (Tuple2<V, W> values : dic.keysValues()) {
            res.insert(values);
        }
        return res;
    }

    public Set<WeightedEdge<V, W>> edges() {
        // COMPLETAR
        Set<WeightedEdge<V, W>> edges = new HashSet<>();
        for (Tuple2<V, Dictionary<V, W>> src : graph.keysValues()) {
            for (Tuple2<V, W> dst : src._2().keysValues()) {
                WeightedEdge<V, W> edge = new WE<>(src._1(), dst._1(), dst._2());
                edges.insert(edge);
            }
        }
        return edges;
    }

    /** DON'T EDIT ANYTHING BELOW THIS COMMENT **/

    public Set<V> vertices() {
        Set<V> vs = new HashSet<>();
        for (V v : graph.keys())
            vs.insert(v);
        return vs;
    }

    public boolean isEmpty() {
        return graph.isEmpty();
    }

    public int numVertices() {
        return graph.size();
    }

    public int numEdges() {
        int num = 0;
        for (Dictionary<V, W> d : graph.values())
            num += d.size();
        return num; // aqui era num/2 pero porque contaba las aristas doble
    }

    public String toString() {
        String className = getClass().getSimpleName();
        String s = className + "(vertices=(";

        Iterator<V> it1 = vertices().iterator();
        while (it1.hasNext())
            s += it1.next() + (it1.hasNext() ? ", " : "");
        s += ")";

        s += ", edges=(";
        Iterator<WeightedEdge<V, W>> it2 = edges().iterator();
        while (it2.hasNext())
            s += it2.next() + (it2.hasNext() ? ", " : "");
        s += "))";

        return s;
    }
}
