package com.syams.sppk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DaftarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText txt_email;
    EditText txt_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        Button btn_daftar = (Button) findViewById(R.id.btn_daftar);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        mAuth = FirebaseAuth.getInstance();



        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txt_email.getText().toString();
                String password = txt_email.getText().toString();
                final Intent i = new Intent(DaftarActivity.this,HomeActivity.class);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(DaftarActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            i.putExtra("email",user.getEmail());
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.

                            try {
                                throw task.getException();
                            } catch(com.google.firebase.auth.FirebaseAuthWeakPasswordException e) {
                                txt_password.setError("Password lemah");
                                txt_password.requestFocus();
                            } catch(com.google.firebase.auth.FirebaseAuthInvalidCredentialsException e) {
                                txt_email.setError("Email tidak benar");
                                txt_email.requestFocus();
                            } catch(com.google.firebase.auth.FirebaseAuthUserCollisionException e) {
                                txt_email.setError("User sudah ada");
                                txt_email.requestFocus();
                            } catch(Exception e) {
                               Toast.makeText(DaftarActivity.this, "Authentication failed. "+e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });



    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            // User is signed in
            Intent i = new Intent(DaftarActivity.this,HomeActivity.class);
            i.putExtra("email",currentUser.getEmail());
            startActivity(i);
        }
    }
}
