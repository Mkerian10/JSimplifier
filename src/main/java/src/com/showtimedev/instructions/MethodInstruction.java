package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import src.com.showtimedev.utils.BCUtils;

public class MethodInstruction extends Instruction<MethodInsnNode> implements Nameable, Describeable, Ownable{
	
	public MethodInstruction(MethodInsnNode insn){
		super(insn);
	}
	
	@Override
	public String name(){
		return getInstructionNode().name;
	}
	
	@Override
	public String owner(){
		return getInstructionNode().owner;
	}
	
	@Override
	public String description(){
		return getInstructionNode().desc;
	}
	
	@Override
	public String toString(){
		return super.toString() + " Name: " + name() + " owner: " + BCUtils.className(owner()) + " desc: " + description();
	}
}
