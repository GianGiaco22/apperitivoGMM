package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.helper.InputValidation;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.model.Ordine;
import com.example.gian2.apperitivogmm.model.Pietanza;
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
    private Ordine ordine;
    private int tavolo;
    private String utente;


    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordina);
        initListeners();
        initObjects();
        initViews();
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
            databaseHelper.addPietanza(menu[i]);
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
            databaseHelper.addPietanza(menu[i]);
        }
        titolo.setText("Ordine al tavolo "+tavolo+" dal cameriere "+utente);
        ordine.setCodice(databaseHelper.cercaUltimoCodiceOrdine()+1);
        databaseHelper.inserisciPietanze("INSERT OR IGNORE INTO pietanza (nome,categoria,descrizione,costo)\n" +
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

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.antipasti:

        }
    }

    private void initViews(){

       titolo=(TextView) findViewById(R.id.titolo);
       antipasti=(Button) findViewById(R.id.antipasti);
       primi=(Button) findViewById(R.id.primi);
       secondi=(Button) findViewById((R.id.secondi));
       dolci=(Button) findViewById(R.id.dolci);
       visualizza_ordine=(Button) findViewById(R.id.visualizza_ordine);
       tavolo=getIntent().getIntExtra("Tavolo",0);
       utente=getIntent().getStringExtra("Cameriere_usrnm").toString().trim();
       ordine=new Ordine();
       ordine.setCameriere(getIntent().getStringExtra("Cameriere_usrnm").toString().trim());
       ordine.setTavolo(tavolo);

    }

    private void initListeners(){

    }

    private void initObjects(){
        databaseHelper=new DatabaseHelper(ordinaActivity);
        inputValidation=new InputValidation(ordinaActivity);
    }




}