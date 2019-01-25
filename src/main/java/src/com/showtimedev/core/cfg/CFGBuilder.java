package src.com.showtimedev.core.cfg;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import src.com.showtimedev.core.extended.MethodWrapper;

import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CFGBuilder implements Opcodes{
	
	public static ControlFlowGraph buildCFG(MethodWrapper mw){
		if(mw == null || mw.mn == null || mw.owner == null){
			throw new NullPointerException("Can not build CFG from null MethodWrapper/MethodWrapper contents");
		}
		
		return new CFGBuilder(new ControlFlowGraph(mw), mw).buildGraph();
	}
	
	private final ControlFlowGraph controlFlowGraph;
	
	private final MethodWrapper methodWrapper;
	
	/**
	 * Maps LabelNodes to their blocks if they've been visited already
	 */
	private final Map<LabelNode, Block> visitedLabels = new HashMap<>();
	
	private ControlFlowGraph buildGraph(){
		controlFlowGraph.setEntranceBlock(buildBlocks(0));
		walkBlockGraph();
		handleExceptions();
		return controlFlowGraph;
	}
	
	private void addException(Map<String, Label> endExceptionMap,
							  Map<String, Block> exceptionHandlersFromStrings,
							  TryCatchBlockNode unstartedTryCatch){
		
		endExceptionMap.put(unstartedTryCatch.type, unstartedTryCatch.end.getLabel());
		Block handler = buildBlocks(methodWrapper.mn.instructions.indexOf(unstartedTryCatch.handler));
		controlFlowGraph.addBlockToCollection(handler);
		controlFlowGraph.getInstructions().addAll(handler.getBlockInsns());
		exceptionHandlersFromStrings.put(unstartedTryCatch.type, handler);
		
	}
	
	private void handleExceptions(){
		Map<String, Label> exceptionMap = new HashMap<>();
		Map<String, Block> exceptionHandlersFromStrings = new HashMap<>();
		Set<TryCatchBlockNode> unstartedTryCatches = new HashSet<>(methodWrapper.mn.tryCatchBlocks);
		
		for(Block b: controlFlowGraph){
			//Add exceptions
			AbstractInsnNode l;
			if((l = b.getBlockInsns().get(0)) instanceof LabelNode){
				for(TryCatchBlockNode tcbn: unstartedTryCatches){
					if(tcbn.start.getLabel().equals(((LabelNode)l).getLabel())){
						addException(exceptionMap, exceptionHandlersFromStrings, tcbn);
						unstartedTryCatches.remove(tcbn);
					}
				}
				for(String s: exceptionMap.keySet()){
					//If this is the end of the exception then remove it from the map
					if(((LabelNode)l).getLabel().equals(exceptionMap.get(s))){
						exceptionMap.remove(s);
					}
				}
			}
			for(String s: exceptionMap.keySet()){
				Block handler = exceptionHandlersFromStrings.get(s);
				handler.addTributary(b);
				b.addExceptionHandler(s, handler);
			}
		}
	}
	
	private void walkBlockGraph(){
		LinkedList<Block> offBranches = new LinkedList<>();
		Set<Block> walkedBranches = new HashSet<>();
		offBranches.add(controlFlowGraph.getEntranceBlock());
		
		Block prev = null;
		
		while(offBranches.size() > 0){
			Block curr = offBranches.pollFirst();
			Objects.requireNonNull(curr);
			
			//Set tributaries
			if(prev != null){
				curr.addTributary(curr);
			}
			prev = curr;
			
			//Check if the branch as been walked
			if(walkedBranches.contains(curr)) continue;
			walkedBranches.add(curr);
			
			//Add the various block requirements
			controlFlowGraph.addBlockToCollection(curr);
			controlFlowGraph.getInstructions().addAll(curr.getBlockInsns());
			
			BlockEdge bt = curr.getTerminator();
			
			if(bt instanceof SingleblockEdge){
				offBranches.addLast(((SingleblockEdge) bt).getNextBlock());
			}else if(bt instanceof DualBlockEdge){
				offBranches.addFirst(((DualBlockEdge) bt).getFalseBlock());
				offBranches.addLast(((DualBlockEdge) bt).getTrueBlock());
			}else if(bt instanceof SwitchBlockEdge){
				for(Block b: bt.terminatingBlocks()){
					offBranches.addLast(b);
				}
			}
		}
	}
	
	@SuppressWarnings("ConstantConditions")
	private Block buildBlocks(int startIndex){
		Block b = new Block(controlFlowGraph, methodWrapper);
		
		ListIterator<AbstractInsnNode> iterator = methodWrapper.mn.instructions.iterator(startIndex);
		
		while(iterator.hasNext()){
			AbstractInsnNode insn = iterator.next();
			
			//Dealing with label nodes
			if(insn instanceof LabelNode){
				if(b.instructionAmount() > 0){
					//If the label node is in the middle of the block, make a new block and point to it
					return handleLabel(b, (LabelNode) insn);
				}else{
					//If the label is already known, return the block already created
					if(visitedLabels.containsKey(insn)){
						return visitedLabels.get(insn);
					}else{
						//Store block for later use
						visitedLabels.put((LabelNode) insn, b);
					}
				}
			}
			
			b.addInstruction(insn);
			
			switch(insn.getOpcode()){
				case ARETURN:
				case RET:
				case RETURN:
				case IRETURN:
				case DRETURN:
				case FRETURN:
				case LRETURN:
					return handleReturnOpcodes(b, insn); //Return signals an end of this layer of control flow
				case GOTO:
					return handleGoto(b, (JumpInsnNode) insn); //Gotos continue this layer of control flow, however it starts a new block
				case IF_ACMPEQ:
				case IF_ACMPNE:
				case IF_ICMPEQ:
				case IF_ICMPGE:
				case IF_ICMPGT:
				case IF_ICMPLE:
				case IF_ICMPLT:
				case IF_ICMPNE:
				case IFEQ:
				case IFGE:
				case IFGT:
				case IFLE:
				case IFLT:
				case IFNE:
				case IFNONNULL:
				case IFNULL:
					/*
					If cases are the most complex but still easily handled. Here we need to recursively branch off
					our control flow, one that follows the instructions directly (false), and then we need to compute
					the jump condition (true)
					 */
					return handleIfCase(b, (JumpInsnNode) insn);
					
				case TABLESWITCH:
					TableSwitchInsnNode table = (TableSwitchInsnNode) insn;
					handleSwitchStatement(b, insn, table.labels, table.dflt);
					return b;
				case LOOKUPSWITCH:
					LookupSwitchInsnNode lookup = (LookupSwitchInsnNode)insn;
					handleSwitchStatement(b, insn, lookup.labels, lookup.dflt);
					return b;
				
			}
		}
		
		return b;
	}
	
	private void handleSwitchStatement(Block entry, AbstractInsnNode insn, List<LabelNode> labels, LabelNode dflt){
		Map<LabelNode, Block> labelBlockMap = new HashMap<>();
		
		for(LabelNode l: labels){
			labelBlockMap.put(l, buildBlocks(methodWrapper.mn.instructions.indexOf(l)));
		}
		
		labelBlockMap.put(dflt, buildBlocks(methodWrapper.mn.instructions.indexOf(dflt)));
		
		entry.setTerminator(new SwitchBlockEdge(insn, labelBlockMap));
	}
	
	private Block handleIfCase(Block b, JumpInsnNode jump){
		//Continue regular control flow, following false block
		Block falseBlock = buildBlocks(methodWrapper.mn.instructions.indexOf(jump) + 1);
		//Follow control flow of the jump
		Block trueBlock = buildBlocks(methodWrapper.mn.instructions.indexOf(jump.label));
		
		b.setTerminator(new DualBlockEdge(jump, trueBlock, falseBlock));
		return b;
	}
	
	private Block handleLabel(Block b, LabelNode ln){
		Block next = buildBlocks(methodWrapper.mn.instructions.indexOf(ln));
		b.setTerminator(new SingleblockEdge(b.getBlockInsns().get(b.instructionAmount() - 1), next));
		return b;
	}
	
	private Block handleGoto(Block b, JumpInsnNode insn){
		Block jumpBlock = buildBlocks(methodWrapper.mn.instructions.indexOf(insn.label));
		SingleblockEdge sbt = new SingleblockEdge(insn, jumpBlock);
		b.setTerminator(sbt);
		return b;
	}
	
	private Block handleReturnOpcodes(Block b, AbstractInsnNode insn){
		RetBlockEdge terminator = new RetBlockEdge(insn);
		b.setTerminator(terminator);
		return b;
	}
}
