package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

public class IntegerInstruction extends Instruction<IntInsnNode>{
	
	public IntegerInstruction(IntInsnNode insn){
		super(insn);
	}
	
	public int operand(){
		return getInstructionNode().operand;
	}
	
	@Override
	public String toString(){
		return super.toString() + " operand: " + operand();
	}
}
