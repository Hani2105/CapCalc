/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author gabor_hanacsek
 */
public class ImportFromExcel {

    JTable j;
    MainWindow m;

    public ImportFromExcel(JTable j, MainWindow m) {
        this.j = j;
        this.m = m;
        importdata();
    }

    public void importdata() {

        try {

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xlsx", "xls");
            chooser.setFileFilter(filter);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int returnVal = chooser.showOpenDialog(m);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File fi = chooser.getSelectedFile();
                FileInputStream file = new FileInputStream(fi);
                //Create Workbook instance holding reference to .xlsx file
                XSSFWorkbook workbook = new XSSFWorkbook(file);

                //Get first/desired sheet from the workbook
                XSSFSheet sheet = workbook.getSheetAt(0);
//a jtable sorait nullazzuk ki
                DefaultTableModel model = new DefaultTableModel();
                model = (DefaultTableModel) j.getModel();
                model.setRowCount(0);

                int rownum = 0;
                int colnum = 0;

//Iterate through each rows one by one
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();
                    //For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    //adjunk egy sort a modellhez
                    model.addRow(new Object[]{model.getColumnCount()});
                    colnum = 0;

                    for (int i = 0; i < row.getLastCellNum(); i++) {

                        Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                model.setValueAt(cell.getNumericCellValue(), rownum, colnum);
                                break;
                            case STRING:
                                model.setValueAt(cell.getStringCellValue(), rownum, colnum);
                                break;
                            case BLANK:
                                model.setValueAt(null, rownum, colnum);
                        }
                        colnum++;

                    }

                    rownum++;
                }
                sheet.disableLocking();
                workbook.close();
                file.close();
                j.setModel(model);
                //mentsuk el a sessionba
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //default title and icon
        JOptionPane.showMessageDialog(m,
                "Sikeres import!");

    }

}
