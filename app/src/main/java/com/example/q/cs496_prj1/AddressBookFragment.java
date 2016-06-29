package com.example.q.cs496_prj1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by q on 2016-06-29.
 */
public class AddressBookFragment extends Fragment{

    private ListView listView;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Address> addr_list;
    AddressAdapter addr_adapter;

    public void AddressBookFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Inflate view group and initialize member variables */
        View rootView = inflater.inflate(R.layout.fragment_addressbook, container, false);
        addr_list = new ArrayList<Address>();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.addr_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        /* Read data from JSON */
        InputStream inputStream = getResources().openRawResource(R.raw.address);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Address ad = null;
        try {
            JSONArray  jArray= new JSONArray(byteArrayOutputStream.toString());
            String name = "";
            String addr = "";
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj_temp = jArray.getJSONObject(i);
                name = jObj_temp.getString("name");
                addr = jObj_temp.getString("addr");
                addr_list.add (new Address(name, addr));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /* Set adapter */
        addr_adapter = new AddressAdapter(getActivity().getApplicationContext(), R.layout.listview_addressitem, addr_list);
        recyclerView.setAdapter(addr_adapter);

        return rootView;
    }
}
