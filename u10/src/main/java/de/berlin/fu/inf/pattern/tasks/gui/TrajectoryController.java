/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import com.google.common.collect.Collections2;
import de.berlin.fu.inf.pattern.util.matrix.List2VectorTransform;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class TrajectoryController {


    private final List<Collection<Vectorable>> trajectories;
    private final List<Collection<Float64Vector>> trajectories2;
    private final Map<Integer, String> trajecotryDescriptors;


    public TrajectoryController(List<Collection<Vectorable>> trajectories) {
        this.trajectories = trajectories;
        trajectories2 = new ArrayList<Collection<Float64Vector>>();
        List2VectorTransform trans = new List2VectorTransform();

        trajecotryDescriptors = new HashMap<Integer, String>();
        int i = 0;
        for( Collection<Vectorable> trajectory : trajectories ) {
            String name = "T" + i + " (" + trajectory.size() + ")";
            trajecotryDescriptors.put(i,name);
            trajectories2.add(Collections2.transform(trajectory, trans));
            i++;
        }

    }


    public Collection<Vectorable> getTrajecotryAsVectorable(int trajectoryNumber) {
        if( trajectoryNumber < 0 || trajectoryNumber >= getNumberOfTrajectories())
            throw new IllegalArgumentException("trajectory index out of range");
        return trajectories.get(trajectoryNumber);
    }

    @Nullable
    public Collection<Float64Vector> getTrajectoryAsVector(int trajectoryNumber) {
        if( trajectoryNumber < 0 || trajectoryNumber >= getNumberOfTrajectories())
            throw new IllegalArgumentException("trajectory index out of range");
        if( trajectories2 == null ) return null;
        return trajectories2.get(trajectoryNumber);
    }

    public int getNumberOfTrajectories() {
        return trajectories.size();
    }

    public Collection<String> getTrajectoryDescriptors() {
        return trajecotryDescriptors.values();
    }

}
