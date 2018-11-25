package src.com.showtimedev.instructions;

import jdk.internal.org.objectweb.asm.tree.LabelNode;
import jdk.internal.org.objectweb.asm.tree.LookupSwitchInsnNode;

import java.util.HashMap;
import java.util.Map;

public class LookupSwitchInstruction extends Instruction<LookupSwitchInsnNode>{
	
	public LookupSwitchInstruction(LookupSwitchInsnNode insn){
		super(insn);
	}
	
	public LabelNode defaultLabel(){
		return getInstructionNode().dflt;
	}
	
	public Map<Integer, LabelNode> lookupMap(){
		Map<Integer, LabelNode> map = new HashMap<>();
		for(int i = 0; i < Math.min(getInstructionNode().labels.size(), getInstructionNode().keys.size()); i++){
			map.put(getInstructionNode().keys.get(i), getInstructionNode().labels.get(i));
		}
		return map;
	}
	
	@Override
	public String toString(){
		return super.toString() + " default: " + defaultLabel().toString() + " lookup Map: " + lookupMap().toString();
	}
}
