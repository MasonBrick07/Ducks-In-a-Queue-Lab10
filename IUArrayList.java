import java.util.*;

/**
 * Array-based implementation of IndexedUnsortedList.
 * 
 * @author 
 *
 * @param <E> type to store
 */
public class IUArrayList<E> implements IndexedUnsortedList<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;
	
	private E[] array;
	private int rear;
	private int modCount; // DO NOT REMOVE ME
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (E[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0; // DO NOT REMOVE ME
	}
	
	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length*2);
	}

	public void addToFront(E element) {
		addElement(0, element);
		modCount++; // DO NOT REMOVE ME
		
	}

	public void addToRear(E element) {
		addElement(rear, element);
		modCount++; // DO NOT REMOVE ME
	}

	public void add(E element) {
		addToRear(element);
		modCount++; // DO NOT REMOVE ME
	}

	public void addAfter(E element, E target) {
		addElement(indexOf(target) + 1, element);
		modCount++; // DO NOT REMOVE ME
	}

	public void add(int index, E element) {
		addElement(index, element);
		modCount++; // DO NOT REMOVE ME
	}

	public E removeFirst() {
		modCount++; // DO NOT REMOVE ME
		return removeElement(0);
	}

	public E removeLast() {
		modCount++; // DO NOT REMOVE ME
		return removeElement(rear - 1);
	}

	public E remove(E element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}

		modCount++; // DO NOT REMOVE ME
		return removeElement(index);
	}

	public E remove(int index) {
		modCount++; // DO NOT REMOVE ME
		return removeElement(index);
	}

	public void set(int index, E element) {
		if (index < 0 || index >= rear) {
			throw new IndexOutOfBoundsException();
		}
		array[index] = element;
		modCount++; // DO NOT REMOVE ME
	}

	public E get(int index) {
		if (isEmpty()) { throw new EmptyCollectionException("Unsorted List"); }
		return array[index];
	}

	public int indexOf(E element) {
		int index = NOT_FOUND;
		
		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}
		
		return index;
	}

	public E first() {
		if (isEmpty()) { throw new EmptyCollectionException("Unsorted List"); }
		return array[0];
	}

	public E last() {
		if (isEmpty()) { throw new EmptyCollectionException("Unsorted List"); }
		return array[rear - 1];
	}

	public boolean contains(E target) {
		return (indexOf(target) != NOT_FOUND);
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return rear;
	}

	public String toString() {
		String result = "[";
		
		for (int i = 0; i < rear; i++) {
			result += array[i].toString();
			if (i < rear - 1) {
				result += ", ";
			}
		}

		return result + "]";
	}

	// Helper methods

	private E removeElement(int index) {
        if (index < 0 || index >= rear) { throw new IndexOutOfBoundsException();}
		if (isEmpty()) { throw new EmptyCollectionException("Unsorted List"); }
        E result = this.array[index];

        // shift the appropriate elements
		for (int i = index; i < rear - 1; i++) {
			array[i] = array[i+1];
		} 
		
		array[rear - 1] = null; // Clear last element
    	rear--;

    	return result;

    }

	private void addElement(int index, E element) {
		if (index > rear || index < 0) { throw new IndexOutOfBoundsException(); }
		if (rear == array.length) {expandCapacity();}

		for (int i = rear; i > index; i--) {
			array[i] = array[i - 1];
		}

		array[index] = element;
		rear++;
	}


	// IGNORE THE FOLLOWING COMMENTED OUT CODE UNTIL LAB 10
	// DON'T DELETE ME, HOWEVER!!!
	public Iterator<E> iterator() {
		// return new IUArrayListIterator(); // UNCOMMENT ME IN LAB 10
		return null; // REMOVE ME IN LAB 10
	}

	// UNCOMMENT THE CODE BELOW IN LAB 10
	
	// private class IUArrayListIterator implements Iterator<E> {

	// 	private int iterModCount, current;
	// 	private boolean canRemove;

	// 	public IUArrayListIterator() {
	// 		iterModCount = modCount;
	// 		current = 0;
	// 		canRemove = false;
	// 	}

	// 	@Override
	// 	public boolean hasNext() {
    //         if (iterModCount != modCount) {
    //             throw new ConcurrentModificationException();
    //         }
    //         return current < rear;
	// 	}

	// 	@Override
	// 	public E next() {
    //         if (!hasNext()) {
    //             throw new NoSuchElementException();
    //         }
    //         E item = array[current];
	// 		current++;
    //         canRemove = true;
    //         return item;
	// 	}

	// 	@Override
	// 	public void remove() {
    //         if (iterModCount != modCount) {
    //             throw new ConcurrentModificationException();
    //         }
    //         if (!canRemove) {
    //             throw new IllegalStateException();
    //         }
    //         // remove the element in the array at index current-1
    //         // presumably decrement the rear
    //         // presumably the modCount is getting incremented
	// 		// all indices have to back up by one
	// 		current--;
	// 		rear--;
	// 		// shift elements to the left
	// 		for (int i = current; i < rear; i++) {
	// 			array[i] = array[i + 1];
	// 		}
	// 		array[rear] = null;
	// 		modCount++;
	// 		iterModCount++;
	// 		// Can only remove the LAST "seen" element
	// 		// set back to a non-removal state 
    //         canRemove = false;
	// 	}
		
	// }

	// IGNORE THE FOLLOWING CODE
	// DON'T DELETE ME, HOWEVER!!!
	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int startingIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
