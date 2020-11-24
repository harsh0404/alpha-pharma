package com.example.alpha_pharma;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mphone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar ProgressBar;

    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mphone = findViewById(R.id.Phone);
        mRegisterBtn = findViewById(R.id.RegisterBtn);
        mLoginBtn = findViewById(R.id.LoginBtn);
        mEmail = findViewById(R.id.Email);
        ProgressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext() ,MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText() .toString();
                String Phone = mphone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6){
                    mPassword.setError("Password Must Be Greater Than 6 Character Or Equal To 6 Character");
                    return;
                }
                ProgressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registration.this, "User created successfully", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users") .document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("Phone number",Phone);
                            documentReference.set(user) .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                  // Log.d(TAG, msg:"OnSuccess: User profile is created for "+ userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(Registration.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            ProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
mLoginBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext() ,Login.class));
    }
});
    }
}