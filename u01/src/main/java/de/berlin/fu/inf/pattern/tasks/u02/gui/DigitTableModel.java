/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.u02.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;

/**
 *
 * @author alex
 */
public class DigitTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = Logger.getLogger(DigitTableModel.class);

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
        logger.trace("TableModel - getValueAt "+row+"/"+col);
    	if( row >= getRowCount()) return null;

        Digit digit = (Digit) digitList.get(row);
        
        if( col < Digit.POINT_NUMBER)
            return digit.getPoint(col);
        else
            return Integer.valueOf(digit.getGroup());
    }


    public void setData(Collection<Digit> elements) {
        this.digitList = new ArrayList<Digit>(elements);
        notifyTableModelListners(new TableModelEvent(this));

    }
    
    private void notifyTableModelListners(TableModelEvent event) {
    	for(TableModelListener listener : getTableModelListeners() ) {
    		listener.tableChanged(event);
    	}
    			
    }
    
    public Digit getDigit(int index) {
    	if( index < this.digitList.size() && index >= 0)
    		return this.digitList.get(index); 
    	else
    		throw new IndexOutOfBoundsException("getDigit()");
    }



}
