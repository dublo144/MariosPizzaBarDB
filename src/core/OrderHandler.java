package core;

/**
 * @author Tobias
 */
import Storage.Storage;
import UI.UI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import toolbox.SortedQueue;

/**
 * @author Benjamin
 */
public class OrderHandler {    
    private UI ui;
    private Storage storage;
    private Menu menu;
    public OrderHandler(UI ui, Storage storage, Menu menu) {
        this.ui = ui;
        this.storage = storage;
        this.menu = menu;
    }
    
    /**
     * Loads in all orders and stores the in a sortedQueue.
     * @return A sorted queue with all the orders.
     */
    public SortedQueue<Order> loadOrdersFromFile() {
        SortedQueue<Order> orders = new SortedQueue<>(); 
        ArrayList<String> lines = storage.readFromFile(Storage.ORDERS_TO_MAKE_FILE);
        HashMap<Integer,Order> pizzaMap = new HashMap<>();
        for (String line : lines) {
            int nr = Integer.parseInt((line.split(" "))[0]);
            int pizNr = Integer.parseInt((line.split(" "))[1]);
            LocalDate date = LocalDate.parse((line.split(" "))[3]);
            LocalTime time = LocalTime.parse((line.split(" "))[4]);
            Pizza pizza = menu.getPizza(pizNr);
            if(pizzaMap.containsKey(nr)) {
                pizzaMap.get(nr).addPizza(pizza);
            }
            else{
                pizzaMap.put(nr, new Order(nr,new ArrayList<>(),date,time));
                pizzaMap.get(nr).addPizza(pizza);
            }
        }
        for (Integer key : pizzaMap.keySet()) {
            orders.add(pizzaMap.get(key));
        }
        return orders;
    }
    
    /**
     * Writes an order to the file, (turnover-, popularPizza-, and ordersToMake-file. 
     * @param order The order which needs to be written to file.
     */
    public void writeOrderToFile(Order order) {
        storage.writeToFile(order.getOrderNumber() + " " + order.getTotalPrice()
                + " " + order.getDate().toString(), Storage.TURNOVER_FILE);
        for (Pizza foodItem : order.getPizzas()) {
            storage.writeToFile(foodItem.getNumber() + " " + order.getDate().toString(),
                    Storage.POPULAR_PIZZA_FILE);
            storage.writeToFile(order.getOrderNumber() + " " + foodItem.getNumber() + " "
                    + foodItem.getName() + " " + order.getDate().toString()
                    + " " + order.getTime().toString(),
                    Storage.ORDERS_TO_MAKE_FILE);
        }
    }
    
    /**
     * Completes an order, removing it from the currently active order list.
     * @param orderNumber The order number of the order to be removed.
     */
    public void completeOrder(int orderNumber) {
        storage.completeOrder(orderNumber);
    }
    
    /**
     * Checks the storage for what is the current largest order number, and returns a value one higher.
     * If no other numbers is found, returns a base number.
     * @return The next unique order number. 
     */
    public int nextOrderNumber() {
        if (!storage.getOrderNumbers().isEmpty()) {
            ArrayList<Integer> allOrderNumbers = storage.getOrderNumbers();
            int[] orderNumbers = new int[allOrderNumbers.size()];
            for (int i = 0; i < orderNumbers.length; i++) {
                orderNumbers[i] = allOrderNumbers.get(i);
            }
            Arrays.sort(orderNumbers);
            return orderNumbers[orderNumbers.length - 1] + 1;
        } else {
            return 1;
        }
    }
    
    /**
     * Checks with the storage and see how many orders there needs to be made.
     * @return An array with all order numbers of orders that needs to be made. (multiple of the same order numbers can be present).
     */
    public int[] getOrderNumbersToMake(){
       ArrayList<String> ordersToMake = storage.readFromFile(Storage.ORDERS_TO_MAKE_FILE);
       String[] splitLine;
       int[] orderNumbersToMake = new int[ordersToMake.size()];
       int count = 0;
       for(String order : ordersToMake){
           splitLine = order.split(" ");
           orderNumbersToMake[count] = Integer.parseInt(splitLine[0]);
           count++;
       }
       return orderNumbersToMake;
    }
}
