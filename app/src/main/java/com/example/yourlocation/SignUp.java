package com.example.yourlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.printservice.PrinterDiscoverySession;
import android.view.View;
import android.widget.Toast;

import com.example.yourlocation.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
ActivitySignUpBinding binding;
FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        pd = new ProgressDialog(this);
        pd.setMessage("Creating your account");


        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                auth.createUserWithEmailAndPassword(binding.emailTxt.getText().toString(), binding.passwordTxt.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    pd.dismiss();
                                    String username = binding.usernameTxt.getText().toString();
                                    Users user = new Users(username,binding.emailTxt.getText().toString(),binding.passwordTxt.getText().toString());
                                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user);

                                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(SignUp.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    if(auth.getCurrentUser()!=null){
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
    }

    }
}