import java.util.*;

/**
 * Double-linked node implementation of IndexedUnsortedList.
 * A ListIterator with all operations is implemented.
 * 
 * @author Student
 * 
 * @param <E> type to store
 */
public class IUDoubleLinkedList<E> implements IndexedUnsortedList<E> {
    private BidirectionalNode<E> front, rear;
    private int count;
    private int modCount;
    
    /** Creates an empty list */
    public IUDoubleLinkedList() {
        front = rear = null;
        count = 0;
        modCount = 0;
    }

    @Override
    public void addToFront(E element) {
        BidirectionalNode<E> newNode = new BidirectionalNode<E>(element);
        
        // if the list is empty, both front and rear will point to the new node
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            newNode.setNext(front);
            front.setPrevious(newNode);
            front = newNode;
        }
        
        count++;
        modCount++;
    }

    @Override
    public void addToRear(E element) {
        BidirectionalNode<E> newNode = new BidirectionalNode<E>(element);
        
        // if the list is empty, both front and rear will point to the new node
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            newNode.setPrevious(rear);
            rear.setNext(newNode);
            rear = newNode;
        }
        
        count++;
        modCount++;
    }

    @Override
    public void add(E element) {
        // default implementation adds to rear
        addToRear(element);
    }

    @Override
    public void addAfter(E element, E target) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        // find the target node
        BidirectionalNode<E> targetNode = front;
        while (targetNode != null && !targetNode.getElement().equals(target)) {
            targetNode = targetNode.getNext();
        }
        
        // if target is not found
        if (targetNode == null) {
            throw new NoSuchElementException();
        }
        
        // if target is the rear node
        if (targetNode == rear) {
            addToRear(element);
        } else {
            BidirectionalNode<E> newNode = new BidirectionalNode<E>(element);
            BidirectionalNode<E> nextNode = targetNode.getNext();
            
            newNode.setPrevious(targetNode);
            newNode.setNext(nextNode);
            targetNode.setNext(newNode);
            nextNode.setPrevious(newNode);
            
            count++;
            modCount++;
        }
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        }
        
        // if adding at the front
        if (index == 0) {
            addToFront(element);
        } 
        // if adding at the rear
        else if (index == count) {
            addToRear(element);
        } 
        // adding in the middle
        else {
            BidirectionalNode<E> current = front;
            
            // find the node at position index
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            
            BidirectionalNode<E> newNode = new BidirectionalNode<E>(element);
            BidirectionalNode<E> prevNode = current.getPrevious();
            
            newNode.setNext(current);
            newNode.setPrevious(prevNode);
            prevNode.setNext(newNode);
            current.setPrevious(newNode);
            
            count++;
            modCount++;
        }
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        E removedElement = front.getElement();
        
        // if there's only one element
        if (front == rear) {
            front = null;
            rear = null;
        } else {
            front = front.getNext();
            front.setPrevious(null);
        }
        
        count--;
        modCount++;
        
        return removedElement;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        E removedElement = rear.getElement();
        
        // if there's only one element
        if (front == rear) {
            front = null;
            rear = null;
        } else {
            rear = rear.getPrevious();
            rear.setNext(null);
        }
        
        count--;
        modCount++;
        
        return removedElement;
    }

    @Override
    public E remove(E element) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        // try to find the element
        BidirectionalNode<E> current = front;
        while (current != null && !current.getElement().equals(element)) {
            current = current.getNext();
        }
        
        // if element is not found
        if (current == null) {
            throw new NoSuchElementException();
        }
        
        return removeNode(current);
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }
        
        // if removing the first element
        if (index == 0) {
            return removeFirst();
        }
        
        // if removing the last element
        if (index == count - 1) {
            return removeLast();
        }
        
        // find the node at the specified index
        BidirectionalNode<E> current = front;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        
        return removeNode(current);
    }
    
    /**
     * helper method that removes a specific node from the list
     * @param node the node to remove
     * @return the element stored in the removed node
     */
    private E removeNode(BidirectionalNode<E> node) {
        E element = node.getElement();
        
        // if removing the only element
        if (front == rear) {
            front = null;
            rear = null;
        }
        // if removing the first element
        else if (node == front) {
            front = front.getNext();
            front.setPrevious(null);
        }
        // iif removing the last element
        else if (node == rear) {
            rear = rear.getPrevious();
            rear.setNext(null);
        }
        // if removing from the middle
        else {
            BidirectionalNode<E> prevNode = node.getPrevious();
            BidirectionalNode<E> nextNode = node.getNext();
            
            prevNode.setNext(nextNode);
            nextNode.setPrevious(prevNode);
        }
        
        count--;
        modCount++;
        
        return element;
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }
        
        BidirectionalNode<E> current = front;
        
        // find the node at the specified index
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        
        current.setElement(element);
        modCount++;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }
        
        BidirectionalNode<E> current = front;
        
        // find the node at the specified index
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        
        return current.getElement();
    }

    @Override
    public int indexOf(E element) {
        BidirectionalNode<E> current = front;
        int index = 0;
        
        // search for the element
        while (current != null) {
            if (current.getElement().equals(element)) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        
        // element not found
        return -1;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        return front.getElement();
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        return rear.getElement();
    }

    @Override
    public boolean contains(E target) {
        return indexOf(target) != -1;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        BidirectionalNode<E> current = front;
        
        while (current != null) {
            result.append(current.getElement());
            if (current.getNext() != null) {
                result.append(", ");
            }
            current = current.getNext();
        }
        
        result.append("]");
        return result.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DLLListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int startingIndex) {
        if (startingIndex < 0 || startingIndex > count) {
            throw new IndexOutOfBoundsException();
        }
        
        return new DLLListIterator(startingIndex);
    }
    
    /**
     * iterator for IUDoubleLinkedList
     */
    private class DLLIterator implements Iterator<E> {
        private BidirectionalNode<E> nextNode;
        private BidirectionalNode<E> lastReturned;
        private int iterModCount;
        
        /**
         * creates a new iterator for the list
         */
        public DLLIterator() {
            nextNode = front;
            lastReturned = null;
            iterModCount = modCount;
        }
        
        @Override
        public boolean hasNext() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextNode != null;
        }
        
        @Override
        public E next() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            lastReturned = nextNode;
            nextNode = nextNode.getNext();
            return lastReturned.getElement();
        }
        
        @Override
        public void remove() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            
            removeNode(lastReturned);
            
            lastReturned = null;
            iterModCount = modCount;
        }
    }
    
    /**
     * ListIterator for IUDoubleLinkedList
     */
    private class DLLListIterator implements ListIterator<E> {
        private BidirectionalNode<E> nextNode; // node that would be returned by next()
        private BidirectionalNode<E> previousNode; // node that would be returned by previous()
        private BidirectionalNode<E> lastReturned; // last node returned by next() or previous()
        private int nextIndex;
        private int iterModCount;
        
        /**
         * creates a new list iterator starting at the specified index
         * @param startingIndex index at which to start the iterator
         */
        public DLLListIterator(int startingIndex) {
            if (startingIndex < 0 || startingIndex > count) {
                throw new IndexOutOfBoundsException();
            }
            
            nextIndex = startingIndex;
            lastReturned = null;
            iterModCount = modCount;
            
            if (startingIndex == count) {
                nextNode = null;
                previousNode = rear;
            } else if (startingIndex == 0) {
                nextNode = front;
                previousNode = null;
            } else {
                nextNode = front;
                for (int i = 0; i < startingIndex; i++) {
                    nextNode = nextNode.getNext();
                }
                previousNode = nextNode.getPrevious();
            }
        }
        
        @Override
        public boolean hasNext() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextNode != null;
        }
        
        @Override
        public E next() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            lastReturned = nextNode;
            previousNode = nextNode;
            nextNode = nextNode.getNext();
            nextIndex++;
            
            return lastReturned.getElement();
        }
        
        @Override
        public boolean hasPrevious() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return previousNode != null;
        }
        
        @Override
        public E previous() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (previousNode == null) {
                throw new NoSuchElementException();
            }
            
            lastReturned = previousNode;
            nextNode = previousNode;
            previousNode = previousNode.getPrevious();
            nextIndex--;
            
            return lastReturned.getElement();
        }
        
        @Override
        public int nextIndex() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextIndex;
        }
        
        @Override
        public int previousIndex() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextIndex - 1;
        }
        
        @Override
        public void remove() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            
            if (lastReturned == nextNode) {
                previousNode = lastReturned.getPrevious();
            } else {
                nextNode = lastReturned.getNext();
                nextIndex--;
            }
            
            // remove the node
            removeNode(lastReturned);
            lastReturned = null;
            iterModCount = modCount;
        }
        
        @Override
        public void set(E e) {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            
            lastReturned.setElement(e);
            iterModCount = ++modCount;
        }
        
        @Override
        public void add(E e) {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            
            BidirectionalNode<E> newNode = new BidirectionalNode<E>(e);
            
            // adding to an empty list
            if (isEmpty()) {
                front = newNode;
                rear = newNode;
                nextNode = null;
                previousNode = newNode;
            }
            // adding to the beginning of the list
            else if (previousNode == null) {
                newNode.setNext(front);
                front.setPrevious(newNode);
                front = newNode;
                previousNode = newNode;
            }
            // adding to the end of the list
            else if (nextNode == null) {
                newNode.setPrevious(rear);
                rear.setNext(newNode);
                rear = newNode;
                previousNode = newNode;
            }
            // adding in the middle of the list
            else {
                newNode.setNext(nextNode);
                newNode.setPrevious(previousNode);
                previousNode.setNext(newNode);
                nextNode.setPrevious(newNode);
                previousNode = newNode;
            }
            
            nextIndex++;
            count++;
            iterModCount = ++modCount;
            lastReturned = null;
        }
    }
}