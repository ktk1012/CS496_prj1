package com.example.q.cs496_prj1;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAddrActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_addr);
        Button bt1 = (Button) findViewById(R.id.AddBtn1);
        EditText ed1 = (EditText) findViewById(R.id.edit_num);
        ed1.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String name = ((EditText) findViewById(R.id.edit_name)).getText().toString();
                String num = ((EditText) findViewById(R.id.edit_num)).getText().toString();
                Spinner genderSpinner = (Spinner) findViewById(R.id.edit_gender);
                String gender = genderSpinner.getSelectedItem().toString();
                String email = ((EditText) findViewById(R.id.edit_email)).getText().toString();
                if (name.length() == 0 || num.length() == 0 ||
                        gender.length() == 0 || email.length() == 0) {
                    Snackbar.make(v, "Please fill the name or phone number", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return;
                }
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("name", name);
                extra.putString("num", num);
                extra.putString("email", email);
                extra.putString("gender", gender);
                intent.putExtras(extra);
                /* Write to file */
                setResult (1, intent);
                finish();
            }
        });
    }
}
