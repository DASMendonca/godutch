package psoc.com.godutch.model;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

import psoc.com.godutch.BillActivity;
import psoc.com.godutch.PersonsLayout;
import psoc.com.godutch.R;

/**
 * Created by asmen on 09/09/2015.
 */
public class BillAdapter extends ArrayAdapter<Line> implements Serializable {

    Activity activity;
    int mLayoutResourceId;
    NumberFormat formatter = NumberFormat.getNumberInstance();


    Bill bill;
    //ArrayList<Person> people = new ArrayList<>();

    public BillAdapter(Activity a, int resource, Bill bill) {

        super(a, resource, bill.getLines());

        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        this.mLayoutResourceId = resource;
        this.activity = a;
        this.bill = bill;
    }


    @Override
    public Line getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view;
        Holder holder;

        if (convertView == null) {

            holder = new Holder();

            LayoutInflater layoutInflater = LayoutInflater.from(activity.getApplicationContext());
            view = layoutInflater.inflate(mLayoutResourceId, parent, false);

            //addButtons(position, view, holder);


            holder.nameLabel = (EditText) view.findViewById(R.id.productDescription);
            holder.priceLabel = (EditText) view.findViewById(R.id.rowPrice);


            view.setTag(holder);


        } else {

            view = convertView;
            holder = (Holder) view.getTag();
        }


        Line line = bill.getLines().get(position);


        if (holder.nameWatcher != null) {

            holder.nameLabel.removeTextChangedListener(holder.nameWatcher);
            holder.priceLabel.removeTextChangedListener(holder.priceWatcher);

        }


        holder.nameLabel.setText(line.getProductDescription());
        holder.priceLabel.setText(formatter.format(line.getPrice()));


        holder.nameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                bill.getLines().get(position).setProductDescription(newText);
            }
        };
        holder.priceWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                bill.getLines().get(position).setPrice(Float.parseFloat(newText));

                Intent intent = new Intent(BillActivity.kMessage_Changed_Product_Price);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            }
        };
        // Update the layout

        //Edit product description field listener

        holder.nameLabel.addTextChangedListener(holder.nameWatcher);
        holder.priceLabel.addTextChangedListener(holder.priceWatcher);

        LinearLayout container = (LinearLayout) view.findViewById(R.id.peopleLayout);
        container.removeAllViews();
        addButtons(position, view, holder);


        return view;
    }

    private void addButtons(int position, View row, Holder holder) {

        LayoutInflater layoutInflater = LayoutInflater.from(activity.getApplicationContext());
        LinearLayout container = (LinearLayout) row.findViewById(R.id.peopleLayout);

        holder.persons = bill.persons;
        for (int i = 0; i < bill.persons.size(); i++) {

            Person p = bill.persons.get(i);

            Log.e("DBUG","Adding button for person "+p.getName());

            PersonsLayout layout = (PersonsLayout) layoutInflater.inflate(R.layout.persons_layout, container, false);

            layout.setPerson(p);
            layout.setLine(this.bill.getLines().get(position));
            holder.personsLayout.add(layout);
            container.addView(layout);


        }
    }


    public class Holder {


        public TextWatcher nameWatcher;
        public TextWatcher priceWatcher;
        public TextView nameLabel;
        public TextView priceLabel;
        public ArrayList<PersonsLayout> personsLayout = new ArrayList<PersonsLayout>();
        public ArrayList<Person> persons;


    }

}
