package com.chj.windowstack;;

@SuppressWarnings("unchecked")
public class CircleDeque<E> {
	private int front;
	private int size;
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 1000;
	
	public CircleDeque() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[index(i)] = null;
		}
		front = 0;
		size = 0;
	}

	/**
	 * 浠庡熬閮ㄥ叆闃�
	 * @param element
	 */
	public void enQueueRear(E element) {
		ensureCapacity(size + 1);
		
		elements[index(size)] = element;
		size++;
	}

	/**
	 * 浠庡ご閮ㄥ嚭闃�
	 * @param element
	 */
	public E deQueueFront() {
		E frontElement = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return frontElement;
	}

	/**
	 * 浠庡ご閮ㄥ叆闃�
	 * @param element
	 */
	public void enQueueFront(E element) {
		ensureCapacity(size + 1);
		
		front = index(-1);
		elements[front] = element;
		size++;
	}

	/**
	 * 浠庡熬閮ㄥ嚭闃�
	 * @param element
	 */
	public E deQueueRear() {
		int rearIndex = index(size - 1);
		E rear = elements[rearIndex];
		elements[rearIndex] = null;
		size--;
		return rear;
	}

	public E front() {
		return elements[front];
	}

	public E rear() {
		return elements[index(size - 1)];
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("capcacity=").append(elements.length)
		.append(" size=").append(size)
		.append(" front=").append(front)
		.append(", [");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				string.append(", ");
			}
			
			string.append(elements[i]);
		}
		string.append("]");
		return string.toString();
	}
	
	private int index(int index) {
		index += front;
		if (index < 0) {
			return index + elements.length;
		}
		return index - (index >= elements.length ? elements.length : 0);
	}
	
	/**
	 * 淇濊瘉瑕佹湁capacity鐨勫閲�
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		
		// 鏂板閲忎负鏃у閲忕殑1.5鍊�
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;
		
		// 閲嶇疆front
		front = 0;
	}
}
