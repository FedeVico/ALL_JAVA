package dataStructures.graph;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.List;
import dataStructures.list.ArrayList;
import dataStructures.set.Set;
import dataStructures.set.HashSet;

public class TopologicalSortingDicPar<V> {

    private List<Set<V>> topSort;
    private boolean hasCycle;

    public TopologicalSortingDicPar(DiGraph<V> graph) {

        topSort = new ArrayList<>();
        // dictionary: vertex -> # of pending predecessors
        Dictionary<V, Integer> pendingPredecessors = new HashDictionary<>();
        Set<V> sources;

        // initialize dictionary
        for (V v : graph.vertices()) {
            pendingPredecessors.insert(v, graph.inDegree(v));
        }

        hasCycle = false;
        while (!hasCycle && pendingPredecessors.size() > 0) {

            // collect sources
            sources = new HashSet<>();
            for (V v : pendingPredecessors.keys()) {
                if (pendingPredecessors.valueOf(v) == 0) {
                    sources.insert(v);
                }
            }

            hasCycle = sources.isEmpty();

            // visit sources
            topSort.append(sources);

            // delete sources; update dictionary
            for (V source : sources) {
                pendingPredecessors.delete(source);
                for (V v : graph.successors(source)) {
                    // v is in the dictionary, since it has at least one source
                    // in sources
                    pendingPredecessors.insert(v,
                            pendingPredecessors.valueOf(v) - 1);
                }
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<Set<V>> order() {
        return hasCycle ? null : topSort;
    }

}
