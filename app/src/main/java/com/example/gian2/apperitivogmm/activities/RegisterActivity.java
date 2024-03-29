package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.helper.InputValidation;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

/**
 * Scopi:
 * > registrare un nuovo profilo utente
 *
 * @authors Gianluca Giacometti, Melissa Palazzo, Marco Bonavoglia
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity register= RegisterActivity.this;

    private Button registrati_button;
    private EditText username;
    private EditText nome;
    private EditText cognome;
    private EditText numtel;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;
    private Cameriere cameriere=new Cameriere();

    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        initListeners();
        initObjects();
        getSupportActionBar().hide();
    }


    //inizializza parte grafica
    private void initViews(){
        registrati_button=(Button) findViewById(R.id.registrati);
        username=(EditText) findViewById(R.id.usrnm);
        nome=(EditText) findViewById(R.id.nome);
        cognome=(EditText) findViewById(R.id.cognome);
        numtel=(EditText) findViewById(R.id.numtel);
        numtel.setInputType(InputType.TYPE_CLASS_NUMBER);

    }


    //inizializza i listeners dei bottoni
    private void initListeners(){
       registrati_button.setOnClickListener(this);
    }

    //inizializzo gli oggetti
    private void initObjects(){
        databaseHelper=new DatabaseHelper(register);
        inputValidation=new InputValidation(register);
    }

    @Override
    public void onClick(View v) {
        if(v == registrati_button)
                //visualizzo se registrazione è andata a buon fine
                postDataToSQLite();

        }



    private void postDataToSQLite(){
        //i campi dell'iscrizione devono essere tutti compilati, altrimenti viene segnalato

        if(!inputValidation.isInputEditTextFilled(username)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per l'username!",Toast.LENGTH_LONG).show();
            return;
        }
        if(!inputValidation.isInputEditTextFilled(nome)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il nome!",Toast.LENGTH_LONG).show();
            return;
        }
        if(!inputValidation.isInputEditTextFilled(cognome)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il cognome!",Toast.LENGTH_LONG).show();
            return;
        }
        if(!inputValidation.isInputTextNumTelFilled(numtel) || numtel.length()!= 10){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il numero di telefono!",Toast.LENGTH_LONG).show();
            return;
        }


        //ultimo campo da controllare: se  stato riempito si procede la registrazione
        //inserendo nella variabile del cameriere e poi nel database il valore immesso nei campi
        if(!databaseHelper.checkCameriere(username.getText().toString().trim())){
            cameriere.setUsername(username.getText().toString().trim());
            cameriere.setNome(nome.getText().toString().trim());
            cameriere.setCognome(cognome.getText().toString().trim());
            cameriere.setNum_telefono(numtel.getText().toString().trim());

            databaseHelper.addCameriere(cameriere);
            Toast.makeText(register,"Sei stato registrato con successo",Toast.LENGTH_LONG).show();
            emptyInputEditText();


            Intent intent=new Intent(getApplicationContext(), Login.class);
            startActivity(intent);

        }else{
            Toast.makeText(register,"Errore nella registrazione",Toast.LENGTH_LONG).show();

        }

    }

    private void emptyInputEditText(){
        username.setText(null);
        nome.setText(null);
        cognome.setText(null);
        numtel.setText(null);
    }
}
