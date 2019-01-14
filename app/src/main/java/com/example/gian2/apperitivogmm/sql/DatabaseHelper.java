package com.example.gian2.apperitivogmm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.model.Ordine;
import com.example.gian2.apperitivogmm.model.Pietanza;
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
    public int addOrdine(Ordine ordine){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("tavolo",ordine.getTavolo());
        values.put("cameriere",ordine.getCameriere());


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
            int pietanza_inserita=cursor.getCount();
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




    public float conto_ordine(Ordine ordine){
        float conto_totale=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT SUM(quantita_pietanza*costo) as conto from pietanza inner join composto on nome=pietanza " +
                " where ordine="+ordine.getCodice();
        Cursor conto_parziale=db.rawQuery(query,null);
        conto_parziale.moveToFirst();
        //conto_totale+=Float.parseFloat(conto_parziale.getString(0));
        return conto_parziale.getFloat(0);

    }

    //calcolo del costo di una pietanza
    public float costo_pietanza(String pietanza, int quantita_pietanza,String modifica){
        float costo=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT costo from pietanza where nome="+pietanza;
        Cursor costo_senza_aggiunte=db.rawQuery(query,null);
        costo_senza_aggiunte.moveToFirst();
        costo=costo_senza_aggiunte.getFloat(0)*quantita_pietanza;
        if(!modifica.equals("")){
            costo+=1;
        }
        return costo;
    }


    public void createMenu(){
        Pietanza[] menu=new Pietanza[28];
        for(int i=0;i<28;i++){
            menu[i]=new Pietanza();
        }
        menu[0].setCategoria("primo");
        menu[0].setDescrizione("spaghetti con vongole veraci e prezzemolo");
        menu[0].setNome("Spaghetti con le vongole");
        menu[0].setPrezzo(9.5f);
        menu[1].setCategoria("primo");
        menu[1].setDescrizione("bigoli al ragù d anatra con formaggio grana");
        menu[1].setNome("Bigoli al ragù d'anatra");
        menu[1].setPrezzo(9.5f);
        menu[2].setCategoria("primo");
        menu[2].setDescrizione("gnocchi fatti in casa con ragù di manzo");
        menu[2].setNome("Gnocchi al ragù");
        menu[2].setPrezzo(9f);
        menu[3].setCategoria("primo");
        menu[3].setDescrizione("riso con vongole, gamberetti e pescato del giorno");
        menu[3].setNome("Riso con pesce");
        menu[3].setPrezzo(13f);
        menu[4].setCategoria("primo");
        menu[4].setDescrizione("bigoli con ragù di manzo");
        menu[4].setNome("Bigoli al ragù");
        menu[4].setPrezzo(9f);
        menu[5].setCategoria("primo");
        menu[5].setDescrizione("gnocchi con salsa di pomodoro");
        menu[5].setNome("Gnocchi al pomodoro");
        menu[5].setPrezzo(8f);
        for(int i=0;i<6;i++){
            addPietanza(menu[i]);
        }
        menu[1].setCategoria("secondo");
        menu[1].setDescrizione("tartare con verdure di stagione");
        menu[1].setNome("Tartare di verdure");
        menu[1].setPrezzo(10f);
        menu[2].setCategoria("secondo");
        menu[2].setDescrizione("pescata del giorno con contorno di patate lesse");
        menu[2].setNome("Grigliata mista di pesce");
        menu[2].setPrezzo(18f);
        menu[3].setCategoria("secondo");
        menu[3].setDescrizione("osetti,salsiccia,pancetta e polenta di contorno");
        menu[3].setNome("Grigliata mista di carne");
        menu[3].setPrezzo(16f);
        menu[4].setCategoria("secondo");
        menu[4].setDescrizione("tagliata di tonno con verdure di contorno di stagione");
        menu[4].setNome("Tagliata di tonno");
        menu[4].setPrezzo(12f);
        menu[5].setCategoria("secondo");
        menu[5].setDescrizione("tagliata di manzo con verdure di contorno di stagione");
        menu[5].setNome("Tagliata di manzo");
        menu[5].setPrezzo(10f);
        for(int i=1;i<6;i++){
            addPietanza(menu[i]);
        }

        inserisciPietanze("INSERT OR IGNORE INTO pietanza (nome,categoria,descrizione,costo)\n" +
                "VALUES "+
                "" +
                "('Tris di formaggi e affettati','antipasto','formaggi e affettati di stagione',9),\n" +
                "('Vongole saltate con crostino','antipasto','vongole pescated oggi',6),\n" +
                "('Antipasto vegetariano','antipasto','verdure di stagione',6),\n" +
                "('Burrata con salmone affumicato e rucola','antipasto','il top della casa',10),\n" +
                "('Crostata nostrana','dolce','crostata con marmellata fatta dalla casa',5),\n" +
                "('Coppa gelato','dolce','gelato alla panna e alla fragola',4),\n" +
                "('Profiteroll bianco','dolce','',4),\n" +
                "('Profitterol nero','dolce','',4),\n" +
                "('Birra bionda','bevanda','birra moretti',5),\n" +
                "('Birra Rossa','bevanda','birra moretti rossa',5),\n" +
                "('Vino rosso','bevanda','vino rosso della casa',4),\n" +
                "('Vino bianco','bevanda','vino rosso della casa',4),\n" +
                "('Acqua naturale','bevanda','bottiglia da 0.75',2.50),\n" +
                "('Acqua gassata','bevanda','bottiglia da 0.75',2.50),\n" +
                "('Caffe','bevanda','caffe classico',1.50),\n" +
                "('Grappa della casa','bevanda','grappa fatta in casa',3);\n");
    }

    }







