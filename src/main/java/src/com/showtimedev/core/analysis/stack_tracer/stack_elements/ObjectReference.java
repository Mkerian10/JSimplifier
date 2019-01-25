package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class ObjectReference extends StackReference<ObjectReference>{
	
	@Override
	protected ObjectReference cloneSelf(){
		return new ObjectReference();
	}
}
