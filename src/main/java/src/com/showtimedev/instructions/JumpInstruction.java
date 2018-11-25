package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
import jdk.internal.org.objectweb.asm.tree.LabelNode;

public class JumpInstruction extends Instruction<JumpInsnNode>{
	
	public JumpInstruction(JumpInsnNode insn){
		super(insn);
	}
	
	public LabelNode getLabel(){
		return getInstructionNode().label;
	}
	
	@Override
	public String toString(){
		return super.toString() + " label: " + getLabel().getLabel().toString();
	}
}
