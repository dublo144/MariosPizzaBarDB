package core;

import UI.MockUI;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Mads
 */
public class UIControllerTest {
    
    private UIController controller;
    private MockUI mock;
    
    public UIControllerTest() {
    }
    
    @Test
    public void testMenuCard() {
        String[] input = new String[] {"1","-1"};
        mock = new MockUI(input);
        controller = new UIController(mock);
        
        controller.menuCard();
        
        assertTrue(mock.getOutputSize() > 1);
    }
    
    @Test
    public void testMenuCard_withNegativeOneChoice() {
        String[] input = new String[] {"-1"};
        mock = new MockUI(input);
        controller = new UIController(mock);
        
        assertTrue(mock.getOutputSize() == 0);
    }
    
    @Test
    public void testSelectPizzas() {
        String[] input = new String[] {"1","-1"};
        mock = new MockUI(input);
        controller = new UIController(mock);
        
        ArrayList<Pizza> result = controller.selectPizzas();
        assertEquals(1, result.size());
    }
    
}