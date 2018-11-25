package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public abstract class Instruction<K extends AbstractInsnNode> implements ByteCodeInstruction{
	
	public Instruction(K insn){
		this.insn = insn;
	}
	
	private final K insn;
	
	public K getInstructionNode(){
		return insn;
	}
	
	@Override
	public int opcode(){
		return getInstructionNode().getOpcode();
	}
	
	@Override
	public int type(){
		return getInstructionNode().getType();
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName() + " Opcode: " + String.format("0x%02X" ,opcode()) + " Type: " + type();
	}
}
