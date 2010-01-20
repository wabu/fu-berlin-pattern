/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.iface.MatrixFactorization;
import de.berlin.fu.inf.pattern.impl.nmf.EMNegativeMatrixFactorization;
import de.berlin.fu.inf.pattern.tasks.digits.RasterDigit;
import de.berlin.fu.inf.pattern.tasks.digits.VectorAsRasterModel;
import de.berlin.fu.inf.pattern.tasks.gui.GUIController;
import de.berlin.fu.inf.pattern.tasks.gui.RasterModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class GUIControllerImpl
        extends AbstractListModel
        implements GUIController
{
    private final Logger logger = Logger.getLogger(GUIControllerImpl.class);

    // our real data
    private List<Entry<RasterDigit, Integer>> digits;
    // models for GUI-Components
    private final VectorAsRasterModel selectedDigitModel, reconstructedDigitModel;
    private final List<VectorAsRasterModel> basicVectorModels;

    private final int featureNumber;
    private MatrixFactorization matrixFac;
    
    public GUIControllerImpl(Map<RasterDigit, Integer> digits) {
        this.digits = new ArrayList<Entry<RasterDigit, Integer>>(digits.entrySet());
        
        featureNumber = 10;

        selectedDigitModel      = new VectorAsRasterModel();
        reconstructedDigitModel = new VectorAsRasterModel();
        List<VectorAsRasterModel> basicsInit = new ArrayList<VectorAsRasterModel>();
        for( int i=0; i<featureNumber; i++) { 
            basicsInit.add(new VectorAsRasterModel());
        }
        // make basicVector model unmodifiable
        basicVectorModels = Collections.unmodifiableList(basicsInit);

        this.setMatrixFac(new EMNegativeMatrixFactorization(featureNumber));
    }

    public void setMatrixFac(MatrixFactorization mf) {
        this.matrixFac = mf;

        new Thread( new Runnable() {

            @Override
            public void run() {
                matrixFac.learn(
                Lists.transform(digits, new Function<Entry<RasterDigit,Integer>, Float64Vector>() {
                    @Override
                    public Float64Vector apply(Entry<RasterDigit,Integer> from) {
                        return from.getKey().getVec();
                    };
                }));

                // update basicVectors
                int i = 0;
                List<Float64Vector> basics = matrixFac.getFeatures();
                for( VectorAsRasterModel vecRasterModel : basicVectorModels) {
                    if( i >= basics.size() ) {
                        logger.warn("feature size is smaller than expected");
                    } else {
                        vecRasterModel.setData(
                                basics.get(i),
                                digits.get(0).getKey().getWidth(),
                                digits.get(0).getKey().getHeight());
                    }

                    i++;
                }
            }
        }).run();
        
    }

    @Override
    public Collection<? extends RasterModel> getBasicVectorDigitModels() {
        return basicVectorModels;
    }

    @Override
    public ListModel getDigitListModel() {
        return this;
    }

    @Override
    public RasterModel getRecontructedDigitModel() {
        return reconstructedDigitModel;
    }

    @Override
    public RasterModel getSlectedDigitModel() {
        return selectedDigitModel;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        logger.debug("valueChanged - " + "adjusting="+lse.getValueIsAdjusting() + " first=" + lse.getFirstIndex() + " last=" + lse.getLastIndex());

        if( lse.getSource() instanceof JList ) {
            JList list = (JList) lse.getSource();
            int index = list.getSelectedIndex();
            RasterDigit rDigit = digits.get(index).getKey();
            this.selectedDigitModel.setData(rDigit);
            
            if( isMatrixFacSet() ) {
                Float64Vector recVec = matrixFac.decode(
                                            matrixFac.encode(rDigit.getVec()));
                this.reconstructedDigitModel.setData(
                        recVec, rDigit.getWidth(), rDigit.getHeight());
            }
        }
    }

    @Override
    public Object getElementAt(int i) {
        return i + " - " + digits.get(i).getValue();
    }

    @Override
    public int getSize() {
        if( digits != null )
            return digits.size();
        else return 0;
    }

    public boolean isMatrixFacSet() {
        return matrixFac == null;
    }



}
