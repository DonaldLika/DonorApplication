package com.enterprise.donorapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enterprise.Session.SessionManager;


public class WelcomeFragment extends Fragment {


    private TextView tv_view;
    private SessionManager session;


    public WelcomeFragment() {



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_welcome,container,false);
        tv_view =(TextView) rootview.findViewById(R.id.username);
        session = new SessionManager(this.getActivity());
        String name = session.getUsername();
        tv_view.setText("Welcome " + name);


        return rootview;
    }


}
