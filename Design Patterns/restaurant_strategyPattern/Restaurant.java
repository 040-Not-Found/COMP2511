package restaurant_strategyPattern;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Restaurant {

    private String name;
    private List<Meal> menu = new ArrayList<Meal>();
    private List<String> members = new ArrayList<String>();
    private CostBehaviour costBehaviour = new DiscountCost();
    
    public Restaurant(String name) {
        this.name = name;
        JSONArray menuJSON = JSONHelper.readInData("src/restaurant/prices.json");

        for (Object Meal : menuJSON) {
            JSONObject jsonMeal = (JSONObject) Meal;
            menu.add(new Meal(jsonMeal.getString("meal"), jsonMeal.getInt("cost")));
        }
    }

    public double cost(List<Meal> order, String payee) {
        return order.stream().mapToDouble(meal -> meal.getCost() * costBehaviour.getCost(members.contains(payee))).sum();
        
    }

    public void displayMenu() {
        for (Meal meal : menu) {
            System.out.println(meal.getName() + " - " + meal.getCost() * costBehaviour.getModifier());
        }
    }

    public static void main(String[] args) {
        Restaurant r = new Restaurant("XS");
        r.displayMenu();
    }

}