package psoc.com.godutch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mppl on 08/09/15.
 */
public class Bill implements Serializable {

    Line[] lines;
    ArrayList<Person> persons = new ArrayList<Person>();

    public Bill(String s){

        ArrayList<Line> lines = Line.linesFromString(s);

        this.lines = lines.toArray(new Line[lines.size()]);

    }


    public void addPerson(Person p){

        persons.add(p);

    }

    public void removePerson(Person p){

        int indexToRemove = -1;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i) == p) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove >= 0){

            persons.remove(indexToRemove);

        }

    }
}
