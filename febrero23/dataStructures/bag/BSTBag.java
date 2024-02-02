/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Bag ADT implemented using a Binary Search Tree.
 */

package dataStructures.bag;

import dataStructures.searchTree.SearchTree;
import dataStructures.searchTree.BST;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTBag<T extends Comparable<? super T>> implements Bag<T> {
    protected SearchTree<T,Integer> bst;

    public BSTBag() {
        bst = new BST<>();
    }

    public boolean isEmpty() {
        return bst.isEmpty();
    }

    public int size() {
        int sz = 0;
        for(int occ : bst.values())
            sz += occ;
        return sz;
    }

    public void insert(T item) {
        bst.updateOrInsert(occ -> occ+1, item, 1);
    }

    public int occurrences(T item) {
        Integer occ = bst.search(item);
        return occ==null ? 0 : occ;
    }

    public void delete(T item) {
        bst.updateOrInsert(occ -> Math.max(0,occ-1), item, 0);
    }

    public Iterator<T> iterator() {
        return new BagIterator();
    }

    private class BagIterator implements Iterator<T> {
        // Invariant: If value is not null, occs > 0 and that is the number
        // of copies of that value left to return. Note that bst can contain
        // nodes for which occurrences are 0.
        Iterator<Tuple2<T,Integer>> it;
        T value;
        int occs;

        void advance() {
            while (it.hasNext()) {
                Tuple2<T,Integer> node = it.next();
                if(node._2() > 0) {
                    value = node._1();
                    occs = node._2();
                    return;
                }
            }
            value = null;
        }

        public BagIterator() {
            it = bst.keysValues().iterator();
            advance();
        }

        public boolean hasNext() {
            return value != null;
        }

        public T next() {
            if(!hasNext())
                throw new NoSuchElementException();
            T x = value;
            occs--;
            if(occs<1)
                advance();
            return x;
        }
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("(");
        Iterator<T> it = this.iterator();
        while(it.hasNext()) {
            sb.append(it.next());
            if(it.hasNext())
                sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }
}
