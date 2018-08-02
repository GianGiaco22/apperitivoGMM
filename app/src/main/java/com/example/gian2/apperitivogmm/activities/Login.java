package com.example.gian2.apperitivogmm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.helper.InputValidation;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity;

    {
        activity = Login.this;
    }

    private Button aggiungi_profilo;
    private Button accesso;
    private EditText username;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();





    }

    //inizializzo parti del form
    private void initViews(){
        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        aggiungi_profilo=(Button) findViewById(R.id.crea);
        accesso=(Button) findViewById(R.id.accedi);
        username=(EditText) findViewById(R.id.username);
        String String_usrnm=preferences.getString("username",null);
    }
    //inizializzo bottoni
    private void initListeners(){
        accesso.setOnClickListener(this);
        aggiungi_profilo.setOnClickListener(this);
    }
    //inizializzo oggetti
    private void initObjects(){
        databaseHelper=new DatabaseHelper(activity);
        inputValidation=new InputValidation(activity);

    }



    @SuppressLint("CommitPrefEdits")
    @Override

    public void onPause() {
        super.onPause();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        EditText usn = (EditText) findViewById(R.id.username);
        String string_usrnm = usn.getText().toString();

        editor.putString("username", string_usrnm);
        editor.commit();

    }

    @Override
    public void onClick(View view) {
        //gestione casi click in uno dei due bottoni
        switch(view.getId()){
            //click sul bottone di accesso all'app
            case R.id.accedi:
                verifyFromSQLite();
                break;
            //click sul bottone di registrazione per l'utilizzo dell'app
            case R.id.crea:
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    //verifica del login
    public void verifyFromSQLite(){
        //se ho l'username vuoto, allora non eseguo
        if(!inputValidation.isInputEditTextFilled(username)){
            Toast.makeText(getApplicationContext(),"Inserisci un valore valido !",Toast.LENGTH_LONG).show();
            return;
        }
        //se l'username inserito è esatto
        if(databaseHelper.checkCameriere(username.getText().toString().trim())){
            //accedo alla pagina di gestione
            Intent accedi_area=new Intent(activity,UserActivity.class);
            //passo a tale pagina il valore dell'username
            accedi_area.putExtra("USERNAME",username.getText().toString().trim());

            username.setText(null);
            startActivity(accedi_area);
            //altrimenti
        }else{
            //avviso l'utente di non aver inserito un username non valido
            Toast.makeText(activity, "Errore, cameriere non iscritto", Toast.LENGTH_SHORT).show();
        }
    }


}
    /*
    public void login(){
        //utilizzo libreria volley
        //vado a richiedere utilizzo del file login.php , attraverso una richiesta HTTP
        StringRequest request=new StringRequest(Request.Method.POST, "http://www.proveteamgl.it/apperitivo/GMM/login.php",new Response.Listener<String>(){
            @Override
            //in caso ottenga una risposta dal server
            public void onResponse(String response){
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                //se ottengo risposta contenente  "successo" --> login eseguito correttamente
                if(response.contains("successo")){
                    //apro activity di gestione degli ordini
                    startActivity(new Intent(getApplicationContext(),GestioneOrdini.class));
                }
            }
            //in caso di risposta errata
        },new Response.ErrorListener(){
            @Override
            //output della risposta errata
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }



        ){
            @Override
            //creo mappa con parametri passati dal file login.php col metodo POST
            protected Map<String,String> getParams() throws AuthFailureError{
                //creo oggetto tipo mappa
                Map<String,String> params=new HashMap<>();
                //inserisco chiave e valore nell'array di tipo Map
                params.put("username",username.getText().toString());
                return params;
            }
        };
        //eseguo l'oggetto StringRequest request
        Volley.newRequestQueue(this).add(request);
    }

    */
