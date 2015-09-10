package psoc.com.godutch.model;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import psoc.com.godutch.R;

public class Bill extends Activity implements Serializable{

    private ListView rowListView;

    Line[] lines;

    ArrayList<psoc.com.godutch.model.Person> persons = new ArrayList<psoc.com.godutch.model.Person>();

    public Bill(String s){

        ArrayList<Line> lines = Line.linesFromString(s);

        this.lines = lines.toArray(new Line[lines.size()]);

    }


    public void addPerson(psoc.com.godutch.model.Person p){

        persons.add(p);

    }

    public void removePerson(psoc.com.godutch.model.Person p){

        int indexToRemove = -1;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i) == p) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove >= 0){

            persons.remove(indexToRemove);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        rowListView =(ListView) findViewById(R.id.billListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill, menu);
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
