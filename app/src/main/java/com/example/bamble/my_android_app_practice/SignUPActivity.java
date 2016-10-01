package com.example.bamble.my_android_app_practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUPActivity extends Activity
{

    boolean emailValidator(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    boolean passwordValidator(String password) {
        if(password.length() > 7) {
            return true;
        } else {
            return false;
        }
    }

    boolean nameValidator(String name) {
        String flname = "[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(flname);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFName, editTextLName, editTextUsername;
    Button btnOk;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        editTextEmail =(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);
        editTextConfirmPassword=(EditText)findViewById(R.id.confirm_password);
        editTextFName=(EditText)findViewById(R.id.fname);
        editTextLName=(EditText)findViewById(R.id.lname);
        editTextUsername=(EditText)findViewById(R.id.username);
        btnOk =(Button)findViewById(R.id.ok);
        btnOk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String email= editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();
                String fname=editTextFName.getText().toString();
                String lname=editTextLName.getText().toString();
                String username=editTextUsername.getText().toString();


                if(email.equals("")||password.equals("")||confirmPassword.equals("")||fname.equals("")||lname.equals("")||username.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please fill out all the field.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!nameValidator(editTextFName.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Invalid First name.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!nameValidator(editTextLName.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Invalid Last name.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(emailValidator(editTextUsername.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Username cannot be an email format.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(emailValidator(editTextEmail.getText())==false){
                    Toast.makeText(getApplicationContext(), "Invalid Email address.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(loginDataBaseAdapter.existingValidator(editTextUsername.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Username already exist.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(loginDataBaseAdapter.existingValidator(editTextEmail.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Email address already exist.", Toast.LENGTH_LONG).show();
                    return;
                }

                else
                {
                    if(emailValidator(editTextEmail.getText()) == true &&
                            passwordValidator(editTextPassword.getText().toString()) == true) {
                        loginDataBaseAdapter.insertEntry(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextFName.getText().toString(), editTextLName.getText().toString(), editTextUsername.getText().toString());
                        Toast.makeText(getApplicationContext(), "Account Successfully Created.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    else if(emailValidator(editTextEmail.getText()) == true &&
                            passwordValidator(editTextPassword.getText().toString()) == false){
                        Toast.makeText(getApplicationContext(), "Password must be at least 8 character.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
