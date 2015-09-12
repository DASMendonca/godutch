package psoc.com.godutch.model;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.googlecode.tesseract.android.TessBaseAPI;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import psoc.com.godutch.GoDutch;
import psoc.com.godutch.Home;
import psoc.com.godutch.R;
import psoc.com.godutch.parsing.B_ReplacerFilter;
import psoc.com.godutch.parsing.L_ReplacerFilter;
import psoc.com.godutch.parsing.LineFilter;
import psoc.com.godutch.parsing.LineWithPriceFilter;
import psoc.com.godutch.parsing.O_ReplacerFilter;



public class Bill implements Serializable{


    ArrayList<Line> lines = new ArrayList<>();

    //Line[] lines = new Line[0];

    ArrayList<Person> persons = new ArrayList<Person>();


    public Bill(Bitmap bm){


        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(Home.DATA_PATH, Home.lang);
        baseApi.setImage(bm);

        String recognizedText = baseApi.getUTF8Text();

        baseApi.end();

        // You now have the text in recognizedText var, you can do anything with it.
        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
        // so that garbage doesn't make it to the display.

        //Log.v(Home.TAG, "OCRED TEXT: " + recognizedText);

        if (Home.lang.equalsIgnoreCase("eng")) {
            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
        }

        //recognizedText = recognizedText.trim();

        this.buildFromString(recognizedText);
    }



    public Bill(String s){

        this.buildFromString(s);
        //this.debugDefaultParams();
    }

    private void buildFromString(String s){

        ArrayList<Line> lines = this.linesFromString(s,this);
        //debugDefaultParams(lines);
        this.lines = lines;

    }

    public Bill(Line[] lines){
        this.lines = new ArrayList<Line>(Arrays.asList(lines));
    }


    public void addPerson(psoc.com.godutch.model.Person p){

        persons.add(p);

        for (Line line : lines) {

            line.personAdded(p);

        }

    }


    public void removePerson(Person p){

        int indexToRemove = -1;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i) == p) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove >= 0){

            persons.remove(indexToRemove);

        }


        for (Line line : lines) {

            line.personRemoved(p);
        }

    }


    public ArrayList<Person> getPersons(){

        return persons;
    }


    public static ArrayList<Line> linesFromString(String inputString,Bill bill){

        LineFilter f1 = new LineFilter();
        O_ReplacerFilter f2 = new O_ReplacerFilter();
        L_ReplacerFilter f3 = new L_ReplacerFilter();
        B_ReplacerFilter f4 = new B_ReplacerFilter();
        LineWithPriceFilter f5 = new LineWithPriceFilter();

        ArrayList<String> input = new ArrayList<>();
        input.add(inputString);


        ArrayList<String> a = f1.filter(input);
        ArrayList<String> b = f2.filter(a);
        ArrayList<String> c = f3.filter(b);
        ArrayList<String> d = f4.filter(c);
        ArrayList<String> e = f5.filter(d);


        ArrayList<String> output = f5.filter(f4.filter(f3.filter(f2.filter(f1.filter(input)))));

        ArrayList<Line> lines = new ArrayList<>();

        for (String lineString:output){

            Line l = new Line(lineString,bill);

            if (l.productDescription.equals("Total") || l.price == 0.00){

                break;

            }
            lines.add(l);

        }

        return lines;
    }


    public ArrayList<Line> getLines() {
        return lines;
    }


    public void addEmptyLine() {


        Line newLine = new Line("New Product",0.00f,this);

        this.lines.add(newLine);


    }


    public float totalForPerson(Person p){

        float totalSoFar = 0.0f;
        for (Line line : lines) {


            totalSoFar += line.priceForPerson(p);

        }

        return totalSoFar;

    }

}
