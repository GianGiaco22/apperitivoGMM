package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

public class VediOrdiniActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    //cameriere di cui visualizzerò l'ordine
    private Cameriere cameriere;
    private Button vedi_ordini;
    private   LinearLayout linearLayoutVediordini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedi_ordini);
        InitViews();
        InitListeners();
        InitObjects();
        cameriere.setUsername(getIntent().getStringExtra("cameriere"));
        visualizzaOrdini();

    }

    @Override
    public void onClick(View view){
        Intent intent=new Intent(getApplicationContext(),CameriereActivity.class);
        intent.putExtra("USERNAME",cameriere.getUsername());
        startActivity(intent);
    }

    private void InitViews(){
        linearLayoutVediordini=(LinearLayout) findViewById(R.id.vedi_ordini);
        vedi_ordini=(Button) findViewById(R.id.conferma);

    }
    private void InitListeners(){
        vedi_ordini.setOnClickListener(this);
    }
    private void InitObjects(){
        databaseHelper=new DatabaseHelper(this);
        cameriere=new Cameriere();
        cameriere.setUsername(getIntent().getStringExtra("cameriere"));
    }


    private void visualizzaOrdini(){
        //ottengo il cursore con tutte le tuple degli ordini
        Cursor ordiniCameriere=databaseHelper.ordini_cameriere(cameriere);
        ordiniCameriere.moveToFirst();
        do{
            //creo layout relativo ad un'ordine
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(18,18,18,18);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            //visualizzo info dell 'ordine in una textView
            TextView info_ordine=new TextView(this);
            info_ordine.setGravity(Gravity.CENTER);
            info_ordine.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
            info_ordine.setTextSize(20);
            info_ordine.setTextColor(Color.WHITE);
            info_ordine.setWidth(300);
            info_ordine.setHeight(100);
            info_ordine.setText("Ordine "+ordiniCameriere.getInt(0)+ " con conto €"+ordiniCameriere.getFloat(1));
            linearLayoutVediordini.addView(linearLayout);
            linearLayout.addView(info_ordine);
            //vedo tuple con codice dell'ordine
            Cursor pietanza_ordine=databaseHelper.vedi_pietanze_ordine(ordiniCameriere.getInt(0));
            while(pietanza_ordine.moveToNext()){
                //visualizzo pietanze realtive all'ordine atraverso un edit text
                TextView pietanzaordinataTextView=new TextView(this);
                pietanzaordinataTextView.setGravity(Gravity.CENTER);
                pietanzaordinataTextView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                pietanzaordinataTextView.setText(pietanza_ordine.getInt(1)+" x "+pietanza_ordine.getString(0)+
                        ((!pietanza_ordine.getString(2).equals("") ? "\n--> "+pietanza_ordine.getString(2):"")));
                pietanzaordinataTextView.setPadding(0,0,0,1);
                pietanzaordinataTextView.setTextSize(17);
                linearLayout.addView(pietanzaordinataTextView);

            }
            pietanza_ordine.close();
        }while(ordiniCameriere.moveToNext());
        ordiniCameriere.close();

    }

}
