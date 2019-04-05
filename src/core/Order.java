package core;

/**
 * @author Tobias
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Order implements Comparable<Order>{

    private int orderNumber;
    private ArrayList<Pizza> pizzas;
    private double totalPrice;
    private LocalDate date = LocalDate.now();
    private LocalTime time;

    public Order(int orderNumber,ArrayList<Pizza> pizza, LocalDate date, LocalTime time) {
        this.orderNumber = orderNumber;
        this.pizzas = pizza;
        this.date = date;
        this.time = time;
        calculateTotalPrice();
    }
    
    public Order(int orderNumber, ArrayList<Pizza> pizza, LocalTime time) {
        this.orderNumber = orderNumber;
        this.pizzas = pizza;
        this.time = time;
        calculateTotalPrice();
    }
    
    private void calculateTotalPrice(){
        totalPrice = 0;
        for(Pizza foodItem : pizzas){
            totalPrice += foodItem.getPrice();
       }
    }
    
    public String[] returnOrder(){
        String[] order = new String[pizzas.size()];
        int count = 0;
        for (Pizza foodItem : pizzas) {
            order[count] = orderNumber 
                    + " " + foodItem.getNumber() 
                    + " " + foodItem.getName() 
                    + " " + date.toString() 
                    + " " + time.toString();
            count++;
        } 
        return order;
    }
       
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public int getOrderNumber() {
        return orderNumber;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
        calculateTotalPrice();
    }
    
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nOrder Nummer: ").append(orderNumber);
        builder.append("\nAfhentning: ").append(date).append(" - ").append(time);
        for (Pizza piz : pizzas) {
            builder.append("\n").append(piz);
        }
        builder.append("\n").append("Total pris: ").append(totalPrice).append("kr");
        return builder.toString();
                
    }

    @Override
    public int compareTo(Order other) {
        int cmp = date.compareTo(other.getDate());
        if(cmp == 0) {
            cmp = time.compareTo(other.getTime());
        }
        return cmp;
    }


}
