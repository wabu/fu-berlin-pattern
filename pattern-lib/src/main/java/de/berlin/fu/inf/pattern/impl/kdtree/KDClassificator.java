package de.berlin.fu.inf.pattern.impl.kdtree;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.SupervisedClassifier;
import de.berlin.fu.inf.pattern.util.types.Dimensionable;

public class KDClassificator<D extends Dimensionable<D>, C> implements SupervisedClassifier<D, C> {

	private final KDTree<DimensionableEntry<D, C>> kdTree;
	
	public KDClassificator(){
		this.kdTree = new KDTreeImpl<DimensionableEntry<D, C>>();
	}
	
	@SuppressWarnings("unchecked") /* can't create generic array */
	public void train(Collection<Entry<D, C>> trainingData) {
		DimensionableEntry<D, C>[] kdEntries = (DimensionableEntry<D, C>[]) 
			Collections2.transform(trainingData, new Function<Entry<D,C>, DimensionableEntry<D, C>>() {
				public DimensionableEntry<D, C> apply(Entry<D, C> e) {
					return new DimensionableEntry<D, C>(e.getData(), e.getClassification());
				}
			}).toArray(new DimensionableEntry<?, ?>[trainingData.size()]);
		kdTree.buildTree(kdEntries);
	}

	public C classify(D data) {
		return kdTree.findNearestValues(new DimensionableEntry<D, C>(data, null)).getClassification();
	}

    @Override
    public String toString() {
        return "kNN-classifier";
    }

}
