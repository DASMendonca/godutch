package psoc.com.godutch;

import android.app.ActionBar;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

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

    private ListView lineListView;
    private ListView totalsListView;
    private BillAdapter billAdapter;
    private TotalAdapter totalAdapter;
    Bill bill = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill);

        //Custom Action Bar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);

        lineListView = (ListView) findViewById(R.id.billListView);
        totalsListView = (ListView) findViewById(R.id.peopleTotals);

        setBill();

        if (bill != null) {


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


    public void addLineButtonClicked(View button) {


        bill.addEmptyLine();

        billAdapter.notifyDataSetChanged();

        System.out.println("Adding line");
    }


    public void updatePersons(ArrayList<Integer> personsID) {
        /*
        Person person = null;
        for (Integer id: personsID) {
            switch (id) {
                case 0:
                    person = new Person("Daniel M.", "dm");
                    bill.addPerson(person);
                    break;
                case 1:
                    person = new Person("Daniel M.", "dm");
                    bill.addPerson(person);
                    break;
                case 2:
                    person = new Person("Daniel M.", "dm");
                    bill.addPerson(person);
                    break;
                case 3:
                    person = new Person("Daniel M.", "dm");
                    bill.addPerson(person);
                    break;
            }
            
        }
        */
    }
}
