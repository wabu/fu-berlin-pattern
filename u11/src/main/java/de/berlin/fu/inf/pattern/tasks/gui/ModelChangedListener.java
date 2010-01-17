/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import java.util.EventListener;

/**
 *
 * @author alex
 */
public interface ModelChangedListener extends EventListener {
    public void onModelChanged(ModelChangedEvent evt);
}
