package com.example.gian2.apperitivogmm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gian2 on 02/08/2018.
 */

public class Pietanza implements Parcelable {

   private String nome ;
   private String descrizione;
   private float prezzo;
   private String categoria;

   //costruttore
   public Pietanza(){

   }
    //metodi da sovrascrivere
   @Override
   public int describeContents(){
       return 0;
   }
    //metodo per scrivere all'interno dell'oggetto Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nome);
        out.writeString(descrizione);
        out.writeFloat(prezzo);
        out.writeString(categoria);
    }

    //metodo per la creazione di un oggettto Parcelable relativo ad una Pietanza
    public static final Parcelable.Creator<Pietanza> CREATOR
            = new Parcelable.Creator<Pietanza>() {
       //restituisco un oggetto Pietanza dall'oggetto Parcelable
        public Pietanza createFromParcel(Parcel in) {
            return new Pietanza(in);
        }
        //restiutisco un array di oggetti pietanza da un array di oggetti parcelable
        @Override
        public Pietanza[] newArray(int size) {
            return new Pietanza[size];
        }
    };

   //ottengo i valori degli attributi della pietanza dall'oggetto parcelable
    //devo tenere lo stesso ordine degli inserimenti dei valori in writeToParcel
    public Pietanza(Parcel in) {
        nome = in.readString();
        descrizione=in.readString();
        prezzo=in.readFloat();
        categoria=in.readString();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
