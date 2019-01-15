package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.CustomPietanzaOrdinataAdapter;
import com.example.gian2.apperitivogmm.model.EditPietanzaOrdinataModel;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by gian2 on 14/01/2019.
 */

public class ContoActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHelper databaseHelper;
    //textView per vedere il conto
    private TextView contoTextView;
    //conto totale
    private TextView info_ordine;
    private float conto;
    //codice dell'ordine relativo
    private int ordine;
    //tavolo ordine
    private int tavolo;
    //cameriere responsabile
    private String cameriere;
    Button torna_tavolo;
    LinearLayout linearlayout;


    protected void onCreate(Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conto);

        //inizializzo views
        initViews();
        //inizializzo listeners
        initListeners();
        //inizializzo oggetti
        initObjects();
        info_ordine.setText("Ordine "+ordine+" dal cameriere "+cameriere+" al tavolo "+tavolo);
        crea_scontrino();




    }


    private void initViews(){
    contoTextView=(TextView) findViewById(R.id.conto);
    info_ordine=(TextView) findViewById(R.id.info);
    //ottengo conto e tavolo dalla precedente activity
    conto=getIntent().getFloatExtra("conto",0);
    ordine=getIntent().getIntExtra("ordine",0);
    tavolo=getIntent().getIntExtra("tavolo",0);
    torna_tavolo=(Button) findViewById(R.id.torna);
    cameriere=getIntent().getStringExtra("Cameriere_usrnm");
    //visualizzo il conto sulla textview
    contoTextView.setText("Conto totale : "+conto);
    linearlayout=(LinearLayout) findViewById(R.id.ordini);

    }
    private void initListeners(){
    torna_tavolo.setOnClickListener(this);

    }


    private void initObjects(){
        databaseHelper=new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onClick(View view){
        Intent intent=new Intent(ContoActivity.this,CameriereActivity.class);
        intent.putExtra("USERNAME",cameriere);
        startActivity(intent);
    }



    private void crea_scontrino(){
        TextView[] pietanzaOrdinataTextView =new TextView[CustomPietanzaOrdinataAdapter.pietanzeOrdinate.size()];
        for(int i=0; i<CustomPietanzaOrdinataAdapter.pietanzeOrdinate.size(); i++){
            pietanzaOrdinataTextView[i]=new TextView(this);
            pietanzaOrdinataTextView[i].setText(CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getQuantita()+"x"+CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getNomePietanza()
            +"    "+CustomPietanzaOrdinataAdapter.pietanzeOrdinate.get(i).getCosto());
            pietanzaOrdinataTextView[i].setTextColor(Color.rgb(255,255,255));
            pietanzaOrdinataTextView[i].setId(i);
            pietanzaOrdinataTextView[i].setGravity(Gravity.CENTER);
            linearlayout.addView(pietanzaOrdinataTextView[i]);


        }
    }
}
