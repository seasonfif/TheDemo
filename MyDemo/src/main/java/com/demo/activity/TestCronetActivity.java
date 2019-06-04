package com.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demo.R;
import com.demo.util.BmpUtil;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TestCronetActivity extends BaseActivity{

    private static final String TAG = "TestCronetActivity";
    private CronetEngine engine;
    private Executor executor;
    private EditText et;
    private Button btn_cronet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        engine = new CronetEngine.Builder(this).build();
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_test_cronet);
        et = (EditText) findViewById(R.id.edit);
        et.setText("http://www.baidu.com");
        et.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et.setHint("");
            }
        });
        btn_cronet = (Button) findViewById(R.id.btn_cronet);
        btn_cronet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                forRequest();
                BmpUtil.testBmpOOM(TestCronetActivity.this);
            }
        });
    }

    private void forRequest(){

        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        final WritableByteChannel channel = Channels.newChannel(data);

        UrlRequest.Callback callback = new UrlRequest.Callback() {
            @Override
            public void onRedirectReceived(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, String s) throws Exception {
                Log.e(TAG, "onRedirectReceived:"+urlResponseInfo.getHttpStatusText());
                urlRequest.followRedirect();
            }

            @Override
            public void onResponseStarted(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo) throws Exception {
                Log.e(TAG, "onResponseStarted:"+urlResponseInfo.getHttpStatusText());
                urlRequest.read(ByteBuffer.allocateDirect(32 * 1024));
            }

            @Override
            public void onReadCompleted(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, ByteBuffer byteBuffer) throws Exception {

                Log.e(TAG, "onReadCompleted:"+urlResponseInfo.getHttpStatusText());
                Log.e(TAG, "onReadCompleted:"+byteBuffer.toString());
                byteBuffer.flip();
                channel.write(byteBuffer);

//                data.write(byteBuffer.array());
                byteBuffer.clear();
                urlRequest.read(byteBuffer);
            }

            @Override
            public void onSucceeded(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo) {

                Log.e(TAG, "onSucceeded:"+urlResponseInfo.getHttpStatusText());
                Log.e(TAG, "onSucceeded:"+urlResponseInfo.getReceivedByteCount());

                String s = new String(data.toByteArray());
                Log.e(TAG, "onSucceeded:"+s);
                Log.e(TAG, "onSucceeded:"+Thread.currentThread().getName());
            }

            @Override
            public void onFailed(UrlRequest urlRequest, UrlResponseInfo urlResponseInfo, CronetException e) {
                Log.e(TAG, "onFailed:"+e.getMessage());
            }
        };

        UrlRequest request = engine.newUrlRequestBuilder(et.getText().toString(), callback, executor).build();
        request.start();
    }

}
