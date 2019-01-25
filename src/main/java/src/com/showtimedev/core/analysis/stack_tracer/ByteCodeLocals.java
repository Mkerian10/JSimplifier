package src.com.showtimedev.core.analysis.stack_tracer;

import src.com.showtimedev.core.analysis.stack_tracer.stack_elements.StackReference;

import java.util.HashMap;
import java.util.Map;

public class ByteCodeLocals{
	
	private final Map<Integer, StackReference> localEnvironment = new HashMap<>();
	
	public void updateVariable(StackReference var, int index){
		localEnvironment.put(index, var);
	}
	
	public StackReference retrieveVariable(int index) throws LocalReferenceNotFoundException{
		StackReference ref = localEnvironment.get(index);
		if(ref != null){
			return ref;
		}
		throw new LocalReferenceNotFoundException("Local variable not found, index: " + index);
	}
}
