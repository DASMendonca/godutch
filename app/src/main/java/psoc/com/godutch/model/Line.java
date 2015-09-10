package psoc.com.godutch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import psoc.com.godutch.parsing.B_ReplacerFilter;
import psoc.com.godutch.parsing.L_ReplacerFilter;
import psoc.com.godutch.parsing.LineFilter;
import psoc.com.godutch.parsing.LineWithPriceFilter;
import psoc.com.godutch.parsing.O_ReplacerFilter;

/**
 * Created by asmen on 09/09/2015.
 */
public class Line implements Serializable{


    Bill parent_bill;
    String productDescription = "";
    float price;
    HashMap<Person,Integer> quantities = new HashMap<>();



    int quantity;

    public Line(String input,Bill parent){

        parent_bill = parent;

        Pattern numberPattern = Pattern.compile("^.*?(\\d+[\\.,]\\d{2}).*?$");

        Matcher numberMatcher = numberPattern.matcher(input);

        while(numberMatcher.find()){

            String group = numberMatcher.group(1);

            float value = Float.parseFloat(numberMatcher.group(1));

            if (value > price){

                price = value;

            }

        }

        Pattern namePattern = Pattern.compile("^.*?(\\b(\\p{L}{2,}[\\s(,\\s)-]*)+\\b).*?$");

        Matcher nameMatcher = namePattern.matcher(input);

        while(nameMatcher.find()){

            String string = nameMatcher.group(1);

            string = string.trim();

            if (string.length() > productDescription.length()){

                productDescription = string;

            }

        }

    }



    public String getProductDescription(){


        return productDescription;

    }

    //Getters

    public float getPrice(){

        return price;
    }

    public Bill getBill(){

        return parent_bill;
    }
/*
    public Line(ArrayList<Person> persons, float price, String productName) {
        this.persons = persons;
        this.price = price;
        this.productDescription = productName;
    }

    */
    /*
    public Line(float price, String productName) {
        this.price = price;
        this.productDescription = productName;
    }
    */
/*
    public ArrayList<Person> getPersons() {
        return persons;
    }
    */


    //Setters


    public void setPrice(float price) {
        this.price = price;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }



    public void addQuantity(Person p){


        if (quantities.get(p) != null){

            quantities.put(p,quantities.get(p)+1);
        }

        else{

            quantities.put(p,1);
        }

    }

    public void removeQuantity(Person p){

        if (quantities.get(p) != null){

            if(quantities.get(p) == 1){
                quantities.remove(p);
            }
            else{
                quantities.put(p,quantities.get(p)+1);
            }
        }
    }

    public int quantityForPerson(Person p){

        if (quantities.keySet().size() == 0){

            return 1;
        }

        else if (quantities.get(p) != null){

            return quantities.get(p);

        }

        return 0;

    }

    private int allQuantities(){


        int accumulator = 0;
        for (Person person : quantities.keySet()) {

            accumulator += quantities.get(person);
        }

        return accumulator;

    }

    public float priceForPerson(Person p){


        if (quantities.keySet().size() == 0){

            return price/parent_bill.persons.size();
        }

        else if (quantities.get(p) != null){

            return quantities.get(p)/allQuantities()*price ;

        }

        return 0;

    }
}
