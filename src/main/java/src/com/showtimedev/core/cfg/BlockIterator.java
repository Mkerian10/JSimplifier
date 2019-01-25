package src.com.showtimedev.core.cfg;

import java.util.*;

public class BlockIterator implements Iterator<Block>{
	
	public BlockIterator(ControlFlowGraph cfg){
		this.cfg = cfg;
		queue.addLast(cfg.getEntranceBlock());
	}
	
	private final ControlFlowGraph cfg;
	
	private final Set<Block> visited = new HashSet<>();
	
	private final LinkedList<Block> queue = new LinkedList<>();
	
	private final LinkedList<Block> handlerQueue = new LinkedList<>();
	
	@Override
	public boolean hasNext(){
		return queue.size() + handlerQueue.size() > 0;
	}
	
	@Override
	public Block next(){
		Block next = null;
		if(queue.size() > 0){
			next = queue.pollFirst();
		}else{
			next = handlerQueue.pollFirst();
		}
		
		Objects.requireNonNull(next);
		
		visited.add(next);
		
		BlockEdge bt = Objects.requireNonNull(next.getTerminator(), next.dumpBlock());
		
		if(bt instanceof DualBlockEdge){
			Block trueBlock = ((DualBlockEdge) bt).getTrueBlock();
			Block falseBlock = ((DualBlockEdge) bt).getFalseBlock();
			
			if(!visited.contains(falseBlock)){
				queue.addFirst(falseBlock);
				visited.add(falseBlock);
			}
			
			if(!visited.contains(trueBlock)){
				queue.addLast(trueBlock);
				visited.add(trueBlock);
			}
			
			
		}else{
			for(Block b : bt.terminatingBlocks()){
				if(!visited.contains(b)){
					queue.addLast(b);
					visited.add(b);
				}
			}
		}
		
		for(Block b: next.getExceptionHandler().values()){
			if(!handlerQueue.contains(b)){
				handlerQueue.addLast(b);
				visited.add(b);
			}
		}
		
		return next;
	}
}
