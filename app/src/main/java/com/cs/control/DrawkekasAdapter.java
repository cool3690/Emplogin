package com.cs.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.day.Keka;
import com.cs.day.R;

import java.util.ArrayList;

public class DrawkekasAdapter extends ArrayAdapter<Drawkeka> {

    Context context;
    private ArrayList<Drawkeka> drawkekas;

    public DrawkekasAdapter(Context context, int textViewResourceId, ArrayList<Drawkeka> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.drawkekas = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.drawkeka, null);
        }
        Drawkeka o = drawkekas.get(position);
        if (o != null) {
            TextView show = (TextView) v.findViewById(R.id.show);
            TextView person=(TextView)v.findViewById(R.id.person);

            TextView sub=(TextView)v.findViewById(R.id.sub);
            TextView manager=(TextView)v.findViewById(R.id.manager);
            ImageView statedot=(ImageView)v.findViewById(R.id.statedot);
            ImageView statedot2=(ImageView)v.findViewById(R.id.statedot2);
            ImageView statedot3=(ImageView)v.findViewById(R.id.statedot3);
            ImageView stateline=(ImageView)v.findViewById(R.id.stateline);
            ImageView stateline2=(ImageView)v.findViewById(R.id.stateline2);
            show.setText(String.valueOf(o.getShow()));
            person.setText(String.valueOf(o.getPerson()));
            sub.setText(String.valueOf(o.getSub()));
            manager.setText(String.valueOf(o.getManager()));
            statedot.setImageResource(o.getStatedot());
            statedot2.setImageResource(o.getStatedot2());
            statedot3.setImageResource(o.getStatedot3());
            stateline.setImageResource(o.getStateline());
            stateline2.setImageResource(o.getStateline2());
        }
        return v;
    }
}
