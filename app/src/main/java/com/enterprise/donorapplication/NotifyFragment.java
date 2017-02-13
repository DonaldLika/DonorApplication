package com.enterprise.donorapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.enterprise.ServerAccess.DonationAccess;
import com.enterprise.ServerAccess.LoginUtil;


public class NotifyFragment extends Fragment {

    private Spinner city_sp;
    private Spinner blood_sp;
    private Button msg;

    View rootview;
    String city_value="";
    String blood_value="";

    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter1;
    private LoginUtil loginUtil;


    public NotifyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginUtil=new LoginUtil(this.getActivity());
        rootview = inflater.inflate(R.layout.fragment_notify,container,false);

        city_sp = (Spinner) rootview.findViewById(R.id.spinner_city);
        blood_sp = (Spinner) rootview.findViewById(R.id.spinner_blood);
        msg = (Button) rootview.findViewById(R.id.button_notify);

        //Spinner 1
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.country_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_sp.setAdapter(adapter);
        city_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city_value = city_sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner 2
        adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.blood_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_sp.setAdapter(adapter1);
        blood_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blood_value = blood_sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(DonationAccess.notifyArea(city_value,blood_value,loginUtil.getToken()))
                {
                    Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "A problem occurred,try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootview;
    }
}