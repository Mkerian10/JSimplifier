package src.com.showtimedev.simulation;

import jdk.internal.org.objectweb.asm.tree.FieldNode;
import src.com.showtimedev.control_flow.DirectedGraph;
import src.com.showtimedev.control_flow.Node;
import src.com.showtimedev.instructions.FieldInstruction;
import src.com.showtimedev.instructions.VarInstruction;
import src.com.showtimedev.loader.Container;
import src.com.showtimedev.wrappers.FieldWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaEnvironment{
	
	public JavaEnvironment(DirectedGraph graph, Container container){
		this.graph = graph;
		this.container = container;
		initVariables();
	}
	
	public List<FieldWrapper> unknownVariables = new ArrayList<>();
	
	Set<FieldWrapper> criticalVariables = new HashSet<>();
	
	/**
	 * Instructions, allows us to walk through the list
	 */
	private final DirectedGraph graph;
	
	private final Container container;
	
	private void initVariables(){
		List<Node> list = graph.nodeCopy();
		for(Node n : list){
			System.out.println("Node" + n.toString());
			if(n.current() instanceof FieldInstruction){
				System.out.println("Field");
				for(FieldNode fn : graph.getClassNode().fields){
					if(fn.name.equals(((FieldInstruction) n.current()).name())){
						unknownVariables.add(new FieldWrapper(fn, graph.getClassNode()));
						break;
					}
				}
			}else if(n.current() instanceof VarInstruction){

			}
		}
	}
	
	public int test(){
		int i = 10;
		int j = 5;
		return i;
	}
	
}
