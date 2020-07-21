/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabor_hanacsek
 */
public class HetDataEdit extends javax.swing.JDialog {

    /**
     * Creates new form HetDataEdit
     */
    WorkStation w;
    Week wk;

    public HetDataEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setVisible(boolean b, WorkStation w) {

        this.w = w;
        //ki kell szedni a hét adatait és be kell tenni a táblába
        hetDataToTable();
        this.setVisible(b);
        new TableWidth(jTable1);
        //beallitjuk, hogy hanyadik hetet editaljuk
        this.setTitle("Week: " + w.jTable1.getColumnName(w.jTable1.getSelectedColumn()));
        //a hét óraszámát is kiszedjük
        for (int i = 0; i < w.getWeekList().size(); i++) {

            if (w.getWeekList().get(i).getWeekname().equals(w.jTable1.getColumnName(w.jTable1.getSelectedColumn()))) {
                jTextField2.setText(String.valueOf(w.getWeekList().get(i).getOraszam()));
                //a mernoki ido beallitasa a textfiledbe
                jTextField3.setText(String.valueOf(w.getWeekList().get(i).getMernokiido()));
                this.wk = w.getWeekList().get(i);
            }

        }

    }
//a kis szerkeszto ablakba teszi ki az adatokat,gyártás és a factorok

    public void hetDataToTable() {

        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        DefaultTableModel model1 = new DefaultTableModel();
        model1 = (DefaultTableModel) jTable2.getModel();
        model1.setRowCount(0);
        //kiszedjuk a hetet a ws ből
        for (int i = 0; i < w.getWeekList().size(); i++) {

            if (w.getWeekList().get(i).getWeekname().equals(w.jTable1.getColumnName(w.jTable1.getSelectedColumn()))) {

                for (int n = 0; n < w.getWeekList().get(i).getGyartasok().size(); n++) {
                    try {

                        double gyartas = 0.00;
                        gyartas = w.getWeekList().get(i).calcGyartasiido(Double.parseDouble(w.getWeekList().get(i).getGyartasok().get(n)[1].toString()), Double.parseDouble(w.getWeekList().get(i).getGyartasok().get(n)[4].toString()), Double.parseDouble(w.getWeekList().get(i).getGyartasok().get(n)[6].toString()));
                        model.addRow(new Object[]{w.getWeekList().get(i).getGyartasok().get(n)[0], w.getWeekList().get(i).getGyartasok().get(n)[1], w.getWeekList().get(i).getGyartasok().get(n)[2], w.getWeekList().get(i).getGyartasok().get(n)[3], new DecimalFormat("#.##").format(Double.parseDouble(w.getWeekList().get(i).getGyartasok().get(n)[4])), new DecimalFormat("#.##").format(gyartas), new DecimalFormat("#.##").format(Double.parseDouble(w.getWeekList().get(i).getGyartasok().get(n)[6]))});
                    } catch (Exception e) {
                    }
                }

                for (int n = 0; n < w.getWeekList().get(i).getTenyezoList().size(); n++) {
                    try {
                        model1.addRow(new Object[]{w.getWeekList().get(i).getTenyezoList().get(n).getNeve(), w.getWeekList().get(i).getTenyezoList().get(n).getLeiras(), w.getWeekList().get(i).getTenyezoList().get(n).getTenyezo()});
                    } catch (Exception e) {
                    }
                }

                break;

            }

        }

        jTable1.setModel(model);
        jTable2.setModel(model1);
//        new TableWidth(jTable2);
        new TableWidth(jTable1);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Hét adatainak módosítása"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PartNumber", "Demand", "StartWeek", "Station", "CT/DB", "Gyártási idő (cummulative)", "Eff"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCellSelectionEnabled(true);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/plus.png"))); // NOI18N
        jLabel1.setToolTipText("Sor hozzáadása");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setText("Kereső:");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(388, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Demand", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("A hetet befolyásoló tényezők szerkesztése"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Név", "Leírás", "Hatékonyság"
            }
        ));
        jTable2.setCellSelectionEnabled(true);
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(5);
        }

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/plus.png"))); // NOI18N
        jLabel2.setToolTipText("Sor hozzáadása");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel4.setText("Hét óraszáma:");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });

        jLabel5.setText("Mérnöki idő:");

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Tényezők", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        //kereső
        new UniversalFilter(jTextField1.getText(), jTable1);
    }//GEN-LAST:event_jTextField1KeyReleased
    // a módosított adatok kicserélése a hét adataiban

    public void reSum() {

        //kikapcsoljuk az editalast
        if (jTable1.isEditing()) {

            jTable1.getCellEditor().cancelCellEditing();
        }
        //megkeressuk a megfelelo hetet
        for (int i = 0; i < w.getWeekList().size(); i++) {

            if (w.getWeekList().get(i).getWeekname().equals(w.jTable1.getColumnName(w.jTable1.getSelectedColumn()))) {
//kiszedjuk a tabla 
                w.getWeekList().get(i).getGyartasok().clear();
                DefaultTableModel model = new DefaultTableModel();
                model = (DefaultTableModel) jTable1.getModel();
                for (int n = 0; n < model.getRowCount(); n++) {

                    try {
                        String[] adatok = new String[7];
                        adatok[0] = model.getValueAt(n, 0).toString().trim();
                        adatok[1] = model.getValueAt(n, 1).toString().trim();
                        try {
                            adatok[2] = model.getValueAt(n, 2).toString().trim();
                            adatok[3] = model.getValueAt(n, 3).toString().trim();
                        } catch (Exception e) {
                            adatok[2] = "";
                            adatok[3] = "";
                        }
                        adatok[4] = model.getValueAt(n, 4).toString().trim();
                        adatok[5] = model.getValueAt(n, 5).toString().trim();
                        adatok[6] = model.getValueAt(n, 6).toString().trim();
                        w.getWeekList().get(i).getGyartasok().add(adatok);

                    } catch (Exception e) {
                    }

                }

                //ujra kell futtatni a kalkulációt 
                w.getWeekList().get(i).setSajatAdatToTable();
                break;

            }

        }

    }


    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        //a billentyu felengedesekor szamoljunk
        if (jTable1.isEditing() == false) {
            DefaultTableModel model = new DefaultTableModel();
            model = (DefaultTableModel) jTable1.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                double gyartas = 0.00;
                try {

                    gyartas = wk.calcGyartasiido(Double.parseDouble(model.getValueAt(i, 1).toString()), Double.parseDouble(model.getValueAt(i, 4).toString()), Double.parseDouble(model.getValueAt(i, 6).toString()));
                    model.setValueAt(new DecimalFormat("#.##").format(gyartas), i, 5);

                } catch (Exception e) {
                }

            }

            jTable1.setModel(model);

        }

        reSum();

    }//GEN-LAST:event_jTable1KeyReleased

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        //sor hozzáadása a táblához
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[6]);
        jTable1.setModel(model);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        //tényezők táblához sor hozzáadása
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable2.getModel();
        model.addRow(new Object[3]);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            setTenyezo();
        }

    }//GEN-LAST:event_jTable2KeyPressed

    public void setTenyezo() {

        //a tényezők mentése
        if (jTable1.isEditing()) {

            jTable1.getCellEditor().cancelCellEditing();
        }
        // a hét befolyásoló tényezőinek frissítése
        //keressük meg melyik hétről van szó
        for (int i = 0; i < w.getWeekList().size(); i++) {
            if (w.getWeekList().get(i).getWeekname().equals(w.jTable1.getColumnName(w.jTable1.getSelectedColumn()))) {

                w.getWeekList().get(i).getTenyezoList().clear();
                //vegigmegyunk a tabla sorain, es ami ki van toltve maradektalalnul azt felvisszuk tenyezokent
                for (int n = 0; n < jTable2.getRowCount(); n++) {
                    try {
                        if (!jTable2.getValueAt(n, 0).equals("") && !jTable2.getValueAt(n, 1).equals("") && !jTable2.getValueAt(n, 2).equals("") && jTable2.getValueAt(n, 0) != null) {

                            Factor f = new Factor(Integer.parseInt(w.getWeekList().get(i).getWeekname()), jTable2.getValueAt(n, 0).toString(), jTable2.getValueAt(n, 1).toString(), Double.parseDouble(jTable2.getValueAt(n, 2).toString()));
                            w.getWeekList().get(i).getTenyezoList().add(f);
                            //System.out.println("hozzáadva!");
                        }
                    } catch (Exception e) {

                    }

                }

                //beallitjuk a het oraszamat is
                w.getWeekList().get(i).setOraszam(Double.parseDouble(jTextField2.getText()));
                //beallitjuk a mernoki időt is
                w.getWeekList().get(i).setMernokiido(Double.parseDouble(jTextField3.getText()));
                //lefuttatjuk a sajatadattotablet
                w.getWeekList().get(i).setSajatAdatToTable();
            }

        }

        //default title and icon
        JOptionPane.showMessageDialog(this,
                "Sikeres módosítás!");

    }


    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            setTenyezo();
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        //mernöki idő beállítása
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            setTenyezo();
        }
    }//GEN-LAST:event_jTextField3KeyPressed

    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HetDataEdit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HetDataEdit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HetDataEdit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HetDataEdit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HetDataEdit dialog = new HetDataEdit(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

   
}
