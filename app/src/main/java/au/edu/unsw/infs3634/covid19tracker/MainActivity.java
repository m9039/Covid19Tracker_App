package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Logging
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logging
        int x = 250;
        Log.d(TAG, "this is an example of logging! " + x);

        //Debugging
        int y = 100, z = 200;
        int total = y + z;

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                launchDetailActivity();
                extraMessage();
            }
        });
    }

    public void launchDetailActivity() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
        Log.d(TAG, "onCreate: Starting launch");

    }

    public void extraMessage(){
        String extraMessage = "This message came from MainActivity";
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("eMessage", extraMessage);
        startActivity(intent);
    }


}