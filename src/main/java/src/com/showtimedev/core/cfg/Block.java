package src.com.showtimedev.core.cfg;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.objectweb.asm.tree.AbstractInsnNode;
import src.com.showtimedev.core.extended.MethodWrapper;
import src.com.showtimedev.utils.InsnUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Block represents a block in a control graph. Defined as a sequence of instructions guaranteed to be executed contiguously
 * barring any exception being thrown. The next Block is defined by the field <code>BlockTerminator</code>. The class also contains
 * the ability to break out into <code>catch</code> blocks, however not <code>finally</code> blocks.
 */
@RequiredArgsConstructor
public class Block{
	
	@Getter
	private final ControlFlowGraph controlFlowGraph;
	
	/**
	 * MethodWrapper this block belongs to.
	 */
	@Getter
	private final MethodWrapper owner;
	
	/**
	 * List of blocks that feed into this block, or null if this is the initial block
	 */
	@Getter
	private final List<Block> tributaryBlock = new ArrayList<>();
	
	/**
	 * List of instructions contained in this block, with the final one being a terminating instruction (RETURN, JUMP, etc.)
	 */
	@Getter
	private final List<AbstractInsnNode> blockInsns = new ArrayList<>();
	
	/**
	 * Maps Exception names to the block of the exception handler, or null if no exceptions are thrown
	 * <p>
	 * e.g (java/lang/IllegalArgumentException -> Catch IllegalArgumentException block
	 */
	@Getter
	private final Map<String, Block> exceptionHandler = new HashMap<>();
	
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private BlockTerminator terminator;
	
	public void addTributary(Block tributary){
		tributaryBlock.add(tributary);
	}
	
	public void addExceptionHandler(String exceptionName, Block handler){
		exceptionHandler.put(exceptionName, handler);
	}
	
	void addInstruction(AbstractInsnNode insn){
		blockInsns.add(insn);
	}
	
	public int instructionAmount(){
		return blockInsns.size();
	}
	
	public String dumpBlock(){
		return "Block! thrown: " + exceptionHandler.keySet().toString() + " hash: " + hashCode() +"\n" + blockInsns.stream().map(InsnUtils::opcodeToString).collect(Collectors.joining("\n"));
	}
	
	@Override
	public int hashCode(){
		return owner.hashCode() * 11 + (tributaryBlock.size() != 0 ? 0x39181f: 1) + getBlockInsns().get(0).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		return obj instanceof Block && obj.hashCode() == hashCode();
	}
}
