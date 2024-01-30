
/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Tests for First Fit algorithms for Bin Packing problem
 */

import dataStructures.list.LinkedList;
import dataStructures.list.List;

public class BinPackingDemo {
    public static void main(String[] args) throws Exception {
        main1(args);
        System.out.println();
        main2(args);
        System.out.println();
        main3(args);
    }

    public static void test(int initialCapacity, int[] weights) throws Exception {
        System.out.printf("Solving a problem instance with %d as initial capacity and these weights", initialCapacity);
        for (int weight : weights)
            System.out.printf(" %d", weight);
        System.out.println(".");
        AVL avl = new AVL();
        avl.addAll(initialCapacity, weights);
        List<Bin> bins = avl.toList();
        System.out.println("Solution is: ");
        System.out.println(bins);
    }

    public static void main1(String[] args) throws Exception {
        test(10, new int[] { 9, 9, 9, 9, 9, 9, 9, 9, 9 });
    }

    public static void main2(String[] args) throws Exception {
        test(10, new int[] { 5, 7, 5, 2, 4, 2, 5, 1, 6 });
    }

    public static void main3(String[] args) throws Exception {
        // Crear una lista de pesos de objetos
        List<Integer> weights = new LinkedList<>();
        weights.append(1);
        weights.append(4);
        weights.append(5);
        weights.append(7);
        weights.append(2);

        // Capacidad inicial de los bins
        int initialCapacity = 10;

        // Probar el método linearBinPacking
        List<Bin> bins = LinearBinPacking.linearBinPacking(initialCapacity, weights);

        // Imprimir los bins resultantes
        System.out.println("Bins resultantes:");
        for (Bin bin : bins) {
            System.out.println(bin);
        }

        // Probar el método allWeights
        Iterable<Integer> allWeights = LinearBinPacking.allWeights(bins);

        // Imprimir todos los pesos de los objetos en los bins
        System.out.println("\nTodos los pesos de los objetos en los bins:");
        for (int weight : allWeights) {
            System.out.print(weight);
            System.out.print(",");
        }
    }
}