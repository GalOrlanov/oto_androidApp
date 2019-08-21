package com.example.oto;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.viewHolder> {
private ArrayList<card_item> _card_list;

    public static class viewHolder extends RecyclerView.ViewHolder{
        public TextView driverName;
        public TextView origin;
        public TextView dest;
        public TextView time;
        private Button request_btn;

        public viewHolder(View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driver_name);
            origin = itemView.findViewById(R.id.from_id);
            dest = itemView.findViewById(R.id.to_id);
            time = itemView.findViewById(R.id.time_id);
            request_btn = itemView.findViewById(R.id.request);

        }
    }

    public cardAdapter(ArrayList<card_item> cardList ){
        _card_list=cardList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vSearchDrive = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_searchdrive,parent,false);
        viewHolder vs =new viewHolder(vSearchDrive);
        return vs;
    }
    @Override
    public void onBindViewHolder( viewHolder holder, int position) {
       final card_item currentItem = _card_list.get(position);
       holder.driverName.setText(currentItem.get_name());
       holder.origin.setText((currentItem.get_origin()));
        holder.dest.setText((currentItem.get_dest()));
        holder.time.setText((currentItem.get_time()));
        holder.request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JSONObject obj = new JSONObject();
                 try {

                    obj.put("_id", currentItem.get_id());
                 }catch (JSONException e) {
                     Log.i("Error", "JSON exeption");
                 }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, App.getUrl()+"ride/join",obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.i("card", "onResponse: " + response.toString());
                                   

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




    @Override
    public int getItemCount() {
        return _card_list.size();
    }
}


