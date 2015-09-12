package psoc.com.godutch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import psoc.com.godutch.model.Line;
import psoc.com.godutch.model.Person;

public class PersonsLayout extends FrameLayout{



    Button b;
    TextView counter;
    TextView nameView;
    View mainButtonCircleView;
    boolean inflated = false;
    private Line line;
    private Person person;

    boolean detached = false;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Line messageLine = (Line) intent.getSerializableExtra("line");

            if (messageLine != line){
                return;
            }



            boolean personPresent = false;
            for (Person currentPerson : line.getBill().getPersons()) {

                if (currentPerson.equals(person)){

                    personPresent = true;
                    break;

                }
            }

            if (!personPresent)  return;



           updateViewsState();

        }
    };




    public PersonsLayout(Context context) {
        super(context);
    }

    public PersonsLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PersonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    public PersonsLayout(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();


        Context context = getContext();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter(BillActivity.kMessage_Changed_Quantities_Name));


        b = (Button) this.findViewById(R.id.button);
        counter = (TextView) this.findViewById(R.id.counter);
        nameView = (TextView) this.findViewById(R.id.nameLabel);
        mainButtonCircleView = this.findViewById(R.id.mainButtonCircle);


        inflated = true;

        updateViewsState();

        if (line != null && person != null && counter != null) {

            counter.setText(String.valueOf(line.quantityForPerson(person)));

        }


        b.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {

                line.addQuantity(person);

                updateViewsState();

                //Broadcast Message to other views interested in it

                Intent intent = new Intent(BillActivity.kMessage_Changed_Quantities_Name);
                intent.putExtra("line", line);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);


            }
        });


        b.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                line.removeQuantity(person);
                updateViewsState();

                Intent intent = new Intent(BillActivity.kMessage_Changed_Quantities_Name);
                intent.putExtra("line", line);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                return true;
            }
        });

    }

    private void updateViewsState() {

        if(line != null && person != null && inflated){

            counter.setText(String.valueOf(line.quantityForPerson(person)));


            if(line.quantityForPerson(person) == 0){

                mainButtonCircleView.setBackgroundResource(R.drawable.button_circle_design_gray);
            }
            else{

                mainButtonCircleView.setBackgroundResource(R.drawable.button_circle_design_blue);
            }




            if(line.allPersonsHaveOneAsQuantity() || line.quantityForPerson(person) == 0){

                counter.setAlpha(0.0f);

            }
            else {

                counter.setAlpha(1.0f);
            }




        }

    }

    public void setPerson(Person person) {
        this.person = person;



        updateViewsState();

        if(line != null && person != null && counter != null){

            counter.setText(String.valueOf(line.quantityForPerson(person)));

        }

        if(this.nameView != null){

            nameView.setText(person.getShortName());

        }
    }

    public void setLine(Line line) {
        this.line = line;


        updateViewsState();

        if(line != null && person != null && counter != null){

            counter.setText(String.valueOf(line.quantityForPerson(person)));

        }


    }

    @Override
    protected void onDetachedFromWindow() {

        Context context = getContext();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        super.onDetachedFromWindow();

        detached = true;



    }
}