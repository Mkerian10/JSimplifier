package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.tree.LabelNode;

public class LabelInstruction extends Instruction<LabelNode>{
	
	public LabelInstruction(LabelNode insn){
		super(insn);
	}
	
	public Label getLabel(){
		return getInstructionNode().getLabel();
	}
	
	@Override
	public String toString(){
		return super.toString() + " label: " + getLabel();
	}
}
