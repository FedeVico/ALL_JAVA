package dataStructures.graph.util;

public class DictionaryDiGraphInteger extends DictionaryDiGraphAbstract<Integer> {
	
	public DictionaryDiGraphInteger(String cadena) {
		super(cadena);
	}
	
	public DictionaryDiGraphInteger(String cadena, String conector) {
		super(cadena, conector);
	}

	public Integer source(String s) {
		return Integer.parseInt(s);
	}
	public Integer target(String s) {
		return Integer.parseInt(s);
	}
}
