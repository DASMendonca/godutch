package psoc.com.godutch.parsing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mppl on 08/09/15.
 */
public class LineWithPriceFilter extends Filter{


    public ArrayList<String> filter(ArrayList<String> input){


        ArrayList<String> toReturn = new ArrayList<String>();

        Pattern p = Pattern.compile("^.*?(\\b(\\p{L}{2,}[\\s(,\\s)-]*)+\\b)(.*?)(\\d+\\.\\d{2}).*$");

        for (String str: input) {

            Matcher m = p.matcher(str);


            while(m.find()){


                toReturn.add(str);

            }

        }

        return toReturn;






    }


}
