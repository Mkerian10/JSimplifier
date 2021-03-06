package src.com.showtimedev.core.cfg;

import lombok.Getter;
import org.objectweb.asm.tree.AbstractInsnNode;

public final class SingleblockEdge extends BlockEdge{
	
	public SingleblockEdge(AbstractInsnNode instruction, Block nextBlock){
		super(instruction);
		this.nextBlock = nextBlock;
	}
	
	@Override
	public int terminatingOptions(){
		return 1;
	}
	
	@Override
	public Block[] terminatingBlocks(){
		return new Block[]{nextBlock};
	}
	
	@Getter
	private final Block nextBlock;
}
