package com.example.gian2.apperitivogmm.model;

/**
 * Created by gian2 on 17/12/2018.
 */

public class EditPietanzaModel {
    private String quantita;
    private String nomePietanza;
    private float prezzo;
    private String descrizione;

    public String getQuantita() {
        return quantita;
    }

    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }

    public String getNomePietanza() {
        return nomePietanza;
    }

    public void setNomePietanza(String nomePietanza) {
        this.nomePietanza = nomePietanza;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
