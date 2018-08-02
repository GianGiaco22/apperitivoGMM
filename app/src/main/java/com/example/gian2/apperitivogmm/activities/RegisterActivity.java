package com.example.gian2.apperitivogmm.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.helper.InputValidation;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

/**
 * Created by gian2 on 31/07/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity register= RegisterActivity.this;

    private Button registrati_button;
    private  EditText username;
    private  EditText nome;
    private  EditText cognome;
    private  EditText numtel;
    private Button login_link_button;
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
    }


    private void initViews(){
        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        String String_username=preferences.getString("username",null);
        String String_cognome=preferences.getString("cognome",null);
        String String_nome=preferences.getString("nome",null);
        String String_numtel=preferences.getString("numtel",null);
        login_link_button=(Button) findViewById(R.id.login_link);
        registrati_button=(Button) findViewById(R.id.registrati);
        username=(EditText) findViewById(R.id.usrnm);
        nome=(EditText) findViewById(R.id.nome);
        cognome=(EditText) findViewById(R.id.cognome);
        numtel=(EditText) findViewById(R.id.numtel);


    }

    private void initListeners(){
       login_link_button.setOnClickListener(this);
       registrati_button.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper=new DatabaseHelper(register);
        inputValidation=new InputValidation(register);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registrati:
                //visualizzo se registrazione è andata a buon fine
                postDataToSQLite();
                break;
            case R.id.login_link:
                //distruggo la activity
                finish();
                break;
        }

    }

    private void postDataToSQLite(){
        if(!inputValidation.isInputEditTextFilled(username)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per l'username !",Toast.LENGTH_LONG).show();
            return;
        }
        if(!inputValidation.isInputEditTextFilled(nome)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il nome !",Toast.LENGTH_LONG).show();
            return;
        }
        if(!inputValidation.isInputEditTextFilled(cognome)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il cognome !",Toast.LENGTH_LONG).show();
            return;
        }
        if(!inputValidation.isInputTextNumTelFilled(numtel)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido per il numero di telefono !",Toast.LENGTH_LONG).show();
            return;
        }

        if(!databaseHelper.checkCameriere(username.getText().toString().trim())){
            cameriere.setUsername(username.getText().toString().trim());
            cameriere.setNome(nome.getText().toString().trim());
            cameriere.setCognome(cognome.getText().toString().trim());
            cameriere.setNum_telefono(numtel.getText().toString().trim());

            databaseHelper.addCameriere(cameriere);
            Toast.makeText(register,"Sei stato registrato con successo",Toast.LENGTH_LONG).show();
            emptyInputEditText();

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
