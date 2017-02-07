package com.enterprise.donorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.enterprise.Session.SessionManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created by donald on 17-01-08.
 */

public class LoginedActivity extends AppCompatActivity {

    private final static String SERVER_URL="serverIP";
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private final static String ACCESSINTOKEN="accesstoken";

    private Button scanButton;
    private Button logoutButton;
    private SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logined);
        scanButton = (Button) findViewById(R.id.scanqr);
        logoutButton=(Button)findViewById(R.id.logout);

        final IntentIntegrator scanIntegrator = new IntentIntegrator(this);

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn())
        {
            this.logoutUser();
        }
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logoutUser();
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    scanIntegrator.initiateScan();
                }
                catch(Exception e)
                {
                    showText("Problem! Try again!");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showText(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT);
        toast.show();
    }

         @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v("####","Request:"+requestCode+ "resultCOde:"+resultCode+" Data:"+data);
        if(data != null && data.getAction() != null && data.getAction().equals(ACTION_SCAN)) //QR Code
        {

            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String qrStr = scanningResult.getContents();
                sendToServer(ACCESSINTOKEN, qrStr);
                super.onActivityResult(requestCode, resultCode, data);
            }
            else
            {
                showText("No QR scan data received");

            }

        }
        else if(data != null)
        {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    private void sendToServer(String accessToken, String qrStr) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String jsonStr = "{\"uuid\":\"" + qrStr + "\",\"access_token\":\"" + accessToken + "\"}";
        Log.v("##", " JSON to post:" + jsonStr);
        try {
            StringEntity se = new StringEntity(jsonStr);
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            Log.v("###", "Respnse:" + response.toString());
            showText("Successfully posted token");

        } catch (ClientProtocolException e) {
            showText("Unable to Post Token");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            showText("Unable to Post Token");
            e.printStackTrace();
        } catch (IOException e) {
            showText("Unable to Post Token");
            e.printStackTrace();
        }

    }
    private void logoutUser() {
        session.setLogin(false);
        // Ktheu tek faqja faqja kryesore
        Intent intent = new Intent(LoginedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

