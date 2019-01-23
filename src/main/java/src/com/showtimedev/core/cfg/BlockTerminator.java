package src.com.showtimedev.core.cfg;

import lombok.RequiredArgsConstructor;
import org.objectweb.asm.tree.AbstractInsnNode;

@RequiredArgsConstructor
public abstract class BlockTerminator{
	
	/**
	 * Instruction in question for the block terminator
	 */
	private final AbstractInsnNode instruction;
	
	/**
	 * @return The amount of blocks able to be found via the block terminator
	 */
	public abstract int terminatingOptions();
	
	/**
	 *
	 * @return The potential terminating blocks, in no particular order, or empty array if none exist
	 */
	public abstract Block[] terminatingBlocks();
	
}
