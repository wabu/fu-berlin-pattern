package de.berlin.fu.inf.pattern.classificators.kd;

import java.rmi.AccessException;
import java.util.Collection;

import javax.print.attribute.SupportedValuesAttribute;

import de.berlin.fu.inf.pattern.classificators.Classifer;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.data.kdtree.Dimensionable;
import de.berlin.fu.inf.pattern.data.kdtree.KDTree;
import de.berlin.fu.inf.pattern.data.kdtree.KDTreeImpl;

public class KDClassificator<D extends Dimensionable & Classifiable> implements Classificator<D> {

	private KDTree<D> kdTree = null;
	
	
	
	public KDClassificator(){
		this.kdTree = new KDTreeImpl<D>();
	}
	
	/**
	 * training method fills KDTree with data
	 * 
	 */

	public Classification classify(D data) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean training(Collection<D> c) {
		// TODO Auto-generated method stub
		return false;
	}

}
