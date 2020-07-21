/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author gabor_hanacsek
 */
public class ExportToExcel {

    JTable j;

    public ExportToExcel(JTable j) {
        this.j = j;
        export();

    }

    public void export() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        //kiszedjuk a modellt
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) j.getModel();
        int rowcount = 0;
        int columncount = 0;
//bejarjuk soronkent es oszloponkent a modellt
        for (int r = 0; r < model.getRowCount(); r++) {
//minden sorban csinálunk egy új sort
            Row row = sheet.createRow(++rowcount);
//kinullázzuk a columnokat
            columncount = 0;
            for (int c = 0; c < model.getColumnCount(); c++) {
                //minden columnnál csinálunk egy új cellát
                Cell cell = row.createCell(++columncount);
                //megvizsgáljuk, hogy a modellben szereplő adat string vagy szám és annak megfelelően írjuk ki
                try {

                    cell.setCellValue(Double.parseDouble(model.getValueAt(r, c).toString()));

                } catch (Exception ex) {
                    try {
                        cell.setCellValue(model.getValueAt(r, c).toString());
                    } catch (Exception e) {
                    }
                }

            }

        }

        //kitesszük a kívánt helyre
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try (FileOutputStream outputStream = new FileOutputStream(chooser.getSelectedFile() + ".xlsx")) {
                workbook.write(outputStream);
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
