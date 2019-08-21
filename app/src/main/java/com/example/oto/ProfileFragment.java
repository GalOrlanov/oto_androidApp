package com.example.oto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    TextView bigName;
    TextView name;
    TextView phone;
    TextView address;
    TextView email;
    String _name = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String TAG = "profileFragment";
        Log.i(TAG, App.getUID() + "      ,     " + App.getToken());
         //getUserDetails();
        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

   bigName= rootView.findViewById(R.id.proflie_name_big);
   name= rootView.findViewById(R.id.profile_name);
   phone= rootView.findViewById(R.id.profile_phone);
        email = rootView.findViewById(R.id.profile_email);


        bigName.setText(App.getFirstName());
        name.setText(App.getFirstName());
        email.setText(App.getEmail());
        phone.setText(App.getPhone());


       // Log.i("asdasdasd", "onCreateView: " + App.getFirstName());

        return rootView;

    }
}

