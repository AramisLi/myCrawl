package com.aramis.library.component.camera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * PermissionsUtils
 * Created by Aramis on 2017/5/4.
 */

public class PermissionsUtils {
    public final static int RequestCode_Camera = 3201;

    public void askCameraPermission(Activity activity) {
        ArrayList<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Boolean[] booleens = new Boolean[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            booleens[i] = checkPermission(activity, permissionList.get(i));
        }
        for (int i = 0; i < booleens.length; i++) {
            if (booleens[i]) {
                permissionList.remove(i);
            }
        }
        String[] array = new String[permissionList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = permissionList.get(i);
        }
        ActivityCompat.requestPermissions(activity, array, RequestCode_Camera);
    }

    public boolean checkPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
