package com.test.ieg.ipcamera;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

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
    private SurfaceView surfaceView;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        camera = new Camera("camera","10.50.218.92","5006",surfaceView,getApplicationContext());
        camera.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        camera.release();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
