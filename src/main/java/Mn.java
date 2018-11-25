import src.com.showtimedev.control_flow.DirectedGraph;
import src.com.showtimedev.loader.Container;
import src.com.showtimedev.loader.InputLoader;
import src.com.showtimedev.simulation.JavaEnvironment;
import src.com.showtimedev.wrappers.MethodWrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Mn{
	
	public static void main(String[] args){
		InputLoader inputLoader = new InputLoader("");
		inputLoader.load();
		Container cont = inputLoader.makeContainer();
		
		MethodWrapper wrapper = cont.getMethodOwners().keySet().stream().filter(methodWrapper -> methodWrapper.getNode().name.equals("test")).findFirst().get();
		
		System.out.println(wrapper.getOwner().name + "." + wrapper.getNode().name);
		DirectedGraph graph = new DirectedGraph(cont.getMethodOwners().get(wrapper), Arrays.stream(wrapper.getNode().instructions.toArray()).collect(Collectors.toList()));
		
		JavaEnvironment env = new JavaEnvironment(graph, cont);
		System.out.println(env.unknownVariables.toString());
		
		
	}
	
	
}
