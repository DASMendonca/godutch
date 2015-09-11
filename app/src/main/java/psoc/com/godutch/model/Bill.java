package psoc.com.godutch.model;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import psoc.com.godutch.GoDutch;
import psoc.com.godutch.R;
import psoc.com.godutch.parsing.B_ReplacerFilter;
import psoc.com.godutch.parsing.L_ReplacerFilter;
import psoc.com.godutch.parsing.LineFilter;
import psoc.com.godutch.parsing.LineWithPriceFilter;
import psoc.com.godutch.parsing.O_ReplacerFilter;


import com.joanzapata.pdfview.*;

public class Bill implements Serializable{

    Line[] lines;

    ArrayList<Person> persons = new ArrayList<Person>();


    public Bill(Bitmap bm){

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(GoDutch.DATA_PATH, GoDutch.lang);
        baseApi.setImage(bm);

        String parsedText = baseApi.getUTF8Text();

        this.buildFromString(parsedText);

    }

    public Bill(InputStream is){

        try {


            /*
            PDFView pdfView = new PDFView();
            pdfView.fromAsset("dan.pdf")
                    .pages(0, 2, 1, 3, 3, 3)
                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();*/
            /*
            PDFFile f = new PDFFile(ByteBuffer.wrap(IOUtils.toByteArray(is)));

            PDFPage page = f.getPage(0);

            float wi = page.getWidth();
            float hei = page.getHeight();

            RectF clip = null;

            Bitmap bi = page.getImage((int) (wi * 1.25), (int) (hei * 1.25), clip);

*/

            PDDocument pdf = PDDocument.load(is, true);

            PDFRenderer renderer = new PDFRenderer(pdf);

            Bitmap bm = renderer.renderImage(0);

            //Bitmap bm = renderer.renderImage(0,(float)0.5, Bitmap.Config.RGB_565);

            TessBaseAPI baseApi = new TessBaseAPI();
            baseApi.setDebug(true);
            baseApi.init(GoDutch.DATA_PATH, GoDutch.lang);
            baseApi.setImage(bm);

            String parsedText = baseApi.getUTF8Text();

            this.buildFromString(parsedText);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public Bill(String s){

        this.buildFromString(s);
    }

    private void buildFromString(String s){

        ArrayList<Line> lines = this.linesFromString(s,null);
        //debugDefaultParams(lines);
        this.lines = lines.toArray(new Line[lines.size()]);

    }

    private void debugDefaultParams(ArrayList<Line> lines) {
        if(GoDutch.DEBUG){
            Line line = new Line();
            line.price = 4.5f;
            line.productDescription = "Example 1";
            lines.add(line);

            line = new Line();
            line.price = 10.5f;
            line.productDescription = "Example 2";
            lines.add(line);

            Person person = new Person("Daniel M.", "dm");
            this.addPerson(person);
            person = new Person("José M.", "jm");
            this.addPerson(person);
            person = new Person("Rodolfo R.", "rr");
            this.addPerson(person);
            person = new Person("Vitor M.", "vm");
            this.addPerson(person);

        }
    }

    public Bill(Line[] lines){
        this.lines = lines;
    }


    public void addPerson(psoc.com.godutch.model.Person p){

        persons.add(p);

    }


    public void removePerson(psoc.com.godutch.model.Person p){

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


    public Line[] getLines() {
        return lines;
    }
}
