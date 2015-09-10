package psoc.com.godutch.model;

import java.io.Serializable;

/**
 * Created by mppl on 08/09/15.
 */
public class Person implements Serializable{

    String name;
    String email;

    Person(String name){

        this.name = name;

    }
}
