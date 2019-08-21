package com.example.oto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {
    private final int EMPTY = 0;
private RecyclerView _RecyclerView;
private RecyclerView.Adapter _Adapter;
private RecyclerView.LayoutManager _LayoutManger;


//json vars
    String objStr;
    JSONObject resObj;


private String Tag="searchList";
private TextView topicTV;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.backgroung_searchlist );
        topicTV=findViewById(R.id.topic);
        topicTV.setText(getIntent().getStringExtra("topic"));




        objStr= getIntent().getStringExtra("response");
        if(objStr == null ){
        Log.i("asdasdasdasdasd" , "obj is null");
        }

            try {
                resObj = new JSONObject(objStr);
            } catch (JSONException e) {
            }

            Log.i(Tag, "->" + objStr);




            ArrayList<card_item_List> cardList = new ArrayList<>();

if( resObj==null){
   // getSupportFragmentManager().beginTransaction().replace(R.id.root_drives,new nomatches_fragment()).commit();

}
else {
    try{
    JSONArray upcomingArray = resObj.getJSONArray("rides");
    for (int i = 0; i < upcomingArray.length(); i++) {

        JSONObject currentUp = upcomingArray.getJSONObject(i);
        JSONArray passengers = currentUp.getJSONArray("passengers");
        String id=currentUp.getString("_id");
        String origin=currentUp.getString("origin");
        String dest=currentUp.getString("dest");
        String time=currentUp.getString("time");
        String driver=currentUp.getString("driver");
        cardList.add(new card_item_List(id,origin,dest,time,driver));


    }
        } catch (JSONException e) {
        }
    }




        _RecyclerView =findViewById(R.id.recyclerView);
        _RecyclerView.setHasFixedSize(true);
        _LayoutManger = new LinearLayoutManager(this);
        _Adapter = new cardAdapterList(cardList);

        _RecyclerView.setLayoutManager(_LayoutManger);
        _RecyclerView.setAdapter(_Adapter);
    }



    }

