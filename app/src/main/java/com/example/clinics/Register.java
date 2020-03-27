package com.example.clinics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText nameText, phText, emText, pwText;
    TextView siedit;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );
        nameText = findViewById ( R.id.activity_register_ed_name );
        phText = findViewById ( R.id.phText );
        emText = findViewById ( R.id.emtext2 );
        pwText = findViewById ( R.id.activity_register_ed_password );
        siedit = findViewById ( R.id.siedit );

        mFirebaseAuth = FirebaseAuth.getInstance ();
        progressBar = findViewById ( R.id.progressBar );

        if (mFirebaseAuth.getCurrentUser () != null) {
            startActivity ( new Intent ( getApplicationContext (), Login.class ) );
            finish ();
        }
        siedit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String email = emText.getText ().toString ().trim ();
                String pass = pwText.getText ().toString ().trim ();
                if (email.isEmpty ()) {
                    emText.setError ( "please enter your email" );
                    return;
                }
                if (pass.isEmpty ()) {
                    emText.setError ( "please enter your password" );
                    return;
                }
                if (pass.length () < 6) {
                    pwText.setError ( "Password Must be >= 6 Characterr" );
                    return;
                }
                progressBar.setVisibility ( View.VISIBLE );
                // register the user in firebase
                mFirebaseAuth.createUserWithEmailAndPassword ( email, pass ).addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            Toast.makeText ( Register.this, "User created", Toast.LENGTH_SHORT ).show ();
                            startActivity ( new Intent ( getApplicationContext (), Login.class ) );
                        } else {
                            Toast.makeText ( Register.this, "Error! " + task.getException ().getMessage (), Toast.LENGTH_SHORT ).show ();
                            Log.i ( "----------", "" + task.getException ().getMessage () );
                        }


                    }
                } );


            }
        } );
        siedit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent in = new Intent ( Register.this, MainActivity.class );
                startActivity ( in );
            }
        } );
    }
}


