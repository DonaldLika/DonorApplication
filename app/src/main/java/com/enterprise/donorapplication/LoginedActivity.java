package com.enterprise.donorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.enterprise.Session.SessionManager;




/**
 * Created by donald on 17-01-08.
 */

public class LoginedActivity extends AppCompatActivity {

    private SessionManager session;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logined);



        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn())
        {
            this.logoutUser();
        }


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
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public  void logoutUser() {
        session.setLogin(false);
        // Ktheu tek faqja faqja kryesore
        Intent intent = new Intent(LoginedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

