package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

public class VarInstruction extends Instruction<VarInsnNode>{
	
	public VarInstruction(VarInsnNode insn){
		super(insn);
	}
	
	public int operand(){
		return getInstructionNode().var;
	}
	
	@Override
	public String toString(){
		return super.toString() + " operand: " + operand();
	}
}
