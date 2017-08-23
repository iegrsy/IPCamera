package com.test.ieg.ipcamera;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.c77.androidstreamingclient.lib.rtp.RtpMediaDecoder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Properties;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by ieg on 22.08.2017.
 */

public class Camera implements View.OnClickListener {

    private static final String TAG = Camera.class.toString();

    private String cameraName;
    private String cameraIp;
    private int cameraPort;
    private SurfaceView surfaceView;
    private int surfaceWidth = 640;
    private int surfaceHeight = 480;
    private Context context;
    private RtpMediaDecoder rtpMediaDecoder;

    public Camera(String cameraName, String cameraIp, int cameraPort, SurfaceView surfaceView, int surfaceWidth, int surfaceHeight, Context context)
    {
        setCameraName(cameraName);
        setCameraIp(cameraIp);
        setCameraPort(cameraPort);
        setSurfaceView(surfaceView);
        this.context = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
    }

    public void start()
    {
        Properties configuration = new Properties();
        try {
            configuration.load(context.getAssets().open("configuration.ini"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rtpMediaDecoder = new RtpMediaDecoder(surfaceView, configuration);

        OutputStream out;
        try {
            out = context.openFileOutput("example.trace", Context.MODE_PRIVATE);
            rtpMediaDecoder.setTraceOutputStream(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        rtpMediaDecoder.setConfiguration(cameraIp, cameraPort, surfaceWidth, surfaceHeight);
        rtpMediaDecoder.start();

        surfaceView.setOnClickListener(this);

        showDebugInfo();
    }

    public void release()
    {
        rtpMediaDecoder.release();
    }

    public void restart()
    {
        rtpMediaDecoder.restart();
    }

    public void showDebugInfo()
    {
        Log.i("configure settings",
                "\nIP:PORT : " + wifiIpAddress() + ":" + rtpMediaDecoder.getDataStreamingPort() +
                        "\nResolution : " + rtpMediaDecoder.getResolution() +
                        "\nTransport Protocol : " + rtpMediaDecoder.getTransportProtocol() +
                        "\nVideo Codec : " + rtpMediaDecoder.getVideoCodec() +
                        "\nBuffer Type : " + rtpMediaDecoder.getBufferType());
    }

    protected String wifiIpAddress()
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endian if needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e(TAG, "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }

    private void setCameraName(String cameraName)
    {
        this.cameraName = cameraName;
    }

    private void setCameraIp(String cameraIp)
    {
        this.cameraIp = cameraIp;
    }

    private void setCameraPort(int cameraPort)
    {
        this.cameraPort = cameraPort;
    }

    private void setSurfaceView(SurfaceView surfaceView)
    {
        this.surfaceView = surfaceView;
    }

    public String getCameraName()
    {
        return cameraName;
    }

    public String getCameraIp()
    {
        return cameraIp;
    }

    public int getCameraPort()
    {
        return cameraPort;
    }

    public SurfaceView getSurfaceView()
    {
        return surfaceView;
    }

    public void pingGoogle()
    {
        String command = "ping -c 3 www.google.com";

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read the output

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                System.out.print(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view)
    {
        restart();
        showDebugInfo();
    }
}
