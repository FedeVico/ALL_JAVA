/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Pairing Heaps
 */

package dataStructures.heap;

import dataStructures.list.LinkedList;
import dataStructures.list.List;
import java.util.Iterator;

public class PairingHeap <T extends Comparable<? super T>> implements Heap<T> {
    private static class Heap<E> {
        E elem;
        List<Heap<E>> heaps;

        // singleton heap
        Heap(E e) {
            elem = e;
            heaps = new LinkedList<>();
        }
    }

    protected Heap<T> root;
    protected int size;

    public PairingHeap() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    private static <T extends Comparable<? super T>> Heap<T> merge(Heap<T> h1, Heap<T> h2) {
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;

        // force h1 to have smaller root
        if (h2.elem.compareTo(h1.elem) < 0) {
            // swap heap1 and heap2
            Heap<T> tmp = h1;
            h1 = h2;
            h2 = tmp;
        }

        // Add h2 to list of heaps in h1
        h1.heaps.prepend(h2);
        return h1;
    }

    public void insert(T x) {
        Heap<T> newHeap = new Heap<>(x);
        root = merge(root, newHeap);
        size++;
    }

    public T minElem() {
        if (isEmpty())
            throw new EmptyHeapException("minElem on empty heap");
        else
            return root.elem;
    }

    // merge heaps pairwise in left to right order, and then merges those in right to left order
    private static <T extends Comparable<? super T>> Heap<T> merges(Iterator<Heap<T>> it) {
        if (!it.hasNext())
            return null;
        else {
            Heap<T> h1 = it.next();
            if (it.hasNext()) {
                Heap<T> h2 = it.next();
                return merge(merge(h1, h2), merges(it));
            } else
                return h1;
        }
    }

    public void delMin() {
        if (isEmpty())
            throw new EmptyHeapException("delMin on empty heap");
        else {
            root = merges(root.heaps.iterator());
            size--;
        }
    }

    void BuildStringRec(StringBuilder sb, Heap<T> heap) {
        if(heap!=null) {
            sb.append("Node<");
            sb.append(heap.elem);
            sb.append("[");
            Iterator<Heap<T>> it = heap.heaps.iterator();
            while(it.hasNext()) {
                BuildStringRec(sb,it.next());
                if(it.hasNext())
                    sb.append(",");
            }
            sb.append("]>");
        }
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("(");
        BuildStringRec(sb, this.root);
        sb.append(")");
        return sb.toString();
    }
}
