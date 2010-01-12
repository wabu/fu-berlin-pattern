/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.awt.Graphics;
import java.util.Collection;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author alex
 */
public class JSimplePlot extends JPanel {
    private final Logger logger = Logger.getLogger(JSimplePlot.class);
    private Collection<Vectorable> data;
    private Collection<Vectorable> vectors;

    private int xFrom;
    private int xTo;
    private int yFrom;
    private int yTo;

    public JSimplePlot() {
        this.setPlotRange(0, 100, 0, 100);
    }



    @Override
    public void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);

        if ( data == null ) return;
        // scale factor in dependency to panels width and height
        double scale = Math.min(this.getWidth()/getXRange() , this.getHeight()/getYRange());
        if( scale < 1 ) scale = 1;
        logger.debug("width=" + this.getWidth() + " height=" + this.getHeight());
        logger.debug("print() with scale " + scale);



        int[] xPoints = new int[data.size()];
        int[] yPoints = new int[data.size()];
        int i=0;
        for(Vectorable vec : data ) {
            xPoints[i] = (int) ((vec.getVectorData()[0]-xFrom) * scale);
            yPoints[i] = (int) (getHeight()-((int)(vec.getVectorData()[1]-yFrom) * scale));
            logger.debug("draw: " + xPoints[i] + " " + yPoints[i]);
            i++;
        }
        grphcs.drawPolyline(xPoints, yPoints, data.size());
        // print
    }

    private double getXRange() {
        return xTo - xFrom;
    }

    private double getYRange() {
        return yTo - yFrom;
    }

    public void setData(Collection<Vectorable> data ) {
        this.data = data;
        this.repaint();
    }

    public void setVectors(Collection<Vectorable> vectors) {
        this.vectors = vectors;
    }

    public void setPlotRange(int xFrom, int xTo, int yFrom, int yTo) {
        this.xFrom = xFrom;
        this.xTo   = xTo;
        this.yFrom = yFrom;
        this.yTo   = yTo;

    }
}
