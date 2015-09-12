package psoc.com.godutch.parsing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mppl on 08/09/15.
 */
public class LineFilter extends Filter {




    public ArrayList<String> filter(ArrayList<String> input){



        ArrayList<String> toReturn = new ArrayList<String>();

        for (String s : input) {

            String[] splits = s.split("\n");

            for (String split : splits) {

                toReturn.add(split);

            }

        }

        return toReturn;
        //return stringsMatchesPattern("(.*)(?:\n) | (.*)(?:$)",input);

    }
}
