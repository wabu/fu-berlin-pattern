/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.u02.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitPoint;

/**
 * Draws an Digit
 *
 * @author alex
 */
class DigitDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = Logger.getLogger(DigitDrawingPanel.class);
    private Digit digit;

    public DigitDrawingPanel() {
    	Dimension d = new Dimension(100,100);
    	
    	   	
    	this.setPreferredSize(d);
    	this.setMinimumSize(d);
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	logger.trace("paint " + this.getClass().getName() + " with " + digit);
    	
    	
    	if( digit == null ) return;
    	    	
    	
    	List<DigitPoint> points = digit.getPoints();
    	DigitPoint lastPoint = null;
    	
    	for( DigitPoint currenPoint : points) {
    		if( lastPoint != null ) {
    			g.drawLine(lastPoint.getX(), DigitPoint.MAX_Y - lastPoint.getY(), 
    					currenPoint.getX(), DigitPoint.MAX_Y - currenPoint.getY());
    		}
    		lastPoint = currenPoint;
    	}   	
    }

    public void setDigit(Digit digit) {
        this.digit = digit;
        
        this.repaint();
    }



}
