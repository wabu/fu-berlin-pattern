package de.berlin.fu.inf.pattern.tasks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TruthTable {
	private static final int VALUE_RANGE = 4;
	
	public final static int CONTRADICTION = 1;
	public final static int CONJUNCTION = 2;
	public final static int INHIBITION_X1 = 3;
	public final static int IDENTITY_X1 = 4;
	public final static int INHIBITION_X2 = 5;
	public final static int IDENTITY_X2 = 6;
	public final static int XOR = 7;
	public final static int OR = 8;
	public final static int NOR = 9;
	public final static int EQUIVALENCE = 10;
	public final static int NEGATION_X1 = 11;
	public final static int NEGATION_X2 = 12;
	public final static int REPLICATION = 13;
	public final static int IMPLICATION = 14;
	public final static int NAND = 15;
	public final static int TAUTOLOGY = 16;
	
	private Map<Integer, Integer> boolMap;
	
	
	public TruthTable() {
		boolMap = new HashMap<Integer, Integer>();
		
		boolMap.put(Arrays.hashCode(new int[]{0,0,0,0}), CONTRADICTION);
		boolMap.put(Arrays.hashCode(new int[]{0,0,0,1}), CONJUNCTION);
		boolMap.put(Arrays.hashCode(new int[]{0,0,1,0}), INHIBITION_X1);
		boolMap.put(Arrays.hashCode(new int[]{0,0,1,1}), IDENTITY_X1);
		boolMap.put(Arrays.hashCode(new int[]{0,1,0,0}), INHIBITION_X2);
		boolMap.put(Arrays.hashCode(new int[]{0,1,0,1}), IDENTITY_X2);
		boolMap.put(Arrays.hashCode(new int[]{0,1,1,0}), XOR);
		boolMap.put(Arrays.hashCode(new int[]{0,1,1,1}), OR);
		boolMap.put(Arrays.hashCode(new int[]{1,0,0,0}), NOR);
		boolMap.put(Arrays.hashCode(new int[]{1,0,0,1}), EQUIVALENCE);
		boolMap.put(Arrays.hashCode(new int[]{1,1,0,0}), NEGATION_X1);
		boolMap.put(Arrays.hashCode(new int[]{1,0,1,0}), NEGATION_X2);
		boolMap.put(Arrays.hashCode(new int[]{1,0,1,1}), REPLICATION);
		boolMap.put(Arrays.hashCode(new int[]{1,1,0,1}), IMPLICATION);
		boolMap.put(Arrays.hashCode(new int[]{1,1,1,0}), NAND);
		boolMap.put(Arrays.hashCode(new int[]{1,1,1,1}), TAUTOLOGY);
	}
	
	
	/**
	 * @param values for parameters <x1, x2> in order {<0,0>,<0,1>,<1,0>,<1,1>}
	 * @return
	 */
	public int getBoolFuncType(int[] values) {
		if( values == null ) throw new NullPointerException("Array is null");
		if( values.length != VALUE_RANGE ) throw new IllegalArgumentException("array parameter hast not size " + VALUE_RANGE);
		
		return this.boolMap.get(Arrays.hashCode(values));
	}
	
	public String boolFuncTypeToString(int id) {
		switch(id) {
		case CONTRADICTION: return "contradiction";
		case CONJUNCTION: return "conjunction";
		case INHIBITION_X1: return "inhibition of x1";
		case INHIBITION_X2: return "inhibition of x2";
		case IDENTITY_X1: return "identitiy of x1";
		case IDENTITY_X2: return "identity of x2";
		case XOR: return "xor";
		case OR: return "or";
		case NOR: return "nor";
		case EQUIVALENCE: return "equivalance";
		case NEGATION_X1: return "negation of x1";
		case NEGATION_X2: return "negation of x2";
		case REPLICATION: return "replication";
		case IMPLICATION: return "implication";
		case NAND: return "nand";
		case TAUTOLOGY: return "tautology";
		
		default: return "unknown (type="+id+")";
		
	}
	}
}
