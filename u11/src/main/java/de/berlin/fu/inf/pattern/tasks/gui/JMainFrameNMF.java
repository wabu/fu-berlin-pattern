/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JMainFrameNMF.java
 *
 * Created on 17.01.2010, 19:53:23
 */

package de.berlin.fu.inf.pattern.tasks.gui;

import java.util.Collection;

/**
 *
 * @author alex
 */
public class JMainFrameNMF extends javax.swing.JFrame {

    private GUIController controller;

    // further GUI-Elements
    private JRasterVisualizer jSelectedDigit;
    private JRasterVisualizer jReconstructedDigit;

    /** Creates new form JMainFrameNMF */
    public JMainFrameNMF() {
        initComponents();
        postInitComponents();
    }
    
    protected void postInitComponents() {
        jSelectedDigit = new JRasterVisualizer(10);
        jReconstructedDigit = new JRasterVisualizer(10);

        this.jMainPanel.add(jSelectedDigit);
        this.jMainPanel.add(jReconstructedDigit);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jDigitList = new javax.swing.JList();
        jMainPanel = new javax.swing.JPanel();
        jBasicRasters = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jDigitList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "HUHU", ":P" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jDigitList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jDigitList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(jDigitList);

        jBasicRasters.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBasicRasters, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addComponent(jMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBasicRasters, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jBasicRasters;
    private javax.swing.JList jDigitList;
    private javax.swing.JPanel jMainPanel;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void setGUIController(GUIController ctrl) {
        if( controller != null ) {
            jDigitList.removeListSelectionListener(this.controller);
        }


        this.controller = ctrl;

        this.jDigitList.setModel(controller.getDigitListModel());

        this.jDigitList.addListSelectionListener(controller);
        
        this.jSelectedDigit.setModel(this.controller.getSlectedDigitModel());
        this.jReconstructedDigit.setModel(this.controller.getRecontructedDigitModel());

        Collection<? extends RasterModel> basicModel = this.controller.getBasicVectorDigitModels();
        for( RasterModel model : basicModel ) {
            JRasterVisualizer vis = new JRasterVisualizer(3);
            vis.setModel(model);
            this.jBasicRasters.add(vis);
        }
        this.validate();


    }
}
