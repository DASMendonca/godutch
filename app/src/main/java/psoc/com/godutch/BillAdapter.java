package psoc.com.godutch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import psoc.com.godutch.model.Line;
import psoc.com.godutch.model.Person;

/**
 * Created by asmen on 09/09/2015.
 */
public class BillAdapter extends ArrayAdapter<Line> {

    Context mContext;
    int mLayoutResourceId;
    Line[] lines = null;

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

       // FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
         //       mContext.getLayoutParams().WRAP_CONTENT, mContext.getLayoutParams().WRAP_CONTENT);

        //using this layoutParams instead just for testing purposes
         FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(30, 30);

        //add the people that are set to share this bill
        for(Person person : line.getBill().getPersons()){
            Button button = new Button(people.getContext()); //not sure if it is the right context
            button.setBackgroundResource(R.drawable.button_circle_design);
            button.setText(person.getShortName());
            button.setLayoutParams(layoutParams);
        }



        return row;
    }
}
