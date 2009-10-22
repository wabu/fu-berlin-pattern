/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.gui;

import de.berlin.fu.inf.pattern.u02.data.Digit;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Draws an Digit
 *
 * @author alex
 */
class DigitDrawingPanel extends JPanel {

    private Digit digit;

    @Override
    public void paint(Graphics arg0) {

        

        super.paint(arg0);
    }


    public void setDigit(Digit digit) {
        this.digit = digit;
        this.validate();
    }



}
