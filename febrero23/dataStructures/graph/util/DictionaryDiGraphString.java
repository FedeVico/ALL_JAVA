package dataStructures.graph.util;

public class DictionaryDiGraphString extends DictionaryDiGraphAbstract<String> {
	
	public DictionaryDiGraphString(String cadena) {
		super(cadena);
	}
	
	public DictionaryDiGraphString(String cadena, String conector) {
		super(cadena, conector);
	}

	public String source(String s) {
		return s;
	}
	public String target(String s) {
		return s;
	}
}
