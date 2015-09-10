package psoc.com.godutch;

/**
 * Created by asmen on 09/09/2015.
 */
public class Person {

    String name;
    String shortName;

    public Person() {
        name = "";
        shortName = "";
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
