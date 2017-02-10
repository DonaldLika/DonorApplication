package com.enterprise.frament;


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

import com.enterprise.donorapplication.R;


public class NotifyFragment extends Fragment {

    private final static String SERVER_URL="serverIP";
    private final static String ACCESSINTOKEN="accesstoken";

    Spinner city_sp;
    Spinner blood_sp;
    Button shfaq;
    ArrayAdapter<CharSequence> adapter;

    public NotifyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_notify,container,false);

        city_sp = (Spinner) rootview.findViewById(R.id.spinner_city);
        blood_sp = (Spinner) rootview.findViewById(R.id.spinner_blood);
        shfaq = (Button) rootview.findViewById(R.id.button_notify);
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.country_array,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_sp.setAdapter(adapter);
        city_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected_val = city_sp.getSelectedItem().toString();
                Toast.makeText(getActivity(), selected_val,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.blood_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        blood_sp.setAdapter(adapter);
        blood_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean selectionControl = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (selectionControl) {
                    String selected_val = blood_sp.getSelectedItem().toString();
                    Toast.makeText(getActivity(), selected_val, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Select", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


        return rootview;
    }
    private void sendMessage() {




    }
}