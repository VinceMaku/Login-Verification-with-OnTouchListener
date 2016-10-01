package com.example.bamble.my_android_app_practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button btnLogIn;
    Button show;
    TextView btnSignUp;
    EditText password;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        btnSignUp = (TextView) findViewById(R.id.signUP);
        btnLogIn = (Button) findViewById(R.id.btnLogin);
        show = (Button) findViewById(R.id.btnShow);
        password = (EditText) findViewById(R.id.txtPass);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUPActivity.class);
                startActivity(intent);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {

            final EditText editTextEmail = (EditText) findViewById(R.id.txtLog);
            final EditText editTextPassword = (EditText) findViewById(R.id.txtPass);

            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(email);

                if (password.equals(storedPassword)) {
                    Toast.makeText(MainActivity.this, "Successfully logged in.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), onTouchActivity.class);
                    startActivity(intent);

                }

                else {
                    if(email.equals("") && password.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Please fill out all the field.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(email.equals("") || password.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Please fill out the field.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(MainActivity.this, "Incorrect Email or Password.", Toast.LENGTH_LONG).show();

                }

            }
        });

        show.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

}