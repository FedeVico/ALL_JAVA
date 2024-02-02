/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Disjoint Sets implemented by means of a dictionary.
 */

package dataStructures.disjointSet;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;

public class DisjointSetDictionary<T extends Comparable<? super T>> implements DisjointSet<T> {
    private Dictionary<T, T> dic;

    /**
     * Important: use generic parameter <> or <T,T>
     */
    public DisjointSetDictionary() {
        dic = new AVLDictionary<>();
    }

    /**
     * Important: use size method which is O(1)
     */
    public int numElements() {
        return dic.size();
    }

    public boolean isEmpty() {
        return dic.isEmpty();
    }

    /**
     * Important: use isDefinedAt which is O(log n)
     * Shouldn't iterate through all keys as it's very inefficient
     */
    public boolean isElem(T elem) {
        return dic.isDefinedAt(elem);
    }

    /**
     * Important: dictionary must ONLY be modified if elem is not already included
     */
    public void add(T elem) {
        if (!dic.isDefinedAt(elem))
            dic.insert(elem, elem);
    }

    /**
     * Important:
     * Should return null if elem is not in dictionary.
     * Must use equals and not ==
     * Should do a SINGLE search in dictionary for each value of `current'
     * Short circuit for && avoids nullPointerException if `current' is null
     */
    private T root(T elem) {
        T previous = null;
        T current = elem;
        do {
            previous = current;
            current = dic.valueOf(current);
        } while (current != null && !current.equals(previous));
        return current;
    }

    /**
     * Important:
     * Must use equals and not ==
     * root(elem) can be null hence root(elem).equals(elem) isn't correct
     */
    private boolean isRoot(T elem) {
        return elem.equals(root(elem));
    }

    /**
     * Important:
     * Should do a SINGLE search in dictionary for each elem (must use root method)
     * Shouldn't search for elem2 if elem1 is null
     * Short circuit for && avoids computing equals is root2 is null
     */
    public boolean areConnected(T elem1, T elem2) {
        T root1 = root(elem1);
        if (root1 == null)
            return false;
        else {
            T root2 = root(elem2);
            return root2 != null && root1.equals(root2);
        }
    }

    /**
     * Important:
     * Must return empty list (and not null) if elem is not in DisjointSet
     * Must use generic parameter in ArrayList constructor
     * Should compute root(elem) just once
     * root can be null but root(v) can't. Hence root.equals(root(v)) isn't correct
     * Should use equals and not == to compare roots
     * Should use append to add new new elements to list
     */
    public List<T> kind(T elem) {
        List<T> list = new ArrayList<>();
        T root = root(elem);
        for (T v : dic.keys())
            if (root(v).equals(root))
                list.append(v);
        return list;
    }

    /**
     * Important:
     * Should only do a SINGLE search for elem1 and elem2, hence root method must be used
     * Should not search for elem2 if root(elem1) is null
     * Must throw an exception if one element is not in DisjointSet
     */
    public void union(T elem1, T elem2) {
        T root1 = root(elem1);
        if (root1 == null)
            throw new IllegalArgumentException("union: missing element");
        T root2 = root(elem2);
        if (root2 == null)
            throw new IllegalArgumentException("union: missing element");
        if (root1.compareTo(root2) < 0)
            dic.insert(root2, root1);
        else
            dic.insert(root1, root2);
    }

    public int numKinds() {
        int n = 0;
        for (T elem : dic.keys())
            if (isRoot(elem))
                n++;
        return n;
    }

    public void flatten() {
        Dictionary<T, T> newDic = new AVLDictionary<>();
        for (T elem : dic.keys()) {
            newDic.insert(elem, root(elem));
        }
        dic = newDic;
    }

    public List<List<T>> kinds() {
        List<List<T>> kinds = new ArrayList<>();

        for (T elem : dic.keys())
            if (isRoot(elem))
                kinds.append(kind(elem));

        return kinds;
    }

    public String toString() {
        return "DisjointSetDictionary(" + dic.toString() + ")";
        //       return "DisjointSetDictionary(" + kinds().toString() + ")";
    }
}