package vn.hust.edu.storageexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private final String APP_SETTINGS = "app_settings";
    private final String KEY_NAME = "name";
    private final String KEY_SWITCH = "switch";
    private final String KEY_CHECK_BOX = "check_box";

    EditText editName, editContent;
    Switch switch1;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit_name);
        switch1 = findViewById(R.id.switch1);
        checkBox = findViewById(R.id.check_box);
        editContent = findViewById(R.id.edit_text);

//        SharedPreferences prefs = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);
//        editName.setText(prefs.getString(KEY_NAME, "Noname"));
//        switch1.setChecked(prefs.getBoolean(KEY_SWITCH, false));
//        checkBox.setChecked(prefs.getBoolean(KEY_CHECK_BOX, false));

//        try {
//            InputStream is = getResources().openRawResource(R.raw.test1);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line;
//            StringBuilder builder = new StringBuilder();
//            while ((line = reader.readLine()) != null)
//                builder.append(line + "\n");
//            reader.close();
//            Log.v("TAG", builder.toString());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

//        try {
//            InputStream is = getAssets().open("test2.txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line;
//            StringBuilder builder = new StringBuilder();
//            while ((line = reader.readLine()) != null)
//                builder.append(line + "\n");
//            reader.close();
//            Log.v("TAG", builder.toString());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String content = editContent.getText().toString();
                    FileOutputStream fos = openFileOutput("data.txt", MODE_PRIVATE);
                    OutputStreamWriter writer = new OutputStreamWriter(fos);
                    writer.write(content);
                    writer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        findViewById(R.id.button_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = openFileInput("data.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                        builder.append(line + "\n");
                    reader.close();
                    editContent.setText(builder.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        findViewById(R.id.button_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String folderToList = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + pathToFolderOrFile;
                File folderFile = new File(folderToList);
                if (folderFile.isDirectory()) {
                    File[] files = folderFile.listFiles();
                    for (File f : files)
                        Log.v("TAG", f.getAbsolutePath());
                } else if (folderFile.isFile()) {
                    // Open text file
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                Log.v("TAG", "Permission granted");
            else {
                Log.v("TAG", "Permission denied");
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1234);
            }
        }

        findViewById(R.id.button_save2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String content = editContent.getText().toString();
                    FileOutputStream fos = new FileOutputStream(sdPath + "/test1.txt");
                    OutputStreamWriter writer = new OutputStreamWriter(fos);
                    writer.write(content);
                    writer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        findViewById(R.id.button_load2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    FileInputStream fis = new FileInputStream(sdPath + "/test1.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                        builder.append(line + "\n");
                    reader.close();
                    editContent.setText(builder.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1234)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.v("TAG", "Permission granted");
            else
                Log.v("TAG", "Permission denied");
    }

    @Override
    protected void onPause() {
        super.onPause();

//        SharedPreferences prefs = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(KEY_NAME, editName.getText().toString());
//        editor.putBoolean(KEY_SWITCH, switch1.isChecked());
//        editor.putBoolean(KEY_CHECK_BOX, checkBox.isChecked());
//        editor.apply();
    }
}