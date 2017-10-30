package com.example.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;





public class MainActivity extends AppCompatActivity {
    private Button bt;
    private TextView text;
    private static final String JSON_URL_GET = "http://www.93.gov.cn/93app/data.do?channelId=2&startNum=2";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.bt);
        text = (TextView) findViewById(R.id.text);
        init();
    }

    //创建一个类方法进行获取资源
    class MyAsyncTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            String urlStr = params[0];//通过0获得传过来的url
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            String jsonStr = "";
            try {
                url = new URL(JSON_URL_GET );
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                int responseCode = httpURLConnection.getResponseCode();
                if(responseCode==200){
                    InputStream inputStream = httpURLConnection.getInputStream();//读取
                    byte[] bt = new byte[1024];
                    int len = 0;
                    while((len = inputStream.read(bt))!=-1){
                        jsonStr+=new String(bt,0,len);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonStr;
        }
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //s就是接受来的结果
            text.setText(s);
        }

    }

    //创建方法
    public void init(){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute(JSON_URL_GET);
            }
        });
    }
}
