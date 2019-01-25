package com.example.gian2.apperitivogmm.model;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gian2.apperitivogmm.R;

import java.util.ArrayList;

public class CustomPietanzaAdapter extends BaseAdapter {

    //affinchè tale array possa essere utilizzato in qualsiasi altra classe
   public static ArrayList<EditPietanzaModel> pietanze;
   private Context context;


   //costruttore
   public CustomPietanzaAdapter(Context context,ArrayList<EditPietanzaModel> pietanze){
        this.pietanze=pietanze;
        this.context=context;
   }

   //metodi da dovre sovrascrivere
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return pietanze.size();
    }

    @Override
    public Object getItem(int position) {
        return pietanze.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pietanza, null, true);

            holder.editTextQuantita = (EditText) convertView.findViewById(R.id.quantita);
            holder.textViewNome=(TextView) convertView.findViewById(R.id.nome);
            holder.textViewPrezzo=(TextView) convertView.findViewById(R.id.prezzo);
            holder.textViewDescrizione=(TextView) convertView.findViewById(R.id.descrizione);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.editTextQuantita.setText(""+pietanze.get(position).getQuantita());
        holder.textViewPrezzo.setText(""+pietanze.get(position).getPrezzo());
        holder.textViewNome.setText(pietanze.get(position).getNomePietanza());
        holder.textViewDescrizione.setText(pietanze.get(position).getDescrizione());

        holder.editTextQuantita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pietanze.get(position).setQuantita(holder.editTextQuantita.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return convertView;
    }

    private class ViewHolder {

        protected EditText editTextQuantita;
        protected TextView textViewNome;
        protected TextView textViewPrezzo;
        protected TextView textViewDescrizione;

    }

}

