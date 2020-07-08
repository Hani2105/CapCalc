/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author gabor_hanacsek
 */
public class Filechooser {

    private static String lastDir = null;

    public static JFileChooser getFileChooser() {
        if (!MainWindow.so.getDemandLastDir().equals("")) {
            JFileChooser fc = new JFileChooser(MainWindow.so.getDemandLastDir());
            fc.setFont(new java.awt.Font("Segoe Script", 1, 12));
            return fc;
        } else {
            JFileChooser fc = new JFileChooser();
            fc.setFont(new java.awt.Font("Segoe Script", 1, 12));
          
            return fc;
        }
    }

  

}
