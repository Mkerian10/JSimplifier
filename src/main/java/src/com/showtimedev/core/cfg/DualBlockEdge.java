package src.com.showtimedev.core.cfg;

import lombok.Getter;
import org.objectweb.asm.tree.AbstractInsnNode;

public final class DualBlockEdge extends BlockEdge{
	
	public DualBlockEdge(AbstractInsnNode instruction, Block trueBlock, Block falseBlock){
		super(instruction);
		this.trueBlock = trueBlock;
		this.falseBlock = falseBlock;
	}
	
	@Override
	public int terminatingOptions(){
		return 2;
	}
	
	@Override
	public Block[] terminatingBlocks(){
		return new Block[]{falseBlock, trueBlock};
	}
	
	/**
	 * The block that will be executed when the terminating condition is true
	 */
	@Getter
	private final Block trueBlock;
	
	@Getter
	private final Block falseBlock;
	
}
