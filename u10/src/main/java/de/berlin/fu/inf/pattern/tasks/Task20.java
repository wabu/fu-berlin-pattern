/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.collect.Collections2;
import de.berlin.fu.inf.pattern.impl.pca.OjaAnalysis;
import de.berlin.fu.inf.pattern.tasks.trajectory.TrajectoryReader;
import de.berlin.fu.inf.pattern.util.matrix.List2VectorTransform;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class Task20 implements Runnable {
    private final Logger logger = Logger.getLogger(Task20.class);

    public void run() {
        logger.info("20) Running PCA for beewaggle.runs");

    	String beeFile = "beewaggle.runs";

    	TrajectoryReader trajectoryReader = new TrajectoryReader();

    	logger.info("read " + beeFile);
    	// read pendigits from training and testing file
    	Collection<Collection<Vectorable>> trajectories;

    	try {
		trajectories = trajectoryReader.readTrajectoryDataFromStream(
                        ClassLoader.getSystemResourceAsStream(beeFile));
    	} catch( IOException ioEx ) {
    		logger.fatal("reading data failed", ioEx);
    		return;
    	}
        assert trajectories == null;
        assert trajectories.size() == 0;

        for( Collection<Vectorable> trajectory : trajectories) {
            Collection<Float64Vector> vecs = Collections2.transform(trajectory, new List2VectorTransform());
            OjaAnalysis oja = new OjaAnalysis(vecs);
            oja.nextPrincipleComponent();
        }



        logger.info("found " + trajectories.size() + " trajectories");

    }
}
