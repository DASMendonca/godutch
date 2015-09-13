package psoc.com.godutch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
import psoc.com.godutch.model.Contacts;
import psoc.com.godutch.model.Person;
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
        File file = new File(_path);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, REQUEST_CAMERA_CODE);

    }

    public void selectTest() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);
        onPhotoTaken(bitmap);
    }


    protected void onPhotoTaken(Bitmap bitmap) {
        _taken = true;

        Bill bill = new Bill(bitmap);

        for (Person p: Contacts.allContacts()) {
            bill.addPerson(p);
        }

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
       // Bundle extras = data.getExtras();
        //Bitmap bitmap = (Bitmap) extras.get("data");

        Bitmap bitmap;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        bitmap = BitmapFactory.decodeFile(_path, options);

        try {
            ExifInterface exif = new ExifInterface(_path);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            Log.v(TAG, "Orient: " + exifOrientation);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            Log.v(TAG, "Rotation: " + rotate);

            if (rotate != 0) {

                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }

            // Convert to ARGB_8888, required by tess
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        } catch (IOException e) {
            Log.e(TAG, "Couldn't correct orientation: " + e.toString());
        }

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

    public void goToMyBills(View view){
        Intent intent = new Intent(this, MyBills.class);
        startActivity(intent);
    }
}
