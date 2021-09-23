package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Button btn = findViewById(R.id.btnshow_video);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });

        Intent intent = getIntent();
        String extraMessage = intent.getStringExtra("eMessage");

        TextView textview = findViewById(R.id.msg_from_main);
        textview.setText(extraMessage);
    }

    public void playVideo() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=5qap5aO4i9A"));
        startActivity(intent);
    }


}