package src.com.showtimedev.core.cfg;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import src.com.showtimedev.core.extended.MethodWrapper;
import src.com.showtimedev.utils.InsnUtils;

import java.util.*;

public final class ControlFlowGraph implements Iterable<Block>{
	
	ControlFlowGraph(MethodWrapper owner){
		this.owner = owner;
		instructions = new ArrayList<>();
		blocks = new ArrayList<>();
	}
	
	/**
	 * List of instructions, in control flow order
	 */
	@Getter
	private final List<AbstractInsnNode> instructions;
	
	/**
	 * MethodWrapper correlating to the CFG
	 */
	@Getter
	private final MethodWrapper owner;
	
	/**
	 * Opening block of the ControlFlowGraph
	 */
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private Block entranceBlock;
	
	/**
	 * Collection of all the blocks in the control flow graph
	 */
	private Collection<Block> blocks;
	
	/**
	 * @return The amount of blocks in the CFG
	 */
	public int blockAmount(){
		return blocks.size();
	}
	
	void addBlockToCollection(Block b){
		blocks.add(b);
	}
	
	public String blockDump(){
		StringBuilder sb = new StringBuilder();
		sb.append(owner.toString()).append("\n");
		for(Block b: this){
			sb.append("\n").append(b.dumpBlock()).append("\n");
		}
		return sb.toString();
	}
	
	public String controlFlowDump(){
		Map<Label, Integer> labelIntegerMap = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		sb.append(owner.toString()).append("\n");
		for(AbstractInsnNode insn: getInstructions()){
			if(insn instanceof LabelNode){
				sb.append("\tLabel: ").append(resolveLabel(((LabelNode) insn).getLabel(), labelIntegerMap));
			}else{
				sb.append(InsnUtils.opcodeToString(insn));
				if(insn instanceof JumpInsnNode){
					sb.append(" ").append(resolveLabel(((JumpInsnNode) insn).label.getLabel(), labelIntegerMap));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private String resolveLabel(Label l, Map<Label, Integer> map){
		if(!map.containsKey(l)){
			map.put(l, map.size());
		}
		return "L" + map.get(l);
	}
	
	@Override
	public Iterator<Block> iterator(){
		return new BlockIterator(this);
	}
}
