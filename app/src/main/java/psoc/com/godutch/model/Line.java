package psoc.com.godutch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import psoc.com.godutch.parsing.B_ReplacerFilter;
import psoc.com.godutch.parsing.L_ReplacerFilter;
import psoc.com.godutch.parsing.LineFilter;
import psoc.com.godutch.parsing.LineWithPriceFilter;
import psoc.com.godutch.parsing.O_ReplacerFilter;

/**
 * Created by mppl on 08/09/15.
 */
public class Line implements Serializable{


    int quantity;
    String name = "";
    float price = -1;



    public Line(String input){

        Pattern numberPattern = Pattern.compile("^.*?(\\d+[\\.,]\\d{2}).*?$");

        Matcher numberMatcher = numberPattern.matcher(input);

        while(numberMatcher.find()){

            String group = numberMatcher.group(1);

            float value = Float.parseFloat(numberMatcher.group(1));

            if (value > price){

                price = value;

            }

        }

        Pattern namePattern = Pattern.compile("^.*?(\\b(\\p{L}{2,}[\\s(,\\s)-]*)+\\b).*?$");

        Matcher nameMatcher = namePattern.matcher(input);

        while(nameMatcher.find()){

            String string = nameMatcher.group(1);

            string = string.trim();

            if (string.length() > name.length()){

                name = string;

            }

        }

    }

    public static ArrayList<Line> linesFromString(String inputString){

        LineFilter f1 = new LineFilter();
        O_ReplacerFilter f2 = new O_ReplacerFilter();
        L_ReplacerFilter f3 = new L_ReplacerFilter();
        B_ReplacerFilter f4 = new B_ReplacerFilter();
        LineWithPriceFilter f5 = new LineWithPriceFilter();

        ArrayList<String> input = new ArrayList<>();
        input.add(inputString);
        ArrayList<String> output = f5.filter(f4.filter(f3.filter(f2.filter(f1.filter(input)))));

        ArrayList<Line> lines = new ArrayList<>();

        for (String lineString:output){

            Line l = new Line(lineString);

            if (l.name.equals("Total") || l.price == 0.00){

                break;

            }
            lines.add(l);

        }

        return lines;
    }



    public String getName(){


        return name;

    }


    public float getPrice(){

        return price;
    }
}
