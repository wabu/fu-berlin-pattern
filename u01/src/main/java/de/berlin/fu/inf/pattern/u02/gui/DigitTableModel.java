/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.gui;

import de.berlin.fu.inf.pattern.u02.data.Digit;
import de.berlin.fu.inf.pattern.u02.data.DigitPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alex
 */
public class DigitTableModel extends AbstractTableModel {

    private List<Digit> digitList = null;

    public int getColumnCount() {
        // show each point and class the digit belongs to
        return Digit.POINT_NUMBER + 1;
    }

    public int getRowCount() {
        return digitList != null ? digitList.size() : 0;
    }


    /**
     *
     */
    public Object getValueAt(int row, int col) {
        if( row >= getRowCount()) return null;

        Digit digit = (Digit) digitList.get(row);

        if( col <= Digit.POINT_NUMBER)
            return digit.getPoint(col);
        else
            return new Integer(digit.getGroup());
    }


    public void setData(Collection<Digit> elements) {
        this.digitList = new ArrayList<Digit>(elements);
    }



}
