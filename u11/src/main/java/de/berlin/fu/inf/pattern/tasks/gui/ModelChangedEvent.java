/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import java.util.EventObject;

/**
 *
 * @author alex
 */
public class ModelChangedEvent extends EventObject {

    public ModelChangedEvent(RasterModel src) {
        super(src);
        if( src == null ) throw new IllegalArgumentException("source must not be null");
    }

    @Override
    public RasterModel getSource() {
        return (RasterModel) source;
    }
}
