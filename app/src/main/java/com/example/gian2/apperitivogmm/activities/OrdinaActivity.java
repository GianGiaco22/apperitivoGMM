package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.helper.InputValidation;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.model.Ordine;
import com.example.gian2.apperitivogmm.model.Pietanza;
import com.example.gian2.apperitivogmm.model.Tavolo;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gian2 on 02/08/2018.
 */

public class OrdinaActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity ordinaActivity;
    {
        ordinaActivity = OrdinaActivity.this;
    }
    private TextView titolo;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;
    //cameriere che segue l 'ordine
    private Cameriere cameriere=new Cameriere();
    //tavolo scelto su cui fare l'ordine
    private int tavolo;
    //cameriere che accede alla creazione dell'ordine
    private String utente;
    //text view per visualizzare tutte le pietanze
    private TextView[] pietanze;
    //editText per la quantità di pietanze scelte
    private EditText[] conta_pietanze;

    private Pietanza[] lista_pietanze;
    // pietanze memorizzate per visualizzare quali sono state scelte e in che quantità
    private ArrayList<Pietanza> pietanze_ordinate;
    private ArrayList<Integer> quantita_pietanze;
    //creo oggetto bottone per ordinare
    private Button conferma;


    protected void onCreate(Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordina);
        //inizializzo views
        initViews();
        //inizializzo listeners
        initListeners();
        //inizializzo oggetti
        initObjects();

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
        String[] categorie={"antipasto","primo","secondo","bevanda","dolce"};
        //inizializzo tutte le diverse variabili e visualizzo tutto il menu
        show_all_dishes();




    }

    @Override
    public void onClick(View view){
        //in base al bottone che clicco...
        switch(view.getId()){
            //nel caso in cui clicco il bottone con id ordina
            case R.id.ordina:
                //contatore per vedere quante pietanze diverse sono state ordinate
                int count_pietanze_ordinate=0;
                //controllo le pietanze ordinate guardando che la quantità sia !=0
                for (int i=0; i<pietanze.length; i++) {
                    //controllo che la quantità sia diversa da 0
                    if(Integer.parseInt(conta_pietanze[i].getText().toString())>0){
                        //inserisco nell array delle pietanze ordinate una pietanza ordinata

                        pietanze_ordinate.add(lista_pietanze[i]);
                        //inserisco nell'array delle quantità delle pietanze ordinate la quantità della pietanza ordinata
                        quantita_pietanze.add((Integer)Integer.parseInt(conta_pietanze[i].getText().toString()));
                        count_pietanze_ordinate++;
                    }
                }
                Toast.makeText(getApplicationContext()," "+count_pietanze_ordinate,Toast.LENGTH_LONG).show();
                //accedo alla pagina di conferma dell'ordine
                Intent conferma_ordine=new Intent(getApplicationContext(),ConfermaOrdineActivity.class);
                //passo le pietanze ordinate tramite implementazione nella classe Pietanza di Parcelable
                conferma_ordine.putParcelableArrayListExtra("Pietanze_ordinate",pietanze_ordinate);
                //invio l'username del cameriere che esegue la raccolta della lista pietanze per l'ordine
                conferma_ordine.putExtra("Cameriere",utente);
                //invio il numero del tavolo
                conferma_ordine.putExtra("Tavolo",tavolo);
                //invio l'array contenente tutte le diverse quantità delle pietanze ordinate
                conferma_ordine.putIntegerArrayListExtra("Conta_pietanze",quantita_pietanze);
                startActivity(conferma_ordine);
            break;
        }

    }
    //inizializzo viste
    private void initViews(){

       titolo=(TextView) findViewById(R.id.titolo);
       conferma=(Button) findViewById(R.id.ordina);
       tavolo=getIntent().getIntExtra("Tavolo",0);
       utente=getIntent().getStringExtra("Cameriere_usrnm").toString().trim();

    }
    //inizializo listeners
    private void initListeners(){
        conferma.setOnClickListener(this);
    }
    //inizializzo oggetti
    private void initObjects(){
        databaseHelper=new DatabaseHelper(ordinaActivity);
        inputValidation=new InputValidation(ordinaActivity);
        pietanze_ordinate=new ArrayList<Pietanza>();
        quantita_pietanze=new ArrayList<Integer>();

    }




    private void show_all_dishes( ){
        Cursor cursor=databaseHelper.vedi_pietanze();
        if(cursor.getCount()>0){
            pietanze=new TextView[cursor.getCount()];
            conta_pietanze=new EditText[cursor.getCount()];
            lista_pietanze=new Pietanza[cursor.getCount()];
            cursor.moveToFirst();
            String categoria_attuale="";
            TextView[] categoria_vedi=new TextView[6];
            //grafica scritte
            int j=0;
            for(int i=0; i<cursor.getCount(); i++){
                if(!cursor.getString(3).equals(categoria_attuale)){
                    categoria_attuale=cursor.getString(3);
                    categoria_vedi[j]=new TextView(this);
                    categoria_vedi[j].setText("\n"+categoria_attuale.toUpperCase()+"\n");
                    categoria_vedi[j].setTextColor(getResources().getColor(R.color.ic_launcher_background));
                    ((LinearLayout) this.findViewById(R.id.pietanze)).addView(categoria_vedi[j]);
                    j++;
                }
                pietanze[i]=new TextView(this);
                conta_pietanze[i]=new EditText(this);
                pietanze[i].setTextColor(getResources().getColor(R.color.colorAccent));
                conta_pietanze[i].setText("0");
                conta_pietanze[i].setTextColor(getResources().getColor(R.color.colorAccent));
                //vedo in ordine : Titolo, prezzo, descrizione
                pietanze[i].setText(cursor.getString(0)+"  €"+cursor.getString(1)+"\nDescrizione : "+cursor.getString(2)+"\n");
                //setto la lista_pietanze, una ad una
                lista_pietanze[i]=new Pietanza();
                //inserisco i diversi valori ad ogni pietanza
                lista_pietanze[i].setNome(cursor.getString(0));
                lista_pietanze[i].setPrezzo(Float.parseFloat(cursor.getString(1)));
                lista_pietanze[i].setDescrizione(cursor.getString(2));
                //creo tale Edittext come solo edit text numerica
                conta_pietanze[i].setInputType(InputType.TYPE_CLASS_NUMBER);
                //inserisci edittext per ogni pietanza per inserirne la quantità
                ((LinearLayout) this.findViewById(R.id.pietanze)).addView(conta_pietanze[i]);
                //inserisci tale textview della pietanza nel layout
                ((LinearLayout) this.findViewById(R.id.pietanze)).addView(pietanze[i]);

                cursor.moveToNext();
            }
        }else{
            Toast.makeText(ordinaActivity,"nessuna pietanza",Toast.LENGTH_SHORT).show();
        }
    }





}
