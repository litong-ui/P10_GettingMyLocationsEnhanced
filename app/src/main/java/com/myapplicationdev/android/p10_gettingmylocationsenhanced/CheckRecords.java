package com.myapplicationdev.android.p10_gettingmylocationsenhanced;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CheckRecords extends AppCompatActivity {

    Button btnRefresh, btnFavorite;
    TextView tvNumOfRecords;
    ListView lvRecords;
    ArrayAdapter<String> aa;
    ArrayList<String> alString;
    String folderLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records);

        btnRefresh = findViewById(R.id.btnRefresh);
        btnFavorite = findViewById(R.id.btnFavorites);
        tvNumOfRecords = findViewById(R.id.textViewNumOfRecords);
        lvRecords = findViewById(R.id.listViewRecords);
        alString = new ArrayList<String>();

        folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
        File targetFile = new File(folderLocation, "data.txt");
        if (targetFile.exists() == true){
            int numOfRecords = 0;
            try {
                FileReader reader = new FileReader(targetFile);
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                while (line != null){
                    alString.add(line);
                    numOfRecords += 1;
                    line = br.readLine();
//                    numOfRecords += 1;
//                    alString.add(line);
                }
                tvNumOfRecords.setText("Number of Records: "+ numOfRecords);
                br.close();
                reader.close();
            } catch (Exception e) {
                Toast.makeText(CheckRecords.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            aa = new ArrayAdapter<String>(CheckRecords.this, android.R.layout.simple_list_item_1, alString);
            lvRecords.setAdapter(aa);


        }


    }
}