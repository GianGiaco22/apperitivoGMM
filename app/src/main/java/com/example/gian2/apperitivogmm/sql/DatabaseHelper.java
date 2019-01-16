package com.example.gian2.apperitivogmm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.model.Ordine;
import com.example.gian2.apperitivogmm.model.Tavolo;



/**
 * Created by gian2 on 31/07/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1 ;
    private static final String DATABASE_NAME="appgmm.db";
    //mai usato
    private static final String DATABASE_USER="provatea_gm";

    //dati utenti iscritti
    private static final String COLUMN_USERNAME="username";
    private static final String TABLE_CAMERIERE="Cameriere";
    private static final String COlUMN_NOME="nome";
    private static final String COlUMN_COGNOME="cognome";
    private static final String COlUMN_NUM_TELEFONO="num_telefono";

    //creazione del cameriere con i dati inseriti
    private  String CREATE_CAMERIERE_TABLE="CREATE TABLE if not exists  "+TABLE_CAMERIERE+"("+
            COLUMN_USERNAME+" varchar(100) not null primary key, "+COlUMN_NOME+" varchar(50) not null, "+
            COlUMN_COGNOME+" varchar(50) not null, "+COlUMN_NUM_TELEFONO+" varchar(10) not null)";



    //inserimento tavoli
    private String CREATE_TABLE_TAVOLO="CREATE TABLE if not exists tavolo(" +
            "  numero int not null primary key" +
            ")";

    //ordine completo
    private String CREATE_TABLE_ORDINE="CREATE TABLE if not exists ordine(\n" +
            "  codice int auto_increment  primary key,\n" +
            "  tavolo int references tavolo(numero)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  cameriere not null references cameriere(username)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "   conto float\n "+
            ")";

    //inserimrnto della pietanza per ogni ordine
    private String CREATE_TABLE_COMPOSTO="create table if not exists composto(\n" +
            "  pietanza varchar(50) not null references pietanza(nome)\n" +
            "  on delete no action\n" +
            "  on update cascade,\n" +
            "  ordine int not null references ordine(codice)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  quantita_pietanza int not null,\n " +
            "  modifica varchar(100),\n"+
            "  primary key(pietanza,ordine)\n" +
            ")";





    //nome della pietanza del menù con costo e descrizione
    private String CREATE_TABLE_PIETANZA="create table if not exists pietanza(\n" +
            "  nome varchar(50) not null primary key,\n" +
            "  categoria varchar(50) not null,\n" +
            "  costo float not null,\n" +
            "  descrizione varchar(200) not null\n" +
            ")";







    @Override
    public void onCreate(SQLiteDatabase db){
    //creo tutte le tabelle del database
        db.execSQL(CREATE_CAMERIERE_TABLE);
        db.execSQL(CREATE_TABLE_PIETANZA);
        db.execSQL(CREATE_TABLE_TAVOLO);
        db.execSQL(CREATE_TABLE_ORDINE);
        db.execSQL(CREATE_TABLE_COMPOSTO);


    }

    private String DROP_TABLE="";
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //gestisce il database
    public DatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    //aggiungo cameriere con i suoi dati alla tabella
    public void addCameriere(Cameriere cameriere){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_USERNAME,cameriere.getUsername());
        values.put(COlUMN_NOME,cameriere.getNome());
        values.put(COlUMN_COGNOME,cameriere.getCognome());
        values.put(COlUMN_NUM_TELEFONO,cameriere.getNum_telefono());

        db.insert(TABLE_CAMERIERE,null,values);
    }

    //ricerco un cameriere
    public boolean checkCameriere(String username){
        //query di ricerca per username
        String[] columns={
                COLUMN_USERNAME
        };
        SQLiteDatabase db=this.getWritableDatabase();
        String selection=COLUMN_USERNAME+" = ?";
        //
        String[] selectionArgs={ username };
        Cursor cursor=db.query(TABLE_CAMERIERE,columns,selection,selectionArgs,null,null,null);
        //conto i camerieri trovati
        int camerieri_trovati=cursor.getCount();
        cursor.close();
        //se ho trovato il cameriere cercato
        if(camerieri_trovati>0){
            return true;
        }
        return false;
    }

    //inserisco pietanza tramite una stringa SQL
    public void inserisciPietanze(String pietanze){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(pietanze);
    }

    //inserisco un ordine
    public int addOrdine(Ordine ordine){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("tavolo",ordine.getTavolo());
        values.put("cameriere",ordine.getCameriere());
        values.put("conto",ordine.getConto());


       return  (int)db.insert("ordine",null,values);


    }



    //aggiungo un tavolo
    public void addTavolo(Tavolo tavolo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String[] columns={
                "numero"
        };
        String selection="numero = ?";
        String numero=""+tavolo.getNumero();
        String[] selectionArgs={numero};
        Cursor cursor=db.query("tavolo",columns,selection,selectionArgs,null,null,null);

        //se non è inserito il numero del tavolo lo aggiungo
        if(cursor.getCount()==0){
            values.put("numero",tavolo.getNumero());
            db.insert("tavolo",null,values);
        }

    }

    //aggiungo pietanza ad un ordine
    public int addComposto(Ordine ordine,String pietanza,int quantita,String modifica){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("ordine",ordine.getCodice());
        values.put("pietanza",pietanza);
        values.put("quantita_pietanza",quantita);
        values.put("modifica",modifica);
       return (int) db.insert("composto",null,values);
    }



    //visualizza le pietanze in base alla tipologia
    public Cursor vedi_pietanze(String categoria){
        String[] columns={
                "nome",
                "costo",
                "descrizione",
                "categoria"
        };

        SQLiteDatabase db=this.getReadableDatabase();
        String selection="categoria= ?";
        String[] selectionArgs={
                categoria
        };

        Cursor cursor=db.query("pietanza",columns,selection,selectionArgs,
                null,null,"nome,costo");
        return cursor;
    }



    //metodo per vedere tutte le pietanze relative ad un ordine
    public Cursor vedi_pietanze_ordine(int codiceOrdine){
        String[] columns={
                "pietanza",
                "quantita",
                "modifica"
        };
        SQLiteDatabase db=this.getReadableDatabase();
        String selection="ordine= ?";
        String[] selectionArgs={
                codiceOrdine+""
        };
        Cursor cursor=db.query("composto",columns,selection,selectionArgs,null,null,null);
        return cursor;
    }


    public void createMenu(){

        inserisciPietanze("INSERT OR IGNORE INTO pietanza (nome,categoria,descrizione,costo)\n" +
                "VALUES "+
                "" +
                "('Spaghetti alle vongole', 'primo', 'Spaghetti con vongole veraci, prezzemolo, un filo di olio', 9.5),\n" +
                "('Bigoli con anatra', 'primo', 'Bigoli al ragù di anatra con scaglie di formaggio grana', 9.5),\n" +
                "('Gnocchi al ragù', 'primo', 'Gnocchi di patate fatti in casa con ragù di manzo', 9),\n" +
                "('Risotto di pesce', 'primo', 'Risotto con vongole, gamberetti e pescato del giorno', 13),\n" +
                "('Tagliatelle al ragù', 'primo', 'Tagliatelle fatte in casa con ragù di manzo', 9),\n" +
                "('Gnocchi al pomodoro', 'primo', 'Gnocchi di patate fatti in casa con passata di pomodoro', 8),\n" +
                "('Tartare di verdure', 'secondo', 'Tartare con verdure di stagione', 10),\n" +
                "('Grigliata mista di pesce', 'secondo', 'Grigliata di pesce fresco con patate al forno', 18),\n" +
                "('Grigliata mista di carne', 'secondo', 'Ossetti, salsiccia, pancetta e polenta', 16),\n" +
                "('Tagliata di tonno', 'secondo', 'Tagliata di tonno con verdure di stagione', 12),\n" +
                "('Tagliata di manzo', 'secondo', 'Tagliata di manzo con verdure di stagione', 10),\n" +
                "('Misto di formaggi e affettati', 'antipasto', 'Formaggi stagionati, ricotta e affettati freschi della casa', 9),\n" +
                "('Vongole in crostino', 'antipasto', 'Vongole fresche di giornata con crostini', 6),\n" +
                "('Antipasto vegetariano', 'antipasto', 'Mix di verdure cotte e crude di stagione', 6),\n" +
                "('Crostini al salmone', 'antipasto', 'Crostini con burro e salmone selvaggio affumicato', 10),\n" +
                "('Crostata', 'dolce', 'Crostata con marmellata alle fragole fatta in casa', 5),\n" +
                "('Coppa gelato', 'dolce', 'Coppa con gelato alla panna e al cioccolato', 4),\n" +
                "('Profiteroles bianco', 'dolce', 'Profiteroles al cioccolato bianco', 4),\n" +
                "('Profiteroles nero', 'dolce', 'Profiteroles al cioccolato al latte', 4),\n" +
                "('Birra bionda', 'bevanda', 'Birra Moretti bionda da 0.5 l', 5),\n" +
                "('Birra rossa', 'bevanda', 'Birra Moretti rossa da 0.5 l', 5),\n" +
                "('Vino rosso', 'bevanda', 'Bottiglia da 0.75 l di Cabernet franc', 4),\n" +
                "('Vino bianco', 'bevanda', 'Bottiglia da 0.75 l di Verduzzo', 4),\n" +
                "('Acqua naturale', 'bevanda', 'Bottiglia da 0.75 l', 2.50),\n" +
                "('Acqua gassata', 'bevanda', 'Bottiglia da 0.75 l', 2.50),\n" +
                "('Caffè', 'bevanda', 'Caffè espresso', 1.50),\n" +
                "('Grappa', 'bevanda', 'Grappa fatta in casa', 3)\n"
        );
    }

    }







