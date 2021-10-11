package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CountryAdapter.clickListener{
    //Logging
    public static final String TAG = "MainActivity";
    public ArrayList<Country> mCountries = Country.getCountries();
    public RecyclerView mRecyclerView;
    public CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate recyclerview
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        countryAdapter = new CountryAdapter(this, mCountries, this);
        mRecyclerView.setAdapter(countryAdapter);

        //Logging
        int x = 250;
        Log.d(TAG, "this is an example of logging! " + x);

        //Debugging
        int y = 100, z = 200;
        int total = y + z;

    }

    public void onClick(int position){
        String message = mCountries.get(position).getId();
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("eMessage", message);
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

