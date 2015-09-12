package psoc.com.godutch.model;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
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

import psoc.com.godutch.BillActivity;
import psoc.com.godutch.R;

/**
 * Created by asmen on 11/09/2015.
 */
public class TotalAdapter extends ArrayAdapter<Person> implements Serializable {

    int mLayoutResourceId;
    Context mContext = null;
    Bill bill;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Line messageLine = (Line) intent.getSerializableExtra("line");


            notifyDataSetChanged();



        }
    };

    public TotalAdapter(Context context, int resource, Bill b) {
        super(context, resource, b.getPersons());

        mContext = context;
        mLayoutResourceId = resource;
        bill = b;



        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,new IntentFilter(BillActivity.kMessage_Changed_Quantities_Name));




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

        Person person = bill.persons.get(position);
        holder.personName.setText(person.getName());
        holder.total.setText(Float.toString(bill.totalForPerson(bill.persons.get(position))));

        return row;
    }

    public static class PersonTotalHolder{
        TextView total;
        TextView personName;
    }



}
