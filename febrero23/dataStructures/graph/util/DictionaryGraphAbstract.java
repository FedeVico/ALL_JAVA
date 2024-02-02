package dataStructures.graph.util;

import java.util.NoSuchElementException;
import java.util.Scanner;

import dataStructures.graph.DictionaryGraph;
import dataStructures.graph.GraphException;

public abstract class DictionaryGraphAbstract<T> extends DictionaryGraph<T>{
	private static String CONECTOR = "--";
	private String conector;
	
	public DictionaryGraphAbstract(String cadena) {
		this(cadena, CONECTOR);
	}
	public DictionaryGraphAbstract(String cadena, String conector) {
		this.conector = escape(conector);
		Scanner sc = new Scanner(cadena);
		createDictionary(sc);
		sc.close();		
	}
	
	private String escape(String co) {
		String salida = "";
		for(Character c : co.toCharArray()) {
			salida += "\\" + c;
		}
		return salida;
	}
	
	protected void createDictionary(Scanner sc) {
		sc.useDelimiter(",");
		while (sc.hasNext()) {
			addEdge(sc.next().trim());
		}
	}
	
	protected void addEdge(String arco) {
		try (Scanner sca = new Scanner(arco)) {
			sca.useDelimiter(conector);
			T origen = source(sca.next().trim());
			T destino = target(sca.next().trim());
			addVertex(origen);
			addVertex(destino);
			addEdge(origen, destino);
		} catch (NoSuchElementException e) {
			throw new GraphException("Bad edge parser "+ arco);
		} 
	}
	
	protected abstract T source(String src);
	protected abstract T target(String trg);
}
