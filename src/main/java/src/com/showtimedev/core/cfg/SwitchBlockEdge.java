package src.com.showtimedev.core.cfg;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import src.com.showtimedev.utils.InsnUtils;

import java.util.Map;

public final class SwitchBlockEdge extends BlockEdge{
	
	public SwitchBlockEdge(AbstractInsnNode instruction, Map<LabelNode, Block> labelBlockMap){
		super(instruction);
		this.labelBlockMap = labelBlockMap;
	}
	
	private final Map<LabelNode, Block> labelBlockMap;
	
	@Override
	public int terminatingOptions(){
		if(instruction instanceof LookupSwitchInsnNode){
			return ((LookupSwitchInsnNode) instruction).labels.size() + 1;
		}else if(instruction instanceof TableSwitchInsnNode){
			return ((TableSwitchInsnNode) instruction).labels.size() + 1;
		}
		throw new IllegalStateException(InsnUtils.opcodeToString(instruction));
	}
	
	@Override
	public Block[] terminatingBlocks(){
		return labelBlockMap.values().toArray(new Block[0]);
	}
}
