package psoc.com.godutch.parsing;

import java.util.ArrayList;

/**
 * Created by mppl on 09/09/15.
 */
public class O_ReplacerFilter extends Filter {


    @Override
    public ArrayList<String> filter(ArrayList<String> input) {



        ArrayList<String> toReturn = new ArrayList<>();
        for (String str : input) {

           String output = replaceChar(str, 'O', '0', new ReplacerCondition() {
                @Override
                Boolean shouldReplace(char previous, char next) {

                    return previous == '.' || previous == ','  || next == '.' || next == ',' || Character.isDigit(previous) || Character.isDigit(next);
                }
            });



            output = replaceChar(output, '0', 'O', new ReplacerCondition() {
                @Override
                Boolean shouldReplace(char previous, char next) {

                    return (Character.isLetter(previous) && (Character.isLetter(next) || Character.isWhitespace(next))) || (Character.isLetter(next) && (Character.isLetter(previous) || Character.isWhitespace(previous)));

                }
            });

            toReturn.add(output);


        }


        return  toReturn;

    }
}
