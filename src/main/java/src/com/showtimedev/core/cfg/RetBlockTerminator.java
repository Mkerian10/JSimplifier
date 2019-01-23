package src.com.showtimedev.core.cfg;

import org.objectweb.asm.tree.AbstractInsnNode;

public final class RetBlockTerminator extends BlockTerminator{
	
	public RetBlockTerminator(AbstractInsnNode instruction){
		super(instruction);
	}
	
	@Override
	public int terminatingOptions(){
		return 0;
	}
	
	@Override
	public Block[] terminatingBlocks(){
		return new Block[0];
	}
}
