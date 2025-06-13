import java.util.Comparator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Quicksort algorithm.
 *
 * @author CPSC 221 Instructors
 */
public class Sort {	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <E> IndexedUnsortedList<E> newList() {
		return new WrappedDLL<E>();
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <E>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <E extends Comparable<E>> void sort(IndexedUnsortedList<E> list) {
		quicksort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <E>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <E> void sort(IndexedUnsortedList <E> list, Comparator<E> c) {
		quicksort(list, c);
	}
	
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <E>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <E extends Comparable<E>> void quicksort(IndexedUnsortedList<E> list) {

		if (list.size() <= 1) {
			return;
		}
		
		E pivot = list.removeFirst();

		IndexedUnsortedList<E> left = new WrappedDLL<E>();
		IndexedUnsortedList<E> right = new WrappedDLL<E>();

		while (!list.isEmpty()) {
			E current = list.removeFirst();

			if (current.compareTo(pivot) < 0) {
				left.addToRear(current);

			} else {
				right.addToRear(current);
			}
		}

		quicksort(left);
		quicksort(right);


		while (!left.isEmpty()) {
			list.addToRear(left.removeFirst());
		}
		list.addToRear(pivot);
    	while (!right.isEmpty()) {
        	list.addToRear(right.removeFirst());
        }
}
		
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <E>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <E> void quicksort(IndexedUnsortedList<E> list, Comparator<E> c) {
		if (list.size() <= 1) {
			return;
		}

		E pivot = list.removeFirst();

		IndexedUnsortedList<E> left = new WrappedDLL<E>();
		IndexedUnsortedList<E> right = new WrappedDLL<E>();

		while (!list.isEmpty()) {
			E current = list.removeFirst();

			if (c.compare(current, pivot) < 0) {
				left.addToRear(current);
			} else {
				right.addToRear(current);
			}
		}

		quicksort(left, c);
		quicksort(right, c);

		while (!left.isEmpty()) {
			list.addToRear(left.removeFirst());
		}
		list.addToRear(pivot);
		
		while(!right.isEmpty()) {
			list.addToRear(right.removeFirst());
		}
		
	}
}
		

	

