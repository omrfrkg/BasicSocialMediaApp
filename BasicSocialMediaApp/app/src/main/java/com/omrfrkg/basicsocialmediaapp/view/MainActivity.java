package com.omrfrkg.basicsocialmediaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.omrfrkg.basicsocialmediaapp.R;
import com.omrfrkg.basicsocialmediaapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    Boolean newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        newUser = false;

        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null){
            goToFeedScreen();
        }
    }

    public void loginOrSignUp(View view){
        String email = binding.textUsername.getText().toString();
        String password = binding.textPassword.getText().toString();
        if (newUser == true){
                if (email.equals("") || password.equals("")){
                    Toast.makeText(MainActivity.this,"Please fill in all fields completely!",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            goToFeedScreen();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
        }
        else{
            if (email.equals("") || password.equals("")){
                Toast.makeText(MainActivity.this,"Please fill in all fields completely!",Toast.LENGTH_SHORT).show();
            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        goToFeedScreen();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

        }
        //Toast.makeText(MainActivity.this,"TIKLADIN",Toast.LENGTH_SHORT).show();
    }

    public void goToFeedScreen(){
        Intent intent = new Intent(MainActivity.this,FeedActivity.class);
        startActivity(intent);
        finish();
    }
    public void signUpValidate(View view){
        newUser = true;
        binding.btnLogin.setText("Sign Up");
        binding.textView.setVisibility(View.INVISIBLE);
        binding.imageView.setImageResource(R.drawable.add_user);
    }
}