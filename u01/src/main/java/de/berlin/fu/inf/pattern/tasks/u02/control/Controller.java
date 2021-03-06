/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.u02.control;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.data.SimpleDatabase;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.impl.knn.SimpleKNNClassifier;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitReader;

/**
 *
 * @author alex
 */
public class Controller {
	final Logger log = Logger.getLogger(Controller.class);
	
    private SimpleDatabase<Digit, Integer> simpleDatabase = new SimpleDatabase<Digit, Integer>();

    private DigitReader digitReader = new DigitReader();
    

    public List<Digit> learnFromFile(String fileName) throws IOException {
        Collection<Digit> digitCollection = digitReader.readDigits(fileName);
        simpleDatabase = new SimpleDatabase<Digit, Integer>();
        for(Digit digit : digitCollection) {
            simpleDatabase.add(new Entry<Digit, Integer>(digit, digit.getGroup()));
        }
        return simpleDatabase.getDataView();
    }
    
    public Classifier<Digit, Integer> getClassifier(int k){
        return new SimpleKNNClassifier<Digit, Integer>(k,simpleDatabase);
    }
    
    public float testOnFile(String fileName, int k) throws IOException{
        Collection<Digit> digitCollection = digitReader.readDigits(fileName);
    	Classifier<Digit, Integer> classifier = getClassifier(k);
        int succ = 0;
        int num = 0;
        for(Digit digit : digitCollection) {
        	int expected = digit.getGroup();
        	int classified = classifier.classify(digit);
        	if(expected == classified) {
        		succ++;
        	}
        	num++;
    		if(log.isDebugEnabled() && num%100==0) {
    			log.debug("processed "+num +"/"+digitCollection.size()+", rate "+(float)succ/(float)num);
        	}
        }
        return (float)succ/(float)digitCollection.size();
    }
}
