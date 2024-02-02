package dataStructures.graph.util;

public class DictionaryGraphString extends DictionaryGraphAbstract<String> {
	
	public DictionaryGraphString(String cadena) {
		super(cadena);
	}
	
	public DictionaryGraphString(String cadena, String conector) {
		super(cadena, conector);
	}

	public String source(String s) {
		return s;
	}
	public String target(String s) {
		return s;
	}
}
