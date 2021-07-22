package com.myapplicationdev.android.p10_gettingmylocationsenhanced;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class CheckRecords extends AppCompatActivity {

    Button btnRefresh, btnFavorite;
    TextView tvNumOfRecords;
    ListView lvRecords;
    ArrayAdapter<String> aa;
    ArrayList<String> alRecords;
    String folderLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records);

        btnRefresh = findViewById(R.id.btnRefresh);
        btnFavorite = findViewById(R.id.btnFavorites);
        tvNumOfRecords = findViewById(R.id.textViewNumOfRecords);
        lvRecords = findViewById(R.id.listViewRecords);
        alRecords = new ArrayList<String>();

        folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
        File targetFile = new File(folderLocation, "data.txt");
        if (targetFile.exists() == true){
            int numOfRecords = 0;
            try {
                FileReader reader = new FileReader(targetFile);
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                while (line != null){
                    alRecords.add(line);
                    numOfRecords += 1;
                    line = br.readLine();
                }
                tvNumOfRecords.setText("Number of Records: "+ numOfRecords);
                br.close();
                reader.close();
            } catch (Exception e) {
                Toast.makeText(CheckRecords.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            aa = new ArrayAdapter<String>(CheckRecords.this, android.R.layout.simple_list_item_1, alRecords);
            lvRecords.setAdapter(aa);
        }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alRecords.clear();

                folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
                File targetFile = new File(folderLocation, "data.txt");
                if (targetFile.exists() == true){
                    int numOfRecords = 0;
                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line != null){
                            alRecords.add(line);
                            numOfRecords += 1;
                            line = br.readLine();
                        }
                        tvNumOfRecords.setText("Number of Records: "+ numOfRecords);
                        br.close();
                        reader.close();
                    } catch (Exception e) {
                        Toast.makeText(CheckRecords.this, "Failed to read!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    aa = new ArrayAdapter<String>(CheckRecords.this, android.R.layout.simple_list_item_1, alRecords);
                    lvRecords.setAdapter(aa);
                }
            }
        });
        lvRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String favRecord = alRecords.get(i);

//                AlertDialog.Builder myBuilder = new AlertDialog.Builder(CheckRecords.this);
//                myBuilder.setMessage("Add this location in your favorite list?");
//                myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        try {
//                            folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
//                            File targetFile_I = new File(folderLocation, "favourites.txt");
//                            FileWriter writer_I = new FileWriter(targetFile_I, true);
//                            writer_I.write(favRecords + "\n");
//                            writer_I.flush();
//                            writer_I.close();
//                        } catch (Exception e){
//                            Toast.makeText(getApplicationContext(), "Failed to write!", Toast.LENGTH_LONG).show();
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                myBuilder.setNegativeButton("No", null);
//                AlertDialog myDialog = myBuilder.create();
//                myDialog.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(CheckRecords.this);
                builder.setMessage("Add this location in your favourite list?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
                                    File targetFile_I = new File(folderLocation, "favourites.txt");
                                    FileWriter writer_I = new FileWriter(targetFile_I, true);
                                    writer_I.write(favRecord + "\n");
                                    writer_I.flush();
                                    writer_I.close();
                                } catch (Exception e){
                                    Toast.makeText(getApplicationContext(), "Failed to write!", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}