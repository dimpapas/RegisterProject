import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class SearchSolver {

	public RegisterNode solve(String method, int start, int target) {
		RegisterNode rootNode = new RegisterNode(start, null, "", 0, 0);
		
		if(start == target) return rootNode;
		
		switch(method) {
			case "breadth":
				return bfs(rootNode, target);
			case "depth":
				return dfs(rootNode, target);
			default:
				System.out.println("Unknown method/Invalid input");
				return null;
		}
	}

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
				The statement asks for a check "at least on the current path."
				The visited set here does something more powerful: it checks the ENTIRE tree we have seen.
				This is safe and faster to avoid loops.*/
				if(!visited.contains(next.value)) {
					visited.add(next.value);
					frontier.push(next);
				}
			}
		}
		//No solution found
		return null;
	}
}
