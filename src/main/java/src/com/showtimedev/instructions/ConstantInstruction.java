package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;

public class ConstantInstruction extends Instruction<LdcInsnNode>{
	
	public ConstantInstruction(LdcInsnNode insn){
		super(insn);
	}
	
	public Object constant(){
		return getInstructionNode().cst;
	}
	
	@Override
	public String toString(){
		return super.toString() + " Constant: " + constant();
	}
}
