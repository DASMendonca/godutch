package psoc.com.godutch.parsing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mppl on 08/09/15.
 */
public abstract class Filter {

    public abstract ArrayList<String> filter(String string);


    protected ArrayList<String> stringsMatchesPattern(String pattern,String input){


        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher(input);

        ArrayList<String> toReturn = new ArrayList<String>();

        while(m.find()){

            //System.out.print();

            /*
            if (m.group(1) == null){
                break;
            }
            */

            toReturn.add(input.substring(m.start(),m.end()).trim().replace("\n","").replaceAll("[\\s]{2,}"," "));

        }

        return toReturn;

    }

}
