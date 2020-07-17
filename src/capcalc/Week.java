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
    private double oraszam = 168.0;
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
        //kinullazzuk a hethez tartozó adatokat a modellben
        //meg kell keresni h hányadik hét
        int hetoszlopa = 0;
        for (int i = 0; i < ws.jTable1.getColumnCount(); i++) {

            if (ws.jTable1.getColumnName(i).equals(getWeekname())) {

                hetoszlopa = i;
            }

        }

        for (int r = 0; r < model.getRowCount(); r++) {
            try {
                model.setValueAt(null, r, hetoszlopa);
            } catch (Exception e) {
            }

        }

        //bejarjuk a sajat adatokat es megprobaljuk megkeresni a prefixet
        outerloop:
        for (int i = 0; i < getGyartasok().size(); i++) {

            for (int r = 0; r < model.getRowCount(); r++) {

                if (model.getValueAt(r, 0).equals(getGyartasok().get(i)[0].substring(0, 5))) {

                    //ha talalunk akkor megkeressuk a het nevet is
                    double gyartasido = 0;
                    for (int c = 1; c < model.getColumnCount(); c++) {

                        if (model.getColumnName(c).equals(getWeekname())) {
                            //ki kell számolni az uj adatot
                            double ujadat = 0.00;

                            //be kell allitani az addatokat a cellaba
                            try {

                                gyartasido += Double.parseDouble(model.getValueAt(r, c).toString()) + calcGyartasiido(Double.parseDouble(getGyartasok().get(i)[1]), Double.parseDouble(getGyartasok().get(i)[4]), Double.parseDouble(getGyartasok().get(i)[6]));

                            } catch (Exception e) {
                                try {
                                    gyartasido = calcGyartasiido(Double.parseDouble(getGyartasok().get(i)[1]), Double.parseDouble(getGyartasok().get(i)[4]), Double.parseDouble(getGyartasok().get(i)[6]));
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

                    try {
                        gyartasido += Double.parseDouble(model.getValueAt(model.getRowCount() - 1, c).toString()) + calcGyartasiido(Double.parseDouble(getGyartasok().get(i)[1]), Double.parseDouble(getGyartasok().get(i)[4]), Double.parseDouble(getGyartasok().get(i)[6]));
                    } catch (Exception e) {
                        try {
                            gyartasido = calcGyartasiido(Double.parseDouble(getGyartasok().get(i)[1]), Double.parseDouble(getGyartasok().get(i)[4]), Double.parseDouble(getGyartasok().get(i)[6]));
                        } catch (Exception ex) {
                        }
                    }

                    model.setValueAt(new DecimalFormat("#.##").format(gyartasido), model.getRowCount() - 1, c);
                    continue outerloop;

                }

            }

        }

        //ki kell szamolni a summat es beírni a legfelső sorba
        for (int c = 1; c < model.getColumnCount(); c++) {

            if (model.getColumnName(c).equals(getWeekname())) {

                //bejarjuk a sorokat es osszeadjuk h mizu
                double osszeg = 0.00;
                for (int r = 1; r < model.getRowCount(); r++) {
                    try {
                        osszeg += Double.parseDouble(model.getValueAt(r, c).toString());
                    } catch (Exception e) {
                    }
                }
                model.setValueAt(new DecimalFormat("#.##").format(osszeg), 0, c);
                break;

            }

        }

        ws.jTable1.setModel(model);
        new TableWidth(ws.jTable1);
    }

    public double calcGyartasiido(double demand, double ct, double eff) {

        double gyi = 0.00;
        //demand * ciklusido órásítva és a pn hatékonysággal számolva
        try {
            gyi = demand * ct / 60 / 60 / eff;
        } catch (Exception e) {
        }
        //vegyük figyelembe a heti paramétereket is ha van
        for (int i = 0; i < this.getTenyezoList().size(); i++) {

            gyi = gyi / this.getTenyezoList().get(i).getTenyezo();
        }

        gyi = gyi / ws.getHatekonysag();
        gyi += ws.getTarazasiido();

        return gyi;
    }

}
