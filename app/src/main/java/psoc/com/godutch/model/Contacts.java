package psoc.com.godutch.model;

import java.util.ArrayList;

/**
 * Created by vmineiro on 12/09/15.
 */
public class Contacts {

    public static ArrayList<Person> list = null;
    private static ArrayList<String> allNames;


    public static ArrayList<Person> allContacts() {

        if (list == null) {
            initializeLists();
        }

        return list;
    }

    private static void initializeLists() {
        list = new ArrayList<Person>();
        allNames = new ArrayList<String>();
        Person person = new Person("Daniel M.", "dm");
        allNames.add("Daniel M.");
        list.add(person);
        person = new Person("José M.", "jm");
        allNames.add("José M.");
        list.add(person);
        person = new Person("Rodolfo R.", "rr");
        allNames.add("Rodolfo R.");
        list.add(person);
        person = new Person("Vitor M.", "vm");
        allNames.add("Vitor M.");
        list.add(person);
    }

    public static ArrayList<String> getAllNames() {

        if (list == null) {
            initializeLists();
        }

        return allNames;
    }
}
