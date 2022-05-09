package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DryFood extends Food implements MonthInterface{
    LocalDate now = LocalDate.now();

    public DryFood(int id, String name, int foodClass, int quantity, String importDay, String expiry, double attribute) {
        super(id, name, foodClass, quantity, importDay, expiry, attribute);
        price = 10000*monthBetween(importDay,now)*attribute;
        className = "Dry";
    }

    public DryFood(int id, String name, int foodClass , int quantity, String importDay, String expiryDay) {
        super(id, name, foodClass, quantity, importDay, expiryDay);
        price = 10000*monthBetween(importDay,now)* attribute;
        className = "Dry";
    }

    public DryFood(String name, int quantity, int foodClass,String importDay, String expiryDay, double attribute) {
        super(name, quantity, foodClass, importDay, expiryDay, attribute);
        price = 10000*monthBetween(importDay,now)*attribute;
        className = "Dry";
    }

    public void setPrice(){
        price = 10000*monthBetween(importDay,now)* attribute;
    }

    @Override
    public double monthBetween(String before, LocalDate after) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dBefore = LocalDate.parse(before,formatter);
        double monthBetween = ChronoUnit.MONTHS.between(dBefore,after);
        if (monthBetween<0){
            return 0;
        }
        return monthBetween+1;
    }

    public String getClassName() {
        return className;
    }
}
