package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.TypeInsnNode;

public class TypeInstruction extends Instruction<TypeInsnNode> implements Describeable{
	
	public TypeInstruction(TypeInsnNode insn){
		super(insn);
	}
	
	@Override
	public String description(){
		return getInstructionNode().desc;
	}
	
	@Override
	public String toString(){
		return super.toString() + " desc: " + description();
	}
}
