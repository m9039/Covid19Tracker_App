package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageButton btn = findViewById(R.id.btnShowGoogle);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                googleSearch();
            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra("eMessage"); //id

        Country country = Country.getCountry(message); //add debugger to check if it is getting correct country
        setTitle(country.getCountryCode()); //displays country code in title

        TextView Country = findViewById(R.id.tvCountry);
        Country.setText(country.getCountry());

        TextView NewCases = findViewById(R.id.tvNewCases);
        NewCases.setText(""+country.getNewConfirmed());

        TextView TotalCases = findViewById(R.id.tvTotalCases);
        TotalCases.setText(String.valueOf(country.getTotalConfirmed()));

        TextView NewDeaths = findViewById(R.id.tvNewDeaths);
        NewDeaths.setText(""+country.getNewDeaths());

        TextView TotalDeaths = findViewById(R.id.tvTotalDeaths);
        TotalDeaths.setText(""+country.getTotalDeaths());

        TextView NewRecovered = findViewById(R.id.tvNewRecovered);
        NewRecovered.setText(""+country.getNewRecovered());

        TextView TotalRecovered = findViewById(R.id.tvTotalRecovered);
        TotalRecovered.setText(""+country.getTotalRecovered());
    }

    public void googleSearch() {
        Intent intent = getIntent();
        String message = intent.getStringExtra("eMessage"); //id
        Country country = Country.getCountry(message); //add debugger to check if it is getting correct country
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=covid+" + country.getCountry()));
        startActivity(i);
    }


}