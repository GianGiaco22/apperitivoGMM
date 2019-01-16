package com.example.gian2.apperitivogmm.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;
import com.example.gian2.apperitivogmm.sql.DatabaseHelper;

public class VediOrdiniActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedi_ordini);
    }

    @Override
    public void onClick(View view){

    }

    private void InitViews(){

    }
    private void InitListeners(){

    }
    private void initObjects(){
        databaseHelper=new DatabaseHelper(this);
    }

}
