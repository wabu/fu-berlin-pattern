/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import com.sun.java.swing.SwingUtilities3;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 *
 * @author alex
 */
public class JSimplePlot extends JPanel {
    private final Logger logger = Logger.getLogger(JSimplePlot.class);
    private final Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW};
    private Collection<Vectorable> data;
    private Collection<Vectorable> vectors;
    private Vectorable center;

    private int xFrom;
    private int xTo;
    private int yFrom;
    private int yTo;

    public JSimplePlot() {
        this.setPlotRange(0, 100, 0, 100);
        this.setBackground(Color.WHITE);
    }



    @Override
    public void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);

        if ( data == null ) return;
        // scale factor in dependency to panels width and height
        double scale = Math.min(this.getWidth()/getXRange() , this.getHeight()/getYRange());
        if( scale < 1 ) scale = 1;
        logger.trace("width=" + this.getWidth() + " height=" + this.getHeight());
        logger.trace("print() with scale " + scale);



        int[] xPoints = new int[data.size()];
        int[] yPoints = new int[data.size()];
        int i=0;
        for(Vectorable vec : data ) {
            xPoints[i] = (int) ((vec.getVectorData()[0]-xFrom) * scale);
            yPoints[i] = (int) (getHeight()-(vec.getVectorData()[1]-yFrom) * scale);
            i++;
        }
        grphcs.setColor(Color.GRAY);
        grphcs.drawPolyline(xPoints, yPoints, data.size());
        // print

        i = 1;
        if( vectors != null && center != null ) {
            for(Vectorable vec : vectors) {
                int xStart = (int) ((center.getVectorData()[0]-xFrom) * scale);
                int yStart = (int) (getHeight()-(center.getVectorData()[1]-yFrom) * scale);
                int xEnd = (int) ((vec.getVectorData()[0]*10/i*scale) + xStart);
                int yEnd = (int) ((vec.getVectorData()[1]*10/i*scale) + yStart);
                grphcs.setColor(colors[i-1]);
                i++;
                logger.debug("vec: " + xStart + ", " + yStart);

                grphcs.drawLine(xStart, yStart, xEnd, yEnd);
            }
        }


    }

    private double getXRange() {
        return xTo - xFrom;
    }

    private double getYRange() {
        return yTo - yFrom;
    }

    public void setData(Collection<Vectorable> data ) {
        if( this.data == data ) return; // if there is really the same object
        this.data = data;
        if( data != null && data.size() > 0 )
            this.center = data.iterator().next();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                repaint();
            }
        });
    }

    public void setVectors(Collection<Vectorable> vectors) {
        this.vectors = vectors;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                repaint();
            }
        });
    }
    
    public void setPlotRange(int xFrom, int xTo, int yFrom, int yTo) {
        this.xFrom = xFrom;
        this.xTo   = xTo;
        this.yFrom = yFrom;
        this.yTo   = yTo;

    }
}
