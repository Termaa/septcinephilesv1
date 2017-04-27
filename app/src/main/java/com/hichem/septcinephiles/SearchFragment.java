package com.hichem.septcinephiles;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;


public class SearchFragment extends Fragment {

    private static EditText searchvalue;

    Listener activityCallback;

    public interface Listener {
        public void onButtonClick(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Activity a = (Activity) context;
            activityCallback = (Listener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ToolbarListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.search_fragment_1,
                container, false);
        searchvalue = (EditText) view.findViewById(R.id.seachvalue);

        final Button button = (Button) view.findViewById(R.id.search_go_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });

        return view;
    }

    private void buttonClicked(View v) {
        activityCallback.onButtonClick(searchvalue.getText().toString());
    }

}
