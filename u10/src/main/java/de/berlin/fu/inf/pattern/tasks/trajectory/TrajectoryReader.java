/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.trajectory;

import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 *
 * @author covin
 */
public class TrajectoryReader {
    private static final String TRAJECTORY_TOKEN = "#";

    private final Logger logger = Logger.getLogger(TrajectoryReader.class);


    public Vectorable readTrajectoryPosition(String line) {
        logger.trace("read trajectory datum: " + line);

        StringTokenizer tokenizer = new StringTokenizer(line);
        if(tokenizer.countTokens() != 2) {
            throw new IllegalStateException("can not parse '" + line + "' to Vectorable");
        }

        DoubleVector doubleVector = new DoubleVector(
                    Double.parseDouble(tokenizer.nextToken()),
                    Double.parseDouble(tokenizer.nextToken()));
        return doubleVector;
    }
    
    public List<Collection<Vectorable>> readTrajectoryDataFromStream(InputStream istream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(istream));

        String line = null;
        List<Collection<Vectorable>> allTrajectories = new ArrayList<Collection<Vectorable>>();
        List<Vectorable> trajectory = new ArrayList<Vectorable>();
        allTrajectories.add(trajectory);

        int i = 0;
        while( (line = reader.readLine()) != null) {
            logger.trace("Read line: " + i++);

            if( line.startsWith(TRAJECTORY_TOKEN)) {
                logger.trace("new trajectory, token was found");
                trajectory = new ArrayList<Vectorable>();
                allTrajectories.add(trajectory);
            } else {
                trajectory.add(this.readTrajectoryPosition(line));
            }
        }

        return allTrajectories;
    }


}
