package core;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author Benjamin
 */
public class CustomerTest {
    
    private Customer customer;
    public CustomerTest() {
        customer = new Customer("Benjamin","Paepke","Hej med dig");
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Benjamin", customer.getFirstName());
    }
    
    @Test
    public void testGetLastName() {
        assertEquals("Paepke", customer.getLastName());
    }
    
    @Test
    public void testGetAddress() {
        assertEquals("Hej med dig", customer.getAddress());
    }
    
    
}