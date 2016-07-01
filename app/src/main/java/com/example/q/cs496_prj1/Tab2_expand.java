package com.example.q.cs496_prj1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by q on 2016-06-29.
 */
public class Tab2_expand extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_expand);

        Intent intent=new Intent(this.getIntent());
        String s=intent.getStringExtra("address");
        String image_name = "image" + s;
        ImageView imageview=(ImageView) findViewById(R.id.imageView);
        int id = this.getResources().getIdentifier(image_name, "drawable", this.getPackageName());
        imageview.setImageResource(id);
    }

    public void back(View v) {
        this.onBackPressed();
    }
}
