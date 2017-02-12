package com.enterprise.donorapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.enterprise.ServerAccess.DonationAccess;
import com.enterprise.ServerAccess.LoginUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanFragment extends Fragment  {


    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private LoginUtil loginUtil;
    Button scanButton;

    public ScanFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginUtil=new LoginUtil(this.getActivity());
        View rootview = inflater.inflate(R.layout.fragment_scan,container,false);
        scanButton = (Button) rootview.findViewById(R.id.button_scan);


        final IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());

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

        return rootview;
    }

    private void showText(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v("####","Request:"+requestCode+ "resultCOde:"+resultCode+" Data:"+data);
        if(data != null && data.getAction() != null && data.getAction().equals(ACTION_SCAN))
        {

            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult.getContents() != null) {
                String qrStr = scanningResult.getContents();

                if(DonationAccess.notifyDonor(qrStr,loginUtil.getToken()))
                {
                    showText("QR data send to server");
                }

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

}

