package dataStructures.graph;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.List;
import dataStructures.list.ArrayList;
import dataStructures.queue.Queue;
import dataStructures.queue.LinkedQueue;

public class TopologicalSortingDic<V> {

    private List<V> topSort;
    private boolean hasCycle;

    public TopologicalSortingDic(DiGraph<V> graph) {

        topSort = new ArrayList<>();
        // dictionary: vertex -> # of pending predecessors
        Dictionary<V, Integer> pendingPredecessors = new HashDictionary<>();
        Queue<V> sources = new LinkedQueue<>();

        // initialize dictionary
        for (V v : graph.vertices()) {
            pendingPredecessors.insert(v, graph.inDegree(v));
        }

        hasCycle = false;
        while (!hasCycle && pendingPredecessors.size() > 0) {

            // collect sources

            for (V v : pendingPredecessors.keys()) {
                if (pendingPredecessors.valueOf(v) == 0) {
                    sources.enqueue(v);
                }
            }

            hasCycle = sources.isEmpty();

            // delete and visit sources; update dictionary
            while (!sources.isEmpty()) {
                V source = sources.first();
                sources.dequeue();
                pendingPredecessors.delete(source);
                topSort.append(source);
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

    public List<V> order() {
        return hasCycle ? null : topSort;
    }
}
