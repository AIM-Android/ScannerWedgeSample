package com.advantech.scannerwedgedemo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static String copyAssetToInternalStorage(Context context, String assetName) {
        File file = new File(context.getFilesDir(), assetName);
        try (InputStream inputStream = context.getAssets().open(assetName);
             OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static String getFilePathByUri_BELOWAPI11(Context context, Uri uri) {
        // 以 content:// 开头的，比如  content://media/external/file/960
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            String path = null;
            String[] projection = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null,
                    null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        return null;
    }

    public static Bitmap tobitmap90(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        // 设置旋转角度
        matrix.setRotate(90);
        // 重新绘制Bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static Bitmap tobitmap(Bitmap bitmap, int width, int height) {

        Bitmap target = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(target);
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, target.getWidth(), target.getHeight()), null);
        return target;
    }

    public static int getHeight(int width, int pageWidthPoint, int pageHeightPoint) {
        double bili = width / (double) pageWidthPoint;
        return (int) (pageHeightPoint * bili);
    }
}