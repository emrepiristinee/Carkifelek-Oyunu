
public class Stack {
	private int top;
	private Object[] stackelements;

	Stack(int capacity) {
		stackelements = new Object[capacity];
		top = -1;
	}

	void push(Object data) {
		if (isFull())
			System.out.println("Stack overflow");
		else {
			top++;
			stackelements[top] = data;
		}
	}

	Object pop() {
		if (isEmpty()) {
			System.out.println("Stack is empty"); 
			return null;
		} else {
			Object retData = stackelements[top];
			top--;
			return retData;
		}
	}

	Object peek() {
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return null;
		} else
			return stackelements[top];
	}

	boolean isEmpty() {
		return (top == -1);
	}

	boolean isFull() {
		return (top + 1 == stackelements.length);
	}

	int size() {
		return top + 1;
	}
}
