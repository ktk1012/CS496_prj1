package com.example.q.cs496_prj1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by q on 2016-06-30.
 */
public class TodoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment, container, false);

        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.todo_fab);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (TodoFragment.this.getActivity(),TodoAddActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
