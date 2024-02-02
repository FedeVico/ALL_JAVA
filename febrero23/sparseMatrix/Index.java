package sparseMatrix;

public class Index implements Comparable<Index> {
    private final int row;
    private final int column;

    public Index(int r, int c) {
        row = r;
        column = c;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int compareTo(Index ind) {
        int cmp = Integer.compare(row, ind.row);
        if (cmp == 0)
            cmp = Integer.compare(column, ind.column);
        return cmp;
    }

    public String toString() {
        return "Index("+ row +", "+ column +")";
    }
}
