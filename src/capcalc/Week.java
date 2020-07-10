/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabor_hanacsek
 */
public class Week {

    //melyik ws hez tartozik
    private String wsname = "";
    private String weekname = "";
    private ArrayList<String[]> gyartasok = new ArrayList<>();
    private WorkStation ws = null;
    private double oraszam = 10.0;
    private ArrayList<Factor> tenyezolist = new ArrayList<>();

    public Week(String wsname, String weekname, WorkStation ws) {

        setWsname(wsname);
        setWeekname(weekname);
        setWs(ws);
        //a konstrukció után be kell gyujtenie a saját adatait az összesített listából
        getSajatAdat();
        //ki kell tenni a táblába a megfelelő helyre
        setSajatAdatToTable();

    }

    public ArrayList<Factor> getTenyezoList() {
        return tenyezolist;
    }

    public void setTenyezoList(ArrayList<Factor> tenyezolist) {
        this.tenyezolist = tenyezolist;
    }

    public double getOraszam() {
        return oraszam;
    }

    public void setOraszam(double oraszam) {
        this.oraszam = oraszam;
    }

    public WorkStation getWs() {
        return ws;
    }

    public void setWs(WorkStation ws) {
        this.ws = ws;
    }

    public String getWsname() {
        return wsname;
    }

    public void setWsname(String wsname) {
        this.wsname = wsname;
    }

    public String getWeekname() {
        return weekname;
    }

    public void setWeekname(String weekname) {
        this.weekname = weekname;
    }

    public ArrayList<String[]> getGyartasok() {
        return gyartasok;
    }

    public void setGyartasok(ArrayList<String[]> gyartasok) {
        this.gyartasok = gyartasok;
    }

    public void getSajatAdat() {

        getGyartasok().clear();
        //az összesített sheetről kiszedi a megfelelő adatokat és eltárolja saját magában

        for (int i = 0; i < MainWindow.so.getOsszegzes().size(); i++) {
            try {
                if (MainWindow.so.getOsszegzes().get(i)[2].toLowerCase().trim().equals(getWeekname().toLowerCase().trim()) && MainWindow.so.getOsszegzes().get(i)[3].toLowerCase().trim().equals(getWsname().toLowerCase().trim())) {

                    getGyartasok().add(MainWindow.so.getOsszegzes().get(i));

                }
            } catch (Exception e) {
            }

        }

    }

    public void setSajatAdatToTable() {
//ez teszi ki a saját gyártásait a workstation táblájába
        //be kell jarni a tablat, meg kell keresni a prefixet es a hetnek a szamat
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) ws.jTable1.getModel();

        //bejarjuk a sajat adatokat es megprobaljuk megkeresni a prefixet
        outerloop:
        for (int i = 0; i < getGyartasok().size(); i++) {

            for (int r = 0; r < model.getRowCount(); r++) {

                if (model.getValueAt(r, 0).equals(getGyartasok().get(i)[0].substring(0, 5))) {

                    //ha talalunk akkor megkeressuk a het nevet is
                    double gyartasido = 0;
                    for (int c = 1; c < model.getColumnCount(); c++) {

                        if (model.getColumnName(c).equals(getWeekname())) {
                            //be kell allitani az addatokat a cellaba
                            try {
                                //a gyartasidot ki kell szamolni, demand * qty /60 /60 
                                gyartasido += Double.parseDouble(model.getValueAt(r, c).toString()) + ((Double.parseDouble(getGyartasok().get(i)[1]) * Double.parseDouble(getGyartasok().get(i)[4])) / 60 / 60);

                            } catch (Exception e) {
                                try {
                                    gyartasido = Double.parseDouble(getGyartasok().get(i)[1]) * Double.parseDouble(getGyartasok().get(i)[4]) / 60 / 60;
                                } catch (Exception ex) {
                                }
                            }
                            model.setValueAt(new DecimalFormat("#.##").format(gyartasido), r, c);

                        }

                    }

                    continue outerloop;
                }

            }
            //ha ide jutunk akkor kell egy uj sor es beallitani a prefixet, de 2 sor eleve letezik
            model.addRow(new Object[model.getColumnCount()]);
            model.setValueAt(getGyartasok().get(i)[0].substring(0, 5), model.getRowCount() - 1, 0);
            //be kell írni az adatokat is
            double gyartasido = 0;
            for (int c = 1; c < model.getColumnCount(); c++) {

                if (model.getColumnName(c).equals(getWeekname())) {
                    //be kell allitani az addatokat a cellaba
                    try {
                        gyartasido += Double.parseDouble(model.getValueAt(model.getRowCount() - 1, c).toString()) + ((Double.parseDouble(getGyartasok().get(i)[1]) * Double.parseDouble(getGyartasok().get(i)[4])) / 60 / 60);
                    } catch (Exception e) {
                        try {
                            gyartasido = Double.parseDouble(getGyartasok().get(i)[1]) * Double.parseDouble(getGyartasok().get(i)[4]) / 60 / 60;
                        } catch (Exception ex) {
                        }
                    }
                    model.setValueAt(new DecimalFormat("#.##").format(gyartasido), model.getRowCount() - 1, c);
                    continue outerloop;

                }

            }

        }

    }

}
