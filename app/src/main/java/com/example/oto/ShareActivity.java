package com.example.oto;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class ShareActivity extends AppCompatActivity {

    String TAG = "placeAutoComplete";

///Vkiew elements
    //suggest button
    Button btnSuggest;
    final Context context = this;
    private Button button;

   //Auto complete
    String origin_ac;
    String dest_ac;


    //time
    EditText chooseTime;
    TimePickerDialog timePickerDialog;
    String time;
    String ampm;
    int currentHour;
    int currentMinute;


    //date
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    Calendar c2;
    EditText chooseDate;
    String date;


    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share);


        //Initialize views
        chooseTime = findViewById(R.id.time_suggest);
        btnSuggest = findViewById(R.id.suggest_btn);


        /*start autocomplete */
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCp5mD7PtTX4ikEnIhSBXPQwp45ze4qLAE");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment_from = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.from_ac_suggest);
        //add to autocomplete
        AutocompleteSupportFragment autocompleteFragment_to = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.to_ac_suggest);

        // Specify the types of place data to return.
        autocompleteFragment_from.setPlaceFields(Arrays.asList(Place.Field.ADDRESS,Place.Field.NAME));
        autocompleteFragment_from.setHint("From:");
        autocompleteFragment_from.setCountry("il");

        autocompleteFragment_to.setPlaceFields(Arrays.asList(Place.Field.ADDRESS,Place.Field.NAME));
        autocompleteFragment_to.setHint("To:");
        autocompleteFragment_to.setCountry("il");
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment_from.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //txtView.setText(place.getName()+","+place.getId());
                Log.i(TAG, "Place: " + place.getAddress() + ", " + place.getId());
                origin_ac = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment_to.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getAddress() + ", " + place.getId());
                dest_ac = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        /*finish autocomplete */
        //time dialog
        chooseTime=findViewById(R.id.time_suggest);
        chooseTime.setOnTouchListener(new View.OnTouchListener() {
            int callCount=0;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Boolean isShowing = true;
                    if (callCount == 0) {
                        c2 = Calendar.getInstance();
                        currentHour = c2.get(Calendar.HOUR_OF_DAY);
                        currentMinute = c2.get(Calendar.MINUTE);
                        timePickerDialog = new TimePickerDialog(ShareActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay >= 12) {
                                    ampm = " PM";
                                } else {
                                    ampm = " AM";
                                }
                                timePickerDialog.updateTime(currentHour,currentMinute);
                                time=String.format("%02d:%02d", hourOfDay, minute);
                                chooseTime.setText(time + ampm);
                            }
                        }, currentHour, currentMinute, true);
                    }
                    timePickerDialog.show();
                }
                return false;
            }
        });
        //Date dialog
        chooseDate=findViewById(R.id.date_suggest);
        chooseDate.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    calendar = Calendar.getInstance();
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                  final int month = calendar.get(Calendar.MONTH);
                   final int year = calendar.get(Calendar.YEAR);
                    datePickerDialog = new DatePickerDialog(ShareActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mYear, int mMonth, int mday) {
                             datePickerDialog.updateDate(calendar.get(Calendar.DAY_OF_MONTH),month,year);
                            date=mYear + "-" + (mMonth + 1) + "-" + mday;
                            chooseDate.setText(date);
                        }
                    }, day, month, year);
                    datePickerDialog.updateDate(year,month,day);
                    datePickerDialog.show();

                }
                return false;
            }
        });



        btnSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//json create
                final JSONObject obj = new JSONObject();
                try {
                    obj.put("origin", origin_ac);
                    obj.put("dest", dest_ac);
                    obj.put("date",date);
                    obj.put("time", time);
                    obj.put("driver", App.getUID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 Log.i("bgcng",App.getUID());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, App.getUrl()+"ride", obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.i(TAG, "onResponse: " + response.toString());

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                                    // set title
                                    alertDialogBuilder.setTitle("Suggest A Ride");

                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage("Thanks for suggesting a ride!  ")
                                            .setCancelable(false)
                                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                    // if this button is clicked, close
                                                    // current activity
                                                    ShareActivity.this.finish();
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();

                                    // show it
                                    alertDialog.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i(error.getMessage(), "HttpPost() - onErrorResponse() ");
                            }
                        }) {
                    @Override
                    public byte[] getBody() {
                        String stringobj = obj.toString();
                        return stringobj.getBytes();
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", App.getToken());
                        return headers;
                    }

                };

                RequestQueue queue = Volley.newRequestQueue(App.getContext());
                queue.add(jsonObjectRequest);


            }

        });
    }
/*
    public void openSuggestResult() {
        Intent intent = new Intent(this, SuggestResultActivity.class);
        startActivity(intent);
    }

    public void openRegisterStep1Activity() {
        Intent intent = new Intent(this, RegisterStep1Activity.class);
        startActivity(intent);
    }
*/

}