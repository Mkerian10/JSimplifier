package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.LabelNode;
import jdk.internal.org.objectweb.asm.tree.TableSwitchInsnNode;

import java.util.List;

public class TableSwitchInstruction extends Instruction<TableSwitchInsnNode>{
	
	public TableSwitchInstruction(TableSwitchInsnNode insn){
		super(insn);
	}
	
	public int min(){
		return getInstructionNode().min;
	}
	
	public int max(){
		return getInstructionNode().max;
	}
	
	public LabelNode getDefault(){
		return getInstructionNode().dflt;
	}
	
	public List<LabelNode> labels(){
		return getInstructionNode().labels;
	}
	
	@Override
	public String toString(){
		return super.toString() + " min: " + min() + " max: " + max() + " getDefault: " + getDefault().toString() + " labels: " + labels().toString();
	}
}
