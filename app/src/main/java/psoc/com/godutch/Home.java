package psoc.com.godutch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Home extends Activity {

    private static int CAMERA_REQUEST_CODE = 0;
    private static int REQUEST_CODE_GALLERY = 1;

    protected Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Hide ActionBar
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


                int opt = dlg.getOptionSeleted();

                Intent intent = null;

                switch (opt) {
                    case 0:
                        intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, REQUEST_CODE_GALLERY);
                        break;
                    case 1:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, CAMERA_REQUEST_CODE);
                        }
                        ;
                        break;
                    case 2:
                                /*Intent getPDF = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (getPDF.resolveActivity(getContext().getPackageManager()) != null) {
                                    startActivityForResult(getPDF, CAMERA_REQUEST_CODE);
                                }*/
                        break;
                    default:
                        break;
                }


            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }*/

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
}
