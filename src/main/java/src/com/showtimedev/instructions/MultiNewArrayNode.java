package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;

public class MultiNewArrayNode extends Instruction<MultiANewArrayInsnNode> implements Describeable{
	
	public MultiNewArrayNode(MultiANewArrayInsnNode insn){
		super(insn);
	}
	
	@Override
	public String description(){
		return getInstructionNode().desc;
	}
	
	public int dimensions(){
		return getInstructionNode().dims;
	}
	
	@Override
	public String toString(){
		return super.toString() + " description: " + description() + " dimensions " + dimensions();
	}
}
