import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * AUTHOR: Jason Fukumoto
 * FILE: TSM.java
 * 
 * PURPOSE: This program will determine the fastest route by using 
 * heuristic.
 * 
 * INPUT FILE - example.mtx (partial):
 *  --------------
 * |%%MatrixMarket|
 * |3 3 6         |
 * |1 2 1.0       |
 * |2 1 2.0       |
 * |1 3 3.0       |
 * |3 1 4.0       |
 *  --------------
 * 
 */

// Depending on the argument input, prints out the cost and visit order.
// If TIME is in the argument, prints out all cost for all heuristic,
//backtracking, and custom heuristic--with the time.
public class TSM {
    public static void main(String[] args) {
        String fileName = args[0];
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Integer> maxNode = new ArrayList<Integer>();
        DGraph myGraph = constructGraph(fileScanner, maxNode);
        Trip huerTrip = heuristic(myGraph);
        System.out.println(huerTrip.toString(myGraph));

    }

    // Finds the minimum distance and returns the trip. For every node,
    // checks the weights of that node to determine the minimum.
    public static Trip heuristic(DGraph myGraph) {
        int city = 1;
        int closestCity = 0;
        Trip currTrip = new Trip(myGraph.getNumNodes());
        currTrip.chooseNextCity(city);
        for (int i = 2; i < myGraph.getNumNodes() + 1; i++) {
            double min = 100000000;
            // Calls the list without duplicates
            for (int neighbor : neighborList(myGraph, currTrip, city)) {
                double distance = myGraph.getWeight(city, neighbor);
                if (currTrip.isCityAvailable(neighbor) && distance < min) {
                    min = distance;
                    closestCity = neighbor;
                }
            }
            currTrip.chooseNextCity(closestCity);
            city = closestCity;
        }
        return currTrip;
    }

    // Returns a list that does not have the same nodes in the list.
    // List used for trip method when iterating through the neighbors.
    public static List<Integer> neighborList(DGraph myGraph, Trip trip,
            int city) {
        List<Integer> modifyList = new ArrayList<Integer>();
        List<Integer> oldList = myGraph.getNeighbors(city);
        for (int i : oldList) {
            if (trip.isCityAvailable(i)) {
                modifyList.add(i);
            }
        }
        return modifyList;
    }

    // Prints out the time for a heuristic
    public static void getTime(DGraph myGraph) {
        long beginTime = System.nanoTime();
        long finishTime = System.nanoTime();
        long totalTime = ((finishTime - beginTime) / 1000000);
        Trip mineTrip = heuristic(myGraph);
        System.out.println("mine: cost = " + mineTrip.tripCost(myGraph) + ", "
                + totalTime + " milliseconds");
    }


    // Determines the max number of nodes from the top line and parses
    // the string to an integer
    public static void getTopLine(boolean topLine, List<Integer> maxNode,
            List<String> newLine) {
        int numNode1 = Integer.parseInt(newLine.get(0));
        int numNode2 = Integer.parseInt(newLine.get(1));
        if (numNode1 > numNode2) {
            maxNode.add(numNode1);
        } else {
            maxNode.add(numNode2);
        }
    }

    // Creates and returns a new list without any white spaces.
    public static List<String> removeSpace(String[] line) {
        List<String> cleanLine = new ArrayList<String>();
        for (String element : line) {
            if (!element.equals("")) {
                cleanLine.add(element);
            }
        }
        return cleanLine;
    }

    // Cleans up each file line by parsing strings to integers. Omits the first
    // line before the trips. Creates and returns a graph object with
    // trips.
    public static DGraph constructGraph(Scanner fileScanner,
            List<Integer> maxNode) {
        boolean topLine = false;
        DGraph myGraph = null;
        while (fileScanner.hasNextLine()) {
            String firstLine = fileScanner.nextLine();
            if (!firstLine.contains("%")) {
                String[] line = firstLine.split(" "); // splits line
                // System.out.println(Arrays.toString(line));
                List<String> cleanLine = removeSpace(line);
                // System.out.println(cleanLine.toString());
                if (!topLine) { // first line only
                    getTopLine(topLine, maxNode, cleanLine);
                    myGraph = new DGraph(maxNode.get(0));
                    topLine = true;
                }
                int start = Integer.parseInt(cleanLine.get(0));
                int end = Integer.parseInt(cleanLine.get(1));
                double cost = Double.parseDouble(cleanLine.get(2));
                myGraph.addEdge(start, end, cost);
            }
        }
        return myGraph;
    }
}