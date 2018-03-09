package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

public class ForgotNewPassActivity extends AppCompatActivity {

    String code;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_new_pass);
        code = getIntent().getStringExtra("code");
        email = getIntent().getStringExtra("email");

    }

    public void update(View view){
        EditText etPass = (EditText) findViewById(R.id.etNewPassPassword);
        EditText etPassVerify = (EditText) findViewById(R.id.etNewPassConfirm);

        String pass = etPass.getText().toString();
        String passVerify = etPassVerify.getText().toString();

        if(pass.equals(passVerify)){
            //TODO check for password validity
            if(true){
                StringRequest forgotNewPassRequest = ServerFunctions.getForgotNewPassRequest(this,pass,email,code);
                RequestQueueVolley.getInstance(this).add(forgotNewPassRequest);
            }
        }
        else{
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }
}