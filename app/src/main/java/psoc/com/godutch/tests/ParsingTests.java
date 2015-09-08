package psoc.com.godutch.tests;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;

import psoc.com.godutch.parsing.LineFilter;

/**
 * Created by mppl on 08/09/15.
 */
public class ParsingTests extends InstrumentationTestCase {


    private static final String strA = "Nome Consumidor fina1\n" +
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

/*
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
*/

    public void testLineFilter() throws Exception {

        LineFilter filter = new LineFilter();

        ArrayList<String> list = filter.filter(strA);

        assertTrue(list.size() == 15);

    }


}
