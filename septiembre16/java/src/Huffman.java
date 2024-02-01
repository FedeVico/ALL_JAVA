/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name:
 * Student's group:
 */
package src;

import java.util.Iterator;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.set.SortedLinkedSet;
import dataStructures.tuple.Tuple2;

public class Huffman {
    // Exercise 1
    public static Dictionary<Character, Integer> weights(String s) {
        // to do
        Dictionary<Character, Integer> dic = new AVLDictionary();
        for (int i = 0; i < s.length(); i++) {
            char actual = s.charAt(i);
            int current = 1;
            if (dic.isDefinedAt(actual)) {
                current = dic.valueOf(actual) + 1;
            }
            dic.insert(actual, current);
        }
        return dic;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
        // to do
        PriorityQueue<WLeafTree<Character>> result = new BinaryHeapPriorityQueue<>();
        Dictionary<Character, Integer> dic = weights(s);
        for (Tuple2<Character, Integer> values : dic.keysValues()) {
            result.enqueue(new WLeafTree<Character>(values._1(), values._2()));
        }
        return result;
    }

    // Exercise 2.b
    public static WLeafTree<Character> huffmanTree(String s) {
        // to do
        PriorityQueue<WLeafTree<Character>> hojas = huffmanLeaves(s);
        if (hojas.isEmpty())
            throw new HuffmanException("invalid string");
        WLeafTree<Character> menor1 = hojas.first();
        hojas.dequeue();
        if (hojas.isEmpty())
            throw new HuffmanException("invalid string");
        WLeafTree<Character> menor2 = hojas.first();
        hojas.dequeue();
        WLeafTree<Character> res = new WLeafTree<>(menor1, menor2);
        while (!hojas.isEmpty()) {
            hojas.enqueue(res);
            menor1 = hojas.first();
            hojas.dequeue();
            menor2 = hojas.first();
            hojas.dequeue();
            res = new WLeafTree<>(menor1, menor2);
        }
        return res;
    }

    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1,
            Dictionary<Character, List<Integer>> d2) {
        // to do
        Dictionary<Character, List<Integer>> res = new AVLDictionary<>();
        for (Tuple2<Character, List<Integer>> rama : d1.keysValues()) {
            res.insert(rama._1(), rama._2());
        }
        for (Tuple2<Character, List<Integer>> rama : d2.keysValues()) {
            if (res.isDefinedAt(rama._1())) {
                throw new HuffmanException("dictionaries share some keys, no union for you");
            }
            res.insert(rama._1(), rama._2());
        }
        return res;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        // to do
        Dictionary<Character, List<Integer>> res = new AVLDictionary<>();
        for (Tuple2<Character, List<Integer>> rama : d.keysValues()) {
            List<Integer> lista = new ArrayList<>();
            lista = rama._2();
            lista.prepend(i);
            res.insert(rama._1(), lista);
        }
        return res;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        // to do
        Dictionary<Character, List<Integer>> res = new AVLDictionary<>();
        if (ht.isLeaf()) {
            res.insert(ht.elem(), new LinkedList<>());
            return res;
        } else {
            Dictionary<Character, List<Integer>> izq = huffmanCode(ht.leftChild());
            Dictionary<Character, List<Integer>> der = huffmanCode(ht.rightChild());
            izq = prefixWith(0, izq);
            der = prefixWith(1, der);
            return joinDics(izq, der);
        }
    }

    // Exercise 4
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        // to do
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (Integer valor : hc.valueOf(c)) {
                res.append(valor);
            }
        }
        return res;
    }

    // Exercise 5
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        // to do
        String palabra = "";
        Iterator<Integer> it = bits.iterator();
        while (it.hasNext()) {
            WLeafTree<Character> aux = ht;
            while (!aux.isLeaf()) {
                Integer next = it.next();
                if (next.equals(0))
                    aux = aux.leftChild();
                else
                    aux = aux.rightChild();
            }
            palabra += aux.elem();
        }
        return palabra;
    }
}
