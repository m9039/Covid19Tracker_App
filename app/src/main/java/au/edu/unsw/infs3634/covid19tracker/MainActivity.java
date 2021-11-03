package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private CountryAdapter countryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate recyclerview
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // Set the layout manager (Linear or Grid)
        layoutManager = new LinearLayoutManager(this);
        // layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        // Implement the ClickListener for the adapter
        CountryAdapter.clickListener listener = new CountryAdapter.clickListener() {
            @Override
            public void onCountryClick(View view, String countryCode) {
                launchDetailActivity(countryCode);
            }
        };

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CovidService service = retrofit.create(CovidService.class);
        Call<au.edu.unsw.infs3634.covid19tracker.Response> responseCall = service.getResponse();
        responseCall.enqueue(new Callback<au.edu.unsw.infs3634.covid19tracker.Response>() {
            @Override
            public void onResponse(Call<au.edu.unsw.infs3634.covid19tracker.Response> call, retrofit2.Response<au.edu.unsw.infs3634.covid19tracker.Response> response) {
                List<Country> countries = response.body().getCountries();
                countryAdapter.setCountries(countries);
            }

            @Override
            public void onFailure(Call<au.edu.unsw.infs3634.covid19tracker.Response> call, Throwable t) {

            }
        });

        // Create an adapter and supply the countries data to be displayed

        Gson gson = new Gson();
        au.edu.unsw.infs3634.covid19tracker.Response response = gson.fromJson(au.edu.unsw.infs3634.covid19tracker.Response.json, au.edu.unsw.infs3634.covid19tracker.Response.class);
        countryAdapter = new CountryAdapter(response.getCountries(), listener);
        countryAdapter.sort(CountryAdapter.SORT_METHOD_TOTAL);
        // Connect the adapter with the RecyclerView
        mRecyclerView.setAdapter(countryAdapter);

    }

    // Called when the user taps the Launch Detail Activity button
    private void launchDetailActivity(String message){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }


    //adds the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);       //specifies our menu resource
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //listen to text changes in search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {        //when user types something and presses enter
                countryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {            //when there are text changes
                countryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    //react to user interaction with the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.sortNewCases:
                countryAdapter.sort(1);
                return true;
            case R.id.sortTotalCases:
                countryAdapter.sort(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

