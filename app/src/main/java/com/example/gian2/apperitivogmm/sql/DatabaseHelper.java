package com.example.gian2.apperitivogmm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.model.Ingrediente;
import com.example.gian2.apperitivogmm.model.Ordine;
import com.example.gian2.apperitivogmm.model.Pietanza;
import com.example.gian2.apperitivogmm.model.Pietanza_Ordinata;
import com.example.gian2.apperitivogmm.model.Tavolo;

import java.util.Map;
import java.util.Set;

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

    //lista ingredienti
    private String CREATE_TABLE_INGREDIENTE="CREATE TABLE if not exists ingrediente(" +
            "  nome varchar(50) not null primary key);" +
            ")";

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
            "  on delete no action\n" +
            ")";

    //inserimrnto della pietanza per ogni ordine
    private String CREATE_TABLE_COMPOSTO="create table if not exists composto(\n" +
            "  pietanza_ordinata int  references pietanza_ordinata(codice)\n" +
            "  on delete no action\n" +
            "  on update cascade,\n" +
            "  ordine int not null references ordine(codice)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  quantita_pietanza int not null,\n " +
            "  primary key(pietanza_ordinata,ordine)\n" +
            ")";

    //ingredienti da cui è formata la pietanza
    private String CREATE_TABLE_CREA="create table if not exists crea(\n" +
            "  pietanza varchar(50)  references pietanza(nome)\n" +
            "  on delete no action\n" +
            "  on update cascade,\n" +
            "  ingrediente varchar(50) not null references ingrediente(nome)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  quantita integer not null,\n" +
            "  primary key(pietanza,ingrediente)\n" +

            ")";

    //nome della pietanza del menù con costo e descrizione
    private String CREATE_TABLE_PIETANZA="create table if not exists pietanza(\n" +
            "  nome varchar(50) not null primary key,\n" +
            "  categoria varchar(50) not null,\n" +
            "  costo float not null,\n" +
            "  descrizione varchar(200) not null\n" +
            ")";

    //asscocia la pietanza scelta al codice dell'ordine
    private String CREATE_TABLE_PIETANZA_ORDINATA="create table if not exists pietanza_ordinata(\n" +
            "  codice int not null primary key,\n" +
            "  pietanza varchar(50) references pietanza(nome)\n" +
            "  on update cascade\n" +
            "  on delete cascade\n" +
            ")";

    //dati dell'aggiunta di un ingrediente per la pietanza ordinata
    private String CREATE_TABLE_AGGIUNTO="create table if not exists aggiunto( \n" +
            "  ingrediente varchar(50) not null references ingrediente(nome)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  pietanza_ordinata int not null references pietanza_ordinata(codice)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  costo float not null,\n" +
            "  primary key(ingrediente,pietanza_ordinata)\n" +

            ")";



    @Override
    public void onCreate(SQLiteDatabase db){
    //creo tutte le tabelle del database
        db.execSQL(CREATE_CAMERIERE_TABLE);
        db.execSQL(CREATE_TABLE_PIETANZA);
        db.execSQL(CREATE_TABLE_TAVOLO);
        db.execSQL(CREATE_TABLE_ORDINE);
        db.execSQL(CREATE_TABLE_INGREDIENTE);
        db.execSQL(CREATE_TABLE_COMPOSTO);
        db.execSQL(CREATE_TABLE_CREA);
        db.execSQL(CREATE_TABLE_AGGIUNTO);
        db.execSQL(CREATE_TABLE_PIETANZA_ORDINATA);


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


    //inserisco una pietanza campo per campo
    public void addPietanza(Pietanza pietanza){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //controllo di non aver già inserito tale pietanza
        String[] columns={
                "nome"
        };
        String selection="nome = ?";
        String[] selectionArgs={ pietanza.getNome() };
        Cursor cursor=db.query("pietanza",columns,selection,selectionArgs,null,null,null);
        int pietanza_inserita=cursor.getCount();

        //se non ho nessuna pietanza con tale nome, la inserisco
        if(pietanza_inserita==0){
            values.put("nome",pietanza.getNome());
            values.put("descrizione",pietanza.getDescrizione());
            values.put("costo",pietanza.getPrezzo());
            values.put("categoria",pietanza.getCategoria());
            db.insert("pietanza",null,values);
        }

    }

    //inserisco pietanza tramite una stringa SQL
    public void inserisciPietanze(String pietanze){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(pietanze);
    }

    //inserisco un ordine
    public long addOrdine(Ordine ordine){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("tavolo",ordine.getTavolo());
        values.put("cameriere",ordine.getCameriere());


       return  db.insert("ordine",null,values);


    }

    //inserisco nuovo ingrediente
    public void addIngrediente(Ingrediente ingrediente){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("nome",ingrediente.getNome());
        db.insert("ingrediente",null,values);
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
            int pietanza_inserita=cursor.getCount();
            values.put("numero",tavolo.getNumero());
            db.insert("tavolo",null,values);
        }

    }

    //aggiungo pietanza ad un ordine
    public void addComposto(Ordine ordine,Pietanza_Ordinata pietanza_ordinata){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("ordine",ordine.getCodice());
        values.put("pietanza_ordinata",pietanza_ordinata.getCodice());
        db.insert("composto",null,values);
    }

    //aggiungo pietanza_ordinata (che può avere ingredienti aggiuntivi)
    public void addPietanzaOrdinata(Pietanza pietanza,int codice){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("codice",codice);
        values.put("pietanza",pietanza.getNome());
        db.insert("pietanza_ordinata",null,values);
    }

    //aggiungo record a tabella crea (ingredienti usati per una pietanza)
    public void addCrea(Pietanza pietanza,Ingrediente ingrediente,int quantita){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("pietanza",pietanza.getNome());
        values.put("ingrediente",ingrediente.getNome());
        values.put("quantita",ingrediente.getNome());
        db.insert("crea",null,values);
    }

    //inserisco la possibile aggiunta che una persona ha fatto nella sua pietanza ordinata
    public void addAggiunto(Pietanza_Ordinata pietanza_ordinata,Ingrediente ingrediente,int costo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("pietanza_ordinata",pietanza_ordinata.getCodice());
        values.put("ingrediente",ingrediente.getNome());
        values.put("costo",costo);
        db.insert("aggiunto",null,values);
    }


    //controllo se un certo ordine esiste
    public boolean checkOrdine(int codice){
        String[] columns={
                "codice"
        };
        SQLiteDatabase db=this.getWritableDatabase();
        String selection="codice"+" = ?";
        String[] selectionArgs={ ""+codice };
        Cursor cursor=db.query("ordine",columns,selection,selectionArgs,null,null,null);
        //conto i camerieri trovati
        int ordini_trovati=cursor.getCount();
        cursor.close();
        //se ho trovato il cameriere cercato
        if(ordini_trovati>0){
            return true;
        }
        return false;

    }

    //ricerco ultimo codice dell'ordine inserito
    public int cercaUltimoCodiceOrdine(){
        String[] columns={
                "MAX(codice) AS max_codice"
        };
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("ordine",columns,null,null,null,null,null);

        if(cursor.moveToFirst()){
            int max_codice=cursor.getInt((int)cursor.getColumnIndex("max_codice"));
            cursor.close();
            return max_codice;
        }
        cursor.close();
        //in caso  non esista nessun ordine inserito, il codice del primo è 1
        return 1;

    }

    //ottengo i piatti di una determinata categoria
    /*public Pietanza[] ottieniPiattiTipologia(String[] categoria) {
        Pietanza[] piatti = new Pietanza[10];
        String[] columns = {
                "nome",
                "costo",
                "descrizione",
                "categoria"


        };
        String selection = "categoria" + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("pietanza", columns, selection, categoria, null, null, "nome");
        if (cursor.moveToFirst()) {
            int i = 0;
            while (!cursor.isAfterLast()) {
                piatti[i] = new Pietanza();

            }
        }
        }*/


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
        String selectionArgs[]={categoria};
        Cursor cursor=db.query("pietanza",columns,selection,selectionArgs,null,null,null);
        return cursor;
    }

    }







