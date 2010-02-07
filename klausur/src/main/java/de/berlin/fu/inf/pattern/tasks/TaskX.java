/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.tasks.impls.WabusNotSoooSimplePredictor;
import de.berlin.fu.inf.pattern.tasks.impls.WabusSimplePredictor;
import java.util.List;
import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class TaskX implements Runnable {
    private final Logger logger = Logger.getLogger(TaskX.class);

    public void run() {
        Reader rd = new Reader();
        try {
            rd.addDataFromResource("drive.csv");
        } catch (Exception ex) {
            logger.error("error while reading data", ex);
        }

        List<Vector<Float64>> data = rd.getData();
        List<Vector<Float64>> train = data.subList(0, data.size() / 2);
        List<Vector<Float64>> test = data.subList(data.size()/2, data.size());

        AbstractPredictor pred = new WabusNotSoooSimplePredictor(3);
        pred.train(train);

        double xsum = 0;
        double ysum = 0;
        for(List<Vector<Float64>> states :
            new QueuedIterable<Vector<Float64>>(pred.getHistorySize()+1, test)) {

            Vector<Float64> target = states.get(states.size() - 1);
            states.remove(states.size()-1);

            Vector<Float64> prediction = pred.predict(states);

            //Vector<Float64> diff = prediction.minus(target);
            //sum += diff.times(diff).doubleValue();

            double xdiff = prediction.get(0).doubleValue() -
                    target.get(0).doubleValue();
            xsum += Math.abs(xdiff);
            double ydiff = prediction.get(1).doubleValue() -
                    target.get(1).doubleValue();
            ysum += Math.abs(ydiff);
        }
        double xerr = xsum/(train.size()-(pred.getHistorySize()+1));
        double yerr = ysum/(train.size()-(pred.getHistorySize()+1));

        logger.info("xerr is "+xerr);
        logger.info("yerr is "+yerr);
    }
}
