package psoc.com.godutch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
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
            Context context,AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }



    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();


        b = (Button) this.findViewById(R.id.button);
        counter = (TextView) this.findViewById(R.id.counter);
        nameView = (TextView) this.findViewById(R.id.nameLabel);
        mainButtonCircleView = this.findViewById(R.id.mainButtonCircle);


        inflated = true;

        refreshViews();

        if(line != null && person != null && counter != null){

            counter.setText(String.valueOf(line.quantityForPerson(person)));

        }


        b.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {

                line.addQuantity(person);

                refreshViews();







            }
        });

        b.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;

            }
        });
    }

    private void refreshViews() {


        if(line != null && person != null && inflated){

            counter.setText(String.valueOf(line.quantityForPerson(person)));

            if (line.quantityForPerson(person) == 0 ){

                counter.setAlpha(0.0f);
                mainButtonCircleView.setBackgroundResource(R.drawable.button_circle_design_gray);

            }
            else{


                mainButtonCircleView.setBackgroundResource(R.drawable.button_circle_design_blue);
                counter.setAlpha(1.0f);

            }
            if(line.allPersonsHaveOneAsQuantity()){

                counter.setAlpha(0.0f);
            }

        }



    }

    public void setPerson(Person person) {
        this.person = person;



        refreshViews();

        if(line != null && person != null && counter != null){

            counter.setText(String.valueOf(line.quantityForPerson(person)));

        }

        if(this.nameView != null){

            nameView.setText(person.getShortName());

        }
    }

    public void setLine(Line line) {
        this.line = line;


        refreshViews();

        if(line != null && person != null && counter != null){

            counter.setText(String.valueOf(line.quantityForPerson(person)));

        }


    }
}