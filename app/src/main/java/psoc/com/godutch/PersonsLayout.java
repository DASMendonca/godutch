package psoc.com.godutch;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

    public Line line;

    public Person person;


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

        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {


        super.onLayout(changed, left, top, right, bottom);




        b.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;

            }
        });
    }
}