/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

/**
 *
 * @author gabor_hanacsek
 */
//a befolyásoló tényezők
public class Factor {

    private int evhet;
    private double tenyezo;
    private String leiras;
    private String neve;

    public Factor(int evhet, String neve, String leiras, double tenyezo) {
        
        setEvhet(evhet);
        setNeve(neve);
        setLeiras(leiras);
        setTenyezo(tenyezo);
        
    }

    public String getNeve() {
        return neve;
    }

    public void setNeve(String neve) {
        this.neve = neve;
    }

    public int getEvhet() {
        return evhet;
    }

    public void setEvhet(int evhet) {
        this.evhet = evhet;
    }

    public double getTenyezo() {
        return tenyezo;
    }

    public void setTenyezo(double tenyezo) {
        this.tenyezo = tenyezo;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

}
