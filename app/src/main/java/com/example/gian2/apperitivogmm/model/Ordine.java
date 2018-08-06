package com.example.gian2.apperitivogmm.model;

/**
 * Created by gian2 on 02/08/2018.
 */

public class Ordine {

    private int codice;
    private int tavolo;
    private Cameriere cameriere;

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

    public Cameriere getCameriere() {
        return cameriere;
    }

    public void setCameriere(Cameriere cameriere) {
        this.cameriere = cameriere;
    }
}
