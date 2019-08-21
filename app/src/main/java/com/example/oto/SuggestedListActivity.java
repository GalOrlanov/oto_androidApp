package com.example.oto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuggestedListActivity extends AppCompatActivity {
    private final int EMPTY = 0;
    private RecyclerView _RecyclerView;
    private RecyclerView.Adapter _Adapter;
    private RecyclerView.LayoutManager _LayoutManger;

    //json vars
    String objStr;
    JSONObject resObj;


    private String Tag = "suggestedList";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.backgroung_searchlist);

        final ProgressDialog pDialog = new ProgressDialog(SuggestedListActivity.this);
        pDialog.show();
        pDialog.setMessage("Loading...");
        String url = App.getUrl() + "ride/byUserId";
        final RequestQueue queue = Volley.newRequestQueue(SuggestedListActivity.this);
        final StringRequest request = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("UpcomingList Res ", response);
                Intent intent = new Intent(SuggestedListActivity.this, SearchListActivity.class);
                intent.putExtra("response", response);
                intent.putExtra("topic", "Your Suggested Rides");

                if (response == null || response.length() == 2) {

                    final Dialog dialog = new Dialog(SuggestedListActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog);
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                    lp.x = 150;
                    lp.y = 800;
                    lp.width = 800;
                    lp.height = 500;
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
                } else {

                    pDialog.dismiss();
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();

                AlertDialog.Builder builder = new AlertDialog.Builder(SuggestedListActivity.this);
                View view = LayoutInflater.from(SuggestedListActivity.this).inflate(R.layout.dialog, null);
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

