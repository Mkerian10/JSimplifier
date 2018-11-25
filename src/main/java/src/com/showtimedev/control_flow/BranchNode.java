package src.com.showtimedev.control_flow;

import src.com.showtimedev.instructions.Instruction;

public class BranchNode implements Node{
	
	public BranchNode(Instruction current, Instruction ifTrue, Instruction ifFalse){
		this.current = current;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}
	
	private final Instruction current;
	
	private final Instruction ifTrue;
	
	private final Instruction ifFalse;
	
	@Override
	public Instruction current(){
		return current;
	}
	
	@Override
	public Instruction[] next(){
		return new Instruction[]{ifTrue, ifFalse};
	}
	
	@Override
	public String toString(){
		return "Current: " + current.toString() + " ||| ifTrue: " + ifTrue.toString() + " ||| ifFalse " + ifFalse.toString();
	}
}
