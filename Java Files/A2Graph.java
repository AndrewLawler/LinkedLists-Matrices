import java.util.*;
import java.io.*;

class A2Graph {

	private static final int MaxVertex = 100;
	private static final int MinVertex = 2;
	private static Scanner keyboardInput = new Scanner(System.in);
	// adjacency matrix, adjMatrix[i][j] = 1 means i and j are adjacent, 0 otherwise
	public static int[][] adjMatrix = new int[MaxVertex][MaxVertex];
	public static int numVertex; // total number of vertices

	// DO NOT change the main method
	public static void main(String[] args) throws Exception {
		boolean userContinue = true;
		int distance = 1;
		int[][] neighbourMatrix = new int[MaxVertex][MaxVertex];

		input();

		try {
			// System.out.print("Enter a distance (" + MinVertex + "--" + numVertex + ", -1
			// to exit): ");
			distance = keyboardInput.nextInt();
		} catch (Exception e) {
			keyboardInput.next();
		}
		if (distance < MinVertex || distance > numVertex)
			System.out.println("incorrect range");
		else {
			neighbourhood(distance, neighbourMatrix, numVertex);
			printSquareArray(neighbourMatrix, numVertex);
		}

		degreeSeparation();
	}

	// find the degree of separation of the graph using the method neightbourhood()
	static void degreeSeparation() {

		boolean complete = false;
		int hits = 0;
		int degreeOfSeperation = 0;
		int[][] arr = new int[numVertex][numVertex];
		// need to check if two rows only connect to each other, if they do, no graph 
		while(complete!=true){
			//kn^3+3n^2
			for(int l=2; l<numVertex; l++){
					// make arr blank
				zero(arr, numVertex);
					// call neighbourhood to make arr
				neighbourhood(l, arr, numVertex);
					// loop neighbourhood for hits
				hits = loopArray(arr, numVertex);
					// if we have exactly numVertex hits, must be full
				if(hits==numVertex){
					degreeOfSeperation = l;
					complete = true;
					break;
				}
			}
			complete = true;
		}
		if(degreeOfSeperation>0){
			// if we had an answer
			System.out.println("Degree of Seperation is "+degreeOfSeperation);
		}
		else {
			// no answer, must not be connected
			System.out.println("The graph is not connected");
		}
		
	}

	// input parameter: an integer distance
	// output: compute neighbourhood matrix for distance
	static void neighbourhood(int distance, int result[][], int size){
		
		// make result array the same as the adjMatrix
		
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				result[i][j] = adjMatrix[i][j];
			}
		}

		for(int l=1; l<distance; l++){
			// loop row
			for(int i=0; i<size; i++){
				// loop columns
				for(int j=0; j<size; j++){
					// if column == 1, check row
					if(result[i][j]==l){
						// looping new row
						for(int k=0; k<size; k++){
							// if looping for distance 1 
							if(l==1){
								if(result[j][k]==l){
									// if we are not in a diagonal position
									if(i!=k){
										result[i][k] = l+1;
									}
								}
							}
							else{
								// if result is correct distance
								if(result[j][k]+result[i][j] == l+1){
									// if result is 0 or l+1
									if(result[i][k]>l+1 || result[i][k]==0){
										// if we are not diagonal
										if(i!=k){
											result[i][k] = l+1;
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}

	static int loopArray(int array[][], int size){
		// looping array to find hits (spots which are zero)
		int hit = 0;
		for(int i=0; i<size; i++){
			for(int k=0; k<size; k++){
				if(array[i][k]==0){
					hit++;
				}
			}
		}
		// return zeros
		return hit;
	}

	static void zero(int array[][], int size){
		// make entire array zero
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				array[i][j] = 0;
			}
		}
	}

	// DO NOT change this method
	static void printSquareArray(int array[][], int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	// DO NOT change this method
	static void input() {
		int i, j;
		boolean success = false;

		try {
			success = true;
			// System.out.print("How many vertices (" + MinVertex + "--" + MaxVertex + ")?
			// ");
			numVertex = keyboardInput.nextInt();
			if (numVertex > MaxVertex || numVertex < MinVertex) {
				success = false;
			}
			if (success) {
				// System.out.println("Enter adjacency matrix: ");
				for (i = 0; i < numVertex; i++)
					for (j = 0; j < numVertex; j++)
						adjMatrix[i][j] = keyboardInput.nextInt();
				for (i = 0; i < numVertex && success; i++) {
					if (adjMatrix[i][i] != 0)
						success = false;
					for (j = 0; j < numVertex; j++) {
						if (adjMatrix[i][j] != adjMatrix[j][i])
							success = false;
					}
				}
			}
			if (!success) {
				System.out.print("Incorrect range ");
				System.out.print("or adjacency matrix not symmetric ");
				System.out.println("or vertex connected to itself");
				System.exit(0);
			}
		} catch (Exception e) {
			keyboardInput.next();
		}
	}

}
