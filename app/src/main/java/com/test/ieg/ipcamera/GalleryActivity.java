package com.test.ieg.ipcamera;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.c77.androidstreamingclient.lib.rtp.RtpMediaDecoder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Properties;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    private SurfaceView surfaceView, surfaceView2, surfaceView3;
    private Camera camera, camera2, camera3;
    private LinearLayout linearLayout;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.camera_view, null);
                linearLayout.addView(view1, linearLayout.getChildCount() - 1);
            }
        });


//        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
//        surfaceView2 = (SurfaceView) findViewById(R.id.surfaceView2);
//        surfaceView3 = (SurfaceView) findViewById(R.id.surfaceView3);
//
//        camera = new Camera("camera", "10.50.218.92", 5006, surfaceView, 640, 480, getApplicationContext());
//        camera.start();
//
//        camera2 = new Camera("camera", "10.50.218.92", 5008, surfaceView2, 640, 100, getApplicationContext());
//        camera2.start();
//
//        camera3 = new Camera("camera", "10.50.218.92", 5010, surfaceView3, 640, 200, getApplicationContext());
//        camera3.start();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
//        camera.release();
//        camera2.release();
//        camera3.release();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
