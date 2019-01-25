package src.com.showtimedev.core.analysis.stack_tracer;

import src.com.showtimedev.core.analysis.stack_tracer.stack_elements.StackReference;

import java.util.ArrayDeque;
import java.util.Deque;

public class ByteCodeStack{

	private final Deque<StackReference> stack = new ArrayDeque<>();
	
	public void push(StackReference element){
		stack.push(element);
	}
	
	public StackReference pop(){
		return stack.pop();
	}
	
	public<K extends StackReference> K pop(Class<K> expectedClass) throws UnexpectedStackElementException{
		StackReference ref = pop();
		if(expectedClass.isInstance(ref)){
			return expectedClass.cast(ref);
		}
		throw new UnexpectedStackElementException("Popped reference of invalid type. Expected: " + expectedClass.getSimpleName() + " Actual: " + ref.getClass().getSimpleName());
	}
	
	public void clearStack(){
		stack.clear();
	}

}
