package psoc.com.godutch.parsing;

import java.util.ArrayList;

/**
 * Created by mppl on 12/09/15.
 */
public class CommaReplacer extends Filter{


    public ArrayList<String> filter(ArrayList<String> input){


        ArrayList<String> toReturn = new ArrayList<String>();

        for (String str : input) {

            String output = replaceChar(str,',',' ',new ReplacerCondition(){

                Boolean shouldReplace(char previous,char next){

                    return  Character.isLetter(previous) || Character.isLetter(next);

                }

            });



            toReturn.add(output);
        }

        return toReturn;
    }

}
