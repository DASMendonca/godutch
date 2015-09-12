package psoc.com.godutch.model;

import java.io.Serializable;

/**
 * Created by asmen on 09/09/2015.
 */
public class Person implements Serializable{

    String name = "";
    String shortName = "";


    public Person() {
    }

    public Person(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


}
