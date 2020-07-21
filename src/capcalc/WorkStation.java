/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabor_hanacsek
 */
public class WorkStation extends javax.swing.JPanel {

    private String name = "";
    private double hatekonysag = 0.00;
    private ArrayList<Week> weekList = new ArrayList<>();
    MainWindow m;
//    private double tarazasiido = 0.00;

    public WorkStation(String name, double hatekonysag, int szelesseg, int magassag, MainWindow m) {
        initComponents();
        this.m = m;
        jTable1.getTableHeader().setDefaultRenderer(new WsHeaderRenderer(this));
        jTable1.setDefaultRenderer(Object.class, new WsRenderer(this));
        setName(name);
        setHatekonysag(hatekonysag);
        //setTarazasiido(tarazasiido);
        setSize(MainWindow.jPanel2.getSize().width, magassag);
        //a pozicioja beallitasa
        setPosition();
        //beallitjuk a jlistet
        setJlist();
        //beallitjuk a nevét és a hatékonyságot a táblába
        setStartData();
        //hozzáadjuk a heteit
        addWeeksToWs();
        //beallitjuk a ws magassagat
        setWsHeight();

    }


    public ArrayList<Week> getWeekList() {
        return weekList;
    }

    public void setWeekList(ArrayList<Week> weekList) {
        this.weekList = weekList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHatekonysag() {
        return hatekonysag;
    }

    public void setHatekonysag(double hatekonysag) {
        this.hatekonysag = hatekonysag;
    }

    //ezzel állítjuk be a ws magasságát
    public void setWsHeight() {

        this.setSize(this.getWidth(), jTable1.getPreferredSize().height + 40);
        MainWindow.jPanel2.revalidate();
        MainWindow.jPanel2.repaint();

    }

    //ezzel keresi meg a helyét, alulra helyezkedik el
    public void setPosition() {
        MainWindow.setWomagassag(0);
        ArrayList<WorkStation> wolist = new ArrayList<>();
        //begyujtjuk az osszes komponenst ami wo a panelrol
        for (int i = 0; i < MainWindow.jPanel2.getComponentCount(); i++) {

            if (MainWindow.jPanel2.getComponent(i) instanceof WorkStation && !MainWindow.jPanel2.getComponent(i).equals(this)) {

                wolist.add((WorkStation) MainWindow.jPanel2.getComponent(i));

            }

        }

        //ha ez megvan akkor bejarjuk a listat es szepen kitesszuk oket sorban
        for (WorkStation w : wolist) {

            w.setLocation(0, MainWindow.getWomagassag());
            MainWindow.setWomagassag(MainWindow.getWomagassag() + w.getHeight() + 10);

        }

        //eltesszuk thist is a helyere
        this.setLocation(0, MainWindow.getWomagassag());
        MainWindow.setWomagassag(MainWindow.getWomagassag() + this.getHeight() + 10);

//ha a womagassag magassaga nagyobb a jpanel 2 preferred magassaganal akkor utanna allitjuk
        if (MainWindow.getWomagassag() > MainWindow.jPanel2.getPreferredSize().height) {

            MainWindow.jPanel2.setPreferredSize(new Dimension(MainWindow.jPanel2.getWidth(), MainWindow.getWomagassag() + 10));

        }

        //a jtable magassaga legyen a sajat magassaga
//        this.setSize(new Dimension(this.getSize().width, jTable1.getHeight()));
        MainWindow.jPanel2.revalidate();

    }

    public void addWeeksToWs() {
        //az osszegzes listabaol kell kiszedni azokat az elemeket amikhez ez a sor tartozik
        int minhet = 999999;
        int maxhet = 0;
        //kitoroljuk a weektarolot
        this.getWeekList().clear();
        for (int i = 0; i < MainWindow.so.getOsszegzes().size(); i++) {
            //ha egyezik a sor neve a stationeval
            if (this.getName().toUpperCase().trim().equals(MainWindow.so.getOsszegzes().get(i)[3].toUpperCase().trim())) {
                if (minhet > Integer.parseInt(MainWindow.so.getOsszegzes().get(i)[2])) {

                    minhet = Integer.parseInt(MainWindow.so.getOsszegzes().get(i)[2]);

                }

                if (maxhet < Integer.parseInt(MainWindow.so.getOsszegzes().get(i)[2])) {

                    maxhet = Integer.parseInt(MainWindow.so.getOsszegzes().get(i)[2]);

                }

            }
        }

        //kiszamoljuk hany hetet kell hozzaadni
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        int hetszama = minhet;
        for (int i = hetszama; hetszama <= maxhet; i++) {
            if (Integer.parseInt(String.valueOf(hetszama).substring(2)) == 55) {

                hetszama += 46;
            }
            model.addColumn(hetszama);
            hetszama++;

        }
        jTable1.setModel(model);

        //hozzáadjuk a hét objektumokat a ws hez
        for (int i = 1; i < model.getColumnCount(); i++) {

            //hozzáadunk egy hetet a ws hétlistájához
            getWeekList().add(new Week(this.getName(), model.getColumnName(i), this));
        }

        //a szelesseg automatara allitasa
        new TableWidth(jTable1);

    }

    public void setStartData() {
//az alap adatok beállítása, név, alap hatékonyság, a jélistbe

       
        //a táblába beállítjuk a sum erteket
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        model.setColumnCount(1);
        model.addRow(new Object[]{"SUM:"});
        jTable1.setModel(model);

    }
    
    public void setJlist(){
    
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("<html><span style=\"color:red;\">Állomás neve: </span>" + getName());
        listModel.addElement("<html><span style=\"color:red;\">Állomás alap hatékonysága: </span>" + getHatekonysag());
        jList1.setModel(listModel);
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        torles = new javax.swing.JMenuItem();
        editws = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        torles.setText("Állomás törlése");
        torles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                torlesActionPerformed(evt);
            }
        });
        jPopupMenu1.add(torles);

        editws.setText("Állomás szerkesztése");
        editws.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editwsActionPerformed(evt);
            }
        });
        jPopupMenu1.add(editws);

        jPopupMenu1.getAccessibleContext().setAccessibleParent(this);

        setComponentPopupMenu(jPopupMenu1);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Prefix"
            }
        ));
        jTable1.setCellSelectionEnabled(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/machine.png"))); // NOI18N

        jList1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void torlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_torlesActionPerformed
        //állomás törlése

        MainWindow.jPanel2.remove(this);
        setPosition();

        MainWindow.jPanel2.revalidate();
        MainWindow.jPanel2.repaint();

    }//GEN-LAST:event_torlesActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formMousePressed

    private void editwsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editwsActionPerformed
        //hetedit visible
        m.ohe.setVisible(true, this);

    }//GEN-LAST:event_editwsActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        m.hde.setVisible(true, this);
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem editws;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JList<String> jList1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTable1;
    private javax.swing.JMenuItem torles;
    // End of variables declaration//GEN-END:variables
}
