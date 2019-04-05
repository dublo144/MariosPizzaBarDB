package core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tobias
 * @author Alexander 
 */
public class OrderTest {

    private Order order;
    private Customer customer;
    private LocalTime pickUpTime;
    private LocalDate date;
    private double pizzaPrice1 = 59, pizzaPrice2 = 57;
    
    public OrderTest() {
        date = LocalDate.of(2000, Month.MARCH, 20);
        pickUpTime = LocalTime.of(18, 30);
        customer = new Customer("Peter Pan","11 22 33 44");
        ArrayList<Pizza> pizza = new ArrayList<>();
        pizza.add(new Pizza(2, "Amerikaner", "Pepperoni", pizzaPrice1));
        pizza.add(new Pizza(1, "Vesuvio", "tomatsauce", pizzaPrice2));
        order = new Order(1, pizza, date, pickUpTime);
    }

    @Test
    public void testReturnOrder() {
        String[] expected = new String[] {"1 2 Amerikaner " + date.toString() + " " + pickUpTime.toString(),
                                            "1 1 Vesuvio " + date.toString() + " " + pickUpTime.toString()};
        
        
        String[] result = order.returnOrder();
        
        assertEquals(expected.length, result.length);
        assertEquals(expected[0],result[0]);
        assertEquals(expected[1],result[1]);
    }
    
    @Test
    public void testGetDate() {
        assertEquals(LocalDate.of(2000, Month.MARCH, 20), order.getDate());
    }
    
    @Test
    public void testGetTime() {
        assertEquals(LocalTime.of(18, 30), order.getTime());
    }
    
    @Test
    public void testGetTotalPrice() {
        assertEquals(pizzaPrice1+pizzaPrice2, order.getTotalPrice(),0.0);
    }
    
    @Test
    public void testGetTotalPrice_WithPizzaAddedAfterCreation() {
        double pizzaPrice3 = 100;
        order.addPizza(new Pizza(1,"Superman","Wow", pizzaPrice3));
        assertEquals(pizzaPrice1+pizzaPrice2+pizzaPrice3, order.getTotalPrice(),0.0);
    }
    
    @Test
    public void testGetOrderNumber() {
        assertEquals(1, order.getOrderNumber());
    }
    
    @Test
    public void testAddPizza() {
        order.addPizza(new Pizza(33,"Wow Pizza","Rainbow sunshine", 400));
        assertEquals(3, order.getPizzas().size());
    }
    
    @Test
    public void testGetPizzas() {
        assertEquals(2, order.getPizzas().size());
    }
     
     @Test
     public void testGetPickUpTime() {
         assertEquals(pickUpTime, order.getTime());
     }
     
}