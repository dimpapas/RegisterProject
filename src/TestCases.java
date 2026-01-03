
public class TestCases {

	public static void main(String[] args) {
		SearchSolver solver = new SearchSolver();

		int[][] scenarios = {
				//Small distances
				{1, 5},
				{5, 20},
				{10, 30},
				
				//Medium distances
				{2, 100},
				{10, 200},
				{5, 500},
				
				//Big distances
				{3, 1000},
				{10, 5000},
				
				//Reverse course (Target is smaller than Start)
				{100, 10},
				{500, 2},
				{64, 2}, //Perfect Square
				{81, 3}  //Root-chain
		};
		
		/*Depth-First Search is intentionally excluded from the benchmarks.
		 * DFS may explore very deep paths in this problem's state space,
		 * leading to excessive runtime or StackOverflow errors,
		 * especially for large start-target values*/
		String[] methods = {"breadth", "best", "astar"};
		
		System.out.println("Running Benchmarks...");
		System.out.println("Method, Start, Target, Steps(N), Cost(C), Time(msec)");
		
		/* scenarios is a 2D array where each row represents one test case.
		 * Each row is an int[] with two elements:
		 *   scenario[0] = start value
		 *   scenario[1] = target value
		 * The enhanced for-loop iterates over each row (int[]), not the entire 2D array.
		 */
		for(int[] scenario : scenarios) {
			int start = scenario[0];
			int target = scenario[1];
			
			for(String method: methods) {
				//Garbage collection before each reading, for clearer results
				System.gc()	;
				
				long startTime = System.nanoTime();
				
				RegisterNode result = solver.solve(method, start, target);
				
				long endTime = System.nanoTime();
				double duration = (endTime - startTime) / 1_000_000.0;
				
				if(result != null) {
					//Calculating total steps(N)
					int steps = 0;
					
					//Backtracking the path of the solution
					RegisterNode temp = result;
					while(temp.parent != null) {
						steps++;
						temp = temp.parent;
					}
					
					System.out.printf("%s, %d ,%d, %d, %d, %.4f\n", method, start, target, steps, result.g, duration);
				} else {
					System.out.printf("%s, %d, %d, FAILED\n", method, start, target);
				}
			}
		}
		System.out.println("Benchmark completed.");
	}

}
