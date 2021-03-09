package com.material.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class SignUpSimpleLight extends AppCompatActivity {

    private TextInputEditText emailTIET, passwordTIET1, passwordTIET2;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        emailTIET = findViewById(R.id.email1);
        passwordTIET1 = findViewById(R.id.passwordTIET1);
        passwordTIET2 = findViewById(R.id.passwordTIET2);
        progressDialog = new ProgressDialog(this);
        SignUpButton = findViewById(R.id.sign_up);
        SignInTv = findViewById(R.id.sign_in_TV);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpSimpleLight.this, LoginSimpleLight.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Register(){
        String email=emailTIET.getText().toString();
        String password1=passwordTIET1.getText().toString();
        String password2=passwordTIET2.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailTIET.setError("Enter Your Email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordTIET1.setError("Enter Your Password");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            passwordTIET2.setError("Confirm Your Password");
            return;
        }
        else if(!password1.equals(password2)){
            passwordTIET2.setError("Different Password");
            return;
        }
        else if(!isValidEmail(email)){
            emailTIET.setError("Invalid Email");
            return;
        }
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpSimpleLight.this, "Succesfully Register",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(SignUpSimpleLight.this, MyDashboard.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUpSimpleLight.this,"Sign Up Failed",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
