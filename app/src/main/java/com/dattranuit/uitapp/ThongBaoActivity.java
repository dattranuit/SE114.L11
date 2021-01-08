package com.dattranuit.uitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ThongBaoActivity extends AppCompatActivity {

    public Map<String, String> cookies = null;
    public ArrayList<ThongBao> thongBaoArrayList = null;
    public LoadingDialog loadingDialog = null;
    public ThongbaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = ((Intent) intent).getBundleExtra("BUNDLE");
        if (bundle != null) {
            thongBaoArrayList = (ArrayList<ThongBao>) bundle.getSerializable("Notification");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thong_bao);
        getSupportActionBar().setTitle("THÔNG BÁO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.notify_list);


        if(thongBaoArrayList == null){

            thongBaoArrayList = new ArrayList<>();
            thongBaoArrayList.add(new ThongBao("abc", "a", "c", "a", "a", "a", "a", "a", "a", "s"));
        }
//        else {
//            Log.d("Array", thongBaoArrayList.get(0).getTitle());
//        }

        adapter = new ThongbaoAdapter(this, R.layout.notification_item, thongBaoArrayList);
        listView.setAdapter(adapter);

    }


}