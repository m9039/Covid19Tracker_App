package au.edu.unsw.infs3634.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter <CountryAdapter.MyViewHolder> {
    public ArrayList<Country> mCountries;
    public clickListener mListener;

    public CountryAdapter(Context context, ArrayList<Country> countries, clickListener listener ){
        this.mCountries = countries;
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
        Country country = mCountries.get(position);
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
        return mCountries.size();
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
}
