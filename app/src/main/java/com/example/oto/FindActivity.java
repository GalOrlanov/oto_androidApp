package com.example.oto;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.app.ProgressDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.api.Places;

public class FindActivity extends AppCompatActivity {
    String TAG = "placeAutoComplete";
    Button mDatebtn;
    Calendar calendar;
    Button searchBtn;
    TextView tv;
    DatePickerDialog datePickerDialog;
    EditText chooseTime;
    EditText chooseDate;
    TimePickerDialog timePickerDialog;
    Calendar c2;
    int currentHour;
    int currentMinute;
    String ampm;


    //strings for the autocomplete
    static String saveOrigin;
    static String saveDestination;
    static String saveDate;
    static String time;
    static String date;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(final Bundle savedInstanceState) {

        searchBtn = findViewById(R.id.search_ride);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find);

        /*start autocomplete */
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCp5mD7PtTX4ikEnIhSBXPQwp45ze4qLAE");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment from = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.find_from_ac);
        //add to autocomplete
        AutocompleteSupportFragment to = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.find_to_ac);

        // Specify the types of place data to return.
        from.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME));
        to.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME));
        to.setHint("To:");
        to.setCountry("il");
        from.setHint("From:");
        from.setCountry("il");
        // Set up a PlaceSelectionListener to handle the response.
        from.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //txtView.setText(place.getName()+","+place.getId());
                saveOrigin = place.getAddress();
                Log.i(TAG, "Origin Place: " + saveOrigin);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        to.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                saveDestination = place.getAddress();
                Log.i(TAG, "Destenation Place: " + saveDestination);
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        /*finish autocomplete */

        //Date
        chooseDate = findViewById(R.id.date);
        chooseDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(FindActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mYear, int mMonth, int mday) {
                            date = mday + "/" + (mMonth + 1) + "/" + mYear;
                            chooseDate.setText(date);

                        }
                    }, day, month, year);
                    datePickerDialog.updateDate(year, month, day);
                    datePickerDialog.show();

                }
                return false;
            }
        });

        //TIMER
        chooseTime = findViewById(R.id.time);
        chooseTime.setOnTouchListener(new View.OnTouchListener() {
            int callCount = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Boolean isShowing = true;
                    if (callCount == 0) {
                        c2 = Calendar.getInstance();
                        currentHour = c2.get(Calendar.HOUR_OF_DAY);
                        currentMinute = c2.get(Calendar.MINUTE);
                        timePickerDialog = new TimePickerDialog(FindActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timePickerDialog.updateTime(currentHour, currentMinute);
                                time = String.format("%02d:%02d", hourOfDay, minute);
                                chooseTime.setText(time);
                            }
                        }, currentHour, currentMinute, true);
                    }
                    timePickerDialog.show();
                }
                return false;
            }
        });
        searchBtn = findViewById(R.id.search_ride);

/// set the date with time and date edit texts


        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Log.i(TAG, "onClick: " + App.getToken());
                saveDate = date + " " + time + ":14 GMT";

                final ProgressDialog pDialog = new ProgressDialog(FindActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.setProgressStyle(pDialog.STYLE_SPINNER);
                pDialog.show();
                String url = App.getUrl() + "ride/search?date=" + saveDate + "&dest=" + saveDestination + "&origin=" + saveOrigin;
                final RequestQueue queue = Volley.newRequestQueue(FindActivity.this);
                final StringRequest request = new StringRequest(url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(FindActivity.this, SearchRideActivity.class);
                       intent.putExtra("response", response);
                        Log.i(TAG, "onResponse: " + response);
                        if(response == null || response.length() == 2){

                           final Dialog dialog = new Dialog(FindActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                           dialog.setContentView(R.layout.dialog);
                            Window dialogWindow = dialog.getWindow();
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                            dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                            lp.x = 150;
                            lp.y= 800;
                            lp.width=800;
                            lp.height=500;
                            dialogWindow.setAttributes(lp);
                            TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
                            text.setText("No matches!");

                            Button dialogButton1 = (Button) dialog.findViewById(R.id.dialog_button);
                            dialogButton1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            pDialog.dismiss();
                            dialog.show();
                        }

                        else {

                            pDialog.dismiss();
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());
                        pDialog.hide();

                        AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                         View view = LayoutInflater.from(FindActivity.this).inflate(R.layout.dialog,null);
                        TextView errTv = (TextView) view.findViewById(R.id.dialog_text);

                        builder.setView(view);
                        builder.create().show();




                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", App.getToken());
                        return headers;
                    }
                };


                queue.add(request);
                queue.start();


            }


        });
    }
}