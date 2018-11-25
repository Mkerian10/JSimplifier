package src.com.showtimedev.control_flow;

import src.com.showtimedev.instructions.Instruction;

public class BasicNode implements Node{
	
	public BasicNode(Instruction current, Instruction next){
		this.current = current;
		this.next = next;
	}
	
	private final Instruction current;
	
	private final Instruction next;
	
	@Override
	public Instruction current(){
		return current;
	}
	
	@Override
	public Instruction[] next(){
		return new Instruction[]{next};
	}
	
	@Override
	public String toString(){
		return "Curr: " + current.toString() + " ||| next " + next.toString();
	}
}
