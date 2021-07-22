package com.myapplicationdev.android.p10_gettingmylocationsenhanced;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileWriter;

public class MyService2 extends Service {

//    LocationRequest mLocationRequest;
//    LocationCallback mLocationCallBack;
//    FusedLocationProviderClient client;
//    String folderLocation;
//
//    public MyService2() {
//    }
//
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(3000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setSmallestDisplacement(100);
//
//        mLocationCallBack = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult != null) {
//                    Location data = locationResult.getLastLocation();
//                    double lat = data.getLatitude();
//                    double lng = data.getLongitude();
//
//                    try {
//                        folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
//                        File targetFile_I = new File(folderLocation, "data.txt");
//                        FileWriter writer_I = new FileWriter(targetFile_I, true);
//                        writer_I.write(lat + ", " + lng + "\n");
//                        writer_I.flush();
//                        writer_I.close();
//                    } catch (Exception e){
//                        Toast.makeText(getApplicationContext(), "Failed to write!", Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "failed to receive location", Toast.LENGTH_SHORT).show();
//                }
//            };
//        };
//
//        //Folder creation for Internal Storage
//        folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
//        File folder = new File(folderLocation);
//        if (folder.exists() == false){
//            boolean result = folder.mkdir();
//            if (result = true){
//                Log.d("File Read/Write", "Folder created");
//            }
//        }
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
//        if (checkPermission() == true){
//            client.requestLocationUpdates(mLocationRequest, mLocationCallBack, null);
//
//        } else {
//            ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},0);
//        }
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        client.removeLocationUpdates(mLocationCallBack);
//        Toast.makeText(getApplicationContext(), "Remove Location Update Successfully", Toast.LENGTH_SHORT).show();
//    }
//
//    private boolean checkPermission(){
//        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
//                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
//        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
//                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
//
//        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED && permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}