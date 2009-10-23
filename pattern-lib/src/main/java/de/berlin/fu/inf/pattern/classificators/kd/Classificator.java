package de.berlin.fu.inf.pattern.classificators.kd;

import java.util.Collection;


/**
 * 
 * @author alex
 *
 * @param <E> is the entry type. For classification it needs to be classifiable ;)
 * @param <C>
 */
public interface Classificator<E extends Classifiable> {
	
	public boolean training(Collection<E> c);
	
	public Classification classify(E value);

}
