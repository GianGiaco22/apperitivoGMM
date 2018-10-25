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
import com.example.gian2.apperitivogmm.model.Tavolo;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

/**
 * Created by gian2 on 02/08/2018.
 */

public class OrdinaActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity ordinaActivity= OrdinaActivity.this;
    private TextView titolo;
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
        for (int i=1;i<=12;i++){
            Tavolo t=new Tavolo();
            t.setNumero(i);
            databaseHelper.addTavolo(t);
        }
        Pietanza[] menu=new Pietanza[28];
        for(int i=0;i<28;i++){
            menu[i]=new Pietanza();
        }
        menu[0].setCategoria("primo");
        menu[0].setDescrizione("Spaghetti con vongole veraci, prezzemolo, un filo di olio");
        menu[0].setNome("Spaghetti alle vongole");
        menu[0].setPrezzo(9.5f);
        menu[1].setCategoria("primo");
        menu[1].setDescrizione("Bigoli al ragù d'anatra con formaggio grana");
        menu[1].setNome("Bigoli all'anatra");
        menu[1].setPrezzo(9.5f);
        menu[2].setCategoria("primo");
        menu[2].setDescrizione("Gnocchi di patate fatti in casa con ragù di manzo");
        menu[2].setNome("Gnocchi al ragù");
        menu[2].setPrezzo(9f);
        menu[3].setCategoria("primo");
        menu[3].setDescrizione("Riso con vongole, gamberetti e pescato del giorno");
        menu[3].setNome("Risotto di pesce");
        menu[3].setPrezzo(13f);
        menu[4].setCategoria("primo");
        menu[4].setDescrizione("Bigoli con ragù di manzo");
        menu[4].setNome("Bigoli al ragù");
        menu[4].setPrezzo(9f);
        menu[5].setCategoria("primo");
        menu[5].setDescrizione("Gnocchi con passata di pomodoro");
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
        menu[2].setDescrizione("Grigliata di pesce fresco con patate lesse");
        menu[2].setNome("Grigliata mista di pesce");
        menu[2].setPrezzo(18f);
        menu[3].setCategoria("secondo");
        menu[3].setDescrizione("Ossetti,salsiccia,pancetta e polenta");
        menu[3].setNome("Grigliata mista di carne");
        menu[3].setPrezzo(16f);
        menu[4].setCategoria("secondo");
        menu[4].setDescrizione("Tagliata di tonno con verdure di stagione");
        menu[4].setNome("Tagliata di tonno");
        menu[4].setPrezzo(12f);
        menu[5].setCategoria("secondo");
        menu[5].setDescrizione("Tagliata di manzo con verdure di stagione");
        menu[5].setNome("Tagliata di manzo");
        menu[5].setPrezzo(10f);
        for(int i=1;i<6;i++){
            databaseHelper.addPietanza(menu[i]);
        }
        titolo.setText("Ordine: tavolo "+tavolo+", cameriere "+utente);
        ordine.setCodice(databaseHelper.cercaUltimoCodiceOrdine()+1);
        databaseHelper.inserisciPietanze("INSERT OR IGNORE INTO pietanza (nome,categoria,descrizione,costo)\n" +
                "VALUES "+
                "" +
                "('Misto di formaggi e affettati','antipasto','Formaggi stagionati, ricotta e affettati freschi della casa',9),\n" +
                "('Vongole saltate con crostino','antipasto','vongole pescated oggi',6),\n" +
                "('Antipasto vegetariano','antipasto','Mix di verdure cotte e crude di stagione',6),\n" +
                "('Crostini al salmone','antipasto','Crostini con burro e salmone selvaggio affuicato',10),\n" +
                "('Crostata','dolce','Crostata con marmellata fatta in casa',5),\n" +
                "('Coppa gelato','dolce','Coppa con gelato alla panna e alla fragola',4),\n" +
                "('Profiteroles bianco','dolce','Profiteroles al cioccolato bianco',4),\n" +
                "('Profiteroles nero','dolce','Profiteroles al cioccolato al latte',4),\n" +
                "('Birra bionda','bevanda','Birra Moretti bionda, 0.5 l',5),\n" +
                "('Birra Rossa','bevanda','Birra Moretti rossa, 0.5 l',5),\n" +
                "('Vino rosso','bevanda','Cabernet franc, 0.5 l',4),\n" +
                "('Vino bianco','bevanda','Verduzzo, 0.5 l',4),\n" +
                "('Acqua naturale','bevanda','Bottiglia, 0.75 l',2.50),\n" +
                "('Acqua gassata','bevanda','Bottiglia, 0.75 l',2.50),\n" +
                "('Caffè','bevanda','Caffè espresso',1.50),\n" +
                "('Grappa','bevanda','Grappa fatta in casa',3);\n");
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){


        }
    }

    private void initViews(){

       titolo=(TextView) findViewById(R.id.titolo);


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
