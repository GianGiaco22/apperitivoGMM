package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.Tavolo;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

/**
 * Scopi:
 * > selezionare il tavolo per effettuare un ordine
 * > accedere allo storico degli ordini effettuati
 *
 * @authors Gianluca Giacometti, Melissa Palazzo, Marco Bonavoglia
 * @version 1.0
 */

public class CameriereActivity extends AppCompatActivity implements View.OnClickListener {
   //dichiarazione dei Button relativi ai tavoli
    private AppCompatActivity cameriere_attivita= CameriereActivity.this;
    private TextView vedi_username;
    private Button tavolo1;
    private Button tavolo2;
    private Button tavolo3;
    private Button tavolo4;
    private Button tavolo5;
    private Button tavolo6;
    private Button tavolo7;
    private Button tavolo8;
    private Button tavolo9;
    private Button tavolo10;
    private Button tavolo11;
    private Button tavolo12;

    private DatabaseHelper databaseHelper; //oggetto per le interazioni con il database
    private String usernameFromIntent; //username del cameriere corrente
    private Button vedi_ordini; //button che rimanda allo storico ordini

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        vedi_username=(TextView) findViewById(R.id.vedi);
        usernameFromIntent=getIntent().getStringExtra("USERNAME");
        vedi_username.setText("Benvenuto "+usernameFromIntent+"\nseleziona il tavolo:");

        initViews();
        initListeners();
        initObjects();

        getSupportActionBar().hide();

        for (int i=1;i<=12;i++){
            Tavolo t=new Tavolo();

            t.setNumero(i);
            databaseHelper.addTavolo(t);
        }
    }

    //inizializza parte grafica
    private void initViews(){
        vedi_username=(TextView)  findViewById(R.id.vedi);
        tavolo1=(Button) findViewById(R.id.tavolo1);
        tavolo2=(Button) findViewById(R.id.tavolo2);
        tavolo3=(Button) findViewById(R.id.tavolo3);
        tavolo4=(Button) findViewById(R.id.tavolo4);
        tavolo5=(Button) findViewById(R.id.tavolo5);
        tavolo6=(Button) findViewById(R.id.tavolo6);
        tavolo7=(Button) findViewById(R.id.tavolo7);
        tavolo8=(Button) findViewById(R.id.tavolo8);
        tavolo9=(Button) findViewById(R.id.tavolo9);
        tavolo10=(Button) findViewById(R.id.tavolo10);
        tavolo11=(Button) findViewById(R.id.tavolo11);
        tavolo12=(Button) findViewById(R.id.tavolo12);
        vedi_ordini=(Button) findViewById(R.id.vedi_ordini);
    }

    //inizializza i listeners dei bottoni
    private void initListeners(){
        tavolo1.setOnClickListener(this);
        tavolo2.setOnClickListener(this);
        tavolo3.setOnClickListener(this);
        tavolo4.setOnClickListener(this);
        tavolo5.setOnClickListener(this);
        tavolo6.setOnClickListener(this);
        tavolo7.setOnClickListener(this);
        tavolo8.setOnClickListener(this);
        tavolo9.setOnClickListener(this);
        tavolo10.setOnClickListener(this);
        tavolo11.setOnClickListener(this);
        tavolo12.setOnClickListener(this);

        vedi_ordini.setOnClickListener(this);
    }

    //inizializzo gli oggetti
    private void initObjects(){//
        databaseHelper=new DatabaseHelper(cameriere_attivita);

    }

    @Override
    public void onClick(View view){
        //gestisco i click sui possibili textEdit
        Tavolo tavolo=new Tavolo();
        switch (view.getId()){
            case R.id.tavolo1:
                tavolo.setNumero(1);
                ordina(tavolo);
                break;
            case R.id.tavolo2:
                tavolo.setNumero(2);
                ordina(tavolo);
                break;
            case R.id.tavolo3:
                tavolo.setNumero(3);
                ordina(tavolo);
                break;
            case R.id.tavolo4:
                tavolo.setNumero(4);
                ordina(tavolo);
                break;
            case R.id.tavolo5:
                tavolo.setNumero(5);
                ordina(tavolo);
                break;
            case R.id.tavolo6:
                tavolo.setNumero(6);
                ordina(tavolo);
                break;
            case R.id.tavolo7:
                tavolo.setNumero(7);
                ordina(tavolo);
                break;
            case R.id.tavolo8:
                tavolo.setNumero(8);
                ordina(tavolo);
                break;
            case R.id.tavolo9:
                tavolo.setNumero(9);
                ordina(tavolo);
                break;
            case R.id.tavolo10:
                tavolo.setNumero(10);
                ordina(tavolo);
                break;
            case R.id.tavolo11:
                tavolo.setNumero(11);
                ordina(tavolo);
                break;
            case R.id.tavolo12:
                tavolo.setNumero(12);
                ordina(tavolo);
                break;
            case R.id.vedi_ordini:
                //intent per aprire Activity che visualizza ordini di un cameriere
                Intent intent=new Intent(getApplicationContext(),VediOrdiniActivity.class);
                //invio informazioni del cameriere
                intent.putExtra("cameriere",usernameFromIntent);
                startActivity(intent);
                break;

        }
    }

    //metodo per andare ad ordinare per un determinato tavolo
    private void ordina(Tavolo tavolo){
        Intent intent=new Intent(getApplicationContext(),OrdinaActivity.class);
        //passo all'activity successiva il dato relativo al tavolo da cui ho ricevuto un ordine
        intent.putExtra("Tavolo",tavolo.getNumero());
        intent.putExtra("Cameriere_usrnm",usernameFromIntent);
        //faccio partire intent
        startActivity(intent);

    }
}
