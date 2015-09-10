package psoc.com.godutch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.LineNumberReader;
import java.text.AttributedCharacterIterator;

/**
 * Created by asmen on 09/09/2015.
 */
public class BillAdapter extends ArrayAdapter<BillRow> {

    Context mContext;
    int mLayoutResourceId;
    BillRow[] billRows = null;

    public BillAdapter(Context context, int resource, BillRow[] billRows) {
        super(context, resource, billRows);

        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.billRows = billRows;
    }


    @Override
    public BillRow getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BillRow billRow = getItem(position);

        //inflate the layout for a single row
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        row = layoutInflater.inflate(mLayoutResourceId, parent, false);

        //get a reference to different view elements we wish to update
        TextView productDescription = (TextView) row.findViewById(R.id.productDescription);
        TextView productPrice = (TextView) row.findViewById(R.id.rowPrice);
        LinearLayout people = (LinearLayout) row.findViewById(R.id.peopleLayout);

        productDescription.setText(billRow.getProductDescription());
        productPrice.setText(String.valueOf(billRow.getPrice()));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(people.getLayoutParams().WRAP_CONTENT, people.getLayoutParams().WRAP_CONTENT);
        for(Person person : billRow.getPersons()){
            TextView textView = new TextView(people.getContext()); //not sure if it is the right context
            textView.setBackgroundResource(R.drawable.textview_circle_design);
            textView.setText(person.getShortName());
            textView.setLayoutParams(layoutParams);

        }

        return row;
    }
}
