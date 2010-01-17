/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.tasks.digits.RasterDigit;
import de.berlin.fu.inf.pattern.tasks.digits.RasterDigitModel;
import de.berlin.fu.inf.pattern.tasks.gui.GUIController;
import de.berlin.fu.inf.pattern.tasks.gui.RasterModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author alex
 */
public class GUIControllerImpl implements GUIController {
    private final Logger logger = Logger.getLogger(GUIControllerImpl.class);

    // our real data
    private final Map<RasterDigit, Integer> digits;
    // models for GUI-Components
    private final RasterDigitModel selectedDigitModel, reconstructedDigitModel;
    private final ArrayList<RasterModel> basicVectorModels;
    private final RasterDigitListModel listModel;
    
    public GUIControllerImpl(Map<RasterDigit, Integer> digits) {
        this.digits = digits;
        selectedDigitModel      = new RasterDigitModel();
        reconstructedDigitModel = new RasterDigitModel();
        basicVectorModels       = new ArrayList<RasterModel>();
        listModel               = new RasterDigitListModel(digits);
    }


    public Collection<RasterModel> getBasicVectorDigitModels() {
        return basicVectorModels;
    }

    public ListModel getDigitListModel() {
        return listModel;
    }

    public RasterModel getRecontructedDigitModel() {
        return reconstructedDigitModel;
    }

    public RasterModel getSlectedDigitModel() {
        return selectedDigitModel;
    }

    public void valueChanged(ListSelectionEvent lse) {
        logger.debug("valueChanged - " + lse);
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
