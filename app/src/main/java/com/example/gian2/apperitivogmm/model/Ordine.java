package com.example.gian2.apperitivogmm.model;

import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

/**
 * Created by gian2 on 02/08/2018.
 */

public class Ordine {

    private  int codice;
    private int tavolo;
    private String cameriere;
    private float conto;

    public Ordine() {

    }


    public int getCodice() {
        return codice;
    }


    public void setCodice(int codice) {
        this.codice = codice;
    }

    public int getTavolo() {
        return tavolo;
    }

    public void setTavolo(int tavolo) {
        this.tavolo = tavolo;
    }

    public String getCameriere() {
        return cameriere;
    }

    public void setCameriere(String cameriere) {
        this.cameriere = cameriere;
    }

    public void setConto(float conto){
        this.conto=conto;
    }

    public float getConto(){
        return conto;
    }
}
