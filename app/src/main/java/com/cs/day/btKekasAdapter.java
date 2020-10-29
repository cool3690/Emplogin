package com.cs.day;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class btKekasAdapter extends ArrayAdapter<btKeka> {

    Context context;
    private ArrayList<btKeka> kekas;

    public btKekasAdapter(Context context, int textViewResourceId, ArrayList<btKeka> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.kekas = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.btkeka, null);
        }
        btKeka o = kekas.get(position);
        if (o != null) {
            TextView title = (TextView) v.findViewById(R.id.title);

            TextView content=(TextView)v.findViewById(R.id.content);
            TextView depart=(TextView)v.findViewById(R.id.depart);
            TextView  date=(TextView)v.findViewById(R.id.date);
            ImageView btn=(ImageView)v.findViewById(R.id.btn);

              /* */
            title.setText(String.valueOf(o.getTitle()));

            content.setText(String.valueOf(o.getContent()));
            depart.setText(String.valueOf(o.getDepart()));
            date.setText(String.valueOf(o.getDate()));
             btn.setImageResource(o.getBtn());

              /**/
        }
        return v;
    }
}
