/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.SupervisedClassifier;
import de.berlin.fu.inf.pattern.impl.trees.ID3Tree;
import de.berlin.fu.inf.pattern.tasks.CarData.Acceptability;
import de.berlin.fu.inf.pattern.util.test.ClassifierTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author wabu
 */
public class Task26 implements Runnable {
    private final Logger logger = Logger.getLogger(Task26.class);
    private final String resource = "car.data";

    @Override
    public void run() {
        CarDataReader data = new CarDataReader();
        logger.info("reading data from "+resource);
        try {
            data.addDataFromResource(resource);
        } catch (Exception ex) {
            logger.error("reading "+resource, ex);
            return;
        }
        logger.info("read "+data.getData().size()+" entries");

        Random rnd = new Random();
        List<CarData> train = new ArrayList<CarData>(data.getData().size()/2);
        List<CarData> test = new ArrayList<CarData>(data.getData().size()/2);
        for(CarData d : data.getData()) {
            if(rnd.nextBoolean()) {
                train.add(d);
            } else {
                test.add(d);
            }
        }

        SupervisedClassifier<CarData, CarData.Acceptability> tree = 
                new ID3Tree<CarData, CarData.Acceptability>();
        logger.info("createing tree");
        tree.train(Collections2.transform(train, new EntryTransfomation()));
        logger.info("tree created. f00 h4X0r");
        ClassifierTest<CarData, Acceptability> ct =
                new ClassifierTest<CarData, CarData.Acceptability>(tree);
        double rate = ct.runTest(Iterables.transform(train, new EntryTransfomation()));
        logger.info("rate on training set is "+rate);
        rate = ct.runTest(Iterables.transform(test, new EntryTransfomation()));
        logger.info("rate on testing set is  "+rate);
    }

    private static class EntryTransfomation implements Function<CarData, Entry<CarData, Acceptability>> {
        @Override
        public Entry<CarData, Acceptability> apply(CarData from) {
            return new Entry<CarData, Acceptability>(from, from.getKlass());
        }
    }
}
