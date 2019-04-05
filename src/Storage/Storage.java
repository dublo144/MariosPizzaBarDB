package Storage;

import java.util.ArrayList;

/**
 * @author Alexander
 */
public interface Storage {
    public static final String TURNOVER_FILE = "turnover.txt";
    
    public static final String POPULAR_PIZZA_FILE = "popularPizza.txt";
    
    public static final String ORDERS_TO_MAKE_FILE = "ordersToMake.txt";
    public static final String ORDERS_TO_MAKE_TEMP_FILE = "ordersToMakeTemp.txt";
    
    public static final String ALL_ORDERS_FILE = "allOrders.txt";
    
    public static final String MENU_FILE = "Menu.txt";
    public static final String MENU_TEMP_FILE = "MenuTemp.txt";
    
    public void completeOrder(int orderNumber);
    public void removePizza(int pizzaNumber);
    public ArrayList<String> readFromFile(String filename);
    public void writeToFile(String words, String filename);
    public ArrayList<Integer> getOrderNumbers();
}
