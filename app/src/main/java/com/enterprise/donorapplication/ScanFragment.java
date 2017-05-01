package com.enterprise.donorapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.enterprise.ServerAccess.LoginUtil;
import com.google.zxing.integration.android.IntentIntegrator;

public class ScanFragment extends Fragment  {

    LoginUtil loginUtil;
    Button scanButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginUtil = new LoginUtil(getActivity());
        View rootview = inflater.inflate(R.layout.fragment_scan,container,false);
        scanButton = (Button) rootview.findViewById(R.id.button_scan);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                scanIntegrator.setPrompt("Scan");
                scanIntegrator.setCameraId(0);
                scanIntegrator.initiateScan();
            }
        });

        return rootview;
    }


}

