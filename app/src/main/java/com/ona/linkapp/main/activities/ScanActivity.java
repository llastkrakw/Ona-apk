package com.ona.linkapp.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.ona.linkapp.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private int SCAN_RESULT = 10;
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            mScannerView.setFlash(false);
            mScannerView.setAutoFocus(true);
            setContentView(mScannerView);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result rawResult) {

        // Do something with the result here
        Intent intent =new Intent();
        intent.putExtra("URL", rawResult.getText());
        setResult(SCAN_RESULT, intent);
        finish();//finishing activity

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}