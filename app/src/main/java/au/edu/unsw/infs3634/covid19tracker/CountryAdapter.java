package au.edu.unsw.infs3634.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter <CountryAdapter.MyViewHolder> implements Filterable {
    public List<Country> mCountries;
    public List<Country> mCountriesFiltered;
    public clickListener mListener;

    public CountryAdapter(Context context, List<Country> countries, clickListener listener ){
        this.mCountries = countries;
        this.mCountriesFiltered = countries;            //this is so that everytime you search, you keep orignial copy
        this.mListener = listener;
    }

    //inflate the row layout when needed
    @NonNull
    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view, this, mListener);
    }

    //bind data to textview elements in each row
    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.MyViewHolder holder, int position) {
        Country country = mCountriesFiltered.get(position);
        String countryNames = country.getCountry();
        holder.country.setText(countryNames);

        int totalCases = country.getTotalConfirmed();
        holder.totalCases.setText(String.valueOf(totalCases));

        int newCases = country.getNewConfirmed();
        holder.newCases.setText("+"+ newCases);
    }

    //counts total number of rows on the list
    @Override
    public int getItemCount() {
        return mCountriesFiltered.size();
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

    //this method creates a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView country, totalCases, newCases;
        clickListener mListener;
        final CountryAdapter countryAdapter;

        public MyViewHolder(@NonNull View view, CountryAdapter countryAdapter, clickListener mListener) {
            super(view);
            country = view.findViewById(R.id.tvRecyclerCountry);
            totalCases = view.findViewById(R.id.tvRecyclerTotalCases);
            newCases = view.findViewById(R.id.tvRecyclerNewCases);
            this.countryAdapter = countryAdapter;
            this.mListener = mListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(getAdapterPosition());
        }
        }

        public interface clickListener{
            void onClick(int position);
    }

    //sort method
    public void sort(final int sortMethod){
        if(mCountriesFiltered.size() > 0){              //check that it is not empty
            Collections.sort(mCountriesFiltered, new Comparator<Country>() {
                @Override
                public int compare(Country o1, Country o2) {
                    if (sortMethod == 1){
                        return o2.getNewConfirmed().compareTo(o1.getNewConfirmed());
                    } else if (sortMethod == 2){
                        return o2.getTotalConfirmed().compareTo(o1.getTotalConfirmed());
                    }
                    return o1.getCountry().compareTo(o2.getCountry()); //if not specified it will sort by country name
                }
            });
        }
        notifyDataSetChanged();
    }

}
