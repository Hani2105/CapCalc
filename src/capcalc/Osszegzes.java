/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import static capcalc.MainWindow.so;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabor_hanacsek
 */
public class Osszegzes implements Runnable {

    MainWindow m;

    public Osszegzes(MainWindow m) {
        this.m = m;

    }

    @Override
    public void run() {

        //kinullazzuk a tablat
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) m.jTable5.getModel();
        model.setRowCount(0);
        //kinullazzuk az adatokat
        so.getOsszegzes().clear();
        //beallitjuk a splaschben a demand maxát
        m.s.jProgressBar1.setString(null);
        m.s.jProgressBar1.setMaximum(so.getDemandList().size());
        //be kell jarni a demand tablat soronkent, meg kell keresni hozzá a default stationt, a ciklusidoket es a starthetet kiszamolni
        //a demand tabla beforgatasa
        outerloop:
        for (int d = 0; d < so.getDemandList().size(); d++) {
            //beallitjuk az erteket a splashben
            m.s.jProgressBar1.setValue(d);
//felvesszuk a pn valtozot
            String pn = so.getDemandList().get(d)[1].trim().toUpperCase();
//átalakítjuk a startdátumot évhétté
            String startdate = null;
            try {
                startdate = m.evhet(so.getDemandList().get(d)[7].trim());
            } catch (ParseException ex) {
                Logger.getLogger(Osszegzes.class.getName()).log(Level.SEVERE, null, ex);
            }
//kiszedjuk a gyartando darabszamot
            int qty = 0;
            try {
                qty = Integer.parseInt(so.getDemandList().get(d)[13].replaceAll("[^0-9]", ""));
            } catch (Exception e) {
            }
//be kell jarni a kapcsolatok tablat es meg kell keresni, hogy letezik e ehhez a pn hez hozzaadott sor vagy cella
            String defaultws = "";

            for (int k = 0; k < so.getKapcsolatList().size(); k++) {
//ha megvan a pn ben a kapcsoaltlista pn je
                if (pn.equals(so.getKapcsolatList().get(k)[0].trim().toUpperCase())) {
//megnezzuk, hogy van e hozza default sor, vagyis kell, hogy legyen
                    defaultws = so.getKapcsolatList().get(k)[1].trim();
                    break;

                }

            }
//akkor megyünk tovább ha van default ws, ha nincs akkor azt írjuk be az összegzésbe, hogy az kellene..
            if (defaultws.equals("")) {

                String[] adatok = new String[7];
                adatok[0] = pn;
                adatok[1] = String.valueOf(qty);
                adatok[2] = startdate;
                adatok[3] = "Nincs default állomás!";
                adatok[4] = "";
                adatok[5] = "";
                adatok[6] = "";

                so.getOsszegzes().add(adatok);
                model.addRow(adatok);
                //johet a kovetkezo sor
                continue outerloop;
            } //ha van default állomás akkor megpróbáljuk megkeresni az smt-s ciklusidők között a pn-t és az állomás kombót
            else if (!defaultws.equals("")) {
                //a cycletime
                double ct = 0.00;
                double eff = 0.00;
                //panelizacio

                for (int c = 0; c < so.getSmtcycletime().size(); c++) {

                    if (pn.equals(so.getSmtcycletime().get(c)[1].trim().toUpperCase()) && defaultws.toUpperCase().trim().equals(so.getSmtcycletime().get(c)[3].toUpperCase().trim())) {
//a legerosebb merest kell alapul venni
                        //ha kalkulalt
                        try {
                            if (so.getSmtcycletime().get(c)[8] != null) {

                                ct += Double.parseDouble(so.getSmtcycletime().get(c)[8]) / Double.parseDouble(so.getSmtcycletime().get(c)[5]);
                                eff = Double.parseDouble(so.getSmtcycletime().get(c)[9]) / 100;

                                continue;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } //ha gyorsmeres van

                        try {
                            if (so.getSmtcycletime().get(c)[7] != null) {

                                ct += Double.parseDouble(so.getSmtcycletime().get(c)[7]) / Double.parseDouble(so.getSmtcycletime().get(c)[5]);
                                eff = Double.parseDouble(so.getSmtcycletime().get(c)[9]) / 100;
                                continue;
                            }
                        } catch (Exception e) {
                        }//mert
                        try {
                            if (so.getSmtcycletime().get(c)[6] != null) {

                                ct += Double.parseDouble(so.getSmtcycletime().get(c)[6]) / Double.parseDouble(so.getSmtcycletime().get(c)[5]);
                                eff = Double.parseDouble(so.getSmtcycletime().get(c)[9]) / 100;
                                continue;
                            }
                        } catch (Exception e) {
                        }

                    }

                }

//ha végigértünk és nulla a ct akkor kiirjuk, hogy nincs ciklusido smt-n
                if (ct == 0.00) {

                    String[] adatok = new String[7];
                    adatok[0] = pn;
                    adatok[1] = String.valueOf(qty);
                    adatok[2] = startdate;
                    adatok[3] = defaultws;
                    adatok[4] = "Nincs megadott ciklusidő SMT-n!";
                    adatok[5] = "";
                    adatok[6] = "";
                    so.getOsszegzes().add(adatok);
                    model.addRow(adatok);

                } //ha van ct
                else if (ct > 0.00) {
//ki kell számolni az össz igénybe vett időt
//a ciklusidők panel szinten vannak megadva az adatbazisban!!!!!!!!!!!!!!!!!!!!!!
                    String[] adatok = new String[7];
                    adatok[0] = pn;
                    adatok[1] = String.valueOf(qty);
                    adatok[2] = startdate;
                    adatok[3] = defaultws;
                    adatok[4] = new DecimalFormat("#.##").format(ct);
                    double idoigeny = ((ct * qty) / 60) / 60 / eff;
                    adatok[5] = String.valueOf(new DecimalFormat("#.##").format(idoigeny));
                    adatok[6] = String.valueOf(eff);

//meg kell nezni, hogy eltezik e mar ilyen adat a listben es ha igen akkor csak ossze kell adni az ertekeket es nem uj sort letrehozni
                    so.getOsszegzes().add(adatok);
                    model.addRow(adatok);

                }

            }

        }

        m.jTable5.setModel(model);
        //eltuntetjuk a spasht es a mainet megjelenitjuk
        m.s.setVisible(false);
        m.setVisible(true);

    }
}
