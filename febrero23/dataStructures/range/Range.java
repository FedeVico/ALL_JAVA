/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Arithmetic sequeuences over intergers (a.k.a ranges) as Java's Iterables
 */

package dataStructures.range;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Range implements Iterable<Integer> {
    private int from, to, step;

    public Range(int from, int to, int step) {
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public Range(int from, int to) {
        this.from = from;
        this.to = to;
        this.step = (from <= to ? 1 : -1);
    }

    private class RangeIterator implements Iterator<Integer> {
        private int current;

        public RangeIterator() {
            current = from;
        }

        public boolean hasNext() {
            return step > 0 ? current <= to :
                   step < 0 ? current >= to :
                              true; // step==0 is an infinite sequence: from, from, from, ...
        }

        public Integer next() {
            if(!hasNext())
                throw new NoSuchElementException("next on exhausted iterator");

            int x = current;
            current += step;
            return x;
        }
    }

    public Iterator<Integer> iterator() {
        return new RangeIterator();
    }
}
