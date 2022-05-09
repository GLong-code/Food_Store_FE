package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class FreshFood extends Food implements MonthInterface{
    LocalDate now = LocalDate.now();

    public FreshFood(int id, String name, int quantity,int foodClass, String importDay, String expiryDay) {
        super(id, name, quantity, foodClass, importDay, expiryDay);
        price = 20000*monthBetween(importDay,now)* attribute;
        className = "Fresh";
    }

    public FreshFood(String name, int foodClass, int quantity, String importDay, String expiry, double atribute) {
        super(name, foodClass, quantity, importDay, expiry, atribute);
        price = 20000*monthBetween(importDay,now)*atribute;
        className = "Fresh";
    }

    public FreshFood(int id, String name, int foodClass, int quantity, String importDay, String expiry, double atribute) {
        super(id, name, foodClass, quantity, importDay, expiry, atribute);
        price = 20000*monthBetween(importDay,now)*atribute;
        className = "Fresh";
    }

    public void setPrice(){
        price = 20000*monthBetween(importDay,now)* attribute;
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
