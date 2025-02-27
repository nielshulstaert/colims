/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.colims.client.view.fasta;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author demet
 */
public class HeaderParseRuleAdditionDialog extends javax.swing.JDialog {

    /**
     * Creates new form HeaderParseRuleAdditionDialog
     * @param parent
     * @param modal
     */
    public HeaderParseRuleAdditionDialog(JDialog parent, boolean modal) {
        super(parent, modal);

        this.getContentPane().setBackground(Color.WHITE);
        initComponents();
    }

    public JTextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public JPanel getHeaderParseRulePanel() {
        return headerParseRulePanel;
    }

    public JTextField getParseRuleTextField() {
        return parseRuleTextField;
    }

    public JButton getSaveParseRuleButton() {
        return saveParseRuleButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerParseRulePanel = new javax.swing.JPanel();
        parseRuleLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        parseRuleTextField = new javax.swing.JTextField();
        descriptionTextField = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        saveParseRuleButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Header parse rule management");
        setBackground(new java.awt.Color(255, 255, 255));

        headerParseRulePanel.setBackground(new java.awt.Color(255, 255, 255));
        headerParseRulePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        parseRuleLabel.setText("Header Parse Rule :");

        descriptionLabel.setText("Description :");

        parseRuleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parseRuleTextFieldActionPerformed(evt);
            }
        });

        descriptionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerParseRulePanelLayout = new javax.swing.GroupLayout(headerParseRulePanel);
        headerParseRulePanel.setLayout(headerParseRulePanelLayout);
        headerParseRulePanelLayout.setHorizontalGroup(
            headerParseRulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerParseRulePanelLayout.createSequentialGroup()
                .addGroup(headerParseRulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(parseRuleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(headerParseRulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(parseRuleTextField))
                .addContainerGap())
        );
        headerParseRulePanelLayout.setVerticalGroup(
            headerParseRulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerParseRulePanelLayout.createSequentialGroup()
                .addGroup(headerParseRulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parseRuleLabel)
                    .addComponent(parseRuleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(headerParseRulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionLabel)
                    .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        closeButton.setText("close");
        closeButton.setMaximumSize(new java.awt.Dimension(80, 25));
        closeButton.setMinimumSize(new java.awt.Dimension(80, 25));
        closeButton.setPreferredSize(new java.awt.Dimension(80, 25));

        saveParseRuleButton.setText("save");
        saveParseRuleButton.setMaximumSize(new java.awt.Dimension(80, 25));
        saveParseRuleButton.setMinimumSize(new java.awt.Dimension(80, 25));
        saveParseRuleButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerParseRulePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveParseRuleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerParseRulePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveParseRuleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void parseRuleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parseRuleTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parseRuleTextFieldActionPerformed

    private void descriptionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descriptionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descriptionTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JPanel headerParseRulePanel;
    private javax.swing.JLabel parseRuleLabel;
    private javax.swing.JTextField parseRuleTextField;
    private javax.swing.JButton saveParseRuleButton;
    // End of variables declaration//GEN-END:variables
}
