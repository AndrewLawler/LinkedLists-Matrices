import java.util.*;
import java.io.*;

// Implement a linked list from the object A2Node
class A2List {

	private static Scanner keyboardInput = new Scanner(System.in);
	public static A2Node head, tail; // head and tail of the linked list
	private static final int MaxInitCount = 10;
	private static final int MaxReqCount = 100;
	public static int initCount;
	public static int reqCount;

	public static int[] reqData = new int[MaxReqCount]; // store the requests, accessible to all methods

	// DO NOT change the main method
	public static void main(String[] args) throws Exception {
		A2Node curr;
		int tmp = -1;
		int[] initData = new int[MaxInitCount];

		initCount = 0;
		reqCount = 0;
		head = null;
		tail = null;

		try {
// 			System.out.println();
// 			System.out.print("Enter the initial number of files in the cabinet (1-" + MaxInitCount + "): ");
			initCount = keyboardInput.nextInt();
			if (initCount > MaxInitCount || initCount <= 0)
				System.exit(0);
// 			System.out.print("Enter the initial file IDs in the cabinet (" + initCount + "different +ve integers): ");
			for (int i = 0; i < initCount; i++)
				initData[i] = keyboardInput.nextInt();
//			System.out.println();
// 			System.out.print("Enter the number of file requests (1=" + MaxReqCount + "): ");
			reqCount = keyboardInput.nextInt();
			if (reqCount > MaxReqCount || reqCount <= 0)
				System.exit(0);
// 			System.out.print("Enter the request file IDs (" + reqCount + " different +ve integers): ");
			for (int i = 0; i < reqCount; i++)
				reqData[i] = keyboardInput.nextInt();
		} catch (Exception e) {
			keyboardInput.next();
			System.exit(0);
		}

		try {
			System.out.println("appendIfMiss...");
			// create a list with the input data
			// call appendIfMiss()
			for (int i = initCount - 1; i >= 0; i--) {
				insertNodeHead(new A2Node(initData[i]));
			}
			appendIfMiss();
		} catch (Exception e) {
			System.out.println("appendIfMiss exception! " + e);
		}

		try {
			System.out.println("moveToFront...");
			// empty the previous list and restart with the input data
			// then call moveToFront()
			emptyList();
			for (int i = initCount - 1; i >= 0; i--) {
				insertNodeHead(new A2Node(initData[i]));
			}
			moveToFront();
		} catch (Exception e) {
			System.out.println("moveToFront exception!");
		}
		
		try {
			System.out.println("freqCount...");
			// empty the previous list and restart with the input data
			// then call freqCount()
			emptyList();
			for (int i = initCount - 1; i >= 0; i--) {
				insertNodeHead(new A2Node(initData[i]));
			}
			freqCount();
		} catch (Exception e) {
			System.out.println("freqCount exception!");
		}
	}

	// append to end of list when miss
	static void appendIfMiss() {
		A2Node curr;
		int hits = 0;
		boolean hit = false;
		int currCount = 1;
		// array the same size as requests
		int[] positions = new int[reqCount];
		
		// loop requests
		for (int i = 0; i < reqCount; i++) {
			// set currCount and curr
			currCount = 1;
			curr = head;
			hit = false;
			// loop list
			while (curr != null) {
				// if curr.data = key
				if (curr.data == reqData[i]) {
					// set hit to true and set position[i] to be currCount as this is the distance travelled
					hits++;
					hit = true;
					positions[i] = currCount;
					break;
				}
				// loop next curr
				curr = curr.next;
				currCount++;
			}
			if (hit == false) {
				// we didnt find any value == key
				positions[i] = currCount - 1;
				// add node to the end
				insertNodeTail(new A2Node(reqData[i]));
			}
		}
		// print count of data
		printCount(positions);
		// print hits
		System.out.println("\n" + hits + " h ");
		// print list
		printList();

	}

	// move the file requested to the beginning of the list
	static void moveToFront() {

		A2Node curr;
		A2Node currPrev;
		int hits = 0;
		boolean hit = false;
		int currCount = 1;
		// array the same size as requests
		int[] positions = new int[reqCount];

		// loop requests
		for (int i = 0; i < reqCount; i++) {
			// set currCount to be 1 and set curr to start at head. We then set currPrev to be null as thats one step behind curr.
			currCount = 1;
			curr = head;
			currPrev = null;
			hit = false;
			// loop list
			while (curr != null) {
				// if head.data = request
				if (reqData[i] == head.data) {
					hits++;
					hit = true;
					// add to position array, we have a hit on the head
					positions[i] = currCount;
					break;
				}
				// if hit but not head
				else if (curr.data == reqData[i]) {
					hits++;
					hit = true;
					// set current curr to be removed from list
					currPrev.next = curr.next;
					// add node to the head
					insertNodeHead(curr);
					// add to positions array
					positions[i] = currCount;
					break;
				}
				// loop next curr for all and set currPrev to be curr
				currPrev = curr;
				curr = curr.next;
				currCount++;
			}
			if (hit == false) {
				// we didnt find any value == key
				positions[i] = currCount - 1;
				// add node to the head
				insertNodeHead(new A2Node(reqData[i]));
			}
		}
		// print count of data
		printCount(positions);
		// print hits
		System.out.println("\n" + hits + " h ");
		// print list
		printList();

	}

	// move the file requested so that order is by non-increasing frequency
	static void freqCount() {

		A2Node curr;
		int hits = 0;
		boolean hit = false;
		int currCount = 1;
		// array the same size as requests
		int[] positions = new int[reqCount];

		// loop requests
		for (int i = 0; i < reqCount; i++) {
			// set curr to be head
			currCount = 1;
			curr = head;
			hit = false;
			// loop list
			while (curr != null) {
				// if we have a hit
				if (curr.data == reqData[i]) {
					// adding one to the freq
					curr.freq++;
					hits++;
					hit = true;
					// add to positions array for current posttion
					positions[i] = currCount;
					break;
				}
				// loop next
				curr = curr.next;
				currCount++;
			}
			// we dont have a hit
			if (hit == false) {
				// set position to be currCount - 1 as currCount will be too high
				positions[i] = currCount - 1;
				// insert node to tail
				insertNodeTail(new A2Node(reqData[i]));
			}
			// sort the list based on frequency
			bubbleSort();

		}
		// print out data
		printCount(positions);
		// print out hits
		System.out.println("\n" + hits + " h ");
		// print out list
		printList();

		// at the moment the nodes will have their new frequencies, i need to order them based upon those frequencies


	}

	// CUSTOM METHODS

	// Prints count
	static void printCount(int[] positions) {
		// print the count of all the positions in the desired format
		for (int i = 0; i < reqCount; i++) {
			System.out.print(positions[i] + " ");
		}
	}

	// bubble sort (edited) (lab06)
	static void bubbleSort() {
		A2Node last, curr;
		// if we are not at head, go
		if (head != null) {
			last = null;
			curr = head;
			// loop list
			while (curr.next != last) {
				// loop list
				while (curr.next != last) {
					// if frequency of curr is less, swap
					if (curr.freq < curr.next.freq) {
						swapNode(curr, curr.next);
					}
					// loop list more
					curr = curr.next;
				}
				// set values again
				last = curr;
				curr = head;
			}		
		}
	}

	// swapnode (lab06) edited to take freq and swap freq and data
	static void swapNode(A2Node a, A2Node b) {
		// swap two nodes' data and freq
		int tmp;
		int tmp2;
		tmp = a.data;
		tmp2 = a.freq;
		a.data = b.data;
		a.freq = b.freq;
		b.data = tmp;
		b.freq = tmp2;
	}

	static void insertNodeTail(A2Node newNode) {
		// insert new node at the tail of the list
		tail.next = newNode;
		tail = newNode;
		tail.next = null;
	}

	static void insertNodeHead(A2Node newNode) {
		// insert new node at the head of the list
		newNode.next = head;
		if (head == null)
			tail = newNode;
		head = newNode;

	}

	// DO NOT change this method
	// delete the node at the head of the linked list
	static A2Node deleteHead() {
		A2Node curr;

		curr = head;
		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
		}
		return curr;
	}

	// DO NOT change this method
	// print the content of the list in two orders:
	// from head to tail
	static void printList() {
		A2Node curr;

		System.out.print("List: ");
		curr = head;
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
		System.out.println();
	}

	// DO NOT change this method
	static void emptyList() {

		while (head != null)
			deleteHead();
	}

}
