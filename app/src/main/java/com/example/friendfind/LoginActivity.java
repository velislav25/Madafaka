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
import java.util.HashMap;
import java.util.Map;

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
                //validate inputs
                if(userEmail!=null && !userEmail.isEmpty() && userPass!=null && !userPass.isEmpty()){
                    loginUser(userEmail, userPass);
                }else {
                    Toast.makeText(getApplicationContext(),"Email or password empty~!",Toast.LENGTH_SHORT).show();
                }

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
                        if (task.isSuccessful()){
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                            Map userObj = new HashMap<>();
                            String email = usernameTextView.getText().toString();
                            userObj.put("email", email);
                            String user = email.substring(0, email.lastIndexOf("@"));
                            userObj.put("name", user);

                            databaseReference.updateChildren(userObj);

                            Toast.makeText(LoginActivity.this,"Sign Up Successful. Please login", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this,"Sign Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }


}