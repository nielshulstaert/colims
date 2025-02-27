package com.compomics.colims.client.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 *
 * @author Niels Hulstaert
 */
public class MzIdentMlExportDialog extends javax.swing.JDialog {

    /**
     * The mzIdentMl export file chooser.
     */
    private final JFileChooser mzIdentMlExportChooser = new JFileChooser();
    /**
     * The spectra export file chooser.
     */
    private final JFileChooser mgfExportChooser = new JFileChooser();

    /**
     * Dialog constructor.
     *
     * @param parent the parent dialog
     * @param modal is the dialog modal
     */
    public MzIdentMlExportDialog(final JDialog parent, final boolean modal) {
        super(parent, modal);

        initComponents();
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JTextField getNumberTextField() {
        return mgfTextField;
    }

    public JButton getSaveOrUpdateButton() {
        return exportButton;
    }

    public JTextField getTitleTextField() {
        return mzIdentMlTextField;
    }

    public JButton getBrowseMgfButton() {
        return browseMgfButton;
    }

    public JButton getBrowseMzIdentMlButton() {
        return browseMzIdentMlButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JCheckBox getExportSpectraCheckBox() {
        return exportSpectraCheckBox;
    }

    public JTextField getMgfTextField() {
        return mgfTextField;
    }

    public JTextField getMzIdentMlTextField() {
        return mzIdentMlTextField;
    }

    public JFileChooser getMzIdentMlExportChooser() {
        return mzIdentMlExportChooser;
    }

    public JFileChooser getMgfExportChooser() {
        return mgfExportChooser;
    }

    public JComboBox<String> getUserComboBox() {
        return userComboBox;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mzIdentMlExportPanel = new javax.swing.JPanel();
        mzIdentMlTextField = new javax.swing.JTextField();
        mgfLabel = new javax.swing.JLabel();
        mgfTextField = new javax.swing.JTextField();
        exportButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        mzIdentMlLabel = new javax.swing.JLabel();
        exportSpectraCheckBox = new javax.swing.JCheckBox();
        browseMgfButton = new javax.swing.JButton();
        browseMzIdentMlButton = new javax.swing.JButton();
        userComboBox = new javax.swing.JComboBox<>();
        userLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MzIdentML export");

        mzIdentMlExportPanel.setBackground(new java.awt.Color(255, 255, 255));

        mzIdentMlTextField.setEditable(false);

        mgfLabel.setText("MGF file:");
        mgfLabel.setMaximumSize(new java.awt.Dimension(89, 15));
        mgfLabel.setMinimumSize(new java.awt.Dimension(89, 15));
        mgfLabel.setPreferredSize(new java.awt.Dimension(89, 15));

        mgfTextField.setEditable(false);

        exportButton.setText("export");
        exportButton.setToolTipText("");
        exportButton.setMaximumSize(new java.awt.Dimension(80, 25));
        exportButton.setMinimumSize(new java.awt.Dimension(80, 25));
        exportButton.setPreferredSize(new java.awt.Dimension(80, 25));

        closeButton.setText("close");
        closeButton.setToolTipText("");
        closeButton.setMaximumSize(new java.awt.Dimension(80, 25));
        closeButton.setMinimumSize(new java.awt.Dimension(80, 25));
        closeButton.setPreferredSize(new java.awt.Dimension(80, 25));

        mzIdentMlLabel.setText("MzIdentML file*:");

        exportSpectraCheckBox.setText("export spectra as MGF");

        browseMgfButton.setText("browse");
        browseMgfButton.setToolTipText("");
        browseMgfButton.setMaximumSize(new java.awt.Dimension(80, 25));
        browseMgfButton.setMinimumSize(new java.awt.Dimension(80, 25));
        browseMgfButton.setPreferredSize(new java.awt.Dimension(80, 25));

        browseMzIdentMlButton.setText("browse");
        browseMzIdentMlButton.setToolTipText("");
        browseMzIdentMlButton.setMaximumSize(new java.awt.Dimension(80, 25));
        browseMzIdentMlButton.setMinimumSize(new java.awt.Dimension(80, 25));
        browseMzIdentMlButton.setPreferredSize(new java.awt.Dimension(80, 25));

        userLabel.setText("User:");

        javax.swing.GroupLayout mzIdentMlExportPanelLayout = new javax.swing.GroupLayout(mzIdentMlExportPanel);
        mzIdentMlExportPanel.setLayout(mzIdentMlExportPanelLayout);
        mzIdentMlExportPanelLayout.setHorizontalGroup(
            mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mzIdentMlExportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mzIdentMlExportPanelLayout.createSequentialGroup()
                        .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mgfLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mgfTextField)
                            .addComponent(userComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(mzIdentMlExportPanelLayout.createSequentialGroup()
                        .addComponent(exportSpectraCheckBox)
                        .addGap(0, 324, Short.MAX_VALUE))
                    .addComponent(exportButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mzIdentMlExportPanelLayout.createSequentialGroup()
                        .addComponent(mzIdentMlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mzIdentMlTextField)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(browseMgfButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseMzIdentMlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        mzIdentMlExportPanelLayout.setVerticalGroup(
            mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mzIdentMlExportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mzIdentMlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mzIdentMlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(browseMzIdentMlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportSpectraCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mgfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseMgfButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mgfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mzIdentMlExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mzIdentMlExportPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mzIdentMlExportPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseMgfButton;
    private javax.swing.JButton browseMzIdentMlButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton exportButton;
    private javax.swing.JCheckBox exportSpectraCheckBox;
    private javax.swing.JLabel mgfLabel;
    private javax.swing.JTextField mgfTextField;
    private javax.swing.JPanel mzIdentMlExportPanel;
    private javax.swing.JLabel mzIdentMlLabel;
    private javax.swing.JTextField mzIdentMlTextField;
    private javax.swing.JComboBox<String> userComboBox;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
