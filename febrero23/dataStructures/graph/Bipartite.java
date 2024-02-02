/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Class to test for graph bipartiteness using depth first traversal.
 */

package dataStructures.graph;

import dataStructures.stack.Stack;
import dataStructures.dictionary.Dictionary;
import dataStructures.stack.LinkedStack;
import dataStructures.dictionary.HashDictionary;

public class Bipartite<V> {

    private class Pair {
        V vertex;
        Boolean color;
        public Pair(V v, Boolean c) {
            vertex = v;
            color = c;
        }
    }

    private Stack<Pair> stack;
    private Dictionary<V, Boolean> assignedColor;
    private boolean bipartite = true;

    public Bipartite(Graph<V> g) {
        stack = new LinkedStack<>();
        assignedColor = new HashDictionary<>();

        V source = g.vertices().iterator().next();
        stack.push(new Pair(source, true));

        while (!stack.isEmpty()) {
            Pair pair = stack.top();
            stack.pop();

            Boolean color = assignedColor.valueOf(pair.vertex);
            if (color == null)  //pair.vertex was unvisited
                assignedColor.insert(pair.vertex, pair.color);
            else if (color != pair.color) {
                bipartite = false;
                break;
            }

            boolean inverseColor = !pair.color;
            for (V v : g.successors(pair.vertex)) {
                Boolean vColor = assignedColor.valueOf(v);
                if (vColor == null) // v is unvisited
                    stack.push(new Pair(v, inverseColor));
                else if(vColor != inverseColor) {
                    bipartite = false;
                    break;
                }
            }
        }
    }

    public boolean isBipartite() {
        return bipartite;
    }

    public Dictionary<V,Boolean> colors() {
        return assignedColor;
    }
}