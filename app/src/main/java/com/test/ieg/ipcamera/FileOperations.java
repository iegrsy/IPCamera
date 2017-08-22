package com.test.ieg.ipcamera;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ieg on 22.08.2017.
 */

public class FileOperations {

    private Context context;
    private String fileStr;
    private List<CameraInfo> cameraInfos;



    public FileOperations(Context context) {
        this.context = context;
        fileStr = readFile();
        cameraInfos = new ArrayList<CameraInfo>();
        setCameraInfos(fileStr);
    }

    public void setCameraInfos(String str) {
        try {
            JSONObject object = new JSONObject(str);
            JSONArray array = object.getJSONArray("cameras");
            cameraInfos.clear();

            for (int i=0;i<array.length();i++){
                JSONObject object1 = array.getJSONObject(i);

                CameraInfo cameraInfo = new CameraInfo();
                cameraInfo.camname = object1.getString("name");
                cameraInfo.ip = object1.getString("ip");
                cameraInfo.port = object1.getString("port");
                cameraInfo.workspace = object1.getString("workspace");

                cameraInfos.add(cameraInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<CameraInfo> getCameraInfos() {
        return cameraInfos;
    }

    public String readFile() {
        StringBuilder buf = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("cameras.json"), "UTF-8"));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                buf.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buf.toString();
    }
}
