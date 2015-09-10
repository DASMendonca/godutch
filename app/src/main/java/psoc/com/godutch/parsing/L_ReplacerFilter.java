package psoc.com.godutch.parsing;

import java.util.ArrayList;

/**
 * Created by mppl on 09/09/15.
 */
public class L_ReplacerFilter extends Filter{



    public ArrayList<String> filter(ArrayList<String> input){


        ArrayList<String> toReturn = new ArrayList<String>();

        for (String str : input) {

          String output = replaceChar(str,'1','l',new ReplacerCondition(){

               Boolean shouldReplace(char previous,char next){

                   return  Character.isLetter(previous) || Character.isLetter(next);

               }

           });


            output = replaceChar(output, 'l', '1', new ReplacerCondition() {
                @Override
                Boolean shouldReplace(char previous, char next) {


                    return  Character.isDigit(previous) || Character.isDigit(next) || previous == '.' || previous == ',' || next == '.' || next == ',';


                }
            });
            toReturn.add(output);
        }

        return toReturn;
    }

}
