package com.enterprise.donorapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.enterprise.ServerAccess.LoginUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    EditText _usernameText;
    EditText _passwordText;
    Button _loginButton;
    TextView loginErrorMsg;
    LoginUtil loginUtil;

    public MainActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _usernameText=(EditText)findViewById(R.id.input_username);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _loginButton=(Button)findViewById(R.id.btn_login);
        loginErrorMsg=(TextView)findViewById(R.id.loginErrorMsg);

       loginUtil = new LoginUtil(getApplicationContext());

        if (loginUtil.isLogined()) {
            Intent intent = new Intent(MainActivity.this, LoginedActivity.class);
            startActivity(intent);
            finish();
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(validate()) {
                    NetAsync(view);
                }
            }
        });
    }

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(MainActivity.this);
            nDialog.setTitle("Checking Network");
            nDialog.setMessage("Loading..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {

                } catch (IOException e) {

                }
            }
            return false;
        }

         @Override
       protected void onPostExecute(Boolean th){

            if(th){
                nDialog.dismiss();
                new ProcessLogin().execute();
            }
            else{
                nDialog.dismiss();
                loginErrorMsg.setText("Error in Network Connection");
            }
        }
    }


   private class ProcessLogin extends AsyncTask<String, String, Boolean> {

        private ProgressDialog pDialog;

        String username, password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            _usernameText = (EditText) findViewById(R.id.input_username);
            _passwordText = (EditText) findViewById(R.id.input_password);

            username = _usernameText.getText().toString();
            password = _passwordText.getText().toString();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {

                return loginUtil.Login(username,password);
        }

        @Override
        protected void onPostExecute(Boolean response) {
            if (response) {
                pDialog.setMessage("Loading User Space");
                pDialog.setTitle("Getting Data");
                Intent upanel = new Intent(getApplicationContext(), LoginedActivity.class);
                upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pDialog.dismiss();
                startActivity(upanel);

                finish();

            } else {
                pDialog.dismiss();
                loginErrorMsg.setText("Incorrect Username/Password");
            }
        }
    }
    public void NetAsync(View view){
        new NetCheck().execute();
    }


    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("Enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            _passwordText.setError("Enter a valid password");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        return valid;
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
