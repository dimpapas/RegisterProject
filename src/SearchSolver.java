import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class SearchSolver {

	public RegisterNode solve(String method, int start, int target) {
		RegisterNode rootNode = new RegisterNode(start, null, "", 0, 0);
		
		//Calculation of the heuristic function for the initial state (needed for Best First Search and A*)
		rootNode.h = calculateHeuristic(rootNode.value, target);
		
		if(start == target) return rootNode;
		
		switch(method) {
			case "breadth":
				return bfs(rootNode, target);
			case "depth":
				return dfs(rootNode, target);
			case "best":
				return bestFirst(rootNode, target);
			case "astar":
				return astar(rootNode, target);
			default:
				System.out.println("Unknown method/Invalid input:" + method);
				return null;
		}
	}


	/*This is the heuristic function
	 * it calculates a cost estimate to the target*/
	private double calculateHeuristic(int currentValue, int target) {
		//In case we find the exact target
		if(currentValue == target) return 0;
		
		/*Distance divided by 4. We divide by 4 because the Square
		 *  command increases the value by a cost/gain ratio of 
		 *  approximately 1/4. This allows us to avoid 
		 *  overestimation (Admissibility). Explained in the README file*/
		return Math.abs(target - currentValue)/4.0;
	}

	//ALGORITHMS
	
	//1. Breadth First Search
	private RegisterNode bfs(RegisterNode rootNode, int target) {
		Queue<RegisterNode> frontier = new LinkedList<RegisterNode>();
		Set<Integer> visited = new HashSet<Integer>();//Set to avoid cycles in the tree
		
		frontier.add(rootNode);
		visited.add(rootNode.value);
		
		while(!frontier.isEmpty()) {
			RegisterNode current = frontier.poll();//Firstly,we take the oldest node
			
			if(current.value == target) {
				return current;
			}
			
			//Creating the children of the current node
			List<RegisterNode> successors = current.getSuccessors();
			for(RegisterNode next : successors) {
				
				//Checks if the value has been visited, in order to avoid cycles
				if(!visited.contains(next.value)) {
					visited.add(next.value);
					frontier.add(next);
				}
			}
		}
		//No solution found
		return null;
	}
	
	
	//2. Depth First Search
	private RegisterNode dfs(RegisterNode rootNode, int target) {
		Stack<RegisterNode> frontier = new Stack<RegisterNode>();
		Set<Integer> visited = new HashSet<Integer>();//Set to avoid cycles in the tree
		
		frontier.push(rootNode);
		visited.add(rootNode.value);
		
		while(!frontier.isEmpty()) {
			RegisterNode current = frontier.pop();//Firstly,we take the most recent node
			
			if(current.value == target) {
				return current;
			}
			
			//Creating the children of the current node
			List<RegisterNode> successors = current.getSuccessors();
			for(RegisterNode next : successors) {
				
				/* Cycle check: If we haven't visited it before
				 * The statement asks for a check "at least on the current path."
				 * The visited set here does something more powerful: it checks 
				 * the ENTIRE tree we have seen. This is safe and faster to avoid loops.*/
				if(!visited.contains(next.value)) {
					visited.add(next.value);
					frontier.push(next);
				}
			}
		}
		//No solution found
		return null;
	}
	
	
	//3. Best First Search
	private RegisterNode bestFirst(RegisterNode rootNode, int target) {
		/*Priority Queue: It uses a heap data structure.
		 * This allows you to extract the "best" node in O(logn) time.*/
		PriorityQueue<RegisterNode> frontier = new PriorityQueue<RegisterNode>(
				//Lambda expression: Compares only based on h
				(n1, n2) -> Double.compare(n1.h, n2.h)
		);
		
		Set<Integer> visited = new HashSet<Integer>();//Set to avoid cycles in the tree
		
		frontier.add(rootNode);
		visited.add(rootNode.value);
		
		while(!frontier.isEmpty()) {
			RegisterNode current = frontier.poll();//Firstly,we take the node with the smallest h
			
			if(current.value == target) return current;
			
			//Creating the children of the current node
			List<RegisterNode> successors = current.getSuccessors();
			for(RegisterNode next : successors) {
				
				//Checks if the value has been visited, in order to avoid cycles
				if(!visited.contains(next.value)) {
					
					//calculation of h for the child
					next.h = calculateHeuristic(next.value, target);
					
					visited.add(next.value);
					frontier.add(next);
				}
			}
		}
		
		//No solution found
		return null;
	}
	
	
	//4. A*(A Star) Search
	private RegisterNode astar(RegisterNode rootNode, int target) {
		PriorityQueue<RegisterNode> frontier = new PriorityQueue<RegisterNode>(
				//Lambda expression: Compares based on f = g + h
				(n1, n2) -> Double.compare(n1.g + n1.h, n2.g + n2.h)
		);
		
		Set<Integer> visited = new HashSet<Integer>();//Set to avoid cycles in the tree
		
		frontier.add(rootNode);
		visited.add(rootNode.value);
		
		while(!frontier.isEmpty()) {
			RegisterNode current = frontier.poll();//Firstly,we take the node with the smallest g + h
			
			if(current.value == target) return current;
			
			//Creating the children of the current node
			List<RegisterNode> successors = current.getSuccessors();
			for(RegisterNode next : successors) {
				
				//Checks if the value has been visited, in order to avoid cycles
				if(!visited.contains(next.value)) {
					
					//calculation of h for the child
					next.h = calculateHeuristic(next.value, target);
					
					visited.add(next.value);
					frontier.add(next);
				}
			}
		}
		return null;
	}
}
