package com.dattranuit.uitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    public Map<String, String> cookies = null;
    public SinhVien sinhVien = null;
    public LoadingDialog loadingDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            cookies = (Map<String, String>) bundle.getSerializable("Cookies");
            Log.d("Cookies", cookies.toString());
        }
        loadingDialog = new LoadingDialog(MainActivity2.this);
        loadingDialog.StartLoadingDialog();
        SinhVienLoad sinhViendata = new SinhVienLoad();
        sinhViendata.execute();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main2);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_tkb:
                            fragment = new TkbFragment();
                            getSupportActionBar().setTitle("Thoi Khoa Bieu");
                            break;
                        case R.id.nav_lichthi:
                            fragment = new LichthiFragment();
                            getSupportActionBar().setTitle("Lich Thi");
                            break;
                        case  R.id.nav_canhan:
                            fragment = new CanhanFragment();
                            getSupportActionBar().setTitle("Ca Nhan");
                            SendDataSinhVien(fragment);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragment).commit();
                    return true;
                }
            };

    public void SendDataSinhVien(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("TenSV", sinhVien.getHoTen());
        bundle.putString("NgSinh", sinhVien.getNgaySinh());
        bundle.putString("MSSV", sinhVien.getMSSV());
        bundle.putString("LopSH", sinhVien.getLopSH());
        bundle.putString("Khoa", sinhVien.getKhoa());
        bundle.putString("HeDT", sinhVien.getHeDT());
        fragment.setArguments(bundle);
    }

    public  class SinhVienLoad extends AsyncTask<Void, Void, SinhVien>{

        @Override
        protected SinhVien doInBackground(Void... voids) {
            try {
                String url = "https://daa.uit.edu.vn/sinhvien/kqhoctap";
                Connection.Response res = null;
                res = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .cookies(cookies)
                        .execute();
                Document document = res.parse();
                Elements elements = document.select("td > strong");
                if(elements.size() != 0){
                    sinhVien = new SinhVien(
                            elements.get(0).text(), // Hoten
                            elements.get(1).text(), // NgaySinh
                            elements.get(2).text(), // GioiTinh
                            elements.get(3).text(), // MSSV
                            elements.get(4).text(), // LopSH
                            elements.get(5).text(), // Khoa
                            elements.get(6).text(), // BacDT
                            elements.get(7).text()); // HeDT
                    Log.d("Ten", sinhVien.getHoTen());
                    return sinhVien;
                }
                else {
                    return null;
                }
            } catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(SinhVien sinhVien) {
            super.onPostExecute(sinhVien);
            loadingDialog.DismissDialog();
        }
    }
}


