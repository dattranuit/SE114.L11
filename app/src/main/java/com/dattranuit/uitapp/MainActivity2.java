package com.dattranuit.uitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    public static Map<String, String> cookies = null;
    public SinhVien sinhVien = null;
    public LoadingDialog loadingDialog = null;
    public ArrayList<Deadline> deadlineArrayList = null;
    public ArrayList<ThongBao> thongBaoArrayList;
    public ArrayList<Lichthi> lichthiArrayList= null;
    public String bangDiem = "";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_notification:
                loadingDialog = new LoadingDialog(MainActivity2.this);
                loadingDialog.StartLoadingDialog();
                thongBaoArrayList = new ArrayList<>();
                NotificationLoad notificationLoad = new NotificationLoad();
                notificationLoad.execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            cookies = (Map<String, String>) bundle.getSerializable("Cookies");
        }

        loadingDialog = new LoadingDialog(MainActivity2.this);
        loadingDialog.StartLoadingDialog();

        SinhVienLoad sinhViendata = new SinhVienLoad();
        sinhViendata.execute();

        DeadlineLoad deadlineData = new DeadlineLoad();
        deadlineData.execute();

        ExamLoad examLoad = new ExamLoad();
        examLoad.execute();
        BangDiemLoad bangDiemLoad = new BangDiemLoad();
        bangDiemLoad.execute();

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
                            SendDataExam(fragment);
                            break;
                        case R.id.nav_diem:
                            fragment = new BangdiemFragment();
                            getSupportActionBar().setTitle("Bang Diem");
                            SendDataBangDiem(fragment);
                            break;
                        case R.id.nav_dealine:
                            fragment = new DeadlineFragment();
                            getSupportActionBar().setTitle("DeadLine");
                            SendDataDeadline(fragment);
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
            //loadingDialog.DismissDialog();
        }
    }

    public class DeadlineLoad extends  AsyncTask<Void, Void, ArrayList<Deadline>>{

        @Override
        protected ArrayList<Deadline> doInBackground(Void... voids) {
            try {

                deadlineArrayList = new ArrayList<>();

                String url = "https://courses.uit.edu.vn/login/index.php";
                Connection.Response tmp = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .execute();

                Document document = tmp.parse();
                Elements tokenElement = document.getElementsByAttributeValue("name", "logintoken");
                String token = tokenElement.attr("value");

                tmp = Jsoup.connect(url)
                        .method(Connection.Method.POST)
                        .cookies(tmp.cookies())
                        .data("username", MainActivity.usr)
                        .data("password", MainActivity.pas)
                        .data("logintoken", token)
                        .execute();
                Map<String, String> cookie = tmp.cookies();


                String url2= "https://courses.uit.edu.vn/calendar/view.php?view=upcoming";
                Connection.Response res = Jsoup.connect(url2)
                        .cookies(cookie)
                        .execute();
                Document doc = res.parse();

                Elements elements = doc.select("a.card-link");

                ArrayList<String> link = new ArrayList<>();

                for(Element element : elements){
                    String temp = element.attr("href");
                    if(temp.contains("assign")){
                        link.add(temp);
                    }
                }
                for(String item : link){
                    Connection.Response response = Jsoup.connect(item)
                            .cookies(cookie)
                            .execute();
                    Document document1 = response.parse();
                    String[] subjectArr = document1.getElementsByTag("h1").text().split("-");
                    String subject = subjectArr[1];
                    String content = document1.getElementsByTag("h2").text();
                    Elements elements1 = document1.getElementsByTag("td");
                    String submitStatus = elements1.get(0).text().contains("No attempt")?"Chưa nộp bài":"Đã nộp bài";
                    String timeDeadline = elements1.get(2).text();
                    String timeRemain = elements1.get(3).text().contains("is overdue")?"Hết hạn":"Còn chờ";

                    deadlineArrayList.add(new Deadline(subject, content, timeDeadline, timeRemain, submitStatus, item));
                    Log.d("Subject", subject);
                }
                if(deadlineArrayList.size() != 0){
                    return deadlineArrayList;
                }
                else{
                    return null;
                }
            } catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<Deadline> deadline) {
            super.onPostExecute(deadline);
        }
    }

    public class NotificationLoad extends AsyncTask<Void, Void, ArrayList<ThongBao>> {
        @Override
        protected ArrayList<ThongBao> doInBackground(Void... voids) {
            try {
                ArrayList<String> link = ListLinkNoti();
                if (link != null) {
                    for (String item : link) {
                        Connection.Response res = null;
                        res = Jsoup.connect(item)
                                .method(Connection.Method.GET)
                                .cookies(cookies)
                                .execute();
                        Document document = res.parse();
                        Elements elements = document.select("p > strong");
                        Element elementTitle = document.getElementById("page-title");
                        String create_time = document.select("div > span").text();
                        ThongBao thongBao = new ThongBao(
                                elementTitle.text(), create_time,
                                elements.get(0).text(), elements.get(1).text(), elements.get(2).text(),
                                elements.get(3).text(), elements.get(4).text(), elements.get(5).text(),
                                elements.get(6).text(), elements.get(7).text()
                        );

                        thongBaoArrayList.add(thongBao);
                        Log.d("Array", thongBaoArrayList.get(0).getTitle());

                    }
                    if(thongBaoArrayList.size() != 0){
                        return thongBaoArrayList;
                    }
                } else {
                    return null;
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ThongBao> thongBaos) {
            super.onPostExecute(thongBaos);
            loadingDialog.DismissDialog();
            SendDataNoti();
        }
    }

    public class ExamLoad extends  AsyncTask<Void, Void, ArrayList<Lichthi>>{

        @Override
        protected void onPostExecute(ArrayList<Lichthi> lichthis) {
            super.onPostExecute(lichthis);
        }

        @Override
        protected ArrayList<Lichthi> doInBackground(Void... voids) {
            try {
                lichthiArrayList = new ArrayList<>();
                String url = "https://daa.uit.edu.vn/ajax-block/lichthi/ajax";
                Connection.Response res = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .cookies(cookies)
                        .ignoreContentType(true)
                        .execute();
                String data = res.body();
                JSONParser parser = new JSONParser();


                Object obj = parser.parse(data);

                JSONArray json = (JSONArray) obj;

                JSONObject jsonObject = (JSONObject) json.get(1);
                String lichthi = jsonObject.get("data").toString();

                Document document = Jsoup.parse(lichthi);
                Elements elements = document.select("tr > td");
                //ArrayList<Lichthi> arrayList = new ArrayList<>();
                for (int i = 0; i < elements.size(); i += 7) {
                    lichthiArrayList.add(new Lichthi(elements.get(i).text(),elements.get(i+1).text(),
                            elements.get(i+2).text(), elements.get(i+3).text(),
                            elements.get(i+4).text(), elements.get(i+5).text(),elements.get(i+6).text()
                    ));
                }
                if(lichthiArrayList != null){
                    Log.d("Arr size", Integer.toString(lichthiArrayList.size()));
                    return lichthiArrayList;
                }else {
                    return null;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public class BangDiemLoad extends AsyncTask<Void,Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loadingDialog.DismissDialog();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String url = "https://daa.uit.edu.vn/sinhvien/kqhoctap";
                Connection.Response res2 = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .cookies(cookies)
                        .execute();
                String result = res2.body();
                Document doc = Jsoup.parse(result);
                String data = doc.getElementsByAttributeValue("bordercolor","#000000").toString();
                data = data.replace("<td>&nbsp;</td>", "");
                data = data.replace("<th>&nbsp;Ghi chú</th>", "");
                data = data.replace("width=\"100%\"", "style=\"margin-left: auto; margin-right: auto;\"");
                data = data.replace("colspan=\"3\"", "colspan=\"6\"");
                data = data.replace("<th>&nbsp;Tên học phần</th>", "");
                data = data.replace("bordercolor=\"#000000\"", "bordercolor=\"000000\"");
                data = data.replace("Mã HP", "MH");
                data = data.replace("Tín chỉ", "TC");
                data = data.replace("Điểm ", "");
                Document document = Jsoup.parse(data);
                document.select("tr > td:eq(2)").remove();
                bangDiem = document.toString();
                return document.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private ArrayList<String> ListLinkNoti() throws IOException, ParseException, JSONException {
        String url = "https://daa.uit.edu.vn/ajax-block/message/ajax";
        Connection.Response res2 = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .cookies(cookies)
                .execute();

        String data = res2.body();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(data);
        JSONArray json = (JSONArray) obj;
        JSONObject jsonObject = (JSONObject) json.get(1);
        String thongBao = jsonObject.get("data").toString();

        Document document = Jsoup.parse(thongBao);
        Elements elements = document.getElementsByTag("a");
        ArrayList<String> arrayList = new ArrayList<>();
        for (Element e : elements) {
            arrayList.add(e.attr("href"));
        }
        return arrayList;
    }

    private void SendDataNoti() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Notification", (Serializable) thongBaoArrayList);
        Intent intent = new Intent(MainActivity2.this, ThongBaoActivity.class);
        intent.putExtra("BUNDLE",bundle);
        startActivity(intent);

    }

    private  void SendDataExam(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putSerializable("ExamList", (Serializable) lichthiArrayList);
        Log.d("Test", Integer.toString(lichthiArrayList.size()));
        fragment.setArguments(bundle);
    }

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

    public void SendDataDeadline(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putSerializable("DeadlineList", (Serializable) deadlineArrayList);
        fragment.setArguments(bundle);
    }

    public  void SendDataBangDiem(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("DataBangDiem", bangDiem);
        fragment.setArguments(bundle);
    }
}


