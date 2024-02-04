/******************************************************************************
 * Student's name: ?????????????????????????????????????
 * Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
 * Student's group: ?
 * PC code: ???
 *
 * Data Structures. Grados en Informatica. UMA.
 *****************************************************************************/

package sparsematrix.src;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.List;

// | = Exercise a - SparseMatrix constructor
public class SparseMatrix {
    public final int rows;
    public final int columns;
    private Dictionary<Index, Integer> nonZeros;

    public SparseMatrix(int r, int c) {
        // todo
        this.rows = r;
        this.columns = c;
        this.nonZeros = new AVLDictionary<>();
    }

    // | = Exercise b - value
    private int value(Index ind) {
        // todo
        int value = 0;
        if (nonZeros.isDefinedAt(ind))
            value = nonZeros.valueOf(ind);
        return value;
    }

    // | = Exercise c - update
    private void update(Index ind, int value) {
        // todo
        if (value == 0) {
            nonZeros.delete(ind);
        } else {
            nonZeros.delete(ind);
            nonZeros.insert(ind, value);
        }
    }

    // | = Exercise d - index
    private Index index(int r, int c) {
        // todo
        if (r < this.rows && r < this.columns && r >= 0 && c >= 0)
            return new Index(r, c);
        else
            throw new IllegalArgumentException();
    }

    // | = Exercise e - set
    public void set(int r, int c, int value) {
        // todo
        if (r < this.rows && r < this.columns && r >= 0 && c >= 0) {
            Index indice = index(r, c);
            update(indice, value);
        } else
            throw new IllegalArgumentException();
    }

    // | = Exercise f - get
    public int get(int r, int c) {
        if (r < this.rows && r < this.columns && r >= 0 && c >= 0) {
            Index indice = index(r, c);
            return value(indice);
        } else
            throw new IllegalArgumentException();
    }

    // | = Exercise g - add
    public static SparseMatrix add(SparseMatrix m1, SparseMatrix m2) {
        // todo
        if (m1.rows != m2.rows || m1.columns != m2.columns)
            throw new IllegalArgumentException();
        SparseMatrix result = new SparseMatrix(m1.rows, m1.columns);
        for (Index ind : m1.nonZeros.keys()) {
            result.update(ind, m1.value(ind));
        }
        for (Index ind : m2.nonZeros.keys()) {
            result.update(ind, result.value(ind) + m2.value(ind));
        }
        return result;
    }

    // | = Exercise h - transpose
    public SparseMatrix transpose() {
        // todo
        SparseMatrix result = new SparseMatrix(columns, rows);
        for (Index ind : nonZeros.keys()) {
            int original = value(ind);
            Index indTrans = new Index(ind.getColumn(), ind.getRow());
            result.update(indTrans, original);
        }
        return result;
    }

    // | = Exercise i - toString
    public String toString() {
        // todo
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Index index = index(i, j);
                int value = value(index);
                res.append(String.format("%6d", value));
            }
            res.append("\n");
        }
        return res.toString();
    }

    // | = Exercise j - fromList and fromList2
    // Complexity using get and ArrayList:
    // Complexity using get and LinkedList:
    public static SparseMatrix fromList(int r, int c, List<Integer> list) {
        // todo
        SparseMatrix m = new SparseMatrix(r, c);
        for (int i = 0; i < list.size(); i += 3) {
            int row = list.get(i);
            int column = list.get(i + 1);
            int value = list.get(i + 2);

            m.set(row, column, value);
        }
        return m;
    }

    // Complexity using iterator and ArrayList:
    // Complexity using iterator and LinkedList:
    public static SparseMatrix fromList2(int r, int c, List<Integer> list) {
        // todo
        return null;
    }
}
