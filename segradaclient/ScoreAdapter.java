package com.example.segradaclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<Score>
{
    public ScoreAdapter(Context context, List<Score> objects) {
        super(context,R.layout.score, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get the inflater that will convert the contact_layout.xml file into an
        // actual object
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //create a view to display the contacts's info
        View contactView = inflater.inflate(R.layout.score,parent, false);

        // keep track of contact this view is working with
        contactView.setTag(getItem(position));

        // get text views that will hold strings
        TextView PlayerColor = (TextView) contactView.findViewById(R.id.PlayerColor);
        TextView Holes = (TextView) contactView.findViewById(R.id.Chat);
        TextView Pairs = (TextView) contactView.findViewById(R.id.Pairs);
        TextView Columns = (TextView) contactView.findViewById(R.id.Columns);
        TextView ColorSets = (TextView) contactView.findViewById(R.id.ColorSets);

        // set text fields
        PlayerColor.setText(getItem(position).getColor());
        Holes.setText(getItem(position).getHoles());
        Pairs.setText(getItem(position).getPairs());
        ColorSets.setText(getItem(position).getSets());
        Columns.setText(getItem(position).getColumns());

        return contactView;
}}
