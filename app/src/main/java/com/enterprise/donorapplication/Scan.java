package com.enterprise.donorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by albal on 07/02/2017.
 */

public class Scan extends AppCompatActivity {

    private final static String SERVER_URL="serverIP";
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private final static String ACCESSINTOKEN="accesstoken";

     LoginedActivity activity;

     Button scanButton;
     TextView tv;
     Button send_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);


        scanButton = (Button) findViewById(R.id.button_scan);
        tv = (TextView) findViewById(R.id.textView2);
        send_msg = (Button) findViewById(R.id.button_send);

        final IntentIntegrator scanIntegrator = new IntentIntegrator(this);

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
    private void showText(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.scan) {

            Intent intent = new Intent(this,Scan.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.notify) {
            Intent intent = new Intent(this,Notify.class);
            this.startActivity(intent);
            return true;
        }
        if (id == R.id.logout) {
            activity.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);

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
}
