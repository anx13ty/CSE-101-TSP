import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * An algorithm to find an optimal solution to the Traveling Salesman Problem. This is
 * accomplished by first generating an initial path on the input graph and then improving
 * upon it ..indefinitely.
 * 
 * @author Brent Bahry and John Vivio
 *
 */
public class PathFinder {
	static int NODES;  // number of vertices in input graph
	static double[][] matrix;  // adjacency matrix populated by data from input graph
	static int[][] bestPath;  // the current best path
	static int[][] currentPath;  // the current path being tested
	static double largestWeight = 0;  // the largest weight is the largest defined weight * 10
	static double SOME_HUGE_NUMBER = 200000;  // the default value to show no relationship existing between two vertices in matrix
	
	/**
	 * Calls methods that create an initial best path and then improve on it.
	 * 
	 * @param args an input file containing a graph
	 * @throws IOException trickles down from readInput()
	 */
	public static void main (String[] args) throws IOException {
		readInput(args[0]);
		createInitialPath();
		printPath();
	}
	
	/**
	 * Takes an input file containing a graph and constructs an adjacency matrix
	 * representing the relations between vertices in the input graph; where the presence of
	 * the relationship is indicated by having a value that is the weight of the path between
	 * the two vertices. 
	 * 
	 * If there is no relation between the vertices, this value is initialized to
	 * SOME_HUGE_NUMBER.
	 * 
	 * @param inFile an input file containing a graph
	 * @throws IOException if the input file is not found
	 * 					   if any line of the input file (except the first) does not contain a numeric value
	 */
	public static void readInput(String inFile) throws IOException {		
		try {
			BufferedReader reader = new BufferedReader( new FileReader(inFile) );
			String line = reader.readLine();
			NODES = Integer.parseInt(line.substring(1).trim());
			matrix = new double[NODES][NODES];
			initializeMatrix("matrix", NODES, NODES);
			line = reader.readLine();
			
			while (line != null) {
				int i = 0;
				double[] temp = new double[3];
				
				for (String c:line.split("\t"))
					temp[i++] = Double.valueOf(c.trim()).doubleValue();
				
				if (temp[2] > largestWeight) largestWeight = temp[2];
				
				matrix[(int) temp[0] - 1][(int) temp[1] - 1] = temp[2];
				matrix[(int) temp[1] - 1][(int) temp[0] - 1] = temp[2];
				line = reader.readLine();
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println("Input file not found: " + inFile);
			e.printStackTrace();
		}
		catch (NumberFormatException nfe) {
	      System.out.println("NumberFormatException: " + nfe.getMessage());
	    }
	}
	
	/**
	 * Initializes the input array to the given value depending on which array is passed
	 * in as an argument.
	 * 
	 * @param array the array to be initialized
	 * @param rows the number of rows in the input array
	 * @param columns the number of columns in the input array
	 */
	public static void initializeMatrix(String array, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (array == "matrix")
					matrix[i][j] = SOME_HUGE_NUMBER;
				else
					bestPath[i][j] = -1;
			}
		}
	}
	
	/**
	 * Creates an initial best path using a generic greedy algorithm. The <tt>bestPath</tt>
	 * array is an adjacency list representation of the best path constructed from the
	 * adjacency matrix <tt>matrix</tt>. 
	 */
	public static void createInitialPath() {
		int previousNode = -2;
		int currentNode = 0;
		int nextNode = 0;
		double weight;
		bestPath = new int[NODES][2];
		initializeMatrix("bestSolution", NODES, 2);
		
		for (int i = 1; i < NODES; i++) {
			weight = largestWeight;
			bestPath[currentNode][0] = previousNode + 1;
			
			for (int j = 0; j < NODES; j++) {
				if (bestPath[j][0] == -1 && bestPath[j][1] == -1 && matrix[currentNode][j] != SOME_HUGE_NUMBER) {
					if (matrix[currentNode][j] < weight && j != currentNode) {
						weight = matrix[currentNode][j];
						nextNode = j;
					}
				}
			}
			
			bestPath[currentNode][1] = nextNode + 1;
			previousNode = currentNode;
			currentNode = nextNode;
		}
	}
	
	/**
	 * Traverses through the <tt>bestPath</tt> adjacency list and prints out the nodes
	 * in the sequence that makes them an optimal route.
	 */
	public static void printPath() {
		int currentNode = 0;
		int nextNode = 0;
		
		System.out.println(1);  // since the first node in the path is always 1
		
		for (int i = 0; i < NODES; i++) {
			nextNode = bestPath[currentNode][1];
			System.out.println(nextNode);
			currentNode = nextNode - 1;
		}
	}
}
