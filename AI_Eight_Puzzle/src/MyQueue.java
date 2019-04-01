import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

//implementation of my queue
public class MyQueue<Item> implements Iterable<Item> {
	private Node<Item> first;
	private Node<Item> last;
	private int N;

	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}

	// initializes empty queue
	public MyQueue() {
		first = null;
		last = null;
		N = 0;
	}

	// clears current queue
	public void clear() {
		first = null;
		last = null;
		N = 0;
	}

	// checks if queue is empty true if it's empty, otherwise false
	public boolean isEmpty() {
		return first == null;
	}

	// returns the size of the Queue
	public int size() {
		return N;
	}

	// returns the least recently added item to myqueue, throws an exception if
	// queue is empty
	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		return first.item;
	}

	// adds item to the queue
	public void enqueue(Item item) {

		Node<Item> oldlast = last;
		last = new Node<Item>();
		last.item = item;
		last.next = null;
		if (isEmpty())
			first = last;
		else
			oldlast.next = last;
		N++;
	}

	// removes and returns least recently added item in the queue, throws error is
	// queue is empty
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		Item item = first.item;
		first = first.next;
		N--;
		if (isEmpty())
			last = null; // to avoid loitering
		return item;
	}

	// returns an iterator that iterates through the items in the queues in a first
	// in first out order
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	// adds a queue to the front of the current queue
	public void addQueue(MyQueue<Item> queue) {
		if (!queue.isEmpty()) {
			Node<Item> oldFirst = first;
			if (isEmpty()) {
				first = queue.first;
				last = queue.last;
			} else {
				first = queue.first;
				queue.last.next = oldFirst;
			}
			N = N + queue.size();
		}
	}
}
