/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import java.util.Collection;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author alex
 */
public interface GUIController extends ListSelectionListener {

    public ListModel getDigitListModel();
    public RasterModel getSlectedDigitModel();
    public RasterModel getRecontructedDigitModel();
    public Collection<? extends RasterModel> getBasicVectorDigitModels();

}
