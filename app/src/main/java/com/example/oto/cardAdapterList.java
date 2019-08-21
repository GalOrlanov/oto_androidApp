package com.example.oto;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class cardAdapterList extends RecyclerView.Adapter<cardAdapterList.viewHolder> {
private ArrayList<card_item_List> _card_list;

    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView driver;
        public TextView origin;
        public TextView dest;
        public TextView time;
        public viewHolder(View itemView) {
            super(itemView);
            dest = itemView.findViewById(R.id.from_upcoming);
            origin = itemView.findViewById(R.id.towhere_upcoming);
            time = itemView.findViewById(R.id.upcoming_TheTime);



        }
    }

    public cardAdapterList(ArrayList<card_item_List> cardList ){
        _card_list=cardList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vSearchList = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_upcoming,parent,false);
        viewHolder vl =new viewHolder(vSearchList);
        return vl;
    }
    @Override
    public void onBindViewHolder( viewHolder holder, int position) {
        card_item_List currentItem = _card_list.get(position);
       holder.origin.setText((currentItem.get_origin()));
        holder.dest.setText((currentItem.get_dest()));
        holder.time.setText((currentItem.get_time()));
    }

    @Override
    public int getItemCount() {
        return _card_list.size();
    }

}


