package com.cs.day;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class KekasAdapter extends ArrayAdapter<Keka> {

    Context context;
    private ArrayList<Keka> kekas;

    public KekasAdapter(Context context, int textViewResourceId, ArrayList<Keka> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.kekas = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.keka, null);
        }
        Keka o = kekas.get(position);
        if (o != null) {
            TextView show = (TextView) v.findViewById(R.id.show);

            show.setText(String.valueOf(o.getShow()));

         
        }
        return v;
    }
}
