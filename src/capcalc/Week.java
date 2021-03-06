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
    //a heten elerheto oraszam
    private double oraszam = 168.0;
    private ArrayList<Factor> tenyezolist = new ArrayList<>();
    //tarazssal mernokivel egyutt
    private double gyartasiora = 0.00;
    private double mernokiido = 0.00;
    //a tiszta gyártási idő
    private double sumgyartasiido = 0.00;
    //a tárazási idő
    private double tarazasiido = 0.00;

    public Week(String wsname, String weekname, WorkStation ws) {

        setWsname(wsname);
        setWeekname(weekname);
        setWs(ws);
        //a konstrukció után be kell gyujtenie a saját adatait az összesített listából
        getSajatAdat();
        //ki kell tenni a táblába a megfelelő helyre
        setSajatAdatToTable();

    }

    public double getTarazasiido() {
        return tarazasiido;
    }

    public void setTarazasiido(double tarazasiido) {
        this.tarazasiido = tarazasiido;
    }

    public double getSumgyartasiido() {
        return sumgyartasiido;
    }

    public void setSumgyartasiido(double sumgyartasiido) {
        this.sumgyartasiido = sumgyartasiido;
    }

    public double getMernokiido() {
        return mernokiido;
    }

    public void setMernokiido(double mernokiido) {
        this.mernokiido = mernokiido;
    }

    public double getGyartasiora() {
        return gyartasiora;
    }

    public void setGyartasiora(double gyartasiora) {
        this.gyartasiora = gyartasiora;
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
        //az összesített sheetről kiszedi a megfelelő adatokat és eltárolja saját magában
        getGyartasok().clear();

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

                            //ha a cellába ERROR van írva akkor mehetünk is tovább
                            try {
                                if (model.getValueAt(r, c).toString().equals("ERROR")) {
                                    continue outerloop;
                                }
                            } catch (Exception e) {
                            }

                            //ki kell számolni az uj adatot
                            double ujadat = 0.00;

                            //be kell allitani az addatokat a cellaba
                            try {

                                gyartasido += Double.parseDouble(model.getValueAt(r, c).toString()) + calcGyartasiido(Double.parseDouble(getGyartasok().get(i)[1]), Double.parseDouble(getGyartasok().get(i)[4]), Double.parseDouble(getGyartasok().get(i)[6]));

                            } catch (Exception e) {
                                try {
                                    gyartasido = calcGyartasiido(Double.parseDouble(getGyartasok().get(i)[1]), Double.parseDouble(getGyartasok().get(i)[4]), Double.parseDouble(getGyartasok().get(i)[6]));
                                } catch (Exception ex) {
                                    model.setValueAt("ERROR", r, c);
                                    continue outerloop;

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

        //ki kell szamolni a summat es beírni a legfelső sorba--------------------------------------------->
        //kinullazzuk a sum gyartasi idot
        for (int c = 1; c < model.getColumnCount(); c++) {
            setSumgyartasiido(0);
            if (model.getColumnName(c).equals(getWeekname())) {

                //bejarjuk a sorokat es osszeadjuk h mizu
                double osszeg = 0.00;
                for (int r = 1; r < model.getRowCount(); r++) {
                    try {
                        osszeg += Double.parseDouble(model.getValueAt(r, c).toString());
                        //hozzaadjuk a gyartasiidohoz (pure)
                        setSumgyartasiido(getSumgyartasiido() + Double.parseDouble(model.getValueAt(r, c).toString()));

                    } catch (Exception e) {
                    }
                }
                //hozzáadjuk a mérnöki időt
                osszeg += getMernokiido();
                //hozzáadjuk a tárazgálási időt
                osszeg += calcTarazasiido();

                //számoljuk ki százalékban is
                //kiszámoljuk az 1 százalékát a hét óraszámának
                double szazalek = 0.00;
                szazalek = this.getOraszam() / 100;
                //megnezzuk, hogy ez hanyszor van meg az összegben
                szazalek = osszeg / szazalek;
                setGyartasiora(osszeg);

                model.setValueAt(new DecimalFormat("#.##").format(osszeg) + "ó " + new DecimalFormat("#.##").format(szazalek) + "%", 0, c);
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

        return gyi;
    }

    public double calcTarazasiido() {

        double ido = 0.00;
        try {
            ido = getTarazasiido() * this.getGyartasok().size();
        } catch (Exception e) {
        }

        return ido;

    }

}
