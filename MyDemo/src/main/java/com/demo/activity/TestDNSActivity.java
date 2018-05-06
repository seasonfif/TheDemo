package com.demo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.R;
import com.sogou.dnsguard.DNSGuard;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dns;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TestDNSActivity extends BaseActivity{

    private static final String TAG = "TestDNSActivity";
    private OkHttpClient client;
    private EditText et;
    private Button btn_dns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = new OkHttpClient
                .Builder()
                .dns(new MyDns())
                .build();
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_test_dns);
        et = (EditText) findViewById(R.id.edit);
        et.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et.setHint("");
            }
        });
        btn_dns = (Button) findViewById(R.id.btn_dns);
        btn_dns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useInOkHttp();
            }
        });
    }

    private void useInOkHttp() {
        String url = et.getHint().toString().trim();
        if (TextUtils.isEmpty(url)){
            url = et.getText().toString().trim();
        }
        if (TextUtils.isEmpty(url)){
            Toast.makeText(this, "url不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", "response.code() == " + response.code());
                Log.e("TAG", "response.body() == " + response.body().string());
            }
        });
    }

    private class MyDns implements Dns {
        @Override
        public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            return DNSGuard.getsInstance().lookup(hostname);
        }
    }
}
