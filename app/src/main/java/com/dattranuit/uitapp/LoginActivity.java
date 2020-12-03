package com.dattranuit.uitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public EditText edt_usr = null; //= (EditText) findViewById(R.id.username);
    public EditText edt_pass = null;//(EditText) findViewById(R.id.password);
    public ProgressBar pgbar = null; //(ProgressBar) findViewById(R.id.progressBar);

    public String token = "";
    public String ten = "";
    public String usr ="";
    public String pas ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_usr = (EditText) findViewById(R.id.username);
        edt_pass = (EditText) findViewById(R.id.password);
        pgbar = (ProgressBar) findViewById(R.id.progressBar);
        LoadViewView();

        final Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                login.execute();

            }
        });
    }

    public  void LoadViewView() {
        final WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setVisibility(View.GONE);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);

                webView.loadUrl("javascript: var $=jQuery.noConflict();" +
                        "if($('div.g-recaptcha').length)" +
                        "{" +
                        "var sitekey = $('div.g-recaptcha').attr('data-sitekey');" + // getting the site-key from existing recaptcha element
                        "$('body > *').remove(); " + // deleting all content of the page
                        "$('body').append('<div id=\"captcha\"></div>'); " + // a div to draw new recaptcha
                        "grecaptcha.render('captcha', {\n" + // render the new recaptcha element in 'captcha' div
                        "    'sitekey' : sitekey,\n" +
                        "    'callback' : function(response){console.log('captchatoken:'+response)},\n" + // log the token on callback
                        "});" +
                        "$('body').css('background-color','transparent');" + // for aesthetic purposes
                        "}");
                webView.setVisibility(View.VISIBLE); // show the webview now since captcha is ready
            }
        });
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage)
            {
                String message = consoleMessage.message();
                if (message.startsWith("captchatoken:"))
                {
                    token = message.substring(13); // removing 'captchatoken:' part from console message
                    //usr = edt_usr.getText().toString();
                    //pas = edt_usr.getText().toString();
                    // now this token can be used in a POST request for logging in
                    //textView.setText("token: " + token);
                }
                return super.onConsoleMessage(consoleMessage);
            }
        });

        webView.loadUrl("https://daa.uit.edu.vn/");
    }
    private void ShowProgressBar(boolean isShow){
        pgbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        //findViewById(R.id.).setVisibility(!isShow ? View.VISIBLE : View.GONE);
    }

    private class Login extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://daa.uit.edu.vn/";
            Connection.Response res = null;
            Map<String, String> ck = null;
            try {
                res = Jsoup.connect(url)
                        .method(Connection.Method.POST)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36")
                        .data("name", usr)
                        .data("pass", pas)
                        .data("form_id", "user_login_block")
                        .data("op", "Đăng nhập")
                        .data("g-recaptcha-response", token)
                        .execute();
                ck = res.cookies();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            try {
                res = Jsoup.connect("https://daa.uit.edu.vn/user/")
                        .method(Connection.Method.GET)
                        .cookies(ck)
                        .execute();
                Document document = res.parse();
                Elements result = document.select("div.field-items");
                if(result.size() == 0)
                    return null;
                Log.d("elements size", Integer.toString(result.size()));
                ten = result.get(1).text();
                Log.d("name ","Tien trinh :" + ten);

            } catch (IOException e) {

                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Toast.makeText()
            //return 1;
            Toast.makeText(LoginActivity.this, "ten", Toast.LENGTH_LONG).show();
        }
    }
}