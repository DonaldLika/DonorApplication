package com.enterprise.donorapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class NotifyFragment extends Fragment implements OnMapReadyCallback{
    private final static String SERVER_URL="serverIP";

    private Spinner city_sp;
    private Spinner blood_sp;
    private Button shfaq;
    GoogleMap mGoogleMap;
    MapView mapView;
    View rootview;
    String city_value="";
    String blood_value="";

    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter1;



    public NotifyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_notify,container,false);

        city_sp = (Spinner) rootview.findViewById(R.id.spinner_city);
        blood_sp = (Spinner) rootview.findViewById(R.id.spinner_blood);
        shfaq = (Button) rootview.findViewById(R.id.button_notify);

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

        shfaq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage(blood_value,city_value);
            }
        });


        return rootview;
    }



        //Dergimi i te dhenave ne server
    private void sendMessage(String city,String bloodType) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String jsonStr = "{\"city\":\"" + city + "\",\"type\":\"" + bloodType + "\"}";
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

    private void showText(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    //Vendosja e hartes

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