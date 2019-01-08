package com.govege.trollo.govege;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trollo on 2018-12-30.
 */

public class PlacesArrayAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Place> places;
    private int rowLayoutId;
    public PlacesArrayAdapter(Context context, int resource, ArrayList<Place> objects) {
        super(context, resource, objects);
        this.context = context;
        this.rowLayoutId = resource;
        this.places = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(rowLayoutId, parent, false);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView addressTextView = (TextView) rowView.findViewById(R.id.addressTextView);
        TextView distanceTextView = (TextView) rowView.findViewById(R.id.distanceTextView);
        TextView openNowTextView = (TextView) rowView.findViewById(R.id.openNowTextView);
        List<ImageView> rating = new ArrayList<>();
        rating.add((ImageView) rowView.findViewById(R.id.rating1));
        rating.add((ImageView) rowView.findViewById(R.id.rating2));
        rating.add((ImageView) rowView.findViewById(R.id.rating3));
        rating.add((ImageView) rowView.findViewById(R.id.rating4));
        rating.add((ImageView) rowView.findViewById(R.id.rating5));

        Place place = places.get(position);
        nameTextView.setText(place.getName());
        addressTextView.setText(place.getAddress());
        distanceTextView.setText(prepareDistanceText(place));
        openNowTextView.setText(place.getOpenNow() ? "open" : "closed");
        openNowTextView.setTextColor(place.getOpenNow() ? Color.GREEN : Color.RED);
        for(int i = 0; i < rating.size(); i++){
            rating.get(i).setImageResource(android.R.drawable.star_big_off);
        }
        for(int i = 0; i < Math.round(place.getRating()); i++){
            rating.get(i).setImageResource(android.R.drawable.star_big_on);
        }
        return rowView;
    }

    private String prepareDistanceText(Place place) {
        return "(" + (String.format("%.2f", ((MainActivity)context).getCurrentLocation().calculateDistance(place.getLocation()))) + " km away)";
    }
}
