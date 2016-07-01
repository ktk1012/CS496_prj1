package com.example.q.cs496_prj1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by q on 2016-06-29.
 */


public class AddressBookFragment extends Fragment{

    /* Request for create new address */
    private final static int REQEST_ADD = 1234;         /* Reqeust code for creating new address */
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

        /* Initialize floating button */
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(getActivity(), CreateAddrActivity.class);
                startActivityForResult(intent, REQEST_ADD);
            }
        });


        /* Get JSON string from data directory */
        String jsonString = null;
        try {
            File address_file = new File(getContext().getFilesDir(),
                    getString(R.string.address_file_name));
            if (!address_file.exists()) {
                address_file.createNewFile();
                FileOutputStream fos = new FileOutputStream(address_file);
                fos.write("[]".getBytes());
                fos.close();
            }
            InputStream inputStream;
            inputStream = getContext().openFileInput(getString(R.string.address_file_name));
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader;
                bufferedReader = new BufferedReader(inputStreamReader);
                String receive = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receive = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receive);
                }
                inputStream.close();
                jsonString = stringBuilder.toString();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /* Add address class into Array list and apply Recyler view adapter */
        Address ad = null;
        try {
            JSONArray  jArray= new JSONArray(jsonString);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj_temp = jArray.getJSONObject(i);
                String name = jObj_temp.getString("name");
                String addr = jObj_temp.getString("addr");
                String email = jObj_temp.getString("email");
                String gender = jObj_temp.getString("gender");
                addr_list.add (new Address(name, addr, email, gender));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /* Set adapter */
        addr_adapter = new AddressAdapter(getActivity().getApplicationContext(), R.layout.listview_addressitem, addr_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(addr_adapter);

        /* Add recycler view's item click listener */
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                addr_list.get(position).flip_is_expanded();
                addr_adapter.notifyItemChanged(position);
            }

            @Override
            public void onItemLongPress(View childView, final int position) {
                String name = addr_list.get(position).get_name();
                Snackbar.make(getView(), name, Snackbar.LENGTH_SHORT)
                        .setAction("Remove", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addr_list.remove(position);
                                addr_adapter.notifyItemRemoved(position);
                            }
                        }).show();

            }
        }));

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQEST_ADD) {
            /* Successfully added */
            if (resultCode == 1) {
                String name = intent.getStringExtra("name");
                String num = intent.getStringExtra("num");
                String email = intent.getStringExtra("email");
                String gender = intent.getStringExtra("gender");
                addr_list.add(new Address(name, num, email, gender));
                addr_adapter.notifyItemInserted(addr_list.size() - 1);
                Snackbar.make(getView(), "Add address success", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onDestroyView() {

        /* Serialize array list to JSON array */
        JSONArray jArray = new JSONArray();
        for (int i = 0; i < addr_list.size(); i++) {
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("name", addr_list.get(i).get_name());
                jObj.put("addr", addr_list.get(i).get_addr());
                jObj.put("email", addr_list.get(i).get_email());
                jObj.put("gender", addr_list.get(i).get_gender());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jArray.put(jObj);
        }

        /* Write addresses back to internal storage */
        File address_file = new File(getContext().getFilesDir(),
                getString(R.string.address_file_name));
        try {
            if (!address_file.exists()) {
                address_file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(address_file);
            fos.write(jArray.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }
}
