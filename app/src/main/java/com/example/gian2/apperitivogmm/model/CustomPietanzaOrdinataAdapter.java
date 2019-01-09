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

/**
 * Created by gian2 on 09/01/2019.
 */

public class CustomPietanzaOrdinataAdapter extends BaseAdapter {
    public static ArrayList<EditPietanzaOrdinataModel> pietanzeOrdinate;
    private Context context;


    public CustomPietanzaOrdinataAdapter(Context context,ArrayList<EditPietanzaOrdinataModel> pietanzeOrdinate){
       this.pietanzeOrdinate=pietanzeOrdinate;
        this.context=context;
    }

    //metodi da dover sovrascrivere
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
        return pietanzeOrdinate.size();
    }

    @Override
    public Object getItem(int position) {
        return pietanzeOrdinate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CustomPietanzaOrdinataAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new CustomPietanzaOrdinataAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pietanza_ordinata, null, true);

            holder.editTextModifica = (EditText) convertView.findViewById(R.id.modifica);
            holder.textViewNome=(TextView) convertView.findViewById(R.id.nome);
            holder.textViewPrezzo=(TextView) convertView.findViewById(R.id.prezzo);
            holder.textViewQuantita=(TextView) convertView.findViewById(R.id.quantita);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (CustomPietanzaOrdinataAdapter.ViewHolder)convertView.getTag();
        }

        holder.editTextModifica.setText(""+pietanzeOrdinate.get(position).getQuantita());
        holder.textViewPrezzo.setText(""+pietanzeOrdinate.get(position).getCosto());
        holder.textViewNome.setText(pietanzeOrdinate.get(position).getNomePietanza());
        holder.textViewQuantita.setText(""+pietanzeOrdinate.get(position).getQuantita());

        holder.editTextModifica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pietanzeOrdinate.get(position).setQuantita(Integer.parseInt(holder.editTextModifica.getText().toString()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected EditText editTextModifica;
        protected TextView textViewNome;
        protected TextView textViewPrezzo;
        protected TextView textViewQuantita;

    }

}
