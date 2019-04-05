package Storage;

import core.Order;
import core.Pizza;
import java.util.ArrayList;

/**
 * @author Benjamin
 */
public class MockStorage implements Storage{
    
    private ArrayList<Order> orders;
    private ArrayList<Pizza> pizzas;
    private ArrayList<String> fileLines;
    private ArrayList<String> fileOutput = new ArrayList<>();
    private ArrayList<Integer> orderNumbers = new ArrayList<>();
    
    public MockStorage() {
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    public void setPizzas(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
    
    public int getOrdersSize() {
        return orders.size();
    }
    
    public int getPizzasSize() {
        return pizzas.size();
    }
    
    @Override
    public void completeOrder(int orderNumber) {
        int index = -1;
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getOrderNumber() == orderNumber)
                index = i;
        }
        orders.remove(index);
    }

    @Override
    public void removePizza(int pizzaNumber) {
        int index = -1;
        for (int i = 0; i < pizzas.size(); i++) {
            if(pizzas.get(i).getNumber() == pizzaNumber)
                index = i;
        }
        pizzas.remove(index);
    }
    
    public void setFileLines(ArrayList<String> fileLines) {
        this.fileLines = fileLines;
    }
    

    @Override
    public ArrayList<String> readFromFile(String filename) {
        return fileLines;
    }

    @Override
    public void writeToFile(String words, String filename) {
        fileOutput.add(words);
    }
    
    public int outputFileSize() {
        return fileOutput.size();
    }
    
    public void setOrderNumbers(ArrayList<Integer> orderNumbers) {
        this.orderNumbers = orderNumbers;
    }

    @Override
    public ArrayList<Integer> getOrderNumbers() {
        return orderNumbers;
    }

}
