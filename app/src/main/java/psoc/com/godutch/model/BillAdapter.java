package psoc.com.godutch.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import psoc.com.godutch.R;
import psoc.com.godutch.model.Line;
import psoc.com.godutch.model.Person;

/**
 * Created by asmen on 09/09/2015.
 */
public class BillAdapter extends ArrayAdapter<Line>  implements Serializable{

    Context mContext;
    int mLayoutResourceId;
    Line[] lines = null;
    ArrayList<Person> people = new ArrayList<>();

    public BillAdapter(Context context, int resource, Line[] lines) {
        super(context, resource, lines);

        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.lines = lines;
    }


    @Override
    public Line getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Line line = getItem(position);

        //inflate the layout for a single row
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        row = layoutInflater.inflate(mLayoutResourceId, parent, false);

        //get a reference to different view elements we wish to update
        TextView productDescription = (TextView) row.findViewById(R.id.productDescription);
        TextView productPrice = (TextView) row.findViewById(R.id.rowPrice);
        LinearLayout people = (LinearLayout) row.findViewById(R.id.peopleLayout);

        productDescription.setText(line.getProductDescription());
        productPrice.setText(String.valueOf(line.getPrice()));

        LinearLayout dummyLinearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.bill_person_button, null);
        Button dummyButton = (Button) dummyLinearLayout.findViewById(R.id.bill_person_button);

        //as the dimension values for this button are in dp, we inflate a dummy and git its pixels
        //for the device in usage
        for(int i = 0; i< this.people.size(); i++){
            Button btn = new Button(people.getContext());
            Person person = this.people.get(i);
            btn.setLayoutParams(dummyButton.getLayoutParams());
            btn.setText(person.getShortName());
            btn.setId(i);
            btn.setBackgroundResource(R.drawable.button_circle_design);
            people.addView(btn);
        }

        dummyLinearLayout.removeAllViewsInLayout();


        return row;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

}
