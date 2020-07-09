/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gabor_hanacsek
 */
public class WsRenderer extends DefaultTableCellRenderer {

    WorkStation ws;

    public WsRenderer(WorkStation ws) {

        this.ws = ws;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        int initialDelay = ToolTipManager.sharedInstance().getInitialDelay();

        // Show tool tips immediately
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(9999999);
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //alapbol legyen null a background color
        c.setBackground(null);
        c.setForeground(null);
        //a tooltip legyen nulla
        c.setToolTipText(null);

        //a sum row szinezese ha sum nagyobb mint a ws oraszama
        if (row == 0 && column > 0) {

            for (int i = 0; i < ws.getWeekList().size(); i++) {

                if (ws.getWeekList().get(i).getWeekname().equals(table.getColumnName(column))) {

                    if (ws.getWeekList().get(i).getOraszam() < Double.parseDouble(table.getValueAt(row, column).toString())) {

                        c.setForeground(Color.red);

                    }

                }

            }

        }
        //a tooltiptext beallítasa

        if (row > 0 && column > 0 && table.getValueAt(row, column) != null) {
            String tooltiptext = "<html>";
            //ki kell talalni, hogy az adott cella adata miből áll össze
            //ki kell találni a hetet
            for (int i = 0; i < ws.getWeekList().size(); i++) {
//ha a hét száma egyezik a column nevével továbbmegyünk
                if (ws.getWeekList().get(i).getWeekname().equals(table.getColumnName(column))) {
//bepörgetjük az adatait és ha egyezik a prefix a sor elejen szereplovel hozzaadjuk a tooltip texthez
                    for (int n = 0; n < ws.getWeekList().get(i).getGyartasok().size(); n++) {
                        if (ws.getWeekList().get(i).getGyartasok().get(n)[0].substring(0, 5).equals(table.getValueAt(row, 0).toString())) {
                            String gyartas = "";
                            try {
                                gyartas = new DecimalFormat("#.##").format(Double.parseDouble(ws.getWeekList().get(i).getGyartasok().get(n)[5]));
                            } catch (Exception e) {

                            }
                            tooltiptext += "<span style=\"color:red;\">PN: </span>" + ws.getWeekList().get(i).getGyartasok().get(n)[0] + " <span style=\"color:red;\">Demand: </span>" + ws.getWeekList().get(i).getGyartasok().get(n)[1] + " <span style=\"color:red;\">CT: </span>" + ws.getWeekList().get(i).getGyartasok().get(n)[4] + " <span style=\"color:red;\">Gyártási idő: </span>" + gyartas + "<br>";

                        }
                    }

                }

            }

            c.setToolTipText(tooltiptext);

        }

        return c;

    }

}
