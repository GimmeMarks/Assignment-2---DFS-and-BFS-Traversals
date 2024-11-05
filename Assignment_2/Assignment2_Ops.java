package Assignment_2;

import java.util.*;

public class Assignment2_Ops {

    // Constants for the grid size and symbols
    public static final int GRID_SIZE = 10; // Size
    private static final char EMPTY = ' '; // Empty Cells
    public static final char OBS = '#'; // Cell is an obstacle
    public static final char START = 'S'; // Start position
    public static final char END = 'E'; // End Position

    // Array for grid
    public char[][] grid;

    // Array to check if cell is visisted
    private boolean[][] visited;

    // Directions to move: right, down, left, up
    private final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};


    // To create the grid and visited array
    public Assignment2_Ops() {
        grid = new char[GRID_SIZE][GRID_SIZE];
        visited = new boolean[GRID_SIZE][GRID_SIZE];
        generateGrid(); // Calls to generate the grid
    }

    // Method to generate the grid with random obstacles and spaces
    private void generateGrid() {
        Random rand = new Random();

        //
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // 1 and 4 chance to place an obsticle, or else its empty
                grid[i][j] = rand.nextInt(4) == 0 ? OBS : EMPTY;
            }
        }
    }

    // Method to display the grid
    public void displayGrid() {
        
        // Prints each row of the grid and replaces the , with nothing
        for (char[] row : grid) {
            System.out.println(Arrays.toString(row).replaceAll(",", ""));
        }
        System.out.println(); //prints space
    }

    // This allows to check if the next place is a valid movement in a boolean
    private boolean isValid(int x, int y) {
        // Return result if coordinates x and y are within the grid size and if it isnt visited and if the cell is not an obstacle
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE && !visited[x][y] && grid[x][y] != OBS;
    }


    //Depth-First Search to find path from start to end (startX, int startY to int endX, int endY)
    public boolean dfs(int startX, int startY, int endX, int endY) {
        Stack<int[]> stack = new Stack<>(); // Create a stack to store cells to visit in dfs

        stack.push(new int[]{startX, startY}); // Pushes the start position into the stack
        visited[startX][startY] = true; // Marks the start position as a visited cell

        //Maps the track to the parent of each cell (Had to get ChatGPT to help with this, i honestly dont remember the prompt
        //It was something like "I need help with the reconstruct path and how to implement it into my code")
        Map<String, String> parentMap = new HashMap<>();

        //While stack is not empty
        while (!stack.isEmpty()) {
            int[] current = stack.pop(); //Pop the most recent cell
            int x = current[0], y = current[1]; 

            // This tells it to explore the different directions
            for (int[] dir : directions) {
                int newX = x + dir[0], newY = y + dir[1]; 

                // If the next position is good and not an obsticle, push it onto the stack
                if (isValid(newX, newY)) {
                    stack.push(new int[]{newX, newY}); // Pushes the new position onto the stack
                    visited[newX][newY] = true; // Mark new position as visited onto the visited array
                    parentMap.put(newX + "," + newY, x + "," + y); // Track the oarent of the cell
                }
            }


            // If we reched the end, reconstruct the path and return true
            //T This is how we find if its good
            if (x == endX && y == endY) {
                reconstructPath(parentMap, startX, startY, endX, endY);
                return true;
            }

        }

        // If we exit the loop than there is not good path and return no path found
        return false;
    }


    // Breadth-First Search to find a path from start to end (startX, int startY to int endX, int endY)
    public boolean bfs(int startX, int startY, int endX, int endY) {
        Queue<int[]> queue = new LinkedList<>(); // Create a queue to store cells to visit in dfs
        
        // Add the start position to the queue
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true; // Marks the start as visited

        // Map to track the parent of each cell
        Map<String, String> parentMap = new HashMap<>();

        //While queue is not empty
        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // Removes the front cell from the queue
            int x = current[0], y = current[1];

            // Explore the different directions similar to dfs
            for (int[] dir : directions) {
                int newX = x + dir[0], newY = y + dir[1];

                // Check if the next place is valid and adds it to the queue
                if (isValid(newX, newY)) {
                    queue.add(new int[]{newX, newY}); // Add the new position to queue like dfs
                    visited[newX][newY] = true; // Adds the new spot as visisted
                    parentMap.put(newX + "," + newY, x + "," + y); // Tracks the parent of the cell for reconstruct
                }
            }

            // If we reach the end point, reconstructed the path and retrun true
            if (x == endX && y == endY) {
                reconstructPath(parentMap, startX, startY, endX, endY);
                return true;
            }

        }

        // If we exit the loop than there is not good path and return no path found        
        return false;
    }

    // Method to clear the 'visited' array, marking all cells as unvisited
    public void clearVisited() {
        //for each row visited make false
        for (boolean[] row : visited) {
            Arrays.fill(row, false);
        }
    }

    // This reconstructs the path from start to the end and marks the path with + for the way to geth there
    private void reconstructPath(Map<String, String> parentMap, int startX, int startY, int endX, int endY) {
        // It reconstructs from the end to the beginning
        // Make the end the new current position
        String current = endX + "," + endY;

        // Continue the while loop until we reach the start position
        while (!current.equals(startX + "," + startY)) {
            //Split the current string into x and y corrddinates from the ,
            String[] coords = current.split(",");

            //Convert the string parts into ints ("3" into 3)
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);

            // If the current position is not the start or the end then add a +
            if (grid[x][y] != START && grid[x][y] != END) {
                grid[x][y] = '+'; // Put + to show path
            }
            // Move to the parent of the current position
            current = parentMap.get(current); // stores the parent of each cell visited during the dfs anmd bfs
        }
    }
}
