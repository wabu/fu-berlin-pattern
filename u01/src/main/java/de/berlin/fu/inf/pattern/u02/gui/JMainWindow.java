/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author alex
 */
public class JMainWindow extends JFrame {

    private DigitOutputPanel digitTablePanel;
    private DigitDrawingPanel digitDrawPanel;

    public JMainWindow() {
        init();
    }

    protected void init() {
        this.setSize(800, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        
        this.digitTablePanel = new DigitOutputPanel();
        this.digitDrawPanel = new DigitDrawingPanel();


        content.add(this.digitTablePanel, BorderLayout.WEST);
        content.add(this.digitDrawPanel, BorderLayout.CENTER);

        
        this.pack();

    }

}
