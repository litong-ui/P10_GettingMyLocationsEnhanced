package com.myapplicationdev.android.p10_gettingmylocationsenhanced;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    FusedLocationProviderClient client;
    Button btnStartDetector, btnStopDetector, btnCheckRecords, btnMusic;
    TextView tvLatitude, tvLongitude;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallBack;

    String folderLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartDetector = findViewById(R.id.btnStartDetector);
        btnStopDetector = findViewById(R.id.btnStopDetector);
        btnCheckRecords = findViewById(R.id.btnCheckRecords);
        btnMusic = findViewById(R.id.btnMusic);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        client = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(100);

        mLocationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location data = locationResult.getLastLocation();
                    double lat = data.getLatitude();
                    double lng = data.getLongitude();
                    tvLatitude.setText("Latitude: " + lat);
                    tvLongitude.setText("Longitude: " + lng);
                    Toast.makeText(MainActivity.this, "Location Update Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "failed to receive location", Toast.LENGTH_SHORT).show();
                }
            };
        };

        if (checkPermission() == true) {
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
//                        Toast.makeText(MainActivity.this, "Location is not null", Toast.LENGTH_SHORT).show();
                        tvLatitude.setText("Latitude: " + location.getLatitude());
                        tvLongitude.setText("Longitude: " + location.getLongitude());
                    }else {
                        String msg = "No Last Known Location found";
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Check Permission failed", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},0);
        }

        //Folder creation for Internal Storage
        folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
        File folder = new File(folderLocation);
        if (folder.exists() == false){
            boolean result = folder.mkdir();
            if (result = true){
                Log.d("File Read/Write", "Folder created");
            }
        }

        // Zoom the map, get current location, place marker
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                // show current location
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED){
                    map.setMyLocationEnabled(true);
                }else{
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
            }
        });




        btnStartDetector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission() == true){
//                    Toast.makeText(MainActivity.this, "Check permission successfully", Toast.LENGTH_SHORT).show();
                    client.requestLocationUpdates(mLocationRequest, mLocationCallBack, null);
                    if (folder.exists() == true){
                        try {
                            folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
                            File targetFile_I = new File(folderLocation, "data.txt");
                            FileWriter writer_I = new FileWriter(targetFile_I, true);
                            writer_I.write(tvLatitude.getText().toString() + ", " + tvLongitude.getText().toString()+ "\n");
                            writer_I.flush();
                            writer_I.close();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Check Permission failed", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},0);
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
                }
            }
        });
        btnStopDetector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.removeLocationUpdates(mLocationCallBack);
                Toast.makeText(MainActivity.this, "Remove Location Update Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnCheckRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CheckRecords.class);
                startActivity(i);
            }
        });

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PermissionChecker.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnMusic.getText() == "Music On"){
                    startService(new Intent(MainActivity.this, MyService.class));
                    btnMusic.setText("Music Off");
                } else{
                    stopService(new Intent(MainActivity.this, MyService.class));
                    btnMusic.setText("Music On");
                }
            }
        });


    }

    private boolean checkPermission(){
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED
                || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the read SMS
                    //  as if the btnRetrieve is clicked
                    btnStartDetector.performClick();

                } else {
                    // permission denied... notify user
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}