package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.CustomPietanzaOrdinataAdapter;
import com.example.gian2.apperitivogmm.model.EditPietanzaOrdinataModel;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by gian2 on 14/01/2019.
 */

public class ContoActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHelper databaseHelper;
    //textView per vedere il conto
    private TextView contoTextView;
    //conto totale
    private float conto;
    //codice dell'ordine relativo
    private int ordine;
    //cameriere responsabile
    private String cameriere;
    Button torna_tavolo;


    protected void onCreate(Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conto);

        //inizializzo views
        initViews();
        //inizializzo listeners
        initListeners();
        //inizializzo oggetti
        initObjects();




    }


    private void initViews(){
    contoTextView=(TextView) findViewById(R.id.conto);
    //ottengo conto e tavolo dalla precedente activity
    conto=getIntent().getFloatExtra("conto",0);
    ordine=getIntent().getIntExtra("ordine",0);
    torna_tavolo=(Button) findViewById(R.id.torna);
    cameriere=getIntent().getStringExtra("Cameriere_usrnm");
    //visualizzo il conto sulla textview
    contoTextView.setText("Conto totale : "+conto);


    }
    private void initListeners(){
    torna_tavolo.setOnClickListener(this);

    }


    private void initObjects(){
        databaseHelper=new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onClick(View view){
        Intent intent=new Intent(ContoActivity.this,CameriereActivity.class);
        intent.putExtra("USERNAME",cameriere);
        startActivity(intent);
    }
}
