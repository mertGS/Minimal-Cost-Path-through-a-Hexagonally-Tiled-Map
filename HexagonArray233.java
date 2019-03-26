
import java.io.*;
import java.util.*;

public class HexagonArray233 
{

	int[] array = new int[234]; // The array with hexagon costs, starting from 1, 0 ignored for all arrays
	int[][] neighbours = new int[234][6]; // array of neighbours for all hexagons. 0th is up, 1 is next clockwise ...
	int[] costs = new int[234]; // path cost to the hexagon
	int[] predecessors = new int[234]; // predecessor of the hexagon on path
	boolean[] visited = new boolean[234]; // flag to show if the hexagon is already visited
	int[] path = new int[234]; // array to extract the path to it
	
	// Check which hexagon has the lowest cost at the moment and not yet visited
	int GetNextHexagon()
	{
		int m = Integer.MAX_VALUE;
		int h = 0;
		
		// Iterate trough all hexagons and find the one which is not yet visited and has minimum cost
		for (int i = 1; i <= 233; i++)
		{
			if (!visited[i])
			{
				if (costs[i] < m)
				{
					m = costs[i];
					h = i;
				}
			}
		}
		return h;
	}
	
	// Check if the path from current hexagon to the neighbour lowers the neighbours path cost
	void UpdateNeighboursCosts(int hexagon)
	{
		// Iterate trough all neighbours
		for (int i = 0; i < 6; i++)
		{
			int h = neighbours[hexagon][i];
			// If neighbour exist and is not yet visited
			if ((h != Integer.MAX_VALUE) && (!visited[h]))
			{
				// if current cost is higher then it would be if the path is trough current hexagon then update neighbours cost to the lower and set current hexagon to its predecessor
				if (costs[h] > (array[h] + costs[hexagon]))
				{
					costs[h] = array[h] + costs[hexagon];
					predecessors[h] = hexagon;
				}
			}
		}
	}
	
	public static void main(String[] args) 
	{
		// Create an instance of the class
		HexagonArray233 hexarray = new HexagonArray233();
		
		// Read the file into array
		try 
		{
			Scanner scanner = new Scanner(new File("input.txt"));
			while (scanner.hasNextInt())
			{
				hexarray.array[scanner.nextInt()] = scanner.nextInt();
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get neighbours to all hexagons
		for (int i = 1; i <= 233; i++)
		{
			// Check if upper neighbour exist (12 clock)
			if (i > 15)
			{
				hexarray.neighbours[i][0] = i - 15;
			}
			else
			{
				hexarray.neighbours[i][0] = Integer.MAX_VALUE;
			}

			// Check if upper right neighbour exist (2 clock)
			if ((i < 9) || ((i % 15) == 8))
			{
				hexarray.neighbours[i][1] = Integer.MAX_VALUE;
			}
			else
			{
				hexarray.neighbours[i][1] = i - 7;
			}

			// Check if lower right neighbour exist (4 clock)
		    if ((i > 225) || ((i % 15) == 8))
			{
				hexarray.neighbours[i][2] = Integer.MAX_VALUE;
			}
			else
			{
				hexarray.neighbours[i][2] = i + 8;
			}
			
		    // Check if lower neighbour exist (6 clock)
		    if (i < 219)
			{
				hexarray.neighbours[i][3] = i + 15;
			}
			else
			{
				hexarray.neighbours[i][3] = Integer.MAX_VALUE;
			}
			
			// Check if lower left neighbour exist (8 clock)
		    if ((i > 225) || ((i % 15) == 1))
			{
				hexarray.neighbours[i][4] = Integer.MAX_VALUE;
			}
			else
			{
				hexarray.neighbours[i][4] = i + 7;
			}
		    
			// Check if upper left neighbour exist (10 clock)
			if ((i < 9) || ((i % 15) == 1))
			{
				hexarray.neighbours[i][5] = Integer.MAX_VALUE;
			}
			else
			{
				hexarray.neighbours[i][5] = i - 8;
			}
			
			// Set costs to infinity, and other parameters to not exist
			hexarray.costs[i] = Integer.MAX_VALUE;
			hexarray.predecessors[i] = -1;
			hexarray.visited[i] = false;
			hexarray.path[i] = 0;
		}
		
		// Start Dijkstras algorithm from hexagon 226
		hexarray.costs[226] = hexarray.array[226];
		int hexagon = 226;
		
		// Loop until the destination hexagon is reached
		while (hexagon != 8)
		{
			// Update neighbours costs
			hexarray.UpdateNeighboursCosts(hexagon);
			// Mark current hexagon as visited
			hexarray.visited[hexagon] = true;
			// Set the unvisted hexagon with lowest costs to next hexagon for visit
			hexagon = hexarray.GetNextHexagon();
		}
		
		// Extract the path from predecessors
		int i = 0;
		while (hexagon != 226)
		{
			hexarray.path[i] = hexagon;
			hexagon = hexarray.predecessors[hexagon];
			i++;
		}
		hexarray.path[i] = 226;
		
		try
		{
			// Print the path
			FileWriter fileWriter = new FileWriter("Output.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			while (i >= 0)
			{
				System.out.println(hexarray.path[i]); // print to console
				bufferedWriter.write(Integer.toString(hexarray.path[i]) + "\n"); // print to file
				i--;
			}
		
			// Print the cost
			System.out.print("MINIMAL-COST PATH COSTS: ");
			System.out.println(hexarray.costs[8]);
			bufferedWriter.write("MINIMAL-COST PATH COSTS: " + Integer.toString(hexarray.costs[8]) + "\n");
			bufferedWriter.close();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
