package psoc.com.godutch.tests;

import android.content.res.Resources;
import android.test.InstrumentationTestCase;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import psoc.com.godutch.model.Bill;
import psoc.com.godutch.model.Line;
import psoc.com.godutch.parsing.B_ReplacerFilter;
import psoc.com.godutch.parsing.L_ReplacerFilter;
import psoc.com.godutch.parsing.LineFilter;
import psoc.com.godutch.parsing.LineWithPriceFilter;
import psoc.com.godutch.parsing.O_ReplacerFilter;

/**
 * Created by mppl on 08/09/15.
 */
public class ParsingTests extends InstrumentationTestCase {


    public static final String strA = "Nome Consumidor fina1\n" +
            "    Fatura simplificada FS 130/126239\n" +
            "    Datat2015-07-23 Hora:21:54\n" +
            "    üt Artigo IV Tota1\n" +
            "    1 Menu Especialídades 23 e 6.35\n" +
            "    Cachorro Surbias\n" +
            "    Asua 33 çl\n" +
            "    Taxa Base IVA Total\n" +
            "    23.00 e 5.16 e1.19 e B.35\n" +
            "    Tota1 e 6.35\n" +
            "    Troco e 0.00\n" +
            "    Emp: GERENTE 21z54\n" +
            "    Processado por computador\n" +
            "    Obrigad0, Thank you\n" +
            "    IVA incluido";


    public static  final String strB = "Nome ---~~-~-A__-__.-__\"_,___~_,__-__\n" +
            "    N.C. 227179552\n" +
            "    Morada\n" +
            "    Fatura simplificada FS 008/544417\n" +
            "    2015-09-08\n" +
            "    VQt,Artigp Unídade 1v TotaJ\n" +
            "    1 PRATO CARNE DIA E 3.20 23 e 3.20\n" +
            "    l SQPA E 0.70 23 E 0579\n" +
            "    1'() t Ei I ez :3 _ Ea(3\n" +
            "    Hora 13122\n" +
            "    processado por computador\n" +
            "    IVA incluido\n" +
            "    W5t9-Processado por programa\n" +
            "    certificado n. 0071/AT";
/*

    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
*/

    public void testLineFilter() throws Exception {

        LineFilter filter = new LineFilter();


        ArrayList<String> array = new ArrayList<String>();

        array.add(strA);
        ArrayList<String> list = filter.filter(array);

        assertTrue(list.size() == 15);

    }


    public  void testLineNumberFilter() throws Exception{


        LineFilter f1 = new LineFilter();
        LineWithPriceFilter f2 = new LineWithPriceFilter();


        ArrayList<String> inputArray = new ArrayList<String>();
        inputArray.add(strA);

        ArrayList<String> array2 = f1.filter(inputArray);

        ArrayList<String> array3 = f2.filter(array2);


        assertTrue(array3.size() == 2);


    }


    public void testSimpleLReplacement() throws Exception{


        L_ReplacerFilter f1 = new L_ReplacerFilter();


        ArrayList<String> input = new ArrayList<String>();

        String strToTest = " 1 ola1";

        input.add(strToTest);


        ArrayList<String> output = f1.filter(input);

        assertTrue(output.get(0).equals(" 1 olal"));

    }

    public void testSimpleOReplacement() throws Exception{


        O_ReplacerFilter f1 = new O_ReplacerFilter();


        ArrayList<String> input = new ArrayList<String>();

        String strToTest = " Ola O.27 3.O1";

        input.add(strToTest);


        ArrayList<String> output = f1.filter(input);

        assertTrue(output.get(0).equals(" Ola 0.27 3.01"));

    }

    public void testFullFilterStackTest1() throws Exception{


        LineFilter f1 = new LineFilter();
        LineWithPriceFilter f5 = new LineWithPriceFilter();
        O_ReplacerFilter f2 = new O_ReplacerFilter();
        L_ReplacerFilter f3 = new L_ReplacerFilter();
        B_ReplacerFilter f4 = new B_ReplacerFilter();


        ArrayList<String> original = new ArrayList<String>();

        original.add(strA);



        ArrayList<String> output = f1.filter(original);

        output = f2.filter(output);

        output = f3.filter(output);

        output = f4.filter(output);

        output = f5.filter(output);



        assertTrue(output.size() == 3);


    }


    public void testFullFilterStackTest2() throws Exception{


        LineFilter f1 = new LineFilter();
        LineWithPriceFilter f5 = new LineWithPriceFilter();
        O_ReplacerFilter f2 = new O_ReplacerFilter();
        L_ReplacerFilter f3 = new L_ReplacerFilter();
        B_ReplacerFilter f4 = new B_ReplacerFilter();


        ArrayList<String> original = new ArrayList<String>();

        original.add(strB);



        ArrayList<String> output = f1.filter(original);

        output = f2.filter(output);

        output = f3.filter(output);

        output = f4.filter(output);

        output = f5.filter(output);



        assertTrue(output.size() == 2);


    }


    public void testLinesParsingTest1() throws Exception{



        ArrayList<Line> lines = Bill.linesFromString(strA, null);

        assertTrue(lines.size() == 1);


        Line l = lines.get(0);
        assertTrue(Math.abs(l.getPrice() - 6.35) < 0.0001);
        assertTrue(l.getProductDescription().equals("Menu Especialídades"));


    }

    public void testLinesParsingTest2() throws Exception{



        ArrayList<Line> lines = Bill.linesFromString(strB,null);

        assertTrue(lines.size() == 2);


        Line l1 = lines.get(0);
        Line l2 = lines.get(1);

        assertTrue(Math.abs(l1.getPrice() - 3.20) < 0.0001);
        assertTrue(l1.getProductDescription().equals("PRATO CARNE DIA"));

        assertTrue(Math.abs(l2.getPrice()- 0.70) < 0.0001);
        assertTrue(l2.getProductDescription().equals("SQPA"));


    }

    public void testLinesParsingTest3() throws Exception{


/*
        ArrayList<Line> lines = Line.linesFromString(strC);

        assertTrue(lines.size() == 2);


        Line l1 = lines.get(0);
        Line l2 = lines.get(1);

        assertTrue(Math.abs(l1.getPrice() - 3.20) < 0.0001);
        assertTrue(l1.getProductDescription().equals("PRATO CARNE DIA"));

        assertTrue(Math.abs(l2.getPrice()- 0.70) < 0.0001);
        assertTrue(l2.getProductDescription().equals("SQPA"));
*/

    }


    public void testPDFParsing() throws Exception{


        //Resources.getSystem().openRawResource(R.raw.encomenda);

       InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/dan.pdf");

        // FileInputStream s = new FileInputStream(new File(R.raw.encomenda));
        Bill b = new Bill(is);




        assertTrue(b.getLines().length > 1);

    }
}
