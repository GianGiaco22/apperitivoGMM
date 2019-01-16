package com.example.gian2.apperitivogmm.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.CustomPietanzaAdapter;
import com.example.gian2.apperitivogmm.model.CustomPietanzaOrdinataAdapter;
import com.example.gian2.apperitivogmm.model.EditPietanzaModel;
import com.example.gian2.apperitivogmm.model.EditPietanzaOrdinataModel;
import com.example.gian2.apperitivogmm.model.Ordine;
import com.example.gian2.apperitivogmm.model.Pietanza;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;
import java.util.ArrayList;

import static com.example.gian2.apperitivogmm.model.CustomPietanzaAdapter.pietanze;

public class ConfermaOrdineActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    private int tavolo;
    private String cameriere;
    private Button invia_ordine;
    private TextView info_ordine;

    private CustomPietanzaOrdinataAdapter customPietanzaOrdinataAdapter;
    private ListView listaPietanzaOrdinate ;
    private ArrayList<EditPietanzaOrdinataModel> pietanzaView;
    //ordine da creare
    private Ordine ordine;


    protected void onCreate(Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_ordine);

        //inizializzo views
        initViews();
        //inizializzo listeners
        initListeners();
        //inizializzo oggetti
        initObjects();

        //creo il menu
        databaseHelper.createMenu();
        pietanzaView=getPietanzeOrdinate();
        customPietanzaOrdinataAdapter=new CustomPietanzaOrdinataAdapter(this,pietanzaView);
        listaPietanzaOrdinate.setAdapter(customPietanzaOrdinataAdapter);


    }


    private void initViews(){
        invia_ordine=(Button) findViewById(R.id.conferma);
        listaPietanzaOrdinate=(ListView) findViewById(R.id.ordine_completo);

        cameriere=getIntent().getStringExtra("cameriere").toString().trim();
        tavolo=getIntent().getIntExtra("tavolo",0);



    }
    private void initListeners(){
        invia_ordine.setOnClickListener(this);
    }


    private void initObjects(){
        databaseHelper=new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onClick(View view){
        float conto_senza_modifiche=0;
        float conto_modifiche=0;
        for(int i=0; i<pietanzaView.size();i++){
             conto_senza_modifiche+=pietanzaView.get(i).getCosto()*pietanzaView.get(i).getQuantita();
             if(!pietanzaView.get(i).getModifica().equals("")){
                 conto_modifiche++;
             }
        }
       //creo intent per passare a ContoActivity
        Intent intent=new Intent(ConfermaOrdineActivity.this,ContoActivity.class);
        //passo i due conti alla prossima Activity
        intent.putExtra("conto_modifiche",conto_modifiche);
        intent.putExtra("conto_senza_modifiche",conto_senza_modifiche);
        //passo codice  ordine alla prossima activity
        intent.putExtra("ordine",ordine.getCodice());
        intent.putExtra("Cameriere_usrnm",cameriere);
        intent.putExtra("tavolo",tavolo);
        startActivity(intent);
    }





    private ArrayList<EditPietanzaOrdinataModel> getPietanzeOrdinate(){
        ArrayList<EditPietanzaOrdinataModel> editPietanzaOrdinataModelArrayList=new ArrayList<>();
        for(int i = 0; i< CustomPietanzaAdapter.pietanze.size(); i++){
            if(Integer.parseInt(CustomPietanzaAdapter.pietanze.get(i).getQuantita())!=0) {
                EditPietanzaOrdinataModel editPietanzaOrdinataModel = new EditPietanzaOrdinataModel();
                editPietanzaOrdinataModel.setCosto(CustomPietanzaAdapter.pietanze.get(i).getPrezzo());
                editPietanzaOrdinataModel.setNomePietanza(CustomPietanzaAdapter.pietanze.get(i).getNomePietanza());
                editPietanzaOrdinataModel.setQuantita(Integer.parseInt(CustomPietanzaAdapter.pietanze.get(i).getQuantita()));
                editPietanzaOrdinataModel.setModifica("");
                editPietanzaOrdinataModelArrayList.add(editPietanzaOrdinataModel);
            }
        }

        return editPietanzaOrdinataModelArrayList;
    }
}














