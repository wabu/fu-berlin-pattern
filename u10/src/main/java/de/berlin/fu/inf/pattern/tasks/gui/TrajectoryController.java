/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alex
 */
public class TrajectoryController {


    private final List<Collection<Vectorable>> trajectories;
    private final Map<Integer, String> trajecotryDescriptors;

    public TrajectoryController(List<Collection<Vectorable>> trajectories) {
        this.trajectories = trajectories;

        trajecotryDescriptors = new HashMap<Integer, String>();
        int i = 0;
        for( Collection<Vectorable> trajectory : trajectories ) {
            String name = "T" + i + " (" + trajectory.size() + ")";
            trajecotryDescriptors.put(i,name);
            i++;
        }

    }


    public Collection<Vectorable> getTrajecotryData(int trajectoryNumber) {
        if( trajectoryNumber < 0 || trajectoryNumber >= getNumberOfTrajectories())
            throw new IllegalArgumentException("trajectory index out of range");
        return trajectories.get(trajectoryNumber);
    }

    public int getNumberOfTrajectories() {
        return trajectories.size();
    }

    public Collection<String> getTrajectoryDescriptors() {
        return trajecotryDescriptors.values();
    }

}
