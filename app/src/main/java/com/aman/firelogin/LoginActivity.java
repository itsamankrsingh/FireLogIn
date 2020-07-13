package com.aman.firelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView mNewHere;
    EditText mEmail,mPassword;
    Button mLogin;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNewHere=findViewById(R.id.txtNewHere);
        mNewHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        mEmail=findViewById(R.id.edtLoginEmail);
        mPassword=findViewById(R.id.edtLoginPassword);
        progressBar=findViewById(R.id.loginProgressBar);
        firebaseAuth=FirebaseAuth.getInstance();
        mLogin=findViewById(R.id.btnLoginButton);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError(getString(R.string.email_required));
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError(getString(R.string.password_required));
                    return;
                }

                if (password.length()<6){
                    mPassword.setError("Password must be greater than 6 character");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //signin
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}