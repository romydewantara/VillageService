package com.example.villageservice.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PortableDocumentFormat {

    private Context context;
    private Bitmap bitmap;

    private int width;
    private int height;

    @SuppressLint("NewApi")
    public PortableDocumentFormat(Context context) {
        this.context = context;
    }

    @SuppressLint("NewApi")
    public void generatePdf(View view, String docName) {
        bitmap = loadBitmap(view, view.getWidth(), view.getHeight());
        drawFile();

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        //save file to internal storage
        String dir_internal_download_path = context.getExternalCacheDir().getPath() + File.separator + "mypdf" + File.separator;

        File file = new File(dir_internal_download_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = dir_internal_download_path + docName + ".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error " + e.toString());
            Toast.makeText(context, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }

    private Bitmap loadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void drawFile() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthFloat = displayMetrics.widthPixels;
        float heightFloat = displayMetrics.heightPixels;
        width = (int) widthFloat;
        height = (int) heightFloat;
    }

}
