/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.test;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.Classifier;

/**
 *
 * @author wabu
 */
public class ClassifierTest<V, C> {
    private final Classifier<V, C> classifier;

    /**
     * @param classifier the trained classifier
     */
    public ClassifierTest(Classifier<V, C> classifier) {
        this.classifier = classifier;
    }

    public double runTest(Iterable<Entry<V,C>> testData){
        int total = 0;
        int correct = 0;

        for (Entry<V, C> t : testData) {
            if(t.getClassification().equals(classifier.classify(t.getData()))) {
                correct++;
            }
            total++;
        }
        return correct/(double)total;
    }
}
