public class LongBits {

    public static final int bitsPerLong = Long.SIZE;

    /**
     * Cuenta el número de unos en la representación binaria de un long.
     *
     * @param bits el long del que se contarán los unos.
     * @return el número de unos en la representación binaria del long.
     */
    public static long countOnes(long bits) {
        bits -= (bits >>> 1) & 0x5555555555555555L;
        bits = (bits & 0x3333333333333333L) + ((bits >>> 2) & 0x3333333333333333L);
        bits = (bits + (bits >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
        bits += bits >>> 8;
        bits += bits >>> 16;
        bits += bits >>> 32;
        return bits & 0x7f;
    }

    /**
     * Comprueba si un bit particular en la representación binaria de un long está
     * encendido.
     *
     * @param bits el long en el que se realizará la comprobación.
     * @param bit  el índice del bit que se comprobará.
     * @return true si el bit en la posición indicada está encendido, false en caso
     *         contrario.
     */
    public static boolean contains(long bits, long bit) {
        return (bits & (1L << bit)) != 0;
    }

    /**
     * Establece un bit particular en la representación binaria de un long.
     *
     * @param bits el long en el que se establecerá el bit.
     * @param bit  el índice del bit que se establecerá.
     * @return el long resultante después de establecer el bit.
     */
    public static long set(long bits, long bit) {
        return bits | (1L << bit);
    }

    public static long delete(long bitset, long element) {
        return bitset & ~(1L << element);
    }

    /**
     * Realiza una operación de unión bit a bit entre dos long.
     *
     * @param bits1 el primer long en la operación de unión.
     * @param bits2 el segundo long en la operación de unión.
     * @return el resultado de la operación de unión.
     */
    public static long or(long bits1, long bits2) {
        return bits1 | bits2;
    }
}
