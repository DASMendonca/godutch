package psoc.com.godutch.parsing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mppl on 08/09/15.
 */
public class LineFilter extends Filter {




    public ArrayList<String> filter(String input){


        return stringsMatchesPattern("(.*)(?:\n) | (.*)(?:$)",input);

    }
}
