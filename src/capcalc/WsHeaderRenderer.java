/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gabor_hanacsek
 */
public class WsHeaderRenderer extends DefaultTableCellRenderer {

    WorkStation w;

    public WsHeaderRenderer(WorkStation w) {
        this.w = w;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setToolTipText(null);

        String tooltiptext = "<html>";
        for (int i = 0; i < w.getWeekList().size(); i++) {

            //ha megegyezik a hét neve a column nevével akkor beállítjuk tooltipnek a faktorokat és a hét óráját
            if (w.getWeekList().get(i).getWeekname().equals(table.getColumnName(column))) {
                tooltiptext += "<span style=\"color:orange;\">A héten elérhető órák száma: </span>" + w.getWeekList().get(i).getOraszam() + " óra<br>";
                for (int n = 0; n < w.getWeekList().get(i).getTenyezoList().size(); n++) {

                    tooltiptext += "<span style=\"color:orange;\">Neve: </span>" + w.getWeekList().get(i).getTenyezoList().get(n).getNeve() + "<span style=\"color:orange;\"> Leírása: </span>" + w.getWeekList().get(i).getTenyezoList().get(n).getLeiras() + "<span style=\"color:orange;\"> Hatékonyság: </span>" + w.getWeekList().get(i).getTenyezoList().get(n).getTenyezo() + "%<br>";

                }
                //a tárazási idő hozzáadása
                tooltiptext += "<span style=\"color:orange;\">Tárazási idő: </span>" + new DecimalFormat("#.##").format(w.getWeekList().get(i).calcTarazasiido()) + " óra<br>";
                //a mernoki ido hozzaadasa
                tooltiptext += "<span style=\"color:orange;\">Mérnöki idő: </span>" + new DecimalFormat("#.##").format(w.getWeekList().get(i).getMernokiido()) + " óra<br>";
                //a gyártási idő kiírása
                tooltiptext += "<span style=\"color:orange;\">Gyártási idő: </span>" + new DecimalFormat("#.##").format(w.getWeekList().get(i).getSumgyartasiido()) + " óra<br>";
            }

        }
        c.setFont(new Font(Font.DIALOG, Font.BOLD, 11));
        c.setToolTipText(tooltiptext);
        c.setHorizontalAlignment(JLabel.CENTER);
        return c;

    }
}
