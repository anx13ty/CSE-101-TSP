import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class PathFinder {
	static final int ROWS = 4;
	static final int COLUMNS = 50000;  // supposedly the largest set of nodes tested will be 20000
	static int INPUT_LINES = 0;  // the number of lines in the input file minus the first line
	static int NODES = 0;
	static double[][] graph;  // array holding all info from input file
	static int bestCost = Integer.MAX_VALUE;
	
	public static void main (String[] args) throws IOException {
		readInput(args[0]);
		setUnvisited();
		printGraph();
	}
	
	public static void setUnvisited() {
		for (int i = 0; i < NODES; i++) {
			graph[3][i] = 0;
		}
	}
	
	public static void printGraph() {
		System.out.println("Number of nodes: " + NODES);
		System.out.println("Graph:");
		for (int i = 0; i < INPUT_LINES; i++) {
			String visited = graph[3][i] == 1 ? "true":"false";
			System.out.println(graph[0][i] + "  --------  " + graph[1][i] + "    ( weight: " + graph[2][i] + " )" + "    [ visited: " + visited + " ]");
		}
	}
	
	public static void readInput(String inFile) throws IOException {		
		try {
			BufferedReader reader = new BufferedReader( new FileReader(inFile) );
			String line = reader.readLine();
			NODES = Integer.parseInt(line.substring(1).trim());
			graph = new double[ROWS][COLUMNS];
			line = reader.readLine();
			
			while (line != null) {
				int i = 0;
				
				for (String c:line.split("\t")) {  
					try {
						graph[i++][INPUT_LINES] = Double.valueOf(c.trim()).doubleValue();
				    } catch (NumberFormatException nfe) {
				         System.out.println("NumberFormatException: " + nfe.getMessage());
				    }
		        }
				
				line = reader.readLine();
				INPUT_LINES++;
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
}
