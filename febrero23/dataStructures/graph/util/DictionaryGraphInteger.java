package dataStructures.graph.util;

public class DictionaryGraphInteger extends DictionaryGraphAbstract<Integer> {
	
	public DictionaryGraphInteger(String cadena) {
		super(cadena);
	}
	
	public DictionaryGraphInteger(String cadena, String conector) {
		super(cadena, conector);
	}

	public Integer source(String s) {
		return Integer.parseInt(s);
	}
	public Integer target(String s) {
		return Integer.parseInt(s);
	}
}
