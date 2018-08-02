package com.example.gian2.apperitivogmm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;

import org.w3c.dom.Text;

/**
 * Created by gian2 on 31/07/2018.
 */

public class UserActivity extends AppCompatActivity {

    private TextView vedi_username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        vedi_username=(TextView) findViewById(R.id.vedi);
        String usernameFromIntent=getIntent().getStringExtra("USERNAME");
        vedi_username.setText(" Benvenuto "+usernameFromIntent);

    }
}
