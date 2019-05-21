class A2Node {
	public int data; 
	public A2Node next;
	public int freq;
    
	// constructor to create a new node with data equals to parameter i
	public A2Node (int i) {
		next = null;
		data = i;
		freq = 1;
	}
}

