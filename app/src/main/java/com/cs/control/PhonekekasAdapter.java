package com.cs.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.day.R;

import java.util.ArrayList;

public class PhonekekasAdapter extends ArrayAdapter<Phonekeka> {

    Context context;
    private ArrayList<Phonekeka> phonekekas;

    public PhonekekasAdapter(Context context, int textViewResourceId, ArrayList<Phonekeka> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.phonekekas = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.phone, null);
        }
        Phonekeka o = phonekekas.get(position);
        if (o != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView tel=(TextView)v.findViewById(R.id.tel);

            TextView extension=(TextView)v.findViewById(R.id.extension);

            name.setText(String.valueOf(o.getName()));
            tel.setText(String.valueOf(o.getTel()));
            extension.setText(String.valueOf(o.getExtension()));

        }
        return v;
    }
}
