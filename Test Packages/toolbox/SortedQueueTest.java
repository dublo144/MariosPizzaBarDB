package toolbox;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Benjamin
 */
public class SortedQueueTest {

    private SortedQueue<Integer> queue;
    private int startQueueSize;
    public SortedQueueTest() {
        queue = new SortedQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        startQueueSize = queue.size();
    }

    @Test
    public void testAdd() {
        queue.add(100);
        assertEquals(startQueueSize+1, queue.size());
    }

    @Test
    public void testGet() {
        assertEquals(1, (int)queue.get(0));
        assertEquals(2, (int)queue.get(1));
        assertEquals(3, (int)queue.get(2));
        assertEquals(4, (int)queue.get(3));
    }
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGet_IndexOutOfBounds() {
        queue.get(1000);
    }

    @Test
    public void testRemove_0args() {
        int result = queue.remove();
        assertEquals(1,result);
    }

    @Test
    public void testRemoveLast() {
        queue.add(1000);
        int result = queue.removeLast();
        
        assertEquals(1000, result);
    }

    @Test
    public void testRemove_usingIndex() {
        SortedQueue<Integer> q = new SortedQueue<>();
        q.add(1);
        q.add(50);
        q.add(100);
        
        int result = q.remove(1);
        assertEquals(50,result);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testRemove_illegalIndex_negativeIndex() {
        queue.remove(-1);
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testRemove_illegalIndex_tooHighIndex() {
        queue.remove(9999);
    }
    
    
    @Test
    public void testSize() {
        SortedQueue<Integer> q = new SortedQueue<>();
        q.add(1);
        q.add(50);
        q.add(100);
        assertEquals(3,q.size());
    }

    @Test
    public void testIterator_hasNext() {
        SortedQueue<Integer> q = new SortedQueue<>();
        q.add(1);
        q.add(50);
        q.add(100);
        Iterator i = q.iterator();
        assertTrue(i.hasNext());
        i.next();
        assertTrue(i.hasNext());
        i.next();
        assertTrue(i.hasNext());
        i.next();
        assertFalse(i.hasNext());
    }

}