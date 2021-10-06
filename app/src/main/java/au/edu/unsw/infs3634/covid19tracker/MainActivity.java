package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CountryAdapter.clickListener{
    //Logging
    public static final String TAG = "MainActivity";
    public ArrayList<Country> mCountries = Country.getCountries();
    public RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate recyclerview
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CountryAdapter countryAdapter = new CountryAdapter(this, mCountries, this);
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
}

