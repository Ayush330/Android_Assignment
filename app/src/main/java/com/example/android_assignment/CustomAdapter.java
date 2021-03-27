package com.example.android_assignment;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{

    private final List<Data> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView Name;
        private final TextView Capital;
        private final TextView Region;
        private final TextView Subregion;
        private final TextView Population;
        private final TextView Border;
        private final TextView Language;
        private final ImageView im;

        public ViewHolder(View view)
        {
            super(view);
            Name = (TextView)  view.findViewById(R.id.name);
            Capital = (TextView)  view.findViewById(R.id.capital);
            Region = (TextView)  view.findViewById(R.id.region);
            Subregion = (TextView)  view.findViewById(R.id.subregion);
            Population = (TextView)  view.findViewById(R.id.population);
            Border = (TextView)  view.findViewById(R.id.borders);
            Language = (TextView)  view.findViewById(R.id.language);
            im       = (ImageView) view.findViewById(R.id.imageView);
        }

        public TextView getNameView()
        {
            return Name;
        }

        public TextView getCapitalView()
        {
            return Capital;
        }

        public TextView getRegionView()
        {
            return Region;
        }

        public TextView getsubRegionView()
        {
            return Subregion;
        }

        public TextView getPopulationView()
        {
            return Population;
        }

        public TextView getBorderView()
        {
            return Border;
        }

        public TextView getLanguageView()
        {
            return Language;
        }

        public ImageView getImageView()
        {
            return im;
        }
    }


    public CustomAdapter(List<Data> dataSet)
    {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {

        View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.reycycler, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {
        viewHolder.getNameView().setText(localDataSet.get(position).name);
        viewHolder.getCapitalView().setText("Capital: "+localDataSet.get(position).capital);
        viewHolder.getRegionView().setText("Region: "+localDataSet.get(position).region);
        viewHolder.getsubRegionView().setText("Sub_Region: "+localDataSet.get(position).subregion);
        viewHolder.getPopulationView().setText("Population: "+String.valueOf(localDataSet.get(position).population)+"");
        viewHolder.getBorderView().setText("Borders: "+localDataSet.get(position).borders.toString());
        viewHolder.getLanguageView().setText("Languages: "+localDataSet.get(position).languages.get(0).name);
        String url= localDataSet.get(position).flag;
        GlideToVectorYou.justLoadImage((Activity)viewHolder.getImageView().getContext(), Uri.parse(url), viewHolder.getImageView());
    }


    @Override
    public int getItemCount()
    {
        return localDataSet.size();
    }
}

