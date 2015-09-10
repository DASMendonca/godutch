package psoc.com.godutch.parsing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mppl on 08/09/15.
 */
public abstract class Filter {

    public abstract ArrayList<String> filter(ArrayList<String> string);


    protected ArrayList<String> stringsMatchesPattern(String pattern,ArrayList<String> input){




        ArrayList<String> toReturn = new ArrayList<String>();


        for (String str: input) {

            Pattern p = Pattern.compile(pattern);

            Matcher m = p.matcher(str);


            while(m.find()){

                //System.out.print();

            /*
            if (m.group(1) == null){
                break;
            }
            */

                toReturn.add(str.substring(m.start(),m.end()).trim().replace("\n","").replaceAll("[\\s]{2,}"," "));

            }

        }



        return toReturn;

    }


    protected String replaceChar(String original , char toReplace, char replaceWith, ReplacerCondition condition){


        String output = "";
        char previousChar = '\0';
        char nextChar = '\0';
        final int len = original.length();
        for (int i = 0; i < len; i++) {

            if (i == 0) {

                previousChar = '\0';

            }
            else{

                previousChar = original.charAt(i-1);

            }
            if (i == len-1){

                nextChar = '\0';
            }
            else{

                nextChar = original.charAt(i+1);

            }

            char current = original.charAt(i);
            if (current == toReplace) {

                if (condition.shouldReplace(previousChar,nextChar)){

                    current = replaceWith;
                }

            }


            output += current;
        }

        return output;




    }

}
