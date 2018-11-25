package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.tree.InvokeDynamicInsnNode;

import java.util.Arrays;

public class InvokeDynamicInstruction extends Instruction<InvokeDynamicInsnNode> implements Nameable, Describeable{
	
	public InvokeDynamicInstruction(InvokeDynamicInsnNode insn){
		super(insn);
	}
	
	@Override
	public String name(){
		return getInstructionNode().name;
	}
	
	@Override
	public String description(){
		return getInstructionNode().desc;
	}
	
	public Handle getHandle(){
		return getInstructionNode().bsm;
	}
	
	public Object[] args(){
		return getInstructionNode().bsmArgs;
	}
	
	@Override
	public String toString(){
		return super.toString() + " Name: " + name() + " desc: " + description() + " handle: " + getHandle() + " args: " + Arrays.toString(args());
	}
}
