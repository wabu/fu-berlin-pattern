/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.tasks.digits.RasterDigit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractListModel;

/**
 *
 * @author alex
 */
public class RasterDigitListModel extends AbstractListModel {
    private List<Entry<RasterDigit, Integer>> digits;

    public RasterDigitListModel(Map<RasterDigit, Integer> digits) {
        this.digits = new ArrayList<Entry<RasterDigit, Integer>>(digits.entrySet());
    }

    public Object getElementAt(int i) {
        return i + ") " + " Digit of " + digits.get(i).getValue();
    }

    public int getSize() {
        if( digits != null )
            return digits.size();
        else return 0;
    }

}
