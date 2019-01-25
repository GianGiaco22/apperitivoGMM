package com.example.gian2.apperitivogmm.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Scopi:
 * > controllare la validità dei dati immessi
 *
 * @authors Gianluca Giacometti, Melissa Palazzo, Marco Bonavoglia
 * @version 1.0
 */

public class InputValidation {

    private Context context;

    public InputValidation (Context context){
        this.context=context;
    }
    //metodo per controllo dati inseriti non nulli
    public boolean isInputEditTextFilled(EditText testo){
        String value=testo.getText().toString();
        if(value.isEmpty()){
            hideKeyboardFrom(testo);
            return false;
        }
        return true;
    }
    //controllo che numero telefonico abbia un formato esatto
    public boolean isInputTextNumTelFilled(EditText num_tel){
        String value=num_tel.getText().toString();
        if((value.isEmpty()) && value.length()!=10){
            hideKeyboardFrom(num_tel);
            return false;
        }
        return true;
    }

    //metodo per nascondere tastiera da EditText

    private void hideKeyboardFrom(View view){
        InputMethodManager imm=(InputMethodManager)  context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



}
