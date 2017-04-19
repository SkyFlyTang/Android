package com.pb.fileprovider.test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnpermission;
    private Button btnopenimagedebug;
    private Button btnopenimagerelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnopenimagerelease = (Button) findViewById(R.id.btn_open_image_release);
        this.btnopenimagedebug = (Button) findViewById(R.id.btn_open_image_debug);
        this.btnpermission = (Button) findViewById(R.id.btn_permission);
        btnpermission.setOnClickListener(this);
        btnopenimagedebug.setOnClickListener(this);
        btnopenimagerelease.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_permission:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0);
                }
                break;
            case R.id.btn_open_image_debug:

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download" + File.separator + "debug.jpg";
                File file = new File(filePath);
                if (!file.exists()){
                    return;
                }
                Uri uri = FileProvider.getUriForFile(MainActivity.this, StringUtils.AUTHOR_DEBUG, file);
                intent.setDataAndType(uri, "image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }


                break;
            case R.id.btn_open_image_release:
                Intent intentRelease = new Intent();
                intentRelease.setAction(Intent.ACTION_VIEW);
                String fileString = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Test" + File.separator + "release.jpg";
                File fileRelease = new File(fileString);
                if (!fileRelease.exists()){
                    return;
                }
                Uri uriRelease = FileProvider.getUriForFile(MainActivity.this, StringUtils.AUTHOR_RELEASE, fileRelease);
                intentRelease.setDataAndType(uriRelease, "image/*");
                intentRelease.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (intentRelease.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentRelease);
                }

                break;
        }
    }
}
