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

        for (Person person : parent_bill.persons) {

            quantities.put(person,1);
        }


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

    public Line(String name,float price,Bill parent){

        this.parent_bill = parent;
        this.productDescription = name;
        this.price = price;

        for (Person person : parent_bill.persons) {

            quantities.put(person,1);
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

    //Setters

    public void setPrice(float price) {
        this.price = price;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void addQuantity(Person p){


        boolean shouldReset = true;


        if (allPersonsHaveOneAsQuantity()){

            for (Person person : quantities.keySet()) {

                quantities.put(person,0);
            }

        }

        quantities.put(p,quantities.get(p)+1);


    }

    public void removeQuantity(Person p){


        quantities.put(p,quantities.get(p)-1);


    }

    public int quantityForPerson(Person p){


        return quantities.get(p);

    }

    private int allQuantities(){


        int accumulator = 0;
        for (Person person : quantities.keySet()) {

            accumulator += quantities.get(person);
        }

        return accumulator;

    }

    public float priceForPerson(Person p){

        return  price*quantityForPerson(p)/allQuantities();

    }

    public boolean allPersonsHaveOneAsQuantity(){


        for (Person person : this.quantities.keySet()) {

            if (this.quantities.get(person) != 1){

                return false;
            }
        }

        return true;

    }

    public void personAdded(Person p) {


        if(allPersonsHaveOneAsQuantity()){

            quantities.put(p,1);
        }
        else{

            quantities.put(p,0);
        }

    }

    public void personRemoved(Person p){


        quantities.remove(p);
    }

}
