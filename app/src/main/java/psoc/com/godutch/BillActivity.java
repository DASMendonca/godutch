package psoc.com.godutch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import java.text.NumberFormat;
import java.util.List;

import psoc.com.godutch.R;
import psoc.com.godutch.model.Bill;
import psoc.com.godutch.model.BillAdapter;
import psoc.com.godutch.model.Line;
import psoc.com.godutch.model.Person;
import psoc.com.godutch.model.Person;
import psoc.com.godutch.model.TotalAdapter;

/**
 * Created by asmen on 10/09/2015.
 */
public class BillActivity extends Activity implements PersonFragment.OnFragmentInteractionListener {

    public static final String INTENT_KEY_BILL_LINES = "billLinesKey";
    public static final String INTENT_KEY_BILL = "billKey";

    public static final String kMessage_Changed_Quantities_Name = "lineQuantitiesChangedMessage";
    public static final String kMessage_Changed_Nr_Lines_Name = "nrLinesChangedMessage";
    public static final String kMessage_Changed_Nr_Persons_Name = "nrPersonsChangedMessage";
    public static final String kMessage_Changed_Product_Price = "productPriceChangedMessage";

    private ListView lineListView;
    private ListView totalsListView;
    private TextView total;
    private BillAdapter billAdapter;
    private TotalAdapter totalAdapter;

    Bill bill = null;
    NumberFormat formatter = NumberFormat.getNumberInstance();

    private BroadcastReceiver priceMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalAdapter.notifyDataSetChanged();
            total.setText(formatter.format(bill.getTotal()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        setContentView(R.layout.activity_bill);

        //Custom Action Bar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);

        //Subscribe productPriceChangedMessage
        LocalBroadcastManager.getInstance(this).registerReceiver(priceMsgReceiver, new IntentFilter(BillActivity.kMessage_Changed_Product_Price));

        lineListView = (ListView) findViewById(R.id.billListView);
        totalsListView = (ListView) findViewById(R.id.peopleTotals);
        total = (TextView) findViewById(R.id.billTotal);

        setBill();

        if (bill != null) {

            total.setText(formatter.format( bill.getTotal()));

            billAdapter = new BillAdapter(this, R.layout.bill_line, bill);
            totalAdapter = new TotalAdapter(this,R.layout.person_total,
                   bill);

            //billAdapter.setPeople(bill.getPersons());
            if (lineListView != null) {
                lineListView.setAdapter(billAdapter);
            }

            if(totalsListView != null)
                totalsListView.setAdapter(totalAdapter);
        }

    }

    private void setBill() {

        bill = (Bill) getIntent().getExtras().getSerializable(INTENT_KEY_BILL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bill, menu);

        MenuItem btn = (MenuItem) menu.findItem(R.id.action_search);
        btn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AddPersonDialog dlg = new AddPersonDialog();
                dlg.show(getFragmentManager(), "Person");
                return true;
            }
        });

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
    public void onFragmentInteraction(Uri uri) {



    }

    public void addLineButtonClicked(View button){


        bill.addEmptyLine();

        billAdapter.notifyDataSetChanged();

        //System.out.println("Adding line");

        lineListView.setSelection(lineListView.getCount() - 1);
    }


    public void updatePersons(ArrayList<Person> personsUpdated) {

        ArrayList<Person> tempList = new ArrayList<Person>();
        for (Person p: bill.getPersons()) {
            boolean found = false;
            for (Person p_u: personsUpdated ) {
                if (p.equals(p_u)) {
                    found = true;
                    break;
                }
            }
            if (!found) tempList.add(p);
        }

        for (Person p: tempList) {
            bill.removePerson(p);
        }

        for (Person p_u: personsUpdated) {
            boolean found = false;
            for (Person p: bill.getPersons() ) {
                if (p.equals(p_u)) {
                    found = true;
                    break;
                }
            }
            if (!found) bill.addPerson(p_u);
        }


        billAdapter.notifyDataSetChanged();
        totalAdapter.notifyDataSetChanged();
    }
}
