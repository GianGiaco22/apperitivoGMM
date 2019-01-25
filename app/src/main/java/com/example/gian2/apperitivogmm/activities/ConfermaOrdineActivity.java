package com.example.gian2.apperitivogmm.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.CustomPietanzaAdapter;
import com.example.gian2.apperitivogmm.model.CustomPietanzaOrdinataAdapter;
import com.example.gian2.apperitivogmm.model.EditPietanzaOrdinataModel;
import java.util.ArrayList;

/**
 * Scopi:
 * > visualizzare il riepilogo dell'rdine prima della conferma
 * > aggiungere eventuali modifiche
 *
 * @authors Gianluca Giacometti, Melissa Palazzo, Marco Bonavoglia
 * @version 1.0
 */
public class ConfermaOrdineActivity extends AppCompatActivity implements View.OnClickListener {

    //tavolo possibile ordine
    private int tavolo;
    //cameriere possibile ordine
    private String cameriere;
    //button per inviare ordine all'activity ContoOrdine
    private Button invia_ordine;
    //gestore per visualizzare pietanze ordinate
    private CustomPietanzaOrdinataAdapter customPietanzaOrdinataAdapter;
    //componente grafico che contiente la lista delle pietanze ordinate
    private ListView listaPietanzaOrdinate ;
    //array di componenti grafici relativi alle pietanze ordinate modificabili
    private ArrayList<EditPietanzaOrdinataModel> pietanzaView;


    protected void onCreate(Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_ordine);
        cameriere=getIntent().getStringExtra("cameriere").toString().trim();
        tavolo=getIntent().getIntExtra("tavolo",0);
        //inizializzo views
        initViews();
        //inizializzo listeners
        initListeners();
        //inizializzo oggetti
        initObjects();

        //ottengo lista componenti grafici relativi a pietanze ordinate
        pietanzaView=getPietanzeOrdinate();
        //inizializzo Adapter per inserire tutte le diverse pietanze ordinate nella ListView
        customPietanzaOrdinataAdapter=new CustomPietanzaOrdinataAdapter(this,pietanzaView);
        //inserisco la lista di pietanze
        try{
            listaPietanzaOrdinate.setAdapter(customPietanzaOrdinataAdapter);
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Ordine vuoto!!",Toast.LENGTH_LONG).show();
            finish();
        }

    }


    //inizializza parte grafica
    private void initViews(){
        invia_ordine=(Button) findViewById(R.id.conferma);
        listaPietanzaOrdinate=(ListView) findViewById(R.id.ordine_completo);
    }

    //inizializza i listeners dei bottoni
    private void initListeners(){
        invia_ordine.setOnClickListener(this);
    }


    //inizializzo gli oggetti
    private void initObjects(){

    }

    @Override
    public void onClick(View view){
        //creo conti senza modifiche e delle modifiche
        //per ogni modifica, aggiungo 1 euro al conto
        float conto_senza_modifiche=0;
        float conto_modifiche=0;
        for(int i=0; i<pietanzaView.size();i++){
            //sommo i diversi conti parziali dati dal costo unitario della pietanza per la quantità della stessa ordinata
             conto_senza_modifiche+=pietanzaView.get(i).getCosto()*pietanzaView.get(i).getQuantita();
             //se EditText modifica non è vuota
             if(!pietanzaView.get(i).getModifica().equals("")){
                 //allora ho fatto una modifica e quindi il costo solo delle modifiche viene aumentato di 1 euro
                 conto_modifiche++;
             }
        }
       //creo intent per passare a ContoActivity
        Intent intent=new Intent(ConfermaOrdineActivity.this, ContoActivity.class);
        //passo i due conti alla prossima Activity
        intent.putExtra("conto_modifiche",conto_modifiche);
        intent.putExtra("conto_senza_modifiche",conto_senza_modifiche);
        //passo codice  ordine alla prossima activity
        intent.putExtra("Cameriere_usrnm",cameriere);
        intent.putExtra("tavolo",tavolo);
        startActivity(intent);
    }




    //metodo per ottenere tutte le pietanze ordinate con la quantità relativa
    private ArrayList<EditPietanzaOrdinataModel> getPietanzeOrdinate(){
        //array che passerò con con tutte le pietanze aventi quantità diversa da 0
        ArrayList<EditPietanzaOrdinataModel> editPietanzaOrdinataModelArrayList =new ArrayList<>();
        if(CustomPietanzaAdapter.pietanze.size()!=0){
            for(int i = 0; i< CustomPietanzaAdapter.pietanze.size(); i++){
                //se la quantità della pietanza della lista del menu è maggiore di 0
                if(!CustomPietanzaAdapter.pietanze.get(i).getQuantita().equals("")) {
                    //creo oggetto per creare una pietanza ordinata a cui posso aggiungere modifiche
                    EditPietanzaOrdinataModel editPietanzaOrdinataModel = new EditPietanzaOrdinataModel();
                    editPietanzaOrdinataModel.setCosto(CustomPietanzaAdapter.pietanze.get(i).getPrezzo());
                    editPietanzaOrdinataModel.setNomePietanza(CustomPietanzaAdapter.pietanze.get(i).getNomePietanza());
                    editPietanzaOrdinataModel.setQuantita(Integer.parseInt(CustomPietanzaAdapter.pietanze.get(i).getQuantita()));
                    editPietanzaOrdinataModel.setModifica("");
                    //aggiungo tale oggetto all'array di oggetti da passare
                    editPietanzaOrdinataModelArrayList.add(editPietanzaOrdinataModel);
                }
            }
        }

    //ritorno la lista di oggetti modificabili legati alle pietanze ordinate modificabili
        return editPietanzaOrdinataModelArrayList;
    }
}














