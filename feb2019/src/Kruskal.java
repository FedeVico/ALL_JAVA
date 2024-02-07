
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
import dataStructures.dictionary.AVLDictionary;
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
		// Variables
		Set<WeightedEdge<V, W>> T = new HashSet<>();
		PriorityQueue<WeightedEdge<V, W>> PQ = new BinaryHeapPriorityQueue<>();
		Dictionary<V, V> dict = new HashDictionary<>();
		// Inicializar variables
		for (WeightedEdge<V, W> edge : g.edges()) {
			PQ.enqueue(edge);
		}
		for (V vertex : g.vertices()) {
			dict.insert(vertex, vertex);
		}
		// ALGORITMO
		while (!PQ.isEmpty()) {
			WeightedEdge<V, W> arista = PQ.first();
			PQ.dequeue();
			V repreSRC = representante(arista.source(), dict);
			V repreDST = representante(arista.destination(), dict);
			if (!repreSRC.equals(repreDST)) {
				dict.insert(repreDST, arista.source());
				T.insert(arista);
			}
		}
		return T;
	}

	public static <V> V representante(V v, Dictionary<V, V> dic) {
		V repre = dic.valueOf(v);
		if (repre.equals(v))
			return repre;
		else
			return representante(repre, dic);
	}

	// Sólo para evaluación continua / only for part time students
	public static <V, W> Set<Set<WeightedEdge<V, W>>> kruskals(WeightedGraph<V, W> g) {
		// COMPLETAR
		return null;
	}
}
