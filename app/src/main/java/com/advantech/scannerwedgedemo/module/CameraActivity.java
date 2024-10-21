package com.advantech.scannerwedgedemo.module;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;

/**
 * time   : 2024/10/15
 * desc   : Camera
 * version: 1.0
 */
public class CameraActivity extends BaseActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();

    private static String mRootPath;

    private ImageView takePhotoButton;
    private ImageView switchCameraButton;
    private PreviewView previewView;
    private ImageCapture mImageCapture;

    private int mFacing = CameraSelector.LENS_FACING_BACK;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        takePhotoButton = findViewById(R.id.take_photo);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        switchCameraButton = findViewById(R.id.switch_camera);
        switchCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchCamera();
            }
        });
        previewView = findViewById(R.id.preview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            } else {
                startPreview();
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startPreview();
    }

    private void startPreview() {
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                    Preview preview = new Preview.Builder().build();

                    mImageCapture = new ImageCapture.Builder()
                            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                            .setTargetResolution(new Size(640, 480))
                            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                            .build();

                    CameraSelector cameraSelector = new CameraSelector
                            .Builder()
                            .requireLensFacing(mFacing)
                            .build();

                    cameraProvider.unbindAll();

                    Camera camera = cameraProvider.bindToLifecycle(CameraActivity.this, cameraSelector, preview, mImageCapture);
                    preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.getCameraInfo()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (mImageCapture != null) {
            String dirPath = getExternalFilesDir("").getAbsolutePath()
                    + File.separator + "TestPhoto";
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions
                    .Builder(file)
                    .build();

            mImageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    showToast("photo saved success.");
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    showToast("photo saved fail.");
                }
            });
        }
    }

    private void switchCamera() {
        mFacing = mFacing == CameraSelector.LENS_FACING_FRONT
                ? CameraSelector.LENS_FACING_BACK : CameraSelector.LENS_FACING_FRONT;
        startPreview();
    }
}
