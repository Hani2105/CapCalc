/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gabor_hanacsek
 */
public class connections {

    private String driver;
    private String url;
    private String username;
    private String password;
    public ResultSet rs;
    private Connection conn;

    public connections(String driver, String url, String username, String password) throws ClassNotFoundException {

        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        Class.forName("com.mysql.jdbc.Driver");

    }

    public Object lekerdez(String query) throws SQLException, ClassNotFoundException, Exception {

        conn = (Connection) DriverManager.getConnection(url, username, password);
        Statement st = conn.createStatement();
        rs = st.executeQuery(query);

        return rs;

    }

    public void kinyir() {

        try {
            if (conn != null) {
                this.rs.close();
                this.conn.close();
            }
        } catch (SQLException ex) {

            Logger.getLogger(CapCalc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
