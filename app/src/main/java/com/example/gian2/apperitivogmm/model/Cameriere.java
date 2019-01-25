package com.example.gian2.apperitivogmm.model;

public class Cameriere {

    private String username;
    private String nome;
    private String cognome;
    private String num_telefono;



    //getter e setter
    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getNum_telefono() {
        return num_telefono;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNum_telefono(String num_telefono) {
        this.num_telefono = num_telefono;
    }
}

