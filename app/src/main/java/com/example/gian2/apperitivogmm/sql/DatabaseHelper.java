package com.example.gian2.apperitivogmm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gian2.apperitivogmm.model.Cameriere;

/**
 * Created by gian2 on 31/07/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1 ;
    private static final String DATABASE_NAME="appgmm.db";
    private static final String DATABASE_USER="provatea_gm";
    private static final String COLUMN_USERNAME="username";
    private static final String TABLE_CAMERIERE="Cameriere";
    private static final String COlUMN_NOME="nome";
    private static final String COlUMN_COGNOME="cognome";
    private static final String COlUMN_NUM_TELEFONO="num_telefono";
    private  String CREATE_CAMERIERE_TABLE="CREATE TABLE "+TABLE_CAMERIERE+"("+
            COLUMN_USERNAME+" varchar(100) not null primary key, "+COlUMN_NOME+" varchar(50) not null, "+
            COlUMN_COGNOME+" varchar(50) not null, "+COlUMN_NUM_TELEFONO+" varchar(10) not null)";

    private String CREATE_TABLE_INGREDIENTE="CREATE TABLE ingrediente(" +
            "  nome varchar(50) not null primary key);" +
            ")";

    private String CREATE_TABLE_TAVOLO="CREATE TABLE tavolo(" +
            "  numero int not null primary key" +
            ")";

    private String CREATE_TABLE_ORDINE="CREATE TABLE ordine(\n" +
            "  codice int not null primary key,\n" +
            "  tavolo int references tavolo(numero)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  cameriere not null references cameriere(username)\n" +
            "  on update cascade\n" +
            "  on delete no action\n" +
            ")";

    private String CREATE_TABLE_COMPOSTO="create table composto(\n" +
            "  pietanza_ordinata int  references pietanza_ordinata(codice)\n" +
            "  on delete no action\n" +
            "  on update cascade,\n" +
            "  ordine int not null references ordine(codice)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  primary key(pietanza_ordinata,ordine)\n" +
            ")";
    private String CREATE_TABLE_CREA="create table crea(\n" +
            "  pietanza varchar(50)  references pietanza(nome)\n" +
            "  on delete no action\n" +
            "  on update cascade,\n" +
            "  ingrediente varchar(50) not null references ingrediente(nome)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  primary key(pietanza,ingrediente),\n" +
            "  quantita int not null\n" +
            ")";

    private String CREATE_TABLE_PIETANZA="create table pietanza(\n" +
            "  nome varchar(50) not null primary key,\n" +
            "  categoria varchar(50) not null,\n" +
            "  costo float not null,\n" +
            "  descrizione varchar(200) not null\n" +
            ")";

    private String CREATE_TABLE_PIETANZA_ORDINATA="create table pietanza_ordinata(\n" +
            "  codice int not null primary key,\n" +
            "  pietanza varchar(50) references pietanza(nome)\n" +
            "  on update cascade\n" +
            "  on delete cascade\n" +
            ")";

    private String CREATE_TABLE_AGGIUNTO="create table aggiunto( \n" +
            "  ingrediente varchar(50) not null references ingrediente(nome)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  pietanza_ordinata int not null references pietanza_ordinata(codice)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  costo float not null,\n" +
            "  primary key(ingrediente,pietanza_ordinata)\n" +

            ")";

    private String DROP_TABLE="DROP IF EXISTS "+TABLE_CAMERIERE;

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

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public DatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    //aggiungo cameriere alla tabella
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
        //costruzione query di ricerca per username
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
}