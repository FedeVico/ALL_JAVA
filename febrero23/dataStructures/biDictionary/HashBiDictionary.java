/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Bidirectional dictionaries implemented by means of two ordinary dictionaries.
 */

package dataStructures.biDictionary;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.List;
import dataStructures.list.ArrayList;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;

/**
 * An implementation of bidirectional dictionaries (finite maps), a bijective
 * association between two sets of unique keys and unique values.
 *
 * dKeys stores associations from keys to values.
 * dValues stores associations from values to keys.
 *
 * Invariant: is that if k is associated to v in the first dictionary (dKeys)
 * then v must be associated to k in the second one (dValues), and vice versa
 *
 * @param <K> Type of keys.
 * @param <V> Type of values.
 */
public class HashBiDictionary<K,V> implements BiDictionary<K,V> {
	private Dictionary<K,V> dKeys;
	private Dictionary<V,K> dValues;
	
	public HashBiDictionary() {
		dKeys = new HashDictionary<>();
		dValues = new HashDictionary<>();
	}

	// no need to check dValues as dKeys.isEmpty implies dValues.isEmpty
	public boolean isEmpty() {
		return dKeys.isEmpty();
	}

    // no need to check use dValues as dKeys.size == dValues.size
	public int size() {
		return dKeys.size();
	}

	// Must delete old associations in order to preserve invariant
    // isDefinedAt should not be used as valueOf must be used in any case.
	public void insert(K k, V v) {
		V oldValue = dKeys.valueOf(k);
		K oldKey = dValues.valueOf(v);

		// Remove old associations
		if (oldValue != null) {
			dValues.delete(oldValue);
		}
		if (oldKey != null) {
			dKeys.delete(oldKey);
		}

		// Insert new associations
		dKeys.insert(k, v);
		dValues.insert(v, k);
	}

	// No need to check for null result as dictionary valueOf already does
	public V valueOf(K k) {
		return dKeys.valueOf(k);
	}

    // No need to check for null result as dictionary valueOf already does
	public K keyOf(V v) {
		return dValues.valueOf(v);
	}
	
	public boolean isDefinedAtKey(K k) {
		return dKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedAtValue(V v) {
		return dValues.isDefinedAt(v);
	}

	// Must delete old association for v in order to preserve invariant.
    // isDefinedAt should not be used as valueOf must be used in any case.
	public void deleteByKey(K k) {
		V v = dKeys.valueOf(k);
		if (v != null) {
			dKeys.delete(k);
			dValues.delete(v);
		}
	}

    // Must delete old association for v in order to preserve invariant.
    // isDefinedAt should not be used as valueOf must be used in any case.
	public void deleteByValue(V v) {
		K k = dValues.valueOf(v);
		if (k != null) {
			dKeys.delete(k);
			dValues.delete(v);
		}
	}
	
	public Iterable<K> keys() {
		return dKeys.keys();
	}
	
	public Iterable<V> values() {
		return dValues.keys();
	}
	
	public Iterable<Tuple2<K, V>> keysValues() {
		return dKeys.keysValues();
	}

    // bijective dictionary to bidirectional dictionary
    public static <K, V> BiDictionary<K, V> toBiDictionary(Dictionary<K, V> dict) {

        // As values may have repeated elements in an ordinary dictionary, we need
        // to check that there are not repetitions to check dict is a biyective one
        Set<V> valuesSet = new HashSet<>();
        for(V v : dict.values())
            valuesSet.insert(v);

        boolean isBijective = dict.size() != valuesSet.size();

        if(!isBijective)
            throw new IllegalArgumentException("Non-bijective dictionary");

        HashBiDictionary<K, V> bdict = new HashBiDictionary<>();

        // Must use keysValues in order to avoid search for different values.
        for(Tuple2<K,V> tuple : dict.keysValues()) {
            K k = tuple._1();
            V v = tuple._2();

            // No need to use insert method as there can't be repetitions
            bdict.dKeys.insert(k, v);
            bdict.dValues.insert(v, k);
        }

        return bdict;
    }

    // Composition of this dictionary and argument.
    // isDefinedAt should not be used as valueOf must be used in any case.
	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		HashBiDictionary<K, W> composition = new HashBiDictionary<>();

        // Must use keysValues in order to avoid search for different values.
		for (Tuple2<K, V> tuple : keysValues()) {
			V v = tuple._2();
			W w = bdic.valueOf(v);
			if (w != null) {
			    K k = tuple._1();

                // No need to use insert method as there can't be repetitions
                composition.dKeys.insert(k, w);
                composition.dValues.insert(w, k);
            }
		}

		return composition;
	}

	// As keys size == values size, it is a permutation if all values are keys too
	public static <K> boolean isPermutation(BiDictionary<K, K> bdic) {
	    boolean isPerm = true;

        Iterator<K> it = bdic.values().iterator();

	    while (isPerm && it.hasNext())
            isPerm = bdic.isDefinedAtKey(it.next());

		return isPerm;
	}
	
	public static <K> List<K> orbitOf(K k, BiDictionary<K, K> bdic) {
		if (!isPermutation(bdic))
			throw new IllegalArgumentException("Not a permutation");

		List<K> list = new ArrayList<>();

		K x = bdic.valueOf(k);
		if(k != null) {
            list.append(k);
            while (!k.equals(x)) {
                list.append(x);
                x = bdic.valueOf(x);
            }
        }
		return list;
	}
	
	public static <K> List<List<K>> cyclesOf(BiDictionary<K,K> bdic) {
		if (!isPermutation(bdic))
			throw new IllegalArgumentException("Not a permutation");

		List<List<K>> cycles = new ArrayList<>();

		Set<K> keysSet = new HashSet<>();
		for (K k : bdic.keys())
			keysSet.insert(k);

		while (!keysSet.isEmpty()) {
			K k = keysSet.iterator().next();
			List<K> orbit = orbitOf(k, bdic);

			for (K x : orbit)
				keysSet.delete(x);

			cycles.append(orbit);
		}
		return cycles;
	}

	@Override
	public String toString() {
	    return "HashBiDictionary [dKeys=" + dKeys + ", dValues=" + dValues + "]";
	}
}
