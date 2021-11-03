package au.edu.unsw.infs3634.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter <CountryAdapter.MyViewHolder> implements Filterable {
    public List<Country> mCountries, mCountriesFiltered;
    public clickListener mListener;
    public static final int SORT_METHOD_NEW = 1;
    public static final int SORT_METHOD_TOTAL = 2;


    //constructor method for adapter class
    public CountryAdapter(List<Country> countries, clickListener listener){
        this.mCountries = countries;
        this.mCountriesFiltered = countries;            //this is so that everytime you search, you keep orignial copy
        this.mListener = listener;
    }

    public void setCountries(List<Country> countries){
        mCountries.clear();
        this.mCountries.addAll(countries);//add countries from API into recyclerview
        notifyDataSetChanged();//notify UI changes made
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();                //converting sequence to string
                if (charString.isEmpty()) {                                 //check to make sure char string not empty
                    mCountriesFiltered = mCountries;                        //returns original list
                } else{
                    List<Country> filteredList = new ArrayList<>();
                    for(Country country : mCountries) {
                        if(country.getCountry().toLowerCase().contains(charString.toLowerCase())){    //if search value is included in country names
                            filteredList.add(country);                                                //adds it to the filtered list
                        }
                    }
                    mCountriesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mCountriesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mCountriesFiltered = (List<Country>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface clickListener{
        void onCountryClick(View view, String countryCode);
    }

    //inflate the row layout when needed
    @NonNull
    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view, mListener);
    }

    //bind data to textview elements in each row
    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.MyViewHolder holder, int position) {
        // Retrieve the country by it's position in filtered list
        Country country = mCountriesFiltered.get(position);
        DecimalFormat df = new DecimalFormat( "#,###,###,###" );
        holder.country.setText(country.getCountry());
        holder.totalCases.setText(df.format(country.getTotalConfirmed()));
        holder.newCases.setText("+" + df.format(country.getNewConfirmed()));
        holder.itemView.setTag(country.getCountryCode());

        Glide.with(holder.ivFlag)
                .load("https://flagcdn.com/16x12/" +country.getCountryCode().toLowerCase()+".png")
                .into(holder.ivFlag);
    }

    //counts total number of rows on the list
    @Override
    public int getItemCount() {
        return mCountriesFiltered.size();
    }

    //this method creates a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView country, totalCases, newCases;
        private clickListener mListener;
        ImageView ivFlag;

        public MyViewHolder(@NonNull View view, clickListener mListener) {
            super(view);
            this.mListener = mListener;
            view.setOnClickListener(this);
            country = view.findViewById(R.id.tvRecyclerCountry);
            totalCases = view.findViewById(R.id.tvRecyclerTotalCases);
            newCases = view.findViewById(R.id.tvRecyclerNewCases);
            ivFlag = view.findViewById(R.id.ivFlag);
        }

        @Override
        public void onClick(View view) {
            mListener.onCountryClick(view, (String) view.getTag());
        }
    };

    // Use the Java Collection.sort() and Comparator methods to sort the list
    public void sort(final int sortMethod) {
        if (mCountriesFiltered.size() > 0) {              //check that it is not empty
            Collections.sort(mCountriesFiltered, new Comparator<Country>() {
                @Override
                public int compare(Country o1, Country o2) {
                    if (sortMethod == SORT_METHOD_NEW) {
                        return o2.getNewConfirmed().compareTo(o1.getNewConfirmed());
                    } else if (sortMethod == SORT_METHOD_TOTAL) {
                        return o2.getTotalConfirmed().compareTo(o1.getTotalConfirmed());
                    }
                    return o1.getCountry().compareTo(o2.getCountry()); //if not specified it will sort by country name
                }
            });
        }
        notifyDataSetChanged();
    }
}

