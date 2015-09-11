package psoc.com.godutch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import psoc.com.godutch.model.Bill;
import psoc.com.godutch.tests.ParsingTests;


public class Home extends Activity {


    protected Button button;
    private static int REQUEST_CAMERA_CODE = 0;
    private static int REQUEST_CODE_GALLERY = 1;
    public final static Boolean DEBUG = true;


    public static final String PACKAGE_NAME = "psoc.com.godutch";
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/GoDutch/";


    public static final String lang = "por";
    private static final String TAG = "GoDutch.java";

    protected String _path;
    protected boolean _taken;

    protected static final String PHOTO_TAKEN = "photo_taken";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Hide Action Bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();


        TextView tv = (TextView) findViewById(R.id.appTitle);
        int height_in_pixels = tv.getLineCount() * tv.getLineHeight(); //approx height text
        tv.setHeight(height_in_pixels);

        button = (Button) findViewById(R.id.btn_new_bill);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptInputDialog dlg = new ReceiptInputDialog();
                dlg.show(getFragmentManager(), "ola");

            }
        });



        //TESS Preparation

        String[] paths = new String[]{DATA_PATH, DATA_PATH + "tessdata/"};

        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }

        }

        // lang.traineddata file with the app (in assets folder)
        // You can get them at:
        // http://code.google.com/p/tesseract-ocr/downloads/list
        // This area needs work and optimization
        prepareOCRForTess();

        _path = DATA_PATH + "/ocr.jpg";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "resultCode: " + resultCode);

        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case 0: //why -1?
                    onCameraPhotoTaken(data);
                    break;
                case 1:
                    if (data != null)
                        onPhotoSelected(data.getData());
                    break;
                default:
                    Log.v(TAG, "User cancelled");
            }
    }



    public void selectFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }


    public void selectFromCamera() {
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);

    }

    public void selectFromPDF() {

    }


    protected void onPhotoTaken(Bitmap bitmap) {
        _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Log.v(TAG, "Before baseApi");

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(DATA_PATH, lang);
        baseApi.setImage(bitmap);

        String recognizedText = baseApi.getUTF8Text();

        baseApi.end();

        // You now have the text in recognizedText var, you can do anything with it.
        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
        // so that garbage doesn't make it to the display.

        Log.v(TAG, "OCRED TEXT: " + recognizedText);

        if (lang.equalsIgnoreCase("eng")) {
            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
        }

        recognizedText = recognizedText.trim();

        //DEBUG
        //recognizedText = ParsingTests.strA;

        Bill bill = new Bill(recognizedText);
        Intent billIntent = new Intent(this, BillActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(BillActivity.INTENT_KEY_BILL, bill);

        billIntent.putExtras(extras);


        //activity
        /*if (recognizedText.length() != 0) {
            field.setText(field.getText().toString().length() == 0 ? recognizedText : field.getText() + " " + recognizedText);
            field.setSelection(field.getText().toString().length());
        }*/

        startActivity(billIntent);
        // Cycle done.
    }

    private void prepareOCRForTess() {
        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
            try {

                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
                //GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(DATA_PATH
                        + "tessdata/" + lang + ".traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                //while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                //gin.close();
                out.close();

                Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
            }
        }
    }

    protected void onCameraPhotoTaken(Intent data) {
        //FROM HERE
        Bundle extras = data.getExtras();
        Bitmap bitmap = (Bitmap) extras.get("data");

        // _image.setImageBitmap( bitmap );

        onPhotoTaken(bitmap);

    }

    protected void onPhotoSelected(Uri uri) {
        Uri selectedImage = uri;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap bill = BitmapFactory.decodeFile(picturePath);

        onPhotoTaken(bill);

    }
}
