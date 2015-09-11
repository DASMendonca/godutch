package psoc.com.godutch;

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

    protected Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
}
