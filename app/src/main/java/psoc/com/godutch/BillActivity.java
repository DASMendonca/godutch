package psoc.com.godutch;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import psoc.com.godutch.R;
import psoc.com.godutch.model.Bill;
import psoc.com.godutch.model.BillAdapter;
import psoc.com.godutch.model.Line;

/**
 * Created by asmen on 10/09/2015.
 */
public class BillActivity extends Activity implements PersonFragment.OnFragmentInteractionListener {

    public static final String INTENT_KEY_BILL_LINES = "billLinesKey";
    public static final String INTENT_KEY_BILL = "billKey";

    private ListView lineListView;
    private BillAdapter billAdapter;
    Bill bill = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill);

        lineListView = (ListView) findViewById(R.id.billListView);

        setBill();

        if (bill != null) {


            Line[] l = bill.getLines();

            if(l.length == 1){


                Line[] newArray = new Line[2];
                newArray[0] = l[0];
                newArray[1] = new Line();
                newArray[1].setPrice(10.0f);
                newArray[1].setProductDescription("Ola");

                l = newArray;
            }


            billAdapter = new BillAdapter(this, R.layout.bill_line, l);
            //billAdapter.setPeople(bill.getPersons());

            if (lineListView != null) {
                lineListView.setAdapter(billAdapter);
            }
        }

    }

    private void setBill() {

        bill = (Bill) getIntent().getExtras().getSerializable(INTENT_KEY_BILL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bill, menu);
        return super.onCreateOptionsMenu(menu);
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
    public void onFragmentInteraction(Uri uri) {



    }
}
