package com.example.chaper07_client.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.example.chaper07_client.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtil {
    // save text data to specific directory
    public static void saveText(String path, String txt) {
        BufferedWriter os = null;
        try {
            os = new BufferedWriter(new FileWriter(path));
            os.write(txt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveTextToFile(Context context, String fileName, String txt) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.Files.FileColumns.MEDIA_TYPE, "text/plain");
        values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
        if (uri != null) {
            try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
                outputStream.write(txt.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // read data from specific directory
    public static String  readText(String path) {
        BufferedReader is = null;
        StringBuffer sb = new StringBuffer();
        try {
            is = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = is.readLine() )!= null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    // save bit data into specific directory as image file
    public static void saveImage(String path, Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap openImage(String path) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    // 检查文件是否存在，以及文件路径是否合法
    public static boolean checkFileUri(Context ctx, String path) {
        File file = new File(path);
        Log.d("Matt", String.format("old path:%s", path));
        if (!file.exists() || !file.isFile() || file.length() < 0) {
            return false;
        }
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(ctx, ctx.getString(R.string.file_provider), file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
