package com.compomics.colims.client.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author Davy Maddelein
 */
public class UserQueryPanel extends javax.swing.JPanel {

    private final JFileChooser exportFileChooser = new JFileChooser();

    /**
     * Creates new form UserQueryPanel.
     */
    public UserQueryPanel() {
        initComponents();

        exportFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        queryResultTableScrollPane.getViewport().setOpaque(false);
    }

    public JButton getExecuteQueryButton() {
        return executeQueryButton;
    }

    public JTextArea getQueryInputTextArea() {
        return queryInputTextArea;
    }

    public JTable getQueryResultTable() {
        return queryResultTable;
    }

    public JComboBox getUserQueryComboBox() {
        return userQueryComboBox;
    }

    public JButton getClearQueryButton() {
        return clearQueryButton;
    }

    public JButton getClearResultsButton() {
        return clearResultsButton;
    }

    public JButton getExportResultsButton() {
        return exportResultsButton;
    }

    public JFileChooser getExportFileChooser() {
        return exportFileChooser;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        queryInputParentPanel = new javax.swing.JPanel();
        queryInputTextAreaScrollPane = new javax.swing.JScrollPane();
        queryInputTextArea = new javax.swing.JTextArea();
        clearQueryButton = new javax.swing.JButton();
        userQueryComboBox = new javax.swing.JComboBox();
        executeQueryButton = new javax.swing.JButton();
        queryResultParentPanel = new javax.swing.JPanel();
        queryResultTableScrollPane = new javax.swing.JScrollPane();
        queryResultTable = new javax.swing.JTable();
        exportResultsButton = new javax.swing.JButton();
        clearResultsButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        queryInputParentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Query Input"));
        queryInputParentPanel.setOpaque(false);
        queryInputParentPanel.setPreferredSize(new java.awt.Dimension(0, 0));

        queryInputTextArea.setColumns(20);
        queryInputTextArea.setRows(5);
        queryInputTextAreaScrollPane.setViewportView(queryInputTextArea);

        clearQueryButton.setText("clear");
        clearQueryButton.setToolTipText("clear the query input");
        clearQueryButton.setMaximumSize(new java.awt.Dimension(80, 25));
        clearQueryButton.setMinimumSize(new java.awt.Dimension(80, 25));
        clearQueryButton.setPreferredSize(new java.awt.Dimension(80, 25));

        executeQueryButton.setText("execute");
        executeQueryButton.setToolTipText("execute the query");
        executeQueryButton.setMaximumSize(new java.awt.Dimension(80, 25));
        executeQueryButton.setMinimumSize(new java.awt.Dimension(80, 25));
        executeQueryButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout queryInputParentPanelLayout = new javax.swing.GroupLayout(queryInputParentPanel);
        queryInputParentPanel.setLayout(queryInputParentPanelLayout);
        queryInputParentPanelLayout.setHorizontalGroup(
            queryInputParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(queryInputParentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(queryInputParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(queryInputTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addComponent(userQueryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, queryInputParentPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(clearQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(executeQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        queryInputParentPanelLayout.setVerticalGroup(
            queryInputParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(queryInputParentPanelLayout.createSequentialGroup()
                .addComponent(userQueryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(queryInputTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(queryInputParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(executeQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        add(queryInputParentPanel, gridBagConstraints);

        queryResultParentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Query Result"));
        queryResultParentPanel.setOpaque(false);
        queryResultParentPanel.setPreferredSize(new java.awt.Dimension(0, 0));

        queryResultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        queryResultTableScrollPane.setViewportView(queryResultTable);

        exportResultsButton.setText("export");
        exportResultsButton.setToolTipText("export the results to a tab separeted file");
        exportResultsButton.setMaximumSize(new java.awt.Dimension(80, 25));
        exportResultsButton.setMinimumSize(new java.awt.Dimension(80, 25));
        exportResultsButton.setPreferredSize(new java.awt.Dimension(80, 25));

        clearResultsButton.setText("clear");
        clearResultsButton.setToolTipText("clear the results table");
        clearResultsButton.setMaximumSize(new java.awt.Dimension(80, 25));
        clearResultsButton.setMinimumSize(new java.awt.Dimension(80, 25));
        clearResultsButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout queryResultParentPanelLayout = new javax.swing.GroupLayout(queryResultParentPanel);
        queryResultParentPanel.setLayout(queryResultParentPanelLayout);
        queryResultParentPanelLayout.setHorizontalGroup(
            queryResultParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(queryResultTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
            .addGroup(queryResultParentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clearResultsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportResultsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        queryResultParentPanelLayout.setVerticalGroup(
            queryResultParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(queryResultParentPanelLayout.createSequentialGroup()
                .addComponent(queryResultTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(queryResultParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearResultsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportResultsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        add(queryResultParentPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearQueryButton;
    private javax.swing.JButton clearResultsButton;
    private javax.swing.JButton executeQueryButton;
    private javax.swing.JButton exportResultsButton;
    private javax.swing.JPanel queryInputParentPanel;
    private javax.swing.JTextArea queryInputTextArea;
    private javax.swing.JScrollPane queryInputTextAreaScrollPane;
    private javax.swing.JPanel queryResultParentPanel;
    private javax.swing.JTable queryResultTable;
    private javax.swing.JScrollPane queryResultTableScrollPane;
    private javax.swing.JComboBox userQueryComboBox;
    // End of variables declaration//GEN-END:variables

}
