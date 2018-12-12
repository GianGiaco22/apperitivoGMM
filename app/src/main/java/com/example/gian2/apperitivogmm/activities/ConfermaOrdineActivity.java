package com.example.gian2.apperitivogmm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.model.Pietanza;

import java.util.ArrayList;

public class ConfermaOrdineActivity extends AppCompatActivity {
    //textview per vedere ogni pietanza ordinata
    TextView[] textViewPietanze;
    //edittext per vedere la quantità di ogni pietanza ordinata
    EditText[] editTextQuantitaPietanza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_ordine);
        //prendo i diversi valori passati tramite intent
        Intent ordina_activity=getIntent();
        ArrayList<Pietanza> pietanze_ordinate= ordina_activity.getParcelableArrayListExtra("Pietanze_ordinate");
        textViewPietanze=new TextView[pietanze_ordinate.size()];
        editTextQuantitaPietanza=new EditText[pietanze_ordinate.size()];
        for(int i=0; i<pietanze_ordinate.size();i++){
            textViewPietanze[i]=new TextView(this);
            editTextQuantitaPietanza[i]=new EditText(this);
            textViewPietanze[i].setText(pietanze_ordinate.get(i).getNome());
            textViewPietanze[i].setTextColor(getResources().getColor(R.color.colorAccent));
            editTextQuantitaPietanza[i].setTextColor(getResources().getColor(R.color.colorAccent));
            ((LinearLayout) this.findViewById(R.id.pietanze)).addView(textViewPietanze[i]);
        }
    }

}
