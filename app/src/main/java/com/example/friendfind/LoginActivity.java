package com.example.friendfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.io.Console;

public class LoginActivity extends AppCompatActivity {

    TextView usernameTextView;
    TextView passTextView;
    FirebaseAuth instanceAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTextView = (TextView) findViewById(R.id.editTextTextUsername);
        passTextView = (TextView) findViewById(R.id.editTextPassword);
        Button loginBtn = (Button) findViewById(R.id.buttonLogin);
        Button regBtn = (Button) findViewById(R.id.buttonRegister);

        //Init firebase
        FirebaseApp.initializeApp(this);
        instanceAuth = FirebaseAuth.getInstance();

        //check if login?
        FirebaseUser currentUser = instanceAuth.getCurrentUser();
        if(currentUser!=null){
            runApp();
        }


        //button listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = usernameTextView.getText().toString();
                String userPass = passTextView.getText().toString();
                loginUser(userEmail, userPass);
            }


        });

        //button listener
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = usernameTextView.getText().toString();
                String userPass = passTextView.getText().toString();
                instanceAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Sign Up Error", Toast.LENGTH_SHORT).show();
                        }else{
                            String user_id = instanceAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
                            current_user_db.setValue(true);
                        }
                    }
                });
            }


        });

    }

    private void loginUser(String userEmail, String userPass) {
        instanceAuth.signInWithEmailAndPassword(userEmail,userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //run app if isSuccessful
                            runApp();
                        }else {
                            Toast.makeText(getApplicationContext(),"Wrong login or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void runApp() {
        saveUserActivity();
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void saveUserActivity() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference userReference = databaseReference.child(instanceAuth.getUid());
        userReference.setValue(instanceAuth.getCurrentUser().getEmail());
    }
}