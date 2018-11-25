package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import src.com.showtimedev.utils.BCUtils;

public class FieldInstruction extends Instruction<FieldInsnNode> implements Nameable, Describeable, Ownable{
	
	public FieldInstruction(FieldInsnNode insn){
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
