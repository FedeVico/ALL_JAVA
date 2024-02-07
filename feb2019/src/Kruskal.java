
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

import dataStructures.graph.WeightedGraph;
import dataStructures.graph.WeightedGraph.WeightedEdge;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.WBLeftistHeapPriorityQueue;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;
import dataStructures.set.HashSet;

public class Kruskal {
	public static <V, W> Set<WeightedEdge<V, W>> kruskal(WeightedGraph<V, W> g) {
		Set<WeightedEdge<V, W>> t = new HashSet<>();

		PriorityQueue<WeightedEdge<V, W>> PQ = new LinkedPriorityQueue<>();
		for (WeightedEdge<V, W> edge : g.edges()) {
			PQ.enqueue(edge);
		}

		Dictionary<V, V> Dict = new HashDictionary<>();
		for (V vertex : g.vertices()) {
			Dict.insert(vertex, vertex);
		}

		while (!PQ.isEmpty()) {
			WeightedEdge<V, W> edge = PQ.first();
			PQ.dequeue();
			V a = representante(edge.source(), Dict);
			V b = representante(edge.destination(), Dict);
			if (!a.equals(b)) {
				t.insert(edge);
				Dict.insert(b, edge.source());
			}
		}
		return t;
	}

	private static <V> V representante(V vertex, Dictionary<V, V> dict) {
		V value = dict.valueOf(vertex);

		if (value.equals(vertex)) {
			return vertex;
		} else {
			return representante(value, dict);
		}
	}

	// Sólo para evaluación continua / only for part time students
	public static <V, W> Set<Set<WeightedEdge<V, W>>> kruskals(WeightedGraph<V, W> g) {

		// COMPLETAR

		return null;
	}
}
