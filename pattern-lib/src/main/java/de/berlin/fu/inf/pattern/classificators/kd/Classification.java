package de.berlin.fu.inf.pattern.classificators.kd;

/**
 * An interface to determine classification of specific objects
 * 
 * @author alex
 */
public interface Classification {
	
	/**
	 * check if to classifications are identical
	 * 
	 * @param c, another classification object
	 * @return true, if c and this classification are identical
	 */
	public abstract boolean equals(Classification c);

}
