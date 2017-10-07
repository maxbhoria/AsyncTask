package com.abc.iimt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mail, pwd;
    private Button login, create;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ViewFlipper simpleViewFlipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mail = (EditText) findViewById(R.id.Email);
        pwd = (EditText) findViewById(R.id.Password);

        int[] images = {R.mipmap.ic_launcher, R.mipmap.g6, R.mipmap.tw, R.mipmap.untitled, R.mipmap.ic_launcher_round};     // array of images

        // get The references of ViewFlipper
        simpleViewFlipper = (ViewFlipper) findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper

        // loop for creating ImageView's
        for (int i = 0; i < images.length; i++) {
            // create the object of ImageView
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]); // set image in ImageView
            simpleViewFlipper.addView(imageView); // add the created ImageView in ViewFlipper
        }
        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        // set the animation type's to ViewFlipper
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);
        // set interval time for flipping between views
        simpleViewFlipper.setFlipInterval(3000);
        // set auto start for flipping between views
        simpleViewFlipper.setAutoStart(true);

        login = (Button) findViewById(R.id.Login);
        create = (Button) findViewById(R.id.create);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, AccActivity.class));
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void startSignIn() {
        String email = mail.getText().toString();
        String password = pwd.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Fields are Empty", Toast.LENGTH_LONG).show();
        } else
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in Problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void closeOptionsMenu() {
        super.closeOptionsMenu();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}