package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.CustomPietanzaAdapter;
import com.example.gian2.apperitivogmm.model.EditPietanzaModel;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by gian2 on 02/08/2018.
 */

public class OrdinaActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity ordinaActivity;
    {
        ordinaActivity = OrdinaActivity.this;
    }

    private DatabaseHelper databaseHelper;
    //tavolo scelto su cui fare l'ordine
    private int tavolo;
    //cameriere che accede alla creazione dell'ordine
    private String utente;

    //creo oggetto bottone per ordinare
    //private Button conferma;
    private Button conferma;
    //creo arrayList pietanze

    private CustomPietanzaAdapter customPietanzaAdapter;
    private ListView lvmenu ;
    private ArrayList<EditPietanzaModel> pietanzaView;


    protected void onCreate(Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordina);

        //inizializzo views
        initViews();
        //inizializzo listeners
        initListeners();
        //inizializzo oggetti
        initObjects();
        getSupportActionBar().hide();

        //creo il menu
        databaseHelper.createMenu();
        pietanzaView=get_all_dishes();
        customPietanzaAdapter=new CustomPietanzaAdapter(this,pietanzaView);
        lvmenu.setAdapter(customPietanzaAdapter);


    }

    @Override
    public void onClick(View view){
        Intent intent = new Intent(OrdinaActivity.this,ConfermaOrdineActivity.class);
        //passo username cameriere alla prossima activity
        intent.putExtra("cameriere",utente);
        //passo tavolo alla prossima activity
        intent.putExtra("tavolo",tavolo);
        startActivity(intent);

    }
    //inizializzo viste
    private void initViews(){
       lvmenu=(ListView) findViewById(R.id.menu);
       conferma=(Button) findViewById(R.id.conferma);

    }
    //inizializo listeners
    private void initListeners(){
        conferma.setOnClickListener(this);
    }
    //inizializzo oggetti
    private void initObjects(){
        tavolo=getIntent().getIntExtra("Tavolo",0);
        utente=getIntent().getStringExtra("Cameriere_usrnm").toString().trim();
        databaseHelper=new DatabaseHelper(ordinaActivity);
    }

    private ArrayList<EditPietanzaModel> get_all_dishes( ){
        //oggetto per vedere tutte le pietanze dal database
        String[] categoria={"bevanda","antipasto","primo","secondo","dolce"};
        ArrayList<EditPietanzaModel> lvmenu=new ArrayList<>();
        for (int j=0; j<categoria.length;j++){
            Cursor cursor=databaseHelper.vedi_pietanze(categoria[j]);
            if(cursor.getCount()>0){
                cursor.moveToFirst();

                String categoria_attuale="";
                TextView[] categoria_vedi=new TextView[6];
                //grafica scritte
                for(int i=0; i<cursor.getCount(); i++){
                    if(!cursor.getString(3).equals(categoria_attuale)){

                    }
                    EditPietanzaModel editPietanzaModel =new EditPietanzaModel();
                    editPietanzaModel.setPrezzo(Float.parseFloat(cursor.getString(1)));
                    editPietanzaModel.setDescrizione(cursor.getString(2));
                    editPietanzaModel.setNomePietanza(cursor.getString(0));
                    editPietanzaModel.setQuantita("");
                    lvmenu.add(editPietanzaModel);
                    //creo tale Edittext come solo edit text numerica
                    cursor.moveToNext();
                }
            }
        }
        return lvmenu;
    }










}
