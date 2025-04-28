import java.util.*;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author 
 * 
 * @param <E> type to store
 */
public class IUSingleLinkedList<E> implements IndexedUnsortedList<E> {
	private LinearNode<E> front, rear;
	private int count;
	private int modCount;
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		front = rear = null;
		count = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(E element) {
		addElement(0, element);
	}

	@Override
	public void addToRear(E element) {
		addElement(count, element);
	}

	@Override
	public void add(E element) {
		addElement(count, element);
	}

	@Override
	public void addAfter(E element, E target) {
		if (isEmpty()) { throw new NoSuchElementException(); }
	
		LinearNode<E> current = front;
		int index = 0;
		while (current != null && !current.getElement().equals(target)) {
			current = current.getNext();
			index++;
		}
	
		if (current == null) {
			throw new NoSuchElementException();
		}
	
		addElement(index + 1, element);
	}
		

	@Override
	public void add(int index, E element) {
		addElement(index, element);
	}

	@Override
	public E removeFirst() {
		if (isEmpty()) { throw new NoSuchElementException();}
	
		E result = front.getElement();
		front = front.getNext();
		count--;
		modCount++;
	
		if (count == 0) {
			rear = null;
		}
	
		return result;
	}

	@Override
	public E removeLast() {
		if (isEmpty()) { throw new NoSuchElementException(); }
	
		E result;
		if (count == 1) {
			result = front.getElement();
			front = rear = null;
		} else {
			LinearNode<E> current = front;
			while (current.getNext() != rear) {
				current = current.getNext();
			}
			result = rear.getElement();
			current.setNext(null);
			rear = current;
		}
	
		count--;
		modCount++;
		return result;
	}

	@Override
	public E remove(E element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		LinearNode<E> current = front, previous = null;
		while (current != null && !current.getElement().equals(element)) {
			previous = current;
			current = current.getNext();
		}
		// Matching element not found
		if (current == null) {
			throw new NoSuchElementException();
		}
		return removeElement(previous, current);		
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }
	
		if (index == 0) {
			return removeFirst();
		}
	
		LinearNode<E> current = front;
		for (int i = 0; i < index - 1; i++) {
			current = current.getNext();
		}
	
		LinearNode<E> nodeToRemove = current.getNext();
		return removeElement(current, nodeToRemove);
	}

	@Override
	public void set(int index, E element) {
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }
	
		LinearNode<E> current = front;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
	
		current.setElement(element);
		modCount++; 
	}
		

	@Override
	public E get(int index) {
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException();}
	
		LinearNode<E> current = front;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
	
		return current.getElement();
	}

	@Override
	public int indexOf(E element) {
		LinearNode<E> current = front;
		int index = 0;
		while (current != null) {
			if (current.getElement().equals(element)) {
				return index;
			}
			current = current.getNext();
			index++;
		}
		return -1;
	}

	@Override
	public E first() {
		if (isEmpty()) { throw new NoSuchElementException(); }
		return front.getElement();
	}

	@Override
	public E last() {
		if (isEmpty()) { throw new NoSuchElementException(); }
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
		String result = "[";
		LinearNode<E> current = front;

	while (current != null) {
		result += current.getElement();
		if (current.getNext() != null) {
			result += ", ";
		}
		current = current.getNext();
	}

	result += "]";
	return result;

	}

	private E removeElement(LinearNode<E> previous, LinearNode<E> current) {
		// Grab element
		E result = current.getElement();
		// If not the first element in the list
		if (previous != null) {
			previous.setNext(current.getNext());
		} else { // If the first element in the list
			front = current.getNext();
		}
		// If the last element in the list
		if (current.getNext() == null) {
			rear = previous;
		}
		count--;
		modCount++;

		return result;
	}

	private void addElement(int index, E element) {
		if (index < 0 || index > count) {
			throw new IndexOutOfBoundsException();
		}
	
		LinearNode<E> newNode = new LinearNode<E>(element);

		if (index == 0) {
			
			// make the newNode the front and make the next element the former front element
			newNode.setNext(front);
			front = newNode;
			
			// if adding the element creates a single element list
			if (count == 0) {
				rear = newNode;
			}
		} else {
			LinearNode<E> current = front;
			
			// shift elements before index down and then insert target element at index
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			if (newNode.getNext() == null) {
				rear = newNode;
			}
		}
	
		count++;
		modCount++;
	}


	@Override
	public Iterator<E> iterator() {
		return new SLLIterator();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<E> {
		private LinearNode<E> previous;
		private LinearNode<E> current;
		private LinearNode<E> next;
		private int iterModCount;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			previous = null;
			current = null;
			next = front;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount) { throw new ConcurrentModificationException();}
			return next != null;
		}

		@Override
		public E next() {
			if (iterModCount != modCount) { throw new ConcurrentModificationException();}
			if (next == null) {throw new NoSuchElementException();}
		
			previous = current;
			current = next;
			next = next.getNext();
		
			return current.getElement();
		}
		
		@Override
		public void remove() {
			if (iterModCount != modCount) {throw new ConcurrentModificationException();}
			if (current == null) {throw new IllegalStateException();}
		
			if (current == front) {
				// Remove the first element
				front = next;
				if (count == 1) {
					rear = null;
				}
			} else {
				// Skip over current
				previous.setNext(next);
				if (current == rear) {
					rear = previous;
				}
			}
		
			current = null;
			count--;
			modCount++;
			iterModCount++;
		}
	}

	// IGNORE THE FOLLOWING CODE
	// DON'T DELETE ME, HOWEVER!!!
	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}
}
