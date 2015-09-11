package psoc.com.godutch.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

import psoc.com.godutch.R;

/**
 * Created by asmen on 11/09/2015.
 */
public class TotalAdapter extends ArrayAdapter<Person> implements Serializable {

    int mLayoutResourceId;
    Context mContext = null;
    Person[] people = null;

    public TotalAdapter(Context context, int resource, Person[] people) {
        super(context, resource, people);

        mContext = context;
        mLayoutResourceId = resource;
        this.people = people;

    }


    @Override
    public Person getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PersonTotalHolder holder = null;

        if (row == null) {

            holder = new PersonTotalHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            row = layoutInflater.inflate(mLayoutResourceId, parent, false);

            holder.personName = (TextView) row.findViewById(R.id.totalPersonName);
            holder.total = (TextView) row.findViewById(R.id.totalForPerson);


            row.setTag(holder);

        } else{
            holder = (PersonTotalHolder) row.getTag();
        }

        Person person = people[position];
        holder.personName.setText(person.getName());
        holder.total.setText("€ 10.0");

        return row;
    }

    public static class PersonTotalHolder{
        TextView total;
        TextView personName;
    }
}
