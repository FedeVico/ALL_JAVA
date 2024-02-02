/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Disjoint Sets.
 */

package dataStructures.disjointSet;

import dataStructures.list.List;

public interface DisjointSet<T extends Comparable<? super T>> {
    /**
     * Total number of elements in disjoint set
     */
    int numElements();

    /**
     * True is disjoint set is empty
     */
    public boolean isEmpty();

    /**
     * Adds new element to disjoint set corresponding to a new unitary equivalence class
     */
    void add(T elem);

    /**
     * Checks if element is is disjoint set
     */
    boolean isElem(T elem);

    /**
     * true if elem1 and elem2 are in same equivalence class
     */
    boolean areConnected(T elem1, T elem2);

    /**
     * A list with all elements in same equivalence class as elem
     */
    List<T> kind(T elem);

    /**
     * Joints equivalence classes corresponding to parameters
     */
    void union(T elem1, T elem2);

    /**
     * Number of equivalence classes in this disjoint set
     */
    int numKinds();


    /**
     * All equivalence classes in this disjoint set
     */
    List<List<T>> kinds();
}
