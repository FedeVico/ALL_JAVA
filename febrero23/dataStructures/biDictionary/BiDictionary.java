/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Interface for bidirectional dictionaries, a bijective association between two sets of unique keys and unique values.
 */

package dataStructures.biDictionary;

import dataStructures.tuple.Tuple2;

/**
 * Interface for bidirectional dictionaries (finite maps), a bijective association between two sets of unique keys and unique values.
 * @param <K> Type of keys.
 * @param <V> Type of values.
 */

public interface BiDictionary<K,V> {
		/**
		 * Test for dictionary emptiness.
		 * 
		 * @return {@code true} if bidictionary is empty (stores zero key/value associations), else {@code false}.
		 */
		boolean isEmpty();

		/**
		 * Retrieves number of key/value associations in bidictionary.
		 * @return Number of associations in bidictionary.
		 */	
		int size();

		/**
		 * Inserts a new key/value association in bidictionary. If association was
         * already present in bidictionary, old association is replaced by new one.
		 * @param k Key in association.
		 * @param v Value in association.
		 */
		void insert(K k, V v);
		
		
		/**
		 * Retrieves value associated to key {@code k}. If key is not
		 * in bidictionary, {@code null} is returned.
		 * @param k Key for which associated value is sought.
		 * @return Value associated to key or {@code null} if key is not in bidictionary.
		 */
		V valueOf(K k);

		/**
		 * Retrieves key associated to value {@code v}. If value is not
		 * in bidictionary, {@code null} is returned.
		 * @param v value for which associated value is sought.
		 * @return Key associated to value or {@code null} if value is not in bidictionary.
		 */
		K keyOf(V v);

		/**
		 * Tests whether an association with key {@code k} is included in bidictionary.
		 * @param k Key of association.
		 * @return {@code true} if bidictionary includes an association for key {@code k}, else {@code false}.
		 */
		boolean isDefinedAtKey(K k);

		/**
		 * Tests whether an association with value {@code v} is included in bidictionary.
		 * @param v Value of association.
		 * @return {@code true} if bidictionary includes an association for value {@code v}, else {@code false}.
		 */
		boolean isDefinedAtValue(V v);

		/**
		 * Removes a key/value association from bidictionary. If association is not
		 * in bidictionary, bidictionary is not modified (this is not considered an
		 * error and thus no exception is thrown).
		 * @param k Key of association to remove.
		 */
		void deleteByKey(K k);

		/**
		 * Removes a key/value association from bidictionary. If association is not
		 * in bidictionary, bidictionary is not modified (this is not considered an
		 * error and thus no exception is thrown).
		 * @param v value of association to remove.
		 */
		void deleteByValue(V v);

		/** 
		 * Retrieves an {@code Iterable} over all keys in bidictionary.
		 * Note that {@code remove} method is not supported in corresponding {@code iterator}. 
		 * Note also that bidictionary structure or keys should not be modified during iteration as
		 * iterator state may become inconsistent.
		 * @see Iterable
		 * @return An {@code Iterable} over all keys in bidictionary.
		 */
		Iterable<K> keys();

		/** 
		 * Retrieves an {@code Iterable} over all values in bidictionary.
		 * Note that {@code remove} method is not supported in corresponding {@code iterator}. 
		 * Note also that bidictionary structure or keys should not be modified during iteration as
		 * iterator state may become inconsistent.
		 * @see Iterable
		 * @return An {@code Iterable} over all keys in bidictionary.
		 */
		Iterable<V> values();

		/** 
		 * Retrieves an {@code Iterable} over all keys and values in bidictionary.
		 * Note that {@code remove} method is not supported in corresponding {@code iterator}. 
		 * Note also that bidictionary structure or keys should not be modified during iteration as
		 * iterator state may become inconsistent.
		 * @see Iterable
		 * @return An {@code Iterable} over all keys in dictionary.
		 */
		Iterable<Tuple2<K,V>> keysValues();
	}
