package com.example.oto;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpcomingListActivity extends AppCompatActivity {


    TextView tv;

    //strings for the autocomplete
    static String saveOrigin;
    static String saveDestination;
    static String saveDate;
    static String time;
    static String date;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.backgroung_searchlist);

                final ProgressDialog pDialog = new ProgressDialog(UpcomingListActivity.this);
                pDialog.show();
                pDialog.setMessage("Loading...");
                String url = App.getUrl() + "ride/passenger";
                final RequestQueue queue = Volley.newRequestQueue(UpcomingListActivity.this);
                final StringRequest request = new StringRequest(url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                             Log.i("UpcomingList Res " , response);
                            Intent intent = new Intent(UpcomingListActivity.this, SearchListActivity.class);
                            intent.putExtra("response", response);
                        intent.putExtra("topic", "Upcoming Rides");

                        if(response == null || response.length() == 2){

                           final Dialog dialog = new Dialog(UpcomingListActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
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
                        pDialog.hide();

                        AlertDialog.Builder builder = new AlertDialog.Builder(UpcomingListActivity.this);
                         View view = LayoutInflater.from(UpcomingListActivity.this).inflate(R.layout.dialog,null);
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
}