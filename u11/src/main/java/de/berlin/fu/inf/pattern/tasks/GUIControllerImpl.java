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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author alex
 */
public class GUIControllerImpl extends AbstractListModel implements GUIController {
    private final Logger logger = Logger.getLogger(GUIControllerImpl.class);

    // our real data
    //private final Map<RasterDigit, Integer> digits;
    private List<Entry<RasterDigit, Integer>> digits;
    // models for GUI-Components
    private final RasterDigitModel selectedDigitModel, reconstructedDigitModel;
    private final ArrayList<RasterModel> basicVectorModels;
    
    public GUIControllerImpl(Map<RasterDigit, Integer> digits) {
        this.digits = new ArrayList<Entry<RasterDigit, Integer>>(digits.entrySet());
        
        selectedDigitModel      = new RasterDigitModel();
        reconstructedDigitModel = new RasterDigitModel();
        basicVectorModels       = new ArrayList<RasterModel>();

    }


    public Collection<RasterModel> getBasicVectorDigitModels() {
        return basicVectorModels;
    }

    public ListModel getDigitListModel() {
        return this;
    }

    public RasterModel getRecontructedDigitModel() {
        return reconstructedDigitModel;
    }

    public RasterModel getSlectedDigitModel() {
        return selectedDigitModel;
    }

    @SuppressWarnings("unchecked")
    public void valueChanged(ListSelectionEvent lse) {
        logger.debug("valueChanged - " + "adjusting="+lse.getValueIsAdjusting() + " first=" + lse.getFirstIndex() + " last=" + lse.getLastIndex());

        if( lse.getSource() instanceof JList ) {
            JList list = (JList) lse.getSource();
            int index = list.getSelectedIndex();
            this.selectedDigitModel.setData(digits.get(index).getKey());
        }

    }

    public Object getElementAt(int i) {
        return i + " - " + digits.get(i).getValue();
    }

    public int getSize() {
        if( digits != null )
            return digits.size();
        else return 0;
    }



}
