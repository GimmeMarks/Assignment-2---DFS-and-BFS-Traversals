package Assignment_2;

import java.util.Random;

public class Assignment2_Main {
    public static void main(String[] args) {
        Assignment2_Ops ops = new Assignment2_Ops();
        ops.displayGrid();

        Random rand = new Random();
        int startX, startY, endX, endY;

        // Find valid start and end points
        do {
            startX = rand.nextInt(Assignment2_Ops.GRID_SIZE);
            startY = rand.nextInt(Assignment2_Ops.GRID_SIZE);
        } while (ops.grid[startX][startY] == Assignment2_Ops.OBS);

        do {
            endX = rand.nextInt(Assignment2_Ops.GRID_SIZE);
            endY = rand.nextInt(Assignment2_Ops.GRID_SIZE);
        } while (ops.grid[endX][endY] == Assignment2_Ops.OBS || (startX == endX && startY == endY));

        ops.grid[startX][startY] = Assignment2_Ops.START;
        ops.grid[endX][endY] = Assignment2_Ops.END;

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
        ops.clearVisited();
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
