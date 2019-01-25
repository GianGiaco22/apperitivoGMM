package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.CustomPietanzaOrdinataAdapter;
import com.example.gian2.apperitivogmm.model.Ordine;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

/**
 * Scopi:
 * > visualizzare il conto senza modifiche
 * > visualizzare il conto delle sole modifiche
 * > visualizzare il conto totale
 * > confermare l'ordine per poi tornare alla selezione di un tavolo per un nuovo ordine
 * *
 * @authors Gianluca Giacometti, Melissa Palazzo, Marco Bonavoglia
 * @version 1.0
 */

public class ContoActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHelper databaseHelper;
    //textView per vedere il conto senza modifiche
    private TextView conto_senza_modificheTextView;
    //textView per vedere il conto totale
    private TextView conto_totaleTextView;
    //textView per vedere il conto delle modifiche
    private TextView conto_modificheTextView;
    //conto totale
    private TextView info_ordine;
    //conto senza modifiche
    private float conto_senza_modifiche;
    //conto delle modifiche
    private float conto_modifiche;
    //conto totale
    private float conto_totale;
   //tavolo dell' ordine
    private int tavolo;
    //cameriere responsabile
    private String cameriere;
    //buton per tornare alla scelta dei tavoli post completamento ordine
    private Button torna_tavolo;



    protected void onCreate(Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conto);
        //ottengo dati passati via Intent
        //ottengo conti tavolo,cameriere dalla precedente activity ConfermaOrdineActivity
        conto_senza_modifiche=getIntent().getFloatExtra("conto_senza_modifiche",0);
        conto_modifiche=getIntent().getFloatExtra("conto_modifiche",0);
        tavolo=getIntent().getIntExtra("tavolo",0);
        cameriere=getIntent().getStringExtra("Cameriere_usrnm");
        conto_totale=conto_modifiche+conto_senza_modifiche;
        //inizializzo views
        initViews();
        //inizializzo listeners
        initListeners();
        //inizializzo oggetti
        initObjects();
        getSupportActionBar().hide();
        //inserisco stringa per info dell'ordine
        info_ordine.setText("Ordine  del cameriere "+cameriere+" \nal tavolo "+tavolo);
        conto_modificheTextView.setText("Importo modifiche: € "+conto_modifiche);
        conto_senza_modificheTextView.setText("Importo parziale: € "+conto_senza_modifiche);
        conto_totaleTextView.setText("Importo totale : € "+conto_totale);

    }


    //inizializza parte grafica
    private void initViews(){
        //inizializzo tutti i componenti del Layout
         conto_totaleTextView=(TextView) findViewById(R.id.conto_totale);
         conto_senza_modificheTextView=(TextView) findViewById(R.id.conto_senza_modifiche);
         conto_modificheTextView=(TextView) findViewById(R.id.conto_modifiche);
         info_ordine=(TextView) findViewById(R.id.info);
         torna_tavolo=(Button) findViewById(R.id.torna);
    }

    //inizializza i listeners dei bottoni
    private void initListeners(){
        //assoccio listener al Button
    torna_tavolo.setOnClickListener(this);

    }

    //inizializzo gli oggetti
    private void initObjects(){
        databaseHelper=new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onClick(View view){
        //creo ordine e lo inserisco nel database
        Ordine ordine=new Ordine();
        ordine.setTavolo(tavolo);
        ordine.setCameriere(cameriere);
        ordine.setConto(conto_totale);
        /*
        **inserisco l'ordine nel database e tale operazione mi restituisce il codice dell'ordine
        **essendo che l'attributo codice dell'ordine è un auto_increment
        */
        ordine.setCodice(databaseHelper.addOrdine(ordine));
        //inserisco ogni pietanza ordinata nella tabella del database composto
        for(int i=0;i<CustomPietanzaOrdinataAdapter.pietanzeOrdinate.size();i++){
            //ottengo i diversi attributi della pietanza ordinata di indice i
            String pietanza=CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getNomePietanza();
            int quantita=CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getQuantita();
            String modifica=CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getModifica();
            //aggiungo tale pietanza ordinata, la quantita, e le possibili modifiche al database nella tabella composto
            databaseHelper.addComposto(ordine,pietanza,quantita,modifica);
        }
        Intent intent=new Intent(ContoActivity.this,CameriereActivity.class);
        intent.putExtra("USERNAME",cameriere);
        startActivity(intent);
    }

}
