/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author gabor_hanacsek
 */
public class SessionKezelo {

    public SessionKezelo() {

    }

    public void sessionIr(SessionObject so) throws FileNotFoundException, IOException {
        //könyvtár létrehozása
        new File(System.getProperty("user.home") + "\\CapCalc").mkdir();
        FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\CapCalc\\session.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(so);
        oos.close();
        fos.close();
    }

    public SessionObject sessionOlvas() {
       SessionObject so = null;
        try {
            FileInputStream file = new FileInputStream(System.getProperty("user.home") + "\\CapCalc\\session.dat");
            ObjectInputStream in = new ObjectInputStream(file);
            so = (SessionObject) in.readObject();
        } catch (Exception e) {
            System.out.println("Hiba a session beolvasásánál!");

        }
        return so;
    }

}
