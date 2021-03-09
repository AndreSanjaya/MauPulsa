package com.material.project.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.material.project.R;

public class LoginSimpleLight extends AppCompatActivity {

    private TextInputEditText emailTIETlogin, passwordTIETlogin;
    private Button SignInButton;
    private TextView SignUpTvLg;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        emailTIETlogin = findViewById(R.id.emaillg);
        passwordTIETlogin = findViewById(R.id.passwordTIETlg);
        progressDialog = new ProgressDialog(this);
        SignInButton = findViewById(R.id.sign_in_TV_lg);
        SignUpTvLg = findViewById(R.id.sign_up_lg);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        SignUpTvLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSimpleLight.this, SignUpSimpleLight.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Login() {
        String emaillg = emailTIETlogin.getText().toString();
        String passwordlg = passwordTIETlogin.getText().toString();
        if (TextUtils.isEmpty(emaillg)) {
            emailTIETlogin.setError("Enter Your Email");
            return;
        } else if(TextUtils.isEmpty(passwordlg)){
            passwordTIETlogin.setError("Enter Your Password");
            return;
        }
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(emaillg, passwordlg).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginSimpleLight.this, "Succesfully Register", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginSimpleLight.this, MyDashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginSimpleLight.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
