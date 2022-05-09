package DAO;

import Common.Common;
import Model.DryFood;
import Model.Food;
import Model.FreshFood;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class FoodModify {
    private static final String path = "http://localhost:8080/api/v1/Products";

    private static final Gson gson = new Gson();

    public ArrayList<Food>getAllProducts() throws IOException {
        URL endpoint = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String result = Common.convertToString(inputStream);
        JSONArray array = new JSONArray(result);
        ArrayList<Food> productsList = new ArrayList<>();

        for(int i = 0; i< array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            int foodClass = object.getInt("foodClass");
            if (foodClass == 1){
                DryFood dryFood = new DryFood(
                        object.getInt("id"),
                        object.getString("name"),
                        object.getInt("foodClass"),
                        object.getInt("quantity"),
                        object.getString("importDay"),
                        object.getString("expiry"),
                        object.getDouble("attribute")
                );
                productsList.add(dryFood);
            }else {
                FreshFood freshFood = new FreshFood(
                        object.getInt("id"),
                        object.getString("name"),
                        object.getInt("foodClass"),
                        object.getInt("quantity"),
                        object.getString("importDay"),
                        object.getString("expiry"),
                        object.getDouble("attribute")
                );
                productsList.add(freshFood);
            }
        }
        return  productsList;
    }

    public ArrayList<Food>findByFoodClass(int foodClass) throws IOException {
        URL endpoint = new URL(path + "/FoodClass/" + foodClass);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String result = Common.convertToString(inputStream);
        JSONArray array = new JSONArray(result);
        ArrayList<Food> foodArrayList = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            if (foodClass == 1) {
                DryFood dryFood = new DryFood(
                        object.getInt("id"),
                        object.getString("name"),
                        object.getInt("foodClass"),
                        object.getInt("quantity"),
                        object.getString("importDay"),
                        object.getString("expiry"),
                        object.getDouble("attribute")
                );
                foodArrayList.add(dryFood);
            } else {
                FreshFood freshFood = new FreshFood(
                        object.getInt("id"),
                        object.getString("name"),
                        object.getInt("foodClass"),
                        object.getInt("quantity"),
                        object.getString("importDay"),
                        object.getString("expiry"),
                        object.getDouble("attribute")
                );
                foodArrayList.add(freshFood);
            }
        }
        return foodArrayList;
    }

    public void insert(Food food) throws IOException{
        URL endPoint = new URL(path + "/insert");
        HttpURLConnection connection = (HttpURLConnection) endPoint.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Method", "POST");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        System.out.println(food);
        String obj = gson.toJson(food);
        System.out.println(obj);
        out.write(obj);
        out.flush();
        out.close();
        InputStream i = new BufferedInputStream(connection.getInputStream());
        String result = Common.convertToString(i);
        System.out.println(result);

        JSONObject object = new JSONObject(result);
        if (object.getString("status").equals("ok")) {
            System.out.println("Insert Product successfully");
        }
        //you can replace "ok" with your defined "error code"
        else {
            System.out.println("Product name already taken");
        }
    }

    public ArrayList<Food>findByName(String name) throws IOException {
        URL endpoint = new URL(path + "/name/" + name);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String result = Common.convertToString(inputStream);
        JSONArray array = new JSONArray(result);
        ArrayList<Food> foodArrayList = new ArrayList<>();
        for(int i = 0; i< array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            Food food = new Food(
                    object.getInt("id"),
                    object.getString("name"),
                    object.getInt("foodClass"),
                    object.getInt("quantity"),
                    object.getString("importDay"),
                    object.getString("expiry"),
                    object.getDouble("attribute"));

            foodArrayList.add(food);
        }
        return  foodArrayList;
    }

    public ArrayList<Food>findByExpiry(String expiry) throws IOException {
        URL endpoint = new URL(path + "/expiry?expiry=" + expiry);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String result = Common.convertToString(inputStream);
        JSONArray array = new JSONArray(result);
        ArrayList<Food> foodArrayList = new ArrayList<>();

        for(int i = 0; i< array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            Food food = new Food(
                    object.getInt("id"),
                    object.getString("name"),
                    object.getInt("foodClass"),
                    object.getInt("quantity"),
                    object.getString("importDay"),
                    object.getString("expiry"),
                    object.getDouble("attribute"));

            foodArrayList.add(food);
        }
        return  foodArrayList;
    }

    public double monthBetween(String before, String after) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dBefore = LocalDate.parse(before,formatter);
        LocalDate dAfter = LocalDate.parse(after,formatter);
        double monthBetween = ChronoUnit.MONTHS.between(dBefore,dAfter);
        if (monthBetween<0){
            return 0;
        }
        return monthBetween+1;
    }

    public double totalCost(String importDay) throws IOException {
        ArrayList<Food> foodArrayList = getAllProducts();
        double total = 0;

        for(int i = 0; i< foodArrayList.size(); i++){
            Food food = foodArrayList.get(i);
            if ((monthBetween(food.getImportDay(), importDay) > 0) && (monthBetween(importDay, food.getExpiry()) > 0)){
                if (food.getFoodClass() == 1){
                    total += 10000*food.getAttribute();
                } else {
                    total += 20000*food.getAttribute();
                }
            }
        }
        return total;
    }


    public void update(Food newFood, int id) throws IOException {
        URL endPoint = new URL(path + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) endPoint.openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Method", "PUT");

        connection.setRequestMethod("PUT");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        String obj = gson.toJson(newFood);
        out.write(obj);
        out.flush();
        out.close();
        InputStream i = new BufferedInputStream(connection.getInputStream());
        System.out.println(Common.convertToString(i));
        System.out.println("Update Product successfully");
    }

    public void delete(Food food) throws IOException {
        URL endPoint = new URL(path + "/" + food.getId());
        HttpURLConnection connection = (HttpURLConnection) endPoint.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        String obj = gson.toJson(food.getId());
        out.write(obj);
        out.flush();
        out.close();
        InputStream i = new BufferedInputStream(connection.getInputStream());
        System.out.println(Common.convertToString(i));

        if(obj != null) {
            System.out.println("Delete product successfully");
        }
        else {
            System.out.println("Cannot find product to delete");
        }
    }

    public boolean isExpired(String expiryDay){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate now = LocalDate.now();
        LocalDate dExpiryDay = LocalDate.parse(expiryDay,formatter);
        int dayBetween = (int) ChronoUnit.DAYS.between(now,dExpiryDay);
        if (dayBetween>0){
            return false;
        }
        return true;
    }

    public void deleteExpiredFood() throws IOException {
        ArrayList<Food> foodArrayList = getAllProducts();
        for(int i = 0; i< foodArrayList.size(); i++){
            Food food = foodArrayList.get(i);
            if (isExpired(food.getExpiry())){
                delete(food);
            }
        }
    }
}