package dataStructures.dictionary;

import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */
public class HashBiDictionary<K, V> implements BiDictionary<K, V> {
	private Dictionary<K, V> bKeys;
	private Dictionary<V, K> bValues;

	public HashBiDictionary() {
		// TODONE
		bKeys = new HashDictionary<>();
		bValues = new HashDictionary<>();
	}

	public boolean isEmpty() {
		// TODONE
		return bKeys.isEmpty();
	}

	public int size() {
		// TODONE
		return bKeys.size();
	}

	public void insert(K k, V v) {
		// TODONE
		// if existen los borramos de ambos lados del dic para reinsertarlos
		if (bKeys.isDefinedAt(k)) {
			bValues.delete(bKeys.valueOf(k));
		}
		if (bValues.isDefinedAt(v)) {
			bKeys.delete(bValues.valueOf(v));
		}
		bKeys.insert(k, v);
		bValues.insert(v, k);
	}

	public V valueOf(K k) {
		// TODONE
		if (!bKeys.isDefinedAt(k))
			return null;
		else
			return bKeys.valueOf(k);
	}

	public K keyOf(V v) {
		// TODONE
		if (!bValues.isDefinedAt(v))
			return null;
		else
			return bValues.valueOf(v);
	}

	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}

	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}

	public void deleteByKey(K k) {
		// TODONE
		if (isDefinedKeyAt(k)) {
			bValues.delete(valueOf(k));
			bKeys.delete(k);
		} else {
			throw new NoSuchElementException("no existe");
		}
	}

	public void deleteByValue(V v) {
		// TODONE
		if (isDefinedValueAt(v)) {
			bKeys.delete(keyOf(v));
			bValues.delete(v);
		} else {
			throw new NoSuchElementException("no existe");
		}
	}

	public Iterable<K> keys() {
		return bKeys.keys();
	}

	public Iterable<V> values() {
		return bValues.keys();
	}

	public Iterable<Tuple2<K, V>> keysValues() {
		return bKeys.keysValues();
	}

	public static <K, V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K, V> dict) {
		// TODO
		if (esInyectivo(dict.values())) { // si es inyectivo creamos nuevo biDict
			BiDictionary<K, V> biDict = new HashBiDictionary<>();
			for (Tuple2<K, V> valores : dict.keysValues()) { // cada tupla de valores es K, V
				biDict.insert(valores._1(), valores._2());
			}
			return biDict;
		} else {
			throw new IllegalArgumentException("No es inyectivo");
		}
	}

	private static <K, V extends Comparable<? super V>> boolean esInyectivo(Iterable<V> values) {
		boolean inyectivo = true;
		V v;
		Set<V> conjunto = new AVLSet<>();
		Iterator<V> it = values.iterator(); // iteramos sobre los valores
		while (it.hasNext() && inyectivo) {
			v = it.next();
			if (conjunto.isElem(v)) { // si ya se ha asignado un valor antes, no es inyectivo
				inyectivo = false;
			}
			conjunto.insert(v); // seguimos insertando para comprobar
		}
		return inyectivo;
	}

	public <W> BiDictionary<K, W> compose(BiDictionary<V, W> bdic) {
		// TODO
		BiDictionary<K, W> biDict = new HashBiDictionary<>();
		for (Tuple2<K, V> valores : bKeys.keysValues()) { // cada tupla de valores es K, V
			if (bdic.isDefinedKeyAt(valores._2())) // si estan relacionados los dic por su K,
				biDict.insert(valores._1(), bdic.valueOf(valores._2())); // ..entonces insert de su valor
		}
		return biDict;
	}

	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K, K> bd) {
		// TODO
		// comprobar que todos los valores de bdic comparado con bd tienen su relacion y
		// son iguales
		boolean permutacion = true;
		Set<K> conj1 = new AVLSet<>();
		Set<K> conj2 = new AVLSet<>();
		for (Tuple2<K, K> values : bd.keysValues()) {
			conj1.insert(values._1());
			conj2.insert(values._2());
		}
		Iterator<K> it = conj1.iterator();
		while (it.hasNext() && permutacion) {
			if (!bd.isDefinedKeyAt(it.next())) {
				permutacion = false;
			}
		}

		return permutacion;
	}

	// Solo alumnos con evaluaci�n por examen final.
	// =====================================

	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K, K> bd) {
		// TODO
		if (isPermutation(bd)) {
			List<K> lista = new ArrayList<>();
			lista.append(k);
			K valorAsociado = bd.valueOf(k);
			lista.append(valorAsociado);

			while (valorAsociado != k) {
				valorAsociado = bd.valueOf(valorAsociado);
				lista.append(valorAsociado);
			}
			lista.remove(lista.size() - 1);
			return lista;
		} else {
			throw new IllegalArgumentException("el bidiccionario no es permutacion");
		}
	}

	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K, K> bd) {
		// TODO
		/*
		 * Se parte de un conjunto
		 * S con los elementos del BiDictionary. Se calcula la órbita de un elemento
		 * cualquiera de S en el BiDictionary. A
		 * continuación, se eliminan de S todos los elementos que forman parte de la
		 * órbita calculada y se repite el proceso
		 * hasta que S quede vacío. En la figura anterior, los ciclos del BiDictionary
		 * son [[1, 4, 5, 7], [2, 3]] (el orden no tiene
		 * necesariamente por qué coincidir).
		 */
		if (isPermutation(bd)) {
			BiDictionary<K, K> aux = new HashBiDictionary<>();
			List<K> lista = new ArrayList<>();
			List<List<K>> result = new ArrayList<>();

			for (K k : bd.keys()) {
				aux.insert(k, bd.valueOf(k));
			}

			while (!aux.isEmpty()) {
				Iterator<K> it = aux.keys().iterator();
				lista = orbitOf(it.next(), bd);
				result.append(lista);
				for (K kToDelete : lista) {
					aux.deleteByKey(kToDelete);
				}
				lista = new ArrayList<>();
			}
			return result;
		} else {
			throw new IllegalArgumentException("el bidiccionario no es permutacion");
		}
	}

	// =====================================

	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}

}
