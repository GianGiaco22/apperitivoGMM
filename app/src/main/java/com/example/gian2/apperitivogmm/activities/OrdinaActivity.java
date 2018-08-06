package com.example.gian2.apperitivogmm.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.helper.InputValidation;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

/**
 * Created by gian2 on 02/08/2018.
 */

public class OrdinaActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity ordinaActivity= OrdinaActivity.this;
    private TextView titolo;
    private Button antipasti;
    private Button primi;
    private Button secondi;
    private Button dolci;
    private Button visualizza_ordine;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;
    private Cameriere cameriere=new Cameriere();


    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordina);
        int tavolo=getIntent().getIntExtra("Tavolo",0);
        initListeners();
        initObjects();
        initViews();
        titolo.setText("Ordine al tavolo "+tavolo);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){

        }
    }

    private void initViews(){

       titolo=(TextView) findViewById(R.id.titolo);
       antipasti=(Button) findViewById(R.id.antipasti);
       primi=(Button) findViewById(R.id.primi);
       secondi=(Button) findViewById((R.id.secondi));
       dolci=(Button) findViewById(R.id.dolci);
       visualizza_ordine=(Button) findViewById(R.id.visualizza_ordine);


    }

    private void initListeners(){

    }

    private void initObjects(){
        databaseHelper=new DatabaseHelper(ordinaActivity);
        inputValidation=new InputValidation(ordinaActivity);
    }

}
