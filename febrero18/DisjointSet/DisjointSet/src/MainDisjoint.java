import dataStructures.set.DisjointSet;
import dataStructures.set.DisjointSetDictionary;

public class MainDisjoint {
    public static void main(String[] args) {
        DisjointSet<String> dj = new DisjointSetDictionary<>();
        String[] pals = { "hola", "a", "todos", "como", "estais", "por", "aqui", "bien" };
        for (String s : pals) {
            dj.add(s);
        }
        System.out.println(dj);
        System.out.println("Size: " + dj.numElements());
        dj.union("hola", "a");
        System.out.println(dj);
        dj.union("como", "estais");
        System.out.println(dj);
        dj.union("por", "aqui");
        System.out.println(dj);
        dj.union("hola", "bien");
        System.out.println(dj);
        dj.union("estais", "por");
        System.out.println(dj);
        System.out.println(dj.kind("bien"));
        System.out.println(dj.areConnected("hola", "hola"));
        System.out.println(dj.areConnected("hola", "bien"));
        System.out.println(dj.areConnected("no", "esta"));
        System.out.println(dj.areConnected("por", "como"));

        // Solo alumnos sin evaluacion continua.
        // =====================================
        // Quitar comentarios a las lineas siguientes
        // para probar flatten() y kinds()

        dj.flatten();
        System.out.println(dj);
        System.out.println(dj.kinds());
    }
}
