package toolbox;

import java.util.Iterator;
/**
 * @author Benjamin
 */
public class SortedQueue<T extends Comparable<T>> implements Iterable<T> {
    private Node<T> firstElement, lastElement;
    private int size; 
    public SortedQueue() {}
    
    /**
     * Adds a element to the sorted queue, lower values will be at the front of the queue.
     * @param element The element to be added to the queue.
     */
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        // If newNode is the very first element in the queue, make both the first and last point to it.
        if (firstElement == null){
            firstElement = newNode;
            lastElement = newNode;
        } else {
            Node<T> currentNode = firstElement;
            boolean nodePlaced = false;
            
            // If we have a last element (queue size > 0)
            // And the new value is greater than the lastElement's value, skip the queue, and insert at the back.
            if(lastElement != null && element.compareTo(lastElement.value) > 0) {
                newNode.left = lastElement;
                lastElement.right = newNode;
                lastElement = newNode;
                nodePlaced = true;
            }
            // Find a place to insert the new node.
            placeNode(nodePlaced, currentNode, newNode);
        }
        size++;
    }
    
    private void placeNode(boolean nodePlaced, Node<T> currentNode, Node<T> newNode) {
        Node<T> oldNode = null;
        while (!nodePlaced) {
            // If currentNode is null, we have reached the end of the queue, and need to place the node at the very end.
            if (currentNode == null && oldNode != null) {
                oldNode.right = newNode;
                newNode.left = oldNode;
                // Save the new last node.
                lastElement = newNode;
                nodePlaced = true;
            }
            else {
                // Check if the currentNode's value is less than the newNodes.
                if (currentNode != null && newNode.value.compareTo(currentNode.value) < 0) {
                    // If the left node, is not null, then we need to remap the left node as well.
                    if (currentNode.left != null) {
                        // Set the currntNode's left node's right to the new node.
                        currentNode.left.right = newNode;
                    } else {
                        // If currentNode.left is null, set the new first.
                        firstElement = newNode;
                    }
                    // Set the newNode's references on both sides.
                    newNode.left = currentNode.left;
                    newNode.right = currentNode;
                    // The currentNode has been moved to the right, and needs its left node updated.
                    currentNode.left = newNode;
                    nodePlaced = true;
                }
            }
            if(!nodePlaced) {
                // Save the last node, thereafter advance the current node to the next.
                oldNode = currentNode;
                currentNode = currentNode.right;
            }
        }
    }
    
    
    /**
     * Gets the element at 'index' in the queue - does not remove the element from the queue.
     * @param index The element location in the queue.
     * @return The element at index. 
     */
    public T get(int index) {
        Node<T> current = firstElement;
        for (int i = 0; i < index; i++) {
            current = current.right;
            if(current == null)
                throw new ArrayIndexOutOfBoundsException("index: " + i + " array length: " + size);
        }
        return current.value;
    }
    
    /**
     * Removes the first element from the queue - and returns the value stored.
     * @return The first element of the queue.
     */
    public T remove() {
        T value = firstElement.value;
        firstElement = firstElement.right;
        return value;
    }
    
    /**
     * Removes the last entry in the queue.
     * @return The last element in the queue.
     */
    public T removeLast() {
        T value = lastElement.value;
        lastElement = lastElement.left;
        lastElement.right = null;
        return value;
    }
    
    /**
     * Removes the element at index from the queue, and returns the value.
     * @param index The index at which to remove an element.
     * @return The element at 'index' of the queue.
     */
    public T remove(int index) {
        if(index == 0)
            return remove();
        else if (index == size - 1)
            return removeLast();  
        else 
            return removeAtIndex(index);
    }
    
    /**
     * Removes an entry at a given index, this asumes that index is not 0 or (size - 1)
     * @param index The index at which to remove an entry.
     * @return The entry at 'index' in the queue.
     */
    private T removeAtIndex(int index) {
        if(index < 0 || index > (size -1))
            throw new ArrayIndexOutOfBoundsException("Index cannot be 0 or size - 1");
        else {
            Node<T> current = firstElement;
            for (int i = 0; i < index; i++) {
                current = current.right;
            }
            T value = current.value;
            // Get the left and right node of the node to be removeds.
            Node<T> left = current.left;
            Node<T> right = current.right;
            // Update the right and left nodes' reference.
            left.right = right;
            right.left = left;
            return value;
        }
    }
    
    /**
     * Gets the size of the queue
     * @return number of elements in the queue.
     */
    public int size() {
        return size;
    }

    /**
     * For supporting for-each loops.
     * @return an interator, which can access each element in the queue.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> current = firstElement;
    
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public T next() {
                T value = current.value;
                current = current.right;
                return value;
            }
        };
    }
    
    /**
     * Internal class - Points to a left and a right node, holds the an element of type T
     * @param <T> The type to be contained in the Node.
     */
    private class Node<T extends Comparable<T>> {
        public Node<T> left, right;
        public T value;

        public Node(T value) {
            this.value = value;
        }
    }
}
