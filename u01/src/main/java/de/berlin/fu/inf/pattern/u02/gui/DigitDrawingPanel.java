/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.gui;

import de.berlin.fu.inf.pattern.u02.data.Digit;
import de.berlin.fu.inf.pattern.u02.data.DigitPoint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * Draws an Digit
 *
 * @author alex
 */
class DigitDrawingPanel extends JPanel {
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
    	
    	Graphics2D g2d;
    	
    	for( DigitPoint currenPoint : points) {
    		if( lastPoint != null ) {
    			g.drawLine(lastPoint.x, DigitPoint.MAX_Y - lastPoint.y, 
    					currenPoint.x, DigitPoint.MAX_Y - currenPoint.y);
    		}
    		lastPoint = currenPoint;
    	}   	
    }

    public void setDigit(Digit digit) {
        this.digit = digit;
        
        this.repaint();
    }



}
