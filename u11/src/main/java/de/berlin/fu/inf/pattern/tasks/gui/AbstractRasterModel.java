/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import de.berlin.fu.inf.pattern.tasks.gui.ModelChangedEvent;
import de.berlin.fu.inf.pattern.tasks.gui.ModelChangedListener;
import de.berlin.fu.inf.pattern.tasks.gui.RasterModel;
import java.util.ArrayList;
import javax.swing.event.EventListenerList;

/**
 *
 * @author alex
 */
public abstract class AbstractRasterModel implements RasterModel {

    private final EventListenerList listeners;
    protected int cols;
    protected int rows;

    public AbstractRasterModel() {
        listeners = new EventListenerList();
        this.cols = 0;
        this.rows = 0;
    }


    protected void notifyModelChangedListeners(ModelChangedEvent evt) {
        for( ModelChangedListener listener : listeners.getListeners(ModelChangedListener.class)) {
            listener.onModelChanged(evt);
        }
    }


    public void addModelChangedListener(ModelChangedListener listener) {
        listeners.add(ModelChangedListener.class, listener);
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public void removeModelChangedListener(ModelChangedListener listener) {
        listeners.remove(ModelChangedListener.class, listener);
    }
}
