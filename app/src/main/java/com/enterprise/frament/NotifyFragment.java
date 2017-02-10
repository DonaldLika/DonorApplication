package com.enterprise.frament;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class NotifyFragment extends Fragment implements OnMapReadyCallback{

    private final static String SERVER_URL="serverIP";
    private final static String ACCESSINTOKEN="accesstoken";

    Spinner city_sp;
    Spinner blood_sp;
    Button shfaq;
    GoogleMap mGoogleMap;
    MapView mapView;
    View rootview;

    ArrayAdapter<CharSequence> adapter;

    public NotifyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_notify,container,false);

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootview.findViewById(R.id.map);
        if(mapView!=null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng( 41.327953, 19.819025)).title("Tirana"));

        CameraPosition Tirana = CameraPosition.builder().target(new LatLng( 41.327953, 19.819025)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Tirana));

    }
}