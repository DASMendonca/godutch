package psoc.com.godutch;

import java.util.ArrayList;

/**
 * Created by asmen on 09/09/2015.
 */
public class BillRow {
    String productDescription;
    float price;
    ArrayList<Person> persons;

    public BillRow(ArrayList<Person> persons, float price, String productName) {
        this.persons = persons;
        this.price = price;
        this.productDescription = productName;
    }

    public BillRow(float price, String productName) {
        this.price = price;
        this.productDescription = productName;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void addPerson(Person person){
        persons.add(person);
    }
}
