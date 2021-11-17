 package au.edu.unsw.infs3634.covid19tracker;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;

 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Patterns;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ProgressBar;
 import android.widget.Toast;

 import com.google.android.gms.tasks.OnCompleteListener;
 import com.google.android.gms.tasks.Task;
 import com.google.firebase.auth.AuthResult;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.FirebaseDatabase;

 public class LoginActivity extends AppCompatActivity {
     Button login;
     EditText etEmail, etPassword;
     ProgressBar progressBar;
     FirebaseAuth mAuth;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);

         etEmail = findViewById(R.id.etEmailLogin);
         etPassword = findViewById(R.id.etPassword);

         login = findViewById(R.id.btnLogin);
         login.setOnClickListener(this::onClick);

         progressBar = findViewById(R.id.progressBar);

         mAuth = FirebaseAuth.getInstance();


     }

     public void onClick(View view) {
         switch (view.getId()) {
             case R.id.btnLogin:
                 loginUser();
                 break;
         }
     }

     private void loginUser() {
         String email = etEmail.getText().toString().trim();
         String password = etPassword.getText().toString().trim();

         if(email.isEmpty()) {
             etEmail.setError("Email is required!");
             etEmail.requestFocus();
             return;
         }

         if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
             etEmail.setError("Please provide a valid email!");
             etEmail.requestFocus();
             return;
         }

         if(password.isEmpty()) {
             etPassword.setError("Password is required!");
             etPassword.requestFocus();
             return;
         }

         if(password.length() < 6) {
             etPassword.setError("Minimum password length should be 6 characters!");
             etPassword.requestFocus();
             return;
         }

         progressBar.setVisibility(View.VISIBLE);
         mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {

                 if (task.isSuccessful()) {
                     //redirects to home activity
                     startActivity(new Intent(LoginActivity.this, MainActivity.class));
                     Toast.makeText(getApplicationContext(), "Successful Login!", Toast.LENGTH_LONG).show();
                     progressBar.setVisibility(View.GONE);

                 } else {
                     Toast.makeText(getApplicationContext(), "Failed to login! Please check your credentials!", Toast.LENGTH_LONG).show();
                     progressBar.setVisibility(View.GONE);
                 }
             }
         });
     }

     public void goToRegister (View view){
         Intent intent = new Intent(this, RegisterActivity.class);
         startActivity(intent);
         //Removes animation when switching to registerActivity
         overridePendingTransition(0, 0);
     }

 }