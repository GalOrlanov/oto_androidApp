package com.example.oto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RateFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rate,container,false);

        final RatingBar ratingRatingBar = (RatingBar) rootView.findViewById(R.id.rating);
        Button submitButton = (Button) rootView.findViewById(R.id.submit);
        final TextView ratingDisplayTextView = (TextView) rootView.findViewById(R.id.importaont_rate);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDisplayTextView.setText("Your rating is: " + ratingRatingBar.getRating());
            }
        });
        return inflater.inflate(R.layout.fragment_rate,container,false);

    }
}
