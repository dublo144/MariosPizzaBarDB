package core;

/**
 * @author Benjamin
 */
public class Pizza {
    private int number;
    private String name;
    private String toppings;
    private double price;

    public Pizza(int number, String name, String toppings, double price) {
        this.number = number;
        this.name = name;
        this.toppings = toppings;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getToppings() {
        return toppings;
    }

    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return number + " " + name + " " + toppings + " " + price;
    }
    
}
