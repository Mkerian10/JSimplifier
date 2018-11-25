package src.com.showtimedev.control_flow;

import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import src.com.showtimedev.instructions.Instruction;
import src.com.showtimedev.instructions.JumpInstruction;
import src.com.showtimedev.instructions.LabelInstruction;
import src.com.showtimedev.utils.BCUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DirectedGraph implements Iterable<Node>{
	
	public DirectedGraph(ClassNode classNode, Collection<AbstractInsnNode> instructions){
		this.classNode = classNode;
		if(instructions.size() < 2) return;
		make(new ArrayList<>(instructions));
	}
	
	private final ClassNode classNode;
	
	private List<Node> nodes = new ArrayList<>();
	
	public List<Node> nodeCopy(){
		return new ArrayList<>(nodes);
	}
	
	public ClassNode getClassNode(){
		return classNode;
	}
	
	private void make(List<AbstractInsnNode> nodes){
		List<Instruction> instructions = nodes.stream().map(BCUtils::makeInstruction).collect(Collectors.toList());
		
		for(int i = 0; i < instructions.size() - 1; i++){
			Instruction instr = instructions.get(i);
			
			Node n;
			
			if(instr instanceof JumpInstruction){
				//Have to find the destination label
				String desired = ((JumpInstruction) instr).getLabel().getLabel().toString();
				
				Instruction dest = null;
				for(int j = i; j < instructions.size(); j++){
					if(instructions.get(j) instanceof LabelInstruction && ((LabelInstruction) instructions.get(j)).getLabel().toString().equals(desired)){
						dest = instructions.get(j);
						break;
					}
				}
				
				if(dest == null){
					throw new RuntimeException("Destination instruction null, fuck");
				}
				
				n = new BranchNode(instr, instructions.get(i + 1), dest);
			}else{
				n = new BasicNode(instr, instructions.get(i + 1));
			}
			
			this.nodes.add(n);
		}
	}
	
	@Override
	public Iterator<Node> iterator(){
		return this.nodes.iterator();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Node n: this){
			sb.append(n.toString()).append("\n\n");
		}
		return sb.toString();
	}
}
