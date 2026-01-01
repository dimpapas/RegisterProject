import java.util.ArrayList;
import java.util.List;

public class RegisterNode {
	private static final int MAX_LIMIT = 1_000_000_000; //Maximum limit according to the exercise instructions
	public int value;			//Current value of register
	public RegisterNode parent;	//Parent node
	public String operation;	//Operation performed
	public int operationCost;	//Cost of the operation
	public int g;				//Total cost from root node
	public double h;			//Heuristic value (Used in A* & Best-First)

	
	//Constructor
	public RegisterNode(int value, RegisterNode parent,String operation,int operationCost, int parentG) {
		this.value = value;
		this.parent = parent;
		this.operation = operation;
		this.operationCost = operationCost;
		this.g = parentG + operationCost;
		this.h = 0; //Initialization, because it will be calculated via a method
	}
	
	public List<RegisterNode> getSuccessors() {
		List<RegisterNode> successors = new ArrayList<RegisterNode>();
		
		//1.Increase by one
		if(this.value < MAX_LIMIT) {
			successors.add(new RegisterNode(this.value+1, this, "increase", 2, this.g));
		}
		
		
		//2.Decrease by one
		if(this.value > 0) {
			successors.add(new RegisterNode(this.value-1, this, "decrease", 2, this.g));
		}
		
		
		//3.Double
		if(this.value > 0 && (this.value)*2 <= MAX_LIMIT) {
			int cost = (int) Math.ceil(this.value/2.0) + 1;
			successors.add(new RegisterNode(this.value*2, this, "double", cost, this.g));
		}
		
		
		//4.Half
		if(this.value > 0) {
			int newValue =(int) Math.floor(this.value/2);
			int cost = (int) Math.ceil(this.value/4.0) + 1;
			successors.add(new RegisterNode(newValue, this, "half", cost, this.g));
		}
		
		
		//5.Square
		if(this.value > 1) {//x>1 because both the values 0 and 1 don't change, if raised at the power of 2
			long squareValueLong = (long) this.value * this.value;
			if (squareValueLong <= MAX_LIMIT) {
				int squareValue = (int) squareValueLong;
				int cost = (int) Math.ceil((squareValue - this.value)/4.0) + 1;
				successors.add(new RegisterNode(squareValue, this, "square", cost, this.g));
			}
		}
		
		//6.Square root
		if(this.value > 1) {
			int root = (int) Math.sqrt(this.value);
			if(root * root == this.value) {
				int cost = (int) Math.ceil((this.value - root)/4.0) + 1;
				successors.add(new RegisterNode(root, this, "root", cost, this.g));
			}
		}
		return successors;
	}
}
