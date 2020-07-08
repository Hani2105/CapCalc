/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capcalc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author gabor_hanacsek
 */
public class SessionObject implements Serializable {

    //a demand eleresi utja
    private String demandLastDir = "";
    //az smt ciklusidők
    private ArrayList<String[]> smtcycletime = new ArrayList<>();
    //a backend ciklusidok
    private ArrayList<String[]> backendcycletime = new ArrayList<>();
    //a wocreation adatok
    private ArrayList<String[]> demandList = new ArrayList<>();
    //a kapcsolat adatok
    private ArrayList<String[]> kapcsolatlist = new ArrayList<>();
    //az osszegzett adatok
    private ArrayList<String[]> osszegzes = new ArrayList<>();

    public ArrayList<String[]> getOsszegzes() {
        return osszegzes;
    }

    public void setOsszegzes(ArrayList<String[]> osszegzes) {
        this.osszegzes = osszegzes;
    }

    public ArrayList<String[]> getKapcsolatList() {
        return kapcsolatlist;
    }

    public void setKapcsolatList(ArrayList<String[]> kapcsolatList) {
        this.kapcsolatlist = kapcsolatList;
    }

    public ArrayList<String[]> getDemandList() {
        return demandList;
    }

    public void setDemandList(ArrayList<String[]> demandlist) {
        this.demandList = demandlist;
    }

    public ArrayList<String[]> getSmtcycletime() {
        return smtcycletime;
    }

    public void setSmtcycletime(ArrayList<String[]> smtcycletime) {
        this.smtcycletime = smtcycletime;
    }

    public ArrayList<String[]> getBackendcycletime() {
        return backendcycletime;
    }

    public void setBackendcycletime(ArrayList<String[]> backendcycletime) {
        this.backendcycletime = backendcycletime;
    }

    public String getDemandLastDir() {
        return demandLastDir;
    }

    public void setDemandLastDir(String demandLastDir) {
        this.demandLastDir = demandLastDir;
    }

}
