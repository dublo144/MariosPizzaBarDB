package core;

import Storage.MockStorage;
import UI.MockUI;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import toolbox.SortedQueue;

/**
 * @author Tobias
 * @author Alexander
 */
public class OrderHandlerTest {

    private MockUI mockUI;
    private MockStorage mockSto;
    private Menu menu;
    private String[] input;
    
    public OrderHandlerTest() {
        input = new String[]{};
        mockUI = new MockUI(input);
        
    }
    
    @Test
    public void testLoadOrdersFromFile() {
        mockSto = new MockStorage();
        ArrayList<String> fileLines = new ArrayList<>();
        // Set file lines for the menu
        fileLines.add("1 Vesuvio tomatsauce,ost,skinke,oregano 57");
        fileLines.add("2 Amerikaner tomatsauce,ost,oksefars,oregano 57");
        fileLines.add("3 Hawaii tomatsauce,ost,skinke,ananas 61");
        mockSto.setFileLines(fileLines);
        menu = new Menu(mockUI, mockSto);
        
        // Clear and set file lines for the orders.
        fileLines.clear();
        fileLines.add("89 1 Vesuvio 2019-03-26 11:09");
        fileLines.add("89 2 Amerikaner 2019-03-26 11:09");
        fileLines.add("89 3 Hawaii 2019-03-26 11:09");
        mockSto.setFileLines(fileLines);
        
        OrderHandler oh = new OrderHandler(mockUI,mockSto,menu);
        SortedQueue<Order> orders = oh.loadOrdersFromFile();
        assertEquals(1,orders.size());
    }
    
    @Test
    public void testLoadOrdersFromFile_withNoOrdersToMake() {
        mockSto = new MockStorage();
        ArrayList<String> fileLines = new ArrayList<>();
        // Set file lines for the menu
        fileLines.add("1 Vesuvio tomatsauce,ost,skinke,oregano 57");
        fileLines.add("2 Amerikaner tomatsauce,ost,oksefars,oregano 57");
        fileLines.add("3 Hawaii tomatsauce,ost,skinke,ananas 61");
        mockSto.setFileLines(fileLines);
        menu = new Menu(mockUI,mockSto);
        
        // Clear and leave orders empty.
        fileLines.clear();
        mockSto.setFileLines(fileLines);
        
        OrderHandler oh = new OrderHandler(mockUI,mockSto,menu);
        SortedQueue<Order> orders = oh.loadOrdersFromFile();
        assertEquals(0,orders.size());
    }
    
   @Test
   public void testWriteOrderToFile() {
       ArrayList<Pizza> pizzas = new ArrayList<>();
       pizzas.add(new Pizza(1,"name","toppings",50));
       Order order = new Order(1,pizzas,LocalTime.of(10, 30));
        mockSto = new MockStorage();
        OrderHandler oh = new OrderHandler(mockUI,mockSto,null);
        
        oh.writeOrderToFile(order);
        assertEquals(3,mockSto.outputFileSize()); 
   }
   
   @Test
   public void testNextOrderNumber() {
       ArrayList<Integer> orderNumbers = new ArrayList<>();
       orderNumbers.add(1);
       orderNumbers.add(2);
       mockSto = new MockStorage();
       mockSto.setOrderNumbers(orderNumbers);
       
       OrderHandler oh = new OrderHandler(mockUI,mockSto,null);
       assertEquals(3,oh.nextOrderNumber());
   }
   
   @Test
   public void testNextOrderNumber_withNoOrderNumbers() {
       ArrayList<Integer> orderNumbers = new ArrayList<>();
       mockSto = new MockStorage();
       mockSto.setOrderNumbers(orderNumbers);
       
       OrderHandler oh = new OrderHandler(mockUI,mockSto,null);
       assertEquals(1,oh.nextOrderNumber());
   }
   
   @Test
   public void testGetOrderNumbersToMake() {
       mockSto = new MockStorage();
        ArrayList<String> fileLines = new ArrayList<>();
        // Set file lines for the menu
        fileLines.add("1 Vesuvio tomatsauce,ost,skinke,oregano 57");
        fileLines.add("2 Amerikaner tomatsauce,ost,oksefars,oregano 57");
        fileLines.add("3 Hawaii tomatsauce,ost,skinke,ananas 61");
        mockSto.setFileLines(fileLines);
        menu = new Menu(mockUI,mockSto);
        
        // Clear and set file lines for the orders.
        fileLines.clear();
        fileLines.add("89 1 Vesuvio 2019-03-26 11:09");
        fileLines.add("89 2 Amerikaner 2019-03-26 11:09");
        fileLines.add("89 3 Hawaii 2019-03-26 11:09");
        mockSto.setFileLines(fileLines);
        
        OrderHandler oh = new OrderHandler(mockUI,mockSto,menu);
        int[] numbersToMake = oh.getOrderNumbersToMake();
        int[] exp = new int[] {89};
        assertEquals(exp[0], numbersToMake[0]);
        assertEquals(exp[0], numbersToMake[1]);
        assertEquals(exp[0], numbersToMake[2]);
   }
   
   @Test
   public void testGetOrderNumbersToMake_withMultipleOrderNumbers() {
              mockSto = new MockStorage();
        ArrayList<String> fileLines = new ArrayList<>();
        // Set file lines for the menu
        fileLines.add("1 Vesuvio tomatsauce,ost,skinke,oregano 57");
        fileLines.add("2 Amerikaner tomatsauce,ost,oksefars,oregano 57");
        fileLines.add("3 Hawaii tomatsauce,ost,skinke,ananas 61");
        mockSto.setFileLines(fileLines);
        menu = new Menu(mockUI,mockSto);
        
        // Clear and set file lines for the orders.
        fileLines.clear();
        // First order
        fileLines.add("89 1 Vesuvio 2019-03-26 11:09");
        fileLines.add("89 2 Amerikaner 2019-03-26 11:09");
        fileLines.add("89 3 Hawaii 2019-03-26 11:09");
        // Second order
        fileLines.add("90 1 Vesuvio 2019-03-26 11:09");
        fileLines.add("90 2 Amerikaner 2019-03-26 11:09");
        fileLines.add("90 3 Hawaii 2019-03-26 11:09");
        mockSto.setFileLines(fileLines);
        
        OrderHandler oh = new OrderHandler(mockUI,mockSto,menu);
        int[] numbersToMake = oh.getOrderNumbersToMake();
        int[] exp = new int[] {89,90};
        assertEquals(exp[0], numbersToMake[0]);
        assertEquals(exp[0], numbersToMake[1]);
        assertEquals(exp[0], numbersToMake[2]);
        
        assertEquals(exp[1], numbersToMake[3]);
        assertEquals(exp[1], numbersToMake[4]);
        assertEquals(exp[1], numbersToMake[5]);
   }
}

