package psoc.com.godutch.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import psoc.com.godutch.PersonsLayout;
import psoc.com.godutch.R;

/**
 * Created by asmen on 09/09/2015.
 */
public class BillAdapter extends ArrayAdapter<Line>  implements Serializable{

    Activity activity;
    int mLayoutResourceId;
    Line[] lines = null;
    ArrayList<Person> people = new ArrayList<>();

    public BillAdapter(Activity a, int resource, Line[] lines) {
        super(a, resource, lines);

        this.mLayoutResourceId = resource;
        this.activity = a;
        this.lines = lines;


        Person person = new Person("Daniel M.", "dm");
        this.people.add(person);
        person = new Person("José M.", "jm");
        this.people.add(person);
        person = new Person("Rodolfo R.", "rr");
        this.people.add(person);
        person = new Person("Vitor M.", "vm");
        this.people.add(person);
    }


    @Override
    public Line getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(activity.getApplicationContext());
            view = layoutInflater.inflate(mLayoutResourceId, parent, false);

            LinearLayout container = (LinearLayout) view.findViewById(R.id.peopleLayout);


            for (Person p :
                 this.people) {


                //View toAdd =
                //
                PersonsLayout layout = (PersonsLayout) layoutInflater.inflate(R.layout.persons_layout, container, true);

                layout.person = p;

                //Setup here

                //container.addView(toAdd);



            }

        } else{

            view = convertView;
        }


        Line line = getItem(position);


        //get a reference to different view elements we wish to update
        TextView productDescription = (TextView) view.findViewById(R.id.productDescription);
        TextView productPrice = (TextView) view.findViewById(R.id.rowPrice);
        LinearLayout people = (LinearLayout) view.findViewById(R.id.peopleLayout);

        productDescription.setText(line.getProductDescription());
        productPrice.setText(String.valueOf(line.getPrice()));

        // Update the layout






        return view;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

}
