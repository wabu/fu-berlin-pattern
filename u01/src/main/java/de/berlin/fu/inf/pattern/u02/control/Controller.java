/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.control;

import de.berlin.fu.inf.pattern.classificators.Classifer;
import de.berlin.fu.inf.pattern.classificators.knn.SimpleKNNClassifier;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.data.SimpleDatabase;
import de.berlin.fu.inf.pattern.u02.data.Digit;
import de.berlin.fu.inf.pattern.u02.data.DigitReader;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author alex
 */
public class Controller {
    private SimpleDatabase<Digit, Integer> simpleDatabase = new SimpleDatabase<Digit, Integer>();

    private DigitReader digitReader = new DigitReader();


    

    public List<Digit> learnFromFile(String fileName) {


        Collection<Digit> digitCollection = digitReader.readDigitsFromFile(fileName);

        simpleDatabase = new SimpleDatabase<Digit, Integer>();



        for(Digit digit : digitCollection) {
            simpleDatabase.add(new Entry<Digit, Integer>(digit, digit.getGroup()));
        }

        Classifer<Digit, Integer> classifier = new SimpleKNNClassifier<Digit, Integer>(1,simpleDatabase);

        return null;
    }



}
