package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.IincInsnNode;

public class IncrementInstruction extends Instruction<IincInsnNode>{
	
	public IncrementInstruction(IincInsnNode insn){
		super(insn);
	}
	
	public int incrementalAmt(){
		return getInstructionNode().incr;
	}
	
	public int varIndex(){
		return getInstructionNode().var;
	}
	
	@Override
	public String toString(){
		return super.toString() + " Increment Amt: " + incrementalAmt() + "  index: " + varIndex();
	}
}
