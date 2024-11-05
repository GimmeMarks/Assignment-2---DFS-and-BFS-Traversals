package Assignment_2;

import java.util.Random;

public class Assignment2_Main {
    public static void main(String[] args) {

        // Creates an instance of assignment2 ops to work in this script
        Assignment2_Ops ops = new Assignment2_Ops();
        ops.displayGrid(); //Display the grid

        // Creates a random object to generate the numbers for start and end points
        Random rand = new Random();
        int startX, startY, endX, endY;

        // ChatGPT helped to make this next "do while" to help create start points that arent on obsticles 
        // Was going to do if statements, but it said that do while would ensure that the code would lood at least
        // once before checking the next condition. if we use an if, it would only do it once and would try again

        // Find valid start points that arent obsticles
        do {
            // Randomly picks a position for the start points x and y
            startX = rand.nextInt(Assignment2_Ops.GRID_SIZE);
            startY = rand.nextInt(Assignment2_Ops.GRID_SIZE);

        } while (ops.grid[startX][startY] == Assignment2_Ops.OBS); //Keep trying until it works

        // Find valid end points that arent obsticles
        do {

            // Randomly picks a position for the start points x and y
            endX = rand.nextInt(Assignment2_Ops.GRID_SIZE);
            endY = rand.nextInt(Assignment2_Ops.GRID_SIZE);

        } while (ops.grid[endX][endY] == Assignment2_Ops.OBS || (startX == endX && startY == endY)); //Keep trying until both works

        //Make the start x and y the start and make the end x and y the end
        ops.grid[startX][startY] = Assignment2_Ops.START;
        ops.grid[endX][endY] = Assignment2_Ops.END;

        //States the start and end points
        System.out.println("Start Point: (" + startX + ", " + startY + ")");
        System.out.println("End Point: (" + endX + ", " + endY + ")");

        // Search using DFS
        ops.clearVisited();

        if (ops.dfs(startX, startY, endX, endY)) {
            System.out.println("DFS Visitation Order:");
            ops.displayGrid(); // Display the grid with the visit order
        } else {
            System.out.println("No path found using DFS.");
        }

        // Search using BFS
        ops.clearVisited(); //Clears the visited paths before going to bfs
        ops.clearGridPaths(); //Clears the + before going to bfs

        ops.grid[startX][startY] = Assignment2_Ops.START;
        ops.grid[endX][endY] = Assignment2_Ops.END;

        if (ops.bfs(startX, startY, endX, endY)) {
            System.out.println("BFS Visitation Order:");
            ops.displayGrid(); // Display the grid with the visit order
        } else {
            System.out.println("No path found using BFS.");
        }
    }
}
