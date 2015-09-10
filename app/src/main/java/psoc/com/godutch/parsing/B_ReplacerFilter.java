package psoc.com.godutch.parsing;

import java.util.ArrayList;

/**
 * Created by mppl on 09/09/15.
 */
public class B_ReplacerFilter extends Filter {


    @Override
    public ArrayList<String> filter(ArrayList<String> input) {



        ArrayList<String> toReturn = new ArrayList<>();
        for (String str : input) {

            toReturn.add(replaceChar(str, 'B', '8', new ReplacerCondition() {
                @Override
                Boolean shouldReplace(char previous, char next) {

                    return previous == '.' || previous == ','  || next == '.' || next == ',' || Character.isDigit(previous) || Character.isDigit(next);

                }
            }));


        }


        return  toReturn;

    }
}
