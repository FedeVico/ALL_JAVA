/******************************************************************************
 * Student's name: ?????????????????????????????????????
 * Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
 * Student's group: ?
 * PC code: ???
 *
 * Data Structures. Grados en Informatica. UMA.
 *****************************************************************************/

package sparseMatrix;

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
    }

    // | = Exercise b - value
    private int value(Index ind) {
        // todo
        return 0;
    }

    // | = Exercise c - update
    private void update(Index ind, int value) {
        // todo
    }

    // | = Exercise d - index
    private Index index(int r, int c) {
        // todo
        return null;
    }

    // | = Exercise e - set
    public void set(int r, int c, int value) {
        // todo
    }

    // | = Exercise f - get
    public int get(int r, int c) {
        // todo
        return 0;
    }

    // | = Exercise g - add
    public static SparseMatrix add(SparseMatrix m1, SparseMatrix m2) {
        // todo
        return null;
    }

    // | = Exercise h - transpose
    public SparseMatrix transpose() {
        // todo
        return null;
    }

    // | = Exercise i - toString
    public String toString() {
        // todo
        return null;
    }

    // | = Exercise j - fromList and fromList2
    // Complexity using get and ArrayList:
    // Complexity using get and LinkedList:
    public static SparseMatrix fromList(int r, int c, List<Integer> list) {
        // todo
        return null;
    }

    // Complexity using iterator and ArrayList:
    // Complexity using iterator and LinkedList:
    public static SparseMatrix fromList2(int r, int c, List<Integer> list) {
        // todo
        return null;
    }
}
