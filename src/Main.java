import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*HOW TO RUN VIA CMD(Developer Notes):
 * 1. Compile: javac Main.java RegisterNode.java SearchSolver.java
 * 2. Run: java Main breadth 5 18 solution.txt*/

public class Main {

    public static void main(String[] args) {
    	/*Checks if all 4 arguments were inserted correctly 
    	 *(for example: register.exe breadth 5 18 solution.txt) */
        if (args.length < 4) {
            System.out.println("Error: Insufficient arguments."
            		+ "\nUsage: java Main <method> <start> <target> <output_file>"
            		+ "\nParameters:"
            		+ "\n<method>: breadth, depth, best, astar"
            		+ "\n<start>: Initial integer value (e.g. 5)"
            		+ "\n<target>: Target integer value (e.g. 18)"
            		+ "\n<output_file>: Path to save the solution (e.g. solution.txt)"
            		+ "\nExample:"
            		+ "\njava Main breadth 5 18 solution.txt");
            return;
        }

        String method = args[0];
        int startValue = Integer.parseInt(args[1]);
        int targetValue = Integer.parseInt(args[2]);
        String filename = args[3];

        System.out.println("Starting search with method: " + method);
        System.out.println("Start: " + startValue + ", Target: " + targetValue);

        //Starts measuring time
        long startTime = System.currentTimeMillis();

        //Call the SearchSolver
        SearchSolver solver = new SearchSolver();
        RegisterNode solutionNode = solver.solve(method, startValue, targetValue);

        //Finish measuring time
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        if (solutionNode == null) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution found in " + duration + " ms");
            System.out.println("Total Cost: " + solutionNode.g);
            
            //Backtracking the path of the solution
            List<RegisterNode> path = new ArrayList<RegisterNode>();
            RegisterNode current = solutionNode;
            
            while (current != null) {
                path.add(current);
                current = current.parent;
            }
            
            //The list is now in reverse order (from target -> start), so we reverse it
            Collections.reverse(path);
            
            //Register the solution in the file
            writeSolutionToFile(path, filename);
        }
    }

    private static void writeSolutionToFile(List<RegisterNode> path, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            //The number of steps is the size of the list minus 1 (the initial node is not a step).
            int N = path.size() - 1;
            //The total cost is g of the last node(The List path is 0 indexed).
            int C = path.get(path.size() - 1).g;

            //Line 1: N, C
            writer.println(N + ", " + C + "\t //N is the number of commands in the solution, C is the total cost");

            /*Lines 2 to N+1: instruction number cost
              We start from i=1 because i=0 is the initial state (no operation)*/
            for (int i = 1; i < path.size(); i++) {
                RegisterNode node = path.get(i);
                RegisterNode prevNode = path.get(i-1);
                
                /*Attention to wording of the exercise:
                 *"number... the numbers ON which the commands were executed"
                 *So we print the number of the PREVIOUS NODE (prevNode.value), not the result.
                 *e.g. If I have 5 -> (increase) -> 6, the command was executed ON 5.*/
                writer.println(node.operation + " " + prevNode.value + " " + node.operationCost);
            }
            
            System.out.println("Solution written to " + filename);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}