package com.example.gian2.apperitivogmm.activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.Cameriere;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

public class VediOrdiniActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    //cameriere di cui visualizzerò l'ordine
    private Cameriere cameriere;
    private   LinearLayout linearLayoutVediordini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedi_ordini);
        InitViews();
        InitListeners();
        InitObjects();
        visualizzaOrdini();

    }

    @Override
    public void onClick(View view){

    }

    private void InitViews(){
        linearLayoutVediordini=(LinearLayout) findViewById(R.id.vedi_ordini);

    }
    private void InitListeners(){

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
        while(ordiniCameriere.moveToNext()){
            //creo layout relativo ad un'ordine
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(18,0,18,18);
            linearLayout.setBackgroundColor(Color.BLACK);
            //visualizzo info dell 'ordine in una textView
            TextView info_ordine=new TextView(this);
            info_ordine.setTextColor(Color.WHITE);
            info_ordine.setGravity(Gravity.CENTER);
            info_ordine.setTextSize(20);
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
                pietanzaordinataTextView.setText(pietanza_ordine.getString(0)+" x "+pietanza_ordine.getInt(1)+"\n"+pietanza_ordine.getString(2));
                pietanzaordinataTextView.setGravity(Gravity.CENTER);
                pietanzaordinataTextView.setTextSize(15);
                pietanzaordinataTextView.setTextColor(Color.WHITE);
                pietanzaordinataTextView.setPadding(0,5,0,5);
                linearLayout.addView(pietanzaordinataTextView);

            }
            pietanza_ordine.close();
        }
        ordiniCameriere.close();

    }

}
